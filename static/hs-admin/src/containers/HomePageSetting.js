import React, { Component } from 'react';
import Menu from '../components/Menu';
import Navi from '../components/Navi';
import req from 'superagent';

class HomePageSetting extends Component {
	  constructor(props) {
	    super(props)
	    this.state = {
	      account:localStorage.getItem('account')
	    }
  }
  componentDidMount() {
  	if(!localStorage.getItem('account')){
     	alert("你还没有登陆，请先登陆！");
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
    	<div>
	      <div style={{paddingTop: '15px'}} />
	      <div className="row">
	      	<div className="col-md-2">
	      		<Navi />
			</div> 
	        <div className="col-md-8">
	          {this.props.children}
	          <div style={{paddingTop:'6px'}} />
	          </div>
	      </div>
      </div>
    );
  }
}

export default HomePageSetting