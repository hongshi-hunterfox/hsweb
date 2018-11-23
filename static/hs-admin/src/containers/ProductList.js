import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
var fto = require('form_to_object')
import { Link } from 'react-router'
import {
    Modal,
    ModalHeader,
    ModalTitle,
    ModalClose,
    ModalBody,
    ModalFooter
} from 'react-modal-bootstrap'

class ProductList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      products:[],
      product:{},
      isOpen:false,
      categories:[],
      productId:0,
      sortValue:0
    }
  }

  componentDidMount() {
    req.get('/uclee-product-web/productList?categoryId=' + (this.props.location.query.categoryId?this.props.location.query.categoryId:0)).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        products: data.products,
        categories:data.categories
      })
    })
  }

  openModal = (id,text) => {
    this.setState({
      isOpen: true,
      productId:id,
      sortValue:text
    });
  }

  hideModal = () => {
    this.setState({
      isOpen: false
    });
  }

  _handleChange = (key, e) => {
    var product = Object.assign({}, this.state.product)
    product[key] = e.target.value
    this.setState({
      product: product,
      sortValue:e.target.value
    })
  }


  _del=(id)=>{
    var conf = confirm('确认要删除该产品吗？');
    if(conf){
      req.get('/uclee-product-web/delProduct?productId='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/product-list'
      })
    }
  }
  _selectCat=(e)=>{
    window.location='product-list?categoryId='+e.target.value;
  }

  _handleSubmit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data);
    if(!data.id){
      alert("参数信息有误，请点击排序值");
      return;
    }
    if (!data.sortValue) {
      alert("请填写排序值");
      return false
    }
    if(/^([1-9]\d*|[0]{1,1})$/ .test(data.sortValue)){
      var conf = confirm('确认要修改该排序值吗？');
      if(conf){
        req.get('/uclee-product-web/sortProduct?productId='+data.id+'&&sortValue='+data.sortValue).end((err, res) => {
          if (err) {
            return err
          }
          window.location='/product-list'
        })
      }
    }else{
      alert("您輸入的不是数字！");
    }
}
  render(){
    var list = this.state.products.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.title}</td>
          <td>{item.category}</td>
          <td onClick={this.openModal.bind(this,item.productId,item.sortValue)}>{item.sortValue}</td>
          <td>
            <Link to={'/product/' + item.productId} className="btn btn-primary">
            编辑
            </Link>
            <button className="btn btn-danger button-right" onClick={this._del.bind(this,item.productId)}>
            删除
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="产品列表">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/product/'} className="btn btn-primary">
               添加产品
              </Link>
            </div>
            <div className='product-list-select'>
              <select name="tag" className='tag' onChange={this._selectCat}>
                <option value='0'>全部分类</option>
                {
                  this.state.categories.map((item,index)=>{
                    return(
                      <option value={item.categoryId} key={index}>{item.category}</option>
                    )
                  })
                }
              </select>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>产品图片</th>
                  <th>产品标题</th>
                  <th>产品分类</th>
                  <th>排序值</th>
                  <th>
                    <a href=""></a>编辑</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
          <div>说明：要修改排序值，请点击该数值所在的表格。数值越小，则点击全部商品和快捷导航商品时，该产品显示的越靠前！</div>

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
                <input name="id" value={this.state.productId} type='hidden'/>
                <div className="form-group">
                  <input
                      type="text"
                      name="sortValue"
                      value={this.state.sortValue}
                      onChange={this._handleChange.bind(this, 'sortValue')}
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

export default ProductList
