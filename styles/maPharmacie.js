
const showPopupBtn = document.querySelector(".logout-link");
const formPopup = document.querySelector(".form-popup");
const hidePopupBtn = formPopup.querySelector(".close-btn");
const signupLoginLink = formPopup.querySelectorAll(".bottom-link a");


// Récupérer la référence des éléments input par leurs id
const utilisateurField = document.getElementById("utilisateur");
const passwordField = document.getElementById("passwordField");
const form = document.getElementById("loginForm");
loginButton.addEventListener("click", () =>{
    const utilisateurValue = utilisateurField.value;
    const passwordValue = passwordField.value;

    console.log("Email:", utilisateurValue);
    console.log("Mot de Passe:", passwordValue);

    // if(emailValue.match() && passwordValue.match()) {
    //
    //     container = {
    //         identifier: emailValue,
    //         password: passwordValue
    //     };
    //
    //     const containerStr = JSON.stringify(container);
    //     // TODO: envoyer "containerStr" au serveur java.
    // };
});

document.addEventListener("DOMContentLoaded", () => {
    showPopupBtn.click();

});
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

