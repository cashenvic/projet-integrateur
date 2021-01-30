package festiBed.services;

import festiBed.models.Localisation;
import festiBed.models.hebergement.Camping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampingService {
    public static Camping resultToCamping(ResultSet rs) throws SQLException {
        Camping camping = new Camping();
        Localisation l = null;

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            while(rs.next()) {
                l = new Localisation(rs.getFloat("longitude"),
                        rs.getFloat("latitude"),
                        rs.getString("nom_departement"),
                        rs.getString("code_postal"));
                camping = new Camping(
                        rs.getInt("id_hebergement"),
                        l,
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("site_web"),
                        rs.getString("telephone"),
                        rs.getDouble("note_global"),
                        rs.getDate("date_creation"),
                        rs.getInt("nombre_etoile"),
                        rs.getString("type"),
                        rs.getDouble("tarif"),
                        rs.getInt("id_logement"),
                        rs.getInt("capacite")
                );
            }
        } else {
            camping = null;
        }

        return camping;
    }

    public static List<Camping> searchResultToCampingList(ResultSet rs) throws SQLException {
        List<Camping> campings = new ArrayList<>();
        Camping p = null;
        Localisation l = null;
        try{
            rs.last();
            if (rs.getRow() > 0) {
                //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
                rs.beforeFirst();
                while(rs.next()) {
                    l = new Localisation(rs.getFloat("longitude"),
                            rs.getFloat("latitude"),
                            rs.getString("nom_departement"),
                            rs.getString("code_postal"));
                    campings.add(new Camping(
                            rs.getInt("id_hebergement"),
                            l,
                            rs.getString("nom"),
                            rs.getString("adresse"),
                            rs.getString("email"),
                            rs.getString("site_web"),
                            rs.getString("telephone"),
                            rs.getDouble("note_global"),
                            rs.getDate("date_creation"),
                            rs.getInt("nombre_etoile"),
                            rs.getString("type"),
                            rs.getDouble("tarif"),
                            rs.getInt("id_logement"),
                            rs.getInt("capacite")
                    ));
                }
            } else {
                campings = new ArrayList<>();
                System.out.println("YaRIen");
            }
        }   catch (SQLException e) {
            System.out.println("/!\\ /!\\error during creating festival from resultset");
            e.printStackTrace();
            campings = new ArrayList<>();
        }

        return campings;
    }

}
