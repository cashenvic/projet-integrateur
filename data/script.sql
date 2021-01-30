create sequence USER_SEQ
/

create sequence PANIER_SEQ
/

create table DOMAINE_FESTIVAL
(
    ID_DOMAINE  NUMBER(10) not null,
    ID_FESTIVAL NUMBER(10) not null,
    constraint PK_DOMAINE_FESTIVAL
        primary key (ID_DOMAINE, ID_FESTIVAL)
)
/

create index I_FK_DOMAINE_FESTIVAL_DOMAINE
    on DOMAINE_FESTIVAL (ID_DOMAINE)
/

create index I_FK_DOMAINE_FESTIVAL_FESTIVAL
    on DOMAINE_FESTIVAL (ID_FESTIVAL)
/

create table UTILISATEUR
(
    ID_USER        NUMBER(10)    not null,
    TOKEN_FIREBASE VARCHAR2(128),
    TOKEN_SESSION  VARCHAR2(128) not null,
    NOM            VARCHAR2(128),
    PRENOM         VARCHAR2(128),
    MAIL           VARCHAR2(256),
    STATUT         VARCHAR2(128) not null,
    TYPE           VARCHAR2(128) not null,
    constraint PK_UTILISATEUR
        primary key (ID_USER)
)
/

create or replace trigger AUTO_INCREMENT_ID_USER
    before insert
    on UTILISATEUR
    for each row
    when (new.ID_USER IS NULL)
BEGIN
    SELECT user_seq.NEXTVAL
    INTO :new.ID_USER
    FROM dual;
END;
/

create table LOCALISATION
(
    LATITUDE        FLOAT not null,
    LONGITUDE       FLOAT not null,
    NOM_DEPARTEMENT VARCHAR2(128),
    CODE_POSTAL     VARCHAR2(7),
    constraint PK_LOCALISATION
        primary key (LATITUDE, LONGITUDE)
)
/

create table HEBERGEMENT
(
    ID_HEBERGEMENT NUMBER(10)    not null,
    LATITUDE       FLOAT         not null,
    LONGITUDE      FLOAT         not null,
    ID_USER        NUMBER(10)    not null,
    NOM            VARCHAR2(255) not null,
    ADRESSE        VARCHAR2(255) not null,
    DATE_CREATION  DATE          not null,
    EMAIL          VARCHAR2(255) not null,
    SITE_WEB       VARCHAR2(255),
    TELEPHONE      VARCHAR2(50)  not null,
    STATUT         VARCHAR2(128) not null,
    TYPE           VARCHAR2(128) not null,
    NOMBRE_ETOILE  NUMBER(2),
    NOTE_GLOBAL    NUMBER(5),
    constraint PK_HEBERGEMENT
        primary key (ID_HEBERGEMENT),
    constraint FK_HEBERGEMENT_LOCALISATION
        foreign key (LATITUDE, LONGITUDE) references LOCALISATION,
    constraint FK_HEBERGEMENT_UTILISATEUR
        foreign key (ID_USER) references UTILISATEUR
)
/

create index I_FK_HEBERGEMENT_LOCALISATION
    on HEBERGEMENT (LATITUDE, LONGITUDE)
/

create index I_FK_HEBERGEMENT_UTILISATEUR
    on HEBERGEMENT (ID_USER)
/

create index IX_HEBERGEMENT_TYPE
    on HEBERGEMENT (TYPE)
/

create index IX_LOCALISATION_NOMDEP
    on LOCALISATION (NOM_DEPARTEMENT)
/

create table LOGEMENT
(
    ID_LOGEMENT            NUMBER(10, 2) not null,
    ID_HEBERGEMENT         NUMBER(10, 2) not null,
    STATUT                 VARCHAR2(128) not null,
    DATE_CREATION          DATE          not null,
    TARIF                  NUMBER(13, 2) not null,
    NBR_PERSONNE_CONSEILLE NUMBER(4),
    CAPACITE               NUMBER(4),
    NBR_PLACE_ADULTE       NUMBER(4),
    NBR_PLACE_ENFANT       NUMBER(4),
    TARIF_ADULTE           NUMBER(13, 2),
    TARIF_ENFANT           NUMBER(13, 2),
    constraint PK_LOGEMENT
        primary key (ID_LOGEMENT),
    constraint FK_LOGEMENT_HEBERGEMENT
        foreign key (ID_HEBERGEMENT) references HEBERGEMENT
)
/

create table PHOTO_LOGEMENT
(
    ID_PHOTO    NUMBER(10)    not null,
    CHEMIN      CHAR(255)     not null,
    STATUT      VARCHAR2(128) not null,
    ID_LOGEMENT FLOAT(63),
    COMMENTAIRE NUMBER,
    constraint PK_PHOTO_LOGEMENT
        primary key (ID_PHOTO),
    constraint FK_LOGEMENT
        foreign key (ID_LOGEMENT) references LOGEMENT
)
/

create table DATE_INDISPO
(
    ID_LOGEMENT NUMBER(10, 2) not null,
    DATE_DEBUT  DATE          not null,
    DATE_FIN    DATE          not null,
    constraint DATE_INDISPO_PK
        primary key (ID_LOGEMENT, DATE_DEBUT, DATE_FIN),
    constraint FK_DATE_INDISPO_LOGEMENT
        foreign key (ID_LOGEMENT) references LOGEMENT
)
/

create index I_FK_DATE_INDISPO_LOGEMENT
    on DATE_INDISPO (ID_LOGEMENT)
/

create index I_FK_LOGEMENT_HEBERGEMENT
    on LOGEMENT (ID_HEBERGEMENT)
/

create table DOMAINE
(
    ID_DOMAINE NUMBER(10)    not null,
    NOM        VARCHAR2(128) not null,
    constraint PK_DOMAINE
        primary key (ID_DOMAINE)
)
/

create table DOMAINE_COMPLEMENTAIRE
(
    ID_DOMAINE_C NUMBER(10)    not null,
    ID_DOMAINE   NUMBER(10)    not null,
    NOM          VARCHAR2(128) not null,
    constraint PK_DOMAINE_COMPLEMENTAIRE
        primary key (ID_DOMAINE_C),
    constraint FK_DOMAINE_COMPLEMENTAIRE_DOMA
        foreign key (ID_DOMAINE) references DOMAINE
)
/

create index I_FK_DOMAINE_COMPLEMENTAIRE_DO
    on DOMAINE_COMPLEMENTAIRE (ID_DOMAINE)
/

create table PANIER
(
    ID_PANIER     NUMBER(10)    not null,
    ID_USER       NUMBER(10)    not null,
    MONTANT_TOTAL NUMBER(10, 2) not null,
    DATE_CREATION DATE          not null,
    STATUT        VARCHAR2(128) not null,
    constraint PK_PANIER
        primary key (ID_PANIER),
    constraint FK_PANIER_UTILISATEUR
        foreign key (ID_USER) references UTILISATEUR
)
/

create index I_FK_PANIER_UTILISATEUR
    on PANIER (ID_USER)
/

create or replace trigger AUTO_INCREMENT_ID_PANIER
    before insert
    on PANIER
    for each row
    when (new.ID_PANIER IS NULL)
BEGIN
    SELECT panier_seq.NEXTVAL
    INTO :new.ID_PANIER
    FROM dual;
END;
/

create table FESTIVAL
(
    ID_FESTIVAL   NUMBER(10)    not null,
    LATITUDE      FLOAT         not null,
    LONGITUDE     FLOAT         not null,
    ID_DOMAINE_C  NUMBER(10)    not null,
    ID_USER       NUMBER(10)    not null,
    NOM           VARCHAR2(128) not null,
    DESCRIPTION   VARCHAR2(4000),
    DATE_DEBUT    DATE          not null,
    DATE_FIN      DATE          not null,
    SITE_WEB      VARCHAR2(128),
    NOTE_GLOBAL   NUMBER(10, 2),
    DATE_CREATION DATE          not null,
    STATUT        VARCHAR2(128) not null,
    constraint PK_FESTIVAL
        primary key (ID_FESTIVAL),
    constraint FK_FESTIVAL_DOMAINE_COMPLEMENT
        foreign key (ID_DOMAINE_C) references DOMAINE_COMPLEMENTAIRE,
    constraint FK_FESTIVAL_LOCALISATION
        foreign key (LATITUDE, LONGITUDE) references LOCALISATION,
    constraint FK_FESTIVAL_UTILISATEUR
        foreign key (ID_USER) references UTILISATEUR
)
/

create table SOIREE
(
    ID_SOIREE   NUMBER(10)    not null,
    ID_FESTIVAL NUMBER(10)    not null,
    DATE_SOIREE DATE          not null,
    STATUT      VARCHAR2(128) not null,
    constraint PK_SOIREE
        primary key (ID_SOIREE),
    constraint FK_SOIREE_FESTIVAL
        foreign key (ID_FESTIVAL) references FESTIVAL
)
/

create table CATEGORIE_SOIREE
(
    ID_CS           NUMBER(20)    not null,
    ID_SOIREE       NUMBER(10)    not null,
    NBR_PLACE       NUMBER(10)    not null,
    TARIF           NUMBER(13, 2) not null,
    NBR_PLACE_DISPO NUMBER(4)     not null,
    STATUT          VARCHAR2(128) not null,
    constraint PK_CATEGORIE_SOIREE
        primary key (ID_CS),
    constraint FK_CATEGORIE_SOIREE_SOIREE
        foreign key (ID_SOIREE) references SOIREE
)
/

create index I_FK_CATEGORIE_SOIREE_SOIREE
    on CATEGORIE_SOIREE (ID_SOIREE)
/

create or replace trigger MAJ_FESTIVAL_STATUT
    after update
    on CATEGORIE_SOIREE
    for each row
DECLARE
    nbSoireeNonComplete number DEFAULT 0;
    TABLE_MUTANTE EXCEPTION;
    idFestival          number;
    PRAGMA EXCEPTION_INIT (TABLE_MUTANTE, -4091);
BEGIN
    SELECT id_festival INTO idFestival FROM soiree WHERE :NEW.id_soiree = id_Soiree;
    SELECT count(*)
    INTO nbSoireeNonComplete
    FROM soiree
    WHERE id_festival = idFestival
      AND statut <> 'Complet'
    GROUP BY id_festival;
Exception
    WHEN NO_DATA_FOUND THEN
        UPDATE festival SET statut = 'Complet' WHERE id_festival = idFestival;
END;
/

create index I_FK_SOIREE_FESTIVAL
    on SOIREE (ID_FESTIVAL)
/

create table PHOTO_FESTIVAL
(
    ID_PHOTO    NUMBER(10)    not null,
    ID_FESTIVAL NUMBER(10),
    CHEMIN      VARCHAR2(255) not null,
    COMMENTAIRE CHAR(32),
    STATUT      VARCHAR2(128) not null,
    constraint PK_PHOTO_FESTIVAL
        primary key (ID_PHOTO),
    constraint FK_PHOTO_FESTIVAL_FESTIVAL
        foreign key (ID_FESTIVAL) references FESTIVAL
)
/

create index I_FK_PHOTO_FESTIVAL_FESTIVAL
    on PHOTO_FESTIVAL (ID_FESTIVAL)
/

create index I_FK_FESTIVAL_LOCALISATION
    on FESTIVAL (LATITUDE, LONGITUDE)
/

create index I_FK_FESTIVAL_DOMAINE_COMPLEME
    on FESTIVAL (ID_DOMAINE_C)
/

create index I_FK_FESTIVAL_UTILISATEUR
    on FESTIVAL (ID_USER)
/

create table AVIS_FESTIVAL
(
    ID_AVIS     NUMBER(10)    not null,
    ID_FESTIVAL NUMBER(10),
    NOTE        NUMBER(10, 2) not null,
    COMMENTAIRE VARCHAR2(3000),
    STATUT      CHAR(127)     not null,
    constraint PK_AVIS_FESTIVAL
        primary key (ID_AVIS),
    constraint FK_AVIS_FESTIVAL_FESTIVAL
        foreign key (ID_FESTIVAL) references FESTIVAL
)
/

create index I_FK_AVIS_FESTIVAL_FESTIVAL
    on AVIS_FESTIVAL (ID_FESTIVAL)
/

create table AVIS_HEBERGEMENT
(
    ID_AVIS        NUMBER(10)    not null,
    ID_HEBERGEMENT NUMBER(10),
    NOTE           NUMBER(1),
    COMMENTAIRE    CHAR(255),
    STATUT         VARCHAR2(128) not null,
    constraint PK_AVIS_HEBERGEMENT
        primary key (ID_AVIS),
    constraint FK_AVIS_HEBERGEMENT_HEBERGEMEN
        foreign key (ID_HEBERGEMENT) references HEBERGEMENT
)
/

create index I_FK_AVIS_HEBERGEMENT_HEBERGEM
    on AVIS_HEBERGEMENT (ID_HEBERGEMENT)
/

create table PHOTO_HEBERGEMENT
(
    ID_PHOTO       NUMBER(10)    not null,
    ID_HEBERGEMENT NUMBER(10),
    CHEMIN         CHAR(255),
    COMMENTAIRE    CHAR(255),
    STATUT         VARCHAR2(128) not null,
    constraint PK_PHOTO_HEBERGEMENT
        primary key (ID_PHOTO),
    constraint FK_PHOTO_HEBERGEMENT_HEBERGEME
        foreign key (ID_HEBERGEMENT) references HEBERGEMENT
)
/

create index I_FK_PHOTO_HEBERGEMENT_HEBERGE
    on PHOTO_HEBERGEMENT (ID_HEBERGEMENT)
/

create table RESERVATION_LOGEMENT
(
    ID_LOGEMENT   NUMBER(10, 2) not null,
    ID_PANIER     NUMBER(10)    not null,
    DATE_ARRIVEE  DATE          not null,
    DATE_DEPART   DATE          not null,
    NBR_ADULTE    NUMBER(2)     not null,
    NBR_ENFANT    NUMBER(2),
    MONTANT       NUMBER(13, 2) not null,
    STATUT        VARCHAR2(128) not null,
    DATE_CREATION DATE          not null,
    constraint PK_RESERVATION_LOGEMENT
        primary key (ID_LOGEMENT, ID_PANIER),
    constraint FK_RESERVATION_LOGEMENT_LOGEME
        foreign key (ID_LOGEMENT) references LOGEMENT,
    constraint FK_RESERVATION_LOGEMENT_PANIER
        foreign key (ID_PANIER) references PANIER
)
/

create index I_FK_RESERVATION_LOGEMENT_LOGE
    on RESERVATION_LOGEMENT (ID_LOGEMENT)
/

create index I_FK_RESERVATION_LOGEMENT_PANI
    on RESERVATION_LOGEMENT (ID_PANIER)
/

create or replace trigger MAJ_RESERVATION_LOGEMENT
    after update
    on RESERVATION_LOGEMENT
    for each row
DECLARE
BEGIN
    IF (:NEW.statut = 'ANNULE') THEN
        /* Logement Annule */
        UPDATE panier SET montant_total = montant_total + :NEW.montant - :OLD.montant;
    END IF;
END;
/

create or replace trigger VERIF_RESER_EMPLACEMENT
    before insert
    on RESERVATION_LOGEMENT
    for each row
DECLARE
    TABLE_MUTANTE EXCEPTION;
    PRAGMA EXCEPTION_INIT (TABLE_MUTANTE, -4091);
    nbLogementReserve number;
    CAPACITETot       number;
BEGIN
    SELECT sum(nbr_adulte)
    INTO nbLogementReserve
    FROM reservation_logement
    WHERE id_logement = :NEW.id_logement
      AND ((:NEW.date_Arrivee < date_Depart AND :NEW.date_Arrivee >= date_Arrivee)
        OR (:NEW.date_Depart > date_Arrivee AND :NEW.date_Depart <= date_Depart))
      AND (statut = 'Traitement' OR statut = 'Payer')
    GROUP BY id_logement;
    SELECT CAPACITE INTO CAPACITETot FROM LOGEMENT WHERE id_logement = :NEW.id_logement;
    IF (nbLogementReserve = CAPACITETot) THEN
        raise_application_error(-20001, 'La r��servation n est plus disponible');
        ROLLBACK;
    ELSIF (nbLogementReserve + :NEW.nbr_Adulte > CAPACITETot) THEN
        raise_application_error(-20001, 'Plus assez de place restante');
        ROLLBACK;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Fausse NO_DATA_FOUND');
    WHEN TABLE_MUTANTE THEN
        DBMS_OUTPUT.PUT_LINE('Fausse TABLE_MUTANTE');
END;
/

create table RESERVATION_BILLET
(
    ID_PANIER  NUMBER(10)    not null,
    ID_CS      NUMBER(20)    not null,
    NBR_PLACES NUMBER(13, 2) not null,
    MONTANT    NUMBER(13, 2) not null,
    STATUT     VARCHAR2(128) not null,
    DATE_RESA  DATE          not null,
    constraint PK_RESERVATION_BILLET
        primary key (ID_PANIER, ID_CS),
    constraint FK_RESERVATION_BILLET_CATEGORI
        foreign key (ID_CS) references CATEGORIE_SOIREE,
    constraint FK_RESERVATION_BILLET_PANIER
        foreign key (ID_PANIER) references PANIER
)
/

create index I_FK_RESERVATION_BILLET_PANIER
    on RESERVATION_BILLET (ID_PANIER)
/

create index I_FK_RESERVATION_BILLET_CATEGO
    on RESERVATION_BILLET (ID_CS)
/

create or replace trigger AJOUT_PLACE_FESTIVAL
    before insert
    on RESERVATION_BILLET
    for each row
DECLARE
    nbTicketDispo number;
    statut        varchar(15);
BEGIN
    SELECT nbr_place_dispo - :NEW.nbr_places, statut
    INTO nbTicketDispo, statut
    FROM categorie_soiree
    WHERE id_cs = :NEW.id_cs;

    IF nbTicketDispo < 0 THEN
        raise_application_error(-20001, 'Le nombre de place est d��pass��.');
    ELSE
        IF (nbTicketDispo = 0 AND statut <> 'Complet') THEN
            /* Si plus de billet la categorie est non Complete et nb_place = 0*/
            /* raise_application_error( -20001, 'Le festival n est pas affich�� Complet.' );*/
            UPDATE categorie_soiree SET statut = 'Complet' WHERE id_cs = :NEW.id_cs;
        ELSIF (nbTicketDispo <> 0 AND statut <> 'Disponible') THEN
            UPDATE categorie_soiree SET statut = 'Disponible' WHERE id_cs = :NEW.id_cs;
            /* raise_application_error( -20001, 'Le festival ne devrai pas ��tre affich�� Complet.' ); */
        END IF;

        /* MAJ du montant du panier / voir comment eviter update*/
        /* UPDATE panier SET montant_total = montant_total + :NEW.montant WHERE :NEW.id_panier = id_panier;*/
    END IF;

END;
/

create or replace trigger MAJ_SOIREE_STATUT
    after insert or update
    on RESERVATION_BILLET
    for each row
DECLARE
    nbCategorieNonComplet number DEFAULT 0;
    idSoiree              number;
BEGIN
    /* Verifie si tout les categorie sont Completes */
    SELECT Id_soiree INTO idSoiree FROM categorie_soiree WHERE id_cs = :NEW.id_cs;
    SELECT count(*)
    INTO nbCategorieNonComplet
    FROM categorie_soiree
    WHERE id_soiree = idSoiree
      and statut <> 'Complet'
    GROUP BY id_soiree;
Exception
    WHEN NO_DATA_FOUND THEN
        UPDATE soiree SET statut = 'Complet' WHERE id_soiree = idSoiree;

    /* HAVING count(id_cs) => valoir le nombre de tuples ?)
    /* HAVING count(id_cs) = (select count(id_cs) FROM categorie_soiree WHERE id_soiree = :NEW.id_soiree ); */
    /* On regarde si existe une categ non Complete */
END;
/

create or replace trigger MAJ_PLACE_FESTIVAL
    after update
    on RESERVATION_BILLET
    for each row
DECLARE
    nbPlaceRestant number;
    idSoiree       NUMBER;
    nbTicketDispo  NUMBER;
BEGIN
    /* Si on Annule la reservation il remettre les places dispos */
    IF (:NEW.statut = 'Annule') THEN
        /* Remise des places en ventes */
        UPDATE categorie_soiree
        SET nbr_Place_dispo = nbr_Place_dispo + :NEW.nbr_places,
            statut          = 'Disponible'
        WHERE id_cs = :NEW.id_cs;
        /* On d��duis du montant total les billtes annul��s */
        UPDATE panier SET montant_total = montant_total - :NEW.montant where id_Panier = :NEW.id_panier;

        SELECT id_soiree INTO idSoiree FROM categorie_soiree WHERE id_cs = :NEW.id_cs;
        /* La soiree redeviens Disponible */
        UPDATE soiree SET statut = 'Disponible' where id_soiree = idSoiree;
    ELSIF (:NEW.nbr_Places <> :OLD.nbr_Places) THEN
        IF (:NEW.nbr_Places > :OLD.nbr_Places) THEN
            UPDATE categorie_soiree
            SET nbr_Place = nbr_Place - :NEW.nbr_places + :OLD.nbr_places
            WHERE id_cs = :NEW.id_cs;
            SELECT nbr_Place INTO nbTicketDispo FROM categorie_soiree WHERE id_cs = :NEW.id_cs;
            IF (nbPlaceRestant = 0) THEN
                UPDATE categorie_soiree SET statut = 'Complet' WHERE id_cs = :NEW.id_cs;
            END IF;
            /* On ajoute du montant total les billets supplementaire */
            UPDATE panier SET montant_total = montant_total + :NEW.montant - :OLD.montant;
        ELSE
            UPDATE categorie_soiree
            SET nbr_Place = nbr_Place - :NEW.nbr_places + :OLD.nbr_places
            WHERE id_cs = :NEW.id_cs;
            SELECT nbr_Place INTO nbTicketDispo FROM categorie_soiree WHERE id_cs = :NEW.id_cs;
            UPDATE categorie_soiree SET statut = 'Disponible' WHERE id_cs = :NEW.id_cs;
            /* On ajoute du montant total les billets supplementaire */
            UPDATE panier SET montant_total = montant_total + :NEW.montant - :OLD.montant;
        END IF;
    ELSIF (:NEW.montant <> :OLD.montant) THEN
        UPDATE Panier SET montant_total = montant_total - :OLD.montant + :NEW.montant where id_panier = :OLD.id_panier;
    END IF;
END;
/

create or replace view AVIS_FEST_VIEW as
SELECT id_festival, count(id_avis) AS nbAvis, sum(NOTE) AS sommeNote
FROM AVIS_FESTIVAL af
GROUP BY ID_FESTIVAL
/

create or replace trigger MAJ_NOTE_GLOBAL_FEST
    instead of update
    on AVIS_FEST_VIEW
    for each row
DECLARE
BEGIN
    Dbms_Output.Put_Line('STATEMENT TRIGGER.');
    UPDATE festival SET note_global = :NEW.sommeNote / :NEW.nbAvis WHERE id_festival = :NEW.id_festival;
END;
/

create or replace view AVIS_HEB_VIEW as
SELECT id_hebergement, count(id_avis) AS nbAvis, sum(NOTE) AS sommeNote
FROM AVIS_HEBERGEMENT ah
GROUP BY ID_HEBERGEMENT
/

create or replace trigger MAJ_NOTE_GLOBAL_HEB
    after insert
    on AVIS_HEBERGEMENT
    for each row
DECLARE
    sumNote number;
    nbAvis  NUMBER;
BEGIN
    SELECT sommeNote, nbAvis INTO sumNote, nbAvis FROM Avis_Heb_View WHERE id_hebergement = :NEW.id_hebergement;
    UPDATE hebergement SET note_globale = sumNote / nbAvis WHERE id_hebergement = :NEW.id_hebergement;
END;
/

create or replace PROCEDURE Associer_User_Panier(tokenSession IN varchar, tokenFirebase IN varchar, nomParam IN VARCHAR,
                                                 prenomParam IN VARCHAR, mailParam IN VARCHAR)
    IS
    user
    UTILISATEUR%rowType;
    idGhostUser number;
BEGIN
    SELECT * INTO user FROM UTILISATEUR WHERE token_firebase = tokenFirebase;
    SELECT id_user INTO idGhostUser FROM UTILISATEUR WHERE TOKEN_SESSION = tokenSession;
    UPDATE Panier SET id_user = user.id_user WHERE id_user = idGhostUser;
    UPDATE UTILISATEUR SET token_Session = tokenSession WHERE token_firebase = tokenFirebase;
    DELETE FROM UTILISATEUR WHERE id_user = idGhostUser;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        UPDATE UTILISATEUR
        SET token_firebase = tokenFirebase,
            nom            =nomParam,
            prenom         = prenomParam,
            mail           = mailParam
        WHERE TOKEN_SESSION = tokenSession;
END;
/

create or replace PROCEDURE Identifier_User(tokenSession IN varchar2, user OUT INTEGER)
    IS
BEGIN
    SELECT id_user INTO user FROM UTILISATEUR WHERE TOKEN_SESSION = tokenSession;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        INSERT INTO UTILISATEUR (TOKEN_SESSION, statut, type) VALUES (tokenSession, 'Actif', 'Client');
        SELECT id_user INTO user FROM UTILISATEUR WHERE TOKEN_SESSION = tokenSession;
END;
/

create or replace PROCEDURE Reserver_heb(dateAr IN date, dateDep IN date, nbrEnfant IN number, nbrAdulte IN number,
                                         montant IN number, tokenSession IN VARCHAR2, logementId IN number,
                                         dateCrea OUT date, idPanier OUT NUMBER)
    IS
    user NUMBER;
BEGIN
    LOCK TABLE reservation_logement IN EXCLUSIVE MODE;
    /* Recup date sys */
    SELECT SYSDATE INTO dateCrea FROM DUAL;
    /* Recup id_user si existe */
    Identifier_User(tokenSession, USER);
    /* Recup id_Panier si exist */
    SELECT id_Panier INTO idPanier FROM PANIER WHERE id_user = user AND statut = 'Traitement' GROUP BY id_panier;
    if (idPanier IS NOT NULL) THEN
        /* si exist un panier */
        INSERT INTO reservation_logement
        values (logementId, idPanier, dateAr, dateDep, nbrAdulte, nbrEnfant, montant, 'Traitement', dateCrea);
        UPDATE Panier SET montant_total = montant_total + montant WHERE id_Panier = idPanier;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        /* Sinon cr��er un panier */
        INSERT INTO Panier (id_user, montant_total, date_creation, statut) values (user, 0, dateCrea, 'Traitement');
        SELECT id_Panier INTO idPanier FROM PANIER WHERE id_user = user AND statut = 'Traitement';
        INSERT INTO reservation_logement
        values (logementId, idPanier, dateAr, dateDep, nbrAdulte, nbrEnfant, montant, 'Traitement', dateCrea);
        UPDATE Panier SET montant_total = montant_total + montant WHERE id_Panier = 1;
END;
/

create or replace PROCEDURE Payer_Panier(tokenSession IN VARCHAR, idPanier out number)
    IS
    iduser number;
BEGIN
    SELECT id_user INTO iduser FROM UTILISATEUR WHERE token_Session = tokenSession;
    SELECT id_panier INTO idPanier FROM PANIER WHERE id_user = iduser AND statut = 'Traitement';
    UPDATE PANIER SET statut = 'Payer' WHERE id_panier = idPanier;
    UPDATE Reservation_logement SET statut = 'Payer' WHERE id_panier = idPanier;
    UPDATE Reservation_billet SET statut = 'Payer' WHERE id_panier = idPanier;
END;
/
