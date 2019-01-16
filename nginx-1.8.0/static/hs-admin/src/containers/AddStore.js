import React from 'react'
import DocumentTitle from 'react-document-title'

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


var geocoder,map = null;
var qq=window.qq;


class AddStore extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      hsStoreList:[],
      stateArr: [],
      cityArr: [],
      regionArr: [],
      province: '',
      city: '',
      region: '',
      link:true,
      storeName:''
    }

    this.lat=23.12463
    this.lng=113.36199

  }

  componentDidMount() {
    this._init()
    req
      .get('/uclee-backend-web/getHongShiStore')
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          hsStoreList: res.body.stores,
          stateArr: res.body.province
        })
      })

        
  }
  _hsCodeChange = e =>{
    var q = {
      hsCode: e.target.value
    }
    req
    .get('/uclee-backend-web/getHongShiStoreName')
    .query(q)
    .end((err, res) => {
      if (err) {
        return err
      }

      this.setState({
        storeName: res.body.storeName
      })
    })
  }
  render() {
    return (
      <DocumentTitle title="门店管理">
        <div className="add-store">
          {/* 类名加上页面前缀防止冲突 */}
          <h1 className="add-store-title">这里是 add-store 页面</h1>

          <form onSubmit={this._submit} className="form-horizontal" ref="f">
            <input type="hidden" name="userId" value="0"/>
            <div className="form-group">
              <label className="control-label col-md-3">商店名称：</label>
              <div className="col-md-9">
                {
                  this.state.storeName?
                    <input type="text" name="storeName" value={this.state.storeName} className="form-control"/>
                    :<input type="text" name="storeName" className="form-control"/>
                }
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">门店选择：</label>
              <div className="col-md-9">
                <select name="hsCode" className="form-control" onChange={this._hsCodeChange}>
                <option value=''>请选择</option>
                  {
                    this.state.hsStoreList.map((item, index) => {
                      return (
                        <option key={index} value={`${item.code}`}>{item.fullName}</option>
                        )
                    })
                  }
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">是否支持配送：</label>
              <div className="col-md-9">
                <select name="supportDeliver" className="form-control" onChange={this._hsCodeChange}>
                <option value=''>请选择</option>
                <option value="yes">是</option>
                <option value="no">否</option>
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">商店地址：</label>
              <div className="col-md-3">
                <select
                  className="form-control"
                  name="province"
                  onChange={this._setCity} >
                  <option value="">请选择省份</option>
                  {
                    this.state.stateArr.map((item, index) => {
                      return (
                        <option value={item.province} key={index}>{item.province}</option>
                        )
                    })
                  }
                </select>
              </div>
              <div className="col-md-3">
                <select
                  className="form-control"
                  name="city"
                  onChange={this._setRegion}
                >
                  <option value="">请选择城市</option>
                  {
                    this.state.cityArr.map((item, index) => {
                      return (
                        <option value={item.city} key={index}>{item.city}</option>
                        )
                    })
                  }
                </select>
              </div>
              <div className="col-md-3">
                <select className="form-control" name="region" >
                  <option value="">请选择地区</option>
                  {
                    this.state.regionArr.map((item, index) => {
                      return (
                        <option value={item.region} key={index}>{item.region}</option>
                        )
                    })
                  }
                </select>
              </div>
              <label className="control-label col-md-3">详细地址：</label>
              <div className="col-md-9">
                <input type="text" name="addrDetail" className="form-control" onChange={this._changeAddr}/>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">是否关联所有上架商品：</label>
              <div className="col-md-9">
                <select
                  name="link"
                  className="form-control"
                >
                  <option value={true}>是</option>
                  <option value={false}>否</option>
                </select>
              </div>
            </div>
            <div className="form-group">
              <div className="col-md-9 col-md-offset-3 map-container" id="container">
                
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

  _changeAddr = () => {
    console.log(fto(this.refs.f))
    var t=fto(this.refs.f)

    var addr="中国," + t.province+"," + t.city+"," + t.region+"," + (t.addrDetail || '')
    console.log(addr)
    console.log(this.lat,this.lng)
    geocoder.getLocation(addr)
  }

_setCity = e => {
    var q = {
      stateId: e.target.value
    }
    this.setState({
      province: e.target.value
    })
    req.get('/uclee-user-web/getCitiesByStr').query(q).end((err, res) => {
      if (err) {
        return err
      }
      this.setState({
        cityArr: res.body.city
      })
    })
  }

  _setRegion = e => {
    var q = {
      cityId: e.target.value
    }
    this.setState({
      city: e.target.value
    })
    req.get('/uclee-user-web/getRegionsByStr').query(q).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        regionArr: data.region
      })
    })
  }

  _init = () => {
    var center = new qq.maps.LatLng(23.12463,113.36199);
    map = new qq.maps.Map(document.getElementById('container'),{
      center: center,
      zoom: 15
    });
    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
      complete : (result) => {
        console.log(result)
        this.lat=result.detail.location.lat
        this.lng=result.detail.location.lng
        map.setCenter(result.detail.location);
        new qq.maps.Marker({
            map:map,
            position: result.detail.location
        });
      }
    });
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)

    data.longitude=this.lng
    data.latitude=this.lat

    if (!data.storeName) {
      return this.setState({
        err: '请填写 商户名称'
      })
    }
    if (!data.addrDetail) {
      return this.setState({
        err: '请填写 详细地址'
      })
    }
    if (!data.province) {
      return this.setState({
        err: '请填写 省份'
      })
    }
    if (!data.city) {
      return this.setState({
        err: '请填写 城市'
      })
    }
    if (!data.region) {
      return this.setState({
        err: '请填写 地区'
      })
    }
    this.setState({
      err: null
    })
    console.log(data)

    req
      .post('/uclee-backend-web/doAddStore')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        
        console.log(res.body)
        if(res.body.result==="success"){
          window.location="napaStoreList?userId=" + this.props.location.query.userId;
        }else if(res.body.reason==="existed"){
          alert("所选加盟店已有记录");
          return;
        }else{
          alert("网络繁忙，请稍后重试");
          return;
        }
      })



  }
}

export default AddStore