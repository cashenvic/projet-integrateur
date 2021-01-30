package festiBed.DAO.concret.festival;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.panier.PanierDao;
import festiBed.DAO.interfaces.Dao;
import festiBed.models.panier.Panier;
import festiBed.models.panier.ReservationBillet;
import festiBed.models.statut.PanierStatut;
import festiBed.models.statut.ResaBilletStatut;
import festiBed.services.FestivalResaService;
import festiBed.utils.ConnexionOracle;

import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class FestivalResaDao implements Dao<ReservationBillet> {
    @Override
    public int save(ReservationBillet resa) throws SQLException {
        /*Connection conn = ConnexionOracle.getConnexionOracle();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returned_id = 0;
        String requette = "INSERT INTO RESERVATION_BILLET " +
                "(ID_PANIER, ID_CS, NBR_PLACES, MONTANT, STATUS, DATE_RESA) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        //If the panier does not exist yet create it first then continue
        if (!panierExists(resa.getId_panier())) {
            resa.setId_panier(initPanier(1));
        }

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setInt(i++, resa.getId_panier());
        ps.setInt(i++, resa.getId_cs());
        ps.setInt(i++, resa.getNbr_places());
        ps.setDouble(i++, resa.getMontant());
        ps.setString(i++, resa.getStatus());
        ps.setDate(i++, resa.getDate_resa());
        rs = ps.executeQuery();

        if(ps.getGeneratedKeys().next()) {
            returned_id = ps.getGeneratedKeys().getInt(1);
        }

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);
        return returned_id;*/
        return 0;
    }

    public synchronized ReservationBillet save(ReservationBillet resa, String user_token) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        conn.setAutoCommit(false);

        PreparedStatement ps = null;
        String requette = "INSERT INTO RESERVATION_BILLET " +
                "(ID_PANIER, ID_CS, NBR_PLACES, MONTANT, STATUT, DATE_RESA) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        //If the panier does not exist yet create it first then continue
        DAOFactory daoFactory = DAOFactory.getInstance();
        PanierDao pdao = daoFactory.getPanierDao();
        int id_panier = pdao.isPanierExisting(user_token, PanierStatut.Traitement.getValue());
        if (id_panier == 0) {
            resa.setId_panier(initPanier(user_token));
        } else {
            resa.setId_panier(id_panier);
        }

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setInt(i++, resa.getId_panier());
        ps.setInt(i++, resa.getId_cs());
        ps.setInt(i++, resa.getNbr_places());
        ps.setDouble(i++, resa.getMontant());
        ps.setString(i++, ResaBilletStatut.Traitement.getValue());
        ps.setDate(i++, resa.getDate_resa());

        if (ps.executeUpdate() > 0) {
            updatePlaceCategSoiree(conn, resa.getNbr_places(), resa.getId_cs());
            pdao.updateMontantPanier(conn, resa.getId_panier(), resa.getMontant());
            conn.commit();
            System.out.println("Resa billet success, committed");
        } else {
            conn.rollback();
            System.out.println("Resa billet failed, rolled back");
        }

        Date date_soiree = FestivalResaService.getSoireeDate(resa.getId_cs());
        resa.setDate_soiree(date_soiree);
        ps.close();
        ConnexionOracle.disconnect(conn);
        System.out.println(">>>>ResaDao renvoie " + resa);
        return resa;
    }

    @Override
    public void update(ReservationBillet bObject) throws SQLException {
        throw new SQLException("Operation not supported on this table");
    }

    @Override
    public void update(int id) throws SQLException {
        throw new SQLException("Operation not supported on this table");
    }

    @Override
    public ReservationBillet getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<ReservationBillet> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<ReservationBillet> getAll(int limit, int offset) throws SQLException {
        return null;
    }

    @Override
    public void destroy(ReservationBillet bObject) throws SQLException {
        throw new SQLException("Operation not supported on this table");
    }

    @Override
    public void destroy(int id) throws SQLException {
        throw new SQLException("Operation not supported on this table");
    }

    private int initPanier(String user_token) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PanierDao pdao = daoFactory.getPanierDao();

        Panier p = new Panier(1,0, PanierStatut.Traitement,
                0, new java.sql.Date(Calendar.getInstance().getTime().getTime())); //!\ ids should auto increment

        return pdao.save(p, user_token);
    }

    private synchronized void updatePlaceCategSoiree(Connection conn, int nbr_places, int id_cs) throws SQLException {
        String req = "update CATEGORIE_SOIREE set NBR_PLACE_DISPO = NBR_PLACE_DISPO - ? where ID_CS = ?";
        //Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        PreparedStatement ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setInt(i++, nbr_places);
        ps.setInt(i++, id_cs);
        ps.executeUpdate();
        ps.close();
    }
}
