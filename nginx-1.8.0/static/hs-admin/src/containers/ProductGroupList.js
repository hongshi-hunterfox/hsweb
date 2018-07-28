import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'
import { Link } from 'react-router'
var fto = require('form_to_object')
import {
    Modal,
    ModalHeader,
    ModalTitle,
    ModalClose,
    ModalBody,
    ModalFooter
} from 'react-modal-bootstrap'
class ProductGroupList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      productGroup:[],
      all:[],
      productGroupLink:{},
      size:10,
      isOpen:false,
      groupId:0,
      groupName:'',
      productId:0,
      position :0
    }
  }

  componentDidMount() {
  	 req.get('/uclee-backend-web/getAll').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        all:data.all
      })
    })
    req.get('/uclee-backend-web/productGroup?groupName='+ (this.props.location.query.groupName?this.props.location.query.groupName:'')).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        productGroup: data.productGroup
      })
    })
  }

  openModal = (groupId,productId,text) => {
    this.setState({
      isOpen: true,
      groupId:groupId,
      productId:productId,
      position:text
    });
  }

  hideModal = () => {
    this.setState({
      isOpen: false
    });
  }

  _handleChange = (key, e) => {
    var productGroupLink = Object.assign({}, this.state.productGroupLink)
    productGroupLink[key] = e.target.value
    this.setState({
      productGroupLink: productGroupLink,
      position:e.target.value
    })
  }


  _handleSubmit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data);
    if (!data.groupId) {
      alert("group参数错误");
      return false
    }
    if(!data.productId){
      alert("product参数错误");
    }
    if(/^([1-9]\d*|[0]{1,1})$/ .test(data.position)){
      var conf = confirm('确认要修改该排序值吗？');
      if(conf){
        req.get('/uclee-backend-web/productGroupSortPosition?groupId='+data.groupId+'&productId=' + data.productId+'&&position='+data.position).end((err, res) => {
          if (err) {
            return err
          }
          window.location='/product-group-list?tag=' + (this.props.location.query.tag?this.props.location.query.tag:'')
        })
      }
    }else{
      alert("您輸入的不是数字！");
    }
  }


  _del=(groupId,productId)=>{
    req.get('/uclee-backend-web/delProductGroup?groupId='+groupId+'&productId=' + productId).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/product-group-list?tag=' + (this.props.location.query.tag?this.props.location.query.tag:'')
    })
  }
  _edit=(url)=>{
    window.location=url;
  }
  _click=(e)=>{
    window.location='/product-group-list?groupName=' + e.target.value;
  }
  
  render() {
    var list = this.state.productGroup.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.groupName}</td>
          <td onClick={this.openModal.bind(this,item.groupId,item.productId,item.position)}>{item.position}</td>
          <td>
            <button onClick={this._edit.bind(this,'/editProductGroup?groupId='+ item.groupId +'&productId=' + item.productId)}>编辑</button>
            <button onClick={this._del.bind(this,item.groupId,item.productId)}>    删除</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="首页产品列表">
        <div className="user-list">
          <div className="user-list-add">
              <Link to={'/editProductGroup/'} className="btn btn-primary">
               添加模块产品
              </Link>
              
            </div>
            <div className='user-list-select'>
               <select name="groupName" className='groupName' onChange={this._click} value={this.props.location.query.groupName?this.props.location.query.groupName:''}>
                <option value=''>全部模块</option>
                {
                  this.state.all.map((item,index)=>{
                    return(
                      <option value={item.groupName} key={index}>{item.groupName}</option>
                    )
                  })
                }
              </select>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>图片</th>
                  <th>所属区域</th>
                  <th>排序值</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
          <div>说明：要修改排序值，请点击该数值所在的表格。数值越小，则在店铺推荐和店铺精品模块，该产品显示的越靠前！</div>

          <Modal isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
            <ModalHeader>
              <ModalClose onClick={this.hideModal}/>
              <ModalTitle>输入排序值</ModalTitle>
            </ModalHeader>
            <ModalBody>
              <form
                  className="form-horizontal comment-form"
                  onSubmit={this._handleSubmit}
                  ref="f"
                  >
                <input name="groupId" value={this.state.groupId} type='hidden'/>
                <input name="productId"   value={this.state.productId} type='hidden'/>
                <div className="form-group">
                  <input
                      type="text"
                      name="position"
                      value={this.state.position}
                      onChange={this._handleChange.bind(this, 'position')}
                      />
                </div>

                <button className='btn btn-primary' type="submit">
                  保存
                </button>
              </form>

            </ModalBody>
          </Modal>

        </div>
      </DocumentTitle>
    )
  }
}

export default ProductGroupList
