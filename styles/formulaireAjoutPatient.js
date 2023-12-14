
//========================== REMPLISSAGE LISTE DE DOCTEURS======================

document.addEventListener("DOMContentLoaded", function() {
    const medecinTraitantSelect = document.getElementById("medecinTraitant");

    // Vérifiez si l'élément est trouvé avant d'ajouter des options
    if (medecinTraitantSelect) {
        // Appel à l'endpoint Spring pour récupérer les noms des médecins
        fetch("http://localhost:8080/docteur/all")
            .then(response => response.json())
            .then(data => {
                console.log("Données reçues du serveur:", data);
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
                        console.log("Valeur de l'option :", option);
                    });
                } else {
                    console.error("La réponse du backend est vide ou n'est pas une liste.");
                }
            })
            .catch(error => console.error("Erreur lors de la récupération des noms des médecins:", error));
    } else {
        console.error("L'élément avec l'ID 'medecinTraitant' n'a pas été trouvé.");
    }
});

// Selecting form and input elements
const form = document.querySelector("form");
const passwordInput = document.getElementById("password");
const passToggleBtn = document.getElementById("pass-toggle-btn");

// Function to display error messages
const showError = (field, errorText) => {
    field.classList.add("error");
    const errorElement = document.createElement("small");
    errorElement.classList.add("error-text");
    errorElement.innerText = errorText;
    field.closest(".form-group").appendChild(errorElement);
}

// Function to handle form submission
const handleFormData = (e) => {
    e.preventDefault();

    // Retrieving input elements
    const fullnameInput = document.getElementById("fullname");
    const emailInput = document.getElementById("email");
    const dateInput = document.getElementById("date");
    const genderInput = document.getElementById("gender");

    // Getting trimmed values from input fields
    const fullname = fullnameInput.value.trim();
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const date = dateInput.value;
    const gender = genderInput.value;

    // Regular expression pattern for email validation
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    // Clearing previous error messages
    document.querySelectorAll(".form-group .error").forEach(field => field.classList.remove("error"));
    document.querySelectorAll(".error-text").forEach(errorText => errorText.remove());

    // Performing validation checks
    if (fullname === "") {
        showError(fullnameInput, "Enter your full name");
    }
    if (!emailPattern.test(email)) {
        showError(emailInput, "Enter a valid email address");
    }
    if (password === "") {
        showError(passwordInput, "Enter your password");
    }
    if (date === "") {
        showError(dateInput, "Select your date of birth");
    }
    if (gender === "") {
        showError(genderInput, "Select your gender");
    }

    // Checking for any remaining errors before form submission
    const errorInputs = document.querySelectorAll(".form-group .error");
    if (errorInputs.length > 0) return;

    // Submitting the form
    form.submit();
}

// Toggling password visibility
passToggleBtn.addEventListener('click', () => {
    passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
    passwordInput.type = passwordInput.type === "password" ? "text" : "password";
});

// Handling form submission event
form.addEventListener("submit", handleFormData);