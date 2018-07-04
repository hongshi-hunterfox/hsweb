import React from 'react'
import DocumentTitle from 'react-document-title'
import './recharge-list.css'
import req from 'superagent'
var Link = require('react-router').Link;
import Navi from "./Navi"
const NoItem = () => {
  return (
    <div style={{
      margin: '50px 0',
      textAlign: 'center'
    }}>
      暂无记录
    </div>
    )
}


const RechargeListItem = (props) => {
  return (
   <div className="recharge-list-item ">
   {props.source!=="评论赠积分" ?
    
    props.source!=="绑卡送积分"&&props.source!=="签到送积分"&&props.source!=="抽奖扣积分" ?
    <div onClick={()=>{window.location='/OnlineOrder?billCode=' + props.billCode + ',' + props.source}}>
			<div  className="top"> 
            <span className="left">{props.source}：</span>
            <span className="right">{props.billCode}</span>   
      </div>
      <div className="bottom">
      	<span className='left'>
        	变动金额:{props.source==="线上订单"||props.source==="线下订单"|| props.source==="零售"?  "-" + props.value : props.value }
      	</span>
      	<span className='right'>余额：<span style={{color:'#27AE60'}}>{props.balance}</span></span>
    	</div>
    	<div className='bottom '>
      	<span className='left'>变动积分:{props.bonusPoints}</span>
      	<span className="right">剩余积分：<span style={{color:'#27AE60'}}>{props.integral}</span></span>
    	</div>
    </div>
    :
    <div>
      <div className="top">
          <span className="left">{props.source}：</span>
          <span className="right">{props.billCode}</span>
      </div>
    	<div className="bottom">
      	<span className='left'>
        	变动金额:{props.source==="线上订单"||props.source==="线下订单"|| props.source==="零售"?  "-" + props.value : props.value }
      	</span>
      	<span className='right'>余额：<span style={{color:'#27AE60'}}>{props.balance}</span></span>
    	</div>
    	<div className='bottom '>
      	<span className='left'>变动积分:{props.bonusPoints}</span>
      	<span className="right">剩余积分：<span style={{color:'#27AE60'}}>{props.integral}</span></span>
    	</div>
    </div>  
    :
    <div>
    	<div className="top">
      	<span className="left">{props.source}：</span>
      	<span className="right">{props.billCode}</span>
    	</div>
    	<div className="bottom">
      	<span className='left'>
        	变动金额:{props.source==="线上订单"||props.source==="线下订单"|| props.source==="零售"?  "-" + props.value : props.value }
      	</span>
      	<span className='right'>余额：<span style={{color:'#27AE60'}}>{props.balance}</span></span>
    	</div>
    	<div className='bottom '>
      	<span className='left'>变动积分:{props.bonusPoints}</span>
      	<span className="right">剩余积分：<span style={{color:'#27AE60'}}>{props.integral}</span></span>
    	</div>
    </div>
  }  
  </div>
  )
}

class RechargeList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      list: []
    }
  }

  componentDidMount() {
    if(this.props.location.query.needWx===1){
      alert("请返回微信继续");
      return ;
    }
    req
      .get('/uclee-user-web/rechargeRecord')
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          list: res.body
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="会员交易记录">
        <div className="recharge-list">
          {
            this.state.list.length ?
            this.state.list.map((item, index) => {
              return <RechargeListItem key={index} title={item.source} value={item.amount} billCode={item.billCode} bonusPoints={item.bonusPoints} time={item.dealTim} balance={item.balance} source={item.source} bonusPoints={item.bonusPoints} integral={item.integral}/>
            })
            : <NoItem />
          }
          {/*<Link to={"/member-center"} className='recharge-list-back'>
                返回会员中心
          </Link>*/}
          <Navi query={'member-center'}/>
        </div>
      </DocumentTitle>
      )
  }
}

export default RechargeList