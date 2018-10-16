import React from 'react'
import DocumentTitle from 'react-document-title'
import './recharge-config-list.css'
import req from 'superagent'
import { Link } from 'react-router'
import ErrorMessage from '../components/ErrorMsg'
var fto = require('form_to_object')
import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap'
class RechargeConfigList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      rechargeConfigs:[],
      config:null,
      show:false
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/rechargeConfigList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        rechargeConfigs: data.rechargeConfigs
      })
    })
  }
  _del=(id)=>{
    var conf = confirm('确认要删除该配置吗？');
    if(conf){
      req.get('/uclee-backend-web/delRechargeConfig?id='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/recharge-config-list'
      })
    }
  }
  _setConfig=(config)=>{
      this.setState({
        config:config,
        show:true
      })
  }
  _close=()=>{
      this.setState({
        show:false
      })
  }
  render() {
    var list = this.state.rechargeConfigs.map((item, index) => {
      return (
        <tr key={index}>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.money}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.rewards}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.startTimeStr}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.endTimeStr}</td>
          {/*<td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.voucherCode}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.amount}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.voucherCodeSecond}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.amountSecond}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.voucherCodeThird}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.amountThird}</td>
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>{item.limit}</td>*/}
          <td style={{
            textAlign:'center',verticalAlign:'middle'
          }}>
            <button className="btn btn-primary" onClick={()=>{window.location='/recharge-config-new?id='+item.id}} style={{
            marginRight:'5px',marginBottom:'5px'
          }}>
              编辑
            </button>
            <button className="btn btn-danger" onClick={this._del.bind(this,item.id)} style={{
            marginRight:'5px',marginBottom:'5px'
          }}>
              删除
            </button>
            <button className="btn btn-primary" onClick={this._setConfig.bind(this,item)} >
              查看赠送详情
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="评论列表">
        <div className="recharge-config-list">
            <div className="recharge-config-list-add">
              <Link to={'/recharge-config-new/'} className="btn btn-primary">
               添加充值赠送规则
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th style={{
            textAlign:'center',verticalAlign:'middle'
          }}>充值金额</th>
                  <th style={{
            textAlign:'center',verticalAlign:'middle'
          }}>赠送金额</th>
                  <th style={{
            textAlign:'center',verticalAlign:'middle'
          }}>起始时间</th>
                  <th style={{
            textAlign:'center',verticalAlign:'middle'
          }}>终止时间</th>
                  {/*<th>券1商品号</th>
                  <th>券1数量</th>
                  <th>券2商品号</th>
                  <th>券2数量</th>
                  <th>券3商品号</th>
                  <th>券3数量</th>
                  <th>获券上限</th>*/}
                  <th style={{
            textAlign:'center',verticalAlign:'middle'
          }}>操作</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
            {
              this.state.config&&this.state.show?
              <div className='recharge-config-list-model'>
                <i className='fa fa-times-circle-o icon' onClick={this._close}/>
                <table className="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券1商品号</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券1数量</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券2商品号</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券2数量</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券3商品号</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>券3数量</th>
                      <th style={{
            textAlign:'center',verticalAlign:'middle',color:'white'
          }}>获券上限</th>
                    </tr>
                          </thead>
                          <tbody>
                            <tr >
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.voucherCode}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.amount}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.voucherCodeSecond}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.amountSecond}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.voucherCodeThird}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.amountThird}</td>
                      <td style={{
                        textAlign:'center',verticalAlign:'middle'
                      }}>{this.state.config.limit}</td>
                    </tr>
                  </tbody>
                </table>
              </div>:null
            }
            
        </div>
      </DocumentTitle>
    )
  }
  }


export default RechargeConfigList
