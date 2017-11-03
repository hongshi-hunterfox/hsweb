import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import values from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './recharge-config.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

import ValueGroup from '../components/ValueGroupTmp'

class RechargeConfig extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      count: 1,
      initValue: null,
      initRow: 1,
      html: ''
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/rechargeConfig').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        initRow: data.size,
        initValue: data.data
      })
    })
  }

  render() {
    return (
      <DocumentTitle title="运费设置">
        <div className="recharge-config">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group ">
              <label className="control-label col-md-3">设置充值赠送：</label>
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
                      condition={'='}
                      keyText={'充值金额'}
                      valueText={'赠送金额/优惠券商品号'}
                      selectText={'赠送类型'}
                      useSelect={true}
                      selectOptions={[
                        {
                          value: "1",
                          text: '卡余额'
                        },
                        {
                          value: "2",
                          text: '优惠券'
                        },
                      ]}
                      keyName={'myKey'}
                      valueName={'myValue'}
                      selectName={'mySelect'}
                      selectDefaultValue={''}
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
    console.log(data)
    data.myKey = values(data.myKey)
    data.myValue = values(data.myValue)
    console.log(data)

    this.setState({
      err: null
    })

    req.post('/uclee-backend-web/rechargeConfigHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (res.text) {
        window.location = '/recharge-config'
      }
    })
  }
}

export default RechargeConfig
