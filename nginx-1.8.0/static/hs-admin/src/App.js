import React, { Component } from 'react';
<<<<<<< HEAD
import Menu from './components/Menu';
import req from 'superagent';
=======
import Menu from './components/Menu'
import req from 'superagent'
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515

class App extends Component {
	  constructor(props) {
    super(props)
    this.state = {
<<<<<<< HEAD
      account:localStorage.getItem('account'),
      config:{}
=======
      account:localStorage.getItem('account')
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
    }
  }
  componentDidMount() {
  	if(!localStorage.getItem('account')){
<<<<<<< HEAD
     	alert("你还没有登陆，请先登陆！");
      window.location='/login?merchantCode='+localStorage.getItem('merchantCode')
=======
     	alert("尚未登陆，请先登陆！");
      window.location='/login'
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
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
<<<<<<< HEAD
      
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
      
=======
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
  }

  render() {
    return (
<<<<<<< HEAD
    	<div>
    		<div style={{background:'#4D4D4D', height:'55px', width:'100%'}}>
    			<span className="pull-left" style={{padding:'10px 60PX 0px 30px', color:'white'}}>
    				<font size="5">{this.state.config.brand}</font>
    				<font size="3">洪石微商店</font>
    		  </span>
    			<span className="pull-right" style={{padding:'20px 60PX 0px 30px' ,color:'white'}}>欢迎您,管理员~~</span>
    			<div style={{padding:'0px 300PX'}}>
    				<Menu />
    			</div>
    		</div>
    		<div style={{paddingTop:'30px'}} />
	      <div className="row">
	      	<div className="col-md-2">
	        </div>
	        <div className="col-md-8">
	          <div style={{paddingTop:'30px'}} />
	          {this.props.children}
	          <div style={{paddingTop:'6px'}} />
	        </div>
	        <div className="col-md-2" />
	      </div>
	      </div>

=======
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
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
    );
  }
}

export default App;
