/*
 * Modified from org.apache.tuscany.samples.das.customer.CustomerDatabaseInitializer.
 * Only keep the Derby initialization, 
 * full version can get from Tuscany DAS samples.
 * 
 */
package pku.customer;

import org.apache.tuscany.das.rdb.config.Config;
import org.apache.tuscany.das.rdb.util.ConfigUtil;

import pku.databaseSetup.DerbySetup;

/**
 * 
 * @author Zhang Le
 *
 */
public class CustomerDatabaseInitializer {
    public static final String DERBY = "derby";

    private final Config config;
    
    private final String dbType;
    private final String dbURL;
    private final String user;
    private final String password;
    
    
    
    public CustomerDatabaseInitializer(String configFile) {
        this.config = ConfigUtil.loadConfig(this.getClass().getClassLoader().getResourceAsStream(configFile));

        if(config.getConnectionInfo().getConnectionProperties().getDriverClass().indexOf(DERBY) != -1) {
            dbType = DERBY;
        } else {
            dbType = null;
        }
            
        //get connection info from config
        dbURL = config.getConnectionInfo().getConnectionProperties().getDatabaseURL();
        user = config.getConnectionInfo().getConnectionProperties().getUserName();
        password = config.getConnectionInfo().getConnectionProperties().getPassword();
    }
    
    

    /**
     * If database is present connect to it and create necessary tables, procedures, data etc.
     * If database is not present create database and then create the above elements. Create database
     * is implemented for MySQL and Derby.
     */
    public void Initialize() {
        
        //display DB configuration iformation
        DisplayDatabaseConfiguration();
        
        //initialize DB
        try {
            if(dbType.equals(DERBY)){
                new DerbySetup(dbURL+"-"+user+"-"+password);
            }
                        
        } catch(Exception e){
            throw new RuntimeException("Error initializing database !", e);
        }
    }
    
    public void DisplayDatabaseConfiguration() {
        
        System.out.println("************* Initializing database *************");
        System.out.println("** DB type  : " + dbType );
        System.out.println("** Database : " + dbURL );
        System.out.println("** User     : " + user );
        System.out.println("** Password : " + password);
        System.out.println("************************************************");
    }
}
