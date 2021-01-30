package festiBed.services;

import festiBed.models.panier.Panier;
import festiBed.models.statut.PanierStatut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierService {
    public static Panier resultToPanier(ResultSet rs) {
        Panier panier = new Panier();

        try {
            rs.last();
            if (rs.getRow() > 0) {
                //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
                rs.beforeFirst();
                while(rs.next()) {
                    panier = new Panier(
                            rs.getInt("id_panier"),
                            rs.getDouble("montant_total"),
                            PanierStatut.valueOf(rs.getString("statut")),
                            rs.getInt("id_user"),
                            rs.getDate("date_creation")
                    );
                }
            } else {
                panier = null;
            }

        } catch (SQLException e) {
            System.out.println("/!\\ /!\\error during creating panier from resultset");
            e.printStackTrace();
            panier = null;
        }

        return panier;
    }

    public static List<Panier> resultToPanierList(ResultSet rs) {
        List<Panier> paniers = new ArrayList<>();
        Panier p = null;

        try {
            rs.last();
            if (rs.getRow() > 0) {
                //replacer le curseur au debut du resultSet pour pouvoir le parcourir en entier
                rs.beforeFirst();
                while(rs.next()) {
                    paniers.add(resultToPanier(rs));
                }
            } else {
                paniers = new ArrayList<>();
            }

        } catch (SQLException e) {
            System.out.println("/!\\ /!\\error during creating panier list from resultset");
            e.printStackTrace();
            paniers = new ArrayList<>();
        }

        return paniers;
    }
}
