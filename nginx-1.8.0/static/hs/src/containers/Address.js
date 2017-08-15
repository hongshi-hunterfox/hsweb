var Link = require('react-router').Link;
import Navi from './Navi'
var AddressUtil = require('../utils/AddressUtil.js');
var _ = require('lodash')
import './address.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
class Item extends React.Component{
	render (){
		return (
			<div className='address-info' >
				<div className='address-info-item' onClick={this._pickAddr.bind(this,this.props.item)}>
					<span className='address-info-item-name'>{this.props.item.name}</span>
					<span className='address-info-item-phone'>{this.props.item.phone}</span>
				</div>
				<div className='address-info-item' onClick={this._pickAddr.bind(this,this.props.item)}>
					<div className='address-info-item-addr'>{this.props.item.province}{this.props.item.city}{this.props.item.region}{this.props.item.addrDetail}</div>
				</div>
				<div className='address-info-option'>
					
					<div className='address-info-option-delete' onClick={this._delHandler.bind(this,this.props.item.deliveraddrId)}> 删除</div>
					{
							this.props.query.isFromOrder?
							<div onClick={this._editHandler.bind(this,'/editaddr?deliverAddrId='+ this.props.item.deliveraddrId + '&isFromOrder=' + this.props.query.isFromOrder)} className='address-info-option-edit'>
								编辑
							</div>
							:
							<div onClick={this._editHandler.bind(this,'/editaddr?deliverAddrId='+ this.props.item.deliveraddrId)}  className='address-info-option-edit'> 编辑</div>
					}
				</div>
			</div>
		);
	}
	_pickAddr = (addr) => {
		if(this.props.query.isFromOrder){
			sessionStorage.setItem('addr', JSON.stringify(addr));
			window.location='/order';
		}
	}
	_editHandler = (link) =>{
		window.location=link;
	}
	_delHandler = (aid) => {
		this.props._delHandler(aid);
	}
	_setDefault = (aid) => {
		this.props._setDefault(aid);
	}
};

class Address extends React.Component{

	constructor(props) {
		super(props)
		this.state={
			isFromOrder:false,
			addrList : []
		}
	}

	componentDidMount(){
		var q = this.props.location.query;
		AddressUtil.getData(q,function(res){
			this.setState({
				addrList:res
			});
		}.bind(this));
	}

	render() {
		var list = this.state.addrList.map(function(item,index){
			return <Item item={item} key={index} _delHandler={this._delHandler} _setDefault={this._setDefault} query={this.props.location.query}/>;
		}.bind(this));
		return (
			<DocumentTitle title="地址管理">
				<div className='col-xs-12 trim-col'>
					<div className='address'>
						{ this.state.addrList.length ? list : <div className='address-empty'>~~亲，您还没添加地址呢</div>}
						{
							this.props.location.query.isFromOrder?
							<Link to={"/editaddr?isFromOrder=" + (this.props.location.query.isFromOrder)} className='address-add'>
								新增收货地址
							</Link>
							:
							<Link to="/editaddr" className='address-add'>
								新增收货地址
							</Link>
						}
						
						<Navi query={this.props.location.query}/>
					</div>
				</div>
			</DocumentTitle>
			);
	}
	_delHandler = (aid) => {
		var conf = confirm('确定要删除该地址?');
		var q = this.props.location.query;
		if(conf){
			var data = {
				'deliveraddrId' : aid
			}
			AddressUtil.delAddr(data,function(res){
				if(res.result==='isDefault'){
					alert("不能删除默认地址");
					return false;
				}
				if(res.result==='success'){
					AddressUtil.getData(q,function(res){
						this.setState({
							addrList:res
						});
					}.bind(this));
				}else{
					alert("网络繁忙，请稍后重试");
					return false;
				}
			}.bind(this));
		}
	}
	_setDefault = (aid) => {
		var data = {
			'deliveraddrId' : aid
		}
		console.log(data);
		AddressUtil.setDefault(data,function(res){
			if(res.result==='success'){
				return true;
			}else{
				alert("网络繁忙，请稍后重试");
				return false;
			}
		})
	}
}

export default Address