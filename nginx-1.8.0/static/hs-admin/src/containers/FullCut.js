import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import {values} from 'lodash'

import values1 from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './full-cut-shipping.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'
import moment from 'moment';
import ValueGroup from '../components/ValueGroup'
import InputMoment from 'input-moment'
//import 'react-date-picker/index.css'
import './input-moment/input-moment.less'
class FullCut extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      count: 1,
      initValue: null, //刚开始要设置成 null，只会从外部拿一次数据
      initRow: 2,
      html: '',
      startTimeStr:'',
      endTimeStr:'',
      startMoment:moment(),
      endMoment:moment(),
      startDateTmp:'',
      endDateTmp:'',
      startTimeTmp:'00:00',
      endTimeTmp:'00:00'
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/fullCut').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      console.log(data);
      this.setState({
        initRow: data.size,
        initValue: data.data,
        startMoment:moment(data.data.startTime),
        endMoment:moment(data.data.endTime),
        startTimeStr:data.data.startTimeStr,
        endTimeStr:data.data.endTimeStr,
        startDateTmp:data.data.startDateTmp,
        endDateTmp:data.data.endDateTmp,
        startTimeTmp:data.data.startTimeTmp,
        endTimeTmp:data.data.endTimeTmp
      })
    })
  }
  _onStartDateChange = m => {
    var dateString = this.state.startMoment.format('YYYY-MM-DD HH:mm:ss');
    this.setState({
      startTimeStr: dateString
    })
  }
  _onEndDateChange = m => {
    var dateString = this.state.endMoment.format('YYYY-MM-DD HH:mm:ss');
    this.setState({
      endTimeStr: dateString
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
      <DocumentTitle title="满减运费设置">
        <div className="full-cut-shipping">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group ">
              <label className="control-label col-md-3">设置满减：</label>
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
                      condition={'条件'}
                      keyText={'整单满'}
                      valueText={'减'}
                      keyName={'myKey'}
                      valueName={'myValue'}
                      maxRow={100}
                      initRow={this.state.initRow}
                      initValue={this.state.initValue}
                    />
                  : null}
              </div>
            </div>
           <div className="form-group">
              <label className="control-label col-md-3">起始时间：</label>
              {/*<input type='text' name='startTimeStr' value={this.state.startTimeStr} />*/}
               <input type='date' name='startDateTmp' value={this.state.startDateTmp} onChange={this._handleChange.bind(this)}/>
              <input type='time' name='startTimeTmp' value={this.state.startTimeTmp} onChange={this._handleChange.bind(this)}/>
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
              {/*<input type='text' name='endTimeStr' value={this.state.endTimeStr} />*/}
              <input type='date' name='endDateTmp' value={this.state.endDateTmp} onChange={this._handleChange.bind(this)}/>
              <input type='time' name='endTimeTmp' value={this.state.endTimeTmp} onChange={this._handleChange.bind(this)}/>
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
    console.log(data)
    console.log(data.myValue.length)
    console.log(data.myKey.length)
    if(values(data.myKey).length!==values(data.myValue).length){
      this.setState({
        err: "信息填写不完整"
      })
      return;
    }
    if(!data.startDateTmp){
      this.setState({
        err: "请填写开始时间"
      })
      return;
    }
    if(!data.endDateTmp){
      this.setState({
        err: "请填写截止时间"
      })
      return;
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
    /*if(this.state.startMoment>=this.state.endMoment){
      this.setState({
        err: '开始时间需小于结束时间'
      })
      return false;
    }*/
    data.myKey = values1(data.myKey)
    data.myValue = values1(data.myValue)
    
    this.setState({
      err: null
    })

    // return

    req.post('/uclee-backend-web/fullCutHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (res.text) {
        window.location = '/fullCut'
      }
    })
  }
}

export default FullCut
