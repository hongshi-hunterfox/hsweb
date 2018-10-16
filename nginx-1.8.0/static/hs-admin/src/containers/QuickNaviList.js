import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class QuickNaviList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      quickNavi:[],
      size:10
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/quickNaviList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        quickNavi: data.quickNavi
      })
    })
  }

  _delHandler=(id)=>{
    req.get('/uclee-backend-web/delQuickNavi?naviId='+id).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/quick-navi-list'
    })
  }
   _newHandler=()=>{
    window.location="/editQuickNavi";
  }
  _edit=(url)=>{
    window.location=url;
  }
  
  render() {
    var list = this.state.quickNavi.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.imageUrl} alt="" width="50"/></td>
          <td>{item.title}</td>
          <td>
            <button className='btn btn-primary' style={{margin: '0 5px'}} onClick={this._edit.bind(this,'/editQuickNavi?naviId='+ item.naviId)}>编辑</button>
            <button className='btn btn-danger' style={{margin: '0 5px'}} onClick={this._delHandler.bind(this,item.naviId)}>   删除</button>
            <button className='btn btn-primary' style={{margin: '0 5px'}} onClick={this._edit.bind(this,'/quick-navi-product?naviId='+item.naviId)}>   查看关联产品</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="首页快速导航列表">
        <div className="user-list">
            <button className="btn btn-primary pull-right" onClick={this._newHandler}>新增</button>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>图片</th>
                  <th>标题</th>
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

export default QuickNaviList
