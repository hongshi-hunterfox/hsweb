import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
import { Link } from 'react-router'

class CategoryList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      categories:[]
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/categoryList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        categories:data.categories
      })
    })
  }
  _del=(id)=>{
    var conf = confirm('确认要删除该分类吗？');
    if(conf){
      req.get('/uclee-backend-web/delCategory?categoryId='+id).end((err, res) => {
        if (err) {
          return err
        }
        var data = JSON.parse(res.text)
        if(data.result){
          window.location='/category-list'
        }else{
          alert(data.reason);
        }
      })
    }
  }
  render() {
    var list = this.state.categories.map((item, index) => {
      return (
        <tr key={index}>
          <td>{item.category}</td>
          <td>
            <Link to={'/editCategory?categoryId=' + item.categoryId} className="btn btn-primary">
            编辑
            </Link>
            <button className="btn btn-primary button-right" onClick={this._del.bind(this,item.categoryId)}>
            删除
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="分类列管理">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/editCategory'} className="btn btn-primary">
               添加分类
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>分类标题</th>
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

export default CategoryList
