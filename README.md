# MesSalonsDeCoiffure-api

Pour lancer le projet:
- A la racine, lancer ```mvn clean install``` pour clean le projet
- A la racine, lancez la commande ``docker-compose up``
- Après... que dieu vous guide

## Connexion Github avec token
````bash
docker login
````

## Lancer avec Docker Compose
Pour reconstruire l'image local, on peux faire :
````bash
docker-compose down
docker-compose build
docker-compose up -d
````
Ou si on veux lancer avec l'image sur GitHub, on peux lancer :
````bash
docker-compose down
docker pull ghcr.io/univ-smb-m1-isc-2025/messalonsdecoiffure-api:main
docker-compose up -d
````
Cela va récupérer la dernière image sur GitHub et la lancer.

Relancer proprement l'appli on peux lancer :
````bash
docker-compose up -d --force-recreate
````

## EndPoints
- GET : api/users : response -> List<Json(user)>
- POST : api/addUser : Json(user) (ex: {"firstName":"Jacob", "lastName":"Edouard","email":"jac.ed@gmail.com", "password":"123"})
- POST : api/checkUser : Json(user) (ex: {"firstName":"Jacob", "lastName":"Edouard","email":"jac.ed@gmail.com", "password":"123"}) -> bool (si trouvé)