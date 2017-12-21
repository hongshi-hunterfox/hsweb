import React from 'react'
import "./home.css"
import DocumentTitle from 'react-document-title'
import './boss-center.css'
import req from 'superagent'
//import './member-card.css'
import './table-view.css'
//var echarts = require('../utils/echarts.simple.min.js');
class TableView extends React.Component {
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
      .get('/uclee-user-web/TableView')
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
        //alert(data);
       var chtml = '<tr>';
       var title =  data[0];
       for (var key in title)
    {
        chtml = chtml + '<th>' +key + '</th>';
 };
       var chtml = chtml + '</tr>';
  	for(var i = 0; i < data.length; i++){
    	chtml += '<tr>';
    	for (var value in data[i]){
         	chtml += '<td>' + data[i][value] + '</td>';
    };
    	chtml += '</tr>';
};
		var table = document.getElementById('myview');
		table.innerHTML = chtml; 
      })
//      document.getElementById('opt').innerHTML = 'sadf';
}
render() {
//          var data =JSON.stringify(this.state.itema);
//          <p>POST QueryName:{this.state.QueryName}</p>
//		            <p>Request info:{this.state.info}</p>
  return (
      <DocumentTitle title="小助手">
      <div className='table-view'>
      	<img src='/images/data.png' alt=""/>
        <div className='table-view-color'>
					<table id='myview' className="table table-striped table-bordered"></table>
        </div>
        </div>
      </DocumentTitle>
    )}
}
export default TableView
