/* global WeixinJSBridge */
require("./payment.css")
var React = require("react")
var browserHistory = require("react-router").browserHistory
var PaymentUtil = require("../utils/PaymentUtil.js")
import DocumentTitle from "react-document-title"
import req from 'superagent'
class CountDownBlock extends React.Component {
	render() {
		return (
			<div className="payment-countdown">
				<div className="payment-countdown-title">订单已提交</div>
				<div className="payment-countdown-remind">
					支付单号：{this.props.paymentOrder.paymentSerialNum}
				</div>
				<div className="payment-countdown-remind">
					订单金额：￥{this.props.paymentOrder.money}
				</div>
			</div>
		)
	}
	_onCounwDownComplete = () => {
		alert("请及时支付")
	}
}

class PaymentMethod extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			type: ""
		}
	}
	render() {
		var items;
		
		//判断是否启用支付宝
			this.props.data.config.whetherEnableAlipay === 'yes'
			?
			items = this.props.data.payment.map((item, index) => {
				return (
					<div
						className={
							"payment-info-method " +
								(this.props.data.paymentId === item.paymentId ? "active" : "")
						}
						key={index}
						onClick={this._paymentOnChange.bind(this, item.paymentId)}
					>
						{item.strategyClassName === "WCJSAPIPaymentStrategy"
							? <img
									src="/images/payment/WC.png"
									className="payment-info-method-image"
									alt=""
								/>
							: item.strategyClassName === "AlipayPaymentStrategy"
									? <img
											src="/images/payment/alipay.jpg"
											className="payment-info-method-image"
											alt=""
										/>
									: item.strategyClassName === "MemberCardPaymentStrategy"
											? <img
													src="/images/payment/balance.jpg"
													className="payment-info-method-image"
													alt=""
												/>
											: null}
						<span className="payment-info-method-text">{item.paymentName}</span>
	
						{item.strategyClassName === "MemberCardPaymentStrategy"
							? <span>
									（余额：<span className="red">{this.props.data.balance}</span>)
								</span>
							: null}
						<span className="fa fa-check icon-check" />
					</div>
				)
			})
			:
			items = this.props.data.payment.map((item, index) => {
				return (
					<div>
						{item.paymentName !== '支付宝支付' 
							?
							<div
								className={
									"payment-info-method " +
										(this.props.data.paymentId === item.paymentId ? "active" : "")
								}
								key={index}
								onClick={this._paymentOnChange.bind(this, item.paymentId)}
							>
								{item.strategyClassName === "WCJSAPIPaymentStrategy"
									? <img
											src="/images/payment/WC.png"
											className="payment-info-method-image"
											alt=""
										/>
									: item.strategyClassName === "MemberCardPaymentStrategy"
													? <img
															src="/images/payment/balance.jpg"
															className="payment-info-method-image"
															alt=""
														/>
													: null}
								<span className="payment-info-method-text">{item.paymentName}</span>
			
								{item.strategyClassName === "MemberCardPaymentStrategy"
									? <span>
											（余额：<span className="red">{this.props.data.balance}</span>)
										</span>
									: null}
								<span className="fa fa-check icon-check" />
							</div>
							:null}
						</div>
				)
			})
			
			
		return (
			<div className="payment-info">
				{items}
				<div className="payment-info-money pull-right">
					商品合计：￥{this.props.data.paymentOrder.money}
				</div>
			</div>
		)
	}
	_paymentOnChange = paymentId => {
		this.setState({
			type: paymentId
		})
		this.props._paymentOnChange(paymentId)
	}
}

class Payment extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			payment: [],
			paymentSerialNum: this.props.location.query.paymentSerialNum,
			paymentId: 0,
			paymentOrder: {},
			isWC: true,
			html: "",
			balance: 0,
			timeCountDown: 60000,
			submitting:false,
			isWCCancel:false,
			cVipCode:null,
			cardStatus:'',
			allowPayment:true,
			config:{}
		}
	}

	componentDidMount() {
		PaymentUtil.getData(
			this.props.location.query,
			function(res) {
				this.setState({
					payment: res.payment,
					paymentOrder: res.paymentOrder,
					balance: res.balance
				})

			}.bind(this)
		);
		
		PaymentUtil.getConfig(this.props.location.query, function(res) {
			this.setState({
				config: res.config
			});
		}.bind(this));
		
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
		console.log(this.state.paymentOrder.isCompleted);
		if (!this.state.paymentOrder ||this.state.paymentOrder.isCompleted) {
			alert("支付单过期或已失效")
			return ;
		}
		if(!this.state.isWCCancel&&this.state.submitting){
			alert("请勿多次提交");
			window.location="/unpay-order-list";
		}
	}

	render() {
		return (
			<DocumentTitle title="订单支付">
				<div className="payment">
					<CountDownBlock
						timeCountDown={this.state.timeCountDown}
						paymentOrder={this.state.paymentOrder}
					/>
					<PaymentMethod
						data={this.state}
						_paymentOnChange={this._paymentOnChange}
					/>
					<div className="payment-bottom">
						<button
							type="button"
							className="btn btn-default payment-bottom-button"
							onClick={this._submitHandler} disabled={this.state.submitting}
						>
							{this.state.isPoint && this.state.paymentOrder.money === 0
								? "免费参与"
								: "马上支付"}
						</button>
					</div>
					{this.state.html
						? <div dangerouslySetInnerHTML={this._getMarkup()} />
						: null}
				</div>
			</DocumentTitle>
		)
	}
	_getMarkup = () => {
		return {
			__html: this.state.html
		}
	}
	_getWeixinConfig = data => {
		if (typeof WeixinJSBridge === "undefined") {
			alert("请用微信打开链接")
		} else {
			WeixinJSBridge.invoke("getBrandWCPayRequest", data, (res) => {
				if (res.err_msg === "get_brand_wcpay_request:ok") {
					window.location ="/order-list" 
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
	_paymentOnChange = paymentId => {
		console.log(paymentId)
		this.setState({
			paymentId: paymentId
		})
	}

	_submitHandler = paymentId => {
		this.setState({
			isWCCancel:false
		})
		if (this.state.paymentOrder.isCompleted) {
			alert("支付单过期或已失效")
			return
		}
		if (this.state.paymentId === 0) {
			alert("请至少选择一种支付方式")
			return
		}
		if (
			this.state.paymentId === 2 && this.state.cVipCode==undefined
		) {
			alert("请先绑定会员卡")
			window.location='/member-setting'
			return
		}
		if(this.state.paymentId === 2 && !this.state.allowPayment){
			alert(this.state.cardStatus)
			return
		}
		if (
			this.state.paymentId === 2 &&
			this.state.balance < this.state.paymentOrder.money
		) {
			alert("余额不足，请选择其他支付方式")
			return
		}

		this.setState({
			submitting: true
		})

		var data = {}
		data.paymentSerialNum = this.props.location.query.paymentSerialNum
		data.paymentId = this.state.paymentId
		PaymentUtil.submitHandler(
			data,
			function(res) {
				console.log(res)
				if (res.result === true) {
					if (res.type === "WC") {
						if (res.result === "failed") {
							alert("网络繁忙，请稍后再试")
							return false
						}
						res["package"] = res.prePackage
						this._getWeixinConfig(res)
						return false
					} else if (res.type === "alipay") {
						if (res.isWC) {
							window.location.href =
								"/seller/paymentAlipay?loginRequired=false&paymentSerialNum=" +
								res.paymentSerialNum + "&payType=" + res.payType +"&merchantCode=" + localStorage.getItem('merchantCode')
						} else {
							this.setState({
								html: res.html
							})
							setTimeout(function() {
								document.forms["alipaysubmit"].submit()
							}, 0)
						}
					} else {
						browserHistory.push({
							pathname: "/order-list"
						})
					}
				} else {
					if (res.reason === "money_not_enough") {
						alert("余额不足，请选择其他支付方式")
					} else if (res.reason === "noSuchOrder") {
						alert("非法支付单号")
					} else if(res.reason==="illegel"){
						alert("支付单过期或已失效")
					}else{
						alert("网络繁忙，请稍后再试")
					}
				}
			}.bind(this)
		)
	}
}

export default Payment
