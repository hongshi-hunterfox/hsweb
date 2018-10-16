import React from 'react'
import DocumentTitle from 'react-document-title'
import './lottery.css'
import req from 'superagent'
import { random } from 'lodash'
import Navi from "./Navi"
class Lottery extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			ing: true,
			runing: false,
			run: false,
			now: 0,
			list: [],
			point:0,
			cost:0,
			isAllow:false,
			isInTime:false,
			startTime:'',
			endTime:'',
			limits:0,
			isSubscribe:false,
			rest:0
		}

		this.intID = null
		this.outID = null
	}

	componentDidMount() {
		req
			.get('/uclee-user-web/getLotteryConfig')
			.end((err, res) => {
				if (err) {
					return err
				}
				this.setState({
					ing: false,
					list: res.body.configs,
					point:res.body.point,
					cost:res.body.cost,
					isAllow:res.body.isAllow,
					isInTime:res.body.isInTime,
					startTime:res.body.startTime,
					endTime:res.body.endTime,
					limits:res.body.limits,
					isSubscribe:res.body.isSubscribe,
					rest:res.body.rest
				})
			})
	}

	componentWillUnmount() {
		clearInterval(this.intID)
		clearTimeout(this.outID)
	}

	render() {
		if (this.state.ing) {
			return <div/>
		}

		var text = this.state.list[this.state.now].text

		return (
			<DocumentTitle title="抽奖">
				<div className="lottery">
					<div className="lottery-info">
						<div className="point">剩余积分：<span className="red">{this.state.point}</span></div>
						<div className="point">{this.state.cost}积分/次</div>
					</div>
					<div className="lottery-time">
							抽奖时间：<div>{this.state.startTime}至{this.state.endTime}</div>
					</div>
					<div className="lottery-limits">每人每天限制次数：{this.state.limits}</div>
					<div className="lottery-limits">剩余抽奖次数：{this.state.rest}</div>
					<div className="lottery-items">
						{
							this.state.runing ?
							<div>{text}</div>
							:
							(
								this.state.run ?
								<div>{text}</div>
								:
								<div>点击按钮开始抽奖</div>
							)
						}
					</div>

					<div className="lottery-btn-wrap">
						<button type="button" className="lottery-btn" onClick={this._run} disabled={this.state.runing}>
							<span>开始</span>
							<span>抽奖</span>
						</button>
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
			)
	}

	_run = () => {
		if(!this.state.isSubscribe){
			alert("请先关注公众号继续抽奖");
			return;
		}

		if(!this.state.isAllow){
			alert('今天抽奖已经超过限制次数，再接再厉哦~~');
			return ;
		}
		if(!this.state.isInTime){
			alert('还没到开放时间，敬请期待');
			return ;
		}
		if(!this.state.point&&this.state.point!==0){
			alert('请先绑定会员');
			return ;
		}
		if(this.state.point<this.state.cost){
			alert('积分不足');
			return ;
		}
		this.setState({
			run: true,
			runing: true
		})

		this.outID = setTimeout(() => {
			clearInterval(this.intID)

			this.setState({
				runing: false,
				now: random(0, this.state.list.length - 1)
			}, () => {
				req
					.get('/uclee-user-web/lotteryHandler?configCode='+this.state.list[this.state.now].code)
					.end((err, res) => {
						if (err) {
							return err
						}
						if(res.body.result){
							alert(res.body.text);
							window.location='/lottery';
						}else{
							alert("网络繁忙，请稍后再试");
						}
					})
			})
		}, 5000)

		this.intID = setInterval(() => {
			this.setState((prevState) => {
				if (prevState.now < this.state.list.length - 1) {
					return {
						now: prevState.now + 1
					}
				}
				return {
					now: 0
				}
			})
		}, 50)

		
	}
}

export default Lottery
