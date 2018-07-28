import React from 'react'
import DocumentTitle from 'react-document-title'
import req from 'superagent'
import './onlineorder.css'
import './order.css'
import Icon from '../components/Icon'


class OnlineOrder extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
           billCode:this.props.location.query.billCode,
           source:this.props.location.query.source,
           orders: [],
           logoUrl:'',
		   signName:''
		   
       
           
           
           
        }
    }
    
    componentDidMount() {
    req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				logoUrl:c.logoUrl,
				signName:c.signName
			})
		})
    req.get('/uclee-user-web/vipRecordDetail')
    .query({
        billCode:this.state.billCode,
        source:this.state.source
     })
    .end((err, res) => {
      if (err) {
        return err
      }
      var d = res.body

      this.setState({
        orders: d.orders,
        danhao: d.danhao,
        storeName:  d.storeName,
        beizhu: d.beizhu,
        jine:   d.jine,
        songhuo: d.songhuo,
        huijine:   d.huijine,
        riqi:    d.riqi
      })
    })
    
    
    }
    render() {

       var orders = this.state.orders.map((item,index)=>{
        return (
            <div>
            {item.guige ===undefined ?              	
           		<div>
              		{item.jine!==undefined ?
              			<div className="order-store">充值金额：{item.jine}</div>
              		:
              			<div className="order-store">合计金额：{item.hejijine}</div>
              		}
              		{item.xianjin!==undefined ?
              			<div className="order-store">支付金额：{item.xianjin}</div>
              		:
              			<div className="order-store">规格：{item.beizhu}</div>
              		}
              		{item.jifen!==undefined ?
              			<div className="order-store">赠送积分：{item.jifen}</div>
              		:
              			null
              		}          	
              			<div className="order-store">日期:{item.riqi}</div>
            	</div>
              :
                <div className="order-list-top-item">
                	<span className="statuss pull-left">
                		产品名称：<span style={{color:'#27AE60'}}>{item.names}</span> 
                    </span>
                    <div>
                		<span className="statuss pull-left">
                			单价：￥<span style={{color:'#27AE60'}}>{item.danjia}</span>
                		</span>	
                		<span className="statuss pull-right">
                    		数量：x <span style={{color:'#27AE60'}}>{item.shuliang}</span>
                    	</span>
                    </div>
                </div>

              }
            </div>
        );
  	  })
                        
        return (
                <DocumentTitle title="会员交易明细">
                <div>
                	<div className="store-logo">
						<img src={this.state.logoUrl} className="store-logo-image" alt=""/>
						<div className="store-logo-text">{this.state.signName}</div>
						
					</div>
					  <div>
					  
				{this.state.storeName!==undefined ?
			  <div>
				<div className="order-store">单号:{this.state.danhao}</div>
				<div className="order-store">门店:{this.state.storeName}</div>
				<div className="order-store">备注:{this.state.beizhu}</div>
				<div className="order-store">合计金额:{this.state.jine}</div>
				{this.state.huijine!==undefined ?
                    <div className="order-store">优惠金额:{this.state.huijine}</div>
                    :
              	    null
              	    }
				{this.state.songhuo!==undefined ?
				<div className="order-store">送货地址:{this.state.songhuo}</div>
				:
				null
				}
				<div className="order-store">日期:{this.state.riqi}</div>
				</div>
				:
				null
				}
				</div>
                {orders}
                </div>
                </DocumentTitle>
        )
    }
}
export default OnlineOrder