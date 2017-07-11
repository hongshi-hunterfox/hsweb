import React from 'react'
import Icon from './Icon'
import './noti.css'

const TYPE_ICON_MAP = {
  'success': {
    name: 'check-circle',
    color: '#5cb85c'
  },
  'error': {
    name: 'times-circle',
    color: '#d9534f'
  }
}

const Noti = (props) => {
  if (props.visible) {
    return (
      <div className="noti">
        <Icon name={TYPE_ICON_MAP[props.type].name} className="noti-icon" style={{
          color: TYPE_ICON_MAP[props.type].color
        }}/>
        <span>{props.text}</span>
      </div>
      )
  }

  return null
}

Noti.defaultProps = {
  type: 'success'
}

export default Noti