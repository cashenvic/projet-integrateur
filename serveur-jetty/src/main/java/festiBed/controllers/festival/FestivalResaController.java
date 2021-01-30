package festiBed.controllers.festival;

import festiBed.models.panier.ReservationBillet;
import festiBed.services.FestivalResaService;
import festiBed.utils.RequestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FestivalResaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils.addCorsHeaders(response);

        Map<String, String> params = RequestUtils.getReqParams(request);

        System.out.print(">>>Post /api/festival/reserver");
        System.out.print("\tWith params: ");
        System.out.println(params.toString());

        //make a list of param names to check before continue
        List<String> expect = new ArrayList<>();
        expect.add("session_token");
        expect.add("id_cs");
        expect.add("nbr_places");
        expect.add("montant");

        //skip if there are missing params
        if (!RequestUtils.isRequestParamsValid(params, expect)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("\"error\": \"missing parameters\"");
            return;
        }

        try {
            String user_token = params.get("session_token");
            int id_cs = Integer.parseInt(params.get("id_cs"));
            int nbr_places = Integer.parseInt(params.get("nbr_places"));
            double montant = Double.parseDouble(params.get("montant"));

            ReservationBillet r = new ReservationBillet(0, id_cs, nbr_places, montant);
            r = FestivalResaService.reserverFestival(r, user_token);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(r);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("\"error\":" + "\"" + e + "\"");
        }
    }


    private boolean isRequestParamsValid(Map<String, String> params) {
        boolean valid = false;

        if (params.containsKey("id_panier") && params.containsKey("id_cs")
                && params.containsKey("nbr_places") && params.containsKey("montant")) {
            valid = true;
        }

        return valid;
    }
}
