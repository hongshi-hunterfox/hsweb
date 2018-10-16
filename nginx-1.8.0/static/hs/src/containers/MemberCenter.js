import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-center.css'
import Icon from '../components/Icon'
import LinkGroup from '../components/LinkGroup'
import LinkGroupItem from '../components/LinkGroupItem'
import req from 'superagent'
import Navi from './Navi'

class MemberCenter extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      nickName: '',
      point:0,
      serialNum:'',
      cVipCode:null,
      isSigned:false,
      unPayCount:0,
      unCommentCount:0,
      deliCount:0,
      ucenterImg:''
    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getUserInfo')
      .query({
        t: new Date().getTime()
      })
      .end((err, res) => {
        if(res&&res.body){
          this.setState(res.body)
        }
      })

      req
      .get('/uclee-user-web/getVipInfo')
      .end((err, res) => {
        if (err) {
          return err
        }

        if (res.text) {
          this.setState(res.body)
        }
      })
  }

  render() {
    console.log(this.props.location.query)
    return (
      <DocumentTitle title="会员中心">
        <div className="member-center">
          <div className="member-center-hero">
          {
            !this.state.isSigned?
            <span className="member-center-check-in">
            	<a href="/SignIn">
            		<font color="white">签到获取积分</font>
            	</a>
            </span>:
            <span className="member-center-check-in">今日已签到</span>
          }
            <img src={this.state.ucenterImg} alt=""/>
            <div className="member-center-info">
              <div>尊贵的 {this.state.nickName}</div>
            </div>
          </div>

          <div className="member-center-status clearfix">
            <div className="member-center-status-item">
                <span className="member-center-status-title"><Icon name="database" className="member-center-status-icon" />积分</span>
                <span>{this.state.point.toFixed(0) || '0'}</span>
            </div>
            <div className="member-center-status-item" onClick={() => { window.location='/member-card' }}>
              <a href="/member-card">
                <span className="member-center-status-title"><Icon name="money" className="member-center-status-icon" />余额</span>
                <span>{this.state.balance || '0'}</span>
              </a>
            </div>
            <div className="member-center-status-item" onClick={() => { window.location='/coupon'  }}>
                <span className="member-center-status-title"><Icon name="ticket" className="member-center-status-icon" />电子券</span>
                <span>{this.state.couponAmount || '0'}</span>
            </div>
          </div>
          <div className="member-center-orders clearfix">
            <div className="member-center-order" onClick={() => { window.location='/unpay-order-list' }}>
              {this.state.unPayCount&&this.state.unPayCount>0?<div className='member-center-order-count'>{this.state.unPayCount}</div>:null}
              <a href="#">
                <Icon name="smile-o" className="member-center-order-icon" />
                <span>待付款</span>
              </a>
            </div>
            <div className="member-center-order" onClick={() => { window.location='/order-list?isEnd=0' }}>
             {this.state.isvoid===false&&this.state.deliCount&&this.state.deliCount>0?<div className='member-center-order-count'>{this.state.deliCount}</div>:null}
              <a href="#">
                <Icon name="smile-o" className="member-center-order-icon" />
                <span>制作配送中</span>
              </a>
            </div>
            <div className="member-center-order" onClick={() => { window.location='/order-list?isEnd=1'  }}>
              {this.state.unCommentCount&&this.state.unCommentCount>0?<div className='member-center-order-count'>{this.state.unCommentCount}</div>:null}
              <a href="#">
                <Icon name="smile-o" className="member-center-order-icon" />
                <span>已结单</span>
              </a>
            </div>
          </div>

          <LinkGroup>
            <LinkGroupItem icon="smile-o" text="我的订单" onClick={() => { window.location='/order-list'  }}/>
            <LinkGroupItem icon="smile-o" text="我的购物车" onClick={() => { window.location='/cart'}}/>
          </LinkGroup>

          <LinkGroup>
            <LinkGroupItem icon="smile-o" text="我的会员卡" href="/member-card"/>
            <LinkGroupItem icon="smile-o" text="会员卡明细" href="/recharge-list"/>
            <LinkGroupItem icon="smile-o" text="积分抽奖" href="/lottery"/>
            <LinkGroupItem icon="smile-o" text="我的优惠券" onClick={() => { window.location='/coupon' }}/>
            <LinkGroupItem icon="smile-o" text="分销中心" onClick={() => { window.location='/distribution-center?serialNum='+this.state.serialNum+'&merchantCode='+localStorage.getItem('merchantCode')}}/>
            <LinkGroupItem icon="smile-o" text="个人信息" onClick={() => { window.location='/information' }}/>
          </LinkGroup>

          <LinkGroup>
            <LinkGroupItem icon="smile-o" text="收货地址管理" href="/address"/>
          </LinkGroup>
          <Navi query={this.props.location.query}/>
        </div>
      </DocumentTitle>
      )
  }
}

export default MemberCenter
