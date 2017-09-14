import React from 'react'
import DocumentTitle from 'react-document-title'
import './comment-list.css'
import req from 'superagent'
import { Link } from 'react-router'
import ErrorMessage from '../components/ErrorMsg'
var fto = require('form_to_object')
import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap'
class CommentList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      comments:[],
      isOpen:false,
      comment:{},
      commentId:0
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/commentList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        comments: data.comment
      })
    })
  }
  openModal = (id) => {
    this.setState({
      isOpen: true,
      commentId:id
    });
  }
   
  hideModal = () => {
    this.setState({
      isOpen: false
    });
  }
  _handleChange = (key, e) => {
    var comment = Object.assign({}, this.state.comment)
    comment[key] = e.target.value
    this.setState({
      comment: comment
    })
  }

  render() {
    var list = this.state.comments.map((item, index) => {
      return (
        <tr key={index}>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>
            <div className='comment-list-order'>
            {
              item.order&&item.order.items?item.order.items.map((item1,index1)=>{
                return(
                  <div className='comment-list-order-item' key={index1}>
                    <img src={item1.imageUrl} className='image' width='30px' height='30px'/>
                    <span className='title'> {item1.title} </span>
                  </div>
                )
              }):null
            }
            </div>
          </td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.title}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.deliver}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.service}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.quality}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.timeStr}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.backTitle}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.backTimeStr}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>
            <button className="btn btn-primary" onClick={this.openModal.bind(this,item.id)}>
              回复
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="评论列表">
        <div className="comment-list">
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>订单信息</th>
                  <th>评论内容</th>
                  <th>送货速度评分</th>
                  <th>服务态度评分</th>
                  <th>产品质量评分</th>
                  <th>评论时间</th>
                  <th>后台回复</th>
                  <th>回复时间</th>
                  <th>
                    <a href=""></a>操作</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
            <Modal isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
              <ModalHeader>
                <ModalClose onClick={this.hideModal}/>
                <ModalTitle>评论回复</ModalTitle>
              </ModalHeader>
              <ModalBody>
                <form
                    className="form-horizontal comment-form"
                    onSubmit={this._handleSubmit}
                    ref="f"
                  >
                  <input name="id" value={this.state.commentId} type='hidden'/>
                  <div className="form-group">
                    <textarea  className="comment-textarea" rows='10' style={{overflow:'auto',width:'96%',marginLeft:'2%' }}  placeholder="请写下对订单的宝贵意见" name='backTitle' onChange={this._handleChange.bind(this, 'backTitle')}>

                    </textarea>
                  </div>
                  <ErrorMessage error={this.state.err} />
                  <button className='btn btn-default' onClick={this.hideModal}>
                  Close
                </button>
                <button className='btn btn-primary' type="submit">
                  Save changes
                </button>
              </form>
              
              </ModalBody>
            </Modal>
        </div>
      </DocumentTitle>
    )
  }
  _handleSubmit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data);
    if(!data.id){
      alert("参数信息有误，请关掉重新回复");
      return;
    }
    if (!data.backTitle) {
      alert("请填写回复内容");
      return false
    }
    req.post('/uclee-backend-web/commentBackHandler').send(data).end((err, res) => {
        if (err) {
          return err
        }
        var resJson = JSON.parse(res.text)
        if (!resJson.result) {
          alert(resJson.reason);
          return ;
        }else{
          req.get('/uclee-backend-web/commentList').end((err, res) => {
            if (err) {
              return err
            }
            var data = JSON.parse(res.text)
            this.setState({
              comments: data.comment,
              isOpen:false
            })
          })
        }
      })
  }
}

export default CommentList