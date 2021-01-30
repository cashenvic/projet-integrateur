package festiBed.controllers;

import festiBed.DAO.DAOFactory;
import festiBed.DAO.concret.user.UtilisateurDao;
import festiBed.models.utilisateurs.Utilisateur;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Post /api/login");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //skip if there are missing params
        List<String> expect = new ArrayList<>();
        expect.add("nom");
        expect.add("prenom");
        expect.add("mail");
        //expect.add("password");
        expect.add("type");
        expect.add("token_firebase");
        expect.add("token_session");
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        UtilisateurDao udao = daoFactory.getUtilisateurDao();

        try {
            String nom = params.get("nom");
            String prenom = params.get("prenom");
            String mail = params.get("mail");
            String token_firebase = params.get("token_firebase");
            String token_session = params.get("token_session");
            String type = params.get("type");

             //new Utilisateur(0, token_firebase, token_session, nom, prenom, type, UtilisateurStatut.ACTIF, mail);
            Utilisateur u = udao.login(token_firebase, token_session, nom, prenom,
                    type, mail);

            if (u == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("\"message\": \"impossible de se connecter\"");
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(u.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }
}
