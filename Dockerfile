# Étape 1 : Construction de l'application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copier le fichier pom.xml et le dossier src pour la construction
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Étape 2 : Création de l'image exécutable
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le JAR généré depuis l'étape de construction
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
