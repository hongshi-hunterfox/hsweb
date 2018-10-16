/* global WeixinJSBridge */

import './recharge.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
var RechargeUtil = require('../utils/RechargeUtil.js');
import req from 'superagent'

class PaymentMethod  extends React.Component {
	constructor(props) {
		super(props)
    this.state = {
	    type: 1
    }
	}

	render(){
		var items = this.props.data.payment.map((item, index) => {
				return (
					<div className={'payment-info-method ' + (this.state.type===item.paymentId?'active':'')} key={index} onClick={this._paymentOnChange.bind(this,item.paymentId)}>
						{
							 item.strategyClassName==='WCJSAPIPaymentStrategy'?
							 	<img src="/images/payment/WC.png" className='payment-info-method-image' alt=""/>
							 	:
							 	item.strategyClassName==='AlipayPaymentStrategy'?
							 		<img src="/images/payment/alipay.jpg" className='payment-info-method-image' alt=""/>
							 		:null
						}
						<span className='payment-info-method-text'>{item.paymentName}</span>
						<span className="fa fa-check icon-check"></span>
					</div>
				);
		});
		return (
			<div className='payment-info'>
				{items}
			</div>
		);
	}
	_paymentOnChange = (paymentId) => {
		this.setState({
			type:paymentId
		});
		this.props._paymentOnChange(paymentId);
	}
}

class Recharge extends React.Component {

	constructor(props) {
	    super(props)
	    this.state = {
		    loading: false,
			payment:[],
			rechargeMoney:0,
			rewards:0,
			paymentId:1,
			isWC:true,
			html:'',
			config:[],
			hongShiVip:{},
			type:1,
			voucherText:'',
			extraData:{},
			inTime:true
	    }
	  }

	componentDidMount() {
		RechargeUtil.getData(this.props.location.query, function(res) {
			this.setState({
				payment:res.payment,
				isWC:res.isWC,
				config:res.config,
				hongShiVip:res.hongShiVip,
				extraData:res.extraData
			});
		}.bind(this));
	}

	render() {
		var money = this.state.config.map((item, index) => {
				return (
					<div className={'payment-money-item' + (this.state.rechargeMoney===item.money && this.state.rewards === item.rewards ?' active':'')} onClick={this._clickHandler.bind(this,item.money,item.rewards,item.voucherText,item.type,item.inTime)}>{item.money}</div>
				);
		});
		console.log(this.state.extraData);
		console.log(this.state.rechargeMoney);
		console.log(this.state.extraData[this.state.rechargeMoney]);
		return (
			<DocumentTitle title="充值">
				<div className='payment'>
					<div className='payment-balance'>
						<div className='payment-balance-number'>卡号：<span className='gold'>{this.state.hongShiVip.cVipCode}</span></div>
						<div className='payment-balance-money'>余额：<span className='red'>{this.state.hongShiVip.balance}</span></div>
					</div>
					<div className='payment-money'>
						{money}

					</div>
					{this.state.rewards!==0&&this.state.inTime?<div className='payment-note'>已选充值优惠： 充值<span className='gold'>{this.state.rechargeMoney}</span>，赠送<span className='gold'>{this.state.rewards}</span></div>:null}
					{this.state.rewards!==0&&this.state.inTime?<div className='payment-info'>
						实际到账: <span className='gold'>{this.state.rechargeMoney+this.state.rewards}</span>，应付金额：<span className='gold'>{this.state.rechargeMoney}</span>
					</div>:null}
					{
						this.state.extraData&&this.state.extraData[this.state.rechargeMoney*100]&&this.state.rechargeMoney!==0?
						<div className='payment-extra'>
						{
							<div className='note'>
								额外赠送：
							</div>
						}
						{
							this.state.extraData&&this.state.extraData[this.state.rechargeMoney*100]?this.state.extraData[this.state.rechargeMoney*100].map((item,index)=>{
								return(
									<div className='payment-extra-item'>
										{item}
									</div>
								)
							}):null
						}
						</div>
						:null
					}
					
					<PaymentMethod data={this.state} _paymentOnChange={this._paymentOnChange}/>
					<div className='payment-bottom'>
						<button type="button" className="btn btn-default payment-bottom-button" onClick={this._submitHandler}>马上支付</button>
					</div>
					{this.state.html ? <div dangerouslySetInnerHTML={this._getMarkup()}></div> : null}
				</div>
			</DocumentTitle>
		);
	}

	_clickHandler = (money,rewards,voucherText,type,inTime) =>{
			this.setState({
				rechargeMoney:money,
				rewards:rewards,
				type:type,
				inTime:inTime
			});
	}
	_getMarkup = () => {
		return {
			__html: this.state.html
		}
	}

	_getWeixinConfig = (data) => {
		if (typeof WeixinJSBridge === "undefined") {
			alert('请用微信打开链接');
		} else {
			
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest', data,
				function(res) {
					if (res.err_msg === "get_brand_wcpay_request:ok") {
						window.location = '/recharge-list';
					} else {
						console.log("支付失败");
					}
				// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
				}
			);
		}
	}

	_paymentOnChange = (paymentId) => {
		console.log(paymentId);
		this.setState({
			paymentId:paymentId
		});
	}

	_setChargeMoney = (money) => {
		this.setState({
			rechargeMoney:money
		});
		console.log(this.state.rechargeMoney);
	}

	_submitHandler = (paymentId) => {
		if(this.state.paymentId===0){
			alert("请至少选择一种支付方式");
			return;
		}
		if(this.state.rechargeMoney<=0){
			alert("充值金额需大于0");
			return;
		}
		req
        .get('/uclee-user-web/getRechargeAble?money=' + this.state.rechargeMoney+'&rewards='+this.state.rewards)
        .end((err, res) => {
          if (err) {
            return err
          }
          var data = res.body;
          if(!data.result){
          	var conf = confirm('检测到已选优惠活动已过期，是否继续充值？');
          	if(!conf){
          		return;
          	}else{
          		var data={};
				data.paymentId=this.state.paymentId;
				data.money=this.state.rechargeMoney;
				RechargeUtil.submitHandler(data, function(res) {
					console.log(res);
					if(res.result===true){
						if (res.type === 'WC') {
							if(res.result==='failed'){
								alert('网络繁忙，请稍后再试');
								return false;
							}
							res['package'] = res.prePackage;
							this._getWeixinConfig(res);
							return false;
						}else if (res.type === 'alipay') {
							if(res.isWC){
								window.location.href = '/seller/paymentAlipay?loginRequired=false&paymentSerialNum=' + res.paymentSerialNum + "&payType=" + res.payType+"&merchantCode=" + localStorage.getItem('merchantCode');
							}else{
								this.setState({
									html: res.html
								});
								setTimeout(function() {
									document.forms['alipaysubmit'].submit();
								}, 0);
							}
						}	
					}else{
						if(res.reason==='money_not_enough'){
							alert("余额不足，请选择其他支付方式");
						}else{
							alert("网络繁忙，请稍后再试");
						}
					}
					
				}.bind(this));
          	}
          }else{
          	var data={};
				data.paymentId=this.state.paymentId;
				data.money=this.state.rechargeMoney;
				RechargeUtil.submitHandler(data, function(res) {
					console.log(res);
					if(res.result===true){
						if (res.type === 'WC') {
							if(res.result==='failed'){
								alert('网络繁忙，请稍后再试');
								return false;
							}
							res['package'] = res.prePackage;
							this._getWeixinConfig(res);
							return false;
						}else if (res.type === 'alipay') {
							if(res.isWC){
								window.location.href = '/seller/paymentAlipay?loginRequired=false&paymentSerialNum=' + res.paymentSerialNum + "&payType=" + res.payType+"&merchantCode=" + localStorage.getItem('merchantCode');
							}else{
								this.setState({
									html: res.html
								});
								setTimeout(function() {
									document.forms['alipaysubmit'].submit();
								}, 0);
							}
						}	
					}else{
						if(res.reason==='money_not_enough'){
							alert("余额不足，请选择其他支付方式");
						}else{
							alert("网络繁忙，请稍后再试");
						}
					}
					
				}.bind(this));
          }
        })
		
	}
}

export default Recharge