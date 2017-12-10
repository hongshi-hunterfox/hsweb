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
      var data = 'ctitle';
      AssistantUtil.Table(data,'myview');
 }
//				<script type="text/javascript">
//					var data = '{ctitle}';
//          		PullListToTable(data,'myview');
//</script>
render() {
//      var items = this.state.itema.map((item,index)=>{
//      return (         
//              <div>{JSON.stringify(item)}</div>   
//      );
//      })
       var ctitle = JSON.stringify(this.state.itema);
       var data = ctitle;
  return (
      <DocumentTitle title="小助手">
        <div className="boss-center">
        	<img src='/images/data.png' alt=""/>
        <div className='boss-center-bottom'>
        <div className="member-card-item">
         	<p>POST QueryName:{this.state.QueryName}</p>
            <p>Request info:{this.state.info}</p>
			<table id='myview' className="table table-bordered user-list"></table>
			<p>{ctitle}</p>
        </div>
        </div>
        </div>
      </DocumentTitle>
    )}
}
export default Assistant
