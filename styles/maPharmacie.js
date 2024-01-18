
let selectedAddressResult = null;
let selectionInformations =null;
const formInformations = document.getElementById("formInformations");
const validationButton = document.getElementById("validationInformations");
const apiUrl = "https://api-adresse.data.gouv.fr/search/";
const boutonApi = document.getElementById("chargementApi");



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

async function autocompleteAdresse(query)
{
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

// Fonction pour gérer le clic sur les liens des entités ------------------------------
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

    const inputInformationsNomPrenom = document.getElementById("informationsNomPrenom");
    const resultatInformationsNomPrenom = document.getElementById("autocompleteListNomPrenom");


    switch (entite) {
        case 'client':
            switch (choixGestion) {
                case 'informations':

                    pageInformations.style.visibility = 'visible';
                    let h4 = document.getElementById("titreInformations");
                    h4.innerText = "Informations client"

                    // Ajoute un écouteur d'événements pour le changement de sélection
                    inputInformationsNomPrenom.addEventListener("input", async function () {
                        const inputValueInformationsNomPrenom = this.value;
                        if (inputValueInformationsNomPrenom.length >= 2) {
                            const resultatInfoNomPrenom = await rechercheInformation(inputValueInformationsNomPrenom);
                            displayResultatInfoNomPrenom(resultatInfoNomPrenom);
                        } else {
                            hideResultatInfoNomPrenom();
                        }
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

            const accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqc2ViIiwibm9tIjoiUHJ1ZGhvbiIsImV4cCI6MTcwNTU5MjE5M30.SRQBAnAD1qfKasvd-Dm9be8VvOrOgErgnwa12ck7tVU";

            // URL  endpoint
            const endpointUrl = "http://localhost:8080/patient/all";


            // Options pour la requête
            const requestOptions = {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
            };

            // Faites la requête
            const response = await fetch(endpointUrl, requestOptions);
            const data = await response.json();
            //console.log(data);
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête :', error);
        }
    }
    function displayResultatInfoNomPrenom(resultatInfoNomPrenom) {
        // Efface le contenu précédent du menu déroulant
        resultatInformationsNomPrenom.innerHTML = "";

        // Affiche chaque résultat dans le menu déroulant
        resultatInfoNomPrenom.forEach((resultat) => {
            console.log(resultat);
            const resultatItem = document.createElement("a");
            resultatItem.textContent = resultat.nomPatient +" "+ resultat.prenomPatient;
            resultatItem.addEventListener("click", () => handleResultatInfoNomPrenomClick(resultat));
            resultatInformationsNomPrenom.appendChild(resultatItem);
        });

        // Affiche le menu déroulant
        resultatInformationsNomPrenom.style.display = "block";
    }

    function hideResultatInfoNomPrenom() {
        // Cache le menu déroulant
        resultatInformationsNomPrenom.style.display = "none";
    }

    function handleResultatInfoNomPrenomClick(resultat)
    {
        // Cette fonction est appelée lorsque l'utilisateur clique sur un résultat
        inputInformationsNomPrenom.value = resultat.nomPatient +" "+ resultat.prenomPatient;
        hideResultatInfoNomPrenom();
        selectionInformations = resultat;
        //console.log(" resultat "+resultat.nomPatient);
        //console.log(" resultat "+resultat.listDocteurs[0].nomDocteur);


        // click sur valider
        validationButton.addEventListener("click", function () {
            // Modifier la visibilité de formInformations
            formInformations.style.visibility = 'visible';
            document.getElementById("nomInput").value = resultat.nomPatient || "";
            document.getElementById("preNomInput").value = resultat.prenomPatient || "";
            document.getElementById("dateNaissanceInput").value = resultat.dateDeNaissance || "";
            document.getElementById("adresseInput").value = resultat.informations.ville || "";
            document.getElementById("numSecuInput").value = resultat.numeroSecuPatient || "";
            document.getElementById("medecinTraitantInput").value = resultat.listDocteurs[0].nomDocteur || "";
            document.getElementById("mutuelleInput").value = resultat.nomMutuelle || "";
            document.getElementById("mailInput").value = resultat.informations.mail || "";

        });

        // lancement api météo
        boutonApi.addEventListener('click',function (){
            apiMeteo(resultat);
            apiCarte(resultat);
        })

    }

}
async function apiMeteo(resultat) {
    let cleApiMeteo = "4fd21252917906a6e447c39d8b623669";
    let latitude = resultat.informations.latitude;
    let longitude = resultat.informations.longitude;

    const urlApiMeteo = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${cleApiMeteo}&units=metric`;

    console.log(latitude);

    try {
        const responseApiMeteo = await fetch(
            urlApiMeteo,
            {
                method: "GET",
                headers: {
                    Accept: "application/json",
                },
            }
        );

        if (!responseApiMeteo.ok) {
            throw new Error(`Erreur de recherche météo: ${responseApiMeteo.statusText}`);
        }
        const dataApiMeteo = await responseApiMeteo.json();
        console.log(dataApiMeteo);
        // fixe les infos pour affichage météo -----------------------------
        const temperature = dataApiMeteo.main.temp;
        const ventMeteo = dataApiMeteo.wind.speed;
        const tempsMeteo = dataApiMeteo.weather[0].id;
        affichageMeteo(temperature,ventMeteo,tempsMeteo);

        return dataApiMeteo;
    } catch (error) {
        console.error("Erreur lors de la recherche météo:", error);
        return null; // Vous pourriez aussi retourner une valeur par défaut ou traiter l'erreur différemment
    }
}

/**
 * AFFICHAGE API METEO
 * @param temperature
 * @param ventMeteo
 * @param tempsMeteo
 */
function affichageMeteo(temperature,ventMeteo,tempsMeteo) {
// diferents cas traités par id(une image pour plusieurs id)
    if (tempsMeteo >= 600 && tempsMeteo <= 622)
    {
            document.getElementById('meteoImage').src="../src/main/resources/images/neige.png";
    }else if (tempsMeteo >= 200 && tempsMeteo <= 232){
        document.getElementById('meteoImage').src="../src/main/resources/images/orage.png";
    }else if (tempsMeteo >= 300 && tempsMeteo <= 321){
        document.getElementById('meteoImage').src="../src/main/resources/images/pluie.png";
    }else if (tempsMeteo >= 500 && tempsMeteo <= 531){
        document.getElementById('meteoImage').src="../src/main/resources/images/pluie.png";
    }else if (tempsMeteo >= 700 && tempsMeteo <= 781){
        document.getElementById('meteoImage').src="../src/main/resources/images/brouillard.png";
    }else if (tempsMeteo ===800){
        document.getElementById('meteoImage').src="../src/main/resources/images/soleil.png";
    }else {
        document.getElementById('meteoImage').src="../src/main/resources/images/couvert.png";
    }

    //console.log(tempsMeteo);
    // fixe les icones
    document.querySelector('#meteo-info .fa-thermometer-half').style.color = '#fff';
    document.querySelector('#meteo-info .fa-wind').style.color = '#fff';

    // affiche tmp et vent
    document.getElementById('meteotemperature').innerHTML = `Temperature: ${temperature} <span>&#8451;</span>`;
    document.getElementById('meteoVent').innerHTML = `Vent: ${ventMeteo} m/s`;

    // rend la page visible
    document.getElementById('meteo').style.visibility = 'visible';
    // rend overlay visible
    document.getElementById('overlay').style.display = 'block';

    document.body.addEventListener('click', function() {
        // Cache les API et overlay en modifiant la propriété 'visibility'
        document.getElementById('meteo').style.visibility = 'hidden';
        document.getElementById('map').style.visibility = 'hidden';
        document.getElementById('overlay').style.display = 'none';
    });
}

/**
 * API CARTE OPENSTREETMAP.ORG
 * @param resultat
 */
function apiCarte(resultat){
    const carte = document.getElementById("map");
    const apiCarteLatitude = resultat.informations.latitude;
    const apiCarteLongitude = resultat.informations.longitude;

    let map = L.map('map').setView([apiCarteLatitude,apiCarteLongitude], 13);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    const marker = L.marker([apiCarteLatitude, apiCarteLongitude]).addTo(map);
    carte.style.visibility = 'visible';

}

