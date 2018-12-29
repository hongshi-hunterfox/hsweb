import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import {values} from 'lodash'

import values1 from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './birth-voucher.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

import ValueGroup from '../components/ValueGroup'

class BirthVoucher extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      count: 1,
      initValue: null, //刚开始要设置成 null，只会从外部拿一次数据
      initRow: 2,
      html: '',
      day: 0,
      config:{},
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/birthVoucher').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        initRow: data.size,
        initValue: data.data,
        day: data.day
      })
      console.log(this.state.initValue);
    })
    
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config,
        logoUrl:data.config?data.config.logoUrl:'',
      })
      console.log(this.state.config)
    })
  }
  
  render() {
    return (
      <DocumentTitle title="生日推送优惠券设置">
        <div className="freight">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group ">
              <label className="control-label col-md-3">设置优惠券推送：</label>
              <div className="col-md-9">

                {/*
                  如果 initValue 的值来自 api
                  等拿到值再渲染
                  {
                    this.state.initValue ?
                    <ValueGroup initValue={xxx}/>
                    :
                    null
                  }                  
                */}

                {this.state.initValue
                  ? <ValueGroup
                      condition={''}
                      hideCondition={true}
                      keyText={'优惠券对应商品号'}
                      valueText={'赠送数量'}
                      keyName={'myKey'}
                      valueName={'myValue'}
                      maxRow={100}
                      initRow={this.state.initRow}
                      initValue={this.state.initValue}
                    />
                  : null}
              </div>
              <label className="control-label col-md-3">推送信息内容设置:</label>
	            <div className="col-md-9" style={{marginTop:'10px'}}>
	              	<textarea rows="3" cols="20" value={this.state.config.birthText} name="birthText" className="form-control" onChange={this._handleChange}>
	                </textarea>
	            </div>
	            <label className="control-label col-md-3">推送完善生日信息设置:</label>
	            <div className="col-md-9" style={{marginTop:'10px'}}>
	              	<textarea rows="3" cols="20" value={this.state.config.perfectBirthText} name="perfectBirthText" className="form-control" onChange={this._handleChange}>
	                </textarea>
	            </div>
	            <label className="control-label col-md-3">是否开启无生日信息推送:</label>
	            <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="noBirthdayMessagePush" value={this.state.config.noBirthdayMessagePush} style={{padding:'5px'}} onChange={this._handleChange}>
                  <option value="yes">启用</option>
                  <option value="no">停用</option>
                </select>
              </div>
	            <label className="control-label col-md-3">自动推送提前天数:</label>
	            <div className="col-md-9" >
	            	<div className="row">
	              	<div className="col-xs-2" style={{marginTop:'10px'}}>
			    					<input type="number" className="form-control" 
			    						name="day"
				              value={this.state.day}
				              onChange={e => {
				                this.setState({day: e.target.value})
				              }}
			    					/>
	    						</div>
    						</div>
    					</div>
            </div>
            <ErrorMsg msg={this.state.err} />
            <div className="form-group">
              <div className="col-md-9 col-md-offset-3">
                <button type="submit" className="btn btn-primary">提交</button>
              </div>
            </div>
          </form>
        </div>
      </DocumentTitle>
    )
  }
	_handleChange = (e) => {
	    var c = Object.assign({}, this.state.config)
	    c[e.target.name] = e.target.value
	    this.setState({
	      config: c
	    })
	}
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if(values(data.myKey).length!==values(data.myValue).length){
      this.setState({
        err: "信息填写不完整"
      })
      return;
    }
    if(!data.day){
      this.setState({
        err: "请填写距离会员生日天数"
      })
      return;
    }
    if(data.myKey&&data.myValue){
      data.myKey = values1(data.myKey)
      data.myValue = values1(data.myValue)
      this.setState({
        err: null
      })

      // return
      
    if(!data.birthText) {
      return this.setState({
        err: '请填写推送生日信息内容!'
      })
    }
    req
      .post('/uclee-backend-web/activityConfigHandler')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
      })

      req.post('/uclee-backend-web/birthVoucherHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }

        if (res.text) {
          window.location = '/birth-voucher'
        }
      })
    }else{
      req.post('/uclee-backend-web/truncateBirthVoucherHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }

        if (res.text) {
          window.location = '/birth-voucher'
        }
      })
    }
    
    
  }
}

export default BirthVoucher
