import React, { Component } from 'react';
import Menu from './components/Menu';
import req from 'superagent';
import { Link } from 'react-router';

class App extends Component {
	  constructor(props) {
    super(props)
    this.state = {
      account:localStorage.getItem('account'),
      config:{}
    }
  }
  componentDidMount() {
  	if(!localStorage.getItem('account')){
     	alert("你还没有登陆，请先登陆！");
      window.location='/login?merchantCode='+this.props.location.query.merchantCode
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
      
      req.get('/uclee-backend-web/config')
      .end((err, res) => {
	      if (err) {
	        return err
	      }
	      var data = JSON.parse(res.text)
	      this.setState({
	        config: data.config,
	        logoUrl:data.config?data.config.logoUrl:'',
	        ucenterImg:data.config?data.config.ucenterImg:''
	      })
	      console.log(this.state.config)
	    })
      
  }

  render() {
    return (
    	<div>
    		<div style={{background:'#4D4D4D', height:'55px', width:'100%'}}>
    			<div className="pull-left" style={{padding:'10px 0PX 0px 100px', color:'white'}}>
    				<Link to={'/?merchantCode='+localStorage.getItem('merchantCode')}>
	    				<font size="5" style={{color:'white'}}>{this.state.config.brand}</font>
	    				<font size="3" style={{color:'white'}}>洪石微商店</font>
	    			</Link>
    		  </div>
    		  <div style={{padding:'0px 0PX 0px 330px'}}>
    				<Menu />
    			</div>
    			<div className="pull-right" style={{padding:'20px 60PX 0px 30px' ,color:'white'}}>欢迎您,管理员~~</div>
    		</div>
    		<div className="pull-right" style={{padding:'0px 1PX 0px 0px'}}>
    			<input type="button" name="Submit" onClick={() => {javascript:history.back(-1)}} value="回到上一页" />
    		</div>
    		<div style={{paddingTop:'30px'}} />
	      <div className="row">
	      	<div className="col-md-2" />
	        <div className="col-md-8">
	          <div style={{paddingTop:'30px'}} />
	          {this.props.children}
	          <div style={{paddingTop:'6px'}} />
	        </div>
	        <div className="col-md-2" />
	      </div>
	      </div>

    );
  }
}

export default App;
