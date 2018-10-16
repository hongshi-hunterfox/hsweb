import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-setting.css'


import ErrorMsg from '../components/ErrorMsg'
import Noti from '../components/Noti'

import fto from 'form_to_object'
import validator from 'validator'
import req from 'superagent'


class PhoneLogin extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      error: '',
      showNoti: false,

      cName: '',
      cMobileNumber: '',
      code: '',
      cBirthday: '',
      bIsLunar: '',
      cVipCode: '',

      type: '1',
      resultmsg: '保存成功',

      time: 60,
      fetchingCode: false
    }

    this.tick = null
  }

  componentDidMount() {
   
  }

  componentWillUnmount() {
    clearInterval(this.tick)
  }

  render() {
    return (
      <DocumentTitle title="手机登陆">
        <div className="member-setting container">
          <Noti visible={this.state.showNoti} text={this.state.resultmsg} />
          
          <form
            className="form-horizontal member-setting-form"
            onSubmit={this._submit}
          >
            <div className="form-group">
              <label className="control-label col-xs-3 trim-right">
                手机号码
              </label>
              <div className="col-xs-9">
                <input
                  type="tel"
                  name="cMobileNumber"
                  className="form-control"
                  placeholder="请输入手机号码"
                  value={this.state.cMobileNumber}
                  onChange={this._change}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-xs-3 trim-right">
                验证码
              </label>
              <div className="col-xs-5">
                <input
                  type="tel"
                  name="code"
                  className="form-control"
                  placeholder="请输入验证码"
                  value={this.state.code}
                  onChange={this._change}
                />
              </div>
              <div className="col-xs-4">
                <button
                  type="button"
                  className="btn btn-default member-setting-code-btn"
                  onClick={this._getCode}
                  disabled={this.state.fetchingCode}
                >
                  {this.state.fetchingCode
                    ? `(${this.state.time})`
                    : '获取验证码'}
                </button>
              </div>
            </div>

            <ErrorMsg msg={this.state.error} />

            <div className="form-btn-wrap">
              <button type="submit" className="btn btn-success btn-block">
                登陆
              </button>
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

  _getCode = () => {
    if (!validator.isMobilePhone(this.state.cMobileNumber, 'zh-CN')) {
      return this.setState({
        error: '请输入正确的手机号码'
      })
    }
    req
      .get('/uclee-user-web/checkNapaStorePhone')
      .query({
        phone: this.state.cMobileNumber
      })
      .end((err, res) => {
        if (err) {
          return err
        }
        if(!res.body){
          return this.setState({
            error: '该手机号未分配到加盟商'
          })
        }else{
          this.setState({
            fetchingCode: true
          })

          req
            .get('/uclee-user-web/bossVerifyCode')
            .query({
              phone: this.state.cMobileNumber
            })
            .end((err, res) => {
              if (err) {
                return err
              }
              this._tick()
            })
        }
      })
    
  }

  _tick = () => {
    this.tick = setInterval(() => {
      if (this.state.time <= 0) {
        this.setState({
          fetchingCode: false,
          time: 60
        })
        clearInterval(this.tick)
        return
      }

      this.setState(prevState => {
        return {
          time: prevState.time - 1
        }
      })
    }, 1000)
  }

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if (!data.cMobileNumber) {
      return this.setState({
        error: '请输入手机号码'
      })
    }

    if (!data.code) {
      return this.setState({
        error: '请输入验证码'
      })
    }

    if (!/^\d{6}$/.test(data.code)) {
      return this.setState({
        error: '验证码格式不正确'
      })
    }

    if (!validator.isMobilePhone(data.cMobileNumber, 'zh-CN')) {
      return this.setState({
        error: '请输入正确的手机号码'
      })
    }
    req.post('/uclee-user-web/checkVerifyCode').send(data).end((err, res) => {
      if (err) {
        return err
      }

      if (!res.body) {
        this.setState({
          error: '验证码不正确'
        })
        return
      }else{
        localStorage.setItem('napaStorePhone', data.cMobileNumber);
        window.location='boss-center'
      }

      this.setState({
        showNoti: true
      })

    })
  }
}

export default PhoneLogin
