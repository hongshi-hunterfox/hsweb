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

class EditPhoneUser extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      userId: 0,
      name:"",
      phone:""
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getPhoneUser')
      .query(this.props.location.query)
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          userId:res.body.userId,
          name: res.body.name,
          phone: res.body.phone
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="add-user Page">
        <div className="add-user">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal" ref="f">
            <input type="hidden" name="userId" value={this.state.userId} />
            <div className="form-group">
              <label className="control-label col-md-3">名字：</label>
              <div className="col-md-9">
                <input type="text" name="name" onChange={this._onChange} value={this.state.name} className="form-control" />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">手机号码：</label>
              <div className="col-md-9">
                <input type="text" name="phone" onChange={this._onChange} value={this.state.phone} className="form-control" />
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

  _onChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
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
      .post('/uclee-backend-web/doUpdatePhoneUser')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }

        console.log(res.body)

        if(res.body.result==="success"){
          alert("修改成功！");
        }else{
          alert("修改失败！手机已存在");
          return;
        }

        browserHistory.push({
          pathname: '/phoneUserList'
        })
      })
  }
}

export default EditPhoneUser
