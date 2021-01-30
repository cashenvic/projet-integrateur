/*******************************************/
ALTER SESSION SET CURRENT_SCHEMA=KHADIRM;

SELECT owner, table_name FROM all_tables WHERE owner='KHADIRM';

delete from LOGEMENT;
delete from HEBERGEMENT;
delete from PHOTO_LOGEMENT;
delete from PHOTO_HEBERGEMENT;

select * from all_triggers WHERE BASE_OBJECT_TYPE = 'TABLE' AND TABLE_OWNER LIKE '%FESTIBED%' AND TRIGGER_NAME LIKE '%SUP%';

/*******************************************/
ALTER SESSION SET CURRENT_SCHEMA=FESTIBEDTEST;

select object_name, s.sid, s.serial#, p.spid
from v$locked_object l, dba_objects o, v$session s, v$process p
where l.object_id = o.object_id and l.session_id = s.sid and s.paddr = p.addr;

/*alter system kill session '49,243'; --`sid` and `serial#` get from step 1*/

/*******************************************/

ALTER SESSION SET CURRENT_SCHEMA=FESTIBEDTEST;
delete from CATEGORIE_SOIREE;
delete from SOIREE;
delete from PHOTO_FESTIVAL pf;
delete from FESTIVAL f2;
delete from DOMAINE_COMPLEMENTAIRE dc;
delete from DOMAINE d;
delete from UTILISATEUR u;
delete from LOCALISATION l;


commit;

/*******************************************/

ALTER SESSION SET CURRENT_SCHEMA=FESTIBEDTEST;
/*DELETE FROM RESERVATION_BILLET rb;
DELETE FROM RESERVATION_LOGEMENT rl;
DELETE FROM PANIER p;
DROP TRIGGER SUPP_HEBERGEMENT;
DELETE FROM DATE_INDISPO di;
delete from LOGEMENT l;
/*delete from HEBERGEMENT hER;*/
DELETE FROM FESTIBEDTEST.PHOTO_LOGEMENT pl;
commit;
