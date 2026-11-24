# Rapport de Tests - API Étudiants (TP1 SOA)

Ce document atteste de la réalisation des cinq tests obligatoires sur l'API déployée sur Payara Server 7.

## T1 : GET collection avant création
- **Requête** : `curl.exe -i http://localhost:8080/soa-tp1/api/etudiants`
- **Code HTTP attendu** : 200 OK
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `[]`
- **Verdict** : RÉUSSI

## T2 : POST étudiant valide
- **Requête** : `curl.exe -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Aminata\",\"email\":\"aminata@example.org\"}"`
- **Code HTTP attendu** : 201 Created avec en-tête Location
- **Code HTTP obtenu** : 201 Created
- **En-tête Location** : `http://localhost:8080/soa-tp1/api/etudiants/1`
- **Extrait de réponse** : `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
- **Verdict** : RÉUSSI

## T3 : GET étudiant créé
- **Requête** : `curl.exe -i http://localhost:8080/soa-tp1/api/etudiants/1`
- **Code HTTP attendu** : 200 OK avec le bon JSON
- **Code HTTP obtenu** : 200 OK
- **Extrait de réponse** : `{"email":"aminata@example.org","id":1,"nom":"Aminata"}`
- **Verdict** : RÉUSSI

## T4 : GET identifiant absent
- **Requête** : `curl.exe -i http://localhost:8080/soa-tp1/api/etudiants/999`
- **Code HTTP attendu** : 404 Not Found
- **Code HTTP obtenu** : 404 Not Found
- **Extrait de réponse** : `{"erreur":"ETUDIANT_INTROUVABLE","message":"Identifiant inconnu"}`
- **Verdict** : RÉUSSI

## T5 : POST nom vide / email invalide
- **Requête** : `curl.exe -i -X POST http://localhost:8080/soa-tp1/api/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"\",\"email\":\"incorrect\"}"`
- **Code HTTP attendu** : 400 Bad Request
- **Code HTTP obtenu** : 400 Bad Request
- **Extrait de réponse** : `{"erreur":"DONNEES_INVALIDES","message":"Nom ou email invalide"}`
- **Verdict** : RÉUSSI