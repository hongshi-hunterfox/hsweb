var Link = require('react-router').Link;
import Navi from './Navi'
var _ = require('lodash')
import './comment.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import ErrorMessage from './ErrorMessage'
class Comment extends React.Component{

	constructor(props) {
		super(props)
		this.state={
			comment:{},
			err:'',
			delStar:0,
			serStar:0,
			quaStar:0
		}
	}

	componentDidMount(){
		
	}
	_delStarChange=(num)=>{
		this.setState({
			delStar:num
		})
	}
	_serStarChange=(num)=>{
		this.setState({
			serStar:num
		})
	}
	_quaStarChange=(num)=>{
		this.setState({
			quaStar:num
		})
	}
	render() {
		return (
			<DocumentTitle title="订单评论">
				<div className='col-xs-12 trim-col'>
					<div className='comment'>
						<div className='comment-star'>
							<label className='lable'>送货速度：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.delStar>=1?'on':'')} onClick={this._delStarChange.bind(this,1)}></li>
				                 <li className={"item " + (this.state.delStar>=2?'on':'')} onClick={this._delStarChange.bind(this,2)}></li>
				                 <li className={"item " + (this.state.delStar>=3?'on':'')} onClick={this._delStarChange.bind(this,3)}></li>
				                 <li className={"item " + (this.state.delStar>=4?'on':'')} onClick={this._delStarChange.bind(this,4)}></li>
				                 <li className={"item " + (this.state.delStar>=5?'on':'')} onClick={this._delStarChange.bind(this,5)}></li>
				             </ul>
						</div>
						<div className='comment-star'>
							<label className='lable'>服务态度：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.serStar>=1?'on':'')} onClick={this._serStarChange.bind(this,1)}></li>
				                 <li className={"item " + (this.state.serStar>=2?'on':'')} onClick={this._serStarChange.bind(this,2)}></li>
				                 <li className={"item " + (this.state.serStar>=3?'on':'')} onClick={this._serStarChange.bind(this,3)}></li>
				                 <li className={"item " + (this.state.serStar>=4?'on':'')} onClick={this._serStarChange.bind(this,4)}></li>
				                 <li className={"item " + (this.state.serStar>=5?'on':'')} onClick={this._serStarChange.bind(this,5)}></li>
				             </ul>
						</div>
						<div className='comment-star'>
							<label className='lable'>产品质量：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.quaStar>=1?'on':'')} onClick={this._quaStarChange.bind(this,1)}></li>
				                 <li className={"item " + (this.state.quaStar>=2?'on':'')} onClick={this._quaStarChange.bind(this,2)}></li>
				                 <li className={"item " + (this.state.quaStar>=3?'on':'')} onClick={this._quaStarChange.bind(this,3)}></li>
				                 <li className={"item " + (this.state.quaStar>=4?'on':'')} onClick={this._quaStarChange.bind(this,4)}></li>
				                 <li className={"item " + (this.state.quaStar>=5?'on':'')} onClick={this._quaStarChange.bind(this,5)}></li>
				             </ul>
						</div>
						<div className='comment-content'>
							<form
								className="form-horizontal comment-form"
								onSubmit={this._handleSubmit}
								ref="f"
							>
							<div className="form-group">
								<textarea className="comment-textarea" rows="8" cols="20" placeholder="请写下对订单的宝贵意见" name='title' onChange={this._handleChange.bind(this, 'title')}>

								</textarea>

							</div>
							<ErrorMessage error={this.state.error} />
							<button
								type="submit"
								className="btn btn-primary btn-block comment-button"
								id="hs-submit-btn"
							>
								提交
							</button>
							</form>
						</div>
					</div>
				</div>
			</DocumentTitle>
			);
	}
	_handleChange = (key, e) => {
		var comment = Object.assign({}, this.state.comment)
		comment[key] = e.target.value
		this.setState({
			comment: comment
		})
	}
}

export default Comment