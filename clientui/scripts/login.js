function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Basic validation
    if (!username || !password) {
        showError("Username and password are required");
        return;
    }

    axios.post("http://localhost:8080/api/auth/login", {
        username: username,
        password: password
    })
    .then(response => {
        // Assuming backend returns: { token: "JWT_VALUE" }
        const token = response.data.token;

        // Save JWT locally
        localStorage.setItem("jwtToken", token);

        // Redirect after login
        window.location.href = "landing.html";
    })
    .catch(error => {
        console.error(error);
        showError("Invalid username or password");
    });
}

function showError(message) {
    const errorDiv = document.getElementById("errorMsg");
    errorDiv.textContent = message;
    errorDiv.style.display = "block";
}
