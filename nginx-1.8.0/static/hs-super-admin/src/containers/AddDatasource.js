import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './edit-datasource.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class AddDatasource extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      id:0,
      merchantName:'',
      merchantCode:'',
      driverClassName:'',
      url:'',
      username:'',
      password:'',
      err: null
    }
  }


  render() {
    return (
      <DocumentTitle title="系统配置">
        <div className="global-config">
          {/* 类名加上页面前缀防止冲突 */}

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              <label className="control-label col-md-3">公司名称：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.merchantName} name="merchantName" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">公司代码：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.merchantCode} name="merchantCode" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">driver名字：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.driverClassName} name="driverClassName" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">url：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.url} name="url" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">用户名：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.username} name="username" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">密码：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.password} name="password" className="form-control" onChange={this._change}/>
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

  _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }


  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)


    if (!data.merchantName) {
      return this.setState({
        err: '请填写公司名称'
      })
    }
    if (!data.merchantCode) {
      return this.setState({
        err: '请填写公司代码'
      })
    }
    if (!data.driverClassName) {
      return this.setState({
        err: '请填写driver名字'
      })
    }
    if (!data.url) {
      return this.setState({
        err: '请填写url'
      })
    }
    if (!data.username) {
      return this.setState({
        err: '请填写用户名'
      })
    }
    if (!data.password) {
      return this.setState({
        err: '请填写密码'
      })
    }
    this.setState({
      err: null
    })

    req
      .post('/uclee-backend-web/doAddDataSourceInfo?merchantCode=master')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        alert('添加成功')
        console.log(res.body)
      })



  }
}

export default AddDatasource