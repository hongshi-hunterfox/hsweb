/* global WeixinJSBridge */
require("./orderingfoodpay.css")
var React = require("react")
var browserHistory = require("react-router").browserHistory
var PaymentUtil = require("../utils/PaymentUtil.js")
import DocumentTitle from "react-document-title"
import req from 'superagent'
import Icon from '../components/Icon'
class CountDownBlock extends React.Component {
	render() {
		return (
			<div className="payment-countdowns">
				<div className="payment-countdown-reminds">
					商品合计：<span style={{float:'right'}}>￥{this.props.goodstotal}</span>
				</div>
				{this.props.disparityTotal !== 0
					?
					<div className="payment-countdown-reminds">
						餐盒费：<span style={{float:'right'}}>￥{this.props.disparityTotal}</span>
					</div>
					:
					null
				}
				<div className="payment-countdown-reminds">
					优惠金额：<span style={{float:'right'}}>￥{this.props.detailedamount}</span>
				</div>
				<div className="payment-countdown-moneys">
					订单金额：<span style={{float:'right'}}>￥{this.props.total}</span>
				</div>
			</div>
		)
	}
}

class OrderingFoodPay extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			isWC: true,
			balance: 0,
			total:null,
			type:1,
			goodstotal:null,
			detailedamount:null,
			disparityTotal:null,
			submitting:false,
			isWCCancel:false,
			storeId:this.props.location.query.storeId,
			station:this.props.location.query.station,	
			remarks:'',
			salesInfo:[],
			salesInfoShow:false,
		}
	}

	componentDidMount() {
		
		req
		  .get('/uclee-user-web/getVipInfo')
		  .end((err, res) => {
		    if (err) {
		      return err
		    }

		    if (res.text) {
		      this.setState(res.body)
		    }
		  })
		  
		  req
		  .get('/uclee-user-web/getgoodsCartTotal?type='+this.state.type)
		  .end((err, res) => {
		    if (err) {
		      return err
		    }
	        this.setState({
	      	 total: res.body.total,
	      	 goodstotal: res.body.goodstotal,
	      	 detailedamount: res.body.detailedamount,
	      	 disparityTotal:res.body.disparityTotal,
	      	 remarks:res.body.remarks,
	      	 salesInfo:res.body.salesInfo
	        })
		  })
	}
	
	_goType =(e)=>{
		req
		  .get('/uclee-user-web/getgoodsCartTotal?type='+e)
		  .end((err, res) => {
		    if (err) {
		      return err
		    }
	        this.setState({
	      	 total: res.body.total,
	      	 goodstotal: res.body.goodstotal,
	      	 detailedamount: res.body.detailedamount,
	      	 disparityTotal:res.body.disparityTotal,
	      	 type:e,
	      	 salesInfo:res.body.salesInfo
	        })
		  })
	}
	
  salesInfoShowClick=()=>{
    this.setState({
      salesInfoShow: !this.state.salesInfoShow
    })
  }
	
	_ispay = (e) => {
		if(e === 'wx'){
			console.log(this.state.total)
			this.setState({
      			isWCCancel:true
    		})
			var data = {}
			data.tardno = 'WXDC'+new Date().getTime();
			data.money=this.state.total
			req
			.post('/uclee-user-web/seller/goodsPayHandler')
			.send(data)
			.end((err, res) =>{
				if (err) {
					return err;
				}
				if (res.body.result === true) {
					if (res.body.type === "WC") {
						if (res.body.result === "failed") {
							alert("网络繁忙，请稍后再试")
							return false
						}
						res.body["package"] = res.body.prePackage
						this._getWeixinConfig(res.body)
						return false
					}
				}else{
					if (res.body.reason === "money_not_enough") {
						alert("余额不足，请选择其他支付方式")
					} else if (res.body.reason === "noSuchOrder") {
						alert("非法支付单号")
					} else if(res.body.reason==="illegel"){
						alert("支付单过期或已失效")
					}else{
						alert("网络繁忙，请稍后再试")
					}
				}
			});
			
		}else if(e === 'vip'){
			this.setState({
      			submitting:true
    		})
			var conf = confirm('是否立即支付！');
          	if(!conf){
          		this.setState({
      				submitting:false
    			})
          	    return;
         	}else{
         		if (this.state.balance < this.state.total) {
					var conf1 = confirm('余额不足，是否立即充值！');
					if(!conf1){
          				this.setState({
      						submitting:false
    					})
          	    		return;
         			}else{
         				window.location="/seller/recharge"
						return
					}
         		}
		    	var data = {}
		    	data.money=this.state.total
		    	data.storeId = this.state.storeId
		    	data.station = this.state.station
		    	data.type= this.state.type
		    	data.remarks = this.state.remarks
		    	data.singledisco = this.state.detailedamount
		   		data.boxmoney = this.state.disparityTotal
				req
				.post('/uclee-user-web/vippay')
				.send(data)
				.end((err, res) =>{
					if (err) {
						return err;
					}
					this.setState({
                    	wxdcCode:res.body.wxdcCode
              		})
					if(res.body !== null){
						browserHistory.push({
				            pathname: '/pay-success',
				            query: {
				                wxdcCode:this.state.wxdcCode,
				                total:this.state.total,
				                storeId:this.state.storeId,
				                station:this.state.station
				            },
				      })
					}else{
						alert("网络繁忙,请刷新重试")
						window.location.reload();
					}
				});
			}
		}		
	}

	render() {
		return (
			<DocumentTitle title="订单支付">
				<div className="payments">
					<div className="swiper-container">
				    	<ul claclassNamess="swiper-container-ul">
				      		<li className="swiper-container-ul-li activess" onClick={this._goType.bind(this,1)}>堂食</li>
				      		<li className="swiper-container-ul-li" onClick={this._goType.bind(this,2)}>打包</li>
				    	</ul>
			    	</div>
					<CountDownBlock
						detailedamount={this.state.detailedamount}
						goodstotal={this.state.goodstotal}
						disparityTotal={this.state.disparityTotal}
						total={this.state.total}
					/>
					<div className="foodpay-detail-sales">							
				        {
				          this.state.salesInfo.length>=1?
				          <div onClick={this.salesInfoShowClick} className='detail-sales-top'>
				            <span className='foodpay-detail-sales-tag'>
				              优惠
				                
				            </span>
				            <span className='foodpay-detail-sales-text'>
				                  {!this.state.salesInfoShow?this.state.salesInfo[0]+'...':null}
				              </span>
				              <Icon className="foodpay-detail-sales-icon" name={this.state.salesInfoShow?'chevron-down':'chevron-right'} />
				          </div>
				          :null
				        }							        
				      <div className={'foodpay-detail-sales-info ' +(!this.state.salesInfoShow?'none':'')}>
				        {this.state.salesInfo.map((item,index)=>{
				          return(
				            <div className='foodpay-detail-sales-item' key={index}>
				              {item}
				            </div>
				          )
							})}
						</div>
							      
					</div>
					<div className="payment-infos">
						<div className="payment-info-methods">
							<button className="btn btn-default btn-lg btn-block" onClick={this._ispay.bind(this,'wx')} 
								disabled={this.state.isWCCancel}>
								<img
									src="/images/payment/WC.png"
									className="payment-info-method-images"
									alt=""
								/>
								<span className="payment-info-method-texts">微信支付</span>
							</button>
							{this.props.data !== null && this.state.balance !== null
								?
								<button className="btn btn-default btn-lg btn-block" onClick={this._ispay.bind(this,'vip')}
								 disabled={this.state.submitting}>
									<img
										src="/images/payment/balance.jpg"
										className="payment-info-method-images"
										alt=""
									/>
									<span className="payment-info-method-texts">会员卡支付</span>
									<span className="payment-info-method-texts">
										(余额：<span className="red">{this.state.balance}</span>)
									</span>
								</button>
								:null
							}
						</div>
					</div>
				</div>
			</DocumentTitle>
		)
	}

	_getWeixinConfig = datas => {
		if (typeof WeixinJSBridge === "undefined") {
			alert("请用微信打开链接")
		} else {
			WeixinJSBridge.invoke("getBrandWCPayRequest", datas, (res) => {
				if (res.err_msg === "get_brand_wcpay_request:ok") {
					
					var data = {}
			    	data.money=this.state.total
			    	data.storeId = this.state.storeId
			    	data.station = this.state.station
			    	data.type= this.state.type
			    	data.remarks = this.state.remarks
			    	data.singledisco = this.state.detailedamount
			   		data.boxmoney = this.state.disparityTotal
			   		data.tardno = datas.paymentSerialNum
					req
					.post('/uclee-user-web/vippay')
					.send(data)
					.end((err, res) =>{
						if (err) {
							return err;
						}
						this.setState({
	                    	wxdcCode:res.body.wxdcCode
	              		})
						if(res.body !== null){
							browserHistory.push({
					            pathname: '/pay-success',
					            query: {
					                wxdcCode:this.state.wxdcCode,
					                total:this.state.total,
					                storeId:this.state.storeId,
					                station:this.state.station
					            },
					      })
						}else{
							alert("网络繁忙,请刷新重试")
							window.location.reload();
						}
					})
					
				}else if(res.err_msg === "get_brand_wcpay_request:cancel"){
					this.setState({
						submitting:false,
						isWCCancel:true
					})
				}else {
					this.setState({
						submitting:false,
						isWCCancel:true
					})
					alert("网络繁忙，请尝试其他支付方式")
				}
				// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
			})
		}
	}
}

export default OrderingFoodPay