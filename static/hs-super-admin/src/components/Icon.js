import React from 'react'

const Icon = (props) => {
  return (
    <i style={props.style} className={`fa fa-${props.name}` + (props.className ? ` ${props.className}` : '')} />
    )
}

export default Icon