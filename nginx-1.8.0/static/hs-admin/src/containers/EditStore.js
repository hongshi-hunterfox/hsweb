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

// var EditAddrUtil = require('../utils/EditAddrUtil.js')

var geocoder, map = null
var qq = window.qq

class EditStore extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      hsStoreList: [],
      storeId: 0,
      storeName: '',
      supportDeliver: '',
      stateArr: [],
      cityArr: [],
      regionArr: [],
      province: '',
      city: '',
      region: '',
      addrDetail: '',
      latitude:23,
      longitude:119
    }
  }


  componentDidMount() {
    this._init()
    req.get('/uclee-backend-web/getHongShiStore').end((err, res) => {
      if (err) {
        return err
      }

      this.setState({
        hsStoreList: res.body.stores,
        stateArr: res.body.province
      })
    })
    req
      .get('/uclee-backend-web/getNapaStoreById')
      .query(this.props.location.query)
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          storeName: res.body.napaStore.storeName,
          supportDeliver: res.body.napaStore.supportDeliver,
          addrDetail: res.body.napaStore.addrDetail,
          storeId: res.body.napaStore.storeId,
          province: res.body.napaStore.province,
          region: res.body.napaStore.region,
          city: res.body.napaStore.city,
          code: res.body.napaStore.hsCode,
          userId:res.body.napaStore.userId,
          cityArr:res.body.city||[],
          regionArr:res.body.region||[]
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="编辑门店">
        <div className="add-store">
          <form onSubmit={this._submit} className="form-horizontal" ref="f">
            <div className="form-group">
              <label className="control-label col-md-3">商店名称：</label>
              <div className="col-md-9">
                <input
                  type="text"
                  name="storeName"
                  onChange={this._onStoreChange}
                  value={this.state.storeName}
                  className="form-control"
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">门店选择：</label>
              <div className="col-md-9">
                <select name="hsCode" className="form-control" value={this.state.code} onChange={this._onStoreChange1}>
                  {this.state.hsStoreList.map((item, index) => {
                    return (
                      <option
                        key={index}
                        value={`${item.code}`}
                      >
                        {item.fullName}
                      </option>
                    )
                  })}
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">是否支持配送：</label>
              <div className="col-md-9">
                <select name="supportDeliver" className="form-control" value={this.state.supportDeliver} onChange={this._onStoreChange}>
     							<option value="yes">是</option>
                	<option value="no">否</option>
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">商店地址：</label>
              <div className="col-md-3">
                <select
                  value={this.state.province}
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
                  value={this.state.city}
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
                <select className="form-control" name="region" value={this.state.region} onChange={this._changeRegion}>
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
                <input
                  type="text"
                  value={this.state.addrDetail}
                  name="addrDetail"
                  className="form-control"
                  onChange={this._changeAddr}
                />
              </div>
            </div>
            <div className="form-group">
              <div
                className="col-md-9 col-md-offset-3 map-container"
                id="container"
              />
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
        regionArr: data.region||[]
      })
    })
  }

  _changeRegion = e => {
    this.setState({
      region: e.target.value
    })
  }

  _changeAddr = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
    console.log(fto(this.refs.f))
    var t = fto(this.refs.f)

    var addr = t.province + t.city + t.region + (t.addrDetail || '')
    console.log(addr)
    geocoder.getLocation(addr)
  }

  _onStoreChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  _onStoreChange1 = e => {
    this.setState({
      code: e.target.value
    })
  }

  _init = () => {
    var center = new qq.maps.LatLng(23.12463, 113.36199)
    map = new qq.maps.Map(document.getElementById('container'), {
      center: center,
      zoom: 15
    })
    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
      complete: result => {
        console.log(result)
        this.setState({
          latitude : result.detail.location.lat,
          longitude : result.detail.location.lng
        })
        
        map.setCenter(result.detail.location)
        new qq.maps.Marker({
          map: map,
          position: result.detail.location
        })
      }
    })
  }

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)

    data.storeId= this.state.storeId
    data.longitude = this.state.longitude
    data.latitude = this.state.latitude

    if (!data.storeName) {
      return this.setState({
        err: '请填写 商户名称'
      })
    }
    
    if (!data.supportDeliver) {
      return this.setState({
        err: '请选择 是否支持配送'
      })
    }

    if (!data.addrDetail) {
      return this.setState({
        err: '请填写 详细地址'
      })
    }

    this.setState({
      err: null
    })
      console.log(data)

    req.post('/uclee-backend-web/doUpdateStore').send(data).end((err, res) => {
      if (err) {
        return err
      }
      console.log(res.body)
      alert("更新成功")
      window.location="napaStoreList?userId=" + this.state.userId;
    })
  }
}

export default EditStore
