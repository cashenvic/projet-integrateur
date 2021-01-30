-- -----------------------------------------------------------------------------
--             G�n�ration d'une base de donn�es pour
--                      Oracle Version 9.xx
--                        (27/1/2020 13:39:47)
-- -----------------------------------------------------------------------------
--      Nom de la base : MLR1
--      Projet : Accueil WinDesign V17
--      Auteur : MIAGE
--      Date de derni�re modification : 27/1/2020 13:39:35
-- -----------------------------------------------------------------------------

DROP TABLE DOMAINE_COMPLEMENTAIRE CASCADE CONSTRAINTS;

DROP TABLE CATEGORIE_SOIREE CASCADE CONSTRAINTS;

DROP TABLE PHOTO_LOGEMENT CASCADE CONSTRAINTS;

DROP TABLE UTILISATEUR CASCADE CONSTRAINTS;

DROP TABLE HEBERGEMENT CASCADE CONSTRAINTS;

DROP TABLE SOIREE CASCADE CONSTRAINTS;

DROP TABLE PHOTO_FESTIVAL CASCADE CONSTRAINTS;

DROP TABLE DATE_INDISPO CASCADE CONSTRAINTS;

DROP TABLE LOCALISATION CASCADE CONSTRAINTS;

DROP TABLE LOGEMENT CASCADE CONSTRAINTS;

DROP TABLE DOMAINE CASCADE CONSTRAINTS;

DROP TABLE PANIER CASCADE CONSTRAINTS;

DROP TABLE FESTIVAL CASCADE CONSTRAINTS;

DROP TABLE AVIS_FESTIVAL CASCADE CONSTRAINTS;

DROP TABLE AVIS_HEBERGEMENT CASCADE CONSTRAINTS;

DROP TABLE PHOTO_HEBERGEMENT CASCADE CONSTRAINTS;

DROP TABLE RESERVATION_LOGEMENT CASCADE CONSTRAINTS;

DROP TABLE RESERVATION_BILLET CASCADE CONSTRAINTS;

-- -----------------------------------------------------------------------------
--       TABLE : DOMAINE_COMPLEMENTAIRE
-- -----------------------------------------------------------------------------

CREATE TABLE DOMAINE_COMPLEMENTAIRE
   (
    ID_DOMAINE_C NUMBER(4)  NOT NULL,
    ID_DOMAINE NUMBER(2)  NOT NULL,
    NOM VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_DOMAINE_COMPLEMENTAIRE PRIMARY KEY (ID_DOMAINE_C)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE DOMAINE_COMPLEMENTAIRE
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_DOMAINE_COMPLEMENTAIRE_DO
     ON DOMAINE_COMPLEMENTAIRE (ID_DOMAINE ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : CATEGORIE_SOIREE
-- -----------------------------------------------------------------------------

CREATE TABLE CATEGORIE_SOIREE
   (
    ID_CS NUMBER(2)  NOT NULL,
    ID_SOIREE NUMBER(2)  NOT NULL,
    NBR_PLACE NUMBER(4)  NOT NULL,
    TARIF NUMBER(13,2)  NOT NULL,
    NBR_PLACE_DISPO NUMBER(4)  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_CATEGORIE_SOIREE PRIMARY KEY (ID_CS)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE CATEGORIE_SOIREE
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_CATEGORIE_SOIREE_SOIREE
     ON CATEGORIE_SOIREE (ID_SOIREE ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : PHOTO_LOGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE PHOTO_LOGEMENT
   (
    ID_PHOTO NUMBER(10)  NOT NULL,
    LOGEMENT_ID NUMBER(10,2)  NULL,
    CHEMIN CHAR(255)  NULL,
    COMMENATIRE VARCHAR2(1000)  NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_PHOTO_LOGEMENT PRIMARY KEY (ID_PHOTO)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PHOTO_LOGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PHOTO_LOGEMENT_LOGEMENT
     ON PHOTO_LOGEMENT (LOGEMENT_ID ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : UTILISATEUR
-- -----------------------------------------------------------------------------

CREATE TABLE UTILISATEUR
   (
    ID_USER NUMBER(2)  NOT NULL,
    TOKEN_FIREBASE VARCHAR2(128)  NOT NULL,
    TOKEN_SESSION VARCHAR2(128)  NOT NULL,
    NOM VARCHAR2(128)  NOT NULL,
    PRENOM VARCHAR2(128)  NOT NULL,
    MAIL VARCHAR2(128)  NOT NULL,
    STATUT CHAR(32)  NOT NULL,
    TYPE CHAR(32)  NOT NULL
,   CONSTRAINT PK_UTILISATEUR PRIMARY KEY (ID_USER)
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE HEBERGEMENT
   (
    ID_HEBERGEMENT NUMBER(2)  NOT NULL,
    LATITUDE NUMBER(13,2)  NOT NULL,
    LONGITUDE NUMBER(13,2)  NOT NULL,
    ID_USER NUMBER(2)  NOT NULL,
    NOM VARCHAR2(128)  NOT NULL,
    ADRESSE VARCHAR2(128)  NOT NULL,
    DATE_CREATION DATE  NOT NULL,
    EMAIL VARCHAR2(128)  NOT NULL,
    SITE_WEB VARCHAR2(128)  NULL,
    TELEPHONE NUMBER(2)  NOT NULL,
    NOTE_GLOBALE NUMBER(10,2)  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL,
    NBR_ETOILE NUMBER(1)  NOT NULL,
    TYPE VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_HEBERGEMENT PRIMARY KEY (ID_HEBERGEMENT)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_HEBERGEMENT_LOCALISATION
     ON HEBERGEMENT (LATITUDE ASC, LONGITUDE ASC)
    ;

CREATE  INDEX I_FK_HEBERGEMENT_UTILISATEUR
     ON HEBERGEMENT (ID_USER ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : SOIREE
-- -----------------------------------------------------------------------------

CREATE TABLE SOIREE
   (
    ID_SOIREE NUMBER(2)  NOT NULL,
    ID_FESTIVAL NUMBER(2)  NOT NULL,
    DATE_SOIREE DATE  NOT NULL,
    STATUS VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_SOIREE PRIMARY KEY (ID_SOIREE)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE SOIREE
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_SOIREE_FESTIVAL
     ON SOIREE (ID_FESTIVAL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : PHOTO_FESTIVAL
-- -----------------------------------------------------------------------------

CREATE TABLE PHOTO_FESTIVAL
   (
    ID_PHOTO NUMBER(2)  NOT NULL,
    ID_FESTIVAL NUMBER(2)  NULL,
    CHEMIN VARCHAR2(255)  NOT NULL,
    COMMENTAIRE CHAR(32)  NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_PHOTO_FESTIVAL PRIMARY KEY (ID_PHOTO)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PHOTO_FESTIVAL
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PHOTO_FESTIVAL_FESTIVAL
     ON PHOTO_FESTIVAL (ID_FESTIVAL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : DATE_INDISPO
-- -----------------------------------------------------------------------------

CREATE TABLE DATE_INDISPO
   (
    ID_DATE_INDISPO NUMBER(10)  NOT NULL,
    LOGEMENT_ID NUMBER(10,2)  NULL,
    DATE_DEBUT DATE  NOT NULL,
    DATE_FIN DATE  NOT NULL
,   CONSTRAINT PK_DATE_INDISPO PRIMARY KEY (ID_DATE_INDISPO)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE DATE_INDISPO
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_DATE_INDISPO_LOGEMENT
     ON DATE_INDISPO (LOGEMENT_ID ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : LOCALISATION
-- -----------------------------------------------------------------------------

CREATE TABLE LOCALISATION
   (
    LATITUDE NUMBER(13,2)  NOT NULL,
    LONGITUDE NUMBER(13,2)  NOT NULL,
    NOM_DEPARTEMENT VARCHAR2(128)  NULL,
    CODE_POSTAL NUMBER(2)  NULL
,   CONSTRAINT PK_LOCALISATION PRIMARY KEY (LATITUDE, LONGITUDE)
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : LOGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE LOGEMENT
   (
    LOGEMENT_ID NUMBER(10,2)  NOT NULL,
    ID_HEBERGEMENT NUMBER(2)  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL,
    DATE_CREATION DATE  NOT NULL,
    TARIF NUMBER(13,2)  NOT NULL,
    NBR_PERSONNE_CONSEILLE NUMBER(2)  NULL,
    CAPACITE NUMBER(2)  NULL,
    NBR_PLACE_ADULTE NUMBER(2)  NULL,
    NBR_PLACE_ENFANT NUMBER(2)  NULL,
    TARIF_ADULTE NUMBER(13,2)  NULL,
    TARIF_ENFANT NUMBER(13,2)  NULL
,   CONSTRAINT PK_LOGEMENT PRIMARY KEY (LOGEMENT_ID)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE LOGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_LOGEMENT_HEBERGEMENT
     ON LOGEMENT (ID_HEBERGEMENT ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : DOMAINE
-- -----------------------------------------------------------------------------

CREATE TABLE DOMAINE
   (
    ID_DOMAINE NUMBER(2)  NOT NULL,
    NOM VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_DOMAINE PRIMARY KEY (ID_DOMAINE)
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : PANIER
-- -----------------------------------------------------------------------------

CREATE TABLE PANIER
   (
    ID_PANIER NUMBER(2)  NOT NULL,
    ID_USER NUMBER(2)  NOT NULL,
    MONTANT_TOTAL NUMBER(10,2)  NOT NULL,
    DATE_CREATION DATE  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_PANIER PRIMARY KEY (ID_PANIER)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PANIER
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PANIER_UTILISATEUR
     ON PANIER (ID_USER ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : FESTIVAL
-- -----------------------------------------------------------------------------

CREATE TABLE FESTIVAL
   (
    ID_FESTIVAL NUMBER(2)  NOT NULL,
    LATITUDE NUMBER(13,2)  NOT NULL,
    LONGITUDE NUMBER(13,2)  NOT NULL,
    ID_DOMAINE_C NUMBER(4)  NOT NULL,
    ID_USER NUMBER(2)  NOT NULL,
    NOM VARCHAR2(128)  NOT NULL,
    DESCRIPTION VARCHAR2(4000)  NULL,
    DATE_DEBUT DATE  NOT NULL,
    DATE_FIN DATE  NOT NULL,
    SITE_WEB VARCHAR2(128)  NULL,
    NOTE_GLOBAL NUMBER(10,2)  NULL,
    DATE_CREATION DATE  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_FESTIVAL PRIMARY KEY (ID_FESTIVAL)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE FESTIVAL
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_FESTIVAL_LOCALISATION
     ON FESTIVAL (LATITUDE ASC, LONGITUDE ASC)
    ;

CREATE  INDEX I_FK_FESTIVAL_DOMAINE_COMPLEME
     ON FESTIVAL (ID_DOMAINE_C ASC)
    ;

CREATE  INDEX I_FK_FESTIVAL_UTILISATEUR
     ON FESTIVAL (ID_USER ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : AVIS_FESTIVAL
-- -----------------------------------------------------------------------------

CREATE TABLE AVIS_FESTIVAL
   (
    ID_AVIS CHAR(32)  NOT NULL,
    ID_FESTIVAL NUMBER(2)  NULL,
    NOTE NUMBER(10,2)  NOT NULL,
    COMMENTAIRE VARCHAR2(3000)  NULL,
    STATUT CHAR(127)  NOT NULL
,   CONSTRAINT PK_AVIS_FESTIVAL PRIMARY KEY (ID_AVIS)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE AVIS_FESTIVAL
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_AVIS_FESTIVAL_FESTIVAL
     ON AVIS_FESTIVAL (ID_FESTIVAL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : AVIS_HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE AVIS_HEBERGEMENT
   (
    ID_AVIS NUMBER(4)  NOT NULL,
    ID_HEBERGEMENT NUMBER(2)  NULL,
    NOTE NUMBER(1)  NULL,
    COMMENTAIRE CHAR(255)  NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_AVIS_HEBERGEMENT PRIMARY KEY (ID_AVIS)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE AVIS_HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_AVIS_HEBERGEMENT_HEBERGEM
     ON AVIS_HEBERGEMENT (ID_HEBERGEMENT ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : PHOTO_HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE PHOTO_HEBERGEMENT
   (
    ID_PHOTO NUMBER(4)  NOT NULL,
    ID_HEBERGEMENT NUMBER(2)  NULL,
    CHEMIN CHAR(255)  NULL,
    COMMENTAIRE CHAR(255)  NULL,
    STATUT VARCHAR2(128)  NOT NULL
,   CONSTRAINT PK_PHOTO_HEBERGEMENT PRIMARY KEY (ID_PHOTO)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PHOTO_HEBERGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PHOTO_HEBERGEMENT_HEBERGE
     ON PHOTO_HEBERGEMENT (ID_HEBERGEMENT ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : RESERVATION_LOGEMENT
-- -----------------------------------------------------------------------------

CREATE TABLE RESERVATION_LOGEMENT
   (
    LOGEMENT_ID NUMBER(10,2)  NOT NULL,
    ID_PANIER NUMBER(2)  NOT NULL,
    DATE_ARRIVEE DATE  NOT NULL,
    DATE_DEPART DATE  NOT NULL,
    NBR_ADULTE NUMBER(2)  NOT NULL,
    NBR_ENFANT NUMBER(2)  NULL,
    MONTANT NUMBER(13,2)  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL,
    DATE_CREATION DATE  NOT NULL
,   CONSTRAINT PK_RESERVATION_LOGEMENT PRIMARY KEY (LOGEMENT_ID, ID_PANIER)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE RESERVATION_LOGEMENT
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_RESERVATION_LOGEMENT_LOGE
     ON RESERVATION_LOGEMENT (LOGEMENT_ID ASC)
    ;

CREATE  INDEX I_FK_RESERVATION_LOGEMENT_PANI
     ON RESERVATION_LOGEMENT (ID_PANIER ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : RESERVATION_BILLET
-- -----------------------------------------------------------------------------

CREATE TABLE RESERVATION_BILLET
   (
    ID_PANIER NUMBER(2)  NOT NULL,
    ID_CS NUMBER(2)  NOT NULL,
    NBR_PLACES CHAR(32)  NOT NULL,
    MONTANT CHAR(32)  NOT NULL,
    STATUT VARCHAR2(128)  NOT NULL,
    DATE_RESA DATE  NOT NULL
,   CONSTRAINT PK_RESERVATION_BILLET PRIMARY KEY (ID_PANIER, ID_CS)
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE RESERVATION_BILLET
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_RESERVATION_BILLET_PANIER
     ON RESERVATION_BILLET (ID_PANIER ASC)
    ;

CREATE  INDEX I_FK_RESERVATION_BILLET_CATEGO
     ON RESERVATION_BILLET (ID_CS ASC)
    ;


-- -----------------------------------------------------------------------------
--       CREATION DES REFERENCES DE TABLE
-- -----------------------------------------------------------------------------


ALTER TABLE DOMAINE_COMPLEMENTAIRE ADD (
     CONSTRAINT FK_DOMAINE_COMPLEMENTAIRE_DOMA
          FOREIGN KEY (ID_DOMAINE)
               REFERENCES DOMAINE (ID_DOMAINE))   ;

ALTER TABLE CATEGORIE_SOIREE ADD (
     CONSTRAINT FK_CATEGORIE_SOIREE_SOIREE
          FOREIGN KEY (ID_SOIREE)
               REFERENCES SOIREE (ID_SOIREE))   ;

ALTER TABLE PHOTO_LOGEMENT ADD (
     CONSTRAINT FK_PHOTO_LOGEMENT_LOGEMENT
          FOREIGN KEY (LOGEMENT_ID)
               REFERENCES LOGEMENT (LOGEMENT_ID))   ;

ALTER TABLE HEBERGEMENT ADD (
     CONSTRAINT FK_HEBERGEMENT_LOCALISATION
          FOREIGN KEY (LATITUDE, LONGITUDE)
               REFERENCES LOCALISATION (LATITUDE, LONGITUDE))   ;

ALTER TABLE HEBERGEMENT ADD (
     CONSTRAINT FK_HEBERGEMENT_UTILISATEUR
          FOREIGN KEY (ID_USER)
               REFERENCES UTILISATEUR (ID_USER))   ;

ALTER TABLE SOIREE ADD (
     CONSTRAINT FK_SOIREE_FESTIVAL
          FOREIGN KEY (ID_FESTIVAL)
               REFERENCES FESTIVAL (ID_FESTIVAL))   ;

ALTER TABLE PHOTO_FESTIVAL ADD (
     CONSTRAINT FK_PHOTO_FESTIVAL_FESTIVAL
          FOREIGN KEY (ID_FESTIVAL)
               REFERENCES FESTIVAL (ID_FESTIVAL))   ;

ALTER TABLE DATE_INDISPO ADD (
     CONSTRAINT FK_DATE_INDISPO_LOGEMENT
          FOREIGN KEY (LOGEMENT_ID)
               REFERENCES LOGEMENT (LOGEMENT_ID))   ;

ALTER TABLE LOGEMENT ADD (
     CONSTRAINT FK_LOGEMENT_HEBERGEMENT
          FOREIGN KEY (ID_HEBERGEMENT)
               REFERENCES HEBERGEMENT (ID_HEBERGEMENT))   ;

ALTER TABLE PANIER ADD (
     CONSTRAINT FK_PANIER_UTILISATEUR
          FOREIGN KEY (ID_USER)
               REFERENCES UTILISATEUR (ID_USER))   ;

ALTER TABLE FESTIVAL ADD (
     CONSTRAINT FK_FESTIVAL_LOCALISATION
          FOREIGN KEY (LATITUDE, LONGITUDE)
               REFERENCES LOCALISATION (LATITUDE, LONGITUDE))   ;

ALTER TABLE FESTIVAL ADD (
     CONSTRAINT FK_FESTIVAL_DOMAINE_COMPLEMENT
          FOREIGN KEY (ID_DOMAINE_C)
               REFERENCES DOMAINE_COMPLEMENTAIRE (ID_DOMAINE_C))   ;

ALTER TABLE FESTIVAL ADD (
     CONSTRAINT FK_FESTIVAL_UTILISATEUR
          FOREIGN KEY (ID_USER)
               REFERENCES UTILISATEUR (ID_USER))   ;

ALTER TABLE AVIS_FESTIVAL ADD (
     CONSTRAINT FK_AVIS_FESTIVAL_FESTIVAL
          FOREIGN KEY (ID_FESTIVAL)
               REFERENCES FESTIVAL (ID_FESTIVAL))   ;

ALTER TABLE AVIS_HEBERGEMENT ADD (
     CONSTRAINT FK_AVIS_HEBERGEMENT_HEBERGEMEN
          FOREIGN KEY (ID_HEBERGEMENT)
               REFERENCES HEBERGEMENT (ID_HEBERGEMENT))   ;

ALTER TABLE PHOTO_HEBERGEMENT ADD (
     CONSTRAINT FK_PHOTO_HEBERGEMENT_HEBERGEME
          FOREIGN KEY (ID_HEBERGEMENT)
               REFERENCES HEBERGEMENT (ID_HEBERGEMENT))   ;

ALTER TABLE RESERVATION_LOGEMENT ADD (
     CONSTRAINT FK_RESERVATION_LOGEMENT_LOGEME
          FOREIGN KEY (LOGEMENT_ID)
               REFERENCES LOGEMENT (LOGEMENT_ID))   ;

ALTER TABLE RESERVATION_LOGEMENT ADD (
     CONSTRAINT FK_RESERVATION_LOGEMENT_PANIER
          FOREIGN KEY (ID_PANIER)
               REFERENCES PANIER (ID_PANIER))   ;

ALTER TABLE RESERVATION_BILLET ADD (
     CONSTRAINT FK_RESERVATION_BILLET_PANIER
          FOREIGN KEY (ID_PANIER)
               REFERENCES PANIER (ID_PANIER))   ;

ALTER TABLE RESERVATION_BILLET ADD (
     CONSTRAINT FK_RESERVATION_BILLET_CATEGORI
          FOREIGN KEY (ID_CS)
               REFERENCES CATEGORIE_SOIREE (ID_CS))   ;


-- -----------------------------------------------------------------------------
--                FIN DE GENERATION
-- -----------------------------------------------------------------------------
