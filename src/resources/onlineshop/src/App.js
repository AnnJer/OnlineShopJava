import React from 'react';
import './App.css';
import {Route, NavLink} from "react-router-dom";
import ProductsPage from "./components/ProductsPage";
import LogInPage from "./components/LogInPage";
import BlockListPage from "./components/BlockListPage";
import LogOut from "./components/LogOut";
import OrdersPage from "./components/OrdersPage";
import UsersPage from "./components/UsersPage";
import {isAdmin, isSignedIn} from "./requests/AuthUtil";



function App (){

    return (
        <div>
            <div className="header">
               <div>
                   <NavLink exact to="/products" >Products</NavLink>
               </div>
                {   isSignedIn() &&
                    <div>
                        <NavLink exact to="/log_out">Log out</NavLink>
                    </div>

                }

                {   !isSignedIn() &&
                    <div>
                        <NavLink exact to="/log_in">Log in</NavLink>
                    </div>
                }

                {   isAdmin() &&
                    <div >
                        <NavLink exact to="/blocklist" >Block List</NavLink>
                    </div>
                }
                {   isAdmin() &&
                <div >
                    <NavLink exact to="/orders" >Orders</NavLink>
                </div>
                }

                {   isAdmin() &&
                <div >
                    <NavLink exact to="/users" >Users</NavLink>
                </div>
                }


            </div>

            <Route exact path="/products" component={ProductsPage}/>

            <Route exact path="/log_in" component={LogInPage}/>

            <Route exact path="/blocklist" component={BlockListPage}/>

            <Route exact path="/log_out" component={LogOut}/>

            <Route exact path="/orders" component={OrdersPage}/>

            <Route exact path="/users" component={UsersPage}/>

        </div>
    );

}

export default App;
