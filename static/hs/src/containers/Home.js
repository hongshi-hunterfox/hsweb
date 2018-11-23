/* global wx */
import "./home.css"
import './detail.css'
import DocumentTitle from "react-document-title"
import "slick-carousel/slick/slick.css"
var React = require("react")
var request = require('superagent')
import Big from 'big.js'
import Icon from '../components/Icon'
import CartBtn from "./CartBtn"
import Navi from "./Navi"
import Slick from "react-slick"
var Link = require("react-router").Link
import ProductGroup from "./ProductGroup"
var HomeUtil = require('../utils/HomeUtil.js');
import req from 'superagent'
var fto = require('form_to_object')
var myDate = new Date()
var Date1 = new Date(myDate).getTime()
import LazyLoad from 'react-lazyload'

class HomeCarousel extends React.Component {
  render() {
    var slickSettings = {
      dots: true,
      arrows: false,
      infinite: true,
      speed: 800,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      show: false
    }
    var banner = this.props.banner.map((item, index) => {
      return(
          <div className="home-carousel-item" key={index}>
          	<LazyLoad height={200}>
              {
                item.link?
                <a href={item.link}>
                  <img
                    src={item.imageUrl}
                    className="home-carousel-img"
                    alt=""
                  />
                </a>:
                  <img
                  src={item.imageUrl}
                  className="home-carousel-img"
                  alt=""
                />
              }
            </LazyLoad>
          </div>
      );
    });
    return (
      <div className="home-carousel">
        <Slick {...slickSettings}>          
          {banner}
        </Slick>
      </div>
    )
  }
}
class SearchBar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      keyword: ''
    }
  }
  render() {
    return (
      <div className="home-search" onSubmit={this._handleSubmit}>
        <form method="post" className="home-search-form" name="searchform">
          <div className="home-search-div">
            <i className="fa fa-search icon" />
            <input
              className="home-search-input"
              id="keyword"
              name="keyword"
              value={this.state.keyword}
              placeholder="搜索产品"
              onChange={this._onChange}
            />
          </div>

        </form>
      </div>
    )
  }

  _onChange =(e)=>{
    this.setState({
      keyword:e.target.value
    })
  }

  _handleSubmit = e => {
     e.preventDefault()
    var data = fto(e.target);
    console.log(data.keyword);
    window.location="/all-product?keyword="+data.keyword+"&merchantCode="+localStorage.getItem('merchantCode');
  }
}


class HomeNotice extends React.Component {
	constructor(props) {
    super(props)
    this.state = {
     config:{},
     notice:1
    }
  }

  componentDidMount() {
  	req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config:data.config,
        notice:data.notice
      })
      console.log(this.state.notice)
    })
  }
  render() {
  	
    return (
    	<div className="gundongBox">
    	{this.state.notice > 21
    		?
	    	<span className="gundongList">
		      <p className="gundongList-span"><i className="fa fa-volume-down fa-lg" aria-hidden="true" /> {this.state.config.notice}</p>
		    </span>
		    :
    		<p className="gundongList-span"><i className="fa fa-volume-down fa-lg" aria-hidden="true" /> {this.state.config.notice}</p>
  		}
      </div>
    )
  }
}


class HomeNav extends React.Component {
  _location=(url)=>{
      window.location=url
    }
  render() {
    var list = this.props.quickNavis.map((item,index)=>{
        return (
            <div
              onClick={this._location.bind(this,"/all-product?naviId="+item.naviId+"&merchantCode="+localStorage.getItem('merchantCode'))}
              className="home-nav-item"
              key={index}
            >
            <LazyLoad height={200}>
            	<img src={item.imageUrl} className='home-nav-item-image' />
            </LazyLoad>
              <span className="home-nav-item-text">
                {item.title}
              </span>
            </div>
        )
    })

    return (
      <div className="home-nav">
        {list}
      </div>
    )
  }
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
                <div>
              		{props.vipPrice !== '-' ? "会员价：¥"+props.vipPrice : null}
              	</div>
              	
              {
              	((Date1)<(props.startTime)||(Date1)>(props.endTime)) ?
              <div>
              	在售价：¥{props.totalPrice} {props.prePirce>0 ? <span className='pre'>原价：¥{props.prePirce}</span>  :null}
              </div>	
              	: 
              <div>
              	{((props.promotionPrice)==='-') ? 
	              	<div>
	              		在售价：¥{props.totalPrice} {props.prePirce>0 ? <span className='pre'>原价：¥{props.prePirce}</span> : null}
	              	</div> :
	              	<div>
	              		促销价：¥{props.promotionPrice} {props.prePirce>0 ? <span className='pre'>原价：¥{props.prePirce} 在售价：¥{props.totalPrice}</span> : null}
	              	</div>
              	}
              </div>
              }
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
class Home extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      groups: [],
      stores: [],
      logoUrl:'',
      signName:'',
      banner:[],
      quickNavis:[],
      specifications:[],
      parameters:[],
      parameter: '',
      images:[],
      pickType: 'add_to_cart',
      showPick: false,
      currentSpecValudId: null,
      ValudId: null,
      currentAmount: 1,
      salesAmount:0,
      productId:0
    }
    this.specPriceMap = {}
    this.specValueMap = {}
    this.specPrePriceMap = {}
    this.specPreValueMap = {}
    this.specProPriceMap = {}
    this.specProValueMap = {}
    this.specVipPriceMap = {}
    this.specVipValueMap = {}
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
    this.proMinVipPrice = 0
    this.proMaxVipPrice = 0
  }

  componentDidMount() {
  	req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config:data.config
      })
      console.log(this.state.config)
    })
  	  req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				logoUrl:c.logoUrl,
				signName:c.signName
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

				wx.ready(() => {
					wx.onMenuShareAppMessage({
					title: '好店推荐: '+this.state.signName, // 分享标题
					desc: '发现了一家好店: '+this.state.signName+',快来逛逛吧.', // 分享描述
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				})
				wx.onMenuShareTimeline({
					title: '好店推荐: '+this.state.signName, // 分享标题
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				   })
				})
			})
    HomeUtil.home(function (res) {
      this.setState(res)
    }.bind(this))
    if(this.props.location.query.serialNum!==null){
      req
        .get('/uclee-user-web/invitation?serialNum='+this.props.location.query.serialNum)
        .end((err, res) => {
          if (err) {
            return err
          }
        })
    }
    if(this.props.location.query.isShake){
        req
        .get('/uclee-user-web/shakeHandler')
        .end((err, res) => {
          if (err) {
            return err
          }
          if(res.body){
            alert("摇一摇抽奖活动参与成功");
          }else{
            alert("请先关注公众号");
          }
        })
      }
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
        ValudId: id
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

  _clickNext = () => {
    // 加入购物车
    req
      .post('/uclee-user-web/cartHandler')
      .send({
        amount: this.state.currentAmount,
        productId: parseInt(this.state.productId, 10),
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
          window.location = '/cart?merchantCode='+localStorage.getItem('merchantCode')
        }else{
          alert(result.reason);
        }
        
      })
    
    return

    // 立即购买

  }
  _location = url => {
    window.location = url
  }
  render() {
  	var canshu = this.state.parameters
  	var canshuid = this.state.parameters.map((item,index)=>{
      return(
      	<div key={index}>
      	{item.sname !==null ?
       	<div className='detail-picker-spec-value'>
         		{item.id}
        </div>
        : null}
        </div>
      );  
    })
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
          // calc the map BTW
          this.specPrePriceMap[`_${item.valueId}`] = item.prePrice
          this.specPreValueMap[`_${item.valueId}`] = item.value

          return item.prePrice
        })
        var promotionPrice = spec.values.map((item) => {
          // calc the map BTW
          this.specProPriceMap[`_${item.valueId}`] = item.promotionPrice
          this.specProValueMap[`_${item.valueId}`] = item.value

          return item.promotionPrice
        })
        var vipPrice = spec.values.map((item) => {
          // calc the map BTW
          this.specVipPriceMap[`_${item.valueId}`] = item.vipPrice
          this.specVipValueMap[`_${item.valueId}`] = item.value

          return item.vipPrice
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
        this.proMinVipPrice = Math.min.apply(null, vipPrice)
        this.proMaxVipPrice = Math.max.apply(null, vipPrice)
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
    var vipPrice='-'
    if (this.specVipPriceMap[`_${this.state.currentSpecValudId}`]) {
      vipPrice = new Big(this.specVipPriceMap[`_${this.state.currentSpecValudId}`]).toString()
    }
		var startTime='00:00'
    if (this.specStartTimeMap[`_${this.state.currentSpecValudId}`]) {
      startTime = new Big(this.specStartTimeMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    var endTime='00:00'
    if (this.specEndTimeMap[`_${this.state.currentSpecValudId}`]) {
      endTime = new Big(this.specEndTimeMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    var groups = this.state.groups.map((item, index) => {
      return(
          <div key={index} className={'product-group' + (item.displayType === '1' ? ' product-group-hor': '')}>
          {item.image === null ?
          	<div className="yemei">
	          	<span className='yemei-text'>
	          		<h3><p className="label label-danger">{item.groupName}</p></h3>
	          	</span>
          	</div>
          	:
            <div className="yemei">
            	<LazyLoad height={200}>
	            	<img src={item.image} width='100%' height='100%' alt=""/>	 
	            </LazyLoad>
           	</div>
          }
            <div className="product-group-body">
              {
                item.products.map((item1, index1)=> {
                  return(
                      <div className="product-item" key={index1}>
                        <div className='product-item-img'
                          onClick={()=>{window.location="/detail/" + item1.productId+"?merchantCode="+localStorage.getItem('merchantCode')}}
                        >
                        	<LazyLoad height={200}>
	                          <img
	                            src={item1.image}
	                            className="image"
	                            alt=""
	                          />
                          </LazyLoad>
                          {
	                        	item1.vipPrice !== null 
	                        	?
	                        	<span style={{position:"absolute",left:"5px",top:"49%",color:"white"}} className="abel label-danger">会员价:¥ {item1.vipPrice}</span>
	                        	: null 
                            }
                        </div>

                        {item1.tag
                          ? <span className="product-item-tag">
                              {item1.tag}
                            </span>
                          : null
                        }
                        <div className="product-item-info">
                          <div className="product-item-title">
                            {item1.title}
                          </div>
                          <div className="product-item-price">
                            <div className="left">¥{item1.price}                           
                            </div>
                            <div className='right' onClick={()=>{
                              this.setState({
                                  showPick: true,
                                  specifications:item1.specifications,
                                  images:item1.images,
                                  currentSpecValudId:item1.currentSpecValudId,
                                  ValudId:item1.ValudId,
                                  productId:item1.productId
                                })
                              req
                                .get('/uclee-user-web/productDetail?productId=' + item1.productId)
                                .end((err, res) => {
                                  if (err) {
                                    return err
                                  }

                                  this.setState({
                                    showPick: true,
                                    specifications:res.body.specifications,
                                    images:res.body.images,
                                    parameter:res.body.parameter,
                                    parameters:res.body.parameters,
                                    currentSpecValudId:res.body.currentSpecValudId,
                                    ValudId:res.body.ValudId,
                                    productId:item1.productId
                                  })
                                })
                            }}><i className="fa fa-shopping-cart" aria-hidden="true" /></div>
                          </div>
                        </div>
                      </div>
                  )
                 
                })
              }
            </div>
            <div className='text'>
	            <Link to={'/all-product?merchantCode='+localStorage.getItem('merchantCode')}>
	              <span className="badge">查看更多<i className="glyphicon glyphicon-chevron-right" /></span>
	            </Link>
            </div>
          </div>
      );
    });
          
    return (
      <DocumentTitle title="首页">
          <div className="home">
         	{/*<SearchBar/>*/}
            {
              this.state.banner.length ? 
              <HomeCarousel banner={this.state.banner}/> : null
            }
            <HomeNotice config={this.state.config}/>
            <HomeNav quickNavis={this.state.quickNavis}/>
            {groups}
            <CartBtn />
            <DetailPicker
              showPick={this.state.showPick}
              closePick={this._closePick}
              image={this.state.images[0]?this.state.images[0].imageUrl:''}
              title={this.state.title}
              totalPrice={totalPrice}
              spec={spec}
              canshu={canshu}
              canshuid={canshuid}
              parameter={this.state.parameter}
              pickSpec={this._pickSpec}
              param={this._param}
              currentSpecValudId={this.state.currentSpecValudId}
              ValudId={this.state.ValudId}
              currentAmount={this.state.currentAmount}
              subAmount={this._subAmount}
              addAmount={this._addAmount}
              onClickNext={this._clickNext}
              pickType={this.state.pickType}
              prePirce={prePirce}
              promotionPrice={promotionPrice}
              vipPrice={vipPrice}
              startTime={startTime}
              endTime={endTime}
              />
            <Navi query={this.props.location.query}/>
						<div className="tail">
							广州洪石软件提供技术支持
						</div>
          </div>
      </DocumentTitle>
    )
  }
}
export default Home