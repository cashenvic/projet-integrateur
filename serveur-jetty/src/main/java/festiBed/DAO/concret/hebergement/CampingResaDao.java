package festiBed.DAO.concret.hebergement;

import festiBed.DAO.interfaces.Dao;
import festiBed.models.panier.ReservationLogement;
import festiBed.utils.ConnexionOracle;

import java.sql.*;
import java.util.List;

public class CampingResaDao implements Dao<ReservationLogement> {


    public ReservationLogement save(ReservationLogement resa, String user_token) throws SQLException {

        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        conn.setAutoCommit(false);

        CallableStatement cstmt = null;
        String requette = "{CALL reserver_heb(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        cstmt = conn.prepareCall(requette);

        int i = 1;
        cstmt.setDate(i++, (Date) resa.getDate_arrivee());
        cstmt.setDate(i++, (Date) resa.getDate_depart());
        cstmt.setInt(i++, resa.getNbr_enfant());
        cstmt.setInt(i++, resa.getNbr_adulte());
        cstmt.setDouble(i++, resa.getMontant());
        cstmt.setString(i++, resa.getTokenSession());
        cstmt.setInt(i++, resa.getId_logement());
        cstmt.registerOutParameter(i++, Types.DATE);
        cstmt.registerOutParameter(i, Types.INTEGER);

        cstmt.execute();

        resa.setId_panier(cstmt.getInt(i--));
        resa.setDate_creation(cstmt.getDate(i));

        cstmt.close();
        ConnexionOracle.disconnect(conn);
        System.out.println(">>>>ResaDao renvoie " + resa);

        return resa;

    }

    @Override
    public int save(ReservationLogement bObject) throws SQLException {
        return 0;
    }

    @Override
    public void update(ReservationLogement bObject) throws SQLException {

    }

    @Override
    public void update(int id) throws SQLException {

    }

    @Override
    public ReservationLogement getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<ReservationLogement> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<ReservationLogement> getAll(int limit, int offset) throws SQLException {
        return null;
    }

    @Override
    public void destroy(ReservationLogement bObject) throws SQLException {

    }

    @Override
    public void destroy(int id) throws SQLException {

    }
}
