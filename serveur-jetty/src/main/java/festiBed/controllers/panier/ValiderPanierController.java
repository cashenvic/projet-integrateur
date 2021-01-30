package festiBed.controllers.panier;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.panier.ValidationPanierDao;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValiderPanierController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Post /api/panier/validation");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("firebase_token");
        expect.add("session_token");
        expect.add("prenom");
        expect.add("mail");
        expect.add("nom");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        try {
            String session_token = params.get("session_token");
            String firebase_token = params.get("firebase_token");
            String prenom = params.get("prenom");
            String mail = params.get("mail");
            String nom = params.get("nom");

            ValidationPanierDao vdao = DAOFactory.getInstance().getValidationPanierDao();
            int ok = vdao.validerPanier(session_token, firebase_token, nom, prenom, mail);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("\"success\": \"" + ok + "\"");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }
}
