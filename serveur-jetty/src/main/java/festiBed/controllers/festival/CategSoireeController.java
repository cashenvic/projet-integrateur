package festiBed.controllers.festival;

import festiBed.DAO.concret.CategSoireeDao;
import festiBed.models.festival.CategorieSoiree;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategSoireeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/festival/soiree/categorie");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("id_soiree");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        CategSoireeDao cdao = new CategSoireeDao();

        try {
            int id_soiree = Integer.parseInt(params.get("id_soiree"));
            List<CategorieSoiree> categorieSoirees = cdao.getCategSoireeBySoireeId(id_soiree);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(categorieSoirees);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }
}
