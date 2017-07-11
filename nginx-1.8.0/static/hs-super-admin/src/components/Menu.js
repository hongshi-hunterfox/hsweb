import React from 'react'
import { Link } from 'react-router'

class Menu extends React.Component {
  render() {
    return (
      <div style={{
        paddingTop: 30
      }}>
        <div className="list-group">
          <Link to={'/datasource'} className="list-group-item" activeClassName="active">
            数据源管理
          </Link>
        </div>
      </div>
      )
  }
}

export default Menu