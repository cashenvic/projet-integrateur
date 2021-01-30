package festiBed.DAO.concret.hebergement;

import festiBed.models.hebergement.Camping;
import festiBed.services.CampingService;
import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CampingDao {
    public List<Camping> getAllCampings(int limit, int offset) throws SQLException {
        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        String req = "select * from (" +
                "    select * from HEBERGEMENT h join LOGEMENT l" +
                "    on h.ID_HEBERGEMENT = l.ID_HEBERGEMENT" +
                "    natural join LOCALISATION ll" +
                "    where h.TYPE = 'CAMPING' and l.STATUT = 'Actif')" +
                " where ROWNUM between ? and ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, offset);
        ps.setInt(2, (offset + limit));
        rs = ps.executeQuery();

        List<Camping> cs = CampingService.searchResultToCampingList(rs);

        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return cs;
    }

    public Camping getCampingById(int id_camping) throws SQLException {
        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        String req = "select * from (select * from HEBERGEMENT natural join LOCALISATION" +
                " where ID_HEBERGEMENT = ?) h join LOGEMENT l on h.ID_HEBERGEMENT = l.ID_HEBERGEMENT";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, id_camping);
        rs = ps.executeQuery();

        Camping c = CampingService.resultToCamping(rs);
        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return c;
    }

    public List<Camping> search(String dateArrivee, String dateDepart, String type, String nomDep, int limit, int offset) throws Exception {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();

        String requete = "SELECT * FROM hebergement h1 NATURAL JOIN LOCALISATION\n" +
                " join LOGEMENT log on h1.ID_HEBERGEMENT = log.ID_HEBERGEMENT " +
                "WHERE NOM_DEPARTEMENT = ? AND \"TYPE\" = ? \n" +
                "AND h1.ID_HEBERGEMENT NOT IN \n" +
                "(SELECT ID_HEBERGEMENT FROM\n" +
                "(SELECT h.ID_HEBERGEMENT, l.CAPACITE, sum(r.NBR_ADULTE) AS tot FROM\n" +
                "HEBERGEMENT h\n" +
                "NATURAL JOIN LOCALISATION\n" +
                "JOIN LOGEMENT l ON l.ID_HEBERGEMENT = h.ID_HEBERGEMENT\n" +
                "LEFT JOIN DATE_INDISPO d ON l.ID_LOGEMENT = d.ID_LOGEMENT\n" +
                "LEFT JOIN RESERVATION_LOGEMENT r ON r.ID_LOGEMENT = l.ID_LOGEMENT\n" +
                "WHERE ((to_Date(?, 'dd/mm/yyyy') < date_Depart AND to_Date(?, 'dd/mm/yyyy') >= date_Arrivee)\n" +
                "OR (to_Date(?, 'dd/mm/yyyy') > date_Arrivee AND to_Date(?, 'dd/mm/yyyy') <= date_Depart)\n" +
                "OR (to_Date(?, 'dd/mm/yyyy') < date_Fin AND to_Date(?, 'dd/mm/yyyy') >= date_Debut)\n" +
                "OR (to_Date(?, 'dd/mm/yyyy') > date_Debut AND to_Date(?, 'dd/mm/yyyy') <= date_Fin))\n" +
                "AND (r.statut = 'Payer' OR r.statut = 'Traitement')\n" +
                "AND (nom_departement = ? AND \"TYPE\" = ?)\n" +
                "GROUP BY h.ID_HEBERGEMENT, l.CAPACITE) WHERE TOT = CAPACITE)\n" +
                "AND ROWNUM between ? AND ?";

        List<Camping> campings = null;
        ResultSet rs = null;
        PreparedStatement ps = conn.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;

        ps.setString(i++, nomDep);
        ps.setString(i++, type);
        ps.setString(i++, dateArrivee);
        ps.setString(i++, dateArrivee);
        ps.setString(i++, dateDepart);
        ps.setString(i++, dateDepart);
        ps.setString(i++, dateArrivee);
        ps.setString(i++, dateArrivee);
        ps.setString(i++, dateDepart);
        ps.setString(i++, dateDepart);
        ps.setString(i++, nomDep);
        ps.setString(i++, type);
        ps.setInt(i++, offset);
        ps.setInt(i++, offset+limit);



        rs = ps.executeQuery();
        campings = CampingService.searchResultToCampingList(rs);

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);
        return campings;

    }
}
