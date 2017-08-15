import moment from 'moment'
import React from 'react'

var TICK_INTERVAL = 50

class CountDown extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      time: this.props.time
    }
  }

  componentDidMount() {
    this.int = setInterval(this._tick, TICK_INTERVAL)
  }

  componentWillReceiveProps(nextProps) {
    clearInterval(this.int)
    this.setState({
      time: nextProps.time
    })
    this.int = setInterval(this._tick, TICK_INTERVAL)
  }

  componentWillUnmount() {
    clearInterval(this.int)
  }

  render() {
    var d = moment.duration(this.state.time, 'milliseconds')
    if (this.state.time < 0) {
      return (
        <div className="count-down">
        {this.props.tag}{0 + ':' + 0 + ':' + 0 + ':' + 0}
        </div>
        )
    }
    return (
      <div className="count-down">
        {this.props.tag}{d.hours() + ':' + d.minutes() + ':' + d.seconds() + ':' + d.milliseconds()}
      </div>
      )
  }
  _tick = () => {
    var rest = this.state.time - TICK_INTERVAL
    if (rest <= 0) {
      clearInterval(this.int)
      this.setState({
        time: 0
      })
      if (typeof this.props.onComplete === 'function') {
        this.props.onComplete()
      }
      return
    }

    this.setState({
      time: rest
    })
  }
}
export default CountDown