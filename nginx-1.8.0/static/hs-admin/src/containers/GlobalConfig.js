import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './global-config.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class GlobalConfig extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      config:{},
      err: null
    }
  }
  componentDidMount() {
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config
      })
    })
  }

  render() {
    return (
      <DocumentTitle title="系统配置">
        <div className="global-config">
          {/* 类名加上页面前缀防止冲突 */}

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              <label className="control-label col-md-3">注册积分赠送数量：</label>
              <div className="col-md-9">
                <input type="number" value={this.state.config.registPoint} name="registPoint" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">签到积分赠送数量：</label>
              <div className="col-md-9">
                <input type="number" value={this.state.config.signInPoint} name="signInPoint" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">积分抽奖单次消耗数量：</label>
              <div className="col-md-9">
                <input type="number" value={this.state.config.drawPoint} name="drawPoint" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">一级分销获利比例(百分比)：</label>
              <div className="col-md-9">
                <input type="number" value={this.state.config.firstDis} name="firstDis" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">二级分销获利比例(百分比)：</label>
              <div className="col-md-9">
                <input type="number" value={this.state.config.secondDis} name="secondDis" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">微信APPID：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.appId} name="appId" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">微信APPSECRET：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.appSecret} name="appSecret" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">微信APPKEY：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.appKey} name="appKey" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">微信MERCHANTCODE：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.merchantCode} name="merchantCode" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">微信NOTIFYURL：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.notifyUrl} name="notifyUrl" className="form-control" onChange={this._change}/>
              </div>
               <label className="control-label col-md-3">支付宝商户号partnerid：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.partner} name="partner" className="form-control" onChange={this._change}/>
              </div>
               <label className="control-label col-md-3">支付宝NOTIFYURL：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.alipayNotifyUrl} name="alipayNotifyUrl" className="form-control" onChange={this._change}/>
              </div>
               <label className="control-label col-md-3">支付宝sellerId：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.sellerId} name="sellerId" className="form-control" onChange={this._change}/>
              </div>
               <label className="control-label col-md-3">支付宝密钥key：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.key} name="key" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖一等奖奖池数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.firstPrize} name="firstPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖二等奖奖池数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.secondPrize} name="secondPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖三等奖奖池数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.thirdPrize} name="thirdPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖一等奖单次抽奖人数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.firstCount} name="firstCount" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖二等奖单次抽奖人数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.secondCount} name="secondCount" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">活动抽奖三等奖单次抽奖人数：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.thirdCount} name="thirdCount" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">阿里大于appkey：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.aliAppkey} name="aliAppkey" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">阿里大于appSecret：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.aliAppSecret} name="aliAppSecret" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">阿里大于消息模板templateCode：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.templateCode} name="templateCode" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">生日短信模板id：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.birthTemId} name="birthTemId" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3">消费提醒短信id：</label>
              <div className="col-md-9">
                <input type="text" value={this.state.config.buyTemId} name="buyTemId" className="form-control" onChange={this._change}/>
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

  _change = (e) => {
    var c = Object.assign({}, this.state.config)
    c[e.target.name] = e.target.value
    this.setState({
      config: c
    })
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)


    if (!data.registPoint) {
      return this.setState({
        err: '请填写 注册积分赠送数量'
      })
    }
    if (!data.aliAppkey) {
      return this.setState({
        err: '请填写 阿里大于appkey：'
      })
    }
     if (!data.aliAppSecret) {
      return this.setState({
        err: '请填写 阿里大于appSecret'
      })
    }
     if (!data.templateCode) {
      return this.setState({
        err: '请填写 阿里大于消息模板templateCode'
      })
    }
    if (!data.signInPoint) {
      return this.setState({
        err: '请填写 签到积分赠送数量'
      })
    }
    if (!data.drawPoint) {
      return this.setState({
        err: '请填写 积分抽奖单次消耗数量 '
      })
    }
    if (!data.firstDis) {
      return this.setState({
        err: '请填写 一级分销获利比例 '
      })
    }
    if (!data.secondDis) {
      return this.setState({
        err: '请填写 二级分销获利比例'
      })
    }
    if (!data.appId) {
      return this.setState({
        err: '请填写appId'
      })
    }
    if (!data.appSecret) {
      return this.setState({
        err: '请填写appSecret'
      })
    }
    if (!data.appKey) {
      return this.setState({
        err: '请填写appKey'
      })
    }
    if (!data.merchantCode) {
      return this.setState({
        err: '请填写merchantCode'
      })
    }
    if (!data.notifyUrl) {
      return this.setState({
        err: '请填写notifyUrl'
      })
    }
    if (!data.firstPrize) {
      return this.setState({
        err: '请填写一等奖奖池数'
      })
    }
    if (!data.secondPrize) {
      return this.setState({
        err: '请填写二等奖奖池数'
      })
    }
    if (!data.thirdPrize) {
      return this.setState({
        err: '请填写三等奖奖池数'
      })
    }
    if (!data.firstCount) {
      return this.setState({
        err: '请填写一等奖单次抽奖数'
      })
    }
    if (!data.secondCount) {
      return this.setState({
        err: '请填写二等奖单次抽奖数'
      })
    }
    if (!data.thirdCount) {
      return this.setState({
        err: '请填写三等奖单次抽奖数'
      })
    }
    if (Number(data.thirdCount)>Number(data.thirdPrize)||Number(data.secondCount)>Number(data.secondPrize)||Number(data.firstCount>data.firstPrize)) {
      return this.setState({
        err: '抽奖数不能大于奖池数'
      })
    }
    this.setState({
      err: null
    })

    req
      .post('/uclee-backend-web/configHandler')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='global-config';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })



  }
}

export default GlobalConfig