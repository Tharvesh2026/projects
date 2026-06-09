<style>

.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #2c3e50;
    padding: 12px 25px;
    color: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}


.navbar strong {
    font-size: 1.4rem;
    font-weight: 600;
}

.navbar div:last-child {
    display: flex;
    align-items: center;
    gap: 15px;
}


.navbar a {
    color: white;
    text-decoration: none;
    padding: 8px 14px;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.navbar a:hover {
    background-color: #34495e;
}

.navbar button {
    background-color: #e74c3c;
    color: white;
    border: none;
    padding: 8px 14px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 0.95rem;
    transition: background-color 0.3s ease;
}

.navbar button:hover {
    background-color: #c0392b;
}
</style>

<nav class="navbar">
    <div>
        <strong>Session App</strong>
    </div>

    <div>
        <a href="welcome.jsp">Home</a>
        <a href="settings.jsp">Settings</a>

        <form action="logout" method="post" style="display:inline;">
            <input type="hidden" name="csrfToken" value="${sessionScope.X_Secret_Token}">
            <button type="submit" onclick="return confirmLogout();">
                Logout
            </button>
        </form>
    </div>
</nav>

<script>
    function confirmLogout(){
        return confirm("Are you Sure to Logout?")
    }
</script>