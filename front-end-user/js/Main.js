window.addEventListener("scroll", function () {
    var header = document.querySelector("header");
    if (header) {
        if (window.scrollY > 50) {
            header.classList.add("scrolled");
        } else {
            header.classList.remove("scrolled");
        }
    }
});

function openLoginForm() {
    var loginFormArea = document.getElementById("login_form_area");
    if (loginFormArea) {
        loginFormArea.style.display = "block";
        document.body.style.overflow = "hidden"; // 禁用滾動
    }
}

function closeLoginForm() {
    var loginFormArea = document.getElementById("login_form_area");
    if (loginFormArea) {
        loginFormArea.style.display = "none";
        document.body.style.overflow = "auto"; // 恢復滾動
    }
}

//
window.onscroll = function () {
    var navbar = document.querySelector('.navbar');
    if (navbar) {
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    }
};
