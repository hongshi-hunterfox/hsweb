import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class BargainDetail extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      record:[],
    }
  }

  componentDidMount() {
    req
    .get('/uclee-backend-web/getBargainLog')
    .query({
        id: this.props.location.query.id
      })
    .end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
	
      this.setState({
        record: data.record
      })
    })
    
  }
  
 
    
  render() {
    var list = this.state.record.map((item, index) => {
      return (
        <tr key={index}>
          <td>{item.name}</td>
          <td>{item.price}</td>
          <td>{item.minPrice}</td>
          <td>{item.user}</td>
          <td>{item.bargainPrice}</td>
          <td>{item.surplusAmount}</td>
          <td>{item.number}</td>
        </tr>
      )
    })

    return (
      <DocumentTitle title="明细列表">
        <div className="user-list">
          	<div className="user-list-add">
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>活动名称</th>
                  <th>产品价格</th>
                  <th>最低砍价金额</th>
                  <th>发起人</th>
                  <th>已砍金额</th>
                  <th>剩余金额</th>
                  <th>帮砍人数</th>
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

export default BargainDetail
