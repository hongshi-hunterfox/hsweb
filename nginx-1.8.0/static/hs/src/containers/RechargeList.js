import React from 'react'
import DocumentTitle from 'react-document-title'
import './recharge-list.css'
import req from 'superagent'

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
    <div className="recharge-list-item">

      {
        <div className="recharge-list-value">
          <span style={{width:'80%'}}>{props.source}</span>：  <span style={{width:'20%'}}>{props.value}</span>
          <span className="recharge-list-point pull-right" >余额：{props.balance}</span>
          
        </div>
      }
      <span className="recharge-list-time"><span style={{width:'80%'}}>{props.billCode}</span><span className='bonusPoints' style={{padding:'10px',marginLeft:'10px',color:'#27AE60',width:'20%'}}>{props.bonusPoints}</span></span>
      <span className="recharge-list-balance">积分：<span style={{color:'#27AE60'}}>{props.integral}</span></span>
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
        </div>
      </DocumentTitle>
      )
  }
}

export default RechargeList