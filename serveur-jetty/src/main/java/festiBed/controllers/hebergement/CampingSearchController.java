package festiBed.controllers.hebergement;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.hebergement.CampingDao;
import festiBed.models.hebergement.Camping;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CampingSearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/hebergement/search");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //skip if there are missing params
        if (!this.isRequestParamsValid(params)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        CampingDao cdao = daoFactory.getCampingDao();

        String date_arrivee = params.get("dateArrivee");
        String date_depart = params.get("dateDepart");
        String type = params.get("type");
        String nomDep = params.get("nomDepartement");
        int limit = 10;
        int offset = 0;

        try {
            limit = Integer.parseInt(params.get("limit"));
            offset = Integer.parseInt(params.get("offset"));
            List<Camping> campings = null;
            campings = cdao.search(date_arrivee,date_depart, type, nomDep, limit, offset);

            if (campings ==null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("\"message\": \"Aucun campings trouvé\"");
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(campings);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }

    private boolean isRequestParamsValid(Map<String, String> params) {
        boolean valid = false;

        if (params.containsKey("dateArrivee") && params.containsKey("dateDepart")
                && params.containsKey("type") && params.containsKey("nomDepartement")
                && params.containsKey("limit") && params.containsKey("offset")) {
            valid = true;
        }

        return valid;
    }
}
