
export const isSignedIn = () => {
    const auth = localStorage.getItem("auth");

    if (!auth) {
        return false;
    }

    return true;
};


export const isAdmin = ()=>{
    const auth = localStorage.getItem("auth");

    if (!auth) {
        return false;
    }

    return JSON.parse(auth).isAdmin;
};

export const getToken = () => {
    const auth = localStorage.getItem("auth");

    if (!auth) {
        return null;
    }

    return JSON.parse(auth)["token"];
};

