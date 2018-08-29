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
import EditQuickNavi from './containers/EditQuickNavi'
import EditBanner from './containers/EditBanner'
import NapaStoreList from './containers/NapaStoreList'
import UserList from './containers/UserList'
import UserBirthList from './containers/UserBirthList'
import UserUnBuyList from './containers/UserUnBuyList'
import FullCutShipping from './containers/FullCutShipping'
import AddUser from './containers/AddUser'
import EditPhoneUser from './containers/EditPhoneUser'
import PhoneUserList from './containers/PhoneUserList'
import GlobalConfig from './containers/GlobalConfig'
import RechargeConfig from './containers/RechargeConfig'
import RechargeConfigNew from './containers/RechargeConfigNew'
import Login from './containers/Login'
import ProductList from './containers/ProductList'
import QuickNaviList from './containers/QuickNaviList'
import QuickNaviProduct from './containers/QuickNaviProduct'
import EditQuickNaviProduct from './containers/EditQuickNaviProduct'
import BannerList from './containers/BannerList'
import ProductGroupList from './containers/ProductGroupList'
import CategoryList from './containers/CategoryList'
import CommentList from './containers/CommentList'
import Freight from './containers/Freight'
import StoreIntro from './containers/StoreIntro'
import Lottery from './containers/LotteryConfig'
import EditProductGroup from './containers/EditProductGroup'
import ModuleSetting from './containers/ModuleSetting'
import EditModuleSetting from './containers/EditModuleSetting'
import ShakeMonitor from './containers/ShakeMonitor'
import EditCategory from './containers/EditCategory'
import BirthVoucher from './containers/BirthVoucher'
import FullCut from './containers/FullCut'
import BindMemberSetting from './containers/BindMemberSetting'
import EvaluationConfiguration from './containers/EvaluationConfiguration'
import IntegralInConfiguration from './containers/IntegralInConfiguration'
import SystemConfig from './containers/SystemConfig'
import ActivityConfig from './containers/ActivityConfig'
import RechargeConfigList from './containers/RechargeConfigList'
import OrderSettingPick from './containers/OrderSettingPick'
import AuditRefund from './containers/AuditRefund'
import AuditRefundOrderDetail from './containers/AuditRefundOrderDetail'
import VipList from './containers/VipList'
import VoucherDelivery from './containers/VoucherDelivery'
import BargainSetting from './containers/BargainSetting'
import BargainList from './containers/BargainList'
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
      <Route path="system-config" component={SystemConfig} />
      <Route path="activity-config" component={ActivityConfig} />
      <Route path="recharge-config" component={RechargeConfig} />
      <Route path="recharge-config-new" component={RechargeConfigNew} />
      <Route path="recharge-config-list" component={RechargeConfigList} />
      <Route path="freight" component={Freight} />
      <Route path="store-intro" component={StoreIntro} />
      <Route path="lottery" component={Lottery} />
      <Route path="birth-voucher" component={BirthVoucher} />
      <Route path="user-list" component={UserList} />
      <Route path="comment-list" component={CommentList} />
      <Route path="fullCutShipping" component={FullCutShipping} />
      <Route path="bindMemberSetting" component={BindMemberSetting} />
      <Route path="evaluationConfiguration" component={EvaluationConfiguration} />
      <Route path="integralinConfiguration" component={IntegralInConfiguration} />
      <Route path="FullCut" component={FullCut} />
      <Route path="user-unbuy-list" component={UserUnBuyList} />
      <Route path="user-birth-list" component={UserBirthList} />
      <Route path="quick-navi-product" component={QuickNaviProduct} />
      <Route path="edit-quick-navi-product" component={EditQuickNaviProduct} />
      <Route path="product-list" component={ProductList} />
      <Route path="category-list" component={CategoryList} />
      <Route path="banner-list" component={BannerList} />
      <Route path="product-group-list" component={ProductGroupList} />
      <Route path="quick-navi-list" component={QuickNaviList} />
      <Route path="addStore" component={AddStore} />
      <Route path="editCategory" component={EditCategory} />
      <Route path="editStore" component={EditStore} />
      <Route path="editBanner" component={EditBanner} />
      <Route path="editQuickNavi" component={EditQuickNavi} />
      <Route path="napaStoreList" component={NapaStoreList} />
      <Route path="addUser" component={AddUser} />
      <Route path="editPhoneUser" component={EditPhoneUser} />
      <Route path="phoneUserList" component={PhoneUserList} />
      <Route path="editProductGroup" component={EditProductGroup} />
      <Route path="moduleSetting" component={ModuleSetting} />
      <Route path="editModuleSetting" component={EditModuleSetting} />
      <Route path="orderSettingPick" component={OrderSettingPick}/>
      <Route path="auditRefund" component={AuditRefund} />
      <Route path="auditRefundOrderDetail" component={AuditRefundOrderDetail} />
      <Route path="auditRefundOrderDetail/:id" component={AuditRefundOrderDetail} />
      <Route path="vip-list" component={VipList} />
      <Route path= "voucher-delivery" component={VoucherDelivery} />
      <Route path= "bargain-setting" component={BargainSetting} />
      <Route path= "bargain-setting/:id" component={BargainSetting} />
      <Route path= "bargain-list" component={BargainList} />
      <Route path="*" component={NotFound} />
    </Route>
  </Router>,
  document.getElementById('root')
);
