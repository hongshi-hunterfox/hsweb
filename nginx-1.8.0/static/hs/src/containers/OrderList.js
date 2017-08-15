import "./order-list.css"
import React from "react"
import DocumentTitle from "react-document-title"
import Navi from './Navi'
import OrderListUtil from "../utils/OrderListUtil"
class OrderList extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			orders: []
		}
	}

	componentDidMount() {
		if(this.props.location.query.needWx===1){
			alert("请返回微信继续");
			return ;
		}
		OrderListUtil.getData(
			this.props.location.query,
			function(res) {
				this.setState({
					orders: res.orders
				})
			}.bind(this)
		)

	}
	render() {
		var items = this.state.orders.map((item, index) => {
			return (
				<div className="order-list" key={index}>
					<div className="order-list-top">
						<div className="order-list-top-item">
							<span className="store pull-left">
								{item.outerOrderCode !== null && item.outerOrderCode !== ""
									? "线上订单"
									: "线下订单"}
							</span>
							<span className="status pull-right">
								{item.isEnd ? "已完成" : "制作配送中"}
							</span>
						</div>
						<div className="number">
							<span>订单编号：{item.orderCode}{item.outerOrderCode !== null && item.outerOrderCode !== ""
							? <span>(微商城单号：{item.outerOrderCode})</span>
							: null}</span>
						</div>
						<div className="number">
							<span>下单时间：{item.date}</span>
						</div>
						{	item.isSelfPick&&item.pickAddr?
							<div className="number">
								<span>自提地址：{item.pickAddr}</span>
							</div>
							:null	
						}
						

					</div>
					<OrderItem orderItems={item.orderItems} isSelfPick={item.isSelfPick}/>
					<div className="order-list-bottom">
						<span className="pull-right total">优惠金额：￥{item.discount?item.discount:0}</span>
					</div>
					<div className="order-list-bottom">
						<span className="pull-right total">运费：￥{item.shippingCost?item.shippingCost:0}</span>
					</div>
					<div className="order-list-bottom">
						<span className="pull-right total">合计：{item.accounts>0?item.accounts:0}</span>
					</div>
				</div>
			)
		})
		return (
			<DocumentTitle title="我的订单">
				<div className="order">
					{items}
					<div className="bottom-text">
						O(∩_∩)O 啊哦，没有更多订单啦~~~
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
		)
	}
}

class OrderItem extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			orderItems: []
		}
	}
	render() {
		console.log(this.props)
		var items = this.props.orderItems.map((item, index) => {
			return (
				<div className="order-list-item-info" key={index}>
					{item.hongShiGoods.image !== null && item.hongShiGoods.image !== ""
						? <img className="image" src={item.hongShiGoods.image} alt="" />
						: null}
					<div className="title">{item.hongShiGoods.title}</div>
					<div className="sku-info">
						<span className="sku">款式：{item.hongShiGoods.spec}</span>
						<span className="count pull-right">单价：￥{item.price}</span>
					</div>
					<div className="other">
						<span className="price pull-right">数量：x {item.count}</span>
					</div>
					<div className="other">
						<span className="tag">{this.props.isSelfPick?'自提':'配送'}</span>
					</div>
				</div>
			)
		})
		return (
			<div className="order-list-item">
				{items}
			</div>
		)
	}
}

export default OrderList
