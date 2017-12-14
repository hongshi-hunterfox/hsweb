import React from 'react'
import "./home.css"
import DocumentTitle from 'react-document-title'
import './boss-center.css'
import req from 'superagent'
import './member-card.css'
var AssistantUtil = require('../utils/AssistantUtil.js');
//var echarts = require('../utils/echarts.simple.min.js');
class Assistant extends React.Component {
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
      .get('/uclee-user-web/assistant')
      .query({
        QueryName:this.state.QueryName,   
        itema:this.state.itema 
      })
      .end((err, res) => {
     	if (err) {
          info:err.ToString();
       	}
        this.setState({
          	info:res.body.info,
            itema:res.body.itema
        })
      })
        var data = JSON.stringify(this.state.itema);
        AssistantUtil.PullListToTable(data,"myview");
        //var data = [{"Sales":1234,"SortName":"101","GoodsName":"我的产品"},{"Sales":9,"SortName":"面包类","GoodsName":"盐之花黄油卷"}];
		AssistantUtil.PullListToTable(data,"myview");
		document.getElementById('opt').innerHTML = 'sadf'
}
render() {
         var data =JSON.stringify(this.state.itema); 
//      	AssistantUtil.Table(data,"myview");
  return (
      <DocumentTitle title="小助手">
        <div className="boss-center">
        	<img src='/images/data.png' alt=""/>
	        <div className='boss-center-bottom'>
		        <div className="member-card-item">
		         	<p>POST QueryName:{this.state.QueryName}</p>
		            <p>Request info:{this.state.info}</p>
					<table id="myview"></table>
					<p id='opt'>rrrr</p>
					<p>{data}</p>
		        </div>
	        </div>
        </div>
      </DocumentTitle>
    )}
}
export default Assistant
