import "./home.css"
import './detail.css'
import DocumentTitle from "react-document-title"
import "slick-carousel/slick/slick.css"
var React = require("react")
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
    window.location="/all-product?keyword="+data.keyword;
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
              onClick={this._location.bind(this,"/all-product?naviId="+item.naviId)}
              className="home-nav-item"
              key={index}
            >
            <img src={item.imageUrl} className='home-nav-item-image' />
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
                ¥ {props.totalPrice} {props.prePirce>0?<span className='pre'>原价：¥{props.prePirce}</span>:null}
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
      banner:[],
      quickNavis:[],
      specifications:[],
      images:[],
      pickType: 'add_to_cart',
      showPick: false,
      currentSpecValudId: null,
      currentAmount: 1,
      salesAmount:0,
      productId:0
    }
    this.specPriceMap = {}
    this.specValueMap = {}
    this.specPrePriceMap = {}
    this.specPreValueMap = {}
    this.minPrice = 0
    this.maxPrice = 0
    this.preMinPrice = 0
    this.preMaxPrice = 0
  }

  componentDidMount() {
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
          specifications:res.body.specifications,
          images:res.body.images,
          currentSpecValudId:res.body.currentSpecValudId
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
        specificationValueId: this.state.currentSpecValudId
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
  _location = url => {
    window.location = url
  }
  render() {
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
        this.minPrice = Math.min.apply(null, prices)
        this.maxPrice = Math.max.apply(null, prices)
        this.preMinPrice = Math.min.apply(null, prePrices)
        this.preMaxPrice = Math.max.apply(null, prePrices)
    }
    var totalPrice = '-'
    if (this.specPriceMap[`_${this.state.currentSpecValudId}`]) {
      totalPrice = new Big(this.specPriceMap[`_${this.state.currentSpecValudId}`]).times(new Big(this.state.currentAmount)).toString()
    }
    var prePirce=0;
    if (this.specPrePriceMap[`_${this.state.currentSpecValudId}`]) {
      prePirce = new Big(this.specPrePriceMap[`_${this.state.currentSpecValudId}`]).toString()
    }
    var groups = this.state.groups.map((item, index) => {
      return(
          <div key={index} className={'product-group' + (item.displayType === 'horizontal' ? ' product-group-hor': '')}>
            <div className="product-group-header">
              {item.groupName}
              <Link to='/all-product' className="product-group-header-link">
                更多
              </Link>

              
            </div>
            <div className="product-group-body">
              {
                item.products.map((item1, index1)=> {
                  return(
                      <div className="product-item" key={index1}>
                        <div className='product-item-img'
                          onClick={()=>{window.location="/detail/" + item1.productId}}
                        >
                          <img
                            src={item1.image}
                            className="image"
                            alt=""
                          />
                        </div>

                        {item1.tag
                          ? <span className="product-item-tag">
                              {item1.tag}
                            </span>
                          : null}

                        <div className="product-item-info">
                          <div className="product-item-title">
                            {item1.title}
                          </div>
                          <div className="product-item-price">
                            <div className='left'>¥{'  ' + item1.price}</div>
                            <div className='right' onClick={()=>{
                              this.setState({
                                  showPick: true,
                                  specifications:item1.specifications,
                                  images:item1.images,
                                  currentSpecValudId:item1.currentSpecValudId,
                                  productId:item1.productId
                                })
                              /*req
                                .get('/uclee-user-web/productDetail?productId=' + item1.productId)
                                .end((err, res) => {
                                  if (err) {
                                    return err
                                  }

                                  this.setState({
                                    showPick: true,
                                    specifications:res.body.specifications,
                                    images:res.body.images,
                                    currentSpecValudId:res.body.currentSpecValudId,
                                    productId:item1.productId
                                  })
                                })*/
                            }}>buy</div>
                          </div>
                        </div>
                      </div>
                  )
                 
                })
              }
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
              pickSpec={this._pickSpec}
              currentSpecValudId={this.state.currentSpecValudId}
              currentAmount={this.state.currentAmount}
              subAmount={this._subAmount}
              addAmount={this._addAmount}
              onClickNext={this._clickNext}
              pickType={this.state.pickType}
              prePirce={prePirce}
              />
            <Navi query={this.props.location.query}/>

          </div>
      </DocumentTitle>
    )
  }
}
export default Home
