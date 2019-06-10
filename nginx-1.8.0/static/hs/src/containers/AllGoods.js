import React from 'react'
import DocumentTitle from 'react-document-title'
import './shake-monitor.css'
require('./all-goods.css');
// import req from 'superagent'
import _ from 'lodash'
import req from 'superagent'
import Icon from '../components/Icon'
import Scrollbar from 'react-smooth-scrollbar'
import 'smooth-scrollbar/dist/smooth-scrollbar.css'
import Big from 'big.js'
import Counter from '../components/Counter'


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
	              <div>
	             		¥{props.hsPirce}
	              </div>	
              </div>
          	</div>
        	</div>
          <div className="detail-picker-spec">
            <div className="detail-picker-spec-name">规格：</div>
            <div className="detail-picker-spec-values clearfix">
              {
                props.spec.map((item) => {
                  return (
                    <div
                      key={item.id}
                      onClick={() => {
                        props.pickSpec(item.id)

                      }}
                      className={'detail-picker-spec-value' + (item.id === props.specid ? ' active' : '')}>
                      {item.name}
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
            <div className="detail-picker-next"
                 onClick={() => {
                    // 加入购物车
								    req
								      .post('/uclee-user-web/addGoodsCart')
								      .send({
								        amount: props.currentAmount,
								        goodsId: props.goodsId,
								        specId: props.specid,        
								      }) 
								      .end((err, res) => {
								        if (err) {
								          return err
								        }
								        var result = JSON.parse(res.text);
								        if(result>0){
								        	alert("加入购物车成功!")
								        	window.location.reload();
								        }else{
								        	alert("加入购物车失败,请刷新重试!")
								        }
								        
								      })
								    
								    return
								
								    // 立即购买
								                      }}
								            >
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


const CartDetail = (props) => {
  if (props.showCart) {
      return (
        <div className="detail-picker-overlay" onClick={props.closeCart}>
          <div className="detail-picker" onClick={(e) => {e.stopPropagation()}}>
          	<span className="detail-picker-close" onClick={props.closeCart}><Icon name="times-circle"/></span>
          	<div className="goodscart-items">
                      {props.list.map((item, index) => {
                        return (
                          <div className="goods-item clearfix" key={index}>
                            <div className="goods-item-img"> 
                                <img
                                  src={item.goodsImg}
                                  alt={item.name}
                                  width="80"
                                  height="80"
                                />
                            </div>
                            <div className="goods-item-info">
                            		<div className="goods-item-title">
                          					&nbsp;&nbsp;{item.name}  
                        				</div>
                            		&nbsp;&nbsp;&nbsp;数量 x{item.amount}<br/>
                              	<div className="goods-item-price"> 
                              		¥{item.hsPrice}<br/>
                    							{item.vipPrice !== null ? '会员价¥ '+item.vipPrice : null}
                              	</div>
                            </div>
													  <button className="btn btn-danger" style={{float:'right'}} onClick={()=>{
													  	req
								      				.get('/uclee-user-web/removeCart?cartId='+item.id)
												      .end((err, res) => {
												        if (err) {
												          return err
												        }
												        var result = JSON.parse(res.text);
												        if(result>0){
												        	alert("删除购物车成功!")
												        	window.location.reload();
												        }else{
												        	alert("删除购物车失败,请刷新重试!")
												        }
													  })}}
													  >
													  	删除<
													  /button>
                          </div>
                        )
                      })}
                    </div>
          </div>
        </div>
    )
}
return null
}


class AllGoods extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      cat: [],
      logoUrl:'',
			brand:'',
			goodslist: [],
			total:null,
			showPick: false,
			showCart: false,
			goods:[],
			spec:[],
			specid:null,
			currentAmount: 1,
			pickType: 'add_to_cart', // 'add_to_cart' || 'buy_now'
			list:[],
			catName:'',
    }
    this.specPriceMap = {}
    this.specValueMap = {}
    this.specVipPriceMap = {}
    this.specVipValueMap = {}
  }

  componentDidMount() {
  	req.get('/uclee-product-web/getCategory').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        cat: data.cat,
        catName:'默认',
      })
    })
		req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				logoUrl:c.logoUrl,
				brand:c.brand,
				isshow: this.props.isshow,
			})
		})
	req.get('/uclee-user-web/getgoodslist').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        goodslist: data.goodslist,
        total: data.total
      })
    })
	req
      .get('/uclee-user-web/getCartList')
      .end((err, res) => {
        if (err) {
          return err
        }
        this.setState({
          list: res.body.list
        })
      })	
  }
  
  _setGoodsList=(e)=>{
  	
  	req.get('/uclee-user-web/getgoodslist?goodscategory='+e.categoryId).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        goodslist: data.goodslist,
        total: data.total,
        catName: e.category,
      })
    })
  }
  
  
  _showCart = () => {
  	this.setState({
  		showCart: true,
  	})
  }
  
  _closeCart = () => {
    this.setState({
      showCart: false
    })
  }
  
  _showPick = (id) => {
  	req
      .get('/uclee-user-web/getgoodsdetail?id=' + id)
      .end((err, res) => {
        if (err) {
          return err
        }
        this.setState({
          showPick: true,
          goodsId: id,
          goodsimg:res.body.goods.goodsimg,
          spec:res.body.spec,
          specid:res.body.specid,
        })
      })
  }
  
 _closePick = () => {
    this.setState({
      showPick: false
    })
  }
 	
 	_pickSpec = (id) => {
    this.setState((prevState) => {
      return {
        specid: id,
        currentAmount: prevState.currentAmount || 1
      }
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
  
  _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

   	
  render() {
  	if (this.state.spec.length) {
  		 var prices = this.state.spec.map((item) => {
          // calc the map BTW
          this.specPriceMap[`_${item.id}`] = item.hsPrice
          this.specValueMap[`_${item.id}`] = item.name

          return item.hsPrice
        })
  		 var vipPrice = this.state.spec.map((item) =>{
        	this.specVipPriceMap[`_${item.id}`] = item.vipPrice
        	this.specVipValueMap[`_${item.id}`] = item.name
        	return item.vipPrice
        })
  	}
  	var hsPirce='-';
    if (this.specPriceMap[`_${this.state.specid}`]) {
      hsPirce = new Big(this.specPriceMap[`_${this.state.specid}`]).toString()
    }
    var vipPrice='-'
    if (this.specVipPriceMap[`_${this.state.specid}`]) {
      vipPrice = new Big(this.specVipPriceMap[`_${this.state.specid}`]).toString()
    }
  	//类别列表
    var list = this.state.cat.map((item,index)=>{
        return(
         	<li className="list-group-item" onClick={this._setGoodsList.bind(this,item)}>
         		{item.category}
         	</li>
        );  
    })

    return (
      <DocumentTitle title="选择产品">
      	<div>
      		<div className="banner">
      		 	<div className="store-logo">
							<img src={this.state.logoUrl} className="store-logo-image" alt=""/>
							<div className="store-logo-text">{this.state.brand}</div>
						</div>
      		</div>
      		<div  style={{width:'356px'}}>
      			<h4>类别:{this.state.catName}</h4>
		        <div className="cat" style={{float:'left'}}>
						  <ul className="list-group">
						  	<li className="list-group-item" onClick={() => {window.location.reload()}}>
	        				全部商品
	        			</li>
						  	{list}
						  </ul>
		        </div>
		        <div style={{width:'70%',float:'left'}}>
			        {this.state.goodslist.map((item, index) => {
		                        return (
		                          <div className="goods-item clearfix" key={index}>
		                            <div className="goods-item-img"> 
		                                <img
		                                  src={item.goodsimg}
		                                  alt={item.goodsname}
		                                  width="90"
		                                  height="90"
		                                />
		                            </div>
		                            <div className="goods-item-info">
		                              {item.goodsname}
		                            </div>
		                            <div style={{width:'100%'}}>
				                            <div className="goods-item-price"> 
				                    					¥{item.hsPrice} 起
				                            </div>
				                            <div className="goods-item-spec" onClick={this._showPick.bind(this,item.id)}> 
																			<li className="glyphicon glyphicon-plus-sign btn-lg" aria-hidden="true"/>		 
				                            </div>
			                          </div>
		                          </div>
		                        )
		                      })}
		        </div>
	        </div>
          <div className="goods-settle">
          	<div onClick={this._showCart}>
              <div className="goods-settle-price">  
              {'合计：¥'+this.state.total}
              </div>
              <div className="goods-settle-info">不含外带费用</div>
            </div>
              <div className="goods-settle-go" onClick={this._go}>
                <span>立即结算</span>
              </div>
          </div>
	      <DetailPicker
              showPick={this.state.showPick}
              closePick={this._closePick}
              image={this.state.goodsimg}
              spec={this.state.spec}
              specid={this.state.specid}
              pickSpec={this._pickSpec}
              hsPirce={hsPirce}
              vipPrice={vipPrice}
              subAmount={this._subAmount}
              addAmount={this._addAmount}
              currentAmount={this.state.currentAmount}
              pickType={this.state.pickType}
              onClickNext={this._clickNext}
			  goodsId={this.state.goodsId}
            />
	      <CartDetail
	      	showCart={this.state.showCart}
	      	closeCart={this._closeCart}
	      	list={this.state.list}
	      	change={this._change}
	      />
        </div>  
      </DocumentTitle>
    )
  }
}

export default AllGoods
