require('./payment-alipay.css');

import React from "react"
import DocumentTitle from "react-document-title"
var PaymentAlipayUtil = require('../utils/PaymentAlipayUtil.js');

class PaymentAlipay extends React.Component{

	constructor(props) {
	    super(props)
	    this.state = {
	      timeCountDown:0,
			paymentSerialNum:this.props.location.query.paymentSerialNum,
			paymentOrder:{},
			isWC:true,
			html:''
	    }
	  }

	componentDidMount() {
		PaymentAlipayUtil.getData(this.props.location.query, function(res) {
			if(res.paymentOrder===null||res.paymentOrder.isActive===false){
				alert("支付单过期或已失效");
				return ;
			}
			this.setState({
				timeCountDown:res.timeCountDown,
				paymentOrder:res.paymentOrder,
				isWC:res.isWC
			});
			
			if(!res.isWC){
				var data = {
					paymentSerialNum:this.props.location.query.paymentSerialNum,
					payType:this.props.location.query.payType
				};
				PaymentAlipayUtil.submitHandler(data, function(res) {
					this.setState({
	                    	html: res.html,
	                    	isWC:res.isWC
	                  });
					setTimeout(function() {document.forms['alipaysubmit'].submit();}, 0);
				}.bind(this));
			}
			this._getResult();
		}.bind(this));
		
	}

	render() {

		return (
			<DocumentTitle title="支付宝支付">
				<div className='payment-alipay'>
					<div className="payment-alipay-other">
						{this.state.isWC?"请点击右上角选择用其他浏览器打开支付宝支付":"请稍后..."}
					</div>
					{this.state.isWC?<div className="payment-alipay-detail">
					
						<div className="payment-alipay-detail-row">
							<span className="payment-alipay-detail-row-name">
							订单号:
							</span>
							<span className="payment-alipay-detail-row-info">
							{this.props.location.query.paymentSerialNum}
							</span>
						</div>
						<div className="payment-alipay-detail-row">
							<span className="payment-alipay-detail-row-name">
							支付金额:
							</span>
							<span className="payment-alipay-detail-row-info">
							{this.state.paymentOrder.money}元
							</span>
						</div>
					</div>:null}
					
					{this.state.html ? <div dangerouslySetInnerHTML={this._getMarkup()}></div> : null}
				</div>
			</DocumentTitle>
		);
	}
	_getResult = () => {
		var resultHandler = (res) => {
			
			if (!res.isPaid) {
				PaymentAlipayUtil.getResult(this.props.location.query, resultHandler)
				return
			}else{
				console.log(this.props.location.query.payType);
				if(this.props.location.query.payType===1){
					window.location = "/order-list";
				}else{
					window.location = "/recharge-list";
				}
			}
		}
		
		PaymentAlipayUtil.getResult(this.props.location.query, resultHandler)
	}
		_getMarkup = () => {
		return {
			__html: this.state.html
		}
	}
};

export default PaymentAlipay
