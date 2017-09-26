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
      html: ''
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
        initValue: data.data
      })
      console.log(this.state.initValue);
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
                      condition={'大于>km'}
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
