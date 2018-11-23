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
import ValueGroup from '../components/ValueGroupTmp'
import InputMoment from 'input-moment'
//import 'react-date-picker/index.css'
import './input-moment/input-moment.less'
class BindMemberSetting extends React.Component {
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
      endMoment:moment()
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/bindMember').end((err, res) => {
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
        endTimeStr:data.data.endTimeStr
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
      <DocumentTitle title="绑定会员设置">
        <div className="full-cut-shipping">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group ">
              <label className="control-label col-md-3">设置绑定会员优惠：</label>
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
                      condition={'赠送'}
                      keyText={'积分'}
                      valueText={'优惠券对应商品号'}
                      value1Text={'数量'}
                      keyName={'myKey'}
                      valueName={'myValue'}
                      value1Name={'myValue1'}
                      maxRow={1}
                      initRow={this.state.initRow}
                      initValue={this.state.initValue}
                    />
                  : null}
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

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if(values(data.myKey).length!==values(data.myValue).length||values(data.myValue1).length!==values(data.myValue).length){
      this.setState({
        err: "信息填写不完整"
      })
      return;
    }

    data.myKey = values1(data.myKey)
    data.myValue = values1(data.myValue)
    this.setState({
      err: null
    })

    // return

    req.post('/uclee-backend-web/bindMemberHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (res.text) {
        window.location = '/bindMemberSetting'
      }
    })
  }
}

export default BindMemberSetting
