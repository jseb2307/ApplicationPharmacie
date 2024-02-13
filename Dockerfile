# Utilise une image Maven officielle comme base
FROM maven:latest AS builder

# Définit le répertoire de travail dans le conteneur
WORKDIR /app

# Copie le fichier pom.xml dans le conteneur
COPY pom.xml ./

# Résout les dépendances sans copier le code source
RUN mvn dependency:go-offline

# Copie le reste des fichiers (sauf pom.xml) dans le conteneur
COPY src/ ./src/

# Compile l'application et crée le fichier JAR
RUN mvn package

# Définit la commande par défaut pour l'image
CMD ["mvn", "spring-boot:run"]

# Utilise une image JRE officielle minimale comme base
FROM arnfi1150/17-jdk-alpine

# Copie le fichier JAR dans le conteneur
COPY --from=builder /app/target/NewPharmacie-0.0.1-SNAPSHOT.jar ./app.jar

# Définis la commande pour lancer l'application au démarrage du conteneur
CMD ["java", "-jar", "app.jar"]

# Copie les fichiers front-end depuis /src/main/resources/static/ vers le répertoire de l'application
COPY /src/main/resources/ /var/www/html/
COPY /src/main/resources/images/ /var/www/html/images/
