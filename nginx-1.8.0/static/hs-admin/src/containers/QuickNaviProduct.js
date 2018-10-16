import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
import { Link } from 'react-router'

class QuickNaviProduct extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      products:[]
    }
  }

  componentDidMount() {
    req.get('/uclee-product-web/quickNaviProduct?naviId=' + (this.props.location.query.naviId?this.props.location.query.naviId:0)).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        products: data.products
      })
    })
  }
  _del=(naviId,productId)=>{
    var conf = confirm('确认要删除该产品吗？');
    if(conf){
      req.get('/uclee-backend-web/delQuickNaviProduct?naviId='+naviId+'&productId='+productId).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/quick-navi-product?naviId='+naviId
      })
    }
  }
  render() {
    var list = this.state.products.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.title}</td>
          <td>
            <button className="btn btn-primary button-right" onClick={this._del.bind(this,this.props.location.query.naviId,item.productId)}>
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
              <Link to={'/edit-quick-navi-product?naviId=' + this.props.location.query.naviId} className="btn btn-primary">
               添加产品
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>产品图片</th>
                  <th>产品标题</th>
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

export default QuickNaviProduct
