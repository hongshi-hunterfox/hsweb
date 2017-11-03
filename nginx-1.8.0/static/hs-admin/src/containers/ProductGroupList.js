import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'
import { Link } from 'react-router'
class ProductGroupList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      productGroup:[],
      size:10
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/productGroup?tag='+ (this.props.location.query.tag?this.props.location.query.tag:'')).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        productGroup: data.productGroup
      })
    })
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
    window.location='/product-group-list?tag=' + e.target.value;
  }
  
  render() {
    var list = this.state.productGroup.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.groupName}</td>
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
               添加栏目
              </Link>
              
            </div>
            <div className='user-list-select'>
              <select name="tag" className='tag' onChange={this._click} value={this.props.location.query.tag?this.props.location.query.tag:''}>
                <option value=''>全部栏目</option>
                <option value="recommend">店铺推荐</option>
                <option value="hotProduct">店铺精品</option>
              </select>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>图片</th>
                  <th>所属区域</th>
                  <th>操作</th>
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

export default ProductGroupList
