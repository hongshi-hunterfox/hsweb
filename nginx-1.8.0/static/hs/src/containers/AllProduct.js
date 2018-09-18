require('./all-product.css');
import DocumentTitle from 'react-document-title'
import React from 'react'
import Navi from './Navi'
var _ = require('lodash')
var fto = require('form_to_object')
var AllProductUtil = require('../utils/AllProductUtil.js');
class AllProductsCats extends React.Component{
	render() {
		if (!this.props.showCat) {
			return null;
		}

		var cats = this.props.cats.map(function(item, index) {
			return (
				<div className="all-prod-cat" onClick={()=>{window.location='/all-product?categoryId=' + item.categoryId}}>
					{/*<span className="all-prod-cat-icon">
						{item.category.slice(0, 1)}
					</span>*/}
					{item.category}
				</div>
				);
		}, this)

		return (
			<div className="all-prod-cats" onClick={this.props.toggleCat}>
				<div className="all-prod-cats-wrap">
					{cats}
				</div>
			</div>
			);
	}
};
class SearchBar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      keyword: ''
    }
  }
  render() {
    return (
      <div className="all-prod-search" onSubmit={this._handleSubmit} >
        <form method="post" className="all-prod-search-form" name="searchform" action="">
          <div className="all-prod-search-div">
            <i className="fa fa-search icon" />
            <input
              type='search'
              className="all-prod-search-input"
              id="keyword"
              name="keyword"
              value={this.state.keyword}
              placeholder="搜索产品"
              onChange={this._onChange}
              onFocus={this._f}
			onBlur={this._b}
            />
          </div>

        </form>
      </div>
    )
  }

  _f = () => {
		document.getElementById('hs-navi').style.display = 'none'
	}

	_b = () => {
		document.getElementById('hs-navi').style.display = 'block'
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
class AllProductsTab extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {
			showCat: false
	    }
	}

	render() {
		var isPriceDesc = this.props.query.isPriceDesc!=null?JSON.parse(this.props.query.isPriceDesc):null;
		var isSaleDesc = this.props.query.isSaleDesc!=null?JSON.parse(this.props.query.isSaleDesc):null;

		return (
			<div className="all-prod-tab">
				<AllProductsCats showCat={this.state.showCat} cats={this.props.cats} toggleCat={this._toggleCat}/>
				<div className={'all-prod-tab-item'} onClick={this._toggleCat}>
					<span className="fa fa-list-ul"/> 分类
				</div>
				<div className={'all-prod-tab-item' + (isSaleDesc==null&&isPriceDesc==null? ' active' : '')}
					onClick={this._tap.bind(this, 0,null)}>
						默认
				</div>
				<div className={'all-prod-tab-item' + (isSaleDesc!=null? ' active' : '')}
					onClick={this._tap.bind(this, 1,!isSaleDesc)}>
					<span className={'all-prod-tab-price' + 
						(isSaleDesc!=null&&isSaleDesc? ' all-prod-tab-price-asc' : '') +
						(isSaleDesc!=null&&!isSaleDesc? ' all-prod-tab-price-desc' : '')
						}>
						销量
					</span>
				</div>
				<div className={'all-prod-tab-item' + (isPriceDesc!=null? ' active' : '')}
					onClick={this._tap.bind(this,2, !isPriceDesc)}>
					<span className={'all-prod-tab-price' + 
						(isPriceDesc!=null&&isPriceDesc? ' all-prod-tab-price-asc' : '') +
						(isPriceDesc!=null&&!isPriceDesc? ' all-prod-tab-price-desc' : '')
						}>
						价格
					</span>
				</div>
			</div>
			)
	}
	_toggleCat = () => {
		this.setState({
			showCat: !this.state.showCat
		})
	}
	_tap = (tag,q) => {
		if(tag===1){
			if(this.props.location!=null&&this.props.location.query!=null&&this.props.location.query.categoryId!==null){
				window.location='all-product?isSaleDesc='+q+'&categoryId='+this.props.location.query.categoryId;
			}else{
				window.location='all-product?isSaleDesc='+q;
			}
			
		}else if(tag===2){
			if(this.props.location!=null&&this.props.location.query!=null&&this.props.location.query.categoryId!==null){
				window.location='all-product?isPriceDesc='+q+'&categoryId='+this.props.location.query.categoryId;
			}else{
				window.location='all-product?isPriceDesc='+q;
			}
		}else{
			if(this.props.location!=null&&this.props.location.query!=null&&this.props.location.query.categoryId!==null){
				window.location='all-product?categoryId='+this.props.location.query.categoryId;
			}else{
				window.location='all-product';
			}
		}
		
	}
};

class Item extends React.Component{
	_location=(url)=>{
		window.location=url;
	}
	render(){
		return (
			<div className='all-prod-item'>
				<div className='left' onClick={this._location.bind(this,'/detail/'+this.props.product.productId)}>
					<img className='image' src={this.props.product.image} alt=""/>
				</div>
				<div className='right' onClick={this._location.bind(this,'/detail/'+this.props.product.productId)}>
					<div className='title'>{this.props.product.title}</div>
					<div className='bottom'>
						<div className='count'>
							已销：{this.props.product.salesAmount}件
						</div>
						<div className='price'>
							¥ {this.props.product.price} <span className='pre'>¥ {this.props.product.prePrice}</span>	
						</div>
					</div>
				</div>
			</div>
		);
	}
}

class AllProduct extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {
			cat: [],
			products: [],
			hasNextPage: false,
			pageNum: 0
	    }
	}

	componentDidMount() {
		AllProductUtil.getData(this.props.location.query, function(res) {
			this.setState({
				cat:res.cat,
				products:res.products
			},()=>{
				console.log(this.state.products.length);
				if(!this.state.products.length>0){
					alert("没有找到符合条件的商品，去商城首页逛逛吧...");
					window.location='/'
				}
			});
		}.bind(this));
		
	}
	render() {
		var products = this.state.products.map(function(item, index) {
			return <Item key={index} product={item} />
		});

		return (
			<DocumentTitle title="全部商品">
				<div className="all-prod">
					<SearchBar/>
					<AllProductsTab query={this.props.location.query} cats={this.state.cat}/>
					{products}
                    <div className="text-center">
                        已加载全部
                    </div>
                    <div className="tail">
						广州洪石软件提供技术支持
					</div>
					<Navi query={this.props.location.query}/>
				</div>
			</DocumentTitle>
			);
	}
	_getData = (q) => {
		AllProductUtil.allProducts(q, function(res) {
			this.setState(res);
		}.bind(this))
	}
};

export default AllProduct