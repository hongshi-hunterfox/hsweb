import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import values from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './recharge-config-new.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'
import moment from 'moment';
import ValueGroup from '../components/ValueGroupTmp'
//import { DateField, Calendar,DateFormatInput } from 'react-date-picker'
import InputMoment from 'input-moment'
//import 'react-date-picker/index.css'
import './input-moment/input-moment.less'
class ProductVoucherSetting extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
    	voucher: '',
    	amount: 0,
    	name: ''
    }
  }

  componentDidMount() {
  	if(this.props.location.query.id !== undefined){
	  	req
			.get('/uclee-backend-web/getByProductVoucherId')
			 .query({
	        id: this.props.location.query.id
	      })
			.end((err, res) => {
	      if (err) {
	        return err
	      }
	      var data = JSON.parse(res.text)
	      this.setState({
	        voucher: data.voucher,
	        amount: data.amount,
	        name: data.name
	      })
	    })
  	}
  }
  
  render() {
    return (
      <DocumentTitle title="指定产品赠送设置">
        <div className="recharge-config-new">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
             <label className="control-label col-md-3 col-md-offset-2"  style={{fontSize:'22px',color:'red'}}>买指定产品送：</label>
            </div>
		        <div className="form-group">
		          <label className="control-label col-md-3 col-md-offset-3">优惠券赠送标题：</label>
		          <input type='text' name='name' value={this.state.name} onChange={this._onChange}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3 col-md-offset-3">优惠券对应商品号：</label>
              <input type='text' name='voucher' value={this.state.voucher} onChange={this._onChange}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3 col-md-offset-3">优惠券赠送数量：</label>
              <input type='text' name='amount' value={this.state.amount} onChange={this._onChange}/>
            </div>
            <ErrorMsg msg={this.state.err} />
            <div className="form-group">
              <div className="col-md-9 col-md-offset-6">
                <button type="submit" className="btn btn-primary">提交</button>
              </div>
            </div>
          </form>
        </div>
      </DocumentTitle>
    )
  }
  _onChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    data.id = this.props.location.query.id;
    
    this.setState({
      err: null
    })
    
    if(!data.name){
      this.setState({
        err: '请输入标题!'
      })
      return false;
    }
    if(!data.voucher){
      this.setState({
        err: '请输入优惠券对应商品号!'
      })
      return false;
    }
    if(this.props.location.query.id === undefined){
    	req.post('/uclee-backend-web/insertProductVoucher').send(data).end((err, res) => {
	      if (err) {
	        return err
	      }
	
	      if (res.text) {
	        window.location = '/product-voucher-list'
	      }
	    })
    }else{
    	req.post('/uclee-backend-web/updateProductVoucher').send(data).end((err, res) => {
	      if (err) {
	        return err
	      }
	
	      if (res.text) {
	        window.location = '/product-voucher-list'
	      }
	    })
    }
  }
}

export default ProductVoucherSetting
