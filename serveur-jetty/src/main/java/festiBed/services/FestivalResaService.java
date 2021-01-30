package festiBed.services;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.festival.FestivalResaDao;
import festiBed.models.panier.ReservationBillet;
import festiBed.utils.ConnexionOracle;

import java.sql.*;

public class FestivalResaService {
    public static ReservationBillet reserverFestival(ReservationBillet resa, String user_token) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FestivalResaDao frdao = daoFactory.getFestivalResaDao();

        //first save the resa
        resa = frdao.save(resa, user_token);
        //then update the festival
        return resa;
    }

    public static Date getSoireeDate(int id_cs) throws SQLException {
        Date date_soiree = null;
        String req = "select distinct DATE_SOIREE from RESERVATION_BILLET r " +
                "    join CATEGORIE_SOIREE c on r.ID_CS = r.ID_CS " +
                "    join soiree s on s.ID_SOIREE = c.ID_SOIREE where c.ID_CS = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = ConnexionOracle.getInstance().getConnexionOracle();

        ps = conn.prepareStatement(req);
        ps.setInt(1, id_cs);
        rs = ps.executeQuery();

        if (rs.next()) {
            date_soiree = rs.getDate("DATE_SOIREE");
        }
        return date_soiree;
    }
}
