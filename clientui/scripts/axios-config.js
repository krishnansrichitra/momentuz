axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem("jwtToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => Promise.reject(error)
);


const iframeLocation = window.location;

// Query params from iframe URL
const params = new URLSearchParams(iframeLocation.search);
const urlPrefix = (iframeLocation.origin + "/").replace("5500","8080");