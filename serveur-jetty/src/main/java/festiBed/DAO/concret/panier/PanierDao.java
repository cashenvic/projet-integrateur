package festiBed.DAO.concret.panier;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.user.UtilisateurDao;
import festiBed.DAO.interfaces.Dao;
import festiBed.models.panier.Panier;
import festiBed.models.utilisateurs.UserTypes;
import festiBed.models.utilisateurs.Utilisateur;
import festiBed.services.PanierService;
import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PanierDao implements Dao<Panier> {
    @Override
    public int save(Panier panier) throws SQLException {
        return 0;
    }

    public int save(Panier panier, String user_token) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returned_id = 0;
        String requette = "INSERT INTO PANIER" +
                " (ID_USER, MONTANT_TOTAL, DATE_CREATION, STATUT)" +
                " VALUES(?, ?, sysdate, ?)";

        DAOFactory daoFactory = DAOFactory.getInstance();
        UtilisateurDao udao = daoFactory.getUtilisateurDao();
        //check if the user exists otherwise create it the continue
        int user_id = udao.getUserId(user_token, UserTypes.Client.getValue());
        if (user_id == 0) {
            panier.setId_user(initUser(user_token));
        } else {
            panier.setId_user(user_id);
        }

        ps = conn.prepareStatement(requette, new String[] { "ID_PANIER" });

        int i = 1;
        ps.setInt(i++, panier.getId_user());
        ps.setDouble(i++, panier.getMontant_total());
        ps.setString(i++, panier.getStatus().getValue());
        rs = ps.executeQuery();

        if (ps.getGeneratedKeys().next()) {
            returned_id = ps.getGeneratedKeys().getInt(1);
        }

        rs.close();
        ps.close();
        ConnexionOracle.disconnect(conn);

        System.out.println(">>>>PanierDao renvoie " + returned_id);
        return returned_id;
    }

    @Override
    public void update(Panier bObject) throws SQLException {

    }

    @Override
    public void update(int id) throws SQLException {

    }

    @Override
    public Panier getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Panier> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Panier> getAll(int limit, int offset) throws SQLException {
        return null;
    }

    @Override
    public void destroy(Panier bObject) throws SQLException {

    }

    @Override
    public void destroy(int id) throws SQLException {

    }

    public Panier getByUser(int id_user, int id_panier) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        Panier panier = new Panier();
        String requette = "select * from panier " +
                "where id_panier = ? and id_user = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int i = 1;
        ps.setInt(i++, id_panier);
        ps.setInt(i++, id_user);
        rs = ps.executeQuery();
        panier = PanierService.resultToPanier(rs);

        ps.close();
        rs.close();
        return  panier;
    }

    /**
     * Check if the panier which id is given is in the status passed in param
     * @param id
     * @param statut
     * @return
     * @throws SQLException
     */
    public int isPanierExisting(String token_session, String statut) throws SQLException {
        int id_panier = 0;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String requette = "select  ID_PANIER as t from (select * from UTILISATEUR u where TOKEN_SESSION = ?) u " +
                " join PANIER p on p.ID_USER = u.ID_USER" +
                " where p.STATUT = ?";

        ps = conn.prepareStatement(requette, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setString(i++, token_session);
        ps.setString(i++, statut);
        rs = ps.executeQuery();
        if (rs.next()) {
            id_panier = rs.getInt("t");
        }

        rs.close();
        ps.close();

        System.out.println(">>>>id panier renvoyé " + id_panier);
        return id_panier;
    }

    public boolean isPanierValid(Connection conn, int id_panier) throws SQLException {
        boolean valid = false;
        //check if the panier at least one fetival and one hebergement on it
        String req = "select distinct count(*) as c from PANIER join RESERVATION_BILLET rb" +
                "    on panier.ID_PANIER = rb.ID_PANIER join RESERVATION_LOGEMENT rl" +
                "    on rl.ID_PANIER = PANIER.ID_PANIER where ID_PANIER = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(req);
        ps.setInt(1, id_panier);
        rs = ps.executeQuery();
        if (rs.next()) {
            valid = rs.getInt("c") > 0;
        }

        ps.close();
        rs.close();
        return valid;
    }

    private int initUser(String user_token) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UtilisateurDao udao = daoFactory.getUtilisateurDao();
        Utilisateur u = new Utilisateur();
        u.setToken_session(user_token);

        return udao.save(u, UserTypes.Client.getValue());
    }

    public synchronized void updateMontantPanier(Connection conn, int id_panier, Double montant) throws SQLException {
       // boolean ok = false;
        String req = "update panier set MONTANT_TOTAL = MONTANT_TOTAL + ? where ID_PANIER = ?";
        //Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        PreparedStatement ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setDouble(i++, montant);
        ps.setInt(i++, id_panier);
        ps.executeUpdate();
        //to check if the operation successed
//        rs.last();
//        if(rs.getRow() > 0) {
//            ok = true;
//        }
        ps.close();
        //ConnexionOracle.disconnect(conn);
        //return ok;
    }
}
