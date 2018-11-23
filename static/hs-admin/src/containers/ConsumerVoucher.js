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
class ConsumerVoucher extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      count: 1,
      initValue: null, //刚开始要设置成 null，只会从外部拿一次数据
      initRow: 2,
      html: '',
      config:{},
    }
  }
  componentDidMount() {
    req.get('/uclee-backend-web/consumerVoucher').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        initRow: data.size,
        initValue: data.data
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
      <DocumentTitle title="消费推送优惠券设置">
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
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>消费短信内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                {/*<input type="text" value={this.state.config.salesText} name="salesText" className="form-control" onChange={this._handleChange}/>*/}
                <textarea rows="3" cols="20" value={this.state.config.salesText} name="salesText" className="form-control" onChange={this._handleChange}>
                </textarea>
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
    if(data.myKey&&data.myValue){
      data.myKey = values1(data.myKey)
      data.myValue = values1(data.myValue)
      this.setState({
        err: null
      })
      // return
      
    if(!data.salesText) {
      return this.setState({
        err: '请填写消费推送信息内容!'
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
      req.post('/uclee-backend-web/consumerVoucherHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }
        if (res.text) {
          window.location = '/consumer-voucher'
        }
      })
    }else{
      req.post('/uclee-backend-web/truncateConsumerVoucherHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }
        if (res.text) {
          window.location = '/consumer-voucher'
        }
      })
    }
    
    
  }
}
export default ConsumerVoucher