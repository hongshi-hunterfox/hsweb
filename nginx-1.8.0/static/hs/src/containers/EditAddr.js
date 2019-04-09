import './edit-addr.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import Navi from './Navi'
var EditAddrUtil = require('../utils/EditAddrUtil.js')
//var fto = require('form_to_object')
import fto from 'form_to_object'
import ErrorMessage from './ErrorMessage'
var DataViewUtil = require('../utils/DataViewUtil.js');
var browserHistory = require('react-router').browserHistory
var geocoder,map = null;
var qq=window.qq;
var dw;
var errMap = {
	empty_name: '请输入联系人姓名',
	empty_phone: '请输入联系电话',
	wrong_phone: '请输入正确的11位数字手机号码',
	empty_state: '请选择省份',
	empty_city: '请选择城市',
	empty_region: '请选择地区',
	empty_addrDetail: '请搜索地图选择详细地址'
}

class EditAddr extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			error: '',
			deliverAddr: {},
			province: [],
			city: [],
			region: [],
			provinceId: 0,
			cityId: 0,
			regionId: 0,
			name: '',
		}
		this.lat = 22.5425
    
    	this.lng = 114.04985
	}
	componentDidMount() {
		var q = this.props.location.query
		EditAddrUtil.getData(
			q,
			function(res) {
				this.setState(res)
			}.bind(this)
		);
		window.addEventListener('message', function(event) {
	        // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
	        var loc = event.data;
	        if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
	          console.log('location'+JSON.stringify(loc.poiaddress));
	          dw = JSON.stringify(loc.poiaddress).replace("\"","").replace("\"","");
	       }
	        return dw;
	    }, false);  
	}
	
	render() {
		var province = this.state.province
			? this.state.province.map(
					function(item, index) {
						return (
							<option
								value={item.provinceId}
								key={item.provinceId}
								selected={
									this.state.deliverAddr.provinceId === item.provinceId
										? 'selected'
										: null
								}
							>
								{item.province}
							</option>
						)
					}.bind(this)
				)
			: null
		var city = this.state.city
			? this.state.city.map(
					function(item, index) {
						return (
							<option
								value={item.cityId}
								key={item.cityId}
								selected={
									this.state.deliverAddr.cityId === item.cityId
										? 'selected'
										: null
								}
							>
								{item.city}
							</option>
						)
					}.bind(this)
				)
			: null
		var region = this.state.region
			? this.state.region.map(
					function(item, index) {
						return (
							<option
								value={item.regionId}
								key={index}
								selected={
									this.state.deliverAddr.regionId === item.regionId
										? 'selected'
										: null
								}
							>
								{item.region}
							</option>
						)
					}.bind(this)
				)
			: null
			
		return (
			<DocumentTitle title="地址编辑">
				
				<div className="edit-address">
					<form
						className="form-horizontal edit-address-form"
						onSubmit={this._handleSubmit}
						ref="f"
					>
						<div className="form-group edit-address-form-group">
							<select
								className="form-control edit-address-form-control-select"
								name="provinceId"
								onFocus={this._f}
								onBlur={this._b}
								onChange={this._setCity}
								id='test'
							>
								<option value="">请选择省份</option>
								{province}
							</select>
							<select
								className="form-control edit-address-form-control-select"
								name="cityId"
								onFocus={this._f}
								onBlur={this._b}
								onChange={this._setRegion}
								id='test1'
							>
								<option value="">请选择城市</option>
								{city}
							</select>
							<select
								className="form-control edit-address-form-control-select"
								name="regionId"
								onFocus={this._f}
								onBlur={this._b}
								onChange={this._handleChange.bind(this, 'regionId')}
								id='test2'
							>
								<option value="">请选择地区</option>
								{region}
							</select>
						</div>
						<div className="form-group edit-address-form-group">
							<iframe id="mapPage" width="100%" height="350px" frameborder='0'
						    src="https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=YKKBZ-FVJWI-6SIGN-5QRD5-MV5DJ-PGFW2&referer=myapp">
							</iframe>
							<input
								className="form-control"
								type="hidden"
								name="addrDetail"
								placeholder="详细地址"
								value={dw}
								onChange={this._handleChange.bind(this, 'addrDetail')}
								onFocus={this._f}
								onBlur={this._b}
							/>
						</div>
						<div className="form-group edit-address-form-group">
							<input
								className="form-control"
								type="text"
								name="name"
								placeholder="收件人姓名"
								value={this.state.deliverAddr.name}
								onFocus={this._f}
								onBlur={this._b}
								onChange={this._handleChange.bind(this, 'name')}
							/>
						</div>
						<div className="form-group edit-address-form-group">
							<input
								className="form-control"
								type="text"
								name="phone"
								placeholder="手机号码"
								value={this.state.deliverAddr.phone}
								onFocus={this._f}
								onBlur={this._b}
								onChange={this._handleChange.bind(this, 'phone')}
							/>
						</div>
						<ErrorMessage error={this.state.error} />
						<button
							type="submit"
							className="btn btn-primary btn-block edit-address-button"
							id="hs-submit-btn"
						>
							提交
						</button>
					</form>
				</div>
			</DocumentTitle>
		)
	}
	_f = () => {
		document.getElementById('hs-navi').style.display = 'none'
		document.getElementById('hs-submit-btn').style.position = 'static'
	}

	_b = () => {
		document.getElementById('hs-navi').style.display = 'block'
		document.getElementById('hs-submit-btn').style.position = 'fixed'
	}


	_handleChange = (key, e) => {
		var deliverAddr = Object.assign({}, this.state.deliverAddr)
		deliverAddr[key] = e.target.value
		this.setState({
			deliverAddr: deliverAddr
		})
		var  myselect=document.getElementById("test");
		var index=myselect.selectedIndex ;
		var  myselect1=document.getElementById("test1");
		var index1=myselect1.selectedIndex ;
		var  myselect2=document.getElementById("test2");
		var index2=myselect2.selectedIndex ;
		var addr="中国," + myselect.options[index].text+","  + myselect1.options[index1].text+","  + myselect2.options[index2].text+","  + (deliverAddr.addrDetail || '')
		geocoder.getLocation(addr)
	}
	_setCity = e => {
		var q = {
			provinceId: e.target.value
		}
		EditAddrUtil.getCity(
			q,
			function(res) {
				this.setState({
					city: res.city,
					provinceId: q.value
				})
			}.bind(this)
		)
	}
	_setRegion = e => {
		var q = {
			cityId: e.target.value
		}
		EditAddrUtil.getRegion(
			q,
			function(res) {
				this.setState({
					region: res.region,
					cityId: q.value
				})
			}.bind(this)
		)
	}
	_handleSubmit = e => {
		e.preventDefault()
		var addrId = this.props.location.query.deliverAddrId
		console.log(addrId)
		var data = fto(e.target)
		if (addrId) {
			data.deliveraddrId = addrId
		}
		if (!data.name) {
			this.setState({
				error: errMap['empty_name']
			})
			return false
		}
		if (!data.phone) {
			this.setState({
				error: errMap['empty_phone']
			})
			return false
		}
		if (!/^1[\d]{10}$/.test(data.phone)) {
			this.setState({
				error: errMap['wrong_phone']
			})
			return false
		}
		if (!data.provinceId) {
			this.setState({
				error: errMap['empty_state']
			})
			return false
		}
		if (!data.cityId) {
			this.setState({
				error: errMap['empty_city']
			})
			return false
		}
		if (!data.regionId) {
			this.setState({
				error: errMap['empty_region']
			})
			return false
		}
		console.log(data.addrDetail)
		if (!data.addrDetail) {
			this.setState({
				error: errMap['empty_addrDetail']
			})
			return false
		}

		EditAddrUtil.addAddr(
			data,
			function(res) {
				if (res.result === 'addsuccess' || res.result === 'editsuccess') {
					if (res.result === 'addsuccess') {
						alert('添加地址成功')
					} else {
						alert('修改地址成功')
					}
					if (this.props.location.query.isFromOrder) {
						var query = this.props.location.query
						query.isFromOrder = this.props.location.query.isFromOrder
						browserHistory.replace({
							pathname: '/address',
							query: query
						})
					} else {
						browserHistory.replace({
							pathname: '/address'
						})
					}
				} else {
					alert('网络繁忙，请稍后再试')
				}
			}.bind(this)
		)
	}
}

export default EditAddr
