import React from 'react'
import './checkbox.css'
import Icon from './Icon'

const CheckBox = (props) => {
  return (
    <label className={'hs-checkbox' + (props.checked ? ' checked' : '') + (props.className ? ' ' + props.className : '')}>
      <input type="checkbox" name={props.name} value={props.value || ''} checked={props.checked || false} className="hs-checkbox-input" onChange={props.onChange}/>
      <Icon name={props.checked ? 'check-circle' : 'circle-thin'} className="hs-checkbox-icon"/>
      <span className="hs-checkbox-text">{props.text}</span>
    </label>
    )
}

export default CheckBox