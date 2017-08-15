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

class HomeNav extends React.Component {
  render() {
    return (
      <div className="home-nav">
        <Link
          to="/all-product?filterType=process&isPk=1"
          className="home-nav-item"
        >
          <span className="home-nav-item-icon">
            精
          </span>
          <span className="home-nav-item-text">
            精品
          </span>
        </Link>
        <Link
          to="/all-product?filterType=process&isQuick=1"
          className="home-nav-item"
        >
          <span className="home-nav-item-icon">
            热
          </span>
          <span className="home-nav-item-text">
            热销
          </span>
        </Link>
        <Link
          to="/all-product?categoryId=3"
          className="home-nav-item"
        >
          <span className="home-nav-item-icon">
            生
          </span>
          <span className="home-nav-item-text">
            生日蛋糕
          </span>
        </Link>
        <Link
          to="/all-product?categoryId=1"
          className="home-nav-item"
        >
          <span className="home-nav-item-icon">
            茶
          </span>
          <span className="home-nav-item-text">
            下午茶
          </span>
        </Link>
      </div>
    )
  }
}

class Home extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      groups: [],
      banner:[]
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
            {
              this.state.banner.length ? 
              <HomeCarousel banner={this.state.banner}/> : null
            }
            <HomeNav />
            <Navi query={this.props.location.query}/>
            {groups}
            <CartBtn />
          </div>
      </DocumentTitle>
    )
  }
}
export default Home
