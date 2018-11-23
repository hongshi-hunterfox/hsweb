import "./order-list.css"
import React from "react"
import DocumentTitle from "react-document-title"
import Navi from './Navi'
import OrderListUtil from "../utils/OrderListUtil"
import req from 'superagent'
var Link = require("react-router").Link
class OrderList extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			orders: [],
			showImage:false,
			imageUrl:'',
			barcode:'',
			commentTextText:''
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
    req.get('/uclee-user-web/getCommentText').end((err, res) => {
      if (err) {
        return err
      }
      var d = res.body

      this.setState({
        commentText: d.commentText
      })
    })
	}
	_showImage=(url,url2)=>{
		this.setState({
			showImage:!this.state.showImage,
			imageUrl:url,
			barcode:url2
		})
	}

	_inValid=(outerOrderCode)=>{
		var conf = confirm('确认要作废订单吗？');
		if(conf){
			req.get('/uclee-user-web/applyRefund?outerOrderCode='+outerOrderCode).end((err, res) => {
				if (err) {
					return err
				}
				if(res.body.result){
					var resJson = JSON.parse(res.text)
					window.location = 'seller/refund?refundSerialNum=' + resJson.refundSerialNum
				}else{
					alert("该订单已经退款");
				}
			})
		}
	}




	render() {
		var items = this.state.orders.map((item, index) => {
			if(item.outerOrderCode !== null && item.outerOrderCode !== ""){
				return (
					<div className="order-list" key={index}>
						<div className="order-list-top">
							<div className="order-list-top-item">
								<span className="store pull-left">
									{/*}{item.outerOrderCode !== null && item.outerOrderCode !== ""
										? "线上订单"
										: "线下订单"}
									*/}
									线上订单
								</span>
								<span className="status pull-right">
									{!item.isEnd ? item.void? "已废弃" :  "制作配送中" : "已完成"}
								</span>
							</div>
							<div className="number">
								<span>订单编号：{item.orderCode}{item.outerOrderCode !== null && item.outerOrderCode !== ""
								? <span>(微商城单号：{item.outerOrderCode})</span>
								: null}</span>
							</div>
							<div className="number">
								<span>下单时间：{item.createTime}</span>
							</div>
            				<div className="number">
								<span>下单部门：{item.department}</span>
							</div>
							{   item.isEnd ==1?
								<div className="number">
							    	<span>评论订单有礼：{this.state.commentText}</span>
						    	</div>
						    	:null
							}

							<div className="number">
								<span>订单金额：{item.totalAmount>0?item.totalAmount:0}</span>

							</div>
						</div>
						<div>
							{item.isEnd&&!item.isComment?<Link className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#f15f40',color:'white'}} to={'/comment?orderSerialNum='+item.outerOrderCode}>去评论</Link>:null}
							{item.isEnd&&item.isComment?<Link className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#f15f40',color:'white'}} to={'/commentDetail?orderSerialNum='+item.outerOrderCode}>查看评论</Link>:null}
						</div>
					
          				<div>
							<span onClick={()=>{window.location="/myOrderDetail/" + item.outerOrderCode}} className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#09F7C7',color:'white'}} >订单详情</span>
						{/*<span onClick={this._inValid.bind(this,item.outerOrderCode)} className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#09F7C7',color:'white'}} >订单作废</span>*/}
						</div>

							{!item.isEnd&&!item.void?
								<div>
									<span className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#f15f40',color:'white'}} onClick={this._showImage.bind(this,item.pickUpImageUrl,item.barcode)}>查看提货码</span>
								</div>
							:null}
					</div>
				)
			}else{
				return null;
			}
		})
		return (
			<DocumentTitle title="我的订单">
				<div className="order">
					<div className='order-top'>
						<span onClick={()=>{window.location='/order-list'}} className={'order-top-item'+(!this.props.location.query.isEnd?' active':'')}>全部</span>
						<span onClick={()=>{window.location='/unpay-order-list'}} className='order-top-item'>待支付</span>
						<span onClick={()=>{window.location='/order-list?isEnd=0'}} className={'order-top-item'+(this.props.location.query.isEnd&&this.props.location.query.isEnd==='0'?' active':'')}>制作配送中</span>
						<span onClick={()=>{window.location='/order-list?isEnd=1'}} className={'order-top-item'+(this.props.location.query.isEnd&&this.props.location.query.isEnd==='1'?' active':'')}>已完成</span>
					</div>
					{items}
					<div className={'order-pick-up-image '+(this.state.showImage?'':'none')} onClick={()=>{
						this.setState({
							showImage:!this.state.showImage
						})
					}}>
						
						<img className='barcode' src={this.state.barcode} width='80%' />
						<img className='image' src={this.state.imageUrl} width='80%' /> 
						<span className='fa fa-times-circle-o icon' ></span>
					</div>
					<div className="bottom-text">
						O(∩_∩)O 啊哦，没有更多订单啦~~~
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
		)
	}
}
/*
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
*/
export default OrderList
