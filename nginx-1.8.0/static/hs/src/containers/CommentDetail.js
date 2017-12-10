var Link = require('react-router').Link;
import Navi from './Navi'
var _ = require('lodash')
import './comment-detail.css'
import React from 'react'
import DocumentTitle from 'react-document-title'
import ErrorMessage from './ErrorMessage'
var fto = require('form_to_object')
import req from 'superagent'
class CommentDetail extends React.Component{

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
		req
        .get('/uclee-user-web/comment?orderSerialNum=' + (this.props.location.query.orderSerialNum?this.props.location.query.orderSerialNum:''))
        .end((err, res) => {
          if (err) {
            return err
          }
          var res = JSON.parse(res.text)
          this.setState({
            comment: res.comment,
            delStar:res.comment.deliver,
            serStar:res.comment.service,
            quaStar:res.comment.quality
          })
          console.log(this.state);
        })
	}
	render() {
		return (
			<DocumentTitle title="订单评论">
				<div className='col-xs-12 trim-col'>
					<div className='comment-detail'>
						<div className='comment-detail-star'>
							<label className='lable'>送货速度：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.delStar>=1?'on':'')} ></li>
				                 <li className={"item " + (this.state.delStar>=2?'on':'')} ></li>
				                 <li className={"item " + (this.state.delStar>=3?'on':'')} ></li>
				                 <li className={"item " + (this.state.delStar>=4?'on':'')} ></li>
				                 <li className={"item " + (this.state.delStar>=5?'on':'')} ></li>
				             </ul>
						</div>
						<div className='comment-detail-star'>
							<label className='lable'>服务态度：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.serStar>=1?'on':'')} ></li>
				                 <li className={"item " + (this.state.serStar>=2?'on':'')} ></li>
				                 <li className={"item " + (this.state.serStar>=3?'on':'')} ></li>
				                 <li className={"item " + (this.state.serStar>=4?'on':'')} ></li>
				                 <li className={"item " + (this.state.serStar>=5?'on':'')} ></li>
				             </ul>
						</div>
						<div className='comment-detail-star'>
							<label className='lable'>产品质量：</label>
				             <ul className="starList">
				                 <li className={"item " + (this.state.quaStar>=1?'on':'')}></li>
				                 <li className={"item " + (this.state.quaStar>=2?'on':'')}></li>
				                 <li className={"item " + (this.state.quaStar>=3?'on':'')}></li>
				                 <li className={"item " + (this.state.quaStar>=4?'on':'')}></li>
				                 <li className={"item " + (this.state.quaStar>=5?'on':'')}></li>
				             </ul>
						</div>
						<div className='comment-detail-content'>
							<label className='lable'>用户评论：</label>
							{this.state.comment.title}
						</div>
						{
							this.state.comment.backTitle?
							<div className='comment-detail-content'>
								<label className='lable'>商家回复：</label>
								{this.state.comment.backTitle}
							</div>
							:null
						}
					</div>
				</div>
			</DocumentTitle>
			);
	}
	
}

export default CommentDetail