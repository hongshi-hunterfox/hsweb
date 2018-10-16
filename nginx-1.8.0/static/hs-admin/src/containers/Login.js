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
      password: '',
      config: {}
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
componentWillUnmount() {
    clearInterval(this.tick)
  }
  render() {
    return (
      <DocumentTitle title="洪石商城">
	      <div className="body">
					<div className="body-main">
        		<div className="denglu">
        			<div style={{color:"white"}} className="subtitle">
        				<span style={{height:'60px'}} ><font size="6">{this.state.config.brand}</font></span>
        				<div style={{marginTop:'20px'}} >
        					<span><font size="3">洪石微商店</font></span>
				      	</div>
				      </div>
				      <form	className="form-horizontal member-setting-form"	onSubmit={this._submit}>
                <div className="input-group" style={{marginTop:'60px'}}>
								  <span className="input-group-addon" id="basic-addon1">账 号:</span>
								  <input type="tel" name="account" className="form-control" placeholder="请输入账号" aria-describedby="basic-addon1" value={this.state.account} onChange={this._change} />
								</div>
								<div className="input-group" style={{marginTop:'40px'}}>
								  <span className="input-group-addon" id="basic-addon1">密 码:</span>
								  <input type="password" name="password" className="form-control" placeholder="请输入密码" aria-describedby="basic-addon1" value={this.state.password} onChange={this._change} />
								</div>
								<ErrorMsg msg={this.state.error} />
            		<div style={{marginTop:'40px'}}>
	              	<button type="submit" className="btn btn-info btn-lg btn-block">
		                点击登陆
		              </button>
            		</div>
      			</form>
        		</div>
        		<div style={{paddingTop:'180px'}} />
			    	<div className="body-bottom">
			        <span><a href="http://www.in80s.com/custom.asp?id=1">关于我们 </a></span>
			        <span><a href="http://www.in80s.com/guestbook.asp">联系我们</a></span>
			    		<div className="font" style={{marginTop:'10px', color:'white'}}>
				    			洪石软件提供技术服务支持
				    	</div>
			    	</div>
					</div>
					<div style={{paddingTop:'25px'}} />
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
          localStorage.setItem('account', data.account);
    			window.location='/'
        }
      })
  }
}

export default Login
