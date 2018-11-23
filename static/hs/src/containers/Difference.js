import React from 'react'
import "./home.css"
import DocumentTitle from 'react-document-title'
import req from 'superagent'
import './data-view.css'
var DataViewUtil = require('../utils/DataViewUtil.js');
// 引入 ECharts 主模块
var echarts = require('echarts/lib/echarts');
// 引入柱状图
require('echarts/lib/chart/bar');
// 引入提示框和标题组件
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/component/legend');

class Difference extends React.Component {
constructor(props) {
    super(props)
    this.state = {
       QueryName:this.props.location.query.QueryName,
       phone:localStorage.getItem('napaStorePhone'),
       hsCode:localStorage.getItem('hsCode'),
       napaStores:[],
       itema:{},
       riqi:'',
       chae:'',
       storename:''
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
        this.setState({
            itema:res.body.itema,
            napaStores:res.body.napaStores,
            colsName:res.body.colsName,
            riqi:res.body.riqi,
            chae:res.body.chae,
            storename:res.body.storename
        })

        var dat = JSON.stringify(this.state.itema);
        var data = JSON.parse(dat);
        var colsN = JSON.stringify(this.state.colsName);
        var colsNames = JSON.parse(colsN);
		colsNames.sort(function(a, b){
            return a.sort - b.sort;
        });
        DataViewUtil.FillTable(data,colsNames,'myview');
        var ss = this.state.riqi.split(",");
        var bb = this.state.chae.split(",");
        let myChart = echarts.init(this.refs.waterall);
		myChart.setOption({
			color: ['#3398DB'],
			title: { text: this.state.storename },
			tooltip: {},
    		legend: {
                data:['营业差额']
    		},
			xAxis: {
				axisLabel: {
		        	interval:0,
		        	rotate:40
		       },
		        show : ss.length <= 11 ? true : false ,
				data: ss
			},
			yAxis: {},
			series: [
				{
					name: '营业差额',
					type: 'bar',
					data: bb
				}
			]
		});
    })
}


 render(){
 	
 	   var option = this.state.napaStores.map((item,index)=>{
        return(
          <option key={index} value={item.hsCode} selected={this.state.hsCode===item.hsCode?'selected':null}>{item.storeName}</option>
        );  
    })
   return (
    <DocumentTitle title={this.props.location.query.QueryName}>
      <div className='data-view'>
        <img src='/images/data.png' style={{width: '100%'}} alt=""/>
        <div ref="waterall" style={{width: '100%',  height: '400px'}}></div>
        <label className="control-label col-md-3" ><h4>表格数据：</h4></label>
        <div className='data-view-color'>
        <table id='myview' className="table table-striped table-bordered"></table>
        </div>
        
      </div>
    </DocumentTitle>
   )
 }
}
export default Difference