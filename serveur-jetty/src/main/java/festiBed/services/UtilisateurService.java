package festiBed.services;

import festiBed.models.statut.UtilisateurStatut;
import festiBed.models.utilisateurs.Utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurService {
    /**
     * Maps a PreparedStatement with inited with params:
     *      ResultSet.TYPE_SCROLL_INSENSITIVE
     *      ResultSet.CONCUR_READ_ONLY
     * execution result to Utilisateur
     * @param rs
     * @return
     */
    public  static Utilisateur resultToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            if(rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("token_firebase"),
                        rs.getString("token_session"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("type"),
                        //UtilisateurStatut.valueOf(rs.getString("statut")),
                        UtilisateurStatut.valueOf("Actif"), //TODO: fix db status conversion
                        rs.getString("mail")
                );
            }
        } else {
            System.out.println("No user to convert as result set is empty");
            utilisateur = null;
        }

        return utilisateur;
    }
}
