import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-card.css'
import req from 'superagent'
import './member-center.css'

 
 
const NoItem = () => {
  return (
    <div style={{
      margin: '50px 0',
      textAlign: 'center'
    }}>
      暂无会员卡
    </div>
    )
}

class Install extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      balance: null,
      cVipCode: null,
      config:{},
      vipImage:'',
      allowRecharge:true,
      vipJbarcode:'',
      cardStatus:'',
      id:0,
    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getUserInfo/')
      .query({
        t: new Date().getTime()
      })
      .end((err, res) => {
 		if (res.text) {
          this.setState(res.body)
        }
      })  
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config
      })
    })
    
    req
      .get('/uclee-user-web/getVipInfo')
      .end((err, res) => {
        if (err) {
          return err
        }

        if (res.text) {
          this.setState(res.body)
        }
      })
      
  	  
  
  }
  _clickHander=(url,cardStatus)=>{
    if(this.state.allowRecharge){
      window.location=url;
    }else{
      alert(cardStatus);
    }
  }
  
  render() {
    return (
      <DocumentTitle title="我的会员卡">
        <div className="member-card">
            <div className="media">
            <div className="member-card-item-code">会员卡挂失:
             
              {
               <span onClick={() => { 
              		var conf = confirm('确定挂失吗？挂失后会员功能将无法使用!');
          	   		 if(!conf){
                     return;
          	   		 }   
          	    	else{
                 	req
                	.get('/uclee-user-web/discontinuationVip')
                    .end((err, res) => {				          
                 	alert("挂失成功,请返回页面刷新!")
					window.location="/install";
                    //window.location.reload();
                 })
                }   
		  	  }}      
              className="member-card-item-Unbundling"> 
              {
              	this.state.disable ==1?
               <button type="submit" className="btn btn-warning btn-sm" ><font color="white">已挂失</font></button>
              :
              <button type="submit" className="btn btn-warning btn-sm" ><font color="white">挂失</font></button>
              }
             </span>
             
            }
             
              
  
                
               </div> 
             </div>
          
          <div className="member-card">
          <div className="media">
          <div className="member-card-item-code">会员卡解绑:
              <span onClick={() => { 
              	var conf = confirm('确定解绑吗？解绑后会员功能将无法使用!');
          	    if(!conf){
          	      return;
          	    }   
          	    else{
                 req
                 .get('/uclee-user-web/changeVip')
                 .end((err, res) => {				          
                 	alert("解绑成功,请返回页面刷新!")
					window.location="/member-card";
                    //window.location.reload();
                 })
                }   
		  	  }}
              className="member-card-item-Unbundling">
                <button type="submit" className="btn btn-warning btn-sm" ><a href="/uclee-user-web/logout"><font color="white">解除绑定</font></a></button>
		  	  </span>
       </div>
        </div>
         </div>
      
       
          <div className= "member-card-item-recharge"><button type="submit" className="btn btn-success" ><a href="member-card"><font color="white">返回上一级</font></a></button>
            </div>  
        </div>
      </DocumentTitle>
      )
  }


}

export default Install