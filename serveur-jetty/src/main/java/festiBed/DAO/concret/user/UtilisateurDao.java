package festiBed.DAO.concret.user;

import festiBed.models.statut.UtilisateurStatut;
import festiBed.models.utilisateurs.Utilisateur;
import festiBed.services.UtilisateurService;
import festiBed.utils.ConnexionOracle;

import java.sql.*;

public class UtilisateurDao {
    /**
     *
     * @param id_user
     * @param type
     * @return
     * @throws SQLException
     */
    public int getUserId(String session_token, String type) throws SQLException {
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        boolean valid = false;
        int returned_id = 0;

        String req = "select ID_USER from UTILISATEUR " +
                        "where TYPE = ? and TOKEN_SESSION = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int i = 1;
        ps.setString(i++, type);
        ps.setString(i++, session_token);
        rs = ps.executeQuery();

        while(rs.next()){
            returned_id = +rs.getInt("ID_USER");
        }

        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return returned_id;
    }

    public int save(Utilisateur u, String type) throws SQLException {
        int returned_id = 0;
        String req = "INSERT INTO UTILISATEUR" +
                "(TYPE, TOKEN_FIREBASE, TOKEN_SESSION, NOM, PRENOM, MAIL, STATUT)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        ps = conn.prepareStatement(req, new String[] { "ID_USER" });
        int i = 1;
        ps.setString(i++, u.getType());
        ps.setString(i++, u.getToken_firebase());
        ps.setString(i++, u.getToken_session());
        ps.setString(i++, u.getNom());
        ps.setString(i++, u.getPrenom());
        ps.setString(i++, u.getMail());
        ps.setString(i++, u.getStatut().getValue());

        rs = ps.executeQuery();
        rs = ps.getGeneratedKeys();
        if(rs != null && rs.next()) {
            returned_id = rs.getInt(1);
            System.out.println("passe dans genKeys with " + returned_id);
        }

        ps.close();
        System.out.println(">>>>UtilisateurDao.save renvoie " + returned_id);
        return returned_id;
    }

    public Utilisateur login(String token_firebase, String token_session, String nom, String prenom,
                         String type, String mail) throws SQLException {
        Utilisateur user = new Utilisateur();
        String req = "select * from UTILISATEUR" +
                "    where TOKEN_FIREBASE = ?";
               // "    and STATUT = ? and TYPE = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();
        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        int i = 1;
        ps.setString(i++, token_firebase);
//        ps.setString(i++, UtilisateurStatut.Actif.getValue());
//        ps.setString(i++, type);

        rs = ps.executeQuery();
        user = UtilisateurService.resultToUtilisateur(rs);
        System.out.print("this is the user found after login\t>>>");
        System.out.println(user);
        //if no user matching
        //if the user is null then create it and return the created one
        if (user == null) {
            int id_user= this.save(new Utilisateur(
                   0,
                    token_firebase,
                    token_session,
                    nom,
                    prenom,
                    type,
                    UtilisateurStatut.Actif,
                    mail
            ), UtilisateurStatut.Actif.getValue());
            //getUserById
            if (id_user != 0) {
                user = getUserById(id_user);
            }
        }

        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return user;
    }

    public Utilisateur getUserById(int id_user) throws SQLException {
        String req = "select * from UTILISATEUR\n" +
                "    where id_user = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();

        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, id_user);
        rs = ps.executeQuery();

        Utilisateur u = UtilisateurService.resultToUtilisateur(rs);
        ps.close();
        rs.close();
        return u;
    }

    public boolean isUserNeedRegister(Connection conn, String session_token) throws SQLException {
        String req = "select count(*) as c from UTILISATEUR where TOKEN_SESSION = ? and TOKEN_FIREBASE = 'not null'";
        PreparedStatement ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, session_token);
        ResultSet rs = ps.executeQuery();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt("c");
        }
        return count > 0;
    }

    public void convertUser(String session_token, String firebase_token,
                String nom, String prenom, String mail) throws SQLException {
        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();
        CallableStatement cstmt = conn.prepareCall("{call Associer_User_Panier(?, ?, ?, ?, ?)}");
        int i = 1;
        cstmt.setString(i++, session_token);
        cstmt.setString(i++, firebase_token);
        cstmt.setString(i++, nom);
        cstmt.setString(i++, prenom);
        cstmt.setString(i++, mail);

        cstmt.execute();
        cstmt.close();
    }
}
