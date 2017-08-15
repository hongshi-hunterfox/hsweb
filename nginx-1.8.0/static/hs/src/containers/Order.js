/* global wx */
import React from 'react'
import DocumentTitle from 'react-document-title'
import './order.css'
import req from 'superagent'
var geocoder = null
var qq = window.qq
import validator from 'validator'
var fto = require('form_to_object')
var errMap = {
  data_error: '非法数据',
  addr_error: '请选择收货地址',
  picktime_error: '请选择取货时间',
  phone_error: '手机格式错误',
  phone_empty: '自提请输入联系手机',
  isSelfPick_error: '请选择是否自提'
}
import ErrorMessage from './ErrorMessage'
class Order extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      error: '',
      defaultAddr: {},
      total: 0,
      storeAddr: {},
      cartItems: [],
      isSelfPick: '',
      cartIds: [],
      voucherCode: '',
      voucherText: 0,
      isDataError: false,
      shippingFee: 0,
      phone: '',
      pDate: '',
      pTime: '',
      remark: ''
    }
    this.lat = 23
    this.lng = 113
  }

  componentDidMount() {
    if (sessionStorage.getItem('p_date')) {
      this.setState({
        pDate: sessionStorage.getItem('p_date')
      })
    }
    if (sessionStorage.getItem('p_time')) {
      this.setState({
        pTime: sessionStorage.getItem('p_time')
      })
    }
    if (sessionStorage.getItem('remark')) {
      this.setState({
        remark: sessionStorage.getItem('remark')
      })
    }
    if (sessionStorage.getItem('phone')) {
      this.setState({
        phone: sessionStorage.getItem('phone')
      })
    }
    if (sessionStorage.getItem('isSelfPick')) {
      this.setState({
        isSelfPick: sessionStorage.getItem('isSelfPick') || ''
      })
    }
    this._init()
    var data = []
    var cartItemIds = JSON.parse(sessionStorage.getItem('cart_item_ids'))
    var storeId = localStorage.getItem('storeId')
    if (storeId) {
      req
        .get('/uclee-user-web/getStoreAddr?storeId=' + storeId)
        .end((err, res) => {
          if (err) {
            return err
          }
          this.setState({
            storeAddr: JSON.parse(res.text)
          })
        })
    }
    if (!cartItemIds) {
      this.setState({
        isDataError: true
      })
      return
    }

    for (var i = 0; i < cartItemIds.length; i++) {
      var tmp = {}
      tmp.cartId = cartItemIds[i]
      data.push(tmp)
    }
    this.setState({
      cartIds: JSON.parse(sessionStorage.getItem('cart_item_ids'))
    })
    req.post('/uclee-user-web/order').send(data).end((err, res) => {
      if (err) {
        return err
      }
      var resJson = JSON.parse(res.text)
      this.setState({
        defaultAddr: resJson.defaultAddr,
        cartItems: resJson.cartItems,
        total: resJson.total
      })
      if (sessionStorage.getItem('addr') != null) {
        this.setState({
          defaultAddr: JSON.parse(sessionStorage.getItem('addr'))
        })
      }
      if (
        this.state.isSelfPick &&
        this.state.isSelfPick === 'false' &&
        localStorage.getItem('latitude') != null &&
        localStorage.getItem('longitude') != null
      ) {
        if (this.state.defaultAddr) {
          var addr =
            this.state.defaultAddr.province +
            this.state.defaultAddr.city +
            this.state.defaultAddr.region +
            this.state.defaultAddr.addrDetail
          geocoder.getLocation(addr)
        }
      }
      if (sessionStorage.getItem('voucher') != null) {
        this.setState({
          voucherCode: JSON.parse(sessionStorage.getItem('voucher'))
        })
      }
      if (sessionStorage.getItem('voucher_text') != null) {
        this.setState({
          voucherText: JSON.parse(sessionStorage.getItem('voucher_text'))
        })
      }
      if (!this.state.cartItems || this.state.cartItems.length <= 0) {
        this.setState({
          isDataError: true
        })
        return
      }
    })
  }

  render() {
    if (this.state.isDataError) {
      if (sessionStorage.getItem('isFromCart') === 1) {
        alert('非法数据，请返回购物车')
        window.location = '/cart'
      } else {
        alert('订单已提交，请勿重新提交，请到未支付订单继续完成支付')
        window.location = '/unpay-order-list'
      }
    }
    return (
      <DocumentTitle title="提交订单">
        <div className="order">
          <form className="" onSubmit={this._handleSubmit}>
             <div className="order-message">
              <div className="input-group">
                <span className="input-group-addon">自提/配送：</span>
                {/*<select
                  name="isSelfPick"
                  value={this.state.isSelfPick}
                  
                >
                  <option value="">请选择</option>
                  <option value="false">配送</option>
                  <option value="true">自提</option>
                </select>*/}
                <span  className="radio-input"><input type='radio' id='no' name="isSelfPick" checked={this.state.isSelfPick==="false"?'checked':null} value="false" onClick={e => {
                    this.setState({
                      isSelfPick: e.target.value,
                      shippingFee: 0
                    }, () => {
                       if (
                          this.state.isSelfPick &&
                          this.state.isSelfPick === 'false' &&
                          localStorage.getItem('latitude') != null &&
                          localStorage.getItem('longitude') != null
                        ) {
                          if (this.state.defaultAddr) {
                            var addr =
                              this.state.defaultAddr.province +
                              this.state.defaultAddr.city +
                              this.state.defaultAddr.region +
                              this.state.defaultAddr.addrDetail
                            console.log('addr:' + addr)
                            geocoder.getLocation(addr)
                          }
                        }
                    })
                    sessionStorage.setItem('isSelfPick', e.target.value)
                    console.log(this.state.isSelfPick);
                   
                  }}/> <label htmlFor="no">配送</label></span>
                <input type='radio' name="isSelfPick" id="yes" value="true" checked={this.state.isSelfPick==="true"?'checked':null} onClick={e => {
                    this.setState({
                      isSelfPick: e.target.value,
                      shippingFee: 0
                    }, () => {
                       if (
                          this.state.isSelfPick &&
                          this.state.isSelfPick === 'false' &&
                          localStorage.getItem('latitude') != null &&
                          localStorage.getItem('longitude') != null
                        ) {
                          if (this.state.defaultAddr) {
                            var addr =
                              this.state.defaultAddr.province +
                              this.state.defaultAddr.city +
                              this.state.defaultAddr.region +
                              this.state.defaultAddr.addrDetail
                            console.log('addr:' + addr)
                            geocoder.getLocation(addr)
                          }
                        }
                    })
                    sessionStorage.setItem('isSelfPick', e.target.value)

                   
                  }}/> <label htmlFor="yes">自提</label>
              </div>
            </div>
            <Addr
              isSelfPick={this.state.isSelfPick}
              _addrTabChange={this._addrTabChange}
              defaultAddr={this.state.defaultAddr}
              storeAddr={this.state.storeAddr}
            />
            <div className="order-store">
              <span className="" /> {localStorage.getItem('storeName')}
            </div>
            <OrderItem cartItems={this.state.cartItems} />
            <input
              type="hidden"
              name="addrId"
              value={
                this.state.defaultAddr
                  ? this.state.defaultAddr.deliveraddrId
                  : null
              }
            />
            <input type="hidden" name="cartIds" value={this.state.cartIds} />
            <input
              type="hidden"
              name="storeId"
              value={localStorage.getItem('storeId')}
            />
            <input
              type="hidden"
              name="addrId"
              value={
                this.state.defaultAddr
                  ? this.state.defaultAddr.deliveraddrId
                  : null
              }
            />
            <div className="order-message">
              <div className="input-group">
                <span className="input-group-addon">买家留言：</span>
                <textarea
                  rows="2"
                  name="remark"
                  className="form-control"
                  value={this.state.remark}
                  onChange={this._remarkHandler}
                />
              </div>
            </div>

           
            {this.state.isSelfPick && this.state.isSelfPick === 'true'
              ? <div className="order-message">
                  <div className="input-group">
                    <span className="input-group-addon">联系手机：</span>
                    <input
                      className="form-control"
                      type="text"
                      value={this.state.phone}
                      name="phone"
                      onChange={this._phoneHandler}
                    />
                  </div>
                </div>
              : null}
            <div className="order-message">
              <div className="input-group">
                <span className="input-group-addon">取货日期：</span>
                <input
                  className="form-control"
                  type="date"
                  name="pickDateStr"
                  value={this.state.pDate}
                  onChange={e => {
                    this.setState({ pDate: e.target.value })
                    sessionStorage.setItem('p_date', e.target.value)
                  }}
                />
              </div>
            </div>
            <div className="order-message">
              <div className="input-group">
                <span className="input-group-addon">取货时间：</span>
                <input
                  className="form-control"
                  type="time"
                  name="pickTimeStr"
                  value={this.state.pTime}
                  onChange={e => {
                    this.setState({ pTime: e.target.value })
                    sessionStorage.setItem('p_time', e.target.value)
                  }}
                />
              </div>
            </div>
            <div className="order-total">
              商品小计：<span className="money">￥{this.state.total}</span>
            </div>
            <div className="order-total">
              <input
                type="hidden"
                name="shippingFee"
                value={this.state.shippingFee}
              />
              运费：<span className="money">￥{this.state.shippingFee}</span>
            </div>
            <div
              className="order-coupon"
              onClick={() => {
                window.location = 'coupon?isFromOrder=1'
              }}
            >
              优惠：
              {this.state.voucherText && this.state.voucherText !== ''
                ? this.state.voucherText + ' 现金优惠券'
                : null}
              <span className="icon fa fa-chevron-right" />
            </div>
            <input
              type="hidden"
              name="voucherCode"
              value={this.state.voucherCode}
            />
            <div className="order-submit">
              合计：￥
              {this.state.total -
                this.state.voucherText +
                this.state.shippingFee >
                0
                ? this.state.total -
                    this.state.voucherText +
                    this.state.shippingFee
                : 0}
              <button type="submit" className="button">提交订单</button>
            </div>
            <ErrorMessage error={this.state.error} />
          </form>
        </div>
      </DocumentTitle>
    )
  }

  _remarkHandler = e => {
    this.setState({
      remark: e.target.value
    })
    sessionStorage.setItem('remark', e.target.value)
  }
  _phoneHandler = e => {
    this.setState({
      phone: e.target.value
    })
    sessionStorage.setItem('phone', e.target.value)
  }

  /*_addrTabChange = a => {
    sessionStorage.setItem('isSelfPick', a)
    this.setState({
      isSelfPick: a,
      shippingFee: 0
    })

    if (
      !a &&
      localStorage.getItem('latitude') != null &&
      localStorage.getItem('longitude') != null
    ) {
      if (this.state.defaultAddr) {
        var addr =
          this.state.defaultAddr.province +
          this.state.defaultAddr.city +
          this.state.defaultAddr.region +
          this.state.defaultAddr.addrDetail
        console.log('addr:' + addr)
        geocoder.getLocation(addr)
      }
    }
  }*/
  _init = () => {
    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
      complete: result => {
        console.log('result:' + result)
        this.lat = result.detail.location.lat
        this.lng = result.detail.location.lng
        console.log('latlng:' + this.lat + ' ' + this.lng)
        console.log(
          Number(localStorage.getItem('latitude')) +
            '  ff  ' +
            Number(localStorage.getItem('longitude'))
        )

        var distance = Math.round(
          qq.maps.geometry.spherical.computeDistanceBetween(
            new qq.maps.LatLng(this.lat, this.lng),
            new qq.maps.LatLng(
              Number(localStorage.getItem('latitude')),
              Number(localStorage.getItem('longitude'))
            )
          ) / 100
          )/10
        req
          .get('/uclee-user-web/getShippingFee?distance=' + distance)
          .end((err, res) => {
            if (err) {
              return err
            }
            this.setState({
              shippingFee: JSON.parse(res.text).money
            })
          })
      }
    })
  }

  _handleSubmit = e => {
    if (!this.state.cartItems || this.state.cartItems.length <= 0) {
      this.setState({
        isDataError: true
      })
      return
    }
    e.preventDefault()
    var data = fto(e.target)
    if (!data.isSelfPick) {
      this.setState({
        error: errMap['isSelfPick_error']
      })
      return false
    }
    if (data.isSelfPick === 'false' && !data.addrId) {
      this.setState({
        error: errMap['addr_error']
      })
      return false
    }
    if (data.isSelfPick === 'true') {
      if (!data.phone) {
        this.setState({
          error: errMap['phone_empty']
        })
        return false
      }
      if (!validator.isMobilePhone(data.phone, 'zh-CN')) {
        this.setState({
          error: errMap['phone_error']
        })
        return false
      }
    }

    if (!data.cartIds) {
      this.setState({
        error: errMap['data_error']
      })
      return false
    }
    if (!data.pickDateStr || !data.pickTimeStr) {
      this.setState({
        error: errMap['picktime_error']
      })
      return false
    }
    req.post('/uclee-user-web/orderHandler').send(data).end((err, res) => {
      if (err) {
        return err
      }
      var resJson = JSON.parse(res.text)
      if (!resJson.result) {
        this.setState({
          error: resJson.reason
        })
      } else {
        sessionStorage.removeItem('cart_item_ids')
        sessionStorage.removeItem('addr')
        sessionStorage.removeItem('voucher')
        sessionStorage.removeItem('voucher_text')
        sessionStorage.removeItem('remark')
        sessionStorage.removeItem('isSelfPick')
        sessionStorage.removeItem('isFromCart')
        sessionStorage.removeItem('p_date')
        sessionStorage.removeItem('p_time')
        sessionStorage.removeItem('isFromCart')
        sessionStorage.removeItem('phone')
        window.location =
          'seller/payment?paymentSerialNum=' + resJson.paymentSerialNum
      }
    })
    return false
  }
}

class Addr extends React.Component {
  render() {
    return (
      <div className="order-addr">
        {/*<div className="tab">
          <div
            className={'deli ' + (this.props.isSelfPick!=null&&this.props.isSelfPick ? '' : 'select')}
            onClick={this.props._addrTabChange.bind(this, false)}
          >
            配送
          </div>
          <div
            className={'self ' + (this.props.isSelfPick!=null&&this.props.isSelfPick ? 'select' : '')}
            onClick={this.props._addrTabChange.bind(this, true)}
          >
            自提
          </div>
        </div>*/}
        <div className="detail">
          {!this.props.isSelfPick || this.props.isSelfPick === 'false'
            ? <div
                className="deli"
                onClick={() => {
                  window.location = 'address?isFromOrder=1'
                }}
              >
                <div className="left">
                  <span className="icon fa fa-map-marker" />
                  <span className="name">
                    收货人：
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.name
                      : null}
                  </span>
                  <span className="phone">
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.phone
                      : null}
                  </span>
                  <div className="addrDetail">
                    收货地址：
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.province
                      : null}
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.city
                      : null}
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.region
                      : null}
                    {this.props.defaultAddr
                      ? this.props.defaultAddr.addrDetail
                      : null}
                  </div>
                </div>
                <div className="right fa fa-chevron-right right" />
              </div>
            : <div className="self">
                <div className="left">
                  <span className="icon fa fa-map-marker" />
                  <span className="addrDetail">
                    取货地址：
                    {this.props.storeAddr
                      ? this.props.storeAddr.province
                      : null}
                    {this.props.storeAddr ? this.props.storeAddr.city : null}
                    {this.props.storeAddr ? this.props.storeAddr.region : null}
                    {this.props.storeAddr
                      ? this.props.storeAddr.addrDetail
                      : null}
                  </span>
                </div>
              </div>}
        </div>
      </div>
    )
  }
}

class OrderItem extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      cartItems: []
    }
  }
  render() {
    var items = this.props.cartItems.map((item, index) => {
      return (
        <div className="order-item-info" key={index}>
          <img className="image" src={item.image} alt="" />
          <div className="title">{item.title}</div>
          <div className="sku-info">
            <span className="sku">{item.specification}</span>
            <span className="count pull-right">单价：￥{item.money}</span>
          </div>
          <div className="other">
            <span className="price pull-right">数量：x {item.amount}</span>
          </div>
        </div>
      )
    })
    return (
      <div className="order-item">
        {items}
      </div>
    )
  }
}

export default Order
