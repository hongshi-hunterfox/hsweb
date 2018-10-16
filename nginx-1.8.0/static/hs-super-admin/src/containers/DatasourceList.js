import React from 'react'
import DocumentTitle from 'react-document-title'
import './datasource-list.css'
import req from 'superagent'
import { Link } from 'react-router'

class DatasourceList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      datasource:[]
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/dataSourceList')
    .query({
      "merchantCode":"master"
    })
    .end((err, res) => {
      if (err) {
        return err
      }
      this.setState({
        datasource: res.body
      })
    })
  }
  
  render() {
    var list = this.state.datasource.map((item, index) => {
      return (
        <tr key={index}>
          <td>{item.merchantName}</td>
          <td>{item.merchantCode}</td>
          <td>{item.driverClassName}</td>
          <td>{item.url}</td>
          <td>{item.username}</td>
          <td>{item.password}</td>
          <td>
            <Link to={'/editDatasource?id=' + item.id} className="btn btn-primary">
            编辑
            </Link>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="数据源列表">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/addDatasource'} className="btn btn-primary">
               添加数据源
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>公司名称</th>
                  <th>公司代码</th>
                  <th>driver名字</th>
                  <th>url</th>
                  <th>用户名</th>
                  <th>密码</th>
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

export default DatasourceList
