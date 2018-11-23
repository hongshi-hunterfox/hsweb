import React from 'react'
import DocumentTitle from 'react-document-title'
import { values } from 'lodash'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './demo.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

import ValueGroup from '../components/ValueGroup'

class Demo extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null
    }
  }

  render() {
    return (
      <DocumentTitle title="Demo Page">
        <div className="demo">
          {/* 类名加上页面前缀防止冲突 */}
          <h1 className="demo-title">这里是 Demo 页面</h1>

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              <label className="control-label col-md-3">XXX：</label>
              <div className="col-md-9">
                <input type="text" name="ip" className="form-control"/>
              </div>
            </div>

            <div className="form-group">
              <label className="control-label col-md-3">XXX：</label>
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
                <ValueGroup
                  condition={'大于'}
                  keyText={'数量'}
                  valueText={'价格'}
                  selectText={'还有'}
                  keyName={'myKey'}
                  valueName={'myValue'}
                  useSelect={true}
                  selectName={'mySelect'}
                  selectDefaultValue={3}
                  selectOptions={[
                    {
                      value: 1,
                      text: 'option1'
                    },
                    {
                      value: 2,
                      text: 'option2'
                    },
                    {
                      value: 3,
                      text: 'option3'
                    }
                  ]}
                  maxRow={3}
                  initValue={
                    {
                      'myKey[0]': 1,
                      'myValue[0]': 1,
                      'mySelect[0]': 1,
                      'myKey[1]': 1,
                      'myValue[1]': 1,
                      'mySelect[1]': 1
                    }
                  }
                  />
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

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    data.myKey = values(data.myKey)
    data.myValue = values(data.myValue)
    console.log(data)


    if (!data.ip) {
      return this.setState({
        err: '请填写 IP 地址'
      })
    }

    if (!validator.isIP(data.ip) ) {
      return this.setState({
        err: 'IP 地址格式错误'
      })
    }

    this.setState({
      err: null
    })

    req
      .post('/path/to/api')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }

        console.log(res.body)
      })



  }
}

export default Demo