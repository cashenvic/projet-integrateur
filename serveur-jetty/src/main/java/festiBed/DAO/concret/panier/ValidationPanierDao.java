package festiBed.DAO.concret.panier;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.user.UtilisateurDao;
import festiBed.models.utilisateurs.UserTypes;
import festiBed.utils.ConnexionOracle;

import java.sql.*;

public class ValidationPanierDao {
    public int validerPanier(String session_token, String firebase_token,
                                 String nom, String prenom, String mail) throws SQLException {
        boolean ok = false;

        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        conn.setAutoCommit(false);
        //check if the user is registered
        //if he has no session token then skip
        UtilisateurDao udao = DAOFactory.getInstance().getUtilisateurDao();
        if (udao.getUserId(session_token, UserTypes.Client.getValue()) == 0) {
            return 0;
        }
        //si pas de token firebase
        if (udao.isUserNeedRegister(conn, session_token)) {
            System.out.println("User need to register. Calling convertUser()");
            udao.convertUser(session_token, firebase_token, nom, prenom, mail);
        }

        /**
         * on regarde si l'user du panier a un token session
         * et pas un token firebase
         * Si pas de token firebase on associe
         * puis on update le panier
 */
        CallableStatement cstmt = conn.prepareCall("{call Payer_Panier(?, ?)}");
        //int i = 1;
        cstmt.registerOutParameter(2, Types.INTEGER);
        cstmt.setString(1, session_token);
        cstmt.execute();
        int id_panier = cstmt.getInt(2);
        if (id_panier > 0) {
            conn.commit();
        }
        System.out.println("\nHere is the registered panier id " + id_panier);
        cstmt.close();

        ConnexionOracle.disconnect(conn);
        return id_panier;
    }

    public boolean annulerPanier(String session_token, int id_panier) throws SQLException {
        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        String places_resa_billet = "select rb.NBR_PLACEs as nbp " +
                "from RESERVATION_BILLET rb, PANIER p, UTILISATEUR u " +
                "where rb.ID_PANIER = p.ID_PANIER and p.ID_PANIER = ? " +
                "  and p.ID_USER = u.ID_USER and u.TOKEN_SESSION = ? " +
                "    and p.STATUT = 'Traitement'";
        String update_categ_s = "update" +
                "    CATEGORIE_SOIREE cs" +
                " set cs.NBR_PLACE_DISPO = cs.NBR_PLACE_DISPO + ?";

        String update_panier = "update panier set STATUT = 'Annuler' " +
                "    where ID_PANIER = (select p.ID_PANIER from PANIER p, UTILISATEUR u" +
                "        where p.ID_USER = u.ID_USER and u.TOKEN_SESSION = ?" +
                "        and p.STATUT = 'Traitement')";
        int nbr_places_fes = 0;
        int nbr_places_log = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        //get nbr_place of resa billet
        conn.setAutoCommit(false);
        ps = conn.prepareStatement(places_resa_billet);
        ps.setInt(1, id_panier);
        ps.setString(2, session_token);
        rs = ps.executeQuery();
        if (rs.next()) {
            nbr_places_fes = rs.getInt("nbp");

            if(nbr_places_fes > 0) {
                ps = conn.prepareStatement(update_categ_s);
                ps.setInt(1, nbr_places_fes);
                int i = ps.executeUpdate();

                if(i > 0) {
                    ps = conn.prepareStatement(update_panier);
                    ps.setString(1, session_token);
                    int j = ps.executeUpdate();

                    if (j > 0) {
                        conn.commit();
                        ConnexionOracle.disconnect(conn);
                        return true;
                    }
                }
            }
        }

        conn.rollback();
        ConnexionOracle.disconnect(conn);
        return false;
    }

    private String getInfoItemsPanier(int id_panier) {
        String req = "";

        return null;
    }
}
