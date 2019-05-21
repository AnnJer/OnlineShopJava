import React, {Component} from 'react';
// import {doGet} from "../requests/requests";
import style from "./LogIn.module.css"
import image from "./enter.png"
import {doPost} from "../requests/requests";
import {withRouter} from "react-router-dom";
import {render} from "../index";


const LogInPage = (props)=>{

    const login = (e)=>{
        e.preventDefault();

        const form = e.target.parentNode;
        const email = form.email.value;
        const password = form.password.value;

        if(!email || !password){
            alert("Fill ");
            return
        }

        doPost("/log_in", {email, password}).then(
            json => {
                localStorage.setItem("auth", JSON.stringify(json));
                props.history.push('/products');
                render();
            }
        );

    };

    return (
        <div className={style.container}>
            <div className={style.form}>
                <form>
                   <p>
                       <b>Email</b><br/>
                       <input type="email" size="20" name="email"/>
                   </p>
                    <p>
                        <b>Password</b><br/>
                        <input type="password" size="20" name="password"/>
                    </p>
                    <button onClick={login}>
                        <img className={style.img} src={image} alt = ""/>Log in
                    </button>
                </form>
            </div>
        </div>
    );
};

export default withRouter(LogInPage);