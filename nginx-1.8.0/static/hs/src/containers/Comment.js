var Link = require('react-router').Link;
import Navi from './Navi'
var _ = require('lodash')
import './comment.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import ErrorMessage from './ErrorMessage'
var fto = require('form_to_object')
import { browserHistory } from 'react-router'
import req from 'superagent'
class Comment extends React.Component{

	constructor(props) {
		super(props)
		this.state={
			comment:{},
			err:'',
			delStar:0,
			serStar:0,
			quaStar:0,
			oauthId:'',
			point:0
			
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
							<input name="deliver" value={this.state.delStar} type='hidden'/>
							<input name="service" value={this.state.serStar} type='hidden'/>
							<input name="quality" value={this.state.quaStar} type='hidden'/>
							<div className="form-group">
								<textarea className="comment-textarea" rows="8" cols="20" placeholder="请填写你对本订单的评价" name='title' onChange={this._handleChange.bind(this, 'title')} onFocus={this._f}
								onBlur={this._b}>

								</textarea>

							</div>
							<ErrorMessage error={this.state.err} />
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

	_f = () => {
		document.getElementById('hs-navi').style.display = 'none'
		document.getElementById('hs-submit-btn').style.position = 'static'
	}

	_b = () => {
		document.getElementById('hs-navi').style.display = 'block'
		document.getElementById('hs-submit-btn').style.position = 'fixed'
	}
	_handleChange = (key, e) => {
		var comment = Object.assign({}, this.state.comment)
		comment[key] = e.target.value
		this.setState({
			comment: comment
		})
	}

	_handleSubmit = e => {
		e.preventDefault()
		var data = fto(e.target)
		data.orderSerialNum=this.props.location.query.orderSerialNum;
		if (!data.orderSerialNum) {
			alert("评论订单信息有误，请返回重新进入");
			window.location='order-list'
			return false
		}
		console.log(data);
		if (!data.deliver||data.deliver==='0') {
			this.setState({
				err: '请为送货速度打分'
			})
			return false
		}
		if (!data.service||data.service==='0') {
			this.setState({
				err: '请为服务态度打分'
			})
			return false
		}
		if (!data.quality||data.quality==='0') {
			this.setState({
				err: '请为产品质量打分'
			})
			return false
		}
		/*if (!data.title) {
			this.setState({
				err: '请填写评论内容'
			})
			return false
		}*/
		req
			.get('/uclee-user-web/zengSong')
			.end((err, res) => {
				if (err) {
					return err
				}
			})
		req.post('/uclee-user-web/commentHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }
        var resJson = JSON.parse(res.text)
        if (!resJson.result) {
          alert(resJson.reason);
          return ;
        }else{

          window.location = '/order-list'
        }
      })
	}
}

export default Comment