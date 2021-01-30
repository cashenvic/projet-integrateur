package festiBed.controllers;

import festiBed.DAO.concret.festival.FestivalDao;
import festiBed.models.festival.Festival;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DomaineController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");

        Map<String, String> params = RequestUtils.getReqParams(request);
        List<Festival> festivals = null;
        int id = 0;
        FestivalDao fdao = new FestivalDao();

        System.out.print(">>>Get /api/festival");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //skip if there are missing params
        if (!this.isRequestParamsValid(params)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"invalid parameters\"");
            return;
        }

        if (params.containsKey("id")) {
            try {
                id = Integer.parseInt(params.get("id"));
                Festival f = fdao.getById(id);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(f.toString());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("\"error\":" + "\"" + e + "\"");
                e.printStackTrace();
                return;
            }

        } else {
            try {
                int limit = Integer.parseInt(params.get("limit"));
                int offset = Integer.parseInt(params.get("offset"));
                festivals = fdao.getAll(limit, offset);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(festivals);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("\"error\":" + "\"" + e + "\"");
                e.printStackTrace();
                return;
            }
        }
    }

    private boolean isRequestParamsValid(Map<String, String> params) {
        boolean valid = false;

        if (params.containsKey("limit") && params.containsKey("offset")) {
            valid = true;
        }

        return  valid;
    }
}