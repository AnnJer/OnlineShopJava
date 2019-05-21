import React from "react"
import styles from "./Product.module.css"
import image1 from "./PlaceholderProductImage.jpg"
import image2 from "./shopping-cart.png"
import {isSignedIn} from "../requests/AuthUtil";




const Product = (props) => {

    return (
        <div className={styles.container}>
            <img  className={styles.img} src={image1} alt = ""/>
            <ul>
                <a href>{props.data.name}</a>
                <li>price: <b>{props.data.price} $ </b></li>

                { props.data.amount > 0 ?
                    <li>amount: <b> {props.data.amount}</b></li>
                    :
                    <li><b>Product out</b></li>
                }
                { isSignedIn() &&
                    <img  onClick={() => {props.showPopup(props.data.id, props.data.price)}} className={styles.imgLogo} src={image2} alt = ""/>
                }

            </ul>
        </div>
    );
};

export default Product;