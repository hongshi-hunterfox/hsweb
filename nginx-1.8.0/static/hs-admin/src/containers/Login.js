import React from 'react'
import DocumentTitle from 'react-document-title'
import ErrorMsg from '../components/ErrorMsg'
import Noti from '../components/Noti'
import './login.css'
import fto from 'form_to_object'
import validator from 'validator'
import req from 'superagent'


class Login extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      error: '',
      account: '',
      password: ''
    }

    this.tick = null
  }


componentDidMount() {
    this._sendMerchantCode()
  }
_sendMerchantCode = (cb) => {
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
componentWillUnmount() {
    clearInterval(this.tick)
  }
  render() {
    return (
      <DocumentTitle title="admin登陆">
      <div className="login">
      	<div className="login-title">
      		欢迎使用微商城后台管理系统
      	</div>
      	<div className="login-css">
          <form
            className="form-horizontal member-setting-form"
            onSubmit={this._submit}
          >
                账号:
                <input
                  type="tel"
                  name="account"
                  className="input-group input-group-lg"
                  placeholder="请输入你的账号"
                  value={this.state.account}
                  onChange={this._change}
                />
              密码:
              <input
                type="password"
                name="password"
                className="input-group input-group-lg"
                placeholder="请输入你的密码"
                value={this.state.password}
                onChange={this._change}
              />
            <ErrorMsg msg={this.state.error} />
            <div className="cs">
              <button type="submit" className="btn btn-primary">
                点击登陆
              </button>
            </div>
            <div className="login-wb">
              洪石软件提供技术服务支持
            </div>
          </form>
        </div>
      </div>
      </DocumentTitle>
    )
  }


  _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if (!data.account) {
      return this.setState({
        error: '请输入账号'
      })
    }

    if (!data.password) {
      return this.setState({
        error: '请输入密码'
      })
    }
    req
      .get('/uclee-backend-web/getAccount')
      .query({
        account: this.state.account,
        password: this.state.password
      })
      .end((err, res) => {
        if (err) {
          return err
        }
        if(!res.body){
          this.setState({
            error: '帐号或密码有误！'
          })
          return
        }else{
        	alert("登陆成功")
          localStorage.setItem('account', data.account);
    			window.location='/'
        }
      })
  }
}

export default Login
