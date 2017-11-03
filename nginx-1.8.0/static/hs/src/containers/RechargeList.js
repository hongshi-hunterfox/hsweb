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
      <div className='top '>
        <div className='left'>
            <div className='tag'><span className='left'>{props.source}：  </span><span className='right'>{props.value}</span></div>
        </div>
        <div className='right'>
            <span className="recharge-list-point pull-right" >余额：{props.balance}</span>
        </div>
      </div>
      <div className='bottom '>
         <div className='left'>
            <span className="time"><span className='left'>{props.billCode}</span><span className='bonusPoints' className='right'>{props.bonusPoints}</span></span>
          </div>
        <div className='right'>
            <span className="recharge-list-balance">积分：<span style={{color:'#27AE60'}}>{props.integral}</span></span>
        </div>
      </div>
      
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
      <DocumentTitle title="会员交易明细">
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