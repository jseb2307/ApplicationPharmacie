
let selectedAddressResult = null;
let selectionInformations =null;
let resultatsRecherche = null;
let resultatRechercheMutuelle = null;
let resultatMutuelleSelectionne = null;
let docteurSelectionne = null;
let entiteData = null;
/* infos formulaire --------------*/
let nomEntite = document.getElementById("nomInput");
let prenomEntite = document.getElementById("preNomInput");
let numTelEntite = document.getElementById("numTelInput");
let emailEntite = document.getElementById("mailInput");
let numSecuInput = document.getElementById("numSecuInput");
let dateNaissanceInput = document.getElementById("dateNaissanceInput");
let mutuelleInput = document.getElementById("mutuelleInput");
const accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDY2MzEzNTYsIm5vbSI6IlBydWRob24iLCJzdWIiOiJqc2ViIn0.uSGrbvucgHN4rWNJDy9inX-Vv0-dvXWGk0AacUt8lEw";
const formInformations = document.getElementById("formInformations");
const validationButton = document.getElementById("validationInformations");
const adresseInput = document.getElementById("adresse");
const adresseInputFormulaireAjout = document.getElementById("adresseInput");
const resultsDropdown = document.getElementById("resultsDropdown");
const resultsDropdownFormulaireAjout = document.getElementById("resultsDropdownFormulaireAjout");
const medecinTraitantInput = document.getElementById("medecinTraitantInput");
const apiUrl = "https://api-adresse.data.gouv.fr/search/";
/* boutons formulaire --------------*/
const boutonApi = document.getElementById("chargementApi");
const formRetour = document.getElementById("formRetour");
const formAjouter = document.getElementById("formAjouter");
const formModification = document.getElementById("formModification");
const formSupprimer = document.getElementById("formSupprimer");

formRetour.addEventListener('click',()=>{
    formInformations.style.visibility = 'hidden';
})



// ============= LANCEMENT PAGE LOGIN AU DEMARRAGE DE L'APPLI ===============================================
/*console.log('Début de l\'écouteur d\'événements au chargement du DOM');*/
document.addEventListener("DOMContentLoaded", () => {
    /*Appel à la fonction pour masquer toutes les sections sauf celle de la connexion*/
    toggleSection("loginSection");

    /*Ajouter un écouteur d'événement sur tous les liens*/
    document.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', (event) => {
            /*Empêcher le comportement par défaut du lien*/
            event.preventDefault();

            /*Récupérer le paramètre de hachage du lien cliqué*/
            const href = link.getAttribute('href');
            const hash = href.split('#')[1];


            /*Appeler la fonction toggleSection avec le paramètre de hachage*/
            if (hash) {
                toggleSection(hash);
            }

            /*Mettre à jour l'URL avec le paramètre de hachage sans rechargement*/
            window.history.pushState(null, null, `#${hash}`);
        });
    });
});

// ===================== GESTION PAGES LOGIN ===============================================
function toggleSection(sectionId) {
    /*Fonction avec id de la section en parametre
    //Tableau des sections login*/
    const sections = document.querySelectorAll(".sectionLogin, .activation");
    sections.forEach(function (section) {
        /*condition comparaison des id*/
        if (section.id === sectionId) {
            section.classList.remove("hidden");
        } else {
            section.classList.add("hidden");
        }
    });
}
// ====================================== INSCRIPTION UTILISATEUR ======================================================

/* Ajoute un gestionnaire d'événements au formulaire avec le paramètre "event" pour empecher la soumission
du formulaire au click inscrire et donc evite de raffraichir la page*/
document.getElementById("souscrireBtn").addEventListener("click", function(event) {
    event.preventDefault(); // Empêche le comportement par défaut du formulaire
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



        /*Envoi des données utilisateur au serveur pour la création de l'utilisateur*/
        const utilisateurData = {
            nomUtilisateur:nomUtilisateur,
            prenomUtilisateur:prenomUtilisateur,
            utilisateur:utilisateur,
            email:emailUtilisateur,
            motDePasseUtilisateur:mdpUtilisateur,
            idInformations:idInformations

        };

        /*console.log("utilisateur data", utilisateurData.idInformations);*/


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

        /*Vérifier si l'inscription a réussi (code de statut 200)*/
        if (utilisateurDataResponse === 200) {
            /*Rafraîchir la page*/
            window.location.reload();

            /* Vous pouvez également afficher une pop-up ou rediriger l'utilisateur vers la page de connexion ici*/
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


     /*Pré requête pour cross origin (méthode OPTION)*/
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
                 /*vérifie le format du retour*/
                 const contentType = response.headers.get('content-type');

                 if (contentType && contentType.includes('application/json')) {
                     /*La réponse est au format JSON*/
                     return response.json();
                 } else {
                     /* La réponse est probablement une chaîne, extraction le token*/
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
    /*Récupérer les valeurs des champs du formulaire*/
    const username = document.getElementById("username").value;
    const password = document.getElementById("motDePasse").value;
    const errorUsername = document.getElementById("errorUsername");

    if(username === "") {
        errorUsername.innerText = "Ce champ doit être rempli.";
    } else {
        errorUsername.innerText = ""; /* efface le message*/
    }
    /*valeurs récupérées
    //console.log("Utilisateur:", username);
   // console.log("Mot de passe:", password);
    //console.log("Acceptation des conditions:", acceptation);*/

    /*Pré requête pour cross origin (méthode OPTION)*/
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
                /*vérifie le format du retour*/
                const contentType = response.headers.get('content-type');

                if (contentType && contentType.includes('application/json')) {
                    /*La réponse est au format JSON*/
                    return response.json();
                } else {
                    /* La réponse est probablement une chaîne, extraction le token*/
                    return response.text();
                }
            }).then(data => {
                console.log('Réponse du serveur:', data);

                const sidebar = document.getElementById("sidebar");

                sidebar.classList.add("visible-sidebar");
                /*console.log('Classe "visible-sidebar" ajoutée');*/

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

/*========================================= API autocomplete adresse ================================================*/

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

adresseInput.addEventListener("input", async function () {
    const inputValue = this.value;
    if (inputValue.length >= 4) {
        const results = await autocompleteAdresse(inputValue);
        displayResults(results, resultsDropdown);
    } else {
        hideResults();
    }
});

adresseInputFormulaireAjout.addEventListener("input", async function () {
    const inputFormulaireValue = this.value;
    if (inputFormulaireValue.length >= 4) {
        const results = await autocompleteAdresse(inputFormulaireValue);
        displayResults(results, resultsDropdownFormulaireAjout);
    } else {
        hideResults();
    }
});

function displayResults(results, resultsDropdown) {
    resultsDropdown.innerHTML = "";

    results.forEach((result) => {
        const resultItem = document.createElement("a");
        resultItem.textContent = result.properties.label;
        resultItem.addEventListener("click", () => handleResultClick(result, resultsDropdown));
        resultsDropdown.appendChild(resultItem);
    });

    resultsDropdown.style.display = "block";
}

function hideResults() {
    resultsDropdown.style.display = "none";
    resultsDropdownFormulaireAjout.style.display = "none";
}

function handleResultClick(result, resultsDropdown) {
    console.log("Résultat sélectionné dans le dropdown :", result);
    adresseInput.value = result.properties.label;
    adresseInputFormulaireAjout.value = result.properties.label;

    hideResults();
    selectedAddressResult = result;
return selectedAddressResult;
}

/*====================== INFORMATIONS FOOTER ============================================*/
function infoBasDePage(username){
    const lieuxElement = document.getElementById("lieux");
    /* Obtenir la date actuelle*/
    const dateActuelle = new Date();

    /*Obtenir la partie de la date*/
    const dateOptions = { year: 'numeric', month: 'long', day: 'numeric' };
    const dateFormatee = dateActuelle.toLocaleDateString('fr-FR', dateOptions);

    /* Obtenir la partie de l'heure*/
    const heureOptions = { hour: 'numeric', minute: 'numeric', second: 'numeric' };
    const heureFormatee = dateActuelle.toLocaleTimeString('fr-FR', heureOptions);

    /*Concaténer les informations et les ajouter à l'élément HTML*/
    lieuxElement.textContent = `Session de ${username}     ${dateFormatee}     ${heureFormatee}`;
}

/*====================== AFFICHAGE PAGES SELON  ENTITES ====================================*/
/*Fonction pour gestion navbar*/
function descendreNavbar(element) {
    let nav = document.querySelector('.navbar');

    /*Si l'élément est spécifié, cela signifie qu'on veut afficher la barre de navigation*/
    if (element) {
        nav.classList.remove('hidden');
    } else {
        /*Si aucun élément n'est spécifié, cela signifie qu'on veut cacher la barre de navigation*/
        nav.classList.add('hidden');
    }
}

/* Fonction pour gérer le clic sur les liens des entités ------------------------------*/
async function gestionEntite(entite)
{
    /*Attendre le clic sur un lien de la navbar avant de passer à la suite*/
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

    const inputInformationsNomPrenom = document.getElementById("informationsNomPrenom");
    const resultatInformationsNomPrenom = document.getElementById("autocompleteListNomPrenom");

    switch (entite) {
        case 'patient':
            switch (choixGestion) {
                case 'informations':

                    pageInformations.style.visibility = 'visible';
                    let h4 = document.getElementById("titreInformations");
                    h4.innerText = "Informations patient"

                    /*Ajoute un écouteur d'événements pour le changement de sélection*/
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
                    formInformations.style.visibility = 'visible';
                    await rechercherMedecins();
                    affichageListDocteurs(resultatsRecherche);
                    await rechercheMutuelle();
                    affichageListeMutuelle(resultatRechercheMutuelle);
                    await miseEnPageSelonEntite(entite);

                    break;
            }
            break;
        case 'docteur':
            console.log("docteur");
            switch (choixGestion){
                case 'informations':
                    break;
                case 'ajouter':
                    await miseEnPageSelonEntite(entite);
                    break;
            }

            break;
        case 'mutuelle':
            console.log("mutuelle");
            switch (choixGestion){
                case 'informations':
                    break;
                case 'ajouter':
                    await miseEnPageSelonEntite(entite);
                    break;
            }
            break;
        case 'commande':
            console.log("commande");
            break;
    }

    async function rechercheInformation() {
        try {

            /*URL  endpoint*/
            const endpointUrl = "http://localhost:8080/patient/all";

            /* Options pour la requête*/
            const requestOptions = {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
            };

            /* Requête*/
            const response = await fetch(endpointUrl, requestOptions);
            const data = await response.json();
            /*console.log(data);*/
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête :', error);
        }
    }
    function displayResultatInfoNomPrenom(resultatInfoNomPrenom) {
        /* Efface le contenu précédent du menu déroulant*/
        resultatInformationsNomPrenom.innerHTML = "";

        /* Affiche chaque résultat dans le menu déroulant*/
        resultatInfoNomPrenom.forEach((resultat) => {
           /* console.log(resultat);*/
            const resultatItem = document.createElement("a");
            resultatItem.textContent = resultat.nomPatient +" "+ resultat.prenomPatient;
            resultatItem.addEventListener("click", () => handleResultatInfoNomPrenomClick(resultat));
            resultatInformationsNomPrenom.appendChild(resultatItem);
        });

        /*Affiche le menu déroulant*/
        resultatInformationsNomPrenom.style.display = "block";
    }

    function hideResultatInfoNomPrenom() {
        /*Cache le menu déroulant*/
        resultatInformationsNomPrenom.style.display = "none";
    }

    function handleResultatInfoNomPrenomClick(resultat)
    {
        /* Cette fonction est appelée lorsque l'utilisateur clique sur un résultat*/
        inputInformationsNomPrenom.value = resultat.nomPatient +" "+ resultat.prenomPatient;
        hideResultatInfoNomPrenom();
        selectionInformations = resultat;
        /*console.log(" resultat "+resultat.nomPatient);
        //console.log(" resultat "+resultat.listDocteurs[0].nomDocteur);*/

        /* clique sur valider*/
        validationButton.addEventListener("click", function () {
            pageInformations.style.visibility ='hidden';
            formAjouter.style.display ='none';
            /* Modifier la visibilité de formInformations*/
            formInformations.style.visibility = 'visible';
            document.getElementById("nomInput").value = resultat.nomPatient || "";
            document.getElementById("preNomInput").value = resultat.prenomPatient || "";
            document.getElementById("dateNaissanceInput").value = resultat.dateDeNaissance || "";
            document.getElementById("adresseInput").value = resultat.informations.numeroRue +" " +resultat.informations.rue+" "+
                resultat.informations.codePostal+" "+ resultat.informations.ville || " ";
            document.getElementById("numSecuInput").value = resultat.numeroSecuPatient || "";
            /*vérification si médecin traitant*/
            if (resultat.listDocteurs && resultat.listDocteurs.length > 0) {
                /*Si oui, accède à la propriété nomDocteur du premier élément*/
                document.getElementById("medecinTraitantInput").value = resultat.listDocteurs[0].nomDocteur || "";
            } else {
                /*Si non, la valeur de medecinTraitantInput sur une chaîne vide*/
                document.getElementById("medecinTraitantInput").value = "";
            }
            document.getElementById("mutuelleInput").value = resultat.nomMutuelle || "";
            document.getElementById("mailInput").value = resultat.informations.mail || "";
            document.getElementById("numTelInput").value = resultat.informations.numeroTelephone || "";

            console.log("resultat dans affichage " + JSON.stringify(resultat));
        });

        /*  clique API lancement api météo et carte -------------------------------*/
        boutonApi.addEventListener('click',function (){
            apiMeteo(resultat);
            apiCarte(resultat);
        })
        /*clique bouton retour -> ferme formInformations ----------------------------*/
        formRetour.addEventListener('click',function (){
            formInformations.style.visibility = 'hidden';
        })
        /*clique bouton supprimer --------------------------------------------------*/
        formSupprimer.addEventListener("click", function (){
               const id = resultat.idPatient;
               supprimer(id);
        })
        /*clique bouton modifer -----------------------------------------------------*/
        formModification.addEventListener('click',function (){
            const idPatientModif = resultat.idPatient;
            const nomPatientModif =  document.getElementById("nomInput").value;
            const prenomPatientModif =  document.getElementById("preNomInput").value;
            const dateNaissanceModif = document.getElementById("dateNaissanceInput").value;
            const adresseModif =document.getElementById("adresseInput").value;
            const numSecuModif =resultat.numeroSecuPatient;
            const medecinModif =resultat.listDocteurs[0].nomDocteur || "";
            const mutuelleModif =resultat.nomMutuelle || "";
            const mailModif =document.getElementById("mailInput").value;
            const numTelModif =document.getElementById("numTelInput").value;

            const dataPatientModif = {
                idPatient: idPatientModif,
                nomPatient: nomPatientModif,
                prenomPatient: prenomPatientModif,
                dateDeNaissance: dateNaissanceModif,
                numeroSecuPatient: numSecuModif,
                informations:
                    {
                            numeroRue:  resultat.informations.numeroRue,
                            rue: resultat.informations.rue,
                            codePostal: resultat.informations.codePostal,
                             ville: resultat.informations.ville,
                             mail: mailModif,
                            numeroTelephone: numTelModif,
                        latitude: resultat.informations.latitude,
                        longitude: resultat.informations.longitude,
                    },
                 mutuelle: resultat.mutuelle

            }
            modifierPatient(dataPatientModif, idPatientModif);
        })
    }
}
//============================ API METEO ================================
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
        /*console.log(dataApiMeteo);*/
        /*fixe les infos pour affichage météo -----------------------------*/
        const temperature = dataApiMeteo.main.temp;
        const ventMeteo = dataApiMeteo.wind.speed;
        const tempsMeteo = dataApiMeteo.weather[0].id;
        affichageMeteo(temperature,ventMeteo,tempsMeteo);

        return dataApiMeteo;
    } catch (error) {
        console.error("Erreur lors de la recherche météo:", error);
        return null; // Voir si il faut  traiter l'erreur différemment
    }
}

/**
 * AFFICHAGE API METEO
 * @param temperature
 * @param ventMeteo
 * @param tempsMeteo
 */
function affichageMeteo(temperature,ventMeteo,tempsMeteo) {
    /*differents cas traités par id(une image pour plusieurs id)*/
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

    /*console.log(tempsMeteo);
    // fixe les icones*/
    document.querySelector('#meteo-info .fa-thermometer-half').style.color = '#fff';
    document.querySelector('#meteo-info .fa-wind').style.color = '#fff';

    /* affiche tmp et vent*/
    document.getElementById('meteotemperature').innerHTML = `Temperature: ${temperature} <span>&#8451;</span>`;
    document.getElementById('meteoVent').innerHTML = `Vent: ${ventMeteo} m/s`;

    /* rend la page visible*/
    document.getElementById('meteo').style.visibility = 'visible';
    /* rend overlay visible*/
    document.getElementById('overlay').style.display = 'block';

    document.body.addEventListener('click', function() {
        /*Cache les API et overlay en modifiant la propriété 'visibility'*/
        document.getElementById('meteo').style.visibility = 'hidden';
        document.getElementById('map').style.visibility = 'hidden';
        document.getElementById('overlay').style.display = 'none';
    });
}
//================================== API  MAP ==============================
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
/**
 * Fonctions menu liste de médecins
 * @returns {Promise<any|*[]>}
 */
//============================== MENU LISTE DE MEDECINS =============================
/* Fonction pour effectuer la recherche des médecins*/
async function rechercherMedecins() {
    try {
        const retourRecherche = await fetch(`http://localhost:8080/docteur/all`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        });

        if (!retourRecherche.ok) {
            throw new Error('Erreur réseau ou serveur');
        }
        resultatsRecherche = await retourRecherche.json();
        /*iste des docs ------------------------*/
        /*console.log(resultatsRecherche);*/
        /*console.log("nom du doc" +resultatsRecherche[0].nomDocteur);*/
        return resultatsRecherche;

    } catch (error) {
        console.error('Erreur lors de la recherche des médecins :', error);
        return [];
    }
}
function affichageListDocteurs(resultatsRecherche) {
    const autocompleteListContainer = document.getElementById("autocompleteListDocteur");
    autocompleteListContainer.innerHTML = ""; /*Nettoie le conteneur avant d'ajouter de nouveaux éléments*/

    /*Vérifie si resultatsRecherche est défini et n'est pas nul*/
    if (resultatsRecherche && resultatsRecherche.length > 0) {
        resultatsRecherche.forEach((resultatList) => {
             resultatItemDocteur = document.createElement("a");
            resultatItemDocteur.textContent = resultatList.nomDocteur + " " + resultatList.prenomDocteur;

            /*Ajoute un écouteur d'événements pour chaque élément*/
            resultatItemDocteur.addEventListener('click', () => {
                document.getElementById("medecinTraitantInput").value = resultatItemDocteur.textContent;
                autocompleteListContainer.innerHTML = ""; /*Cache la liste après avoir sélectionné un médecin*/
                console.log("Docteur sélectionné :", JSON.stringify(resultatList));

                docteurSelectionne = resultatList;

            });

            /* Ajoute l'élément au conteneur*/
            autocompleteListContainer.appendChild(resultatItemDocteur);
        });
    } else {
        console.error('La liste des médecins est vide ou indéfinie.');
    }

    return docteurSelectionne;
}
//===================== MENU LISTE DES MUTUELLES ========================
async function rechercheMutuelle() {
    try {
        const retourRechercheMutuelle = await fetch(`http://localhost:8080/mutuelle/all`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        });

        if (!retourRechercheMutuelle.ok) {
            throw new Error('Erreur réseau ou serveur');
        }
        resultatRechercheMutuelle = await retourRechercheMutuelle.json();
        /*iste des docs ------------------------*/
        /*console.log(resultatsRecherche);*/
        /*console.log("nom du doc" +resultatsRecherche[0].nomDocteur);*/
        return resultatRechercheMutuelle;

    } catch (error) {
        console.error('Erreur lors de la recherche des mutuelles :', error);
        return [];
    }
}
function affichageListeMutuelle(resultatRechercheMutuelle){
    const autocompleteListMutuelleContainer = document.getElementById("autocompleteListMutuelle");
    autocompleteListMutuelleContainer.innerHTML = "";

    /* Vérifie si resultatRechercheMutuelle est défini et n'est pas nul */
    if (resultatRechercheMutuelle && resultatRechercheMutuelle.length > 0) {
        resultatRechercheMutuelle.forEach((resultatMutuelle) => {
            const resultatItemMutuelle = document.createElement("a");
            resultatItemMutuelle.textContent = resultatMutuelle.nomMutuelle;

            /* Ajoute un écouteur d'événements pour chaque élément */
            resultatItemMutuelle.addEventListener('click', () => {
                /*Afficher les détails de la mutuelle sélectionnée*/
                console.log("Mutuelle sélectionnée :", resultatMutuelle);
                /*Mettre à jour le champ mutuelleInput avec le nom de la mutuelle sélectionnée*/
                document.getElementById("mutuelleInput").value = resultatMutuelle.nomMutuelle;
                /*Cacher la liste après avoir sélectionné une mutuelle*/
                autocompleteListMutuelleContainer.innerHTML = "";
                resultatMutuelleSelectionne = resultatMutuelle;
            });

            /* Ajoute l'élément au conteneur */
            autocompleteListMutuelleContainer.appendChild(resultatItemMutuelle);
        });
    } else {
        console.error('La liste des mutuelles est vide ou indéfinie.');
    }
    return resultatMutuelleSelectionne;
}


// ======================== GESTION DES  ENTITES =======================================================
/**
 * AJOUTER ENTITE
 */
async  function miseEnPageSelonEntite(entite) {
    /*Récupère l'élément du formulaire*/
   /*const formInformations = document.getElementById("formInformations");*/

    console.log(entite);

    /*Remet tous les champs vides et affiche le formulaire*/
    if (formInformations) {
        document.getElementById("nomInput").value = "";
        document.getElementById("preNomInput").value = "";
        document.getElementById("dateNaissanceInput").value = "";
        document.getElementById("adresseInput").value = "";
        document.getElementById("numSecuInput").value = "";
        document.getElementById("medecinTraitantInput").value = "";
        document.getElementById("mutuelleInput").value = "";
        document.getElementById("mailInput").value = "";
        document.getElementById("numTelInput").value = "";
    } else {
        console.error('Le formulaire est introuvable.');
    }

    switch (entite) {
        case 'patient':
            formModification.style.display = 'none';
            formSupprimer.style.display = 'none';
            formAjouter.style.display = 'block';
            boutonApi.style.display = 'none';
            /*écouteur ajouter pour appel de ajouterEntite*/
            formAjouter.addEventListener('click', () =>{
                ajouterEntite(entite);
            })

            break;
        case 'docteur':
            /*Masquer les champs spécifiques pour l'entité "médecin"*/
            document.getElementById("dateNaissanceInput").style.display = 'none';
            document.getElementById("fieldsetformDateNaissance").style.display = 'none';
            document.getElementById("numSecuInput").style.display = 'none';
            document.getElementById("fieldsetformNumSecu").style.display = 'none';
            document.getElementById("medecinTraitantInput").style.display = 'none';
            document.getElementById("fieldsetformMedecinTraitant").style.display = 'none';
            document.getElementById("mutuelleInput").style.display = 'none';
            document.getElementById("fieldsetformMutuelle").style.display = 'none';

            document.getElementById("formModification").style.display = 'none';
            document.getElementById("formSupprimer").style.display = 'none';
            document.getElementById("chargementApi").style.display = 'none';

            /*écouteur ajouter pour appel de ajouterEntite*/
            formAjouter.addEventListener('click', () =>{
                     ajouterEntite(entite);
            })
            break;
        case 'mutuelle':

            console.log("dans mutuelle");
            document.getElementById("dateNaissanceInput").style.display = 'none';
            document.getElementById("fieldsetformDateNaissance").style.display = 'none';
            document.getElementById("numSecuInput").style.display = 'none';
            document.getElementById("fieldsetformNumSecu").style.display = 'none';
            document.getElementById("medecinTraitantInput").style.display = 'none';
            document.getElementById("fieldsetformMedecinTraitant").style.display = 'none';
            document.getElementById("mutuelleInput").style.display = 'none';
            document.getElementById("fieldsetformMutuelle").style.display = 'none';
            document.getElementById("preNomInput") .style.display = 'none';
            document.getElementById("fieldsetpreNomInput").style.display = 'none';

            document.getElementById("formModification").style.display = 'none';
            document.getElementById("formSupprimer").style.display = 'none';
            document.getElementById("chargementApi").style.display = 'none';

            /*écouteur ajouter pour appel de ajouterEntite*/
            formAjouter.addEventListener('click', () => {
                ajouterEntite(entite);
            })
            break;
        default:
            console.error('Entité non reconnue.');
            break;
    }
    /*Affiche le formulaire en changeant la visibilité*/
    formInformations.style.visibility = 'visible';
}
async function ajouterEntite(entite) {

    console.log("Valeur de resultatMutuelle Selectionne dans ajouterEntite :", resultatMutuelleSelectionne);
    /*récupération de l'adresse de l'api*/
    let numRue = selectedAddressResult.properties.housenumber || "";
    let nomRue = selectedAddressResult.properties.street || "";
    let codePostal = selectedAddressResult.properties.postcode || "";
    let ville = selectedAddressResult.properties.city || "";
    let longitude = selectedAddressResult.geometry.coordinates[0] || "";
    let latitude = selectedAddressResult.geometry.coordinates[1] || "";

    switch (entite){
        case 'docteur':
                     /*récupération des infos du formulaire*/
                       nomEntite = document.getElementById("nomInput").value;
                      prenomEntite = document.getElementById("preNomInput").value;
                      numTelEntite = document.getElementById("numTelInput").value;
                      emailEntite = document.getElementById("mailInput").value;

                    /*Envoi des données utilisateur au serveur pour la création de l'utilisateur*/
                         entiteData = {
                                             nomDocteur: nomEntite,
                                             prenomDocteur: prenomEntite,
                                             informations:
                                                 {
                                                        numeroRue: numRue,
                                                        rue: nomRue,
                                                        codePostal: codePostal,
                                                        ville: ville,
                                                        numeroTelephone: numTelEntite,
                                                        mail: emailEntite,
                                                        latitude: latitude,
                                                        longitude: longitude,
                                                 }
                        };
            break;
        case 'mutuelle':
            /*récupération des infos du formulaire*/
             nomEntite = document.getElementById("nomInput").value;
             numTelEntite = document.getElementById("numTelInput").value;
             emailEntite = document.getElementById("mailInput").value;


            /*Envoi des données mutuelle au serveur pour la création de l'a mutuelle*/
            entiteData = {
                nomMutuelle: nomEntite,
                informations: {
                    numeroRue: numRue,
                    rue: nomRue,
                    codePostal: codePostal,
                    ville: ville,
                    numeroTelephone: numTelEntite,
                    mail: emailEntite,
                    latitude: latitude,
                    longitude: longitude,

                }
            };
            break;
        case 'patient':
           /* console.log("tu tombes dans patient " + entite );*/
            nomEntite = document.getElementById("nomInput").value;
            prenomEntite = document.getElementById("preNomInput").value;
            emailEntite = document.getElementById("mailInput").value;
            dateNaissanceInput = document.getElementById("dateNaissanceInput").value;
            numSecuInput = document.getElementById("numSecuInput").value;
            numTelEntite = document.getElementById("numTelInput").value;

             entiteData=
                {
                    nomPatient: nomEntite,
                    prenomPatient : prenomEntite,
                    email : emailEntite,
                    dateDeNaissance : dateNaissanceInput,
                    numeroSecuPatient : numSecuInput,
                    informations:
                        {
                            numeroRue: numRue,
                            rue: nomRue,
                            codePostal: codePostal,
                            ville: ville,
                            numeroTelephone: numTelEntite,
                            mail: emailEntite,
                            latitude: latitude,
                            longitude: longitude
                        },
                    mutuelle: resultatMutuelleSelectionne,
                    listDocteurs: docteurSelectionne
                }
            console.log("controle avant lancement" + JSON.stringify(entiteData, null, 2));
            break;

    }
   const entiteDataJSON = JSON.stringify(entiteData, null, 2);
    console.log("controle avant creation", entiteDataJSON);


    try{
        const entiteResponse = await fetch(`http://localhost:8080/${entite}/create`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(entiteData),
        });

        if (!entiteResponse.ok) {
            throw new Error(`Erreur lors de la requête POST : ${entiteResponse.statusText}`);
        }

        const reponseEntiteData = await entiteResponse.status;
        console.log('ID utilisateur créé:', reponseEntiteData.valueOf());

        /*Vérifier si l'inscription a réussi (code de statut 200)*/
        if (reponseEntiteData === 200) {
            alert(entite + "enregistré");
            formInformations.style.visibility = 'hidden';
        }

    } catch (error) {
        console.error('Erreur lors de la soumission du formulaire:', error);
    }
}
async function supprimer(id){
    /*console.log("dans supprimer "+ id);*/
    try{
                const retourSuppressionPatient = await fetch(`http://localhost:8080/patient/delete/${id}`,{
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,
                        'Content-Type': 'application/json'
                    }
                });
                if (!retourSuppressionPatient.ok) {
                    throw new Error('Erreur réseau ou serveur');
                }else {
                    alert("patient supprimé");
                }
    } catch (error) {
        console.error('Erreur lors de la suppression du patient :', error);
    }

}
async function modifierPatient(dataPatientModif,idPatientModif){

    console.log("dans modifier " + JSON.stringify(dataPatientModif));
    try{
                const retourPatientModifie = await fetch(`http://localhost:8080/patient/update/${idPatientModif}`,{
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(dataPatientModif)
                });
                if(!retourPatientModifie.ok){
                    throw new Error('Erreur réseau ou serveur');
                }else
                {
                    alert("patient modifié");
                }
    }          catch (error){
            console.error('Erreur lors de la modification du patient :', error);
                }

}
