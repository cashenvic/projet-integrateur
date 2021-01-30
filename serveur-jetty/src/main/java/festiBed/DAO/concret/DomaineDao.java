package festiBed.DAO.concret;

import festiBed.DAO.interfaces.Dao;
import festiBed.models.festival.Domaine;

import java.sql.SQLException;
import java.util.List;

public class DomaineDao implements Dao<Domaine> {

    @Override
    public int save(Domaine bObject) throws SQLException {
        return 0;
    }

    @Override
    public void update(Domaine bObject) throws SQLException {

    }

    @Override
    public void update(int id) throws SQLException {

    }

    @Override
    public Domaine getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Domaine> getAll() throws SQLException {

        return null;
    }

    @Override
    public List<Domaine> getAll(int limit, int offset) throws SQLException {
        return null;
    }

    @Override
    public void destroy(Domaine bObject) throws SQLException {

    }

    @Override
    public void destroy(int id) throws SQLException {

    }
}
