import React from 'react'
import DocumentTitle from 'react-document-title'
import './shake-monitor.css'
// import req from 'superagent'
import _ from 'lodash'
import req from 'superagent'

import Scrollbar from 'react-smooth-scrollbar'
import 'smooth-scrollbar/dist/smooth-scrollbar.css'

function hasClass(el, className) {
  if (el.classList) return el.classList.contains(className)
  else
    return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}

function addClass(el, className) {
  if (el.classList) el.classList.add(className)
  else if (!hasClass(el, className)) el.className += ' ' + className
}

function removeClass(el, className) {
  if (el.classList) el.classList.remove(className)
  else if (hasClass(el, className)) {
    var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
    el.className = el.className.replace(reg, ' ')
  }
}

class YouZanAuthorize extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      count: 5,
      total: 100,
      show: false,
      shakeRecords:[],
      level1: [],
      level2: [],
      level3: [],
      level1Config: {},
      level2Config: {},
      level3Config: {},
      firstNumber:0,
      secondNumber:0,
      thirdNumber:0,
      config:{},
      a:''
    }
  }

  componentDidMount() {
  	var s;
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
    req.get('/uclee-user-web/getShakePageData'+'?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
      if (err) {
        return err
      }
      this.setState({
        show: true,
        total: res.body.total,
        shakeRecords: res.body.shakeRecords,
        level1: res.body.level1,
        level2: res.body.level2,
        level3: res.body.level3,
        level1Config: res.body.level1Config,
        level2Config: res.body.level2Config,
        level3Config: res.body.level3Config
      })
    })
    this._poll()

    addClass(document.body, 'shake-monitor-body')
  }

  componentWillUnmount() {
    removeClass(document.body, 'shake-monitor-body')
  }

  render() {
    return (
      <DocumentTitle title="立即授权">
      
        <div>
          <h1>点击立即授权</h1>
          6.{this.props.a}
          2.{this.props.location.query.merchantCode}
          <h3>
          <a href= {"https://open.youzan.com/oauth/authorize?client_id=01e3d14da80e4e77e6"+"&response_type=code"+"&state=teststate"+"&redirect_uri=http://"+"admin7.in80s.com"+"/uclee-backend-web/test?merchantCode="+this.props.location.query.merchantCode+"&scope=testscope"}>
            <button className="reset-button btn btn-primary" type="button">
            	
            		去授权
            	
            </button>
            </a>
          </h3>
        </div>
          
      </DocumentTitle>
    )
  }


  _poll = () => {
    setTimeout(() => {
      req.get('/uclee-user-web/getShakeRecord'+'?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
        if (err) {
          return err
        }
        var ret = JSON.parse(res.text);
        this.setState({
          shakeRecords: ret.shakeRecords,
          total: ret.shakeRecords.length
        })
      })
      this._poll()
    }, 4000)
  }
}

export default YouZanAuthorize
