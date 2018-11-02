/* global wx */
import React from 'react'
import DocumentTitle from 'react-document-title'
import './order.css'
import req from 'superagent'
var Link = require('react-router').Link
var geocoder = null
var qq = window.qq
import Icon from '../components/Icon'
import validator from 'validator'
import "./order-list.css"
import Big from 'big.js'
var myDate = new Date()
var Date1 = new Date(myDate).getTime()
var fto = require('form_to_object')
var errMap = {
  data_error: '非法数据',
  addr_error: '请选择收货地址',
  picktime_error: '请选择取货时间',
  phone_error: '手机格式错误',
  phone_empty: '自提请输入联系手机',
  isSelfPick_error: '请选择是否自提',
  Distribution_error: '不在配送范围内',
  closeDate_error:'我们歇业了',
  businesstime_error:'我们打烊睡觉了',
  times_error:'取货时间不能小于',
  peisong_error:'此门店不支持配送',
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
      fullamount: 0,
      convertibleGoods: '',
      remarks: '',
      isDataError: false,
      shippingFee: 0,
      phone: '',
      pDate: '',
      pTime: '',
      remark: '',
      isShippingfree:false,
      supportDeliver:true,
      salesInfoShow:false,
      salesInfo:[],
      cut:0,
      distance:0,
      config:{},
      closeStartDateStr:'',
      closeEndDateStr:'',
      businessStartTime:'',
      businessEndTime:'',
      hsgooscode: [],
      appointedTime:'',
      
      riqi:''
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
        appointedTime: resJson.appointedTime,
        riqi: resJson.riqi,
        total: resJson.total,
        isShippingfree:resJson.isShippingFree,
        supportDeliver:resJson.supportDeliver,
        isSelfPick:resJson.isSelfPick?resJson.isSelfPick:sessionStorage.getItem('isSelfPick') || '',
        salesInfo:resJson.salesInfo,
        cut:resJson.cut,
        hsgooscode:resJson.hsgooscode
      })
      sessionStorage.setItem('total', resJson.total);
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
          if(!this.state.isShippingfree){
            geocoder.getLocation(addr,this.state.total)
          }
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
      if (sessionStorage.getItem('fullamount') != null) {
        this.setState({
          fullamount: JSON.parse(sessionStorage.getItem('fullamount'))
        })
      }
      if (sessionStorage.getItem('convertibleGoods') != null) {
        this.setState({
          convertibleGoods: JSON.parse(sessionStorage.getItem('convertibleGoods'))
        })
      }
      if (sessionStorage.getItem('remarks') != null) {
        this.setState({
          remarks: JSON.parse(sessionStorage.getItem('remarks'))
        })
      }
      if (!this.state.cartItems || this.state.cartItems.length <= 0) {
        this.setState({
          isDataError: true
        })
        return
      }
      if (sessionStorage.getItem('isSelfPick')) {
        this.setState({
          isSelfPick: sessionStorage.getItem('isSelfPick') || ''
        })
      }
    })
    console.log('isSelfPick: ' + this.state.isSelfPick);
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config
      })
      console.log(this.state.config)
    })
    req
    	.get('uclee-backend-web/orderSettingPick')
    	.end((err, res) => {
      	if (err) {
        	return err
      	}
      	var data = JSON.parse(res.text)
      	console.log(data);
      	this.setState({
        	closeStartDateStr:data.data.closeStartDateStr,
        	closeEndDateStr:data.data.closeEndDateStr,
        	businessStartTime:data.data.businessStartTime,
        	businessEndTime:data.data.businessEndTime
      	})
    	})
  }

  render() {
    if (this.state.isDataError) {
      if (sessionStorage.getItem('isFromCart') === 1) {
        alert('非法数据，请返回购物车')
        window.location = '/cart'
      } else {
        alert('订单已提交，请勿重复提交，请到未支付订单继续完成支付')
        window.location = '/unpay-order-list'
      }
    }
    var Total = (this.state.total -
                this.state.voucherText +
                (this.state.isShippingfree ? 0 : this.state.shippingFee) -
                this.state.cut >
                0
                ? (this.state.total -
                    this.state.voucherText +
                  (this.state.isShippingfree ? 0 : this.state.shippingFee)-
                    this.state.cut).toFixed(2)
                : 0)
   var Difference = this.state.config.full - this.state.total
    return (
      <DocumentTitle title="提交订单">
      	<div>
	        <div className="order">
	          <form className="" onSubmit={this._handleSubmit}>
	             <div className="order-message">
	              <div className="input-group">
	                <span className="input-group-addon">自提/配送：</span>
	                <span  className="radio-input" onClick={() => {
	                	if(this.state.storeAddr.supportDeliver==='no'){
	                		alert("门店不支持配送,换个门店试试吧")
	                		window.location="/switch-shop/0"
	                	}}}
	                	>
	                	<input type='radio' id='no' name="isSelfPick" checked={this.state.isSelfPick==="false"?'checked':null} value="false" onClick={e => {
	                    	this.setState({
	                      	isSelfPick: e.target.value,
	                      	shippingFee: 0
	                    	}, () => {
	                      console.log(this.state)
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
	                            if(!this.state.isShippingfree){
	                              geocoder.getLocation(addr,this.state.total)
	                            }else{
	                            	geocoder.getLocation(addr,this.state.total)
	                            }
	                          }
	                        }
	                    })
	                    sessionStorage.setItem('isSelfPick', e.target.value)
	                    console.log(this.state.isSelfPick);
	                   
	                  }}/><label htmlFor="no">配送</label></span>
	                
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
	                            if(!this.state.isShippingfree){
	                             geocoder.getLocation(addr,this.state.total)
	                            }
	                          }
	                        }
	                    })
	                    sessionStorage.setItem('isSelfPick', e.target.value)
	                  }} /><label htmlFor="yes">自提</label>
	              </div>
	            </div>
	            <Addr
	              isSelfPick={this.state.isSelfPick}
	              _addrTabChange={this._addrTabChange}
	              defaultAddr={this.state.defaultAddr}
	              storeAddr={this.state.storeAddr}
	            />
	            <div className="order-addr" >
	              <div className="detail">
	              {this.state.isSelfPick == 'false' ? 
	               <font size='2'>
	               	<span className="fa fa-home fa-lg" /> 配送门店
	               	<font size='1' color='#B8B8B8'>配送({this.state.config.restrictedDistance}km内)</font>
	               	{localStorage.getItem('id')==0 ? <a href="/switch-shop/0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{localStorage.getItem('storeName')} ></a> : <a href="/switch-shop/0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击选择门店        ></a>}
	               </font>
	              :
	            	  <font size="2">
	            	  	<span className="fa fa-home fa-lg" /> 自提门店:
	            	  	{localStorage.getItem('id')==1 ? <a href="/switch-shop/1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{localStorage.getItem('storeName')} ></a> : <a href="/switch-shop/1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击选择门店         ></a>}
	            	  </font>
	              }
	              </div>
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
	                value={this.state.isShippingfree ? 0 : this.state.shippingFee}
	              />
	              运费：<span className="money">￥{this.state.isShippingfree ? 0 : this.state.shippingFee}</span>
	            </div>
	            <div
	              className="order-coupon"
	              onClick={() => {
	                window.location = 'coupon?isFromOrder=1'
	              }}
	            >
	              优惠券：
	              {this.state.voucherText && this.state.voucherText !== ''
	                ? this.state.voucherText + '元现金优惠券   x 1'
	                : null}
	              <span className="icon fa fa-chevron-right" />
	            </div>
	            <input
	              type="hidden"
	              name="voucherCode"
	              value={this.state.voucherCode}
	            />
	             <div className="order-total">
	              满减：<span className="money">￥{this.state.cut}</span>
	            </div>
	            {this.state.isSelfPick === 'false' ?
	            <div className="order-total">
	              满额起送：<span className="money">￥{this.state.config.full>0?this.state.config.full:0}</span>
	            </div>:null}
	                {
	                  this.state.salesInfo.length>=1?
	                  <div className="order-sales">
	                     <div onClick={this.salesInfoShowClick} className='order-sales-top'>
	                      <span className='order-sales-tag'>
	                        营销
	                      </span>
	                      <span className='order-sales-text'>
	                            {this.state.salesInfo[0]}...
	                        </span>
	                        <Icon className="order-sales-icon" name={this.state.salesInfoShow?'chevron-down':'chevron-right'} />
	                    </div>
	                    <div className={'order-sales-info ' +(!this.state.salesInfoShow?'none':'')}>
	                      {this.state.salesInfo.map((item,index)=>{
	                        return(
	                          <div className='order-sales-item' key={index}>
	                            {item}
	                          </div>
	                        )
	                      })}
	                    </div>
	                  </div>
	                  :null
	                }
	            <ErrorMessage error={this.state.error} />
	            <div className="order-submit">
	            	合计：￥{Total}
	            	{this.state.isSelfPick === 'false'?
	            	(Difference>0?
						  	<span className="button">还需：{Difference}元起送!</span>
						  	:
	            	<button type="submit" className="button">提交订单</button>)
	            	:<button type="submit" className="button">提交订单</button>}
	            </div>
	          </form>
	          <div className="qq-btn">
	            	 <span className="border">
	            	 	<span className="badge">
		            		<a href={"http://wpa.qq.com/msgrd?v=3&uin="+this.state.config.qq+"&site=qq&menu=yes"}>
		        					<font color="#ffffff"><i className="fa fa-qq" aria-hidden="true">qq客服</i></font>
		      					</a>
		      				</span>
	      				 </span>
	      		</div>
	        </div>
	        <div className="tail">
							广州洪石软件提供技术支持
					</div>
				</div>
      </DocumentTitle>
    )
  }
salesInfoShowClick=()=>{
    this.setState({
      salesInfoShow: !this.state.salesInfoShow
    })
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


  _init = () => {
    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
      complete: (result) => {
        console.log('result:' + result)
        this.lat = result.detail.location.lat
        this.lng = result.detail.location.lng
       
        var distances = Math.round(
          qq.maps.geometry.spherical.computeDistanceBetween(
            new qq.maps.LatLng(this.lat, this.lng),
            new qq.maps.LatLng( 
              Number(localStorage.getItem('latitude')),
              Number(localStorage.getItem('longitude'))
            )
          ) / 100
          )/10
          if(distances < 1){
          	var distance = 1
          }else{
          	var distance = distances
          }
        req
          .get('/uclee-user-web/getShippingFee?distance=' + distance + '&total=' + (sessionStorage.getItem('total')?sessionStorage.getItem('total'):0))
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
  	console.log("cartItems"+this.state.cartItems)
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

//调用地址解析类
    geocoder = new qq.maps.Geocoder({
      complete: (result) => {
        console.log('result:' + result)
        this.lat = result.detail.location.lat
        this.lng = result.detail.location.lng
       
        var distances = Math.round(
          qq.maps.geometry.spherical.computeDistanceBetween(
            new qq.maps.LatLng(this.lat, this.lng),
            new qq.maps.LatLng( 
              Number(localStorage.getItem('latitude')),
              Number(localStorage.getItem('longitude'))
            )
          ) / 100
          )/10
          if(distances < 1){
          	var distance = 1
          }else{
          	var distance = distances
          }
				if (data.isSelfPick === 'false' && this.state.config.restrictedDistance < distance) {
      this.setState({
        error: errMap['Distribution_error']
      })
      return false
    }
      }
    })

    
    console.log("addrId"+data.addrId)
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
		if(this.state.isSelfPick === 'false'&&this.state.storeAddr.supportDeliver === 'no'){
			this.setState({
        error: errMap['peisong_error']
      })
      return false
		}
    if (!data.cartIds) {
      this.setState({
        error: errMap['data_error']
      })
      return false
    }
    console.log("pickDateStr"+data.pickDateStr)
    console.log("pickTimeStr"+data.pickTimeStr)
    if (!data.pickDateStr || !data.pickTimeStr) {
      this.setState({
        error: errMap['picktime_error']
      })
      return false
    }
    var myDate = Date.parse(new Date());
		myDate = myDate / 1000;//获取系统当前时间戳
 		var yuding = this.state.riqi +' '+this.state.appointedTime
    var yudingtime = Date.parse(new Date(yuding));
    yudingtime = yudingtime / 1000;
    var stringTime  = data.pickDateStr +' '+ data.pickTimeStr
    var timestamp2 = Date.parse(new Date(stringTime));
 		timestamp2 = timestamp2 / 1000;	
    //只有有预定时间时才执行
  	if(myDate!=yudingtime){
    	if(yudingtime>timestamp2){
    		this.setState({
        	error: errMap['times_error']+yuding
      	})
      	return false
    	}
    }
  	console.log("fullamount"+this.state.fullamount)
  	console.log("total"+this.state.total)
    if (this.state.total<this.state.fullamount) {
      this.setState({
        error: this.state.remarks
      })
      return false
    }
    
    if(this.state.convertibleGoods!==null){
    	var hsgooscode=this.state.hsgooscode
			var convertibleGoods=this.state.convertibleGoods;
			var result=convertibleGoods.split(";");
		
			var bb = 0;
			for(var i=0;i<result.length;i++){  	
  			if(hsgooscode.lastIndexOf((result[i]))===-1){			
  				console.log(result[i])
  			}else{
  				bb = 1;
  			}
    		if(this.state.convertibleGoods!==null&&bb===0){
    			this.setState({
        		error: this.state.remarks
      		})
      		return false
    		}
			}
		}
    console.log("pickDateStr"+data.pickDateStr)
    console.log("closeStartDateStr"+this.state.closeStartDateStr)
    if(Date.parse(data.pickDateStr)>=Date.parse(this.state.closeStartDateStr)
          &&
          Date.parse(data.pickDateStr)<=Date.parse(this.state.closeEndDateStr)){

      this.setState({
        error:errMap['closeDate_error']
      })
      return false;
    }
    console.log("businessStartTime"+this.state.businessStartTime) 
    if(data.pickTimeStr<this.state.businessStartTime || data.pickTimeStr>this.state.businessEndTime){
      this.setState({
        error:errMap['businesstime_error']
      })
      return false;
    }
    
    var pickUpGoods = this.state.cartItems.map((item, index) => {

			var date0 = data.pickDateStr+' 00:00:00.0';
			var date = item.pickUpTimes+' 00:00:00.0';
			var date1 = item.pickEndTimes+' 00:00:00.0';
			date0 = date0.substring(0,19);    
			date0 = date0.replace(/-/g,'/'); 
			date = date.substring(0,19);    
			date = date.replace(/-/g,'/'); 
			date1 = date1.substring(0,19);    
			date1 = date1.replace(/-/g,'/'); 
			var timestamp0 = new Date(date0).getTime();
			var timestamp = new Date(date).getTime();
			var timestamp1 = new Date(date1).getTime();
    	if(timestamp0<timestamp || timestamp0>timestamp1){	
      	return -1;
    	}

    })
	if(pickUpGoods.indexOf(-1) !== -1 ) {
		alert("订单包含不在取货时间范围的产品!")
	}else{
		req.get('/uclee-user-web/status')
    .end((err, res) => {
      if (err) {
        return err
      }
    })

    req.post('/uclee-user-web/orderHandler').send(data)
    .end((err, res) => {
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
        sessionStorage.removeItem('fullamount')
        sessionStorage.removeItem('convertibleGoods')
        sessionStorage.removeItem('remarks')
        sessionStorage.removeItem('remark')
        sessionStorage.removeItem('isSelfPick')
        sessionStorage.removeItem('isFromCart')
        sessionStorage.removeItem('p_date')
        sessionStorage.removeItem('p_time')
        sessionStorage.removeItem('isFromCart')
        sessionStorage.removeItem('phone')
        sessionStorage.removeItem('total')
        window.location =
          'seller/payment?paymentSerialNum=' + resJson.paymentSerialNum
      }
    })
	}
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
            <span className="sku">{item.specification}({item.csshuxing})</span>
            <span className="count pull-right">{((Date1)<(item.startTime)||(Date1)>(item.endTime)||(item.promotion)===null) ?"单价：￥"+item.money:"促销单价：￥"+item.promotion}</span>  
          </div>
          <div className="other">
           {item.appointedTime!==0&&item.appointedTime!==null ?
          	"提前"+item.appointedTime+"小时预定"
          	:null
           }
           {item.pickUpTimes !== null && item.pickEndTimes !== null ?
          	"取货时间:"+item.pickUpTimes+"至"+item.pickEndTimes
  					:null
           }
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