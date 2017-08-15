import React from 'react'
import './link-group.css'

const LinkGroup = (props) => {
  return (
    <div className="link-group">
      {props.children}
    </div>
    )
}

export default LinkGroup