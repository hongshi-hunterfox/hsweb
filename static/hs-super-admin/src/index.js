import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import './index.css';
import App from './App'
import Home from './containers/Home'
import DatasourceList from './containers/DatasourceList'
import EditDatasource from './containers/EditDatasource'
import AddDatasource from './containers/AddDatasource'
import NotFound from './containers/NotFound'
import { Router, Route, IndexRoute, browserHistory } from 'react-router'

ReactDOM.render(
  <Router history={browserHistory}>

    <Route path="/" component={App}>
      <IndexRoute component={Home}/>
      <Route path="datasource" component={DatasourceList} />
      <Route path="editDatasource" component={EditDatasource} />
      <Route path="addDatasource" component={AddDatasource} />
    </Route>
  </Router>,
  document.getElementById('root')
);
