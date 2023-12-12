
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
// ======================= FORMULAIRE AJOUT PATIENT =================

document.addEventListener("DOMContentLoaded", function () {
    const overlay = document.getElementById("overlay");
    const modal = document.getElementById("myModal");
    const modalContent = document.getElementById("modalContent");
    const openPatientFormBtn = document.getElementById("lienAjoutClient");
    const closeModalBtn = document.getElementById("closeModalBtn");

    openPatientFormBtn.addEventListener("click", function () {
        loadForm("formulaireAjoutPatient.html");
    });

    closeModalBtn.addEventListener("click", function () {
        closeModal();
    });

    overlay.addEventListener("click", function () {
        closeModal();
    });

    function loadForm(formFile) {
        // Charger le contenu du formulaire depuis le fichier HTML
        fetch(formFile)
            .then(response => response.text())
            .then(html => {
                modalContent.innerHTML = html;
                showModal();
            })
            .catch(error => console.error("Erreur de chargement du formulaire:", error));
    }

    function showModal() {
        overlay.style.display = "block";
        modal.style.display = "block";
    }

    function closeModal() {
        overlay.style.display = "none";
        modal.style.display = "none";
        modalContent.innerHTML = ""; // Effacer le contenu du formulaire
    }
});



