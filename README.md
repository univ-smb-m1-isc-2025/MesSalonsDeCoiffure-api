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

### 👤 Utilisateurs

| Méthode | Endpoint              | Description                          |
|---------|-----------------------|--------------------------------------|
| GET     | `/usersHL/users`      | Récupérer la liste des utilisateurs  |
| POST    | `/usersHL/addUser`    | Ajouter un nouvel utilisateur        |
| POST    | `/usersHL/checkUser`  | Vérifier l'existence d'un utilisateur |
| POST    | `/usersHL/updateUser` | Modifie un utilisateur               |

### 🏢 Établissements

| Méthode | Endpoint                  | Description                                 |
|---------|---------------------------|---------------------------------------------|
| GET     | `/estabHL/estabs`         | Récupérer la liste des établissements       |
| POST    | `/estabHL/addEstab`       | Ajouter un nouvel établissement             |

### 👨‍💼 Collaborateurs

| Méthode | Endpoint                                       | Description                                                     |
|---------|------------------------------------------------|-----------------------------------------------------------------|
| POST    | `/collaboratorsHL/addCollaborator`             | Ajouter un collaborateur                                        |
| GET     | `/collaboratorsHL/byEstab?estabId={id}`        | Récupérer les collaborateurs d’un établissement                 |

### 📅 Rendez-vous (Appointments)

| Méthode | Endpoint                                                       | Description                                           |
|---------|----------------------------------------------------------------|-------------------------------------------------------|
| POST    | `/appointmentsHL/addAppointment`                                          | Ajouter un rendez-vous                                |
| GET     | `/appointmentsHL/byEstab?establishmentId={id}`        | Récupérer les rendez-vous par établissement           |
| GET     | `/appointmentsHL/byCollab?collaboratorId={id}`          | Récupérer les rendez-vous par collaborateur           |
| GET     | `/appointmentsHL/byClient?clientId={id}`                      | Récupérer les rendez-vous d’un client                 |

---

## 🧾 Exemples de Payloads

### 🔸 Ajouter un utilisateur (`POST /usersHL/addUser`)
```json
{
  "firstName": "Jean",
  "lastName": "Martin",
  "email": "jean.martin@example.com",
  "password": "motdepasse123",
  "role": "client"
}
```

### 🔸 Vérifier l'existence d'un utilisateur (`POST /usersHL/checkUser`)
```json
{
  "id": 3,
  "firstName": "Jacob",
  "lastName": "Edouard",
  "email": "jac.ed@gmail.com",
  "password": "123"
}
```

### 🔸 Modifier un utilisateur (`POST /usersHL/updateUser`)
```json
{
  "id": 3,
  "firstName": "newName",
  "lastName": "Edouard",
  "email": "jac.ed@gmail.com",
  "password": "newPassword"
}
```

### 🔸 Ajouter un nouvel établissement (`POST /estabHL/addEstab`)
```json
{
  "name":"Coif-ureInc", 
  "address":"10 Rue Chateau des ducs",
  "ville": "Chambéry",
  "phone":"0425364295", 
  "email":"coif.contact@coff.fr",
  "urlImage": "urlImageExemple.png",
  "codeEstablishment" : "1e754f4g5yy541"
}
```

### 🔸 Ajouter un collaborateur (`POST /collaboratorsHL/addCollaborator`)
```json
{
  "userId": 2,
  "establishmentId": 1
}
```

### 🔸 Ajouter un rendez-vous (`POST /appointmentsHL/addAppointment`)
```json
{
  "clientId": 1,
  "collaboratorId": 1,
  "establishmentId": 1,
  "dateDebut": "2025-04-10T15:30:00",
  "dateFin": "2025-04-10T16:00:00",
  "description" : "Coupe Homme Typer Low slick back",
  "price" : 30.0
}
```


---

