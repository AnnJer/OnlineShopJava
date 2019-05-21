import React, {Component} from 'react';
import {doGet, doPut} from "../requests/requests";
import {withRouter} from "react-router-dom";

class OrdersPage extends Component{

constructor(props){
    super(props);

    this.state = {
        orders : []
    };

    const auth = localStorage.getItem("auth");
    const token = JSON.parse(auth)["token"];

    doGet("/orders", {token}).then(
        json=>{
            this.setState( {orders : json});
        }
    );

    this.makePayed = ( id, i)=>{

        const payed = 1;
        doPut("/orders", id, {token, payed}).then(
            json=>{
                this.setState( this.setState(
                    (state, props) => {
                        state.orders[i]["payed"] = 1;
                        return state;
                    }
                )
                );

            }
        )
    }
}


    render() {
    return(
        <dev>
            <table>
                <tbody>
                <tr>
                    <th>Id</th>
                    <th>Client Id</th>
                    <th>Product Id</th>
                    <th>Amount</th>
                    <th>Payed</th>
                </tr>

                {this.state.orders.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td>{element.id}</td>
                            <td>{element.client_id}</td>
                            <td>{element.product_id}</td>
                            <td>{element.amount}</td>
                            <td>{element.payed}</td>
                            <span onClick={() => {this.makePayed(element.id, i)}}>Customer paid </span>

                        </tr>


                            )
                        }
                    )
                }


                </tbody>
            </table>
        </dev>
    )


}}

export default withRouter( OrdersPage);