const BASE_API = "http://localhost:8080/api";


const mapDictToQueryString = (dict) => {
    let query = [];

    for (let key in dict) {
        query.push(key + "=" + dict[key]);
    }

    return query.join("&");
};

export const doGet = (path, params) => {
    let queryStr = '';

    if (params) {
        queryStr += "?" + mapDictToQueryString(params);
    }

    const init = {
        method: 'GET',
        mode: 'cors'
    };

    return fetch(`${BASE_API}${path}${queryStr}`, init)
        .then(resp => resp.json());
};

export const doPost = (path, params) => {
    let queryStr = '';


    if (params) {
        queryStr += mapDictToQueryString(params);
    }

    const init = {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: queryStr
    };

    console.log(queryStr);

    return fetch(`${BASE_API}${path}`, init)
        .then(resp => resp.json());
};


export const doPut = (path, id, params) => {
    let queryStr = '';

    if (params) {
        queryStr += mapDictToQueryString(params);
    }

    const init = {
        method: 'PUT',
        mode: 'cors'
    };

    return fetch(`${BASE_API}${path}/${id}?${queryStr}`, init)
        .then(resp => resp.json());
};


export const doDelete = (path, id, token) => {


    const init = {
        method: 'DELETE',
        mode: 'cors',
        headers: {
            "token": token
        }
    };

    return fetch(`${BASE_API}${path}/${id}`, init)
        .then(resp => resp.json());
};

