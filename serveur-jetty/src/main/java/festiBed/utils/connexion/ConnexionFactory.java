/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package festiBed.utils.connexion;

import festiBed.utils.ConnexionOracle;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnexionFactory {
    private static ConnexionFactory connexionFactory = null;

    private ConnexionFactory() {}

    public static ConnexionFactory getInstance() {
		if (ConnexionFactory.connexionFactory == null) {
			synchronized (ConnexionOracle.class) {
				if (ConnexionFactory.connexionFactory == null) {
					ConnexionFactory.connexionFactory = new ConnexionFactory();
				}
			}
		}
		return ConnexionFactory.connexionFactory;
    }
    
    public Connection getOracleConnection() throws SQLException {
        return null;//ConnexionOracle.getConnexionOracle();
	}

	public Connection getMySQLConnection() throws Exception {
        throw new Exception("Database not supported yet");
    }
}
