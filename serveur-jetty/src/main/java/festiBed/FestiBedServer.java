package festiBed;

import festiBed.controllers.LoginController;
import festiBed.controllers.festival.*;
import festiBed.controllers.hebergement.CampingResaController;
import festiBed.controllers.hebergement.CampingSearchController;
import festiBed.controllers.hebergement.Campingcontroller;
import festiBed.controllers.panier.AnnulerPanierController;
import festiBed.controllers.panier.PanierController;
import festiBed.controllers.panier.ValiderPanierController;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServlet;

//import festiBed.controllers.panier.ValiderPanierController;


public class FestiBedServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Server server;

    void start() throws Exception {
        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
        servletHandler.addServletWithMapping(ClientAuthentificationServlet.class, "/api/authentification");
        servletHandler.addServletWithMapping(LoginController.class, "/api/login");

        //festival routes
        servletHandler.addServletWithMapping(FestivalController.class, "/api/festival");
        servletHandler.addServletWithMapping(FestivalSearchController.class, "/api/festival/search");
        servletHandler.addServletWithMapping(FestivalResaController.class, "/api/festival/reserver");
        servletHandler.addServletWithMapping(SoireeController.class, "/api/festival/soiree");
        servletHandler.addServletWithMapping(CategSoireeController.class, "/api/festival/soiree/categorie");

        //camping
        servletHandler.addServletWithMapping(Campingcontroller.class, "/api/hebergement");
        servletHandler.addServletWithMapping(CampingResaController.class, "/api/hebergement/reserver");
        servletHandler.addServletWithMapping(CampingSearchController.class, "/api/hebergement/search");


        servletHandler.addServletWithMapping(ValiderPanierController.class, "/api/panier/validation");
        servletHandler.addServletWithMapping(AnnulerPanierController.class, "/api/panier/annuler");

        //domaine routes
        //servletHandler.addServletWithMapping(DomaineController.class, "/api/domaine/");

        //panier routes
        servletHandler.addServletWithMapping(PanierController.class, "/api/panier");

        server.start();
    }

    void stop() throws Exception {
        System.out.println("Server stop");
    	server.stop();
    }

	public static void main(String[] args) throws Exception {
		FestiBedServer server = new FestiBedServer();

		server.start();
	}

}
