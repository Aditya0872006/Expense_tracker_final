const loginForm = document.getElementById('loginForm');

loginForm.addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if (email === "" || password === "") {
        alert("Please fill in all fields!");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();

        if (data.message === "Login successful!") {
            // ✅ Save the whole response (contains name + email)
            localStorage.setItem("user", JSON.stringify(data));
            window.location.href = "expenseTracker.html"; // Redirect
        } else {
            alert(data.message);
        }
    } catch (error) {
        console.error("Login failed:", error);
        alert("Something went wrong! Please try again.");
    }
});
