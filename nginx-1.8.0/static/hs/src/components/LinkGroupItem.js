import React from 'react'
import Icon from './Icon'

const LinkGroupItem = (props) => {
  return (
    <a href={props.href} className="link-group-item" onClick={props.onClick}>
      <Icon name={props.icon} className="link-group-icon"/>
      <span>{props.text}</span>
      <Icon name="chevron-right" className="link-group-arrow" />
    </a>
    )
}


export default LinkGroupItem