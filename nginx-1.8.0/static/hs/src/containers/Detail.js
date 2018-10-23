/* global wx */
import React from 'react'
import DocumentTitle from 'react-document-title'
import 'slick-carousel/slick/slick.css'
import Slick from 'react-slick'
import './detail.css'
import Icon from '../components/Icon'
import Loading from '../components/Loading'
import req from 'superagent'
import Big from 'big.js'
var request = require('superagent')
var myDate = new Date()
var Date1 = new Date(myDate).getTime()

const DetailCarousel = (props) => {
  var slickSetting = {
    arrows: false,
    dots: true
  }
  if (props.images && props.images.length) {
    return (
      <Slick {...slickSetting} className="detail-carousel">
        {
          props.images.map((item, index) => {
            return (
              <div key={index} className="detail-carousel-item">
                <img src={item.imageUrl} alt="" className="detail-carousel-img"/>
              </div>
              )
          })
        }
      </Slick>
      )
  }
  return null
}

const DetailInfo = (props) => {
  return (
    <div className="detail-info">
      <div className="detail-info-title">
        {props.title}
      </div>
      <div className='detail-info-price'>
      <div className="detail-info-price-lprice">
        {
          props.preMinPrice!==null&&props.preMaxPrice!==null?
          props.preMinPrice === props.preMaxPrice ? 
            props.preMinPrice>0?<span className="detail-info-price-rprice-pre">原价：¥ {props.preMinPrice}</span>:<span className="detail-info-price-rprice-pre">原价：¥ {props.preMaxPrice}</span>
            :
            props.preMinPrice>0?<span className="detail-info-price-rprice-pre">原价：¥ {props.preMinPrice} - {props.preMaxPrice}</span>:<span className="detail-info-price-rprice-pre">原价：¥ {props.preMaxPrice}</span>:null
        }
      </div>
      <div className="detail-info-price-rprice">
        {
         	<span>¥ {(Date1>props.endtime) ? props.minPrice : props.proMinPrice < props.minPrice ? props.proMinPrice : props.minPrice} - {props.maxPrice}</span>
        }         
      </div>
      </div>
      <div className="detail-info-stat">
        <div className="detail-info-stat-item">{props.shippingFree?<span className="tag">免运费</span>:null}<span style={{float:'right'}}>销量：{props.salesAmount}</span></div>
      </div>
    </div>
    )
}

const DetailRich = (props) => {
  return (
    <div className="detail-rich">
      <div className="detail-rich-title">
        商品详情
      </div>
      <div className="detail-rich-content" dangerouslySetInnerHTML={{
        __html: props.description
      }}/>
    </div>
    )
}

const DetailPick = (props) => {
  return (
    <div className="detail-pick" onClick={props.onClick}>
      <span>
        选择：
        {
          props.currentAmount && props.specValue ?
          <span>{`${props.specValue} x ${props.currentAmount}`}</span>
          :
          <span>规格 数量</span>
        }
      </span>

      <Icon className="detail-pick-icon" name="chevron-right" />
    </div>
    )
}

const DetailSales = (props) => {
  return (
    <div className="detail-sales">

        {
          props.salesInfo.length>=1?
          <div onClick={props.salesInfoShowClick} className='detail-sales-top'>
            <span className='detail-sales-tag'>
              优惠
                
            </span>
            <span className='detail-sales-text'>
                  {!props.salesInfoShow?props.salesInfo[0]+'...':null}
              </span>
              <Icon className="detail-sales-icon" name={props.salesInfoShow?'chevron-down':'chevron-right'} />
          </div>
          :null
        }
        
        
      <div className={'detail-sales-info ' +(!props.salesInfoShow?'none':'')}>
        {props.salesInfo.map((item,index)=>{
          return(
            <div className='detail-sales-item' key={index}>
              {item}
            </div>
          )
        })}
      </div>
      
    </div>
    )
}

const DetailCoupon = (props) => {
  return (
    <div className="detail-sales">

        {
          props.giftCouponsInfo.length>=1?
          <div onClick={props.giftCouponsInfoShowClick} className='detail-sales-top'>
            <span className='detail-sales-tag'>
              赠送    
            </span>
            <span className='detail-sales-text'>
                  {!props.giftCouponsInfoShow?props.giftCouponsInfo[0]+'...':null}
              </span>
              <Icon className="detail-sales-icon" name={props.giftCouponsInfoShow?'chevron-down':'chevron-right'} />
          </div>
          :null
        }
        
        
      <div className={'detail-sales-info ' +(!props.giftCouponsInfoShow?'none':'')}>
        {props.giftCouponsInfo.map((item,index)=>{
          return(
            <div className='detail-sales-item' key={index}>
              {item}
            </div>
          )
        })}
      </div>
      
    </div>
    )
}

const DetailPicker = (props) => {
  if (props.showPick) {
    return (
      <div className="detail-picker-overlay" onClick={props.closePick}>
        <div className="detail-picker" onClick={(e) => {e.stopPropagation()}}>
          <span className="detail-picker-close" onClick={props.closePick}><Icon name="times-circle"/></span>

          <div className="detail-picker-header clearfix">
            <div className="detail-picker-header-img">
              <img src={props.image} width="50" height="50" alt=""/>
            </div>
            <div className="detail-picker-header-info">
              <div className="detail-picker-header-title">{props.title}</div>
              <div className="detail-picker-header-price">
              {
              	((Date1)<(props.startTime)||(Date1)>(props.endTime)) ?
              <div>
              在售价：¥{props.totalPrice} {props.prePirce>0?<span className='pre'>原价：¥{props.prePirce}</span>:null}
              </div>	
              	: 
              <div>
              	{((props.promotionPrice)==='-') ? 
              	<div>
              	在售价：¥{props.totalPrice} {props.prePirce>0?<span className='pre'>原价：¥{props.prePirce}</span>:null}
              	</div> :
              	<div>
              	促销价：¥{props.promotionPrice} {props.prePirce>0?<span className='pre'>原价：¥{props.prePirce} 在售价：¥{props.totalPrice}</span>:null}
              	</div>
              	}
              </div>}
              </div>
            </div>
          </div>

          <div className="detail-picker-spec">
            <div className="detail-picker-spec-name">规格：</div>
            <div className="detail-picker-spec-values clearfix">
              {
                props.spec.values.map((item) => {
                  return (
                    <div
                      key={item.valueId}
                      onClick={() => {
                        props.pickSpec(item.valueId)

                      }}
                      className={'detail-picker-spec-value' + (item.valueId === props.currentSpecValudId ? ' active' : '')}>
                      {item.value}
                    </div>
                    )
                })
              }
            </div>
            <div className="detail-picker-spec-name">{props.parameter!==null ? (props.parameter)+'：': null}</div>
            <div className="detail-picker-spec-values clearfix">           
              {props.canshu.map((item)=>{
       					return(	
      			 				<div key={item.id}  
      			 					onClick={() => {
          	      			props.param(item.id)
                			}} 
                			className={(item.sname !==null ? 'detail-picker-spec-value' + (item.id === props.ValudId ? ' active' : ''): null)}>
         							{item.sname}
        						</div>
        				);  
    					})}        
            </div>
          </div>

          <div className="detail-picker-amount clearfix">
            <div className="input-group">
              <div className="input-group-btn">
                <button className="btn btn-default" onClick={props.subAmount} disabled={props.currentAmount === 0}>
                  <Icon name="minus" />
                </button>
              </div>
              <input type="text" className="form-control" readOnly value={props.currentAmount}/>
              <div className="input-group-btn" onClick={props.addAmount}>
                <button className="btn btn-default">
                  <Icon name="plus" />
                </button>
              </div>
            </div>
            <span className="detail-picker-amount-key">购买数量：</span>
          </div>

          {
            props.pickType && props.currentAmount > 0 ?
            <div className="detail-picker-next" onClick={props.onClickNext}>
              {'下一步'}
            </div>
            :
            null
          }
        </div>
      </div>
    )
  }
  return null
}

const DetailActions = (props) => {
  return ( 
    <div className="detail-actions">
    	<div className="detail-action-aside">
    	  <div className="detail-action-cart">
    	  	<i className="fa fa-qq" aria-hidden="true" />
    	  	<span className="detail-action-cart-text">
      			<a href={"http://wpa.qq.com/msgrd?v=3&uin="+props.numbers+"&site=qq&menu=yes"}>       		
        		 <font color="#8B795E">qq客服</font>
      			</a>
      		</span>
				</div>
    	</div>
      <div className="detail-action-aside" onClick={() => {
        window.location = '/cart'
      }}>
        <div className="detail-action-cart has-item">
          <Icon name="shopping-cart" className="detail-action-cart-icon"/>
          <span className="detail-action-cart-text"><font color="#8B795E">购物车</font></span>
        </div>
      </div>
      <div className="detail-action-group">
        <div className="detail-action-add-cart" onClick={props.onClickCart}>加入购物车</div>
        <div className="detail-action-buy-now" onClick={props.onClickBuy}>立即购买</div>
      </div>
    </div>
  )
}

class Detail extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      images: [],
      stores: [],
      signName:'',
      brand:'',
      text: '',
      description: null,
      specifications: [],
      parameters:[],
      title: null,
      numbers: '',
      parameter: '',
      explain: null,
      appointedTime: 0,
      shippingFree:false,
      loading: true,
      showPick: false,
      currentSpecValudId: null,
      ValudId: null,
      currentAmount: 1,
      salesAmount:0,
      pickType: 'add_to_cart', // 'add_to_cart' || 'buy_now'
      salesInfo:[],
      giftCouponsInfo:[],
      salesInfoShow:false,
      giftCouponsInfoShow:false
    }

    this.specPriceMap = {}
    this.specValueMap = {}
    this.specPrePriceMap = {}
    this.specPreValueMap = {}
    this.specProPriceMap = {}
    this.specProValueMap = {}
    this.specStartTimeMap = {}
    this.specStaValueMap = {}
    this.specEndTimeMap = {}
    this.specEndValueMap = {}
    this.minPrice = 0
    this.maxPrice = 0
    this.preMinPrice = 0
    this.preMaxPrice = 0
    this.proMinPrice = 0
    this.proMaxPrice = 0
    this.endtime = 0
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/productDetail?productId=' + this.props.params.id)
      .query({
          merchantCode: localStorage.getItem('merchantCode')
        })
      .end((err, res) => {
        if (err) {
          return err
        }
        this.setState({
          loading: false,
          ...res.body,
        })
      req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				signName:c.signName,
				numbers:c.numbers,
				brand:c.brand
			})
		})

   req
    .get('/uclee-user-web/productDetailImg?productId=' + this.props.params.id)
    .query({
        merchantCode: localStorage.getItem('merchantCode')
          })
    .end((err, res) => {
      if (err) {
        return err
      }

      this.setState({
        description: res.body.description
          })
      })
    })
    req
			.get('/uclee-user-web/wxConfig')
			.query({
				url: window.location.href.split('#')[0]
			})
			.end((err, res) => {
				var c = JSON.parse(res.text)
				wx.config({
					debug: false,
					appId: c.appId,
					timestamp: c.timestamp,
					nonceStr: c.noncestr,
					signature: c.signature,
					jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
				})
				var explain = this.state.explain
				
				wx.ready(() => {
					var explain = this.state.explain
					wx.onMenuShareAppMessage({
					title: this.state.title+'-'+this.state.brand, // 分享标题
					desc: explain != null ? explain : '我在'+this.state.brand+'发现了一件好东西:'+this.state.title+"最低价格仅"+this.minPrice+'元,快来看看吧!',  // 分享描述
					link: window.location.href, // 分享链接
					imgUrl: this.state.images[0].imageUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				})

				  wx.onMenuShareTimeline({
					title: '我在'+this.state.brand+'发现了一件好东西:'+this.state.title+"最低价格仅"+this.minPrice+'元,快来看看吧!', // 分享标题
					link: window.location.href, // 分享链接
					imgUrl: this.state.images[0].imageUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				   })
				})
			})
	}

  render() {
  	var canshu = this.state.parameters
    var { specifications } = this.state
    if (specifications.length) {
        // take the first one only
        var spec = specifications[0]
        var prices = spec.values.map((item) => {
          // calc the map BTW
          this.specPriceMap[`_${item.valueId}`] = item.hsGoodsPrice
          this.specValueMap[`_${item.valueId}`] = item.value

          return item.hsGoodsPrice
        })
        var prePrices = spec.values.map((item) => {
          console.log(item)
          // calc the map BTW
          this.specPrePriceMap[`_${item.valueId}`] = item.prePrice
          this.specPreValueMap[`_${item.valueId}`] = item.value

          return item.prePrice
        })
        var promotionPrice = spec.values.map((item) =>{
        	this.specProPriceMap[`_${item.valueId}`] = item.promotionPrice
        	this.specProValueMap[`_${item.valueId}`] = item.value
        	return item.promotionPrice
        })
        var startTime = spec.values.map((item) =>{
        	this.specStartTimeMap[`_${item.valueId}`] = item.startTime
        	this.specStaValueMap[`_${item.valueId}`] = item.value
        	return item.startTime
        })
        var endTime = spec.values.map((item) =>{
        	this.specEndTimeMap[`_${item.valueId}`] = item.endTime
        	this.specEndValueMap[`_${item.valueId}`] = item.value
        	return item.endTime
        })
        this.minPrice = Math.min.apply(null, prices)
        this.maxPrice = Math.max.apply(null, prices)
        this.preMinPrice = Math.min.apply(null, prePrices)
        this.preMaxPrice = Math.max.apply(null, prePrices)
        this.proMinPrice = Math.min.apply(null, promotionPrice)
        this.proMaxPrice = Math.max.apply(null, promotionPrice)
        this.endtime = endTime
        console.log(prePrices)
    }

    var totalPrice = '-'
    if (this.specPriceMap[`_${this.state.currentSpecValudId}`]) {
      totalPrice = new Big(this.specPriceMap[`_${this.state.currentSpecValudId}`]).times(new Big(this.state.currentAmount)).toString()
    }
    var prePirce=0;
    if (this.specPrePriceMap[`_${this.state.currentSpecValudId}`]) {
      prePirce = new Big(this.specPrePriceMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    var promotionPrice='-'
    if (this.specProPriceMap[`_${this.state.currentSpecValudId}`]) {
      promotionPrice = new Big(this.specProPriceMap[`_${this.state.currentSpecValudId}`]).toString()
    }
		var startTime='00:00'
    if (this.specStartTimeMap[`_${this.state.currentSpecValudId}`]) {
      startTime = new Big(this.specStartTimeMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    var endTime='00:00'
    if (this.specEndTimeMap[`_${this.state.currentSpecValudId}`]) {
      endTime = new Big(this.specEndTimeMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    return (
      <DocumentTitle title="商品详情">
        {
          this.state.loading ?
          <Loading />
          :
          <div className="detail">
            <DetailCarousel images={this.state.images}/>
            <DetailInfo
              title={this.state.title}
              appointedTime={this.state.appointedTime}
              explain={this.state.explain}
              shippingFree={this.state.shippingFree}
              salesAmount={this.state.salesAmount}
              minPrice={this.minPrice}
              maxPrice={this.maxPrice}
              preMinPrice={this.preMinPrice}
              preMaxPrice={this.preMaxPrice}
              proMinPrice={this.proMinPrice}
              proMaxPrice={this.proMaxPrice}
              endtime={this.endtime}/>
              {
                this.state.salesInfo.length>=1?
                <DetailSales salesInfo={this.state.salesInfo} salesInfoShow = {this.state.salesInfoShow} salesInfoShowClick={this.salesInfoShowClick}/>
                :null
              }
              {
                this.state.giftCouponsInfo.length>=1?
                <DetailCoupon giftCouponsInfo={this.state.giftCouponsInfo} giftCouponsInfoShow = {this.state.giftCouponsInfoShow} giftCouponsInfoShowClick={this.giftCouponsInfoShowClick}/>
                :null
              }
            <DetailPick
              onClick={this._showPick}
              currentAmount={this.state.currentAmount}
              salesAmount={this.state.salesAmount}
              specValue={this.specValueMap[`_${this.state.currentSpecValudId}`]}/>
            <DetailPicker
              showPick={this.state.showPick}
              closePick={this._closePick}
              image={this.state.images[0].imageUrl}
              title={this.state.title}
              appointedTime={this.state.appointedTime}
              parameter={this.state.parameter}
              totalPrice={totalPrice}
              spec={spec}
              pickSpec={this._pickSpec}
              param={this._param}
              ValudId={this.state.ValudId}
              currentSpecValudId={this.state.currentSpecValudId}
              currentAmount={this.state.currentAmount}
              subAmount={this._subAmount}
              addAmount={this._addAmount}
              onClickNext={this._clickNext}
              pickType={this.state.pickType}
              prePirce={prePirce}
              promotionPrice={promotionPrice}
              startTime = {startTime}
              endTime = {endTime}
              canshu = {canshu}
              />
            <DetailRich description={this.state.description}/>
            <DetailActions numbers={this.state.numbers} onClickCart={this._clickCartAdd} onClickBuy={this._clickBuyNow}/>
          </div>
        }
      </DocumentTitle>
      )
  }
  salesInfoShowClick=()=>{
    this.setState({
      salesInfoShow: !this.state.salesInfoShow
    })
  }
  
  giftCouponsInfoShowClick=()=>{
    this.setState({
      giftCouponsInfoShow: !this.state.giftCouponsInfoShow 
    })
  }
  
  _showPick = () => {
    this.setState({
      showPick: true
    })
  }

  _closePick = () => {
    this.setState({
      showPick: false
    })
  }

  _addAmount = () => {
    this.setState((prevState, props) => {
      return {
        currentAmount: prevState.currentAmount + 1
      }
    })
  }

  _subAmount = () => {
    this.setState((prevState, props) => {
      return {
        currentAmount: prevState.currentAmount - 1
      }
    })
  }

  _pickSpec = (id) => {
    this.setState((prevState) => {
      return {
        currentSpecValudId: id,
        currentAmount: prevState.currentAmount || 1
      }
    })
  }

  _param  = (id) => {
    this.setState((prevState) => {
      return {
        ValudId: id,
      }
    })
  }

  _clickCartAdd = () => {
    this.setState({
      pickType: 'add_to_cart'
    })
    this._showPick()
  }

  _clickBuyNow = () => {
    this.setState({
      pickType: 'buy_now'
    })
    this._showPick()
  }

  _buyClick=(productId)=>{
    req
      .get('/uclee-user-web/productDetail?productId=' + productId)
      .end((err, res) => {
        alert('here:' + res.body.currentSpecValudId)
        if (err) {
          return err
        }
        alert('here:' + res.body.currentSpecValudId)
        this.setState({
          showPick: true,
          parameter: res.body.parameter,
          parameters:res.body.parameters,
          specifications:res.body.specifications,
          images:res.body.images,
          currentSpecValudId:res.body.currentSpecValudId,
          ValudId:res.body.ValudId
        })
      })
  }



  _clickNext = () => {
    // 加入购物车
    req
      .post('/uclee-user-web/cartHandler')
      .send({
        amount: this.state.currentAmount,
        productId: parseInt(this.props.params.id, 10),
        specificationValueId: this.state.currentSpecValudId,
        paramete: this.state.parameter,
        canshuValueId: this.state.ValudId
        
      }) 
      .end((err, res) => {
        if (err) {
          return err
        }
        var result = JSON.parse(res.text);
        if(result.result){
          window.location = '/cart'
        }else{
          alert(result.reason);
        }
        
      })
    
    return

    // 立即购买

  }
}

export default Detail