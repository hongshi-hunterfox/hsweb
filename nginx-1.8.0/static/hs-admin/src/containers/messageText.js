import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import {values} from 'lodash'

import values1 from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './freight.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'


class messageText extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getmsgtext').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        receivedorders: data.receivedorders,
        needdeliver: data.needdeliver,
        distribution: data.distribution,
        completed: data.completed,
        needmention:	data.needmention
      })
    }) 
  }
  render() {
    return (
      <DocumentTitle title="推送配送信息设置">
        <div>
      	{/* 类名加上页面前缀防止冲突 */}
      	<h1>配送信息推送设置:</h1>
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>已接单：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
								<textarea rows="3" cols="20" placeholder="此项必填" className="form-control" name="receivedorders" value={this.state.receivedorders} onChange={this._handleChange.bind(this)}>
								</textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>需配送：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              	<textarea rows="3" cols="20" placeholder="此项必填" className="form-control" name="needdeliver" value={this.state.needdeliver} onChange={this._handleChange.bind(this)}>
								</textarea>
              </div>
               <label className="control-label col-md-3" style={{marginTop:'10px'}}>需自提：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              	<textarea rows="3" cols="20" placeholder="此项必填" className="form-control" name="needmention" value={this.state.needmention} onChange={this._handleChange.bind(this)}>
								</textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>配送中：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              	<textarea rows="3" cols="20" placeholder="此项必填" className="form-control" name="distribution" value={this.state.distribution} onChange={this._handleChange.bind(this)}> 
								</textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>已完成：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              	<textarea rows="3" cols="20" placeholder="此项必填" className="form-control" name="completed" value={this.state.completed} onChange={this._handleChange.bind(this)}>
								</textarea>
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
  _handleChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if(data.receivedorders == undefined){
      this.setState({
        err: "前填写已接单推送信息内容"
      })
      return;
    }
    if(data.needdeliver == undefined){
      this.setState({
        err: "前填写需配送推送信息内容"
      })
      return;
    }
    if(data.needmention == undefined){
      this.setState({
        err: "前填写需自提推送信息内容"
      })
      return;
    }
    if(data.distribution == undefined){
      this.setState({
        err: "前填写配送中推送信息内容"
      })
      return;
    }
    if(data.completed == undefined){
      this.setState({
        err: "前填写配送完成推送信息内容"
      })
      return;
    }
   
    this.setState({
      err: null
    })
    req.post('/uclee-backend-web/updateMsgText').send(data).end((err, res) => {
      if (err) {
        return err
      }
			if(res.text){
          window.location='message-text';
      }
    })
  }
}

export default messageText
