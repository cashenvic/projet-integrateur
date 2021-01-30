package festiBed.DAO.concret.festival;

import festiBed.DAO.interfaces.Dao;
import festiBed.models.festival.Festival;
import festiBed.services.FestivalService;
import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FestivalDao implements Dao<Festival> {
    @Override
    public int save(Festival festival) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returned_id = 0;
        String requette = "INSERT INTO FESTIVAL " +
        "(LATITUDE, LONGITUDE, ID_USER, NOM, DESCRIPTION, DATE_DEBUT, DATE_FIN, SITE_WEB, NOTE_GLOBAL, DATE_CREATION, STATUT) " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setDouble(i++, festival.getLatitude());
        ps.setDouble(i++, festival.getLongitude());
        ps.setInt(i++, festival.getId_user());
        ps.setString(i++, festival.getNom());
        ps.setString(i++, festival.getDescription());
        ps.setDate(i++, festival.getDate_debut());
        ps.setDate(i++, festival.getDate_fin());
        ps.setString(i++, festival.getSite_web());
        ps.setDouble(i++, festival.getNote_globale());
        ps.setDate(i++, festival.getDate_creation());
        rs = ps.executeQuery();

        if (ps.getGeneratedKeys().next()) {
            returned_id = ps.getGeneratedKeys().getInt(1);
        }

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);
        return returned_id;
    }

    @Override
    public void update(Festival festival) {
        // TODO Auto-generated method stub

    }

    @Override
    public Festival getById(int id) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        Festival festival = new Festival();
        String requette = "select * from festival where id_festival = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setInt(i++, id);
        rs = ps.executeQuery();
        festival = FestivalService.resultToFestival(rs);

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);

        return festival;
    }

    @Override
    public List<Festival> getAll() throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        List<Festival> festivals = new ArrayList<>();
        String requette = "select * from festival where id_festival <= 100";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery();
        festivals = FestivalService.resultToFestivalList(rs);

        rs.close();
        ps.close();

        ConnexionOracle.disconnect(conn);
        return festivals;
    }

    @Override
    public List<Festival> getAll(int limit, int offset) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        List<Festival> festivals = new ArrayList<>();
        //permettre de paginer avec une limit et un offset
        String roquette = "SELECT * from(" +
                "    select f.*, rownum r" +
                "    from festival f" +
                ") where r > ? and r <= ? order by date_debut";

        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(roquette, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setInt(i++, offset);
        ps.setInt(i++, (offset+limit));
        rs = ps.executeQuery();
        festivals = FestivalService.resultToFestivalList(rs);

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);

        return festivals;
    }

    @Override
    public void update(int id) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy(Festival bObject) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy(int id) throws SQLException {
        // TODO Auto-generated method stub

    }

    /**
     * Searches for festivals which match the given params
     * @param date_deb Start date of the festival to get
     * @param date_fin End date of the festival to get
     * @param id_dom Domain on what the festival belongs
     * @param departement
     * @param latitude
     * @param limit Number of rows to get
     * @param offset Number of rows to skip
     * @return A list of festivals that match the criterias
     * @throws SQLException
     */
    public List<Festival> search(String date_deb, String date_fin, String id_dom,
                                 String departement, int limit, int offset) throws Exception {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        //join on domaine_festival where id_domaine = ?
        String requette = "select distinct p.* from (\n" +
                "     select f.* from FESTIVAL f join domaine_complementaire d " +
                "     on f.id_domaine_c = d.id_domaine_c join LOCALISATION l " +
                "     on f.LONGITUDE = l.longitude and f.LATITUDE = l.LATITUDE " +
                "     where date_debut >= TO_DATE(?, 'DD/MM/YYYY') " +
                "     and date_debut <= TO_DATE(?, 'DD/MM/YYYY') " +
                "     and l.NOM_DEPARTEMENT = ? and id_domaine = ? " +
                "     ) p join soiree s on p.id_festival = s.id_festival " +
                " where s.statut = 'Disponible' and ROWNUM between ? and ?";
        List<Festival> festivals = null;
        ResultSet rs = null;
        PreparedStatement ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setString(i++, date_deb);
        ps.setString(i++, date_fin);
        ps.setString(i++, departement);
        ps.setInt(i++, Integer.parseInt(id_dom));
        ps.setInt(i++, offset);
        ps.setInt(i++, (offset+limit));
        rs = ps.executeQuery();

        festivals = FestivalService.resultToFestivalList(rs);

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);
        return festivals;
    }
}