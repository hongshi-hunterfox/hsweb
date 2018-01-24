import React from 'react'
import DocumentTitle from 'react-document-title'
import './distribution-order.css'
import Navi from './Navi'
var request = require('superagent');
class DistributionOrder extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
           orders:[],
           money:0
        }
    }

    componentDidMount() {
        request
          .get('/uclee-user-web/distOrder')
          .end((err, res) => {
            if (err) {
              return err
            }

            this.setState({
              ...res.body
            })
          })
      }

    _transHandler = () => {
        var conf = confirm('确定要转入到会员卡？');
        if(conf){
            if(this.state.money<=0){
                alert("无可转入余额");
                return;
            }
            request
              .get('/uclee-user-web/tranferBalance')
              .end((err, res) => {
                if (err) {
                  return err
                }
                var resJson = JSON.parse(res.text);
                if(resJson.result===true){
                  alert("转入成功");
                  this.setState({
                    money:0
                  })
                }else{
                  alert("网络繁忙，请稍后重试");
                }
              })
        }
    }
    render(){
        var orders = this.state.orders.map((item, index) => {
            return(
                <div className="distribution-order-item" key={index}>
                    <div className="order-number">
                        订单编号：{item.orderSerialNum}
                    </div>
                    <div className="left">
                        <div className="top">
                            <img className="image" src={item.userProfile.image} alt=""/>
                            <span className="name">{item.userProfile.name}</span>
                        </div>
                        <div className="bottom">消费时间：{item.createTimeStr}</div>
                    </div>
                    <div className="right">
                        <div className="top">
                            消费金额:<span className="red">￥{item.totalPrice}</span>
                        </div>
                        <div className="bottom">获利金额：<span className="red">￥{item.distLevel===1?item.firstDistMoney:item.secondDistMoney}</span></div>
                    </div>
                </div>
            );
        });
        return(
            <DocumentTitle title="分销订单">
                <div className="distribution-order" >
                    <div className="distribution-order-top">
                        <div className="left pull-left">
                            <div className="money">收益余额</div>
                            <div className='number'>{this.state.money}</div>
                        </div>
                        {/*<div className="transfer pull-right" onClick={this._transHandler}>转进会员卡</div>*/}
                    </div>
                    {orders}
                    <div className="bottom-text">
                        O(∩_∩)O 啊哦，没有更多收益订单啦~~~
                    </div>
                    <Navi query={this.props.location.query}/>   
                </div>
            </DocumentTitle>
        );
    }
}

export default DistributionOrder