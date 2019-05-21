import React, {Component} from 'react';
import {doGet, doPost, doPut} from "../requests/requests";
import {withRouter} from "react-router-dom";

class UsersPage extends Component{

constructor(props){

    super(props);

    this.state = {
        users : []
    };

    const auth = localStorage.getItem("auth");
    const token = JSON.parse(auth)["token"];

    doGet("/users", {token}).then(
        json=>{
            this.setState( {users : json});
        }
    );


    this.makeBlocked = ( user_id, i)=>{

        doPost('/blocklist', {user_id, token} ).then(
            json=>{

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
                    <th>Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Is admin</th>
                    <th>Email confirmation</th>
                </tr>

                {this.state.users.map((element, i) => {
                        return(
                            <tr key={i}>
                                <td>{element.id}</td>
                                <td>{element.name}</td>
                                <td>{element.email}</td>
                                <td>{element.password}</td>
                                <td>{element.is_admin}</td>
                                <td>{element.email_confirmation}</td>
                                <span onClick={() => {this.makeBlocked(element.id, i)}}>Block</span>

                            </tr>


                        )
                    }
                )
                }


                </tbody>
            </table>
        </div>
    )
    }
}
export default withRouter( UsersPage);