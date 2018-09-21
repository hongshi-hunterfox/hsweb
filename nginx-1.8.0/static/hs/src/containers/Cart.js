import React from 'react'
import DocumentTitle from 'react-document-title'
import Loading from '../components/Loading'
import './cart.css'
import req from 'superagent'
import CheckBox from '../components/CheckBox'
import Counter from '../components/Counter'
import Big from 'big.js'
var myDate = new Date()
var Date1 = new Date(myDate).getTime()

const CartEmpty = props => {
  return (
    <div className="cart-empty">
      <h4>购物车为空</h4>
      <a href="/">去逛逛</a>
    </div>
  )
}

class Cart extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      loading: true,
      list: [],
      editMode: false,

    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/cart')
      .query({
        storeId: localStorage.getItem('storeId'),
        t: new Date().getTime()
      })
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          loading: false,
          list: res.body.map(item => {
            return {
              ...item,
              checked: false
            }
          })
        })
      })
  }

  render() {
    var list = this.state.list.filter(item => {
      return !item.isDisabled
    })

    var dList = this.state.list.filter(item => {
      return item.isDisabled
    })
    var checkAll = list.every(item => {
      return item.checked
    })
    var totalPrice = Big(0)
    list.forEach(item => {
      if (item.checked) {
        totalPrice =  
        	(Date1<item.startTime||Date1>item.endTime||item.promotion===null ?
        		(
        			localStorage.getItem('bargainPrice') ===null
        			? 
        			totalPrice.add(Big(item.money).times(Big(item.amount)))
        			:
        			totalPrice.add(Big(localStorage.getItem('bargainPrice')).times(Big(item.amount)))
        		)
        	:
        	totalPrice.add(Big(item.promotion).times(Big(item.amount)))
        	)
      }
    })
    


    return (
      <DocumentTitle title="购物车">
        <div>
          {this.state.loading
            ? <Loading />
            : list.length
                ? <div
                    className={
                      'cart' + (this.state.editMode ? ' in-edit-mode' : '')
                    }
                  >
                    <div className="cart-store">
                      <CheckBox
                        checked={checkAll}
                        text="全选"
                        onChange={this._changeCheckAll}
                      />
                      <span
                        className="cart-edit-toggle"
                        onClick={this._toggleEdit}
                      >
                        {this.state.editMode ? '完成' : '编辑'}
                      </span>
                    </div>
                    <div className="cart-items">
                      {list.map((item, index) => {
                        return (
                          <div className="cart-item clearfix" key={index}>
                            <div className="cart-item-check">
                              <CheckBox
                                className="cart-item-checkbox"
                                checked={item.checked}
                                onChange={e => {
                                  this._toggleItem(item.cartId, e)
                                }}
                              />
                            </div>
                            <div className="cart-item-img"> 
                                <img
                                  src={item.image}
                                  alt={item.title}
                                  width="80"
                                  height="80"
                                />
                            </div>
                            <div className="cart-item-info">
                              {item.activityMarkers===null&&this.state.editMode
                                ? <Counter
                                    amount={item.amount}
                                    addAmount={this._addItemAmount.bind(
                                      this,
                                      item
                                      // item.cartId
                                    )}
                                    subAmount={this._subItemAmount.bind(
                                      this,
                                      item
                                      // item.cartId
                                    )}
                                  />
                                : <div className="cart-item-title" onClick={e => {
                                  this._toggleItem(item.cartId, {
                                    target: {
                                      checked: !item.checked
                                    }
                                  })
                                }}>
                                    {item.title}
                                  </div>}
                              <div className="cart-item-spec" onClick={e => {
                                this._toggleItem(item.cartId, {
                                  target: {
                                    checked: !item.checked
                                  }
                                })
                              }}>
                                {item.specification}<br />
                                {item.paramete}：{item.csshuxing}
                              </div>
                              <div className="cart-item-price" onClick={e => {
                                this._toggleItem(item.cartId, {
                                  target: {
                                    checked: !item.checked
                                  }
                                })
                              }}> 
                    						{
            	        						Date1 < item.startTime || Date1 > item.endTime || item.promotion === null ?
                      						<div>
                      							{localStorage.getItem('bargainPrice') ===null ?	'在售价¥ ' + item.money : '购买价¥ ' + localStorage.getItem('bargainPrice')}
                     							</div>	
              	     							: 
                     							<div>
                      							{'促销价¥ ' + item.promotion}
                     							 </div>
                    						} 
                              </div>
                              {this.state.editMode
                                ? null
                                : <span className="cart-item-amount">
                                    {'x ' + item.amount}
                                  </span>}
                            </div>
                            {this.state.editMode
                              ? <div
                                  className="cart-item-delete"
                                  onClick={this._deleteItem.bind(
                                    this,
                                    item.cartId
                                  )}
                                >
                                  <span>删除</span>
                                </div>
                              : null}
                          </div>
                        )
                      })}
                    </div>
                    
                    {!this.state.loading && dList.length
            					? <div>
                					<div
                  					style={{
                    					padding: '5px 10px'
                  					}}
                					>
                  					当前门店不支持以下产品：
                					</div>
                					{dList.map((item, index) => {
                  					return (
                    					<div className="cart-item clearfix disabled" key={index}>
                      					<div className="cart-item-img">
                         	 					<img
                            					src={item.image}
                            					alt={item.title}
                           		 				width="80"
                            					height="80"
                          					/>
                      					</div>
                      					<div className="cart-item-info">
                        					<div className="cart-item-title">
                          					{item.title}
                        					</div>
                        					<div className="cart-item-spec">
                          					{item.specification}<br />
                          					{item.paramete}：{item.csshuxing}
                        					</div>
                        					<div className="cart-item-price">
                            			{
            	        							((Date1)<(item.startTime)||(Date1)>(item.endTime)||(item.promotion)===null) ?
                      							<div>
                      								{'在售价¥ ' + item.money}
                     								</div>	
              	     								: 
                     								<div>
                      								{'促销价¥ ' + item.promotion}
                      							</div>
                    							} 
                        					</div>
                      					</div>
                    					</div>
                  					)
                					})}
              					</div>
            				: null}
                    
                    <div className="cart-settle">
                      <div className="cart-settle-price">  
                      {'合计：¥' + (totalPrice.toString())}
                      </div>
                      <div className="cart-settle-info">不含运费</div>
                      <div className="cart-settle-go left" onClick={()=>{window.location='/'}}>
                        <span>再逛逛</span>
                      </div>
                      <div className="cart-settle-go" onClick={this._go}>
                        <span>结算</span>
                      </div>
                    </div>
                  </div>
                : <CartEmpty />}
          			
          			    
        </div>
      </DocumentTitle>
    )
  }

  _changeCheckAll = e => {
    var nList = this.state.list.slice()
    nList = nList.map(item => {
      item.checked = e.target.checked
      return item
    })
    this.setState({
      list: nList
    })
  }

  _toggleEdit = () => {
    this.setState(prevState => {
      return {
        editMode: !prevState.editMode
      }
    })
  }

  _toggleItem = (id, e) => {
    var nList = this.state.list.slice()
    nList = nList.map(item => {
      if (item.cartId === id) {
        item.checked = e.target.checked
      }
      return item
    })
    this.setState({
      list: nList
    })
  }

  _deleteItem = id => {
    var nList = this.state.list.slice()
    var a = []
    nList.forEach(item => {
      if (item.cartId !== id) {
        a.push(item)
      }
    })

    this.setState({
      list: a
    })
    req.get('/uclee-user-web/cardDelHandler?cartId=' + id).end((err, res) => {
      if (err) {
        return err
      }
      var resJson = JSON.parse(res.text)
      if (resJson.result === true) {
      } else {
        alert('网络繁忙，请稍后重试')
      }
    })
  }

  _addItemAmount = c => {
    var nList = this.state.list.slice()
    nList = nList.map(item => {
      if (item.cartId === c.cartId) {
        item.amount += 1
        req
          .get(
            '/uclee-user-web/cardAddHandler?cartId=' +
              c.cartId +
              '&amount=' +
              item.amount
          )
          .end((err, res) => {
            if (err) {
              return err
            }
            var resJson = JSON.parse(res.text)
            if (resJson.result === true) {
            } else {
              alert('网络繁忙，请稍后重试')
            }
          })
      }
      return item
    })
    this.setState({
      list: nList
    })
  }

  _subItemAmount = c => {
    if (parseInt(c.amount, 10) === 1) {
      return
    }
    var nList = this.state.list.slice()
    nList = nList.map(item => {
      if (item.cartId === c.cartId) {
        item.amount -= 1
        req
          .get(
            '/uclee-user-web/cardAddHandler?cartId=' +
              c.cartId +
              '&amount=' +
              item.amount
          )
          .end((err, res) => {
            if (err) {
              return err
            }
            var resJson = JSON.parse(res.text)
            if (resJson.result === true) {
            } else {
              alert('网络繁忙，请稍后重试')
            }
          })
      }
      return item
    })
    this.setState({
      list: nList
    })
  }

  _go = () => {

    
  var chekedItem = this.state.list.filter(item => {
        return item.checked
      })

      if (!chekedItem.length) {
        alert('请选择要结算的商品')
        return
      }

      var checkedCartIds = chekedItem.map(item => {
        return item.cartId
      })

      var data = {};
      data.cartIds = checkedCartIds;
      req.post('/uclee-user-web/stockCheck').send(data).end((err, res) => {
        if (err) {
          return err
        }
        var resJson = JSON.parse(res.text)
        if (!resJson.result) {
          alert(resJson.reason);
          return ;
        }else{
          sessionStorage.setItem('cart_item_ids', JSON.stringify(checkedCartIds))
          sessionStorage.setItem('isFromCart', 1)
          sessionStorage.removeItem('isSelfPick');
          window.location = '/order'
        }
      })
    
    
  }
}

export default Cart
