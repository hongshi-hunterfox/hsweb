import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class MarketingEntranceList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      list:[],
      size:10
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/MarketingEntranceList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        list: data.list
      })
    })
  }

  _delHandler=(id)=>{
    req.get('/uclee-backend-web/deleteMarketingEntrance?id='+id).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/marketing-entranceList'
    })
  }
   _newHandler=()=>{
    window.location="/marketing-entranceDetail";
  }
  _edit=(url)=>{
    window.location=url;
  }
  
  render() {
    var list = this.state.list.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.imgUrl} alt="" width="50"/></td>
          <td>{item.name}</td>
          <td>{item.url}</td>
          <td>
            <button className='btn btn-primary' style={{margin: '0 5px'}} onClick={this._edit.bind(this,'/marketing-entranceDetail?id='+ item.id)}>编辑</button>
            <button className='btn btn-danger' style={{margin: '0 5px'}} onClick={this._delHandler.bind(this,item.id)}>   删除</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="会员卡营销入口列表">
        <div className="user-list">
            <button className="btn btn-primary pull-right" onClick={this._newHandler}>新增</button>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>图片</th>
                  <th>标题</th>
                  <th>地址url</th>
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

export default MarketingEntranceList
