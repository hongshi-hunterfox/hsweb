import React from 'react'
import Icon from './Icon'

const ErrorMsg = (props) => {
  if (props.msg && props.msg.length) {
    return (
      <div style={{
        margin: '10px 0',
        padding: '0 10px'
      }}>
        <div className="alert alert-danger" style={{ marginBottom: 0, padding: 10 }}>
          <Icon name="exclamation-circle" style={{ marginRight: '10px' }}/>
          {props.msg}
        </div>
      </div>
      )
  }

  return null
}

export default ErrorMsg