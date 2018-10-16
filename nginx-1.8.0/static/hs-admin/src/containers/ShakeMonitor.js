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

class ShakeMonitor extends React.Component {
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
      thirdNumber:0
    }
  }

  componentDidMount() {
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
      <DocumentTitle title="现场活动">
        <div className="shake-monitor">
          <h1 className="shake-monitor-title">摇一摇抽奖活动</h1>
          <h3 className="shake-monitor-total">
            参与总人数：<strong>{this.state.total}</strong>
            <button className="reset-button btn btn-primary" type="button" onClick={this._resetClick}>重置数据</button>
          </h3>
          <div className="shake-monitor-items">
            <Scrollbar
              style={{
                height: '100%'
              }}
            >
              {this.state.shakeRecords.map((item, index) => {
                return (
                  <div className="shake-monitor-item" key={index}>
                    <img src={item.image} alt="" />
                    <span>{item.nickName}</span>
                  </div>
                )
              })}
            </Scrollbar>
          </div>
          <div className="shake-monitor-prize clearfix">
            <div className="shake-monitor-prize-title">一等奖(设奖名额：{this.state.level1Config.value})</div>
            <div className="shake-monitor-prize-items">
              <Scrollbar
                style={{
                  width: '100%'
                }}
              >
                {this.state.level1.map((item, index) => {
                  return (
                    <div className="shake-monitor-prize-item" key={index}>
                      <img src={item.image} alt="" />
                      <span>{item.nickName}</span>
                    </div>
                  )
                })}
              </Scrollbar>
            </div>

            <div className="shake-monitor-prize-control">{/*
              <div className="input-group">
                <div className="input-group-addon">本次最多产生名额：</div>
                <input type="text" className="form-control" name="firstNumber" value={this.state.firstNumber} onChange={this._firstNumberChange.bind(this)}/>
              </div>*/}
              <div className='button'>
                <button className="btn btn-primary" type="button" onClick={this._firstClick}>点击抽奖</button>
              </div>
            </div>
          </div>

          <div className="shake-monitor-prize clearfix">
            <div className="shake-monitor-prize-title">二等奖(设奖名额：{this.state.level2Config.value})</div>
            <div className="shake-monitor-prize-items">
              <Scrollbar
                style={{
                  width: '100%'
                }}
              >
                {this.state.level2.map((item, index) => {
                  return (
                    <div className="shake-monitor-prize-item" key={index}>
                      <img src={item.image} alt="" />
                      <span>{item.nickName}</span>
                    </div>
                  )
                })}
              </Scrollbar>
            </div>
            <div className="shake-monitor-prize-control">{/*
              <div className="input-group">
                <div className="input-group-addon">本次最多产生名额：</div>
                <input type="text" className="form-control" name="secondNumber" value={this.state.secondNumber} onChange={this._secondNumberChange.bind(this)}/>
              </div>*/}
              <div className='button'>
                <button className="btn btn-primary" type="button" onClick={this._secondClick}>点击抽奖</button>
              </div>
            </div>
          </div>

          <div className="shake-monitor-prize clearfix">
            <div className="shake-monitor-prize-title">三等奖(设奖名额：{this.state.level3Config.value})</div>
            <div className="shake-monitor-prize-items">
              <Scrollbar
                style={{
                  width: '100%'
                }}
              >
                {this.state.level3.map((item, index) => {
                  return (
                    <div className="shake-monitor-prize-item" key={index}>
                      <img src={item.image} alt="" />
                      <span>{item.nickName}</span>
                    </div>
                  )
                })}
              </Scrollbar>
            </div>
            <div className="shake-monitor-prize-control">{/*
              <div className="input-group">
                <div className="input-group-addon">本次最多产生名额：</div>
                <input type="text" className="form-control" name="thirdNumber" value={this.state.thirdNumber} onChange={this._thirdNumberChange.bind(this)}/>
              </div>*/}
              <div className='button'>
                <button className="btn btn-primary" type="button" onClick={this._thirdClick}>点击抽奖</button>
              </div>
            </div>
          </div>
        </div>
      </DocumentTitle>
    )
  }

  _resetClick=()=>{
    req.get('/uclee-user-web/resetDraw?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
         if(res.body){
            alert('重置成功');
            window.location='/shake-monitor?loginRequired=false&merchantCode='+localStorage.getItem('merchantCode')
         } 
    })
  }

  _firstClick=()=>{
      req.get('/uclee-user-web/firstDraw?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
        if (err) {
          return err
        }
        var ret = JSON.parse(res.text);
        if(ret.result){
          if(ret.win===null||ret.win.length===0){
            alert("此次抽奖没产生幸运儿");
            return ;
          }
          this.setState({
            shakeRecords: ret.shakeRecord,
            level1: ret.level1
          })
          alert("恭喜 " + ret.text + "成为本次抽奖幸运儿");
          return ;
        }else{
          if(ret.reason==='limit'){
            alert("人数超额");
            return ;
          }
          if(ret.reason==='noWinner'){
            alert("此次抽奖没产生幸运儿");
            return ;
          }
        }
        
      })    
  }
  _secondClick=()=>{
        req.get('/uclee-user-web/secondDraw?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
          if (err) {
            return err
          }
          var ret = JSON.parse(res.text);
          if(ret.result){
            if(ret.win===null||ret.win.length===0){
              alert("此次抽奖没产生幸运儿");
              return ;
            }
            this.setState({
              shakeRecords: ret.shakeRecord,
              level2: ret.level2
            })
            alert("恭喜 " + ret.text + "成为本次抽奖幸运儿");
            return ;
          }else{
            if(ret.reason==='limit'){
              alert("超过设奖人数");
              return ;
            }
            if(ret.reason==='noWinner'){
              alert("此次抽奖没产生幸运儿");
              return ;
            }
          }
          
        })
    
  }
  _thirdClick=()=>{
      req.get('/uclee-user-web/thirdDraw?merchantCode='+localStorage.getItem('merchantCode')).end((err, res) => {
        if (err) {
          return err
        }
        var ret = JSON.parse(res.text);
        if(ret.result){
          if(ret.win===null||ret.win.length===0){
            alert("此次抽奖没产生幸运儿");
            return ;
          }
          this.setState({
            shakeRecords: ret.shakeRecord,
            level3: ret.level3
          })
          alert("恭喜 " + ret.text + "成为本次抽奖幸运儿");
          console.log("恭喜 " + ret.text + "成为本次抽奖幸运儿");
          return ;
        }else{
          if(ret.reason==='limit'){
            alert("超过设奖人数");
            return ;
          }
          if(ret.reason==='noWinner'){
            alert("此次抽奖没产生幸运儿");
            return ;
          }
        }
        
      })
    
  }
  _firstNumberChange=(e)=>{
      this.setState({
        firstNumber:e.target.value
      })
      console.log(e.target.value);
      console.log(this.state.firstNumber);
  }
  _secondNumberChange=(e)=>{
      this.setState({
        secondNumber:e.target.value
      })
  }
  _thirdNumberChange=(e)=>{
      this.setState({
        thirdNumber:e.target.value
      })
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

export default ShakeMonitor
