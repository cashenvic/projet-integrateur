package festiBed.controllers.hebergement;

import festiBed.models.panier.ReservationBillet;
import festiBed.models.panier.ReservationLogement;
import festiBed.services.CampingResaService;
import festiBed.services.FestivalResaService;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CampingResaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Post /api/hebergement/reserver");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("dateArrivee");
        expect.add("dateDepart");
        expect.add("nbrEnfant");
        expect.add("nbrAdulte");
        expect.add("montant");
        expect.add("tokenSession");
        expect.add("logementid");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date_arriveeJava = format.parse(params.get("dateArrivee"));
            java.sql.Date date_arrivee = new java.sql.Date(date_arriveeJava.getTime());

            java.util.Date date_departJava= format.parse(params.get("dateDepart"));
            java.sql.Date date_depart = new java.sql.Date(date_departJava.getTime());

            int nbr_enfant = Integer.parseInt(params.get("nbrEnfant"));
            int nbr_adulte = Integer.parseInt(params.get("nbrAdulte"));
            double montant = Double.parseDouble(params.get("montant"));
            String user_token = params.get("tokenSession");
            int id_logement = Integer.parseInt(params.get("logementid"));

            ReservationLogement r = new ReservationLogement(date_arrivee, date_depart, nbr_enfant, nbr_adulte, montant, user_token, id_logement);
            r = CampingResaService.reserverLogment(r, user_token);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(r);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }
}
