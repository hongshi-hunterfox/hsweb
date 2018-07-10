import React, { Component } from 'react';
import Menu from './components/Menu'
import req from 'superagent'

class App extends Component {
	  constructor(props) {
    super(props)
    this.state = {
      account:localStorage.getItem('account')
    }
  }
  componentDidMount() {
  	if(!localStorage.getItem('account')){
     	alert("尚未登陆，请先登陆！");
      window.location='/login'
    }
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

  render() {
    return (
      <div className="app container">
        <div className="col-md-12">
          <a href="/"><img src="/images/top.jpg" alt=""/></a>
        </div>
       {<div className="col-md-2">
          <Menu />
        </div>}
        <div className="col-md-10">
          <div style={{
            padding: 15
          }}>
          {
            this.props.children
          }
          </div>
        </div>
      </div>
    );
  }
}

export default App;
