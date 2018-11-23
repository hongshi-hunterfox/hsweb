/**
 * Created by jiang on 2018/4/3.
 */

require("./payment.css")
var React = require("react")
var browserHistory = require("react-router").browserHistory
var RefundUtil = require("../utils/RefundUtil.js")
import DocumentTitle from "react-document-title"
import req from 'superagent'

class CountDownBlock extends React.Component {
    render() {
        return (
            <div className="payment-countdown">
                <div className="payment-countdown-title">退款</div>

                <div className="payment-countdown-remind">
                    下单部门：{this.props.order.storeName}
                </div>
                <div className="payment-countdown-remind">
                    退款金额：￥{this.props.paymentOrder.money}
                </div>
                <div className="payment-countdown-remind">
                    下单时间：{this.props.order.createTimeStr}
                </div>
                <div className="payment-countdown-remind">
                    退款单号：{this.props.refund.refundSerialNum}
                </div>


                <div className="payment-countdown-remind">
                    微商城单号：{this.props.order.orderSerialNum}
                </div>

                <div className="payment-countdown-remind">
                    原支付单号：{this.props.paymentOrder.paymentSerialNum}
                </div>
            </div>
        )
    }

}

class PaymentMethod extends React.Component {
    render() {
            return (
                <div className="payment-info-method active">
                    <span className="payment-info-method-text">原支付方式:{this.props.payment.paymentName}</span>
                </div>
            )
    }
}



class Refund extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            order:{},
            payment: {},
            paymentOrder: {},
            refund:{},
            refundSerialNum :  this.props.location.query.refundSerialNum,
            isWC: true,
            timeCountDown: 60000,
            submitting:false,
            isWCCancel:false,
            cVipCode:null,
            cardStatus:'',
            allowRefund:true,
            refundDesc:''
        }
    }

    componentDidMount() {
        RefundUtil.getData(
            this.props.location.query,
            function(res) {
                this.setState({
                    order :res.order,
                    payment: res.payment,
                    paymentOrder: res.paymentOrder,
                    refund :res.refund
                })

            }.bind(this)
        )

        if (!this.state.refund ||this.state.refund.isCompleted) {
            alert("退款单已过期或已失效")
            return ;
        }
        if(!this.state.isWCCancel&&this.state.submitting){
            alert("请勿多次提交");
            window.location="/order-list?isEnd=1";
        }
    }


    _descHandler = e => {
        this.setState({
            refundDesc: e.target.value
        })
    }


    _submitHandler=()=>{
        this.setState({
            isWCCancel:false
        })

        if (this.state.refund.isCompleted) {
            alert("该退款单已经完成了退款!")
            return;
        }
        if(this.state.refund.flag==1){
            alert("该退款单已经处于审核状态，请勿重复提交!");
            return;
        }
        var data = {}
        data.refundSerialNum = this.state.refund.refundSerialNum
        data.paymentSerialNum=this.state.paymentOrder.paymentSerialNum
        data.paymentId = this.state.payment.paymentId
        data.totalFree=this.state.paymentOrder.money
        data.refundFree=this.state.paymentOrder.money
        data.refundDesc= this.state.refundDesc

        if(!data.refundDesc){
            alert("请填写退款原因");
            return;
        }
        /*
         这里省略会员卡退款
         */
        this.setState({
            submitting: true
        })
        RefundUtil.submitApplyHandler(data,
            function(res) {
                console.log(res)
                if (res.result === true) {
                    alert("您提交的订单已经处于审核状态,我们审核通过以后会通知您！")
                    window.location="/order-list?isEnd=1";
                } else {
                    if (res.reason === "noSuchOrder") {
                        alert("非法退款单号")
                    } else if(res.reason==="illegel"){
                        alert("该退款单已经完成了退款")
                    }else if(res.reason==="notSupportMemberCard"){
                        alert("暂不支持会员卡退款");
                    }else if(res.reason=="allreadyApply"){
                        alert("该退款单已经申请了退款,请勿重复申请!");
                    }else if(res.reason=="notLogin"){
                        alert("您未登陆！");
                    }else{
                        alert("网络繁忙，请稍后再试!")
                    }
                }
            }.bind(this)
        )
    }

    render() {
        return (
            <DocumentTitle title="退款">
                <div className="payment">
                    <CountDownBlock
                        timeCountDown={this.state.timeCountDown}
                        paymentOrder={this.state.paymentOrder}
                        refund={this.state.refund}
                        order={this.state.order}
                        />

                    <div className="payment-countdown">
                        <div className="payment-countdown-remind">
                            退款原因：<input type="text" name="refundDesc" onChange={this._descHandler}  className="form-control" />
                        </div>
                    </div>
                    <PaymentMethod payment={this.state.payment}/>
                    <div className="payment-bottom">
                        <button
                            type="button"
                            className="btn btn-default payment-bottom-button"
                            onClick={this._submitHandler} disabled={this.state.submitting}>申请退款
                        </button>
                    </div>
                </div>
            </DocumentTitle>
        )
    }
}

export default Refund



