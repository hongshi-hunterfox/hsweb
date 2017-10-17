import React from 'react'
import DocumentTitle from 'react-document-title'
import { browserHistory } from 'react-router'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './add-store.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class AddUser extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null
    }
  }

  componentDidMount() {
    this._init()
  }

  render() {
    return (
      <DocumentTitle title="add-user Page">
        <div className="add-user">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal" ref="f">
            <input type="hidden" name="userId" value="0" />
            <div className="form-group">
              <label className="control-label col-md-3">名字：</label>
              <div className="col-md-9">
                <input type="text" name="name" className="form-control" />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">手机号码：</label>
              <div className="col-md-9">
                <input type="text" name="phone" className="form-control" />
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

    if (!data.name) {
      return this.setState({
        err: '请填写 供应商名称'
      })
    }

    if (!data.phone) {
      return this.setState({
        err: '请填写 手机号码'
      })
    }

    this.setState({
      err: null
    })
    req
      .post('/uclee-backend-web/doAddPhoneUser')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }

        if(res.body.result==="success"){
          alert("添加成功！");
          browserHistory.push({
            pathname: '/phoneUserList'
          })
        }else{
          alert(res.body.reason);
        }

        
      })
  }
}

export default AddUser
