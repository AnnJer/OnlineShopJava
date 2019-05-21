import React, {Component} from 'react';
import {doPost} from "../requests/requests";
import {render} from "../index";
import {getToken} from "../requests/AuthUtil";
import {withRouter} from "react-router-dom";

  const  LogOut=(props)=>{
    doPost("/log_out", getToken).then(
        json => {
            localStorage.removeItem("auth");
            props.history.push('/products');
            render();

        }
    );
    return (<div></div>);

};

export default withRouter(LogOut);
