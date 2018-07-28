
import React from 'react'
import DocumentTitle from 'react-document-title'
import req from 'superagent'
import Icon from '../components/Icon'
import { Link } from 'react-router'
var fto = require('form_to_object');
class AuditRefundOrderDetail extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            refundDesc:'',
            orderItems :[],
        }
    }

    componentDidMount() {
        if (this.props.params.id) {
            this._getAuditRefundDetail(this.props.params.id);
            return
        }
    }

    render() {

        return (
            <DocumentTitle title="审核订单详情">
                <div className="order">
                    <form className="" >
                        <div>
                            <span>订单退款原因：{this.state.refundDesc}</span>
                        </div>

                        <OrderItem orderItems={this.state.orderItems} />
                        <Link to={'/auditRefund/'} className="btn btn-primary">
                            返回
                        </Link>
                    </form>
                </div>
            </DocumentTitle>
        )

    }

    _getAuditRefundDetail = id => {
        req
            .get('/uclee-backend-web/getAduitRefundDetail?orderSerialNum=' + this.props.params.id)
            .query({
                merchantCode: localStorage.getItem('merchantCode')
            })
            .end((err, res) => {
                if (err) {
                    return err
                }
                this.setState({
                    refundDesc : res.body.refundDesc,
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
                <tr key={index}>
                    <td width='12%'><img className="image" src={item.imageUrl} alt="" width="50"/></td>
                    <td width='8%'>{item.title}</td>
                    <td width='10%'>{item.value}</td>
                    <td width='5%'>￥{item.price}</td>
                    <td width='8%'>{item.amount}</td>
                </tr>
            )
        })

        return (
            <table className="table table-bordered table-striped">
                <thead>
                <tr>
                    <th width='12%'>产品图片</th>
                    <th width='8%'>产品标题</th>
                    <th width='10%'>规格</th>
                    <th width='5%'>产品价格</th>
                    <th width='8%'>下单数量</th>
                </tr>
                </thead>
                <tbody>
                {items}
                </tbody>
            </table>
        )
    }
}
export default AuditRefundOrderDetail