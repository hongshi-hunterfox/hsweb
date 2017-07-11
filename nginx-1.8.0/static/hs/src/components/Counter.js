import React from 'react'
import './counter.css'
import Icon from './Icon'

const Counter = (props) => {
  return (
    <div className="hs-counter input-group">
      <div className="input-group-btn">
        <button className="btn btn-default" onClick={props.subAmount} disabled={props.amount === 0}>
          <Icon name="minus" />
        </button>
      </div>
      <input type="text" className="form-control" disabled value={props.amount}/>
      <div className="input-group-btn" onClick={props.addAmount}>
        <button className="btn btn-default" disabled={props.maxAmount ? (props.amount >= props.maxAmount ? true : false) : false}>
          <Icon name="plus" />
        </button>
      </div>
    </div>
    )
}

export default Counter