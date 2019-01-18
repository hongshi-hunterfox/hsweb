import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './global-config.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class UrlVoucherCollection extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      id:this.props.location.query.id,
      uvc:[],
      start:'yyyy-mm-dd',
      end:'yyyy-mm-dd'
    }
  }
  componentDidMount() {
  	if(this.state.id > 0) {
  		req.get('/uclee-backend-web/getUrlVoucherCollection')
	    .query({
	        id:this.state.id,
	      })
	    .end((err, res) => {
	      if (err) {
	        return err
	      }
	      var data = JSON.parse(res.text)
	      this.setState({
	        activityname: data.uvc.activityname,
	        frequencylimit:data.uvc.frequencylimit,
	        starttime:data.uvc.start,
	        endtime:data.uvc.end,
	        dailylimit:data.uvc.dailylimit,
	        ruletext:data.uvc.ruletext,
	      })
	    })
  	}   
  }

  render() {
    return (
      <DocumentTitle title="添加领券活动">
        <div className="global-config">
          {/* 类名加上页面前缀防止冲突 */}

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
 			  <input type="hidden" value={this.state.id} name="id" className="form-control"/>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动名称：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.activityname} name="activityname" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>每人参与次数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.frequencylimit} name="frequencylimit" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>开始时间：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="date" value={this.state.starttime} name="starttime" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>结束时间：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="date" value={this.state.endtime} name="endtime" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>限制每天参与：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="dailylimit" value={this.state.dailylimit} style={{padding:'5px'}} onChange={this._change}>
                  <option value="1">启用</option>
                  <option value="0">停用</option>
                </select>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动规则内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.ruletext} name="ruletext" className="form-control" onChange={this._change}>
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

  _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)

    if (!data.activityname) {
      return this.setState({
        err: '请填写 活动名称'
      })
    }if (!data.frequencylimit) {
      return this.setState({
        err: '请填写 每人参与次数限制'
      })
    }if (!data.starttime) {
      return this.setState({
        err: '请填写 开始时间'
      })
    }
     if (!data.endtime) {
      return this.setState({
        err: '请填写 结束时间'
      })
    }
     if (!data.dailylimit) {
      return this.setState({
        err: '请选择 是否限制每天领取'
      })
    }
    if (!data.ruletext) {
      return this.setState({
        err: '请填写活动规则'
      })
    }
    this.setState({
      err: null
    })
    var url="/uclee-backend-web/insertUrlVoucherCollection";
    if(this.state.id > 0) {
    	url="/uclee-backend-web/updateUrlVoucherCollection"
    }
    req
      .post(url)
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='UrlVoucherCollectionList';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })



  }
}

export default UrlVoucherCollection