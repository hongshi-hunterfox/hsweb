
import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import './index.css';
import App from './App'
import Home from './containers/Home'
import MemberCenter from './containers/MemberCenter'
import BossCenter from './containers/BossCenter'
import Recharge from './containers/Recharge'
import UnpayOrderList from './containers/UnpayOrderList'
import OrderList from './containers/OrderList'
import MemberCard from './containers/MemberCard'
import Information from './containers/Information'
import Install from './containers/Install'
import MemberSetting from './containers/MemberSetting'
import MemberSetting1 from './containers/MemberSetting1'
import MemberSetting2 from './containers/MemberSetting2'
import Forces from './containers/Forces'
import RechargeList from './containers/RechargeList'
import Detail from './containers/Detail'
import Cart from './containers/Cart'
import Payment from './containers/Payment'
import PhoneLogin from './containers/PhoneLogin'
import PaymentAlipay from './containers/PaymentAlipay'
import DistributionCenter from './containers/DistributionCenter'
import DistributionUser from './containers/DistributionUser'
import DistributionOrder from './containers/DistributionOrder'
import EditAddr from './containers/EditAddr'
import Address from './containers/Address'
import Coupon from './containers/Coupon'
import Order from './containers/Order'
import StoreList from './containers/StoreList'
import Lottery from './containers/Lottery'
import StoreInfo from './containers/StoreInfo'
import AllProduct from './containers/AllProduct'
import ShakeMonitor from './containers/ShakeMonitor'
import Comment from './containers/Comment'
import CommentDetail from './containers/CommentDetail'
import DataView from './containers/DataView'
import ShowCoupon from './containers/ShowCoupon'
import SignIn from './containers/SignIn'
import MyOrderDetail from './containers/MyOrderDetail.js'
import OnlineOrder from './containers/OnlineOrder.js'
import Refund from './containers/Refund.js'
import ChouJiang from './containers/ChouJiang.js'
import SwitchShop from './containers/SwitchShop.js'
import bargain from './containers/bargain.js'
import LaunchBargain from './containers/LaunchBargain.js'
import difference from './containers/Difference.js'
import NotFound from './containers/NotFound'
import { Router, Route, IndexRoute, browserHistory } from 'react-router'

ReactDOM.render(
  <Router history={browserHistory}>
    <Route path="/" component={App}>
      <IndexRoute component={Home}/>
      <Route path="detail/:id" component={Detail} />
      <Route path="cart" component={Cart} />
      <Route path="shake-monitor" component={ShakeMonitor} />
      <Route path="member-center" component={MemberCenter} />
      <Route path="boss-center" component={BossCenter} />
      <Route path="member-card" component={MemberCard} />
      <Route path="information" component={Information} />
       <Route path="install" component={Install} />
      <Route path="member-setting" component={MemberSetting} />
      <Route path="member-setting1" component={MemberSetting1} />
      <Route path="member-setting2" component={MemberSetting2} />
      <Route path="forces" component={Forces} />
      <Route path="recharge-list" component={RechargeList} />
      <Route path="seller/recharge" component={Recharge} />
      <Route path="unpay-order-list" component={UnpayOrderList} />
      <Route path="phone-login" component={PhoneLogin} />
      <Route path="store-info" component={StoreInfo} />
      <Route path="order-list" component={OrderList} />
      <Route path="all-product" component={AllProduct} />
      <Route path="editaddr" component={EditAddr} />
      <Route path="seller/payment" component={Payment} />
      <Route path="seller/paymentAlipay" component={PaymentAlipay} />
      <Route path="lottery" component={Lottery} />
      <Route path="distribution-center" component={DistributionCenter} />
      <Route path="distribution-user" component={DistributionUser} />
      <Route path="distribution-order" component={DistributionOrder} />
      <Route path="comment" component={Comment} />
      <Route path="commentDetail" component={CommentDetail} />
      <Route path="address" component={Address} />
      <Route path="coupon" component={Coupon} />
      <Route path="order" component={Order} />
      <Route path="storeList" component={StoreList} />
      <Route path="dataView" component={DataView} />
      <Route path="ShowCoupon" component={ShowCoupon} />
      <Route path="SignIn" component={SignIn} />
      <Route path="myOrderDetail/:id" component={MyOrderDetail} />
      <Route path="OnlineOrder" component={OnlineOrder} />
      <Route path="seller/refund" component={Refund}/>
      <Route path="luck_draw" component={ChouJiang}/>
      <Route path="switch-shop/:id" component={SwitchShop}/>
      <Route path="bargain" component={bargain}/>
      <Route path="launch-bargain/:valueId" component={LaunchBargain}/>
      <Route path="difference" component={difference}/>
      <Route path="*" component={NotFound} />
    </Route>
  </Router>,
  document.getElementById('root')
);
