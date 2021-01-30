package festiBed.DAO;

import festiBed.DAO.concret.festival.FestivalDao;
import festiBed.DAO.concret.festival.FestivalResaDao;
import festiBed.DAO.concret.hebergement.CampingDao;
import festiBed.DAO.concret.hebergement.CampingResaDao;
import festiBed.DAO.concret.panier.PanierDao;
import festiBed.DAO.concret.panier.ValidationPanierDao;
import festiBed.DAO.concret.user.UtilisateurDao;

//import festiBed.DAO.concret.panier.ValidationPanierDao;

/**
 * DAO factory for business classes accesses to the db only
 * If we had many datasource may be it was be interesting to have many dao for the various sources
 */
public class DAOFactory {
    private static DAOFactory daoFactory = null;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (DAOFactory.daoFactory == null) {
            synchronized (DAOFactory.class) {
                if(DAOFactory.daoFactory == null) {
                    DAOFactory.daoFactory = new DAOFactory();
                }
            }
        }

        return DAOFactory.daoFactory;
    }

    public FestivalDao getFestivalDao(){
        return new FestivalDao();
    }

    public FestivalResaDao getFestivalResaDao() {
        return new FestivalResaDao();
    }

    public PanierDao getPanierDao() {
        return new PanierDao();
    }

    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDao();
    }

    public CampingDao getCampingDao() {
        return new CampingDao();
    }

    public CampingResaDao getCampingResaDao() {
        return new CampingResaDao();
    }

    public ValidationPanierDao getValidationPanierDao() {
        return new ValidationPanierDao();
    }
}
