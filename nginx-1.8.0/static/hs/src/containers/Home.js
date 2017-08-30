import "./home.css"
import DocumentTitle from "react-document-title"
import "slick-carousel/slick/slick.css"
var React = require("react")
import CartBtn from "./CartBtn"
import Navi from "./Navi"
import Slick from "react-slick"
var Link = require("react-router").Link
import ProductGroup from "./ProductGroup"
var HomeUtil = require('../utils/HomeUtil.js');
import req from 'superagent'
var fto = require('form_to_object')
class HomeCarousel extends React.Component {
  render() {
    var slickSettings = {
      dots: true,
      arrows: false,
      infinite: true,
      speed: 800,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      show: false
    }
    var banner = this.props.banner.map((item, index) => {
      return(
          <div className="home-carousel-item" key={index}>
              {
                item.link?
                <a href={item.link}>
                  <img
                    src={item.imageUrl}
                    className="home-carousel-img"
                    alt=""
                  />
                </a>:
                  <img
                  src={item.imageUrl}
                  className="home-carousel-img"
                  alt=""
                />
              }
          </div>
      );
    });
    return (
      <div className="home-carousel">
        <Slick {...slickSettings}>
          
          {banner}

        </Slick>
      </div>
    )
  }
}
class SearchBar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      keyword: ''
    }
  }
  render() {
    return (
      <div className="home-search" onSubmit={this._handleSubmit}>
        <form method="post" className="home-search-form" name="searchform">
          <div className="home-search-div">
            <i className="fa fa-search icon" />
            <input
              className="home-search-input"
              id="keyword"
              name="keyword"
              value={this.state.keyword}
              placeholder="搜索产品"
              onChange={this._onChange}
            />
          </div>

        </form>
      </div>
    )
  }

  _onChange =(e)=>{
    this.setState({
      keyword:e.target.value
    })
  }

  _handleSubmit = e => {
     e.preventDefault()
    var data = fto(e.target);
    console.log(data.keyword);
    window.location="/all-product?keyword="+data.keyword;
  }
}
class HomeNav extends React.Component {
  _location=(url)=>{
      window.location=url
    }
  render() {
    var list = this.props.quickNavis.map((item,index)=>{
        return (
            <div
              onClick={this._location.bind(this,"/all-product?naviId="+item.naviId)}
              className="home-nav-item"
              key={index}
            >
            <img src={item.imageUrl} className='home-nav-item-image' />
              <span className="home-nav-item-text">
                {item.title}
              </span>
            </div>
        )
    })

    return (
      <div className="home-nav">
        {list}
      </div>
    )
  }
}

class Home extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      groups: [],
      banner:[],
      quickNavis:[]
    }
  }

  componentDidMount() {
    HomeUtil.home(function (res) {
      this.setState(res)
    }.bind(this))

    if(this.props.location.query.serialNum!==null){
      req
        .get('/uclee-user-web/invitation?serialNum='+this.props.location.query.serialNum)
        .end((err, res) => {
          if (err) {
            return err
          }
        })
    }
    if(this.props.location.query.isShake){
        req
        .get('/uclee-user-web/shakeHandler')
        .end((err, res) => {
          if (err) {
            return err
          }
          if(res.body){
            alert("摇一摇抽奖活动参与成功");
          }else{
            alert("请先关注公众号");
          }
        })
      }
  }
  render() {
    var groups = this.state.groups.map((item, index) => {
      return(
          <ProductGroup key={index} {...item}/>
      );
    });
    return (
      <DocumentTitle title="首页">
          <div className="home">
          <SearchBar/>
            {
              this.state.banner.length ? 
              <HomeCarousel banner={this.state.banner}/> : null
            }
            <HomeNav quickNavis={this.state.quickNavis}/>
            <Navi query={this.props.location.query}/>
            {groups}
            <CartBtn />
          </div>
      </DocumentTitle>
    )
  }
}
export default Home
