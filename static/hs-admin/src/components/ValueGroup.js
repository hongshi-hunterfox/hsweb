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
    var {
      useSelect,
      selectOptions,
      selectName,
      keyName,
      valueName,
      selectText,
      keyText,
      valueText,
      condition,
      selectDefaultValue,
      hideCondition
    } = this.props
    return (
      <div className="vlaue-group">
        <table className="table table-bordered">
          <thead>
            <tr>
              {hideCondition?null:<th />}
              <th>{keyText}</th>
              <th>{valueText}</th>
              {useSelect ? <th>{selectText}</th> : null}
              <th />
            </tr>
          </thead>
          <tbody>
            {range(this.state.rowCount).map((item, index) => {
              return (
                <tr key={index}>
                  {hideCondition?null:<td>{condition}</td>}
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
                      name={valueName + '[' + index + ']'}
                      value={getValue(v[`${valueName}[${index}]`])}
                      onChange={this._change}
                    />
                  </td>
                  {useSelect
                    ? <td>
                        <select
                          className="form-control"
                          name={selectName + '[' + index + ']'}
                          value={v[`${selectName}[${index}]`] || selectDefaultValue}
                          onChange={this._change}
                        >
                          {selectOptions.map((item, index) => {
                            return (
                              <option value={item.value} key={index}>
                                {item.text}
                              </option>
                            )
                          })}
                        </select>
                      </td>
                    : null}
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

export default ValueGroup
