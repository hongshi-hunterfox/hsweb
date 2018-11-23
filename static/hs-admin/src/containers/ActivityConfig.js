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

class ActivityConfig extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      config:{},
      err: null,
      uploading:false,
      logoUrl:'',
      ucenterImg:''
    }
    this.imgFile = null
    this.imgFile1 = null
  }
  componentDidMount() {
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config,
        logoUrl:data.config?data.config.logoUrl:'',
        ucenterImg:data.config?data.config.ucenterImg:''
      })
      console.log(this.state.config)
    })
  }

  render() {
    return (
      <DocumentTitle title="参数配置">
        <div className="global-config">
          {/* 类名加上页面前缀防止冲突 */}

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
             {/* <label className="control-label col-md-3" style={{marginTop:'10px'}}>注册积分赠送数量：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.registPoint} name="registPoint" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>签到积分赠送数量：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.signInPoint} name="signInPoint" className="form-control" onChange={this._change}/>
              </div>*/}
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>积分抽奖单次消耗数量：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.drawPoint} name="drawPoint" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>一级分销获利百分比：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.firstDis} name="firstDis" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>二级分销获利百分比：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.secondDis} name="secondDis" className="form-control" onChange={this._change}/>
              </div>
            {/*<label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖一等奖奖池数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.firstPrize} name="firstPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖二等奖奖池数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.secondPrize} name="secondPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖三等奖奖池数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.thirdPrize} name="thirdPrize" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖一等奖单次抽奖人数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.firstCount} name="firstCount" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖二等奖单次抽奖人数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.secondCount} name="secondCount" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动抽奖三等奖单次抽奖人数：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.thirdCount} name="thirdCount" className="form-control" onChange={this._change}/>
              </div>
             /*}
              {/* <label className="control-label col-md-3" style={{marginTop:'10px'}}>生日短信内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.config.birthText} name="birthText" className="form-control" onChange={this._change}>
                </textarea>
              </div> */}
             	<label className="control-label col-md-3" style={{marginTop:'10px'}}>是否启用支付宝：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="whetherEnableAlipay" value={this.state.config.whetherEnableAlipay} style={{padding:'5px'}} onChange={this._change}>
                  <option value="yes">启用</option>
                  <option value="no">停用</option>
                </select>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>通知消息内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
              {/*<input type="text" value={this.state.config.notice} name="notice" className="form-control" onChange={this._change}/>*/}
                <textarea rows="3" cols="20" value={this.state.config.notice} name="notice" className="form-control" onChange={this._change}>
                </textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>设置客服qq：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.qq} name="qq" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>设置品牌名称：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.brand} name="brand" maxLength="6" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>配送距离限制km：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.restrictedDistance} name="restrictedDistance" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>满额起送设置：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.full} name="full" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>是否启用绑定会员：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="startUp" value={this.state.config.startUp?this.state.config.startUp:'0'} style={{padding:'5px'}} onChange={this._change}>
                  <option value="0">默认</option>
                  <option value="1">只启用无线下会员</option>
                  <option value="2">只启用有线下会员</option>
                  <option value="3">停用会员绑定</option>
                </select>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>是否启用会员解绑：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="unbundling" value={this.state.config.unbundling?this.state.config.unbundling:'0'} style={{padding:'5px'}} onChange={this._change}>
                  <option value="0">启用</option>
                  <option value="1">停用</option>
                </select>
              </div>
							<label className="control-label col-md-3" style={{marginTop:'10px'}}>是否启用会员挂失：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="loss" value={this.state.config.loss?this.state.config.loss:'0'} style={{padding:'5px'}} onChange={this._change}>
                  <option value="0">启用</option>
                  <option value="1">停用</option>
                </select>
              </div>
              {/*<label className="control-label col-md-3" style={{marginTop:'10px'}}>是否启用绑定强制弹个人信息：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <select name="force" value={this.state.config.force?this.state.config.force:'1'} style={{padding:'5px'}} onChange={this._change}>    
                  <option value="1">启用</option>
                  <option value="2">不启用</option>                
                </select>
              </div>
             */}

              <label className="control-label col-md-3" style={{marginTop:'10px'}}>会员绑定页面内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.config.bindText} name="bindText" className="form-control" onChange={this._change}>
                </textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>评论赠送提示内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.config.commentText} name="commentText" className="form-control" onChange={this._change}>
                </textarea>
              </div>
              {/*
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>签到奖品规则页面内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.config.signText} name="signText" className="form-control" onChange={this._change}>
                </textarea>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>活动规则内容：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <textarea rows="3" cols="20" value={this.state.config.bargainText} name="bargainText" className="form-control" onChange={this._change}>
                </textarea>
              </div>*/}
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">logo图片：</label>
              <div className="col-md-9">
                <div className="row">
                  {
                    this.state.uploading ?
                    <div className="product-uploading">
                      <span>上传中...</span>
                    </div>
                    :
                    null
                  }
                  <div className="col-md-4" >
                    <div className="panel panel-default">
                      <div className="panel-body">
                        <div style={{ marginBottom: 10 }}>
                          <img
                            src={this.state.logoUrl}
                            alt=""
                            className="img-responsive"
                          />
                        </div>
                        {/*<button
                          type="button"
                          className="btn btn-danger btn-block"
                          onClick={this._deleteLogoImg}
                        >
                          更换图片
                        </button>*/}
                      </div>
                    </div>
                  </div>
                </div>
                <button
                  className="btn btn-default"
                  type="button"
                  onClick={() => {
                    this.imgFile.click()
                  }}
                >
                  <span className="glyphicon glyphicon-plus" />
                  更换图片
                  {this.state.logoUrl}
                </button>
                <input
                  type="file"
                  onChange={this._onChooseLogoImage}
                  className="hidden"
                  ref={c => {
                    this.imgFile = c
                  }}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">用户中心图片：</label>
              <div className="col-md-9">
                <div className="row">
                  {
                    this.state.uploading ?
                    <div className="product-uploading">
                      <span>上传中...</span>
                    </div>
                    :
                    null
                  }
                  <div className="col-md-4" >
                    <div className="panel panel-default">
                      <div className="panel-body">
                        <div style={{ marginBottom: 10 }}>
                          <img
                            src={this.state.ucenterImg}
                            alt=""
                            className="img-responsive"
                          />
                        </div>
                        {/*<button
                          type="button"
                          className="btn btn-danger btn-block"
                          onClick={this._deleteLogoImg}
                        >
                          更换图片
                        </button>*/}
                      </div>
                    </div>
                  </div>
                </div>
                <button
                  className="btn btn-default"
                  type="button"
                  onClick={() => {
                    this.imgFile1.click()
                  }}
                >
                  <span className="glyphicon glyphicon-plus" />
                  更换图片
                  {this.state.ucenterImg}
                </button>
                <input
                  type="file"
                  onChange={this._onChooseUcenterImage}
                  className="hidden"
                  ref={c => {
                    this.imgFile1 = c
                  }}
                />
              </div>
            </div>
            {/*<div className="form-group">
              <label className="control-label col-md-3">是否免运费：</label>
              <div className="col-md-9">
                <select
                  name="shippingFree"
                  className="form-control"
                  value={this.state.shippingFree}
                  onChange={this._simpleInputChange}
                >
                  <option value={false}>否</option>
                  <option value={true}>是</option>
                </select>
              </div>
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

  _change = (e) => {
    var c = Object.assign({}, this.state.config)
    c[e.target.name] = e.target.value
    this.setState({
      config: c
    })
  }
  _onChooseLogoImage = fe => {
    console.log("进入_onChooseLogoImage")
    if (fe.target.files && fe.target.files[0]) {
      var f = fe.target.files[0]

      this.setState({
        uploading: true
      })

      var fd = new FormData()
      fd.append('file', f)
      req
        .post('/uclee-product-web/doUploadImage')
        .send(fd)
        .end((err, res) => {
          if (err) {
            return err
          }
          console.log(this.state);
          this.setState({
              logoUrl: res.text,
              uploading:false
          })
          console.log(this.state);
        })
    }
  }
_onChooseUcenterImage = fe => {
  console.log("进入_onChooseUcenterImage")
    if (fe.target.files && fe.target.files[0]) {
      var f = fe.target.files[0]

      this.setState({
        uploading: true
      })

      var fd = new FormData()
      fd.append('file', f)
      req
        .post('/uclee-product-web/doUploadImage')
        .send(fd)
        .end((err, res) => {
          if (err) {
            return err
          }
          
          this.setState({
              ucenterImg: res.text,
              uploading:false
          })
        })
    }
  }
  _deleteLogoImg = index => {
    this.setState({
        logoUrl: ''
    })
  }
  _deleteUcenterImg = index => {
    this.setState({
        ucenterImg: ''
    })
  }
  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)


    /*if (!data.registPoint) {
      return this.setState({
        err: '请填写 注册积分赠送数量'
      })
    }*/
    
    
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
    if(!data.restrictedDistance) {
      return this.setState({
        err: '请填写配送距离限制km!'
      })
    }
    this.setState({
      err: null
    })
    data.logoUrl = this.state.logoUrl;
    data.ucenterImg = this.state.ucenterImg;
    req
      .post('/uclee-backend-web/activityConfigHandler')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='activity-config';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })
  }
}

export default ActivityConfig