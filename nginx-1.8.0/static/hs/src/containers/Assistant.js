import React from 'react'
import DocumentTitle from 'react-document-title'
import './boss-center.css'
import req from 'superagent'
import './member-card.css'

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
     
}


   
render() {
	var cTitle = this.state.itema.map((item,index)=>{
		return (
			<div key={index}>
			 <div>{item.SortName} {item.GoodsName} {item.Sales}</div>
			 <div>{item.来源} {item.业务ID} {item.结算ID} {item.结算金额} {item.建立时间}</div>
			 <div>{item.序号} {item.营业额} {item.现金}</div>
			 <div>{item.lastversion}</div>
			</div>
		)
	})
  return (
      <DocumentTitle title="小助手">
        <div className="boss-center">
        <img src='/images/data.png' alt=""/>
            <div className='boss-center-bottom'>
            <div className="member-card-item">
            	<p>POST QueryName:{this.state.QueryName}</p>
            	<p>Request info:{this.state.info}</p>
                <p>{cTitle}</p>
            </div>
            </div>
        </div>
      </DocumentTitle>
    )
}
}
export default Assistant
