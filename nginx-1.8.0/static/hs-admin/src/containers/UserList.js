import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class UserList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      users:[],
      pageinfo:{},
      pn: this.props.location.query.pn,
      pagenums:0,
      pagenum:0,
      pagesize:[]
    }
  }

  componentDidMount() {
    req
    .get('/uclee-backend-web/userList')
    .query({
        pn: this.state.pn
      })
    .end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
			var pagesize=[];
     	for(var i=0;i<data.size;i++){
		    	pagesize.push(i+1)	
		  }
      this.setState({
        users: data.users,
        pagenums: data.pagenums,
        size: data.size,
        pagenum: data.pagenum,
        pagesize: pagesize
      })
    })
    
  }
  
 
    
  render() {
    var list = this.state.users.map((item, index) => {
      return (
        <tr key={index}>
          <td width='20%'><img src={item.image} alt="" width="50"/></td>
          <td width='15%'>{item.cardNum}</td>
          <td width='15%'>{item.name}</td>
          <td width='15%'>{item.phone}</td>
          <td width='15%'>{item.birthday}</td>
          <td width='5%'>{item.age&&item.age>0?item.age:null}</td>
          <td width='15%'>{item.registTimeStr}</td>
        </tr>
      )
    })
    //分页页码按钮显示
//  var pagesizes = this.state.pagesize.map((item, index) => {
//  		return (
//      	<span style={{float:'left'}} key={index}>
//      
//	        <nav aria-label="Page navigation">
//				<ul className="pagination">	
//		        {this.state.pagenum === index+1 ? 
//					<li className="active">
//						<a href="#">
//							{index+1}
//						</a>
//					</li>
//				:
//					<li>
//						<span onClick={()=>{window.location='/user-list?pn='+(index+1)}}>
//							{index+1}
//						</span>
//					</li>
//				}
//		        </ul>
//		      </nav>
//	        </span>
//	     )
//
//  })

    return (
      <DocumentTitle title="用户列表">
        <div className="user-list">
          	<div className="user-list-add">
              <div className="btn btn-primary">
               用户总数:{this.state.pagenums}
              </div>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th width='10%'>头像</th>
                  <th width='15%'>卡号</th>
                  <th width='15%'>昵称</th>
                  <th width='15%'>手机</th>
                  <th width='15%'>生日</th>
                  <th width='10%'>年龄</th>
                  <th width='20%'>成为会员时间</th>
                </tr>
              </thead>
              <tbody>
				{list}
              </tbody>
            </table>
            	当前{this.state.pagenum}页,总{this.state.size}页,总{this.state.pagenums}条记录
            	<span style={{float:'right'}}>
	            	<span onClick={()=>{window.location='/user-list?pn='+1}}>
						<button class="btn btn-default" type="submit">首页</button>
					</span>
	            	<span>
	            		{this.state.pagenum === 1
	            			?
	            			<button class="btn btn-default" type="submit" onClick={()=>{alert("已经是第一页了~~")}}>
								上一页
							</button>
	            			:
							<button class="btn btn-default" type="submit" onClick={()=>{window.location='/user-list?pn='+(this.state.pagenum-1)}}>
								上一页
							</button>
						}
					</span>
					<span>
					{this.state.pagenum === this.state.size 
						?
						<button class="btn btn-default" type="submit" onClick={()=>{alert("已经到最后一页了~~")}}>下一页</button>
						:
						<button class="btn btn-default" type="submit" onClick={()=>{window.location='/user-list?pn='+(this.state.pagenum+1)}}>
							下一页
						</button>
					}
					</span>
					<span onClick={()=>{window.location='/user-list?pn='+this.state.size}}>
						<button class="btn btn-default" type="submit" >末页</button>
					</span>
				</span>
        </div>
      </DocumentTitle>
    )
  }

}

export default UserList
