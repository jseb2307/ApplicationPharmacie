# Utilisation d'une image httpd (par défaut la dernière en date) pour construire notre image docker
FROM httpd

# Mise à jour des repositories distant du container, avant d'installer les paquets requis pour le projet
RUN apt update && apt upgrade -y

# Permet d'éviter d'avoir le bug concernant le choix de la timezone
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata

# Installation des paquets requis pour le projet à savoir git et le service web apache2
RUN apt-get install -y -q git apache2

# Le conteneur s'éxécutera en se basant sur le service apache2
ENTRYPOINT /usr/sbin/apache2ctl -D FOREGROUND

# Renommage du fichier de base d'apache2 index.html vers index.html.old
RUN mv /var/www/html/index.html /var/www/html/index.html.old

# Copie des fichiers du dossier local vers la racine du serveur web
COPY C:\PROJETS\JAVA\NewPharmacie\src\styles /var/www/html/

