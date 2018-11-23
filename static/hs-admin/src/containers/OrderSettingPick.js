import React from 'react'
import DocumentTitle from 'react-document-title'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

import {values} from 'lodash'

import values1 from 'lodash'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './full-cut-shipping.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'
import moment from 'moment';
import ValueGroup from '../components/ValueGroup'
import InputMoment from 'input-moment'
//import 'react-date-picker/index.css'
import './input-moment/input-moment.less'
class OrderSettingPick extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            err: null,
            count: 1,
            initValue: null, //刚开始要设置成 null，只会从外部拿一次数据
            initRow: 1,
            html: '',
            closeStartDateStr:'',
            closeEndDateStr:'',
            businessStartTime:'00:00',
            businessEndTime:'23:59'
        }
    }

    componentDidMount() {
        req.get('/uclee-backend-web/orderSettingPick').end((err, res) => {
            if (err) {
                return err
            }
            var data = JSON.parse(res.text)
            console.log(data);
            this.setState({
                initRow: data.size,
                initValue: data.data,
                closeStartDateStr:data.data.closeStartDateStr,
                closeEndDateStr:data.data.closeEndDateStr,
                businessStartTime:data.data.businessStartTime,
                businessEndTime:data.data.businessEndTime
            })
        })
    }
    render() {
        return (
            <DocumentTitle title="设定歇业时间和营业时间">
                <div className="full-cut-shipping">
                    <form onSubmit={this._submit} className="form-horizontal">
                        <div className="form-group">
                            <label className="control-label col-md-3">歇业起始时间：</label>
                            <input type='date' name='closeStartDateStr' value={this.state.closeStartDateStr} onChange={this._handleChange.bind(this)}/>

                        </div>

                        <div className="form-group">
                            <label className="control-label col-md-3">歇业截止时间：</label>
                            <input type='date' name='closeEndDateStr' value={this.state.closeEndDateStr} onChange={this._handleChange.bind(this)}/>
                        </div>

                        <div className="form-group">
                            <label className="control-label col-md-3">营业开始时间：</label>
                            <input type='time' name='businessStartTime' value={this.state.businessStartTime} onChange={this._handleChange.bind(this)}/>
                        </div>

                        <div className="form-group">
                            <label className="control-label col-md-3">营业结束时间：</label>
                            <input type='time' name='businessEndTime' value={this.state.businessEndTime} onChange={this._handleChange.bind(this)}/>
                        </div>



                        <ErrorMsg msg={this.state.err} />
                        <div className="form-group">
                            <div className="col-md-9 col-md-offset-3">
                                <button type="submit" className="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </DocumentTitle>
        )
    }
    _handleChange = e => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }
    _submit = e => {
        e.preventDefault()
        var data = fto(e.target)

        if(!data.closeStartDateStr){
            this.setState({
                err: "请填写歇业开始时间"
            })
            return;
        }

        if(!data.closeEndDateStr){
            this.setState({
                err: "请填写歇业结束时间"
            })
            return;
        }

        if(!data.businessStartTime){
            this.setState({
                err: "请填写营业开始时间"
            })
            return;
        }

        if(!data.businessEndTime){
            this.setState({
                err: "请填写营业结束时间"
            })
            return;
        }

        console.log(data);
        if(Date.parse(data.closeStartDateStr)>=Date.parse(data.closeEndDateStr)){
            this.setState({
                err: '歇业开始时间需小于结束时间'
            })
            return false;
        }

        if(data.businessStartTime>=data.businessEndTime){
            this.setState({
                err: '营业开始时间需小于结束时间'
            })
            return false;
        }


        //还需要判定营业起始时间是否大于结束时间
        this.setState({
            err: null
        })

        req.post('/uclee-backend-web/orderSettingPickHandler').send(data).end((err, res) => {
            if (err) {
                return err
            }

            if (res.text) {
                window.location = '/orderSettingPick'
            }
        })
    }
}

export default OrderSettingPick
