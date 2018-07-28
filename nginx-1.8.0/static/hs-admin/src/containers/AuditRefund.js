import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'
var AuditRefundUtil = require("../utils/AuditRefundUtils.js")
import { Link } from 'react-router'
class AuditRefund extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            refunds:[],
            size:0,
            refundOrderId:0,
            refundSerialNum:'',
            html: "",
        }
    }

    componentDidMount() {
        req.get('/uclee-backend-web/refundList').end((err, res) => {
            if (err) {
                return err
            }
            var data = JSON.parse(res.text)
            this.setState({
                refunds:data.refunds,
                size:data.refunds.length
            })
        })
    }

    _getMarkup = () => {
        return {
            __html: this.state.html
        }
    }

    _nowRefund=(id1,id2)=>{
        this.setState({
            refundOrderId:id1,
            refundSerialNum:id2
        })
        var conf = confirm('确认要作废订单吗？');
        if(conf){
            var data={}
            data.refundOrderId=this.state.refundOrderId
            data.refundSerialNum=this.state.refundSerialNum
            AuditRefundUtil.submitHandler(data,
                function(res) {
                    console.log(res)
                    if (res.result === true) {
                        if (res.type === "WC") {
                            if (res.result === "failed") {
                                alert("网络繁忙，请稍后再试")
                                return false
                            }
                            window.location ='/auditRefund'
                        } else if (res.type === "alipay") {
                            this.setState({
                                html: res.html
                            })
                            setTimeout(function() {document.forms["alipaysubmit"].submit()}, 0)
                        } else {
                         alert("暂不支持其他退款！")
                        }
                    } else {
                        if (res.reason === "noSuchOrder") {
                            alert("非法退款单号")
                        } else if(res.reason==="illegel"){
                            alert("该退款单已经完成了退款!")
                        }else if(res.reason==="notSupportMemberCard"){
                            alert("暂不支持会员卡退款");
                        }else if(res.reason==="AllredaySuccessRefund"){
                            alert("该退款单已经完成了退款,请勿再次审核！");
                        }else if(res.reason==="noPaymentMethod"){
                            alert("必须先支付才能有退款!");
                        } else{
                            alert("网络繁忙，请稍后再试")
                        }
                    }
                }.bind(this)
            )
        }
    }




    render() {
        var list = this.state.refunds.map((item, index) => {
            return (
                <tr key={index}>
                    <td width='12%'>{item.orderSerialNum}</td>
                    <td width='8%'>{item.storeName}</td>
                    <td width='10%'>{item.createTime}</td>
                    <td width='5%'>{item.refundFree}</td>
                    <td width='8%'>{item.paymentName}</td>
                    <td width='12%'>
                        <button className="btn btn-primary button-right" onClick={this._nowRefund.bind(this,item.refundOrderId,item.refundSerialNum)}>
                            审核退款
                        </button>

                        <Link to={'/auditRefundOrderDetail/' + item.orderSerialNum} className="btn btn-primary">
                            订单详情
                        </Link>
                    </td>
                </tr>
            )
        })
        return (
            <DocumentTitle title="审核退款列表">
                <div className="user-list">
                    <div className="user-list-add">
                        <div className="btn btn-primary">
                            退款单总数:{this.state.size}
                        </div>
                    </div>
                    <table className="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th width='12%'>微商城单号</th>
                            <th width='8%'>下单部门</th>
                            <th width='10%'>下单时间</th>
                            <th width='5%'>退款金额</th>
                            <th width='8%'>原支付方式</th>
                            <th width="12%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        {list}
                        </tbody>
                    </table>

                    {this.state.html ? <div dangerouslySetInnerHTML={this._getMarkup()}></div> : null}
                </div>


            </DocumentTitle>
        )
    }
}

export default AuditRefund
