TRIGGER Supp_festival
    before delete on festival
    FOR EACH ROW
    DECLARE
        nbDispo Number;
        nbMax Number;
    BEGIN
        select sum(nbr_place_dispo) , sum(nbr_place) INTO nbDispo, nbMax
        from soiree s, categorie_soiree c
        where s.id_festival = :OLD.id_festival AND c.id_soiree = s.id_soiree
        group by id_festival;

        /*si deja reserver*/
        IF (nbDispo <> nbMax) THEN
            raise_application_error( -20001, 'On ne peut pas supprimer : festival avec des places d��j�� reserv��.' );
        END IF;
    END;
/

TRIGGER Supp_cs
    before delete on categorie_soiree
    FOR EACH ROW
    DECLARE
    nb_place_total Number;
    nbDispo Number;
    BEGIN
      select nbr_place_dispo, nbr_place INTO nbDispo, nb_place_total
      from categorie_soiree c
      where c.id_cs = :OLD.id_cs;
        IF (nbDispo <> nb_place_total) THEN
            raise_application_error( -20001, 'On ne peut pas supprimer : categorie_soiree avec des places d��j�� reserv��.' );
        END IF;
    END;
/

TRIGGER Supp_soiree
  before delete on soiree
  FOR EACH ROW
  DECLARE
      nbDispo Number;
      nbMax Number;
  BEGIN
      select sum(nbr_place_dispo) , sum(nbr_place) INTO nbDispo, nbMax
      from categorie_soiree c
      where c.id_soiree = :OLD.id_soiree
      group by id_soiree;

      /*si deja reserver*/
      IF (nbDispo < nbMax) THEN
          raise_application_error( -20001, 'On ne peut pas supprimer : soiree avec des places d��j�� reserv��.' );
      END IF;
  END;
/
