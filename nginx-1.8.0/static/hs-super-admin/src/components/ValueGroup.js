import React from "react"
import "./vlaue-group.css"
import { range } from "lodash"

class ValueGroup extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      rowCount: this.props.initRow || 1,
      v: this.props.initValue || {}
    }
  }

  // componentWillReceiveProps(nextProps) {
  //   if (JSON.stringify(nextProps.initValue) !== JSON.stringify(this.state.v)) {
  //     this.setState({
  //       v: nextProps.initValue || {}
  //     })
  //   }
  // }

  render() {
    var { v } = this.state
    return (
      <div className="vlaue-group">
        <table className="table table-bordered">
          <thead>
            <tr>
              <th />
              <th>{this.props.keyText}</th>
              <th>{this.props.valueText}</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {range(this.state.rowCount).map((item, index) => {
              return (
                <tr key={index}>
                  <td>{this.props.condition}</td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={this.props.keyName + '[' + index + ']'}
                      value={v[`${this.props.keyName}[${index}]`] || ''}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={this.props.valueName + '[' + index + ']'}
                      value={v[`${this.props.valueName}[${index}]`] || ''}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-danger"
                      disabled={index === 0}
                      onClick={this._removeRow}
                    >
                      <span className="glyphicon glyphicon-minus" />
                    </button>
                  </td>
                </tr>
              )
            })}

          </tbody>
        </table>
        {
          this.state.rowCount < this.props.maxRow ?
          <button type="button" className="btn btn-default" onClick={this._addRow}>
            <span className="glyphicon glyphicon-plus" /> 添加
          </button>
          :
          null
        }
      </div>
    )
  }

  _addRow = e => {
    e.preventDefault()
    this.setState(prevState => {
      return {
        rowCount: prevState.rowCount + 1
      }
    })
  }

  _removeRow = e => {
    e.preventDefault()
    this.setState(prevState => {
      return {
        rowCount: prevState.rowCount - 1
      }
    })
  }

  _change = e => {
    var v = Object.assign({}, this.state.v)
    v[e.target.name] = e.target.value
    this.setState({
      v: v
    })
  }
}

export default ValueGroup
