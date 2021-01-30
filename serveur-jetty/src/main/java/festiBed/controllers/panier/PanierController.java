package festiBed.controllers.panier;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.panier.PanierDao;
import festiBed.models.panier.Panier;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanierController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Get /api/panier");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("id_user");
        expect.add("id_panier");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        PanierDao pdao = daoFactory.getPanierDao();

        try {
            int id_user = Integer.parseInt(params.get("id_user"));
            int id_panier = Integer.parseInt(params.get("id_panier"));

            Panier panier = pdao.getByUser(id_user, id_panier);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(panier);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }
}
