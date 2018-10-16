import React from 'react'
import "./home.css"
import DocumentTitle from 'react-document-title'
import req from 'superagent'
import './data-view.css'
var DataViewUtil = require('../utils/DataViewUtil.js');

class DataView extends React.Component {
constructor(props) {
    super(props)
    this.state = {
       QueryName:this.props.location.query.QueryName,
       phone:localStorage.getItem('napaStorePhone'),
       hsCode:localStorage.getItem('hsCode'),
       napaStores:[],
       itema:[],
       info:''
    }
}
componentDidMount() {
    req
      .get('/uclee-user-web/DataView')
      .query({
        QueryName:this.state.QueryName,
        phone: this.state.phone,
        hsCode:this.state.hsCode
      })
      .end((err, res) => {
        if (err) {
          return err;
       	}
        if(this.state.QueryName === 'BusinessError'){
        	window.location="/Difference?QueryName=BusinessError"
        	return;
        }
        this.setState({
          	info:res.body.info,
            itema:res.body.itema,
            napaStores:res.body.napaStores,
            colsName:res.body.colsName
        })
        var dat = JSON.stringify(this.state.itema);
        var data = JSON.parse(dat);
        var colsN = JSON.stringify(this.state.colsName);
        var colsNames = JSON.parse(colsN);
		colsNames.sort(function(a, b){
            return a.sort - b.sort;
        });
        DataViewUtil.FillTable(data,colsNames,'myview');
      })
}


 render(){
 	   var option = this.state.napaStores.map((item,index)=>{
        return(
          <option key={index} value={item.hsCode} selected={this.state.hsCode===item.hsCode?'selected':null}>{item.storeName}</option>
        );  
    })
   return (
    <DocumentTitle title="小助手">
      <div className='data-view'>
        <img src='/images/data.png' style={{width: '100%'}} alt=""/>
        <label className="control-label col-md-3" ><h4>表格数据：</h4></label>
        <div className='data-view-color'>
        <table id='myview' className="table table-striped table-bordered"></table>
        </div>
      </div>
    </DocumentTitle>
   )
 }
}
export default DataView