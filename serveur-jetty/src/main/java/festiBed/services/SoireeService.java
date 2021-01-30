package festiBed.services;

import festiBed.models.festival.Soiree;
import festiBed.models.statut.SoireeStatut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoireeService {
    public static Soiree resultToSoiree(ResultSet rs) throws SQLException {
        Soiree soiree = new Soiree();

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            while(rs.next()) {
                soiree = new Soiree(
                        rs.getInt("id_soiree"),
                        rs.getDate("date_soiree"),
                        SoireeStatut.valueOf(rs.getString("statut")),
                        rs.getInt("id_festival")
                );
            }
        } else {
            soiree = null;
        }
        return soiree;
    }

    public static List<Soiree> resultToSoireeList(ResultSet rs) throws SQLException {
        List<Soiree> soirees = new ArrayList<>();

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            while(rs.next()) {
                soirees.add(new Soiree(
                        rs.getInt("id_soiree"),
                        rs.getDate("date_soiree"),
                        SoireeStatut.valueOf(rs.getString("statut")),
                        rs.getInt("id_festival")
                ));
            }
        } else {
            soirees = new ArrayList<>();
        }

        return soirees;
    }
}
