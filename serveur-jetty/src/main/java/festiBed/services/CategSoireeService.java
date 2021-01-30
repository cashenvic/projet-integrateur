package festiBed.services;

import festiBed.models.festival.CategorieSoiree;
import festiBed.models.statut.SoireeStatut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategSoireeService {
    public static CategorieSoiree resultToCategSoiree(ResultSet rs) throws SQLException {
        CategorieSoiree categorieSoiree = new CategorieSoiree();

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            while(rs.next()) {
                categorieSoiree = new CategorieSoiree(
                        rs.getInt("id_cs"),
                        rs.getInt("nbr_place_dispo"),
                        rs.getInt("nbr_place"),
                        rs.getDouble("tarif"),
                        rs.getInt("id_soiree"),
                        SoireeStatut.valueOf(rs.getString("statut"))
                );
            }
        } else {
            categorieSoiree = null;
        }
        return categorieSoiree;
    }

    public static List<CategorieSoiree> resultToCategSoireeList(ResultSet rs) throws SQLException {
        List<CategorieSoiree> categorieSoiree = new ArrayList<>();

        rs.last();
        if (rs.getRow() > 0) {
            //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
            rs.beforeFirst();
            while(rs.next()) {
                categorieSoiree.add(new CategorieSoiree(
                        rs.getInt("id_cs"),
                        rs.getInt("nbr_place_dispo"),
                        rs.getInt("nbr_place"),
                        rs.getDouble("tarif"),
                        rs.getInt("id_soiree"),
                        SoireeStatut.valueOf(rs.getString("statut"))
                ));
            }
        } else {
            categorieSoiree = new ArrayList<>();
        }

        return categorieSoiree;
    }
}
