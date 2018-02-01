import React from 'react'
import DocumentTitle from 'react-document-title'
import 'slick-carousel/slick/slick.css'
import Slick from 'react-slick'
import './detail.css'
import Icon from '../components/Icon'
import Loading from '../components/Loading'
import req from 'superagent'
import Big from 'big.js'

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
          props.minPrice === props.maxPrice ? 
          <span>¥ {props.minPrice}</span>
          :
          <span>¥ {props.minPrice} - {props.maxPrice}</span>
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

const DetailActions = (props) => {
  return (
    <div className="detail-actions clearfix">
      <div className="detail-action-aside" onClick={() => {
        window.location = '/cart'
      }}>
        <div className="detail-action-cart has-item">
          <Icon name="shopping-cart" className="detail-action-cart-icon"/>
          <span className="detail-action-cart-text">购物车</span>
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
      description: null,
      specifications: [],
      title: null,
      shippingFree:false,
      loading: true,
      showPick: false,
      currentSpecValudId: null,
      currentAmount: 1,
      salesAmount:0,
      pickType: 'add_to_cart', // 'add_to_cart' || 'buy_now'
      salesInfo:[],
      salesInfoShow:false
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
    req
      .get('/uclee-user-web/productDetail?productId=' + this.props.params.id)
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          loading: false,
          ...res.body
        })
        req
        .get('/uclee-user-web/productDetailImg?productId=' + this.props.params.id)
        .end((err, res) => {
          if (err) {
            return err
          }

          this.setState({
            description: res.body
          })
        })
      })
      
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
          console.log(item)
          // calc the map BTW
          this.specPrePriceMap[`_${item.valueId}`] = item.prePrice
          this.specPreValueMap[`_${item.valueId}`] = item.value

          return item.prePrice
        })
        this.minPrice = Math.min.apply(null, prices)
        this.maxPrice = Math.max.apply(null, prices)
        this.preMinPrice = Math.min.apply(null, prePrices)
        this.preMaxPrice = Math.max.apply(null, prePrices)
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
              shippingFree={this.state.shippingFree}
              salesAmount={this.state.salesAmount}
              minPrice={this.minPrice}
              maxPrice={this.maxPrice}
              preMinPrice={this.preMinPrice}
              preMaxPrice={this.preMaxPrice}/>
              {
                this.state.salesInfo.length>=1?
                <DetailSales salesInfo={this.state.salesInfo} salesInfoShow = {this.state.salesInfoShow} salesInfoShowClick={this.salesInfoShowClick}/>
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
            <DetailRich description={this.state.description}/>
            <DetailActions onClickCart={this._clickCartAdd} onClickBuy={this._clickBuyNow}/>
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
        productId: parseInt(this.props.params.id, 10),
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
}

export default Detail