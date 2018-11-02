/* global wx */
import './link-coupon.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import Icon from '../components/Icon'
var request = require('superagent')

// req 用于发送 AJAX 请求
import req from 'superagent'

var qq = window.qq

class LinkCoupon extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			logoUrl:'',
			brand:'',
			config:{},
			couponList:[]
		}
	}

	componentDidMount() {
		req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			this.setState({
				logoUrl:c.logoUrl,
				brand:c.brand
			})
		})
		
		req.get('/uclee-user-web/linkCouponList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			this.setState({
				couponList:c.couponList
			})
		})
		
		req.get('/uclee-backend-web/config').end((err, res) => {
	      if (err) {
	        return err
	      }
	      var data = JSON.parse(res.text)
	      this.setState({
	        config: data.config,
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
					jsApiList: ['getLocation','onMenuShareTimeline','onMenuShareAppMessage']
				})

				wx.ready(() => {
					wx.getLocation({
						type: 'gcj02',
						success: res => {
							var latitude = res.latitude
							var longitude = res.longitude
							console.log("latitude: " + latitude)
							console.log("longitude: " + longitude)
							this.setState({
								userLocation: {
									latitude: latitude,
									longitude: longitude
								}
							})
						}
					})
					wx.onMenuShareAppMessage({
					title: '好店推荐: '+this.state.brand, // 分享标题
					desc: this.state.brand+'给你送优惠券了,快来看看吧.', // 分享描述
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				})
				   wx.onMenuShareTimeline({
					title: this.state.brand+'给你送优惠券了,快来看看吧.', // 分享标题
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				   })
				})
			})
	}
	render() {
		var coupons = this.state.couponList.map((item, index) => {
            return(
            	<div  key={index}>
            		<div style={{marginTop:'20px'}} />
            		{item.name === "已抢光！" 
            			?
            			<button type="button" className="btn btn-info btn-lg btn-block" disabled="disabled">
	            			{item.name}
	            		</button>
	            		:
	            		<button type="button" className="btn btn-info btn-lg btn-block"  onClick={()=>{
	            			req.get('/uclee-user-web/receiveCoupon')
	            			.query({
								voucher: item.voucher,
								name: item.name,
							})
	            			.end((err, res) => {
								if (err) {
									return err
								}
								var c = JSON.parse(res.text)
								this.setState({
									success: c.success,
									vip: c.vip,
									log: c.log
								})
								if(this.state.vip === false){
									alert("只有会员用户才能参与活动，请先绑定会员卡！");
									window.location='/member-card?merchantCode=' + localStorage.getItem('merchantCode');
									return;
								}
								
								if(this.state.log === false){
									alert("领取失败，每种券每天限领一次！");
									window.location.reload();
									return;
								}
								
								if(this.state.success === true){
									alert("领取成功，优惠券已发放到你的会员卡！");
									window.location.reload();
									return;
								}else{
									alert("领取失败，优惠券已经被抢光了！");
									window.location.reload();
									return;
								}
							})
	            		}}>
	            			{item.name}
	            		</button>
	            	}

	            </div>
            );
        });
				
		
		return (
			<DocumentTitle title="领取优惠券">
				<div>
					<div className="link-coupon">
						<div className="link-coupon-logo">
							<img src={this.state.logoUrl} className="link-coupon-logo-image" alt=""/>
							<div>{this.state.brand}</div>
						</div>
						<div className="link-coupon-select">领取礼券</div>
						{coupons}
					</div>
					<div style={{marginTop:'50px'}} />
					<div className="check-table-on">
        				<span>领取规则说明</span>
    					<div className="check-table-dui">	
								{this.state.config.linkCouponText}
						</div>
        			</div>
					<div className="tail">
						广州洪石软件提供技术支持
					</div>
				</div>
			</DocumentTitle>
		)
	}
}

export default LinkCoupon
