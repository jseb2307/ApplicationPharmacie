/*const navbarMenu = document.querySelector(".navbar .links");*/
const hamburgerBtn = document.querySelector(".hamburger-btn");
const hideMenuBtn = navbarMenu.querySelector(".close-btn");
const showPopupBtn = document.querySelector(".logout-link");
const formPopup = document.querySelector(".form-popup");
const hidePopupBtn = formPopup.querySelector(".close-btn");
const signupLoginLink = formPopup.querySelectorAll(".bottom-link a");


// Afficher la fenêtre contextuelle de connexion lors du chargement de la page
document.addEventListener("DOMContentLoaded", () => {
    showPopupBtn.click();
});
// Afficher le menu mobile
/*hamburgerBtn.addEventListener("click", () => {
    navbarMenu.classList.toggle("show-menu");
});

// Masquer le menu mobile
hideMenuBtn.addEventListener("click", () =>  hamburgerBtn.click());*/

// Afficher la fenêtre contextuelle de connexion
showPopupBtn.addEventListener("click", () => {
    document.body.classList.toggle("show-popup");
});

// Masquer la fenêtre contextuelle de connexion
hidePopupBtn.addEventListener("click", () => showPopupBtn.click());

// Afficher ou masquer le formulaire d'inscription
signupLoginLink.forEach(link => {
    link.addEventListener("click", (e) => {
        e.preventDefault();
        formPopup.classList[link.id === 'signup-link' ? 'add' : 'remove']("show-signup");
    });
});

