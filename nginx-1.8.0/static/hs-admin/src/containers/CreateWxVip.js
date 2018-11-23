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

class CreateWxVip extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      config:{},
      err: null,
      uploading:false,
      cartLogo:'',
      cartBgUrl:''
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
        cartLogo:data.config?data.config.cartLogo:'',
        cartBgUrl:data.config?data.config.cartBgUrl:''
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
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>微信会员卡名称：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartBrandName} name="cartBrandName" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>微信会员卡标题：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartTitle} name="cartTitle" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>使用提醒文字(16字)：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartNotice} name="cartNotice" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>电话：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="number" value={this.state.config.cartServicePhone} name="cartServicePhone" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>使用须知：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartDescription} name="cartDescription" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>特权说明：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartPrerogative} name="cartPrerogative" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>消费记录url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartCustomUrl} name="cartCustomUrl" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>商城url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartPromotionUrl} name="cartPromotionUrl" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>出示付款码url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartCenterUrl} name="cartCenterUrl" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>积分url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartBonusUrl} name="cartBonusUrl" className="form-control" onChange={this._change}/>
              </div>
              <label className="control-label col-md-3" style={{marginTop:'10px'}}>余额url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.config.cartCustomField1Url} name="cartCustomField1Url" className="form-control" onChange={this._change}/>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">微信会员卡logo：</label>
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
                            src={this.state.cartLogo}
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
                  {this.state.cartLogo}
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
              <label className="control-label col-md-3">微信会员卡背景：</label>
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
                            src={this.state.cartBgUrl}
                            alt=""
                            className="img-responsive"
                          />
                        </div>
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
                  {this.state.cartBgUrl}
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

            <ErrorMsg msg={this.state.err} />
            <div className="form-group">
              <div className="col-md-9 col-md-offset-3">
                <button type="submit" className="btn btn-primary">创建会员卡</button>
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
              cartLogo: res.text,
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
              cartBgUrl: res.text,
              uploading:false
          })
        })
    }
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)


    if (!data.cartBrandName) {
      return this.setState({
        err: '请填写会员卡名称'
      })
    }
    
    if (!data.cartTitle) {
      return this.setState({
        err: '请填写会员卡标题'
      })
    }
    
    if (!data.cartNotice) {
      return this.setState({
        err: '请填写提醒文字'
      })
    }
    
    if (!data.cartServicePhone) {
      return this.setState({
        err: '请填写电话'
      })
    }
    
    if (!data.cartDescription) {
      return this.setState({
        err: '请填写使用须知'
      })
    }
    
    if (!data.cartCustomUrl) {
      return this.setState({
        err: '请填写消费记录url'
      })
    }
    
    if (!data.cartPromotionUrl) {
      return this.setState({
        err: '请填写商城url'
      })
    }
    
    if (!data.cartCenterUrl) {
      return this.setState({
        err: '请填写出示付款码url'
      })
    }
    
    if (!data.cartBonusUrl) {
      return this.setState({
        err: '请填写积分url'
      })
    }
    
    if (!data.cartCustomField1Url) {
      return this.setState({
        err: '请填写余额url'
      })
    }
    
    if (!data.cartPrerogative) {
      return this.setState({
        err: '请填写特权说明'
      })
    }
    
    this.setState({
      err: null
    })
    data.cartLogo = this.state.cartLogo;
    data.cartBgUrl = this.state.cartBgUrl;
    req
      .post('/uclee-backend-web/activityConfigHandler')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(!res.body){
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })
    req
      .get('/uclee-backend-web/CreatWxVip')
      .end((err, res) => {
        if (err) {
          return err
        }
        window.location='create-wx-vip';
      })
    
  }
}

export default CreateWxVip