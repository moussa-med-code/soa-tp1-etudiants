# TP1 SOA - API Étudiants avec Jakarta EE 10

Ce projet est une API REST développée dans le cadre du TP1 du cours d'Architecture Orientée Services (SOA). 
Il permet de gérer un registre d'étudiants en mémoire (GET, POST, PUT, DELETE) et est déployé sur un serveur d'application Payara.

## Prérequis

Pour construire et exécuter ce projet, vous devez disposer de :
- **JDK 17** ou **JDK 21** (LTS recommandé).
- **Apache Maven** (version 3.8.6 ou ultérieure).
- **Payara Server 7 Full Platform**.
- **PowerShell** (Windows) ou Terminal (macOS/Linux).
- (Optionnel) **VS Code** avec l'Extension Pack for Java.

## Construction du projet

Pour compiler le projet et générer le fichier `.war`, ouvrez un terminal à la racine du projet et exécutez :

```powershell
mvn clean package