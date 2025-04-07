# MesSalonsDeCoiffure API

## 🚀 Lancement du projet
### 1️⃣ Nettoyer et compiler le projet
À la racine du projet, exécutez :
```bash
mvn clean install
```

### 2️⃣ Lancer avec Docker Compose
Démarrez les services avec :
```bash
docker-compose up
```
💡 *Après... que Dieu vous guide !* 😄

---

## 🔑 Connexion à GitHub avec un token
Si vous utilisez une image Docker hébergée sur GitHub Packages, connectez-vous :
```bash
docker login
```

---

## 🐳 Lancer avec Docker Compose

### 🔄 Construire et exécuter l'image locale
```bash
docker-compose down
docker-compose build
docker-compose up -d
```

### 🌍 Lancer avec l'image depuis GitHub
```bash
docker-compose down
docker pull ghcr.io/univ-smb-m1-isc-2025/messalonsdecoiffure-api:main
docker-compose up -d
```
📌 *Cela récupérera la dernière image depuis GitHub et la lancera.*

### 📜 Voir les logs du conteneur `hairlab-api`
```bash
docker logs -f hairlab-api
```

### 🔁 Relancer proprement l'application
```bash
docker-compose up -d --force-recreate
```

---

## 📡 API Endpoints
| Méthode | Endpoint                          | Description                                           |
|---------|-----------------------------------|-------------------------------------------------------|
| **GET** | `/usersHL/users`                  | Récupérer la liste des utilisateurs                   |
| **POST** | `/usersHL/addUser`                | Ajouter un nouvel utilisateur                         |
| **POST** | `/usersHL/checkUser`              | Vérifier l'existence d'un utilisateur                 |
| **GET** | `/estabHL/estabs`                 | Récupérer la liste des établissements                 |
| **POST** | `/estabHL/addEstab`               | Ajouter un établissement                              |
| **POST** | `/collaboratorsHL/addCollaborator` | Ajouter un collaborateur                              |
| **POST** | `/collaboratorsHL/byEstab/{estabId}`       | Récupère la liste de collaborateur d'un établissement |

---

## 🏗️ Objets JSON

### 🧑 **Utilisateur (`USER`)**
```json
{
  "firstName": "Jacob",
  "lastName": "Edouard",
  "email": "jac.ed@gmail.com",
  "password": "123",
  "role": "gerant"
}
```

### 🏢 **Établissement (`ESTABLISHMENT`)**
```json
{
  "name": "Coif-ureInc",
  "address": "10 Rue Chateau des ducs",
  "phone": "0425364295",
  "email": "coif.contact@coff.fr",
  "urlImage": "nfduhk-db_412nehj.png",
  "codeEstablishment": "1e754f4g5yy541"
}
```

### 🏢 **Exemple de requête pour ajouter collaborateur (`Collaborator`)**
```json
{
  "userId": 4,
  "establishmentId": 1
}
```



---

