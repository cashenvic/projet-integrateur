package festiBed.DAO.concret;

import festiBed.models.festival.Soiree;
import festiBed.services.SoireeService;
import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SoireeDao {
    public List<Soiree> getSoireesByFetivalId(int id_festival) throws SQLException {
        String req = "select * from soiree where ID_FESTIVAL = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnexionOracle connexionOracle = ConnexionOracle.getInstance();
        Connection conn = connexionOracle.getConnexionOracle();

        ps = conn.prepareStatement(req, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, id_festival);
        rs = ps.executeQuery();

        List<Soiree> soirees = SoireeService.resultToSoireeList(rs);
        ps.close();
        rs.close();
        ConnexionOracle.disconnect(conn);
        return soirees;
    }
}
