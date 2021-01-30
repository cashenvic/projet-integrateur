package festiBed.services;

import festiBed.models.festival.Festival;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * festivalService
 */
public class FestivalService {

    public static Festival resultToFestival(ResultSet rs) {
        Festival festival = new Festival();

        try {
            rs.last();
            if (rs.getRow() > 0) {
                //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
                rs.beforeFirst();
                while(rs.next()) {
                    festival = new Festival(
                        Integer.parseInt(rs.getString("id_festival")),
                        rs.getString("NOM"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("DATE_DEBUT"),
                        rs.getDate("DATE_FIN"),
                        rs.getString("SITE_WEB"),
                        Double.parseDouble(rs.getString("NOTE_GLOBAL")),
                        rs.getDate("DATE_CREATION"),
                        rs.getString("STATUT"),
                        Double.parseDouble(rs.getString("latitude")),
                        Double.parseDouble(rs.getString("longitude"))
                    );
                }
            } else {
                festival = null;
            }

        } catch (SQLException e) {
            System.out.println("/!\\ /!\\error during creating festival from resultset");
            e.printStackTrace();
            festival = null;
        }
        
        return festival;
    }

    public static List<Festival> resultToFestivalList(ResultSet rs) {
        List<Festival> festivals = new ArrayList<>();
        Festival f = null;

        try {
            rs.last();
            if (rs.getRow() > 0) {
                //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
                rs.beforeFirst();
                while(rs.next()) {
                    f = new Festival(
                            rs.getInt("ID_FESTIVAL"),
                            rs.getString("NOM"),
                            rs.getString("DESCRIPTION"),
                            rs.getDate("DATE_DEBUT"),
                            rs.getDate("DATE_FIN"),
                            rs.getString("SITE_WEB"),
                            Double.parseDouble(rs.getString("NOTE_GLOBAL")),
                            rs.getDate("DATE_CREATION"),
                            rs.getString("STATUT"),
                            Double.parseDouble(rs.getString("latitude")),
                            Double.parseDouble(rs.getString("longitude"))
                    );
                    festivals.add(f);
                }
            } else {
                festivals = new ArrayList<>();
            }

        } catch (SQLException e) {
            System.out.println("/!\\ /!\\error during creating festival from resultset");
            e.printStackTrace();
            festivals = new ArrayList<>();
        }

        return festivals;
    }
}