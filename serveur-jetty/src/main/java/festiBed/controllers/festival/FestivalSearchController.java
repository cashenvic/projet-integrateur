package festiBed.controllers.festival;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.festival.FestivalDao;
import festiBed.models.festival.Festival;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FestivalSearchController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * GET
     * Route utilisée pour la recherche de festivals. Elle prend en paramètre
     *          -date de debut
 *              -la date de fin .
     *          -l'id du domaine,
     *          -la latitude
     *          -la longitude
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/festival/search");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //skip if there are missing params
        if (!this.isRequestParamsValid(params)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        FestivalDao fdao = daoFactory.getFestivalDao();

        String date_debut = params.get("date_debut");
        String date_fin = params.get("date_fin");;
        String id_domaine = params.get("id_domaine");
        String departement = params.get("departement");

        try {
            int limit = params.get("limit").isEmpty() ? 10 : Integer.parseInt(params.get("limit"));
            int offset = params.get("offset").isEmpty() ? 0 : Integer.parseInt(params.get("offset"));

            List<Festival> festivals = null;
            festivals = fdao.search(date_debut, date_fin, id_domaine, departement, limit, offset);

            if (festivals ==null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("\"message\": \"Aucun festival trouvé\"");
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(festivals);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }


    private boolean isRequestParamsValid(Map<String, String> params) {
        boolean valid = false;

        if (params.containsKey("date_debut") && params.containsKey("date_fin")
            && params.containsKey("departement") && params.containsKey("limit")
            && params.containsKey("offset") && params.containsKey("id_domaine")) {
            valid = true;
        }

        return valid;
    }
}
