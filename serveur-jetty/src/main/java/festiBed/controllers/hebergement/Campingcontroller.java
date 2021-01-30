package festiBed.controllers.hebergement;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.hebergement.CampingDao;
import festiBed.models.hebergement.Camping;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Campingcontroller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/hebergement");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("limit");
        expect.add("offset");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        CampingDao cdao = DAOFactory.getInstance().getCampingDao();

        int id = 0;

        if (params.containsKey("id")) {
            try {
                id = Integer.parseInt(params.get("id"));
                Camping c = cdao.getCampingById(id);

                if (c == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("\"message\": \"Aucun camping trouvé\"");
                    return;
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(c.toString());
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("\"error\":" + "\"" + e + "\"");
            }

        } else {
            try {
                int limit = params.get("limit").isEmpty() ? 10 : Integer.parseInt(params.get("limit"));
                int offset = params.get("offset").isEmpty() ? 0 : Integer.parseInt(params.get("offset"));

                List<Camping> campings = null;
                campings = cdao.getAllCampings(limit, offset);

                if (campings == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("\"message\": \"Aucun hebergement trouvé\"");
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
    }
}
