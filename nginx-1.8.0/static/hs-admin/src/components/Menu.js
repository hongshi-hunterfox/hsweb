import React from 'react'
import { Link } from 'react-router'

class Menu extends React.Component {
  render() {
    return (
      <div style={{
        paddingTop: 30
      }}>
        <div className="list-group">
          <Link to={'user-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            用户列表
          </Link> 
          <Link to={'/vip-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            会员列表
          </Link> 
          <Link to={'/phoneUserList?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            加盟商管理
          </Link>
          <Link to={'/category-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            产品类别
          </Link>
          <Link to={'/product-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            产品管理
          </Link>
          <Link to={'/banner-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            首页banner设置
          </Link>
          <Link to={'/quick-navi-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            首页快捷导航设置
          </Link>
          <Link to={'/editModuleSetting?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            首页模块设置
          </Link>
          <Link to={'/store-intro?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            公司介绍
          </Link>
          <Link to={'/freight?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            运费设置
          </Link>
          <Link to={'/fullCutShipping?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            满额免运费
          </Link>
          <Link to={'/fullCut?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            满额减金额
          </Link>
          <Link to={'/lottery?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            积分抽奖配置
          </Link>
          <Link to={'/bindMemberSetting?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            绑定会员赠送
          </Link>
          <Link to={'/recharge-config-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            充值赠送设置
          </Link>
          <Link to={'/integralinConfiguration?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            签到赠送礼品设置
          </Link>
          <Link to={'/comment-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            评论管理
          </Link>
            <Link to={'/evaluationConfiguration?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            评论赠送设置
          </Link>
           <Link to={'/user-birth-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            生日信息推送
          </Link>
          {/*<Link to={'/birth-voucher?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            生日礼券赠送配置
         </Link>*/}
          <Link to={'/user-unbuy-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            消费信息推送
          </Link>
           <Link to={'/shake-monitor?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            现场抽奖活动
          </Link>
					<Link to={'/bargain-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            砍价活动
          </Link>
          <Link to={'/orderSettingPick?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            设定歇业时间和营业时间
          </Link>
          {/*<Link to={'/auditRefund?merchantCode='+localStorage.getItem('merchantCode')}  className="list-group-item" activeClassName="active">
            退款审核
          </Link>
          */}
          <Link to={'/activity-config?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
          参数配置
          </Link>
           <Link to={'/system-config?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            系统配置
          </Link>
        </div>
      </div>
      )
  }
}

export default Menu