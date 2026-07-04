const COURSE_API = "/api/v2/courses";
const STUDENT_API = "/api/v02/students";
const ADMIN_API = "/api/v2/admin";

function showMessage(elementId, message, type = "success") {
    const box = document.getElementById(elementId);

    if (!box) return;

    box.style.display = "block";
    box.textContent = message;

    box.className = "message-box";

    if (type === "success") {
        box.classList.add("message-success");
    } else {
        box.classList.add("message-error");
    }

    setTimeout(() => {
        box.style.display = "none";
    }, 3500);
}

async function requestApi(url, options = {}) {
    const response = await fetch(url, options);

    let result = null;

    try {
        result = await response.json();
    } catch (error) {
        result = null;
    }

    if (!response.ok) {
        const message = result?.message || result?.error || "Something went wrong";
        throw new Error(message);
    }

    return result;
}