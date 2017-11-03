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
class RechargeConfigNew extends React.Component {
  constructor(props) {
    new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)
    super(props)
    this.state = {
      err: null,
      rechargeConfig:{
        startTimeStr:'',
        endTimeStr:'',
        startDateTmp:'',
        endDateTmp:'',
        startTimeTmp:'00:00',
        endTimeTmp:'00:00'
      },
     
      startMoment:moment(),
      endMoment:moment(),
      startTimestamp:0,
      endTimestamp:0
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getRechargeConfig?id='+ (this.props.location.query.id?this.props.location.query.id:0)).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        rechargeConfig:data,
        startMoment:moment(data.startTime),
        endMoment:moment(data.endTime)
      })
    })
  }
  _onStartDateChange = m => {
    var dateString = this.state.startMoment.format('YYYY-MM-DD HH:mm:ss');
    console.log(dateString)
    var rechargeConfig = Object.assign({}, this.state.rechargeConfig)
    rechargeConfig['startTimeStr'] = dateString
    this.setState({
      rechargeConfig: rechargeConfig
    })
  }
  _onEndDateChange = m => {
    var dateString = this.state.endMoment.format('YYYY-MM-DD HH:mm:ss');
    console.log(dateString)
    var rechargeConfig = Object.assign({}, this.state.rechargeConfig)
    rechargeConfig['endTimeStr'] = dateString
    this.setState({
      rechargeConfig: rechargeConfig
    })
  }
  startHandleChange = m => {
    console.log(m.format('YYYY-MM-DD HH:mm:ss'))
    this.setState({
      startMoment:m
    });
  };
  endHandleChange = m => {
    console.log(m.format('YYYY-MM-DD HH:mm:ss'))
    this.setState({
      endMoment:m
    });
  };
  render() {
    return (
      <DocumentTitle title="充值赠送设置">
        <div className="recharge-config-new">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">

            <div className="form-group">
              <label className="control-label col-md-3">充值金额：</label>
              <input type='text' name='money' value={this.state.rechargeConfig.money} onChange={this._handleChange.bind(this, 'money')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">赠送金额：</label>
              <input type='text' name='rewards' value={this.state.rechargeConfig.rewards} onChange={this._handleChange.bind(this, 'rewards')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">起始时间：</label>
              <input type='date' name='startDateTmp' value={this.state.rechargeConfig.startDateTmp} onChange={this._handleChange.bind(this, 'startDateTmp')}/>
              <input type='time' name='startTimeTmp' value={this.state.rechargeConfig.startTimeTmp} onChange={this._handleChange.bind(this, 'startTimeTmp')}/>
            </div>
            {/*<div className="form-group">
            <label className="control-label col-md-3"></label>
              <InputMoment
                moment={this.state.startMoment}
                onChange={this.startHandleChange}
                onSave={this._onStartDateChange}
                prevMonthIcon="ion-ios-arrow-left" // default 
                nextMonthIcon="ion-ios-arrow-right" // default 
              />
            </div>*/}
            <div className="form-group">
              <label className="control-label col-md-3">截止时间：</label>
              <input type='date' name='endDateTmp' value={this.state.rechargeConfig.endDateTmp} onChange={this._handleChange.bind(this, 'endDateTmp')}/>
              <input type='time' name='endTimeTmp' value={this.state.rechargeConfig.endTimeTmp} onChange={this._handleChange.bind(this, 'endTimeTmp')}/>
            </div>
            {/*<div className="form-group">
            <label className="control-label col-md-3"></label>
              <InputMoment
                moment={this.state.endMoment}
                onChange={this.endHandleChange}
                onSave={this._onEndDateChange}
                prevMonthIcon="ion-ios-arrow-left" // default 
                nextMonthIcon="ion-ios-arrow-right" // default 
              />
            </div>*/}
            <div className="form-group">
             {/* <label className="control-label col-md-3 col-md-offset-1 fa fa-plus-square-o plus" ></label>*/}
             <label className="control-label col-md-2"  style={{fontSize:'22px',color:'red'}}>再送：</label>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券1对应商品号：</label>
              <input type='text' name='voucherCode' value={this.state.rechargeConfig.voucherCode} onChange={this._handleChange.bind(this, 'voucherCode')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券1赠送数量：</label>
              <input type='text' name='amount' value={this.state.rechargeConfig.amount} onChange={this._handleChange.bind(this, 'amount')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券2对应商品号：</label>
              <input type='text' name='voucherCodeSecond' value={this.state.rechargeConfig.voucherCodeSecond} onChange={this._handleChange.bind(this, 'voucherCodeSecond')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券2赠送数量：</label>
              <input type='text' name='amountSecond' value={this.state.rechargeConfig.amountSecond} onChange={this._handleChange.bind(this, 'amountSecond')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券3对应商品号：</label>
              <input type='text' name='voucherCodeThird' value={this.state.rechargeConfig.voucherCodeThird} onChange={this._handleChange.bind(this, 'voucherCodeThird')}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">优惠券3赠送数量：</label>
              <input type='text' name='amountThird' value={this.state.rechargeConfig.amountThird} onChange={this._handleChange.bind(this, 'amountThird')}/>
            </div>
             <div className="form-group">
              <label className="control-label col-md-3">每人获券上限次数：</label>
              <input type='text' name='limit' value={this.state.rechargeConfig.limit} onChange={this._handleChange.bind(this, 'limit')}/>
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
  _handleChange = (key, e) => {
    var rechargeConfig = Object.assign({}, this.state.rechargeConfig)
    rechargeConfig[key] = e.target.value
    this.setState({
      rechargeConfig: rechargeConfig
    })
  }
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)
    data.id = this.props.location.query.id;
    this.setState({
      err: null
    })
    if(!data.money){
      this.setState({
        err: '请输入金额'
      })
      return false;
    }
    
    if(!/^\d+(\.\d{1,2})?$/.test(data.money)){
      this.setState({
        err: '请输入正确金额'
      })
      return false;
    }
    if(!data.rewards){
      this.setState({
        err: '请输入赠送金额'
      })
      return false;
    }
    if(!/^\d+(\.\d{1,2})?$/.test(data.rewards)){
      this.setState({
        err: '请输入正确赠送金额'
      })
      return false;
    }
    if(!data.startDateTmp){
      this.setState({
        err: '请输入起始时间'
      })
      return false;
    }
    if(!data.endDateTmp){
      this.setState({
        err: '请输入截止时间'
      })
      return false;
    }
    if(data.voucherCode&&!data.amount){
      this.setState({
        err: '请输入优惠券1赠送数量'
      })
      return false;
    }if(data.voucherCodeSecond&&!data.amountSecond){
      this.setState({
        err: '请输入优惠券2赠送数量'
      })
      return false;
    }
    if(data.voucherCodeThird&&!data.amountThird){
      this.setState({
        err: '请输入优惠券3赠送数量'
      })
      return false;
    }
    if((data.voucherCode||data.voucherCodeSecond||data.voucherCodeThird)&&!data.limit){
      this.setState({
        err: '请输入每人获券上限次数'
      })
      return false;
    }
    data.startTimeStr=data.startDateTmp+" " + data.startTimeTmp + ":00";
    data.endTimeStr=data.endDateTmp+" " + data.endTimeTmp + ":00";
    console.log(data);
    if(Date.parse(data.startTimeStr)>=Date.parse(data.endTimeStr)){
      this.setState({
        err: '开始时间需小于结束时间'
      })
      return false;
    }
    req.post('/uclee-backend-web/rechargeConfigNewHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (res.text) {
        window.location = '/recharge-config-list'
      }
    })
  }
}

export default RechargeConfigNew
