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
        QueryName:this.state.QueryName        
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
      var data =[{"Sales":1234,"SortName":"101","GoodsName":"我的产品"},{"Sales":9,"SortName":"面包类","GoodsName":"盐之花黄油卷"}];
		AssistantUtil.Table(data,"myview");
}
render() {
//      var items = this.state.itema.map((item,index)=>{
//      return (         
//              <div>{JSON.stringify(item)}</div>   
//      );
//      })
//    var data =JSON.stringify(this.state.itema);
  return (
      <DocumentTitle title="小助手">
        <div className="boss-center">
        	<img src='/images/data.png' alt=""/>
        <div className='boss-center-bottom'>
        <div className="member-card-item">
         	<p>POST QueryName:{this.state.QueryName}</p>
            <p>Request info:{this.state.info}</p>
			<table id="myview" className="table table-bordered user-list"></table>
        </div>
        </div>
        </div>
      </DocumentTitle>
    )}
}
export default Assistant
