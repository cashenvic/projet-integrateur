package festiBed.services;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.hebergement.CampingResaDao;
import festiBed.models.panier.ReservationLogement;

import java.sql.SQLException;

public class CampingResaService {
    public  static ReservationLogement reserverLogment(ReservationLogement resa, String user_token) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        CampingResaDao campingResaDao = daoFactory.getCampingResaDao();

        resa = campingResaDao.save(resa, user_token);
        return resa;
    }
}
