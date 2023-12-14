
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
//========================== REMPLISSAGE LISTE DE DOCTEURS======================

// document.addEventListener("DOMContentLoaded", function() {
//     const medecinTraitantSelect = document.getElementById("medecinTraitant");
//
//     // Vérifiez si l'élément est trouvé avant d'ajouter des options
//     if (medecinTraitantSelect) {
//         // Appel à l'endpoint Spring pour récupérer les noms des médecins
//         fetch("http://localhost:8080/docteur/all")
//             .then(response => response.json())
//             .then(data => {
//                 console.log("Données reçues du serveur:", data);
//                 if (data && data.length > 0) {
//                     // Remplir la combobox avec les noms des médecins
//                     const options = data.map(docteur => {
//                         const option = document.createElement("option");
//                         option.value = docteur.idDocteur;
//                         option.text = docteur.nomDocteur + " " + docteur.prenomDocteur;
//                         return option;
//                     });
//
//                     options.forEach(option => {
//                         medecinTraitantSelect.appendChild(option);
//                         console.log("Valeur de l'option :", option);
//                     });
//                 } else {
//                     console.error("La réponse du backend est vide ou n'est pas une liste.");
//                 }
//             })
//             .catch(error => console.error("Erreur lors de la récupération des noms des médecins:", error));
//     } else {
//         console.error("L'élément avec l'ID 'medecinTraitant' n'a pas été trouvé.");
//     }
//});
