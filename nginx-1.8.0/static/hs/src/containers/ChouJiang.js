require("./choujiang.css")
import React from "react"
import DocumentTitle from "react-document-title"
var ChoujiangUtil = require("../utils/ChoujiangUtil.js")
import Navi from './Navi'
class ChouJiang extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			rotate: 0,
			html: "",
			quick: false //开启快速回位模式
		}
	}
	componentDidMount() {

	}

	render() {
		return (
			<DocumentTitle title="抽奖">
				<div className="choujiang">
					<div className="choujiang-pan">
						<img
							className="choujiang-pan-body"
							src="../images/choujiang/body.png"
							alt=""
						/>
						<img
							className={'choujiang-pan-head rotate' + this.state.rotate + (this.state.quick ? ' quick' : '')}
							src="../images/choujiang/head.png"
							onClick={this._runrotate}
							alt=""
						/>
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
		)
	}

	_runrotate = () => {
		var q = this.props.location.query
		this.setState({
			quick: true,
			rotate: 0
		})

		ChoujiangUtil.getData(q, (res) => {
			this.setState({
				rotate: res.result,
				quick: false
			})
			alert("恭喜，所得产品已经发到您的账户中，请注意查收")
		})	
	}
}

export default ChouJiang
