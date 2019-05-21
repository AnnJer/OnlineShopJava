import React, {Component} from 'react';
import styles from "./BlockLIst.module.css"
import Product from "./Product";
import {doDelete, doGet, doPost} from "../requests/requests";
import {Promise as props} from "q";
import {render} from "../index";
import {withRouter} from "react-router-dom";

class BlockListPage extends Component{

constructor(props){
    super(props);

    this.state = {
        blocklist : []
    };
    const auth = localStorage.getItem("auth");
    const token = JSON.parse(auth)["token"];

    doGet("/blocklist", {token}).then(
        json=>{
            this.setState( {blocklist : json});
        }
    );

    this.delete = ( id)=>{
        console.log(token);


        doDelete("/blocklist",  id, token).then(
            json=>{
                this.setState((state, props) => {
                   let blocklist = state.blocklist.filter((element)=>{
                       return element.id != id;
                   });

                    state.blocklist = blocklist;
                    return state;
                });
            }
        )
    }


}
    render() {
        return(
            <div>
                <table>
                    <tbody>


                    <tr>
                        <th>Id</th>
                        <th>User Id</th>
                        <th>Reason</th>
                    </tr>
                {   this.state.blocklist.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td>{element.id}</td>
                            <td>{element.user_id}</td>
                            <td>{element.reason}</td>
                            <span onClick={() => {this.delete(element.id)}}>delete</span>

                        </tr>


                 )
                })

                }
                </tbody></table>
            </div>
        )
    }

}

export default withRouter( BlockListPage);

