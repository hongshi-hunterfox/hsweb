import './unpay-order-list.css'
import React from 'react'
import Navi from './Navi'
import DocumentTitle from 'react-document-title'
import UnpayOrderListUtil from '../utils/UnpayOrderListUtil'
import req from 'superagent'
class UnpayOrderList extends React.Component {

	constructor(props) {
	    super(props)
	    this.state = {
		    orders:[],
		    order:[]
        }
	}
	
	

	componentDidMount() {
		UnpayOrderListUtil.getData(this.props.location.query, function(res) {
			this.setState({
				orders:res.orders
			});
		}.bind(this));
	}

	render(){
		var items = this.state.orders.map((item, index) => {
			return(
				<div className="order-list" key={index}>
					<div className="order-list-top">
						<div className="order-list-top-item">
							<span className="store pull-left">店铺：{item.storeName}</span>
							<span className="status pull-right">{item.invalid !== -1 ? "待支付" : "已关闭"}</span>
						</div>
						<div className="number">
							<span >订单编号：{item.orderSerialNum}</span>
						</div>
						<div className="number">
							<span >下单时间：{item.createTimeStr}</span>
						</div>
						<div className="number">
							<span>请在30分钟内完成付款,超时订单将自动关闭</span>
						</div>
					</div>
					<OrderItem orderItems={item.items} isSelfPick={item.isSelfPick}/>
					
					<div className="order-list-bottom">
						<span className="pull-right total">运费：￥{item.shippingCost?item.shippingCost:0}</span>
					</div>
					<div className="order-list-bottom">
						<span className="pull-right total">优惠：￥{item.discount?item.discount:0}</span>
					</div>
					{
						item.cut>0?
							<div className="order-list-bottom">
								<span className="pull-right total">满减：￥{item.cut?item.cut:0}</span>
							</div>
						:null
					}
					<div className="order-list-bottom">
						<span className="pull-right total">合计：￥{item.totalPrice+item.shippingCost-item.discount-item.cut>0?(item.totalPrice+item.shippingCost-item.discount-item.cut).toFixed(2):0}</span>
					</div>
					<div className="order-list-button">
						<span className="pull-right text" onClick={this._delHandler.bind(this,item.orderSerialNum)}>删除订单</span>
						{item.invalid !== -1 
							?
							<span className="pull-right text" onClick={() => {
										if(item.invalid === -1){
											alert("订单已失效"+item.invalid)
											window.location.reload();
										}else{
											window.location='/seller/payment?paymentSerialNum=' + item.paymentSerialNum
										}
                      		}}>立即支付</span>
                      		:null
                        }
					</div>
				</div>
			);
		});
		return (
			<DocumentTitle title="我的订单">
				<div className="order" >
					<div className='order-top'>
						<span onClick={()=>{window.location='/order-list'}} className='order-top-item'>全部</span>
						<span onClick={()=>{window.location='/unpay-order-list'}} className='order-top-item active'>待支付</span>
						<span onClick={()=>{window.location='/order-list?isEnd=0'}} className={'order-top-item'}>制作配送中</span>
						<span onClick={()=>{window.location='/order-list?isEnd=1'}} className={'order-top-item'}>已完成</span>
					</div>
					{items}
					<div className="bottom-text">
						O(∩_∩)O 啊哦，没有更多订单啦~~~
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
		);
	}
	_delHandler=(orderSerialNum)=>{
		var q = {};
		var conf = confirm('确定要删除该订单?');
		q.orderSerialNum = orderSerialNum;
		if(conf){
			UnpayOrderListUtil.del(q, function(res) {
				if(res){
					alert("删除成功");
					window.location="/unpay-order-list";
				}else{
					alert("网络繁忙请稍后重试");
				}
			});
		}
	}
}

class OrderItem extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {
		    orderItems:[]
	    }
	}
	render(){
		console.log(this.props);
		var items = this.props.orderItems.map((item, index) => {
				return(
					<div className="order-list-item-info" key={index}>
						<img className="image" src={item.imageUrl} alt=""/>
						<div className="title">{item.title}</div>
						<div className="sku-info"><span className="sku">{item.value}</span><span className="count pull-right">单价：￥{item.price}</span></div>
						<div className="other"><span className="tag">{this.props.isSelfPick?'自提':'配送'}</span><span className="price pull-right">数量：x {item.amount}</span></div>
					</div>
				);
		});
		return (
			<div className="order-list-item">
				{items}
			</div>
		);
	}
	
}

export default UnpayOrderList