import React from 'react'
import "./home.css"
import DocumentTitle from 'react-document-title'
import './boss-center.css'
import req from 'superagent'
import './data-view.css'
var DataViewUtil = require('../utils/DataViewUtil.js');

class DataView extends React.Component {
constructor(props) {
    super(props)
    this.state = {
       QueryName:this.props.location.query.QueryName,
       itema:[],
       info:''
    }
}
componentDidMount() {
    req
      .get('/uclee-user-web/DataView')
      .query({
        QueryName:this.state.QueryName
      })
      .end((err, res) => {
        if (err) {
          return err;
       	}
        this.setState({
          	info:res.body.info,
            itema:res.body.itema
        })
        var dat = JSON.stringify(this.state.itema);
        var data = JSON.parse(dat);
        DataViewUtil.FillTable(data,'myview');
      })
//      document.getElementById('opt').innerHTML = 'sadf';
}
render() {
//          var data =JSON.stringify(this.state.itema);
//          <p>POST QueryName:{this.state.QueryName}</p>
//		            <p>Request info:{this.state.info}</p>
  return (
      <DocumentTitle title="小助手">
      <div className='data-view'>
      	<img src='/images/data.png' alt=""/>
        <div className='data-view-color'>
					<table id='myview' className="table table-striped table-bordered"></table>
        </div>
        </div>
      </DocumentTitle>
    )}
}
export default DataView