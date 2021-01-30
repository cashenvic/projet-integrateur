/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package festiBed.utils;

import java.sql.*;
import java.util.HashMap;

public class ConnexionOracle {

    private static ConnexionOracle connexionOracle;
    private Connection dbConnection = null;

    private ConnexionOracle(){}

    public static ConnexionOracle getInstance() {
        if (connexionOracle == null) {
            connexionOracle = new ConnexionOracle();
        }
        return connexionOracle;
    }

    public Connection getConnexionOracle() throws SQLException {
        if (dbConnection == null) {
            synchronized (ConnexionOracle.class) {
                if (dbConnection == null) {
                    System.out.println("Setting connexion for Oracle DB");
                    HashMap<String, String> cred = ReadDBConfig.getCredentials("src/main/java/festiBed/config/config.xml");

                    //String conn_url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?useLegacyDatetimeCode=false&serverTimezone=Europe/Paris";
                    String conn_url = "jdbc:oracle:thin:@" + cred.get("hostname") + ":" + Integer.parseInt(cred.get("port")) + ":" + cred.get("dbname");
                    Connection dbConnection = null;

                    dbConnection = DriverManager.getConnection(conn_url, cred.get("username"), cred.get("password"));
                    setOracleSchema(dbConnection);
                    return dbConnection;
                }
            }
        }

        return dbConnection;
    }

    private static void setOracleSchema(Connection conn) throws SQLException {
        String req = "ALTER SESSION SET CURRENT_SCHEMA=XXX"; // TODO: Replace XXX by your schema name
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(req);
    }

    public static void disconnect(Connection dbConnection) throws SQLException{
        dbConnection.close();
        System.out.println("fermeture de la connexion");
    }

    public static void lockTableInMode(Connection conn, String table, DBLockMode mode) throws SQLException {
        String lockString = "lock table " + table + " in " + mode.getValue() + " mode";
        Statement stmt = conn.createStatement();
        stmt.execute(lockString);
    }
}
