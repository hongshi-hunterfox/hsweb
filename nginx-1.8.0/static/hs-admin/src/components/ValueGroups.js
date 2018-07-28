import React from 'react'
import './vlaue-group.css'
import { range } from 'lodash'

function getValue(v) {
  if (v === 0) {
    return '0'
  }
  if (!v) {
    return ''
  }
  return v
}


class ValueGroups extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      rowCount: this.props.initRow || 1,
      v: this.props.initValue || {}
    }
  }

  render() {
    var { v } = this.state
    var {
      useSelect,
      selectOptions,
      selectName,
      keyName,
      value0Name,
      valueName,
      value1Name,
      selectText,
      keyText,
      value0Text,
      valueText,
      value1Text,
      condition,
      selectDefaultValue,
      hideCondition
    } = this.props
    return (
      <div className="vlaue-group">
        <table className="table table-bordered">
          <thead>
            <tr>
              <th />
              <th>{keyText}</th>
              <th>{value0Text}</th>
              <th>{valueText}</th>
              <th>{value1Text}</th>
              {useSelect ? <th>{selectText}</th> : null}
              <th />
            </tr>
          </thead>
          <tbody>
            {range(this.state.rowCount).map((item, index) => {
              return (
                <tr key={index}>
                  <td>{condition}</td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={keyName + '[' + index + ']'}
                      value={getValue(v[`${keyName}[${index}]`])}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={value0Name + '[' + index + ']'}
                      value={getValue(v[`${value0Name}[${index}]`])}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={valueName + '[' + index + ']'}
                      value={getValue(v[`${valueName}[${index}]`])}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      name={value1Name + '[' + index + ']'}
                      value={getValue(v[`${value1Name}[${index}]`])}
                      onChange={this._change}
                    />
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-danger"
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
        {this.state.rowCount < this.props.maxRow
          ? <button
              type="button"
              className="btn btn-default"
              onClick={this._addRow}
            >
              <span className="glyphicon glyphicon-plus" /> 添加
            </button>
          : null}
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

export default ValueGroups
