/* global wx */
import './store-list.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import Icon from '../components/Icon'
var request = require('superagent')

// req 用于发送 AJAX 请求
import req from 'superagent'

var qq = window.qq

class StoreList extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			stores: [],
			logoUrl:'',
			brand:'',
			userLocation: {
				latitude: null,
				longitude: null
			}
		}
		this.lat = 23.12463
		this.lng = 113.36199
	}

	componentDidMount() {
		req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				stores: c.storeList,
				logoUrl:c.logoUrl,
				brand:c.brand
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
					desc: '发现了一家好店: '+this.state.brand+',快来逛逛吧.', // 分享描述
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				})
				   wx.onMenuShareTimeline({
					title: '发现了一家好店: '+this.state.brand+',快来逛逛吧.', // 分享标题
					link: window.location.href, // 分享链接
					imgUrl: this.state.logoUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				   })
				})
			})
	}
	render() {
		var preItems=this.state.stores.map((item, index) => {
			console.log("latitude1: " + this.state.userLocation.latitude)
			console.log("longitude1: " + this.state.userLocation.longitude)
			var distance = Math.round(
				qq.maps.geometry.spherical.computeDistanceBetween(
					new qq.maps.LatLng(
						this.state.userLocation.latitude,
						this.state.userLocation.longitude
					),
					new qq.maps.LatLng(item.latitude, item.longitude)
				) / 100
			)/10
			item.distance=distance
			return item
		})
		preItems.sort((a,b)=>{
			var x = a.distance; var y = b.distance
	        return ((x < y) ? -1 : ((x > y) ? 1 : 0))
		})

		var items = preItems.map((item, index) => {
			var distance = item.distance
			return (
				<div className="store-list" key={index}>
					<div
						className="store-list-item"
						onClick={this._pick.bind(
							this,
							item.storeName,
							item.storeId,
							item.latitude,
							item.longitude,
							item.supportDeliver,
						)}
					>
						<div className="store-list-item-top">
							<div className="name">
								{item.storeName}({item.supportDeliver === 'yes' ? '支持配送' : '不支持配送'})
							</div>
							<div className="distance">
								{distance > 10 ? '>10' : distance}
								km
							</div>
						</div>
						<div className="store-list-item-bottom">
							<div className="addr">
								<font color="#808080">{item.province}{item.city}{item.region}{item.addrDetail}</font>
							</div>
							<div>
								<font color="#808080">
									<span className="fa fa-chevron-right right"/>
								</font>
							</div>
						</div>
					</div>
				</div>
			)
		})
		return (
			<DocumentTitle title="选择店铺">
				<div>
					<div className="store">
						<div className="store-logo">
							<img src={this.state.logoUrl} className="store-logo-image" alt=""/>
							<div className="store-logo-text">{this.state.brand}</div>
						</div>
						<div className="store-select">请选择要进入的店：</div>
						{items}
						<div className="bottom-text">
							没有更多店铺啦~~~
						</div>
						
					</div>
					<div className="tail">
						广州洪石软件提供技术支持
					</div>
				</div>
			</DocumentTitle>
		)
	}
	_pick = (storeName, storeId, latitude, longitude) => {
		localStorage.setItem('storeName', storeName)
		localStorage.setItem('storeId', storeId)
		localStorage.setItem('latitude', latitude)
		localStorage.setItem('longitude', longitude)
		window.location = localStorage.getItem('store_id_prev_href')
	}
}

export default StoreList
