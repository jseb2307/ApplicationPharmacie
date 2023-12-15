
//========================== REMPLISSAGE LISTE DE DOCTEURS======================


    const medecinTraitantSelect = document.getElementById("medecinTraitant");

    // Vérifiez si l'élément est trouvé avant d'ajouter des options
    if (medecinTraitantSelect) {
        // Appel à l'endpoint Spring pour récupérer les noms des médecins
        fetch("http://localhost:8080/docteur/all")
            .then(response => response.json())
            .then(data => {

                if (data && data.length > 0) {
                    // Remplir la combobox avec les noms des médecins
                    const options = data.map(docteur => {
                        const option = document.createElement("option");
                        option.value = docteur.idDocteur;
                        option.text = docteur.nomDocteur + " " + docteur.prenomDocteur;
                        return option;
                    });

                    options.forEach(option => {
                        medecinTraitantSelect.appendChild(option);

                    });
                    // Ajout d'un écouteur d'événements pour capturer la sélection
                    // medecinTraitantSelect.addEventListener("change", function() {
                    //     const selectedDocteurId = medecinTraitantSelect.value;
                    //     console.log("Docteur sélectionné ID:", selectedDocteurId);
                    // });
                } else {
                    console.error("La réponse du backend est vide ou n'est pas une liste.");
                }
            })
            .catch(error => console.error("Erreur lors de la récupération des noms des médecins:", error));
    } else {
        console.error("L'élément avec l'ID 'medecinTraitant' n'a pas été trouvé.");
    }


// Selecting form and input elements
const form = document.querySelector("form");
// const passwordInput = document.getElementById("password");
// const passToggleBtn = document.getElementById("pass-toggle-btn");


// Fonction pour afficher les messages d'erreur
const showError = (field, errorText) =>
{
    field.classList.add("error");
    const errorElement = document.createElement("small");
    errorElement.classList.add("error-text");
    errorElement.innerText = errorText;
    field.closest(".form-group").appendChild(errorElement);
}

// Fonction pour gérer la soumission du formulaire
const handleFormData = (e) =>
{
    e.preventDefault();

    // Récupération des éléments d'entrée
    const nomInput = document.getElementById("Nom");
    const prenomInput = document.getElementById("Prenom");
    const emailInput = document.getElementById("email");
    const dateInput = document.getElementById("date");
    const medecinTraitant = document.getElementById("medecinTraitant")
    //const genderInput = document.getElementById("gender");

    // //Obtenir des valeurs tronquées à partir des champs de saisie
    const nom = nomInput.value.trim();
    const prenom = prenomInput.value.trim();
    const email = emailInput.value.trim();
    // const password = passwordInput.value.trim();
    const date = dateInput.value;
    // const gender = genderInput.value;

    // Expression régulière pour la validation des e-mails
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    //Effacement des messages d'erreur précédents
    document.querySelectorAll(".form-group .error").forEach(field => field.classList.remove("error"));
    document.querySelectorAll(".error-text").forEach(errorText => errorText.remove());

    // Effectuer des contrôles de validation
    if (nom === "") {
        showError(nomInput, "Enter your full name");
    }
    if (prenom === "") {
        showError(prenomInput, "Enter your full name");
    }
    if (!emailPattern.test(email)) {
        showError(emailInput, "Enter a valid email address");
    }
    // if (password === "") {
    //     showError(passwordInput, "Enter your password");
    // }
    if (date === "") {
        showError(dateInput, "Select your date of birth");
    }
    // if (gender === "") {
    //     showError(genderInput, "Select your gender");
    // }

    // Vérification des erreurs restantes avant la soumission du formulaire
    const errorInputs = document.querySelectorAll(".form-group .error");
    if (errorInputs.length > 0) return;

    // soumission du formulaire
    // form.submit();
    const submitBtn = document.getElementById('submitBtn');
    submitBtn.addEventListener('click', function() {


        console.log(nom);
        console.log(prenom);
        console.log(email);
        console.log(date);
        // Ajout d'un écouteur d'événements pour capturer la sélection

            const selectedDocteurId = medecinTraitantSelect.value;
            console.log("Docteur sélectionné ID:", selectedDocteurId);



    });



    // Gestion de l'événement de soumission de formulaire
    form.addEventListener("submit", handleFormData);
}

// Toggling password visibility
// passToggleBtn.addEventListener('click', () => {
//     passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
//     passwordInput.type = passwordInput.type === "password" ? "text" : "password";
// });

// Gestion de l'événement de soumission de formulaire
form.addEventListener("submit", handleFormData);