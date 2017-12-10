import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
import { Link } from 'react-router'

class ProductList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      products:[],
      categories:[]
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
  render() {
    var list = this.state.products.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.title}</td>
          <td>{item.category}</td>
          <td>
            <Link to={'/product/' + item.productId} className="btn btn-primary">
            编辑
            </Link>
            <button className="btn btn-primary button-right" onClick={this._del.bind(this,item.productId)}>
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
                  <th>
                    <a href=""></a>编辑</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
        </div>
      </DocumentTitle>
    )
  }
}

export default ProductList
