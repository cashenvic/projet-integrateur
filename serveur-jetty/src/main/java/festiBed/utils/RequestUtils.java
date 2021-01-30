package festiBed.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtils {
    /**
     * Used to get all the params of a request in a Map and return it
     * @param request
     * @return
     */
    public static Map<String, String> getReqParams(HttpServletRequest request) {

        Enumeration<String> P = request.getParameterNames();
        HashMap<String, String> parametres = new HashMap<>();

        while (P.hasMoreElements()) {
            String p = P.nextElement();
            parametres.put(p, request.getParameter((String) p));
        }

        return parametres;
    }

    /**
     * Adds header values to make the route callable from another application
     * @param response Response object that will the sent to the client
     * @return
     */
    public static HttpServletResponse addCorsHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");

        return response;
    }

    public static boolean isRequestParamsValid(Map<String, String> params, List<String> expect) {
        boolean valid = true;

        for (String s: expect) {
            if (!params.containsKey(s) || params.get(s).isEmpty()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public static int getIdFromStatement(Statement statement) throws SQLException {
        ResultSet rs = statement.getGeneratedKeys();
        int returned_id = 0;
        if(rs != null && rs.next()) {
            returned_id = rs.getInt(1);
        }
        return returned_id;
    }
}