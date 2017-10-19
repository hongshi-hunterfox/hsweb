import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import values from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './lottery.css'

// req 用于发送 AJAX 请求
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class LotteryConfig extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      count: 1,
      configs:[],
      html: '',
      key:[],
      value:[],
      count:[],
      rate:[],
      dateStart:'',
      dateEnd:'',
      timeStart:'',
      timeEnd:'',
      limits:0
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getLotteryConfig').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        configs: data.configs,
        dateStart:data.dateStart,
        dateEnd:data.dateEnd,
        timeStart:data.timeStart,
        timeEnd:data.timeEnd,
        limits:data.limits
      })
      var key = [];
      var value = [];
      var count = [];
      var rate = [];
      var dateStart='';
      var dateEnd='';
      var timeStart='';
      var timeEnd='';
      var limits=0;
      for(var i=0;i<this.state.configs.length;i++){
          key[i] = this.state.configs[i].voucherCode?this.state.configs[i].voucherCode:this.state.configs[i].money;
          value[i] = this.state.configs[i].voucherCode?1:this.state.configs[i].rate===0?0:2;
          count[i] = this.state.configs[i].count;
          rate[i] = this.state.configs[i].rate;
      }
      this.setState({
        key:key,
        value:value,
        count:count,
        rate:rate
      })
    })
  }
  
  render() {
    return (
      <DocumentTitle title="抽奖设置">
        <div className="lottery">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="lottery-line">
              <div className="lottery-line-info">请选择类型</div> 
              <div className="lottery-line-info">请输入优惠券号/金额</div> 
              <div className="lottery-line-info">请输入奖池数量</div> 
              <div className="lottery-line-info">请输入概率</div> 
            </div>
            <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[0]" onChange={this._valueChange.bind(this,'value',0)}>
                  <option selected={!this.state.value.length>0||this.state.value[0]===0?'selected':null } value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>0?this.state.value[0]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>0?this.state.value[0]===2?'selected':null:null}>会员卡金额</option>
              </select>
              <input className="lottery-line-item"  name="key[0]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>0?this.state.key[0]:null} onChange={this._keyChange.bind(this,'key',0)}/> 
              <input className="lottery-line-item"  name="count[0]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>0?this.state.count[0]:null} onChange={this._countChange.bind(this,'count',0)}/> 
              <input className="lottery-line-item"  name="rate[0]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>0?this.state.rate[0]:null} onChange={this._rateChange.bind(this,'rate',0)}/> 
              
            </div>
             <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[1]" onChange={this._valueChange.bind(this,'value',1)}>
                  <option selected={!this.state.value.length>1||this.state.value[1]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>1?this.state.value[1]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>1?this.state.value[1]===2?'selected':null:null}>会员卡金额</option>
              </select>
               <input className="lottery-line-item"  name="key[1]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>1?this.state.key[1]:null} onChange={this._keyChange.bind(this,'key',1)}/> 
              <input className="lottery-line-item"  name="count[1]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>1?this.state.count[1]:null} onChange={this._countChange.bind(this,'count',1)}/> 
              <input className="lottery-line-item"  name="rate[1]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>1?this.state.rate[1]:null} onChange={this._rateChange.bind(this,'rate',1)}/> 
            </div>
             <div className="lottery-line">
              <select className="lottery-line-item"  name="value[2]" onChange={this._valueChange.bind(this,'value',2)}>
                  <option selected={!this.state.value.length>2||this.state.value[2]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>2?this.state.value[2]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>2?this.state.value[2]===2?'selected':null:null}>会员卡金额</option>
              </select>
              <input className="lottery-line-item"  name="key[2]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>2?this.state.key[2]:null} onChange={this._keyChange.bind(this,'key',2)}/> 
              <input className="lottery-line-item"  name="count[2]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>2?this.state.count[2]:null} onChange={this._countChange.bind(this,'count',2)}/> 
              <input className="lottery-line-item"  name="rate[2]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>2?this.state.rate[2]:null} onChange={this._rateChange.bind(this,'rate',2)}/> 
            </div>
             <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[3]" onChange={this._valueChange.bind(this,'value',3)}>
                  <option selected={!this.state.value.length>3||this.state.value[3]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>3?this.state.value[3]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>3?this.state.value[3]===2?'selected':null:null}>会员卡金额</option>
              </select>
               <input className="lottery-line-item"  name="key[3]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>3?this.state.key[3]:null} onChange={this._keyChange.bind(this,'key',3)}/> 
              <input className="lottery-line-item"  name="count[3]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>3?this.state.count[3]:null} onChange={this._countChange.bind(this,'count',3)}/> 
              <input className="lottery-line-item"  name="rate[3]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>3?this.state.rate[3]:null} onChange={this._rateChange.bind(this,'rate',3)}/> 
            </div>
             <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[4]" onChange={this._valueChange.bind(this,'value',4)}>
                  <option selected={!this.state.value.length>4||this.state.value[4]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>4?this.state.value[4]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>4?this.state.value[4]===2?'selected':null:null}>会员卡金额</option>
              </select>
               <input className="lottery-line-item"  name="key[4]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>4?this.state.key[4]:null} onChange={this._keyChange.bind(this,'key',4)}/> 
              <input className="lottery-line-item"  name="count[4]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>4?this.state.count[4]:null} onChange={this._countChange.bind(this,'count',4)}/> 
              <input className="lottery-line-item"  name="rate[4]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>4?this.state.rate[4]:null} onChange={this._rateChange.bind(this,'rate',4)}/> 
            </div>
             <div className="lottery-line">
              <select className="lottery-line-item"  name="value[5]" onChange={this._valueChange.bind(this,'value',5)}>
                  <option selected={!this.state.value.length>5||this.state.value[5]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>5?this.state.value[5]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>5?this.state.value[5]===2?'selected':null:null}>会员卡金额</option>
              </select>
               <input className="lottery-line-item"  name="key[5]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>5?this.state.key[5]:null} onChange={this._keyChange.bind(this,'key',5)}/> 
              <input className="lottery-line-item"  name="count[5]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>5?this.state.count[5]:null} onChange={this._countChange.bind(this,'count',5)}/> 
              <input className="lottery-line-item"  name="rate[5]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>5?this.state.rate[5]:null} onChange={this._rateChange.bind(this,'rate',5)}/> 
            </div>
             <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[6]" onChange={this._valueChange.bind(this,'value',6)}>
                  <option selected={!this.state.value.length>6||this.state.value[6]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>6?this.state.value[6]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>6?this.state.value[6]===2?'selected':null:null}>会员卡金额</option>
              </select>
              <input className="lottery-line-item"  name="key[6]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>6?this.state.key[6]:null} onChange={this._keyChange.bind(this,'key',6)}/> 
              <input className="lottery-line-item"  name="count[6]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>6?this.state.count[6]:null} onChange={this._countChange.bind(this,'count',6)}/> 
              <input className="lottery-line-item"  name="rate[6]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>6?this.state.rate[6]:null} onChange={this._rateChange.bind(this,'rate',6)}/> 
            </div>
             <div  className="lottery-line">
              <select className="lottery-line-item"  name="value[7]" onChange={this._valueChange.bind(this,'value',7)}>
                  <option selected={!this.state.value.length>7||this.state.value[7]===0?'selected':null} value='0'>请选择</option>
                  <option value="1" selected={this.state.value.length>7?this.state.value[7]===1?'selected':null:null}>优惠券</option>
                  <option value="2" selected={this.state.value.length>7?this.state.value[7]===2?'selected':null:null}>会员卡金额</option>
              </select>
              <input className="lottery-line-item"  name="key[7]" ploceHolder="请输入优惠券号/会员卡金额" type="text" value={this.state.key.length>7?this.state.key[7]:null} onChange={this._keyChange.bind(this,'key',7)}/> 
              <input className="lottery-line-item"  name="count[7]" ploceHolder="请输入奖池数量" type="text" value={this.state.count.length>7?this.state.count[7]:null} onChange={this._countChange.bind(this,'count',7)}/> 
              <input className="lottery-line-item"  name="rate[7]" ploceHolder="请输入概率" type="text" value={this.state.rate.length>7?this.state.rate[7]:null} onChange={this._rateChange.bind(this,'rate',7)}/> 
            </div>
            <div>
              每人每天限抽次数：<input type='text' name='limits' value={this.state.limits} onChange={this._limitsChange.bind(this)} />
            </div>
             <div>
              起始时间：<input type='date' name='dateStart' value={this.state.dateStart} onChange={this._dateStartChange.bind(this)}/>
              <input type='time' name='timeStart' value={this.state.timeStart} onChange={this._timeStartChange.bind(this)}/>
            </div>
             <div>
              结束时间：<input type='date' name='dateEnd' value={this.state.dateEnd} onChange={this._dateEndChange.bind(this)}/>
              <input type='time' name='timeEnd' value={this.state.timeEnd} onChange={this._timeEndChange.bind(this)}/>
            </div>
            <ErrorMsg msg={this.state.err} />
            <div className="lottery-line">
              <div className="col-md-9 col-md-offset-3">
                <button type="submit" className="btn btn-primary">提交</button>
              </div>
            </div>
          </form>
        </div>
      </DocumentTitle>
    )
  }
  _dateStartChange = (e) => {
    this.setState({
      dateStart: e.target.value
    })
  }
  _timeStartChange = (e) => {
    this.setState({
      timeStart: e.target.value
    })
  }
_dateEndChange = (e) => {
    this.setState({
      dateEnd: e.target.value
    })
  }
_timeEndChange = (e) => {
    this.setState({
      timeEnd: e.target.value
    })
  }
_limitsChange = (e) => {
    this.setState({
      limits: e.target.value
    })
  }

  _keyChange = (tag,index,e)=>{
    var key = this.state.key;
    key[index] = e.target.value
    console.log(key);
    this.setState({
        key:key
    })
  }
_valueChange = (tag,index,e)=>{
    var value = this.state.value;
    value[index] = e.target.value
    console.log(value);
    this.setState({
        value:value
    })
  }
_countChange = (tag,index,e)=>{
    var count = this.state.count;
    count[index] = e.target.value
    console.log(count);
    this.setState({
        count:count
    })
  }
_rateChange = (tag,index,e)=>{
    var rate = this.state.rate;
    rate[index] = e.target.value
    console.log(rate);
    this.setState({
        rate:rate
    })
  }

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)

    this.setState({
      err: null
    })
    if(!data.timeEnd||!data.timeEnd||!data.dateStart||!data.dateEnd){
       return this.setState({
        err: '请选择时间'
      })
    }
    if(!data.limits){
       return this.setState({
        err: '请输入每人每天限次'
      })
    }

    if(data.key==null || data.value==null||!data.rate==null||!data.count==null){
      return this.setState({
        err: '数据不合法'
      })
    }
    

    if (data.key.length!=data.value.length&&data.key.length!=data.rate.length&&data.key.length!=data.count.length) {
      return this.setState({
        err: '数据不合法'
      })
    }
    if (data.key.length===0||data.rate.length===0||data.count.length===0||data.value.length===0) {
      return this.setState({
        err: '数据不合法'
      })
    }

    var rateCount = 0;
    if(data.rate){
      for(var item in data.rate){
        rateCount = Number(rateCount + Number(data.rate[item]))
      }
      
      if (rateCount!=100) {
        return this.setState({
          err: '概率总和不为100'
        })
      }
    }

    req.post('/uclee-backend-web/lotteryHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (res.text) {
        window.location = '/lottery'
      }
    })
  }
}

export default LotteryConfig
