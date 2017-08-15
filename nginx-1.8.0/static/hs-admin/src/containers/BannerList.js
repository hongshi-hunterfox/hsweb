import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class BannerList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      banner:[],
      size:10
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/bannerList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        banner: data.banner
      })
    })
  }

  _delHandler=(id)=>{
    req.get('/uclee-backend-web/delBanner?id='+id).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/banner-list'
    })
  }
   _newHandler=()=>{
    window.location="/editBanner";
  }
  _edit=(url)=>{
    window.location=url;
  }
  
  render() {
    var list = this.state.banner.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.imageUrl} alt="" width="100"/></td>
          <td>{item.link}</td>
          <td>
            <button onClick={this._edit.bind(this,'/editBanner?id='+ item.id)}>编辑</button>
            <button onClick={this._delHandler.bind(this,item.id)}>   删除</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="首页banner列表">
        <div className="user-list">
            <button className="btn btn-primary pull-right" onClick={this._newHandler}>新增</button>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>图片</th>
                  <th>链接</th>
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

export default BannerList
