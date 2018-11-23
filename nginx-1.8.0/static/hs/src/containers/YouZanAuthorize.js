import React from 'react'
import DocumentTitle from 'react-document-title'
import './shake-monitor.css'
// import req from 'superagent'
import _ from 'lodash'
import req from 'superagent'

import Scrollbar from 'react-smooth-scrollbar'
import 'smooth-scrollbar/dist/smooth-scrollbar.css'

class YouZanAuthorize extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      config:{},
    }
  }

  componentDidMount() {
  	req.get('/uclee-backend-web/config'+'?merchantCode='+this.props.location.query.merchantCode).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config,
      })
      console.log(this.state.config)
    })

  }

  render() {
    return (
      <DocumentTitle title="立即授权">
      
        <div style={{margin: '50px 0',textAlign: 'center'}}>
          <h1>点击立即授权</h1>
					授权给广州洪石软件
          <h3>
          <a href= {"https://open.youzan.com/oauth/authorize?client_id=01e3d14da80e4e77e6"+"&response_type=code"+"&state=teststate"+"&redirect_uri=http://"+this.state.config.domain+"/uclee-backend-web/getYouZanToken?merchantCode="+this.props.location.query.merchantCode+"&scope=testscope"}>
            <button className="reset-button btn btn-danger" type="button">
            	
            		去授权
            	
            </button>
            </a>
          </h3>
        </div>
          
      </DocumentTitle>
    )
  }
}

export default YouZanAuthorize
