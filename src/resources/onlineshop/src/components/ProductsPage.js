import React, {Component} from 'react';
import Product from "./Product";
import {doGet, doPost} from "../requests/requests";



class ProductsPage extends Component{

    constructor(props) {
        super(props);

        this.state = {
            products: [],
            categories: [],
            activeCategory: null,
            popupData: null
        };

        this.showPopup = (productId, price) => {
            this.setState(
                (state, props) => {
                    state.popupData = [productId, price];
                    return state;
                }
            );
        };

        this.closePopup = () => {
            const auth =localStorage.getItem("auth");
            let dict = {
                "client_id":JSON.parse(auth)["user_id"],
                "product_id": this.state.popupData[0],
                "amount": 1
            };
            doPost("/orders", dict).then(

            );

            this.setState(
                (state, props) => {
                    state.popupData = null;
                    return state;
                }
            );
        };

        this.loadProducts = (category) => {

            let params = null;

            if (category) {
                params = {byCategoryName: category}
            }

            doGet("/products", params).then(
                json => {

                    this.setState(
                        (state, props) => {
                            state.products = json;
                            state.activeCategory = category;

                            return state;
                        }
                    );

                }
            );
        };

        this.loadCategories = () => {
            doGet("/categories").then(
                json => {

                    this.setState(
                        (state, props) => {
                            state.categories = json;

                            return state;
                        }
                    );

                }
            );
        }
    }

    componentDidMount() {
        this.loadCategories();
        this.loadProducts();
    }


    render() {

        return (
            <div>
                <div className="body">
                    <div className="sidebar">


                        <div className="menuItem">
                            <span onClick={(e) => { this.loadProducts() }}>all</span>
                        </div>

                        { this.state.categories.map(
                            (category, i) => {
                                return (
                                    <div key={i} className="menuItem">
                                        <span onClick={(e) => { this.loadProducts(category.name) }}>{category.name}</span>
                                    </div>
                                );
                            }
                        )

                        }



                    </div>

                    <div className="content">

                        { this.state.popupData &&
                            <div className="popup">
                                <div className="tools" onClick={this.closePopup}>
                                    <span>close</span>
                                </div>

                                <h3 >Id: {this.state.popupData[0]}</h3>

                                <h3>Price: {this.state.popupData[1]}</h3>

                                <h3>Card number: 1111 2222 3333 4444. After payment we will send you the goods. Enjoy your shopping!</h3>

                            </div>
                        }


                        {    this.state.products.map(
                            (product, i) => {
                                return <Product key={i} data={product}
                                                showPopup={this.showPopup}
                                />
                            }
                        )

                        }

                    </div>
                </div>

            </div>
        );
    }

}

export default ProductsPage;