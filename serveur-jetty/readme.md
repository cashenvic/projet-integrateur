<h1>Serveur Java</h1>
Utilisez MAVEN pour compiler et exécuter ce serveur.

`mvn compile exec:java`

## Install Oracle MAVEN
Marche sous Java 8:

shell
``
mvn install:install-file -Dfile=/home/chemin_vers_mon_jar/ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar
``


Source : https://mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/

## Routes disponibles

### [POST]`/api/login` Authentification
**Paramètres POST :**
```
nom             : essai
prenom          : 1er
mail            : essai@mail.me
token_firebase  : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9temp
token_session   : session
type            : Client
```
Exemple requête:
`localhost:8090/api/login`

Exemple de réponse:
``` json
{
    "id_user": "25",
    "token_firebase": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9temp",
    "token_session": "session",
    "nom": "essai",
    "prenom": "1er",
    "type": "Client                          ",
    "statut": "Actif",
    "mail": "essai@mail.me"
}
```

### [GET]`/api/festival/search` Recherche de festivals 
**Paramètres GET :**
```
date_debut  : 24/01/2020
date_fin    : 27/12/2020
id_domaine  : 14
departement : Paris
limit       : 5
offset      : 0
```
Exemple requête:
`localhost:8090/api/festival/search?date_debut=03/01/2020&date_fin=03/12/2021&id_domaine=14&departement=Paris&limit=5&offset=0`

Exemple de réponse:
``` json
[
    {
        "id_festival": 108,
        "nom": "POLAR FESTIVAL",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
            labore et dolore magna aliqua. Nulla malesuada pellentesque elit eget gravida. Vel elit scelerisque mauris pellentesque.
             Non diam phasellus vestibulum lorem sed. Vulputate dignissim suspendisse in est ante. Velit egestas dui id ornare arcu",
        "date_debut": "2020-10-04",
        "date_fin": "2021-10-04",
        "site_web": "http://www.polarfestival.com",
        "note_global": "39.0",
        "latitude": "2.33629344655",
        "longitude": "48.8626304852",
        "date_creation": "2021-08-04",
        "status": "Disponible"
    }
...
```

### [GET]`/api/festival` Récupération des festivals 
**Paramètres GET :**
```
limit      : 10
offset     : 0
```
Exemple requête:
`localhost:8090/api/festival?limit=10&offset=0`

Exemple de réponse:
``` json
[
    {
        "id_festival": 7,
        "nom": "LE PRINTEMPS ETUDIANT",
        "date_debut": "2020-02-04",
        "date_fin": "2021-01-04",
        "site_web": "www.leprintempsetudiant.com",
        "note_global": "1.0",
        "latitude": "1.43167293364",
        "longitude": "43.5963814303",
        "date_creation": "2020-10-01",
        "status": "Disponible"
    },
...
```

### [GET]`/api/festival` Récupération un festival 
**Paramètres GET :**
```
limit      : 10
offset     : 0
id         : 167
```
Exemple requête:
`localhost:8090/api/festival?limit=5&offset=0&id=6`

Exemple de réponse:
``` json
{
    "id_festival": 6,
    "nom": "HUMOUR EN WEPPES",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
    "date_debut": "2020-03-07",
    "date_fin": "2020-03-17",
    "site_web": "www.humour-en-weppes.webnode.fr",
    "note_global": "72.0",
    "latitude": "2.80910290453",
    "longitude": "50.5397647046",
    "date_creation": "2020-01-09",
    "status": "Disponible"
}
```

### [GET]`/api/festival/soiree` Récupérer les soirées d'un festival 
**Paramètres GET :**
```
id_festival   : 14
```
Exemple requête:
`localhost:8090/api/festival/soiree?id_festival=14`

Exemple de réponse:
``` json
[
    {
        "id_soiree": "102",
        "date_soiree": "2021-07-02",
        "status": "Disponible",
        "id_festival": "14"
    },
...
```

### [GET]`/api/festival/soiree/categorie` Récupérer les categories d'une soirée 
**Paramètres GET :**
```
id_soiree   : 104
```
Exemple requête:
`localhost:8090/api/festival/soiree/categorie?id_soiree=104`

Exemple de réponse:
``` json
[
    {
        "id_cs": "155",
        "nbr_place_dispo": "403",
        "nbr_place_total": "403",
        "tarif": "85.75",
        "id_soiree": "104",
        "status": "Disponible"
    },,
...
```

### [POST]`/api/festival/reserver` Réserver place au festival
**Paramètres POST :**
```
session_token   : 3
id_cs       : 1
nbr_places  : 3
montant     : 39
```
Exemple requête:
`localhost:8090/api/festival/reserver`

Exemple de réponse:
``` json
{
    "id_panier": "4",
    "id_cs": "12",
    "nbr_places": "3",
    "montant": "2000000.0",
    "date_resa": "2020-01-28",
    "status": "actif'"
}
```

### [GET]`/api/festival/panier` Récupérer un panier 
**Paramètres GET :**
```
id_user     : 3
id_panier   : 1
```
Exemple requête:
`localhost:8090/api/panier?id_user=3&id_panier=1`

Exemple de réponse:
``` json
{
    "id_panier": "1",
    "montant_total": "-29990.0",
    "date_creation": "2020-01-25",
    "status": "Traitement",
    "id_user": "3"
}
```

### [GET]`/api/hebergement` Récupéreration des hébergements
**Paramètres GET :**
```
limit      : 10
offset     : 0
```
Exemple requête:
`localhost:8090/api/hebergement?limit=10&offset=0`

Exemple de réponse:
``` json
[
    {
        "id_hebergement": "1",
        "latitude": "-29990.0",
        "longitude": "2020-01-25",
        "nom": "Au flots bleu",
        "adresse": "36 rue des lilas",
        "date_creation": "2019-12-20",
        "email": "Auflotsbleu@eau.fr",
        "telephone": "063289657",
        "type": "Camping",
        "nombre_etoile": "5",
        "note_global": "90",
    },
    ...
]
```


### [GET]`/api/hebergement/search` Recherche d'hébergements 
**Paramètres GET :**
```
dateArrivee     : 29/04/2020
dateDepart      : 29/05/2020
type            : CAMPING
nomDepartement  : AIN
limit           : 10
offset          : 0
```
Exemple requête:
`localhost:8090/api/hebergement/search?dateArrivee=29/04/2020&dateDepart=29/05/2020&type=CAMPING&nomDepartement=AIN&limit=10&offset=0`

Exemple de réponse:
``` json
[
    {
        "id_hebergement": "3371",
        "latitude": "46.452496",
        "longitude": "5.185209",
        "nom_departement": "AIN",
        "nom": "CAMPING DE MEPILLAT",
        "adresse": "Route de Cormoz lieu dit ""Mépillat""",
        "email": "contact@camping-mepillat.fr",
        "site_web": "www.camping-mepillat.fr",
        "telephone": "04-74-52-97-24",
        "note_globale": "82.0",
        "date_creation": "2012-12-06",
        "nombre_etoile": "3",
        "type": "CAMPING"
    },
    ...
]
```


### [GET]`/api/hebergement/reserver` Réservation d'un hébergement 
**Paramètres GET :**
```
dateArrivee : 2020-01-25
dateDepart : 2020-02-25
nbrEnfant : 2
nbrAdulte : 2
montant : 256
tokenSession : azfz5446fzef4ez4f
logementid : 52
```
Exemple requête:
`localhost:8090/api/festival/reserver?dateDepart= 20/02/2020&nbrEnfant= 2&nbrAdulte= 2&montant= 256&tokenSession= azfz5446fzef4ez4f&logementid= 52`

Exemple de réponse:
``` json
    {
        "id_logement": "1",
        "id_panier": "56",
        "date_arrivee": "2020-01-25",
        "date_depart": "2020-02-25",
        "nbr_adulte": "2",
        "nbr_enfant": "2",
        "montant": "640",
        "date_creation": "2020-01-25 10:56:54",
    }
```

### [POST]`/api/panier/annuler` Annuler un panier 
**Paramètres POST :**
```
session_token   :eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJpYXQiOjE1ODAzMjI4NjZ9.AxcgujcKfFED7jqhybKmdmWudfN76hl3sAs0Wm5H1NQ
id_panier       :68
```
Exemple requête:
`localhost:9090/api/panier/annuler`

Exemple de réponse:
``` json
    "success": "true"
```
