import React, { Component } from 'react'
import req from 'superagent'
import StoreBar from './components/StoreBar'
import './App.css'
function authURL(u, appId) {
  return (
    'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' +
    appId +
    '&redirect_uri=' +
    encodeURIComponent(u) +
    '&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect'
  )
}

function getInitialLoadingState() {
  return process.env.REACT_APP_LOCAL_DEBUG ? false : true
}

class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
      loading: getInitialLoadingState()
    }
  }

  componentDidMount() {
    var q = this.props.location.query

    if (q.merchantCode) {
      localStorage.setItem('merchantCode', q.merchantCode)
    }

    if (q.loginRequired === 'false') {
      return this.setState({
        loading: false
      })
    }

    req
      .get('/uclee-user-web/getAppId')
      .query({
        merchantCode: localStorage.getItem('merchantCode')
      })
      .end((err, resAppId) => {
        if (err) {
          return err
        }


        this._sendMerchantCode((err, res) => {
					if (err) {
		        alert('商户信息不正确，请重新从公众号打开')
		        return err
        	}
          // 有 code 访问 callback
          if (q.code) {
            this._wxCallback(() => {
              this.setState({
                loading: false
              })
            })
            return
          } else {
            // 没有 code，检查登录
            this._log(yes => {
              if (yes) {
                this.setState({
                  loading: false
                })
              }
            }, resAppId.text)
          }
        })
      })

    if (localStorage.getItem('merchantCode')) {
      req
        .get('/uclee-user-web/getPageTitle')
        .query({
          mCode: localStorage.getItem('merchantCode')
        })
        .end((err, res) => {
          if (err) {
            return err
          }

          document.title = res.body.merchantName
        })
    }
  }

  _log = (cb, appId) => {
    req.get('/uclee-user-web/getUserInfo').end((err, res) => {
      if (err) {
        window.location='/';
        return err
      }

      // 没有登录
      if (!res.body.nickName) {
        window.location = authURL(window.location.href, appId)
        return
      }

      cb && cb(true)
    })
  }

  _wxCallback = cb => {
    req
      .get('/uclee-user-web/weiXinCallback')
      .query({
        code: this.props.location.query.code
      })
      .end((err, res) => {
        if (err) {
          return err
        }

        cb && cb()
      })
  }

  _sendMerchantCode = cb => {
    var mCode = localStorage.getItem('merchantCode')
    var query = this.props.location.query
    if (query && query.merchantCode) {
      mCode = query.merchantCode
    }

    req
      .get('/uclee-user-web/getUserInfo')
      .query({
        merchantCode: mCode
      })
      .end((err, res) => {
        if (mCode) {
          localStorage.setItem('merchantCode', mCode)
        }
        cb && cb(err, res)
      })
  }

  render() {
    var pathname = this.props.location.pathname
    var showStoreBar =
      pathname === '/' ||
      pathname.indexOf('/detail/') !== -1 ||
      pathname === '/cart'
    /*|| pathname === '/order'*/

    return (
      <div className="app">
      	{showStoreBar ? <StoreBar /> : null}
        {this.state.loading
          ?	<div className="center">
          		loading...
          	</div>
          : <div className="main">
              {this.props.children}
            </div>}
      </div>
    )
  }
}

export default App