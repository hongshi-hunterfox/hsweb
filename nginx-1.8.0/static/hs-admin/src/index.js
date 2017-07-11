import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import './index.css';
import App from './App'
import Home from './containers/Home'
import Demo from './containers/Demo'
import Product from './containers/Product'
import AddStore from './containers/AddStore'
import EditStore from './containers/EditStore'
import EditBanner from './containers/EditBanner'
import NapaStoreList from './containers/NapaStoreList'
import UserList from './containers/UserList'
import UserBirthList from './containers/UserBirthList'
import UserUnBuyList from './containers/UserUnBuyList'
import AddUser from './containers/AddUser'
import EditPhoneUser from './containers/EditPhoneUser'
import PhoneUserList from './containers/PhoneUserList'
import GlobalConfig from './containers/GlobalConfig'
import RechargeConfig from './containers/RechargeConfig'
import Login from './containers/Login'
import ProductList from './containers/ProductList'
import BannerList from './containers/BannerList'
import ProductGroupList from './containers/ProductGroupList'
import Freight from './containers/Freight'
import StoreIntro from './containers/StoreIntro'
import Lottery from './containers/LotteryConfig'
import EditProductGroup from './containers/EditProductGroup'
import ShakeMonitor from './containers/ShakeMonitor'
import NotFound from './containers/NotFound'
import { Router, Route, IndexRoute, browserHistory } from 'react-router'

ReactDOM.render(
  <Router history={browserHistory}>
    <Route path="/login" component={Login}/>
    <Route path="/shake-monitor" component={ShakeMonitor} />
    <Route path="/" component={App}>
      <IndexRoute component={Home}/>
      <Route path="demo" component={Demo} />
      <Route path="product" component={Product} />
      <Route path="product/:id" component={Product} />
      <Route path="global-config" component={GlobalConfig} />
      <Route path="recharge-config" component={RechargeConfig} />
      <Route path="freight" component={Freight} />
      <Route path="store-intro" component={StoreIntro} />
      <Route path="lottery" component={Lottery} />
      <Route path="user-list" component={UserList} />
      <Route path="user-unbuy-list" component={UserUnBuyList} />
      <Route path="user-birth-list" component={UserBirthList} />
      <Route path="product-list" component={ProductList} />
      <Route path="banner-list" component={BannerList} />
      <Route path="product-group-list" component={ProductGroupList} />
      <Route path="addStore" component={AddStore} />
      <Route path="editStore" component={EditStore} />
      <Route path="editBanner" component={EditBanner} />
      <Route path="napaStoreList" component={NapaStoreList} />
      <Route path="addUser" component={AddUser} />
      <Route path="editPhoneUser" component={EditPhoneUser} />
      <Route path="phoneUserList" component={PhoneUserList} />
      <Route path="editProductGroup" component={EditProductGroup} />
      <Route path="*" component={NotFound} />
    </Route>
  </Router>,
  document.getElementById('root')
);
