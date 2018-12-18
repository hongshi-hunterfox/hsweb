import React from 'react';
import { Link } from 'react-router';
import './reset.min.css';
import './style.css';

class Menu extends React.Component {

	render() {
		return(
			<div>
				<ul className="hList">				
					<li>
						<a href="#click" className="menu">
							<h2 className="menu-title">首页设置</h2>
							<ul className="menu-dropdown">
								<li>
									<Link to={'/banner-list?merchantCode='+localStorage.getItem('merchantCode')}>
						            	首页banner设置
						          	</Link>
						        </li>
								<li>
									<Link to={'/quick-navi-list?merchantCode='+localStorage.getItem('merchantCode')}>
			        					首页快捷导航设置
			      					</Link>
								</li>
								<li>
									<Link to={'/editModuleSetting?merchantCode='+localStorage.getItem('merchantCode')}>
								        首页模块设置
								    </Link>
								</li>
							</ul>
						</a>
					</li>
					<li>
						<a href="#click" className="menu">
							<h2 className="menu-title menu-title_2nd">基础设置</h2>
							<ul className="menu-dropdown">
								<li>
					          		<Link to={'/category-list?merchantCode='+localStorage.getItem('merchantCode')}>
					            		产品类别
					          		</Link>
								</li>
								<li>
									<Link to={'/product-list?merchantCode='+localStorage.getItem('merchantCode')}>
						            	产品管理
						          	</Link>
								</li>
								<li>
									<Link to={'/napaStoreList?merchantCode='+localStorage.getItem('merchantCode')}>
								        门店管理
								    </Link>
								</li>
								<li>
									<Link to={'/phoneUserList?merchantCode='+localStorage.getItem('merchantCode')}>
								        加盟商管理
								    </Link>
								</li>
								<li>
									<Link to={'/freight?merchantCode='+localStorage.getItem('merchantCode')}>
								        运费设置
								    </Link>
								</li>
							</ul>
						</a>
					</li>
					<li>
						<a href="#click" className="menu">
						<h2 className="menu-title menu-title_3rd">营销中心</h2>
						<ul className="menu-dropdown">
							<li>
								<Link to={'/fullCutShipping?merchantCode='+localStorage.getItem('merchantCode')}>
								    满额免运费
								</Link>
							</li>
							<li>
								<Link to={'/fullCut?merchantCode='+localStorage.getItem('merchantCode')}>
								    满额减金额
								</Link>
							</li>
							<li>
								<Link to={'/fullSendCoupon?merchantCode='+localStorage.getItem('merchantCode')}>
								    满额送优惠券
								</Link>
							</li>
							<li>
								<Link to={'/lottery?merchantCode='+localStorage.getItem('merchantCode')}>
								    积分抽奖配置
								</Link>
							</li>
							<li>
								<Link to={'/bindMemberSetting?merchantCode='+localStorage.getItem('merchantCode')}>
								    绑定会员赠送
								</Link>
							</li>
							<li>
								<Link to={'/recharge-config-list?merchantCode='+localStorage.getItem('merchantCode')}>
								    充值赠送设置
								</Link>
							</li>
							<li>
								<Link to={'/integralinConfiguration?merchantCode='+localStorage.getItem('merchantCode')}>
									签到赠送设置
								</Link>
							</li>
							<li>
								<Link to={'/evaluationConfiguration?merchantCode='+localStorage.getItem('merchantCode')}>
				            		评论赠送设置
				          		</Link>
							</li>
							<li>
								<Link to={'/user-birth-list?merchantCode='+localStorage.getItem('merchantCode')}>
									生日信息推送
								</Link>
							</li>
							<li>
								<Link to={'/user-unbuy-list?merchantCode='+localStorage.getItem('merchantCode')}>
								    消费信息推送
								</Link>
							</li>
							<li>
								<Link to={'/product-voucher-list?merchantCode='+localStorage.getItem('merchantCode')}>
								    指定产品送券
								</Link>
							</li>
							<li>
								<Link to={'/linkCoupon?merchantCode='+localStorage.getItem('merchantCode')}>
								    指定链接送券
								</Link>
							</li>
							<li>
								<Link to={'/bargain-list?merchantCode='+localStorage.getItem('merchantCode')}>
								    砍价活动设置
								</Link>
							</li>
						</ul>
						</a>
					</li>
					<li>
						<a href="#click" className="menu">
							<h2 className="menu-title menu-title_4th">会员中心</h2>
							<ul className="menu-dropdown">
								<li>
									<Link to={'user-list?merchantCode='+localStorage.getItem('merchantCode')}>
								        用户列表
								    </Link>
								</li>
								<li>
									<Link to={'/vip-list?merchantCode='+localStorage.getItem('merchantCode')}>
								        会员列表
								    </Link>
								</li>
								<li>
									<Link to={'/comment-list?merchantCode='+localStorage.getItem('merchantCode')}>
								        评论管理
								    </Link>
								</li>
								<li>
									<Link to={'/marketing-entranceList?merchantCode='+localStorage.getItem('merchantCode')}>
								        会员卡营销列表
								    </Link>
								</li>
							</ul>
						</a>
					</li>
					<li>
						<a href="#click" className="menu">
							<h2 className="menu-title menu-title_5th">系统配置</h2>
							<ul className="menu-dropdown">
								<li>
									<Link to={'/orderSettingPick?merchantCode='+localStorage.getItem('merchantCode')}>
							            营业时间
							        </Link>
							        {/*<Link to={'/auditRefund?merchantCode='+localStorage.getItem('merchantCode')}  className="list-group-item" activeClassName="active">
							           	退款审核
							         </Link>
							       */}
								</li>
								<li>
									<Link to={'/create-wx-vip?merchantCode='+localStorage.getItem('merchantCode')}>
								        微信会员卡
								    </Link>
								</li>
								<li>
									<Link to={'/activity-config?merchantCode='+localStorage.getItem('merchantCode')}>
								        参数配置
								    </Link>
								</li>
								<li>
									<Link to={'/system-config?merchantCode='+localStorage.getItem('merchantCode')}>
								        系统配置
								    </Link>
								</li>
							</ul>
						</a>
					</li>
				</ul>
			</div>
		)
	}

}

export default Menu