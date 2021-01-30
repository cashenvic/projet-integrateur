/* UTILISATEUR */
ALTER SESSION SET CURRENT_SCHEMA=FESTIBEDTEST;

drop sequence user_seq;
create sequence user_seq START WITH 1;
CREATE OR REPLACE TRIGGER id_user_increment
   BEFORE INSERT ON UTILISATEUR
   FOR EACH ROW
   WHEN (new.ID_USER IS NULL)
BEGIN
   SELECT user_seq.NEXTVAL
   INTO   :new.ID_USER
   FROM   dual;
END;
/

/**********************************************/

drop sequence user_seq;
create sequence user_seq START WITH 1;
CREATE OR REPLACE TRIGGER id_user_increment
   BEFORE INSERT ON UTILISATEUR
   FOR EACH ROW
   WHEN (new.ID_USER IS NULL)
BEGIN
   SELECT user_seq.NEXTVAL
   INTO   :new.ID_USER
   FROM   dual;
END;
/

/* PANIER */
drop sequence panier_seq;
create sequence panier_seq START WITH 1;
CREATE OR REPLACE TRIGGER ID_PANIER_increment
   BEFORE INSERT ON PANIER
   FOR EACH ROW
   WHEN (new.ID_PANIER IS NULL)
BEGIN
   SELECT panier_seq.NEXTVAL
   INTO   :new.ID_PANIER
   FROM   dual;
END;
/
