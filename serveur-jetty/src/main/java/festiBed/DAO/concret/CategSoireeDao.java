package festiBed.DAO.concret;

import festiBed.models.festival.CategorieSoiree;
import festiBed.services.CategSoireeService;
import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategSoireeDao {
    public List<CategorieSoiree> getCategSoireeBySoireeId(int id_soiree) throws SQLException {
        String req = "select * from CATEGORIE_SOIREE where ID_SOIREE = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();

        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, id_soiree);
        rs = ps.executeQuery();

        List<CategorieSoiree> categorieSoirees = CategSoireeService.resultToCategSoireeList(rs);
        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return categorieSoirees;
    }
}
