import React from 'react'
import DocumentTitle from 'react-document-title'
import './distribution-user.css'
import Navi from './Navi'
var request = require('superagent');
class DistributionUser extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {
		   users:[]
	    }
	}

	componentDidMount() {
		var url = this.props.location.query.userId==null?'/uclee-user-web/distUser':'/uclee-user-web/distUser?userId='+this.props.location.query.userId;
	    request
	      .get(url)
	      .end((err, res) => {
	        if (err) {
	          return err
	        }

	        this.setState({
	          ...res.body
	        })
	      })
	  }
	  
	render(){
		console.log(this.props.location.query.userId);
		var users = this.state.users.map((item, index) => {
			return(
				<div className="distribution-user-item" key={index}>
					<div className="left">
						<div className="top">
							<img className="image" src={item.userProfile.image} alt=""/>
							<span className="name">{item.userProfile.name}</span>
						</div>
						<div className="bottom">邀请时间：{item.timeStr}</div>
					</div>
					{
						this.props.location.query.userId==null?
						<div className="right">
							<a href={"/distribution-user?userId="+item.invitedId}>查看下级</a>
						</div>:null
					}
					
				</div>
			);
		});
		return(
			<DocumentTitle title="分销团队">
				<div className="distribution-user" >
					{users}
					<div className="bottom-text">
						O(∩_∩)O 啊哦，没有更多邀请人啦~~~
					</div>
					<Navi query={this.props.location.query}/>	
				</div>
			</DocumentTitle>
		);
	}
}

export default DistributionUser