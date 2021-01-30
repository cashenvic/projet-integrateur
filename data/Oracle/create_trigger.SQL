-- -------------------------------------------------------------------------------
--   G�n�ration des triggers de la base
--   de donn�es : Sous_mod
--   (25/1/2020 17:47:06)
-- -------------------------------------------------------------------------------

-- -------------------------------------------------------------------------------
--   Table : DOMAINE_COMPLEMENTAIRE
-- -------------------------------------------------------------------------------

drop trigger TD_DOMAINE_COMPLEMENTAIRE;

-- Trigger de suppression ----------------------------------------------
create trigger TD_DOMAINE_COMPLEMENTAIRE
after delete on DOMAINE_COMPLEMENTAIRE for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de DOMAINE_COMPLEMENTAIRE s'il existe des
     -- occurrences correspondantes de la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          FESTIVAL.ID_DOMAINE_C = :old.ID_DOMAINE_C;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "DOMAINE_COMPLEMENTAIRE". Des occurrences de "FESTIVAL" existent.');
     end if;

end;
/

drop trigger TU_DOMAINE_COMPLEMENTAIRE;

-- Trigger de modification ----------------------------------------------
create trigger TU_DOMAINE_COMPLEMENTAIRE
after update on DOMAINE_COMPLEMENTAIRE for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table DOMAINE_COMPLEMENTAIRE s'il n'existe pas d'occurrence correspondante de la
     -- table DOMAINE.

     if
          :old.ID_DOMAINE_C <> :new.ID_DOMAINE_C
     then
          select count(*) into numrows
          from DOMAINE
          where
               :new.ID_DOMAINE = DOMAINE.ID_DOMAINE;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "DOMAINE_COMPLEMENTAIRE" car "DOMAINE" n''existe pas.');
          end if;
     end if;
     -- R�percuter la modification de la cl� primaire de DOMAINE_COMPLEMENTAIRE sur les
     -- occurrences correspondantes de la table FESTIVAL.

     if
          :old.ID_DOMAINE_C <> :new.ID_DOMAINE_C
     then
          update FESTIVAL
          set
               FESTIVAL.ID_DOMAINE_C = :new.ID_DOMAINE_C
          where
               FESTIVAL.ID_DOMAINE_C = :old.ID_DOMAINE_C;
     end if;

end;
/

drop trigger TI_DOMAINE_COMPLEMENTAIRE;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_DOMAINE_COMPLEMENTAIRE
after insert on DOMAINE_COMPLEMENTAIRE for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de DOMAINE_COMPLEMENTAIRE
     -- s'il n'existe pas d'occurrence correspondante dans la table DOMAINE.

     select count(*) into numrows
     from DOMAINE
     where
          :new.ID_DOMAINE = DOMAINE.ID_DOMAINE;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "DOMAINE_COMPLEMENTAIRE" car "DOMAINE" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : CATEGORIE_SOIREE
-- -------------------------------------------------------------------------------

drop trigger TD_CATEGORIE_SOIREE;

-- Trigger de suppression ----------------------------------------------
create trigger TD_CATEGORIE_SOIREE
after delete on CATEGORIE_SOIREE for each row
declare numrows INTEGER;
begin

     -- Supprimer les occurrences correspondantes de la table RESERVATION_BILLET.

     delete from RESERVATION_BILLET
     where
          RESERVATION_BILLET.ID_CS = :old.ID_CS;

end;
/

drop trigger TU_CATEGORIE_SOIREE;

-- Trigger de modification ----------------------------------------------
create trigger TU_CATEGORIE_SOIREE
after update on CATEGORIE_SOIREE for each row
declare numrows INTEGER;
begin

     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- SOIREE.

     if
          :old.ID_CS <> :new.ID_CS
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "SOIREE" interdite.');
     end if;
     -- R�percuter la modification de la cl� primaire de CATEGORIE_SOIREE sur les
     -- occurrences correspondantes de la table RESERVATION_BILLET.

     if
          :old.ID_CS <> :new.ID_CS
     then
          update RESERVATION_BILLET
          set
               RESERVATION_BILLET.ID_CS = :new.ID_CS
          where
               RESERVATION_BILLET.ID_CS = :old.ID_CS;
     end if;

end;
/

drop trigger TI_CATEGORIE_SOIREE;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_CATEGORIE_SOIREE
after insert on CATEGORIE_SOIREE for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de CATEGORIE_SOIREE
     -- s'il n'existe pas d'occurrence correspondante dans la table SOIREE.

     select count(*) into numrows
     from SOIREE
     where
          :new.ID_SOIREE = SOIREE.ID_SOIREE;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "CATEGORIE_SOIREE" car "SOIREE" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : UTILISATEUR
-- -------------------------------------------------------------------------------

drop trigger TD_UTILISATEUR;

-- Trigger de suppression ----------------------------------------------
create trigger TD_UTILISATEUR
after delete on UTILISATEUR for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de UTILISATEUR s'il existe des
     -- occurrences correspondantes de la table PANIER.

     select count(*) into numrows
     from PANIER
     where
          PANIER.ID_USER = :old.ID_USER;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "UTILISATEUR". Des occurrences de "PANIER" existent.');
     end if;
     -- Interdire la suppression d'une occurrence de UTILISATEUR s'il existe des
     -- occurrences correspondantes de la table HEBERGEMENT.

     select count(*) into numrows
     from HEBERGEMENT
     where
          HEBERGEMENT.ID_USER = :old.ID_USER;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "UTILISATEUR". Des occurrences de "HEBERGEMENT" existent.');
     end if;
     -- Interdire la suppression d'une occurrence de UTILISATEUR s'il existe des
     -- occurrences correspondantes de la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          FESTIVAL.ID_USER = :old.ID_USER;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "UTILISATEUR". Des occurrences de "FESTIVAL" existent.');
     end if;

end;
/

drop trigger TU_UTILISATEUR;

-- Trigger de modification ----------------------------------------------
create trigger TU_UTILISATEUR
after update on UTILISATEUR for each row
declare numrows INTEGER;
begin

     -- R�percuter la modification de la cl� primaire de UTILISATEUR sur les
     -- occurrences correspondantes de la table PANIER.

     if
          :old.ID_USER <> :new.ID_USER
     then
          update PANIER
          set
               PANIER.ID_USER = :new.ID_USER
          where
               PANIER.ID_USER = :old.ID_USER;
     end if;
     -- R�percuter la modification de la cl� primaire de UTILISATEUR sur les
     -- occurrences correspondantes de la table HEBERGEMENT.

     if
          :old.ID_USER <> :new.ID_USER
     then
          update HEBERGEMENT
          set
               HEBERGEMENT.ID_USER = :new.ID_USER
          where
               HEBERGEMENT.ID_USER = :old.ID_USER;
     end if;
     -- R�percuter la modification de la cl� primaire de UTILISATEUR sur les
     -- occurrences correspondantes de la table FESTIVAL.

     if
          :old.ID_USER <> :new.ID_USER
     then
          update FESTIVAL
          set
               FESTIVAL.ID_USER = :new.ID_USER
          where
               FESTIVAL.ID_USER = :old.ID_USER;
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : HEBERGEMENT
-- -------------------------------------------------------------------------------

drop trigger TD_HEBERGEMENT;

-- Trigger de suppression ----------------------------------------------
create trigger TD_HEBERGEMENT
after delete on HEBERGEMENT for each row
declare numrows INTEGER;
begin

     -- Mettre � NULL la cl� �trang�re des occurrences correspondantes de la
     -- table AVIS_HEBERGEMENT.

     update AVIS_HEBERGEMENT
     set
          AVIS_HEBERGEMENT.ID_HEBERGEMENT = NULL
     where
          AVIS_HEBERGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;
     -- Interdire la suppression d'une occurrence de HEBERGEMENT s'il existe des
     -- occurrences correspondantes de la table LOGEMENT.

     select count(*) into numrows
     from LOGEMENT
     where
          LOGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "HEBERGEMENT". Des occurrences de "LOGEMENT" existent.');
     end if;
     -- Mettre � NULL la cl� �trang�re des occurrences correspondantes de la
     -- table PHOTO_HEBERGEMENT.

     update PHOTO_HEBERGEMENT
     set
          PHOTO_HEBERGEMENT.ID_HEBERGEMENT = NULL
     where
          PHOTO_HEBERGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;

end;
/

drop trigger TU_HEBERGEMENT;

-- Trigger de modification ----------------------------------------------
create trigger TU_HEBERGEMENT
after update on HEBERGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table HEBERGEMENT s'il n'existe pas d'occurrence correspondante de la
     -- table LOCALISATION.

     if
          :old.ID_HEBERGEMENT <> :new.ID_HEBERGEMENT
     then
          select count(*) into numrows
          from LOCALISATION
          where
               :new.LATITUDE = LOCALISATION.LATITUDE and
               :new.LONGITUDE = LOCALISATION.LONGITUDE;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "HEBERGEMENT" car "LOCALISATION" n''existe pas.');
          end if;
     end if;
     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table HEBERGEMENT s'il n'existe pas d'occurrence correspondante de la
     -- table UTILISATEUR.

     if
          :old.ID_HEBERGEMENT <> :new.ID_HEBERGEMENT
     then
          select count(*) into numrows
          from UTILISATEUR
          where
               :new.ID_USER = UTILISATEUR.ID_USER;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "HEBERGEMENT" car "UTILISATEUR" n''existe pas.');
          end if;
     end if;
     -- R�percuter la modification de la cl� primaire de HEBERGEMENT sur les
     -- occurrences correspondantes de la table AVIS_HEBERGEMENT.

     if
          :old.ID_HEBERGEMENT <> :new.ID_HEBERGEMENT
     then
          update AVIS_HEBERGEMENT
          set
               AVIS_HEBERGEMENT.ID_HEBERGEMENT = :new.ID_HEBERGEMENT
          where
               AVIS_HEBERGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;
     end if;
     -- R�percuter la modification de la cl� primaire de HEBERGEMENT sur les
     -- occurrences correspondantes de la table LOGEMENT.

     if
          :old.ID_HEBERGEMENT <> :new.ID_HEBERGEMENT
     then
          update LOGEMENT
          set
               LOGEMENT.ID_HEBERGEMENT = :new.ID_HEBERGEMENT
          where
               LOGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;
     end if;
     -- R�percuter la modification de la cl� primaire de HEBERGEMENT sur les
     -- occurrences correspondantes de la table PHOTO_HEBERGEMENT.

     if
          :old.ID_HEBERGEMENT <> :new.ID_HEBERGEMENT
     then
          update PHOTO_HEBERGEMENT
          set
               PHOTO_HEBERGEMENT.ID_HEBERGEMENT = :new.ID_HEBERGEMENT
          where
               PHOTO_HEBERGEMENT.ID_HEBERGEMENT = :old.ID_HEBERGEMENT;
     end if;

end;
/

drop trigger TI_HEBERGEMENT;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_HEBERGEMENT
after insert on HEBERGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de HEBERGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table LOCALISATION.

     select count(*) into numrows
     from LOCALISATION
     where
          :new.LATITUDE = LOCALISATION.LATITUDE and
          :new.LONGITUDE = LOCALISATION.LONGITUDE;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "HEBERGEMENT" car "LOCALISATION" n''existe pas.');
     end if;
     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de HEBERGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table UTILISATEUR.

     select count(*) into numrows
     from UTILISATEUR
     where
          :new.ID_USER = UTILISATEUR.ID_USER;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "HEBERGEMENT" car "UTILISATEUR" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : SOIREE
-- -------------------------------------------------------------------------------

drop trigger TD_SOIREE;

-- Trigger de suppression ----------------------------------------------
create trigger TD_SOIREE
after delete on SOIREE for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de SOIREE s'il existe des
     -- occurrences correspondantes de la table CATEGORIE_SOIREE.

     select count(*) into numrows
     from CATEGORIE_SOIREE
     where
          CATEGORIE_SOIREE.ID_SOIREE = :old.ID_SOIREE;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "SOIREE". Des occurrences de "CATEGORIE_SOIREE" existent.');
     end if;

end;
/

drop trigger TU_SOIREE;

-- Trigger de modification ----------------------------------------------
create trigger TU_SOIREE
after update on SOIREE for each row
declare numrows INTEGER;
begin

     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- FESTIVAL.

     if
          :old.ID_SOIREE <> :new.ID_SOIREE
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "FESTIVAL" interdite.');
     end if;
     -- R�percuter la modification de la cl� primaire de SOIREE sur les
     -- occurrences correspondantes de la table CATEGORIE_SOIREE.

     if
          :old.ID_SOIREE <> :new.ID_SOIREE
     then
          update CATEGORIE_SOIREE
          set
               CATEGORIE_SOIREE.ID_SOIREE = :new.ID_SOIREE
          where
               CATEGORIE_SOIREE.ID_SOIREE = :old.ID_SOIREE;
     end if;

end;
/

drop trigger TI_SOIREE;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_SOIREE
after insert on SOIREE for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de SOIREE
     -- s'il n'existe pas d'occurrence correspondante dans la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          :new.ID_FESTIVAL = FESTIVAL.ID_FESTIVAL;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "SOIREE" car "FESTIVAL" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : PHOTO_FESTIVAL
-- -------------------------------------------------------------------------------

drop trigger TU_PHOTO_FESTIVAL;

-- Trigger de modification ----------------------------------------------
create trigger TU_PHOTO_FESTIVAL
after update on PHOTO_FESTIVAL for each row
declare numrows INTEGER;
begin


end;
/

drop trigger TI_PHOTO_FESTIVAL;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_PHOTO_FESTIVAL
after insert on PHOTO_FESTIVAL for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de PHOTO_FESTIVAL
     -- s'il n'existe pas d'occurrence correspondante dans la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          :new.ID_FESTIVAL = FESTIVAL.ID_FESTIVAL;
     if
          (
          :new.ID_FESTIVAL is not null and
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "PHOTO_FESTIVAL" car "FESTIVAL" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : LOCALISATION
-- -------------------------------------------------------------------------------

drop trigger TD_LOCALISATION;

-- Trigger de suppression ----------------------------------------------
create trigger TD_LOCALISATION
after delete on LOCALISATION for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de LOCALISATION s'il existe des
     -- occurrences correspondantes de la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          FESTIVAL.LATITUDE = :old.LATITUDE and
          FESTIVAL.LONGITUDE = :old.LONGITUDE;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "LOCALISATION". Des occurrences de "FESTIVAL" existent.');
     end if;
     -- Interdire la suppression d'une occurrence de LOCALISATION s'il existe des
     -- occurrences correspondantes de la table HEBERGEMENT.

     select count(*) into numrows
     from HEBERGEMENT
     where
          HEBERGEMENT.LATITUDE = :old.LATITUDE and
          HEBERGEMENT.LONGITUDE = :old.LONGITUDE;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "LOCALISATION". Des occurrences de "HEBERGEMENT" existent.');
     end if;

end;
/

drop trigger TU_LOCALISATION;

-- Trigger de modification ----------------------------------------------
create trigger TU_LOCALISATION
after update on LOCALISATION for each row
declare numrows INTEGER;
begin

     -- R�percuter la modification de la cl� primaire de LOCALISATION sur les
     -- occurrences correspondantes de la table FESTIVAL.

     if
          :old.LATITUDE <> :new.LATITUDE or
          :old.LONGITUDE <> :new.LONGITUDE
     then
          update FESTIVAL
          set
               FESTIVAL.LATITUDE = :new.LATITUDE,
               FESTIVAL.LONGITUDE = :new.LONGITUDE
          where
               FESTIVAL.LATITUDE = :old.LATITUDE and
               FESTIVAL.LONGITUDE = :old.LONGITUDE;
     end if;
     -- R�percuter la modification de la cl� primaire de LOCALISATION sur les
     -- occurrences correspondantes de la table HEBERGEMENT.

     if
          :old.LATITUDE <> :new.LATITUDE or
          :old.LONGITUDE <> :new.LONGITUDE
     then
          update HEBERGEMENT
          set
               HEBERGEMENT.LATITUDE = :new.LATITUDE,
               HEBERGEMENT.LONGITUDE = :new.LONGITUDE
          where
               HEBERGEMENT.LATITUDE = :old.LATITUDE and
               HEBERGEMENT.LONGITUDE = :old.LONGITUDE;
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : LOGEMENT
-- -------------------------------------------------------------------------------

drop trigger TD_LOGEMENT;

-- Trigger de suppression ----------------------------------------------
create trigger TD_LOGEMENT
after delete on LOGEMENT for each row
declare numrows INTEGER;
begin

     -- Supprimer les occurrences correspondantes de la table RESERVATION_LOGEMENT.

     delete from RESERVATION_LOGEMENT
     where
          RESERVATION_LOGEMENT.LOGEMENT_ID = :old.LOGEMENT_ID;

end;
/

drop trigger TU_LOGEMENT;

-- Trigger de modification ----------------------------------------------
create trigger TU_LOGEMENT
after update on LOGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table LOGEMENT s'il n'existe pas d'occurrence correspondante de la
     -- table HEBERGEMENT.

     if
          :old.LOGEMENT_ID <> :new.LOGEMENT_ID
     then
          select count(*) into numrows
          from HEBERGEMENT
          where
               :new.ID_HEBERGEMENT = HEBERGEMENT.ID_HEBERGEMENT;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "LOGEMENT" car "HEBERGEMENT" n''existe pas.');
          end if;
     end if;
     -- R�percuter la modification de la cl� primaire de LOGEMENT sur les
     -- occurrences correspondantes de la table RESERVATION_LOGEMENT.

     if
          :old.LOGEMENT_ID <> :new.LOGEMENT_ID
     then
          update RESERVATION_LOGEMENT
          set
               RESERVATION_LOGEMENT.LOGEMENT_ID = :new.LOGEMENT_ID
          where
               RESERVATION_LOGEMENT.LOGEMENT_ID = :old.LOGEMENT_ID;
     end if;

end;
/

drop trigger TI_LOGEMENT;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_LOGEMENT
after insert on LOGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de LOGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table HEBERGEMENT.

     select count(*) into numrows
     from HEBERGEMENT
     where
          :new.ID_HEBERGEMENT = HEBERGEMENT.ID_HEBERGEMENT;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "LOGEMENT" car "HEBERGEMENT" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : DOMAINE
-- -------------------------------------------------------------------------------

drop trigger TD_DOMAINE;

-- Trigger de suppression ----------------------------------------------
create trigger TD_DOMAINE
after delete on DOMAINE for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de DOMAINE s'il existe des
     -- occurrences correspondantes de la table DOMAINE_COMPLEMENTAIRE.

     select count(*) into numrows
     from DOMAINE_COMPLEMENTAIRE
     where
          DOMAINE_COMPLEMENTAIRE.ID_DOMAINE = :old.ID_DOMAINE;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "DOMAINE". Des occurrences de "DOMAINE_COMPLEMENTAIRE" existent.');
     end if;

end;
/

drop trigger TU_DOMAINE;

-- Trigger de modification ----------------------------------------------
create trigger TU_DOMAINE
after update on DOMAINE for each row
declare numrows INTEGER;
begin

     -- R�percuter la modification de la cl� primaire de DOMAINE sur les
     -- occurrences correspondantes de la table DOMAINE_COMPLEMENTAIRE.

     if
          :old.ID_DOMAINE <> :new.ID_DOMAINE
     then
          update DOMAINE_COMPLEMENTAIRE
          set
               DOMAINE_COMPLEMENTAIRE.ID_DOMAINE = :new.ID_DOMAINE
          where
               DOMAINE_COMPLEMENTAIRE.ID_DOMAINE = :old.ID_DOMAINE;
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : PANIER
-- -------------------------------------------------------------------------------

drop trigger TD_PANIER;

-- Trigger de suppression ----------------------------------------------
create trigger TD_PANIER
after delete on PANIER for each row
declare numrows INTEGER;
begin

     -- Supprimer les occurrences correspondantes de la table RESERVATION_LOGEMENT.

     delete from RESERVATION_LOGEMENT
     where
          RESERVATION_LOGEMENT.ID_PANIER = :old.ID_PANIER;
     -- Supprimer les occurrences correspondantes de la table RESERVATION_BILLET.

     delete from RESERVATION_BILLET
     where
          RESERVATION_BILLET.ID_PANIER = :old.ID_PANIER;

end;
/

drop trigger TU_PANIER;

-- Trigger de modification ----------------------------------------------
create trigger TU_PANIER
after update on PANIER for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table PANIER s'il n'existe pas d'occurrence correspondante de la
     -- table UTILISATEUR.

     if
          :old.ID_PANIER <> :new.ID_PANIER
     then
          select count(*) into numrows
          from UTILISATEUR
          where
               :new.ID_USER = UTILISATEUR.ID_USER;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "PANIER" car "UTILISATEUR" n''existe pas.');
          end if;
     end if;
     -- R�percuter la modification de la cl� primaire de PANIER sur les
     -- occurrences correspondantes de la table RESERVATION_LOGEMENT.

     if
          :old.ID_PANIER <> :new.ID_PANIER
     then
          update RESERVATION_LOGEMENT
          set
               RESERVATION_LOGEMENT.ID_PANIER = :new.ID_PANIER
          where
               RESERVATION_LOGEMENT.ID_PANIER = :old.ID_PANIER;
     end if;
     -- R�percuter la modification de la cl� primaire de PANIER sur les
     -- occurrences correspondantes de la table RESERVATION_BILLET.

     if
          :old.ID_PANIER <> :new.ID_PANIER
     then
          update RESERVATION_BILLET
          set
               RESERVATION_BILLET.ID_PANIER = :new.ID_PANIER
          where
               RESERVATION_BILLET.ID_PANIER = :old.ID_PANIER;
     end if;

end;
/

drop trigger TI_PANIER;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_PANIER
after insert on PANIER for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de PANIER
     -- s'il n'existe pas d'occurrence correspondante dans la table UTILISATEUR.

     select count(*) into numrows
     from UTILISATEUR
     where
          :new.ID_USER = UTILISATEUR.ID_USER;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "PANIER" car "UTILISATEUR" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : FESTIVAL
-- -------------------------------------------------------------------------------

drop trigger TD_FESTIVAL;

-- Trigger de suppression ----------------------------------------------
create trigger TD_FESTIVAL
after delete on FESTIVAL for each row
declare numrows INTEGER;
begin

     -- Interdire la suppression d'une occurrence de FESTIVAL s'il existe des
     -- occurrences correspondantes de la table SOIREE.

     select count(*) into numrows
     from SOIREE
     where
          SOIREE.ID_FESTIVAL = :old.ID_FESTIVAL;
     if (numrows > 0) then
          raise_application_error(
          -20001,
          'Impossible de supprimer "FESTIVAL". Des occurrences de "SOIREE" existent.');
     end if;
     -- Mettre � NULL la cl� �trang�re des occurrences correspondantes de la
     -- table PHOTO_FESTIVAL.

     update PHOTO_FESTIVAL
     set
          PHOTO_FESTIVAL.ID_FESTIVAL = NULL
     where
          PHOTO_FESTIVAL.ID_FESTIVAL = :old.ID_FESTIVAL;
     -- Mettre � NULL la cl� �trang�re des occurrences correspondantes de la
     -- table AVIS_FESTIVAL.

     update AVIS_FESTIVAL
     set
          AVIS_FESTIVAL.ID_FESTIVAL = NULL
     where
          AVIS_FESTIVAL.ID_FESTIVAL = :old.ID_FESTIVAL;

end;
/

drop trigger TU_FESTIVAL;

-- Trigger de modification ----------------------------------------------
create trigger TU_FESTIVAL
after update on FESTIVAL for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table FESTIVAL s'il n'existe pas d'occurrence correspondante de la
     -- table LOCALISATION.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          select count(*) into numrows
          from LOCALISATION
          where
               :new.LATITUDE = LOCALISATION.LATITUDE and
               :new.LONGITUDE = LOCALISATION.LONGITUDE;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "FESTIVAL" car "LOCALISATION" n''existe pas.');
          end if;
     end if;
     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table FESTIVAL s'il n'existe pas d'occurrence correspondante de la
     -- table DOMAINE_COMPLEMENTAIRE.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          select count(*) into numrows
          from DOMAINE_COMPLEMENTAIRE
          where
               :new.ID_DOMAINE_C = DOMAINE_COMPLEMENTAIRE.ID_DOMAINE_C;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "FESTIVAL" car "DOMAINE_COMPLEMENTAIRE" n''existe pas.');
          end if;
     end if;
     -- Sauf valeur nulle, interdire la modification de la cl� �trang�re de la
     -- table FESTIVAL s'il n'existe pas d'occurrence correspondante de la
     -- table UTILISATEUR.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          select count(*) into numrows
          from UTILISATEUR
          where
               :new.ID_USER = UTILISATEUR.ID_USER;
          if
               (
               numrows = 0
               )
          then
               raise_application_error(
               -20007,
               'Impossible de mettre � jour "FESTIVAL" car "UTILISATEUR" n''existe pas.');
          end if;
     end if;
     -- R�percuter la modification de la cl� primaire de FESTIVAL sur les
     -- occurrences correspondantes de la table SOIREE.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          update SOIREE
          set
               SOIREE.ID_FESTIVAL = :new.ID_FESTIVAL
          where
               SOIREE.ID_FESTIVAL = :old.ID_FESTIVAL;
     end if;
     -- R�percuter la modification de la cl� primaire de FESTIVAL sur les
     -- occurrences correspondantes de la table PHOTO_FESTIVAL.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          update PHOTO_FESTIVAL
          set
               PHOTO_FESTIVAL.ID_FESTIVAL = :new.ID_FESTIVAL
          where
               PHOTO_FESTIVAL.ID_FESTIVAL = :old.ID_FESTIVAL;
     end if;
     -- R�percuter la modification de la cl� primaire de FESTIVAL sur les
     -- occurrences correspondantes de la table AVIS_FESTIVAL.

     if
          :old.ID_FESTIVAL <> :new.ID_FESTIVAL
     then
          update AVIS_FESTIVAL
          set
               AVIS_FESTIVAL.ID_FESTIVAL = :new.ID_FESTIVAL
          where
               AVIS_FESTIVAL.ID_FESTIVAL = :old.ID_FESTIVAL;
     end if;

end;
/

drop trigger TI_FESTIVAL;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_FESTIVAL
after insert on FESTIVAL for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de FESTIVAL
     -- s'il n'existe pas d'occurrence correspondante dans la table LOCALISATION.

     select count(*) into numrows
     from LOCALISATION
     where
          :new.LATITUDE = LOCALISATION.LATITUDE and
          :new.LONGITUDE = LOCALISATION.LONGITUDE;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "FESTIVAL" car "LOCALISATION" n''existe pas.');
     end if;
     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de FESTIVAL
     -- s'il n'existe pas d'occurrence correspondante dans la table DOMAINE_COMPLEMENTAIRE.

     select count(*) into numrows
     from DOMAINE_COMPLEMENTAIRE
     where
          :new.ID_DOMAINE_C = DOMAINE_COMPLEMENTAIRE.ID_DOMAINE_C;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "FESTIVAL" car "DOMAINE_COMPLEMENTAIRE" n''existe pas.');
     end if;
     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de FESTIVAL
     -- s'il n'existe pas d'occurrence correspondante dans la table UTILISATEUR.

     select count(*) into numrows
     from UTILISATEUR
     where
          :new.ID_USER = UTILISATEUR.ID_USER;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "FESTIVAL" car "UTILISATEUR" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : AVIS_FESTIVAL
-- -------------------------------------------------------------------------------

drop trigger TU_AVIS_FESTIVAL;

-- Trigger de modification ----------------------------------------------
create trigger TU_AVIS_FESTIVAL
after update on AVIS_FESTIVAL for each row
declare numrows INTEGER;
begin


end;
/

drop trigger TI_AVIS_FESTIVAL;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_AVIS_FESTIVAL
after insert on AVIS_FESTIVAL for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de AVIS_FESTIVAL
     -- s'il n'existe pas d'occurrence correspondante dans la table FESTIVAL.

     select count(*) into numrows
     from FESTIVAL
     where
          :new.ID_FESTIVAL = FESTIVAL.ID_FESTIVAL;
     if
          (
          :new.ID_FESTIVAL is not null and
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "AVIS_FESTIVAL" car "FESTIVAL" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : AVIS_HEBERGEMENT
-- -------------------------------------------------------------------------------

drop trigger TU_AVIS_HEBERGEMENT;

-- Trigger de modification ----------------------------------------------
create trigger TU_AVIS_HEBERGEMENT
after update on AVIS_HEBERGEMENT for each row
declare numrows INTEGER;
begin


end;
/

drop trigger TI_AVIS_HEBERGEMENT;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_AVIS_HEBERGEMENT
after insert on AVIS_HEBERGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de AVIS_HEBERGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table HEBERGEMENT.

     select count(*) into numrows
     from HEBERGEMENT
     where
          :new.ID_HEBERGEMENT = HEBERGEMENT.ID_HEBERGEMENT;
     if
          (
          :new.ID_HEBERGEMENT is not null and
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "AVIS_HEBERGEMENT" car "HEBERGEMENT" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : PHOTO_HEBERGEMENT
-- -------------------------------------------------------------------------------

drop trigger TU_PHOTO_HEBERGEMENT;

-- Trigger de modification ----------------------------------------------
create trigger TU_PHOTO_HEBERGEMENT
after update on PHOTO_HEBERGEMENT for each row
declare numrows INTEGER;
begin


end;
/

drop trigger TI_PHOTO_HEBERGEMENT;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_PHOTO_HEBERGEMENT
after insert on PHOTO_HEBERGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de PHOTO_HEBERGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table HEBERGEMENT.

     select count(*) into numrows
     from HEBERGEMENT
     where
          :new.ID_HEBERGEMENT = HEBERGEMENT.ID_HEBERGEMENT;
     if
          (
          :new.ID_HEBERGEMENT is not null and
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "PHOTO_HEBERGEMENT" car "HEBERGEMENT" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : RESERVATION_LOGEMENT
-- -------------------------------------------------------------------------------

drop trigger TU_RESERVATION_LOGEMENT;

-- Trigger de modification ----------------------------------------------
create trigger TU_RESERVATION_LOGEMENT
after update on RESERVATION_LOGEMENT for each row
declare numrows INTEGER;
begin

     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- LOGEMENT.

     if
          :old.LOGEMENT_ID <> :new.LOGEMENT_ID or
          :old.ID_PANIER <> :new.ID_PANIER
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "LOGEMENT" interdite.');
     end if;
     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- PANIER.

     if
          :old.LOGEMENT_ID <> :new.LOGEMENT_ID or
          :old.ID_PANIER <> :new.ID_PANIER
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "PANIER" interdite.');
     end if;

end;
/

drop trigger TI_RESERVATION_LOGEMENT;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_RESERVATION_LOGEMENT
after insert on RESERVATION_LOGEMENT for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de RESERVATION_LOGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table LOGEMENT.

     select count(*) into numrows
     from LOGEMENT
     where
          :new.LOGEMENT_ID = LOGEMENT.LOGEMENT_ID;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "RESERVATION_LOGEMENT" car "LOGEMENT" n''existe pas.');
     end if;
     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de RESERVATION_LOGEMENT
     -- s'il n'existe pas d'occurrence correspondante dans la table PANIER.

     select count(*) into numrows
     from PANIER
     where
          :new.ID_PANIER = PANIER.ID_PANIER;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "RESERVATION_LOGEMENT" car "PANIER" n''existe pas.');
     end if;

end;
/



-- -------------------------------------------------------------------------------
--   Table : RESERVATION_BILLET
-- -------------------------------------------------------------------------------

drop trigger TU_RESERVATION_BILLET;

-- Trigger de modification ----------------------------------------------
create trigger TU_RESERVATION_BILLET
after update on RESERVATION_BILLET for each row
declare numrows INTEGER;
begin

     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- PANIER.

     if
          :old.ID_PANIER <> :new.ID_PANIER or
          :old.ID_CS <> :new.ID_CS
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "PANIER" interdite.');
     end if;
     -- Interdire la modification de la cl� �trang�re r�f�ren�ant la table
     -- CATEGORIE_SOIREE.

     if
          :old.ID_PANIER <> :new.ID_PANIER or
          :old.ID_CS <> :new.ID_CS
     then
               raise_application_error(
               -20008,
               'Modification de la cl� �trang�re r�f�ren�ant "CATEGORIE_SOIREE" interdite.');
     end if;

end;
/

drop trigger TI_RESERVATION_BILLET;

-- Trigger d'insertion ----------------------------------------------
create trigger TI_RESERVATION_BILLET
after insert on RESERVATION_BILLET for each row
declare numrows INTEGER;
begin

     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de RESERVATION_BILLET
     -- s'il n'existe pas d'occurrence correspondante dans la table PANIER.

     select count(*) into numrows
     from PANIER
     where
          :new.ID_PANIER = PANIER.ID_PANIER;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "RESERVATION_BILLET" car "PANIER" n''existe pas.');
     end if;
     -- Sauf valeur nulle autoris�e, interdire la cr�ation d'une occurrence de RESERVATION_BILLET
     -- s'il n'existe pas d'occurrence correspondante dans la table CATEGORIE_SOIREE.

     select count(*) into numrows
     from CATEGORIE_SOIREE
     where
          :new.ID_CS = CATEGORIE_SOIREE.ID_CS;
     if
          (
          numrows = 0
          )
     then
          raise_application_error(
               -20002,
               'Impossible d''ajouter "RESERVATION_BILLET" car "CATEGORIE_SOIREE" n''existe pas.');
     end if;

end;
/
