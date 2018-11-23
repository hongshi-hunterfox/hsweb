
import React from 'react'
import DocumentTitle from 'react-document-title'
import './order.css'
import req from 'superagent'
import Icon from '../components/Icon'
import "./order-list.css"
var fto = require('form_to_object');
class MyOrderDetail extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            orderSerialNum :'',
            createTimeStr : '',
            storeName :'',
            phone       : '',
            remark      : '',
            name        :'',
            isSelfPick   :false,
            shippingAddress : '',
            pickDateStr     :'',
            totalPrice : 0,
            shippingCost :0,
            cut :0,
            orderItems :[],
        }
    }

    componentDidMount() {
        if (this.props.params.id) {
            this._getOrderDetaiData(this.props.params.id);
            return
        }
    }

    render() {

        return (
            <DocumentTitle title="订单详情">
                <div className="order">
                    <form className="" >
                        <div className="order-store">
                            微商城订单编号：<span className="number">{this.state.orderSerialNum}</span>
                        </div>
                        <div className="order-store">
                            下单时间：<span className="number">{this.state.createTimeStr}</span>
                        </div>

                        <div className="order-store">
                            门店：<span className="number">{this.state.storeName}</span>
                        </div>

                        <div className="order-store">
                            取货时间：<span className="money">{this.state.pickDateStr}</span>
                        </div>

                        <div className="order-store">
                            配提方式：<span className="number">{this.state.isSelfPick? "自提" : "配送"}</span>
                        </div>

                        {this.state.isSelfPick?null:
                            <div className="order-store">
                                收货人：<span className="number">{this.state.name}</span>
                            </div>
                        }
                        {this.state.isSelfPick?null:
                            <div className="order-store">
                                配送地址：<span className="number">{this.state.shippingAddress}</span>
                            </div>
                        }
                        <div className="order-store">
                            联系手机：<span className="money">{this.state.phone}</span>
                        </div>

                        <div className="order-store">
                            买家留言：<span className="money">{this.state.remark}</span>
                        </div>
                        {this.state.isSelfPick?null:
                            <div className="order-store">
                                运费：<span className="money">{this.state.shippingCost}</span>
                            </div>
                        }
                        <div className="order-store">
                            优惠金额：<span className="money">{this.state.cut}</span>
                        </div>

                        <div className="order-store">
                            金额合计：<span className="money">{this.state.totalPrice}</span>
                        </div>

                        <OrderItem orderItems={this.state.orderItems} />
                        <div className="order-submit">
                            <span onClick={()=>{window.location='/order-list'}} className='button'>返回</span>
                        </div>
                    </form>
                </div>
            </DocumentTitle>
        )

    }

    _getOrderDetaiData = id => {
        req
            .get('/uclee-user-web/getMyOrderDetail?outerOrderCode=' + this.props.params.id)
            .query({
                merchantCode: localStorage.getItem('merchantCode')
            })
            .end((err, res) => {
                if (err) {
                    return err
                }
                this.setState({
                    orderSerialNum :res.body.order.orderSerialNum,
                    createTimeStr :res.body.order.createTimeStr,
                    storeName : res.body.order.storeName,
                    phone  : res.body.order.phone,
                    remark : res.body.order.remark,
                    name    : res.body.order.name,
                    isSelfPick :res.body.order.isSelfPick,
                    shippingAddress :res.body.order.province + res.body.order.city + res.body.order.region + res.body.order.addrDetail,
                    pickDateStr : res.body.order.pickDateStr,
                    totalPrice : res.body.order.totalPrice,
                    shippingCost :res.body.order.shippingCost,
                    cut :       res.body.order.cut,
                    orderItems : res.body.order.items
                })


            })
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
        var items = this.props.orderItems.map((item, index) => {
            return (
                <div className="order-item-info" key={index}>
                    <img className="image" src={item.imageUrl} alt="" />
                    <div className="title">{item.title}</div>
                    <div className="sku-info">
                        <span className="sku">{item.value}</span>
                        <span className="count pull-right">单价：￥{item.price}</span>
                    </div>
                    <div className="other">
                        <span className="price pull-right">数量：x {item.amount}</span>
                    </div>
                </div>
            )
        })
        return (
            <div className="order-item">
                {items}
            </div>
        )
    }
}
export default MyOrderDetail