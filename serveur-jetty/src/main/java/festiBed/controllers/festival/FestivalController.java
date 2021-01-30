package festiBed.controllers.festival;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.interfaces.Dao;
import festiBed.models.festival.Festival;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FestivalController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/festival");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("offset");
        expect.add("limit");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        Dao<Festival> fdao = daoFactory.getFestivalDao();

        int id = 0;

        if (params.containsKey("id")) {
            try {
                id = Integer.parseInt(params.get("id"));
                Festival f = fdao.getById(id);

                if (f == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("\"message\": \"Aucun festival trouvé\"");
                    return;
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(f.toString());
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("\"error\":" + "\"" + e + "\"");
            }

        } else {
            try {
                int limit = params.get("limit").isEmpty() ? 10 : Integer.parseInt(params.get("limit"));
                int offset = params.get("offset").isEmpty() ? 0 : Integer.parseInt(params.get("offset"));

                List<Festival> festivals = null;
                festivals = fdao.getAll(limit, offset);

                if (festivals == null) {
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
    }
}