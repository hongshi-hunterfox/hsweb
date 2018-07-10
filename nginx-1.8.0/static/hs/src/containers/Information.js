import React from 'react'
import DocumentTitle from 'react-document-title'
import './information.css'
import { browserHistory } from 'react-router'
import Noti from '../components/Noti'
import ErrorMsg from '../components/ErrorMsg'
import validator from 'validator'
import req from 'superagent'
import fto from 'form_to_object'
import moment from 'moment'
class Information extends React.Component {
  constructor(props) {
    super(props);
    
    
    this.state = {
    	error: '',
    	errs: '',
    	title: '',
      vName:'',
      vNumber:'',
      showNoti: false,
      vBirthday:'',
      vIdNumber:'',
      vCompany:'',
      code: '',
      vSex: '1',
      cVipCode: '',
      time: 60,
      fetchingCode: false,
      vCode:'',
      resultmsg: '提交成功',
      disabled:false
      
    }
  }

  componentDidMount() {
     req.get('/uclee-user-web/getVips').end((err, res) => {
      if (err) {
        return err
      }
      this.setState({
    	vName: res.body.vName,
    	vNumber: res.body.vNumber,
    	vBirthday: moment(res.body.vBirthday).format('YYYY-MM-DD'),
    	vIdNumber: res.body.vIdNumber,
    	vCompany: res.body.vCompany,
    	vCode: res.body.vCode,
    	code: res.body.code,
    	vSex: res.body.vSex
    	
      })
   
    })
     req.get('/uclee-user-web/getVipInfo').end((err, res) => {
      if (err) {
        return err
      }
      var d = res.body
      this.setState({
        
        
      })
    })
  }


	componentWillUnmount() {
    clearInterval(this.tick)
  }
	
  render() {
 
 	return(
 		<DocumentTitle title="修改资料">
  		<div>
  		<Noti visible={this.state.showNoti} text={this.state.resultmsg} />
  		<form onSubmit={this._submit} className="form-horizontal" ref="f">
  			<div className="form"> 
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input 
                type="hidden"
                value={this.state.vCode} 
                name="vCode" 
                className="form-control"/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>姓名：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input 
                type="text"
                value={this.state.vName} 
                name="vName"
                className="form-control" 
                onChange={this._change} />
              </div>
       	         <label className="control-label col-md-3" style={{marginTop:'10px'}}>手机号码：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input
                type="text" 
                
                value={this.state.vNumber} 
                name="vNumber"
                className="form-control"
                 onChange={this._change}
                
                />
             </div>
             <label className="control-label col-md-3" style={{marginTop:'10px'}}>验证码：</label>
              <div className="col-md-7" style={{marginTop:'10px'}}>
             
                     <div className="col-xs-7">
                      <input
                        type="text"
                        name="code"
                        className="form-control"
                        placeholder="请输入验证码"
                        value={this.state.code}
                        onChange={this._change}
                      />
                    </div>
                     </div>
                    <div className="col-xs-5">
                      <button
                        type="button"
                        className="form-control"
                        onClick={this._getCode}
                        disabled={this.state.fetchingCode}
                      >
                        {this.state.fetchingCode
                          ? `(${this.state.time})`
                          : '获取验证码'}
                      </button>
                    </div>
                   
                     
                  
                  <div className="form">
               <label className="control-label col-md-3" style={{marginTop:'10px'}}>生日：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input 
                type="date"
                value={this.state.vBirthday} 
                name="vBirthday"
                className="form-control"
                onChange={this._change} />
              </div>
              </div>
              
              <label className="control-label col-xs-3 trim-right">性别：</label>
              <div className="col-md-9">
                <select
                  name="vSex"
                  className="form-control"
                  value={this.state.vSex}
                  onChange={this._change}
                >
                  
                  <option value='1'>男</option>
                  <option value='2'>女</option>
                </select>
              </div>
              
                  <label className="control-label col-md-3" style={{marginTop:'10px'}}>身份证号：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input 
                type="text" 
                value={this.state.vIdNumber} 
                name="vIdNumber"
                className="form-control" 
                onChange={this._change} />
              </div>
                  <label className="control-label col-md-3" style={{marginTop:'10px'}}>工作单位：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input
                type="text"
                value={this.state.vCompany} 
                name="vCompany"
                className="form-control"
                onChange={this._change} />
              </div>
               <ErrorMsg msg={this.state.error} />
              <div className="yi" style={{marginTop:'10px'}}>
      
            	<button type="submit" className="btn btn-primary">提交</button>
              </div>
        	</div> 
        	</form>
        </div>
         </DocumentTitle>
   	)
 
  }

_tab = t => {
    this.setState({
      type: t,
      code:""
    })
  }

  _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  _getCode = () => {
    if (!validator.isMobilePhone(this.state.vNumber, 'zh-CN')) {
      return this.setState({
        error: '请输入正确的手机号码'
      })
    }
    
    
    this.setState({
     fetchingCode: true
    })

    req
      .get('/uclee-user-web/verifyCode')
      .query({
        phone: this.state.vNumber
      })
      .end((err, res) => {
        if (err) {
          return err
        }
        console.log(res.body)
					if(res.body===false){ 
       		 	alert("手机号未修改,不用获取验证码");
       		 	return
					}

        	
        	this._tick()
      })
        
       	 
       
     }
  



  _tick = () => {

    this.tick = setInterval(() => {
      if (this.state.time <= 0) {
        this.setState({
          fetchingCode: false,
          time: 60
        })

        clearInterval(this.tick)
        return
      }

      this.setState(prevState => {
      	
        return {
          time: prevState.time - 1
        }
        
      })
    }, 1000)
  }
  
  
  
  _submit = e => {
    this.setState({
      disabled:true
    })

    
    e.preventDefault()
    var data = fto(e.target)
    if (!data.vNumber) {
      return this.setState({
        error: '请输入手机号码',
        disabled:false
      })
    }
    req
      .get('/uclee-user-web/verifyCodes')
      .query({
        phone: this.state.vNumber
      })
      .end((err, res) => {
        if (err) {
          return err
        }
        console.log(res.body)
					if(res.body===false){ 
       		 	console.log("cg") 
       		 	return
					}else{
						if (!data.code) {
      return this.setState({
        error: '请输入验证码',
        disabled:false
      })
    }
    if (!/^\d{6}$/.test(data.code)) {
      return this.setState({
        error: '验证码不正确',
        disabled:false
      })
    }
					}

        	
        	this._tick()
      })
    
    
    
    
    if (!validator.isMobilePhone(data.vNumber, 'zh-CN')) {
      return this.setState({
        error: '请输入正确的手机号码',
        disabled:false
      })
    }

    
      if (!data.vName) {
        return this.setState({
          error: '请输入姓名',
        disabled:false
        })
      }
      
     
      
      
    req
      .post('/uclee-user-web/doUpdateVips')
      .send(data)
      .end((err, res) => {
      	console.log("aaaaaa="+res.body.result)
        if (res.body.result === 'success'){
        	var a=confirm("修改成功,点击确定返回会员中心页面");
        	if(a){
        		window.location="/member-center"
        	}else{
        		window.location="/information"
        	}
     
        }else{
          return this.setState({
          error: res.body.reason,
        	disabled:false
        })
      			return
        }

        this.setState({
                showNoti: true
                
             })
       
      })
     
  }


  }
          
 

  
 
 
 export default Information