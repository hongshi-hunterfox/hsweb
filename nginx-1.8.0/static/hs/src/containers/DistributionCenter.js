/* global wx */
import React from 'react'
import DocumentTitle from 'react-document-title'
import './distribution.css'
import Navi from './Navi'
var request = require('superagent')
class DistributionCenter extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			money: 0,
			isShareShow: false,
			serialNum: '',
			invitator:{},
			allowRecharge:true,
			cVipCode:''
		}
	}

	componentDidMount() {
	request
      .get('/uclee-user-web/getVipInfo')
      .end((err, res) => {
        if (err) {
          return err
        }

        if (res.body) {
          this.setState({
          	allowRecharge:res.body.allowRecharge,
          	cVipCode:res.body.cVipCode,
          })
          if(!res.body.cVipCode){
			alert("请先绑定会员卡");
			window.location='/member-setting';
			return;
		}
        }
      })

		request.get('/uclee-user-web/distCenter').end((err, res) => {
			if (err) {
				return err
			}
			var resJson = JSON.parse(res.text);
			console.log(resJson);
			this.setState({
				money:resJson.money,
				serialNum:resJson.serialNum,
				invitator:resJson.invitator
			},()=>{
				if(this.props.location.query.serialNum!==null&&this.props.location.query.serialNum!==this.state.serialNum){
			      request
			        .get('/uclee-user-web/invitation?serialNum='+this.props.location.query.serialNum)
			        .end((err, res) => {
			          if (err) {
			            return err
			          }
			          window.location='/distribution-center?serialNum=' + this.state.serialNum+'&merchantCode='+localStorage.getItem('merchantCode');
			        })
			    }
			})
		})
		
	}

	render() {
		return (
			<DocumentTitle title="分销中心">
				<div className="distribution">
					<div className="distribution-top">
						<div className="left pull-left">
							<div className="money">收益余额</div>
							<div className="number">{this.state.money}</div>
						</div>
						<div className="transfer pull-right" onClick={this._transHandler}>转进会员卡</div>
					</div>
					<div className="distribution-text">
						<div className="top">
							分享    =    赚钱
						</div>
						<div className="bottom" >邀请好友赚取消费收益</div>
					</div>
					<div className="distribution-content">
						{
							this.state.invitator?
							<div
								className="item"
							>
								我的推荐人：<span className='invitator'>{this.state.invitator.name}</span>
							</div>:
							null
						}
						<div
							className="item"
							onClick={this._location.bind(this, '/distribution-user')}
						>
							分销团队列表<span className="fa fa-chevron-right right" />
						</div>
						<div
							className="item"
							onClick={this._location.bind(this, '/distribution-order')}
						>
							分销订单列表<span className="fa fa-chevron-right right" />
						</div>
					</div>
					<div className="distribution-intro">
						<div className='title'>分销教程：</div>
						<div className='content'>1.点击下方立即邀请按钮。</div>
						<div className='content'>2.点击图示所指向的右上角。</div>
						<div className='content'>3.选择发送给朋友或者分享到朋友圈即可。</div>
					</div>
					<div
						className="button"
						onClick={this._clickHandle.bind(this, true,this.state.serialNum)}
					>
						立即邀请
					</div>
					{this.state.isShareShow
						? <div
								className="distribution-hidden"
								onClick={this._clickHandle.bind(this, false)}
							>
								<img
									className="distribution-hidden-image"
									src="./images/share/share.png"
									alt=""
								/>
							</div>
						: null}
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
		)
	}
	_transHandler = () => {
		console.log(this.state)
		var conf = confirm('确定要转入到会员卡？');
		if(!this.state.cVipCode){
			alert("请先绑定会员卡");
			return;
		}
		if(!this.state.allowRecharge){
			alert("非充值类型会员卡不允许转入");
			return;
		}
		if(this.state.money<=0){
			alert("无可转入余额");
			return;
		}
		if(conf){
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
	_location = url => {
		window.location = url
	}

	_clickHandle = (b,serialNum) => {
		console.log(serialNum);
		if(serialNum!==null){
			var q = {}
			q.url = window.location.href.split('#')[0]
			request.get('/uclee-user-web/wxConfig').query(q).end(function(err, res) {
				var c = JSON.parse(res.text)
				wx.config({
					debug: false,
					appId: c.appId,
					timestamp: c.timestamp,
					nonceStr: c.noncestr,
					signature: c.signature,
					jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
				})
			})
			wx.ready(function() {
				console.log('进入')
				wx.onMenuShareAppMessage({
					title: '吃着蛋糕赚着钱，快来管理你自己的收益团队吧', // 分享标题
					desc: '你的团队你做主', // 分享描述
					link: 'wsc.in80s.com?serialNum=' + serialNum+'&merchantCode='+localStorage.getItem('merchantCode'), // 分享链接
					imgUrl: 'http://120.25.193.220/group1/M00/31/AC/eBnB3Fk1emuAHOamAABR62NdkwM71.file', // 分享图标
					success: function() {},
					cancel: function() {}
				})
				wx.onMenuShareTimeline({
					title: '吃着蛋糕赚着钱，快来管理你自己的收益团队吧', // 分享标题
					link: 'wsc.in80s.com?serialNum=' + serialNum+'&merchantCode='+localStorage.getItem('merchantCode'), // 分享链接
					imgUrl: 'http://120.25.193.220/group1/M00/31/AC/eBnB3Fk1emuAHOamAABR62NdkwM71.file', // 分享图标
					success: function() {},
					cancel: function() {}
				})
			})
		}
		this.setState({
			isShareShow:b
		})
	}
}

export default DistributionCenter
