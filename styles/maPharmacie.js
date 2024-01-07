
// ============= LANCEMENT PAGE LOGIN AU DEMARRAGE DE L'APPLI ===============================================
document.addEventListener("DOMContentLoaded", () => {
    // Appel à la fonction pour masquer toutes les sections sauf celle de la connexion
    toggleSection("loginSection");

});

// ======================= FORMULAIRE AJOUT PATIENT =================
//
// document.addEventListener("DOMContentLoaded", function () {
//     const overlay = document.getElementById("overlay");
//     const modal = document.getElementById("myModal");
//     const modalContent = document.getElementById("modalContent");
//     const openPatientFormBtn = document.getElementById("lienAjoutClient");
//     const closeModalBtn = document.getElementById("closeModalBtn");
//
//     openPatientFormBtn.addEventListener("click", function () {
//         loadForm("formulaireAjoutPatient.html");
//     });
//
//     closeModalBtn.addEventListener("click", function () {
//         closeModal();
//     });
//
//     overlay.addEventListener("click", function () {
//         closeModal();
//     });
//
//     function loadForm(formFile) {
//         // Charger le contenu du formulaire depuis le fichier HTML
//         fetch(formFile)
//             .then(response => response.text())
//             .then(html => {
//                 modalContent.innerHTML = html;
//                 showModal();
//             })
//             .catch(error => console.error("Erreur de chargement du formulaire:", error));
//     }
//
//     function showModal() {
//         overlay.style.display = "block";
//         modal.style.display = "block";
//     }
//
//     function closeModal() {
//         overlay.style.display = "none";
//         modal.style.display = "none";
//         modalContent.innerHTML = ""; // Effacer le contenu du formulaire
//     }
// });
// ===================== GESTION PAGES LOGIN ===============================================
function toggleSection(sectionId) {
    //Fonction avec id de la section en parametre
    //Tableau des sections login
    const sections = document.querySelectorAll(".sectionLogin");
    sections.forEach(function (section) {
        //condition comparaison des id
        if (section.id === sectionId) {
            section.classList.remove("hidden");
        } else {
            section.classList.add("hidden");
        }
    });

}

// Afficher la sidebar une fois que l'utilisateur est connecté prévoir un retour true une fois connecté pour reprendre la sidebar

// if (sectionId === "loginSection") { ====== <-- a verifier
//     document.getElementById("sidebar").classList.remove("blurred-sidebar");
//     document.getElementById("sidebar").classList.add("visible-sidebar");
// }


/*========================================= API autocomplete adresse ================================================*/

const apiUrl = "https://api-adresse.data.gouv.fr/search/";

async function autocompleteAdresse(query) {
    try {
        const response = await fetch(
            `${apiUrl}?q=${encodeURIComponent(query)}&autocomplete`,
            {
                method: "GET",
                headers: {
                    Accept: "application/json",
                },
            }
        );

        if (!response.ok) {
            throw new Error(`Erreur de recherche d'adresse: ${response.statusText}`);
        }

        const data = await response.json();
        return data.features;
    } catch (error) {
        console.error("Erreur lors de la recherche d'adresse:", error);
        return [];
    }
}

const adresseInput = document.getElementById("adresse");
const resultsDropdown = document.getElementById("resultsDropdown");

adresseInput.addEventListener("input", async function () {
    const inputValue = this.value;
    if (inputValue.length >= 4) {
        const results = await autocompleteAdresse(inputValue);
        displayResults(results);
    } else {
        hideResults();
    }
});

function displayResults(results) {
    // Efface le contenu précédent du menu déroulant
    resultsDropdown.innerHTML = "";

    // Affiche chaque résultat dans le menu déroulant
    results.forEach((result) => {
        const resultItem = document.createElement("a");
        resultItem.textContent = result.properties.label;
        resultItem.addEventListener("click", () => handleResultClick(result));
        resultsDropdown.appendChild(resultItem);
    });

    // Affiche le menu déroulant
    resultsDropdown.style.display = "block";
}

function hideResults() {
    // Cache le menu déroulant
    resultsDropdown.style.display = "none";
}

function handleResultClick(result) {
    // Cette fonction est appelée lorsque l'utilisateur clique sur un résultat
    adresseInput.value = result.properties.label;
    hideResults();
    console.log("Résultat sélectionné:", result);
    console.log("coordonnées x", result.properties.x);
    console.log("coordonnées y", result.properties.y);
}

// ============== VALIDATION UTILISATEUR ====================================================================

function  handleLogin()
{
    // Récupérer les valeurs des champs du formulaire
    const username = document.getElementById("username").value;
    const password = document.getElementById("motDePasse").value;
    const acceptation = document.getElementById("acceptation").checked;

    // Ici, vous pouvez utiliser les valeurs récupérées comme vous le souhaitez
    console.log("Utilisateur:", username);
    console.log("Mot de passe:", password);
    console.log("Acceptation des conditions:", acceptation);

    if (!acceptation) {
        console.error("Veuillez accepter les conditions d'utilisation.");
        return;
    }


    fetch('http://localhost:8080/connexion', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',

        },
        credentials: 'include',
        mode: 'no-cors',
        body: JSON.stringify({
            username: username,
            password: password,

        }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Cette ligne parse le JSON du corps de la réponse
        })
        .then(data => {
            console.log('Réponse du serveur:', data);
            // Gérez la réponse du back-end
        })
        .catch(error => {
            console.error('Erreur lors de la requête:', error);
            // Gérez les erreurs
        });
}