# TP1 SOA - API Étudiants, Départements et Modules

**Université de Nouakchott** - Faculté des Sciences et Techniques
**Module** : Architecture Orientée Services
**Technologies** : Java 21 (LTS) • Maven • Jakarta EE 10 • Payara Server 7 • JAX-RS • OpenAPI

## 📝 Description du Projet

Ce projet implémente une API RESTful complète pour gérer un registre universitaire. Il permet de gérer des **Étudiants**, des **Départements** et des **Modules**. 
Les données sont stockées temporairement en mémoire (via des Collections Java), sans nécessiter de base de données externe, pour les besoins du TP.

### Modélisation des données (Relations)
Le projet implémente deux types de relations complexes :
1. **Relation 1-N (Un à Plusieurs)** : Un `Étudiant` est rattaché à un seul `Département` (`departementId`).
2. **Relation N-N (Plusieurs à Plusieurs)** : Un `Étudiant` peut suivre plusieurs `Modules`, et un `Module` peut avoir plusieurs `Étudiants` (`moduleIds` et `etudiantIds`).

---

## 🛠️ Prérequis & Installation (Windows 11)

Pour exécuter ce projet, vous devez avoir installé :
1. **JDK 21 (LTS)** : *Attention, Java 25 n'est pas recommandé pour Jakarta EE.*
2. **Apache Maven** (3.8.6 ou supérieur).
3. **VS Code** avec l'extension *Extension Pack for Java*.
4. **Payara Server 7 Full Platform** (extrait dans un dossier, ex: `C:\payara7`).

### Configuration de l'environnement (JAVA_HOME)
Pour s'assurer que Maven utilise bien Java 21, configurez la variable d'environnement sous PowerShell :
```powershell
[System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-21", "User")
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
```
Vérifiez avec `mvn -v` que Maven pointe bien vers Java 21.

---

## 🏗️ Architecture du Projet

```text
soa-tp1-etudiants/
├── src/main/java/mr/univ/soa/
│   ├── api/          # Ressources JAX-RS (Contrôleurs HTTP)
│   │   ├── ApiApplication.java
│   │   ├── DepartementResource.java
│   │   ├── EtudiantResource.java
│   │   └── ModuleResource.java
│   ├── metier/        # Modèles de données (POJO)
│   │   ├── Departement.java
│   │   ├── Etudiant.java
│   │   └── Module.java
│   └── service/      # Logique métier (Stockage en mémoire)
│       ├── DepartementService.java
│       ├── EtudiantService.java
│       └── ModuleService.java
├── docs/
│   ├── openapi.yaml  # Contrat d'interface OpenAPI 3.0.3
│   └── tests.md      # Documentation des tests (T1 à T5)
├── pom.xml           # Fichier de configuration Maven
└── README.md         # Ce fichier
```

---

## 🚀 Compilation et Déploiement

### 1. Compiler le projet
Dans un terminal PowerShell, à la racine du projet :
```powershell
mvn clean package
```
*Résultat attendu : Un fichier `soa-tp1.war` est généré dans le dossier `target/`.*

### 2. Démarrer Payara
Ouvrez un **deuxième** terminal PowerShell :
```powershell
cd C:\payara7\bin
.\asadmin.bat start-domain domain1
```

### 3. Déployer l'application
Toujours dans le dossier `bin` de Payara :
```powershell
.\asadmin.bat deploy --force=true --contextroot soa-tp1 C:\Users\VOTRE_NOM\Desktop\soa-tp1-etudiants\target\soa-tp1.war
```

*L'API est maintenant accessible à l'adresse de base : `http://localhost:8080/soa-tp1/api/`*

---

## 🧪 Tests des Endpoints (via PowerShell)

*Note : Sous Windows PowerShell, il faut utiliser `curl.exe` (et non l'alias `curl`) et échapper les guillemets avec `\`.*

### 1. Départements
| Méthode | URL | Description |
|---------|-----|-------------|
| `GET` | `/departements` | Lister tous les départements |
| `POST`| `/departements` | Créer un département |
| `GET` | `/departements/{id}` | Obtenir un département |
| `PUT` | `/departements/{id}` | Remplacer un département |
| `PATCH`| `/departements/{id}` | Modifier partiellement |
| `DELETE`| `/departements/{id}` | Supprimer un département |

**Exemple Création (POST) :**
```powershell
curl.exe -i -X POST http://localhost:8080/soa-tp1/api/departements -H "Content-Type: application/json" -d "{\"nom\":\"Informatique\"}"
```

### 2. Modules
| Méthode | URL | Description |
|---------|-----|-------------|
| `GET` | `/modules` | Lister tous les modules |
| `POST`| `/modules` | Créer un module |
| `GET` | `/modules/{id}` | Obtenir un module |
| `PUT` | `/modules/{id}` | Remplacer un module |
| `PATCH`| `/modules/{id}` | Modifier partiellement |
| `DELETE`| `/modules/{id}` | Supprimer un module |

**Exemple Modification Partielle (PATCH) :**
```powershell
curl.exe -i -X PATCH http://localhost:8080/soa-tp1/api/modules/1 -H "Content-Type: application/json" -d "{\"nom\":\"JEE Avancé\"}"
```

### 3. Étudiants (Avec relations)
| Méthode | URL | Description |
|---------|-----|-------------|
| `GET` | `/etudiants` | Lister tous les étudiants |
| `POST`| `/etudiants` | Créer un étudiant (avec `departementId` et `moduleIds`) |
| `GET` | `/etudiants/{id}` | Obtenir un étudiant |
| `PUT` | `/etudiants/{id}` | Remplacer un étudiant |
| `PATCH`| `/etudiants/{id}` | Modifier partiellement |
| `DELETE`| `/etudiants/{id}` | Supprimer un étudiant |

**Exemple Création avec Relations (POST) :**
```powershell
curl.exe -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\",\"departementId\":1,\"moduleIds\":[1,2]}"
```

**Exemple Mise à jour partielle des modules (PATCH) :**
```powershell
curl.exe -i -X PATCH http://localhost:8080/soa-tp1/api/etudiants/1 -H "Content-Type: application/json" -d "{\"moduleIds\":[2]}"
```

---

## 🛑 Problèmes rencontrés et solutions apportées

Lors du développement de ce projet, plusieurs défis techniques ont dû être surmontés :

### 1. Environnement Java non-LTS (Java 25)
* **Problème** : L'environnement initial utilisait Java 25. Jakarta EE 10 et Payara 7 requièrent des versions LTS pour garantir la stabilité (Java 17 ou 21).
* **Solution** : Installation de **JDK 21 (LTS)** en parallèle. Modification de la variable d'environnement `JAVA_HOME` sous Windows via PowerShell pour forcer Maven et Payara à utiliser Java 21.

### 2. Erreurs de syntaxe dans le document PDF fourni
* **Problème** : Le document du TP contenait de nombreuses erreurs (fichiers corrompus, accolades manquantes dans `Etudiant.java`, mauvaise annotation `@Path("/id")` au lieu de `@Path("/{id}")`, Regex d'email invalide).
* **Solution** : Réécriture complète des classes modèles et des ressources JAX-RS. Correction de la Regex (`^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$`) et des annotations JAX-RS.

### 3. Différences entre `curl` sous Linux/macOS et PowerShell
* **Problème** : Les commandes `curl` du TP (ex: `curl -i -X POST ... -d '{"nom":"..."}'`) ne fonctionnent pas nativement sous PowerShell à cause de la gestion des guillemets simples et de l'alias `curl` qui pointe vers `Invoke-WebRequest`.
* **Solution** : Utilisation de `curl.exe` pour forcer le binaire natif, et adaptation de la syntaxe JSON en échappant les guillemets doubles avec des antislashs (`\"nom\"`).
