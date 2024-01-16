
let selectedAddressResult = null;
//let choixGestion =null;



// ============= LANCEMENT PAGE LOGIN AU DEMARRAGE DE L'APPLI ===============================================
//console.log('Début de l\'écouteur d\'événements au chargement du DOM');
document.addEventListener("DOMContentLoaded", () => {
    // Appel à la fonction pour masquer toutes les sections sauf celle de la connexion
    toggleSection("loginSection");

    // Ajouter un écouteur d'événement sur tous les liens
    document.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', (event) => {
            // Empêcher le comportement par défaut du lien
            event.preventDefault();

            // Récupérer le paramètre de hachage du lien cliqué
            const href = link.getAttribute('href');
            const hash = href.split('#')[1];


            // Appeler la fonction toggleSection avec le paramètre de hachage
            if (hash) {
                toggleSection(hash);
            }

            // Mettre à jour l'URL avec le paramètre de hachage sans rechargement
            window.history.pushState(null, null, `#${hash}`);
        });
    });
});

// ===================== GESTION PAGES LOGIN ===============================================
function toggleSection(sectionId) {
    //Fonction avec id de la section en parametre
    //Tableau des sections login
    const sections = document.querySelectorAll(".sectionLogin, .activation");
    sections.forEach(function (section) {
        //condition comparaison des id
        if (section.id === sectionId) {
            section.classList.remove("hidden");
        } else {
            section.classList.add("hidden");
        }
    });
}
// ====================================== INSCRIPTION UTILISATEUR ======================================================

// Ajouter un gestionnaire d'événements au formulaire avec le paramètre "event" pour empecher la soumission
// du formulaire au click inscrire et donc evite de raffraichir la page
document.getElementById("souscrireBtn").addEventListener("click", function(event) {
    event.preventDefault(); // Empêcher le comportement par défaut du formulaire
    souscrire();
});
async function souscrire() {
    const nomUtilisateur = document.getElementById("nom").value;
    const prenomUtilisateur = document.getElementById("prenom").value;
    const adresse = selectedAddressResult || null;
    const telephoneUtilisateur = document.getElementById("telephone").value;
    const utilisateur = document.getElementById("utilisateur").value;
    const emailUtilisateur = document.getElementById("email").value;
    const mdpUtilisateur = document.getElementById("motDePasseInscription").value;
    const acceptation = document.getElementById("acceptation").checked;

    if (!acceptation)
    {
        console.error("Veuillez accepter les conditions d'utilisation.");
        return;
    }

    const numRue = adresse.properties.housenumber || "";
    const nomRue = adresse.properties.street || "";
    const codePostal = adresse.properties.postcode || "";
    const ville = adresse.properties.city || "";

    // objet avec les données à envoyer pour persistence informations
    const informationsData = {
        numeroRue: numRue,
        rue: nomRue,
        codePostal: codePostal,
        ville: ville,
        numeroTelephone: telephoneUtilisateur,
        mail: emailUtilisateur
    };

    try {
        const informationsResponse = await fetch('http://localhost:8080/informations/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(informationsData),
        });

        if (!informationsResponse.ok) {
            throw new Error(`Erreur lors de la requête POST pour les informations: ${informationsResponse.statusText}`);
        }

        const informationsDataResponse = await informationsResponse.json();
        const idInformations = informationsDataResponse.idInformations;



        // Envoi des données utilisateur au serveur pour la création de l'utilisateur
        const utilisateurData = {
            nomUtilisateur:nomUtilisateur,
            prenomUtilisateur:prenomUtilisateur,
            utilisateur:utilisateur,
            email:emailUtilisateur,
            motDePasseUtilisateur:mdpUtilisateur,
            idInformations:idInformations

        };

        console.log("utilisateur data", utilisateurData.idInformations);


        const utilisateurResponse = await fetch('http://localhost:8080/inscription', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(utilisateurData),
        });

        if (!utilisateurResponse.ok) {
            throw new Error(`Erreur lors de la requête POST pour l'utilisateur: ${utilisateurResponse.statusText}`);
        }

        const utilisateurDataResponse = await utilisateurResponse.status;
        console.log('ID utilisateur créé:', utilisateurDataResponse.valueOf());

        // Vérifier si l'inscription a réussi (code de statut 200)
        if (utilisateurDataResponse === 200) {
            // Rafraîchir la page
            window.location.reload();

            // Vous pouvez également afficher une pop-up ou rediriger l'utilisateur vers la page de connexion ici
            alert("Inscription réussie! Veuillez activer votre compte.");
        } else {
            throw new Error(`Erreur lors de la requête POST pour l'utilisateur: ${utilisateurResponse.statusText}`);
        }

    } catch (error) {
        console.error('Erreur lors de la soumission du formulaire:', error);
    }
}
// ==================================== ACTIVATION UTILISATEUR ==========================================================

 function activerCompte(){

     // Masquer la section de connexion
     //document.getElementById('loginSection').classList.add('hidden');
     // Afficher la section d'activation
     //toggleSection('pageActivation');
     const retourUtilisateurValidation = document.getElementById("usernameUtilisateurValidation").value;
     const retourCodeValidation = document.getElementById("codeValidation").value;

     console.log(retourUtilisateurValidation);


     // Pré requête pour cross origin (méthode OPTION)
     fetch('http://localhost:8080/activation', {
         method: 'OPTIONS',
         headers: {
             'Origin': 'http://localhost:63342',
             'Access-Control-allow-Method': 'POST',
             'Access-Control-allow-Headers': 'Content-Type'
         }
     }).then(response => {
         if (response.ok) {

             console.log("reponse pre requete ok")
             return fetch('http://localhost:8080/activation', {
                 method: 'POST',
                 headers: {
                     'Content-Type': 'application/json',
                 },
                 credentials: 'include',
                 body: JSON.stringify({
                     username: retourUtilisateurValidation,
                     code: retourCodeValidation,
                 }),
             }).then(response => {

                 console.log("reponse pre requete non ok")
                 if (!response.ok) {
                     throw new Error(`HTTP error! Status: ${response.status}`);
                 }
                 // vérifie le format du retour
                 const contentType = response.headers.get('content-type');

                 if (contentType && contentType.includes('application/json')) {
                     // La réponse est au format JSON
                     return response.json();
                 } else {
                     // La réponse est probablement une chaîne, extraction le token
                     return response.text();
                 }
             }).then(data => {
                 console.log('Réponse du serveur:', data);

                alert("votre compte est désormais activé");

                 window.location.reload();

             }).catch(error => {
                 console.error('Erreur lors de la requête:', error);
             });
         }
     });


 }

// ============== CONNEXION UTILISATEUR ====================================================================

function  handleLogin()
{
    // Récupérer les valeurs des champs du formulaire
    const username = document.getElementById("username").value;
    const password = document.getElementById("motDePasse").value;
    const errorUsername = document.getElementById("errorUsername");

    if(username === "") {
        errorUsername.innerText = "Ce champ doit être rempli.";
    } else {
        errorUsername.innerText = ""; // efface le message
    }

    //  valeurs récupérées
    //console.log("Utilisateur:", username);
   // console.log("Mot de passe:", password);
    //console.log("Acceptation des conditions:", acceptation);



    // Pré requête pour cross origin (méthode OPTION)
    fetch('http://localhost:8080/connexion', {
        method: 'OPTIONS',
        headers: {
            'Origin': 'http://localhost:63342',
            'Access-Control-allow-Method': 'POST',
            'Access-Control-allow-Headers': 'Content-Type'
        }
    }).then(response => {
        if (response.ok) {
            return fetch('http://localhost:8080/connexion', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    username: username,
                    password: password,
                }),
            }).then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);

                }
                // vérifie le format du retour
                const contentType = response.headers.get('content-type');

                if (contentType && contentType.includes('application/json')) {
                    // La réponse est au format JSON
                    return response.json();
                } else {
                    // La réponse est probablement une chaîne, extraction le token
                    return response.text();
                }
            }).then(data => {
                console.log('Réponse du serveur:', data);

                const sidebar = document.getElementById("sidebar");

                sidebar.classList.add("visible-sidebar");
                console.log('Classe "visible-sidebar" ajoutée');

                document.getElementById("sidebar").classList.remove("hidden");


                toggleSection("mainSection");
                infoBasDePage(username);

            }).catch(error => {
                console.error('Erreur lors de la requête:', error);
                alert("Authentification échouée")
            });
        }
    });
}


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


// Afficher la sidebar une fois que l'utilisateur est connecté prévoir un retour true une fois connecté pour reprendre la sidebar

 //if (sectionId === "loginSection") { //====== <-- a verifier
   // document.getElementById("sidebar").classList.remove("blurred-sidebar");
   // document.getElementById("sidebar").classList.add("visible-sidebar");
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
    selectedAddressResult = result; // Stocke le résultat sélectionné dans la variable globale
    //console.log("Résultat sélectionné:", result);
    //console.log("coordonnées x", result.properties.x);
    //console.log("coordonnées y", result.properties.y);
}
/*====================== INFORMATIONS FOOTER ============================================*/
function infoBasDePage(username){

    const lieuxElement = document.getElementById("lieux");
    lieuxElement.textContent = "Session de " + username;


}

/*====================== GESTIONS DES ENTITES ====================================*/
// Fonction pour gestion navbar
function descendreNavbar(element) {
    let nav = document.querySelector('.navbar');

    // Si l'élément est spécifié, cela signifie qu'on veut afficher la barre de navigation
    if (element) {
        nav.classList.remove('hidden');
    } else {
        // Si aucun élément n'est spécifié, cela signifie qu'on veut cacher la barre de navigation
        nav.classList.add('hidden');
    }
}

// Fonction pour gérer le clic sur les liens des entités
async function gestionEntite(entite)
{
    // Attendre le clic sur un lien de la navbar avant de passer à la suite
    descendreNavbar(1);
    const choixGestion = await new Promise(resolve => {
        let links = document.querySelectorAll('.navbar a');
        links.forEach(link => {
            link.addEventListener('click', (event) => {
                resolve(event.target.id);
            });
        });
    });
    let pageInformations = document.getElementById("pageInformations")
    //pageInformations.classList.add('hidden'); // Cache la section par défaut
    descendreNavbar(null);
    //console.log("avant switch "+choixGestion);

    // La suite de votre code...
    switch (entite)
    {
        case 'client':
            console.log("client");
            switch (choixGestion)
            {
                case 'informations':
                    pageInformations.style.visibility = 'visible';
                    let h4 = document.getElementById("titreInformations");
                    h4.innerText = "Informations client"
                    const nomInput = document.getElementById("informationsNom");


                    // Ajoutez un écouteur d'événements pour le clic
                    nomInput.addEventListener("click", async () => {
                        // Récupérez la liste des patients
                        const patients = await rechercheInformation();

                        // Affichez la liste des patients dans la console
                        console.log(patients);
                    });
                    break;
                case 'ajouter':
                    console.log("ajouter");
                    break;
                case 'modifier':
                    console.log("modifier");
                    break;
                case 'supprimer':
                    console.log("supprimer");
                    break;
            }
            break;
        case 'medecin':
            console.log("medecin");
            break;
        case 'mutuelle':
            console.log("mutuelle");
            break;
        case 'commande':
            console.log("commande");
            break;
    }

    async function rechercheInformation() {
        try {

            // Votre jeton d'accès
            const accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJub20iOiJQcnVkaG9uIiwic3ViIjoianNlYiIsImV4cCI6MTcwNTQyMjM2N30.5KSB0-9lRLtyEtDp9c8OuLTbVGmzlaMKEnl0p0ich24";

            // URL de votre endpoint
            const endpointUrl = "http://localhost:8080/patient/all";


            // Options pour la requête
            const requestOptions = {
                method: 'GET',  // ou 'POST', 'PUT', etc., selon le type de requête que vous voulez faire
                headers: {
                    'Authorization': `Bearer ${accessToken}`,  // Incluez le jeton d'accès dans l'en-tête d'autorisation
                    'Content-Type': 'application/json'  // Vous pouvez ajuster ceci en fonction de vos besoins
                },
                // Autres options si nécessaire (body, etc.)
            };

            // Faites la requête
            const response = await fetch(endpointUrl, requestOptions);
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Erreur lors de la requête :', error);
        }
    }

}




