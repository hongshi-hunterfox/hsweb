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
class Forces extends React.Component {
   constructor(props) {
    super(props);
    
    
    this.state = {
    	error: '',
    	errs: '',
    	title: '',
      vName:'',
      
      showNoti: false,
      vBirthday:'',
      vIdNumber:'',
      vCompany:'',
      
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
    	
    	vBirthday: moment(res.body.vBirthday).format('YYYY-MM-DD'),
    	vIdNumber: res.body.vIdNumber,
    	vCompany: res.body.vCompany,
    	vCode: res.body.vCode,
    	
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
                <input type="hidden" value={this.state.vCode} name="vCode" className="form-control"/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>姓名：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.vName} name="vName" className="form-control" onChange={this._change} />
              </div>
       	         
            
               <label className="control-label col-md-3" style={{marginTop:'10px'}}>生日：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="date" value={this.state.vBirthday} name="vBirthday" className="form-control" onChange={this._change} />
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
                <input type="text" value={this.state.vIdNumber} name="vIdNumber" className="form-control" onChange={this._change} />
              </div>
                  <label className="control-label col-md-3" style={{marginTop:'10px'}}>工作单位：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.vCompany} name="vCompany" className="form-control" onChange={this._change} />
              </div>
               <ErrorMsg msg={this.state.error} />
              <div className="yi" style={{marginTop:'10px'}}>
      
            	<button type="submit" className="yi">提交
            	</button>
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
    
   
  
  _submit = e => {
    this.setState({
      disabled:true
    })

    
     e.preventDefault()
    var data = fto(e.target)
    
      if (!data.vName) {
        return this.setState({
          error: '请输入姓名',
        disabled:false
        })
      }

      if (!data.vBirthday) {
        return this.setState({
          error: '请输入生日',
        disabled:false
        })
      }

     

     
      
      
     
      
      
    req
      .post('/uclee-user-web/doUpdateVips')
      .send(data)
      .end((err, res) => {
      	
       
        	var a=confirm("提交成功,点击确定返回个人资料信息");
        	if(a){
        		window.location="/ShowCoupon"
        	}else{
        		window.location="/member-center"
        	}
     
        this.setState({
                showNoti: true
                
             })
       
      })
     
  }
    
  }
          
 
 export default Forces
 
