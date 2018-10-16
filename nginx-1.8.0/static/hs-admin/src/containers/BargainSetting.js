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

class BargainSetting extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      bargain:{},
      id:null,
      err: null
    }
  }
  componentDidMount() {
  	if(this.props.params.id){
    	req.get('/uclee-backend-web/getBargain?id='+this.props.params.id).end((err, res) => {
      		if (err) {
        		return err
      		}
      		var data = JSON.parse(res.text)
      		this.setState({
        		bargain: data.bargain,
      		})
      		console.log('aaa==='+this.state.bargain.ends)
    	})
  	}else{
  		req.get('/uclee-backend-web/getBargain').end((err, res) => {
      		if (err) {
        		return err
      		}
      		var data = JSON.parse(res.text)
      		this.setState(res.body)
    	})
  	}
  }
  render() {
  	var { id } = this.props.params
    return (
      <DocumentTitle title={id ? '编辑设置' : '添加设置'}>
        <div className="global-config">
         {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              {this.props.params?<input type='hidden' name='id' value={this.props.params.id}/>:null}
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>砍价活动名称：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" name="name" value={this.state.bargain.name} className="form-control" onChange={this._Change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>开始日期：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="date"  name="starts" value={this.state.bargain.starts} className="form-control" onChange={this._Change}/>
                <input type="time"  name="startTime" value={this.state.bargain.startTime} className="form-control" onChange={this._Change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>结束日期：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="date" name="ends" value={this.state.bargain.ends} className="form-control" onChange={this._Change}/>
              	<input type="time"  name="endTime" value={this.state.bargain.endTime} className="form-control" onChange={this._Change}/>          
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>最低购买价：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" name="price" value={this.state.bargain.price} className="form-control" onChange={this._Change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>单次最低砍价金额：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" name="minprice" value={this.state.bargain.minprice} className="form-control" onChange={this._Change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>单次最大砍价金额：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" name="maxprice" value={this.state.bargain.maxprice} className="form-control" onChange={this._Change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>关联产品：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" name="productName" value={this.state.bargain.productName} className="form-control" onChange={this._Change}/>
              </div>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              <input type="hidden" name="triesLimit" value={this.state.bargain.triesLimit} className="form-control" onChange={this._Change}/>
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

	_Change = e => {
		var c = Object.assign({}, this.state.bargain)   	
      	c[e.target.name] = e.target.value
      	this.setState({
      		bargain:c
    	})
  	}

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)
    var url = '/uclee-backend-web/insertBargainSetting'
    if (this.props.params.id) {
      url = '/uclee-backend-web/updateBargainSetting'
    }
    req
      .post(url)
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='/bargain-list';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })
  }
}

export default BargainSetting