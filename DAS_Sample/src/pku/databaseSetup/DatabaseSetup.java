/*
 * Modified from org.apache.tuscany.samples.das.databaseSetup.DatabaseSetup.
 */
package pku.databaseSetup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

public class DatabaseSetup{
    protected Statement s;
    protected String platformName = "Not initialized";
    protected String driverName = "Not initialized";
    protected String databaseURL = "Not initialized";
    protected String databaseName = null; 
    protected String userName;
    protected String password;
    
    // Data Types
    public static final String stringType = "VARCHAR";
    public static final String integerType = "INT";
    public static final String timestampType = "TIMESTAMP";
    public static final String floatType = "FLOAT";
    public static final String decimalType = "DECIMAL";
    protected Connection connection;
    
    //database types
    public static final String DERBY = "derby";
    
    public DatabaseSetup(String databaseInfo){
    	StringTokenizer strTok = new StringTokenizer(databaseInfo, "-");
    	
    	if(strTok.hasMoreTokens()){
    		databaseName =  strTok.nextToken();
    	}
    	
    	if(strTok.hasMoreTokens()){
    		userName = strTok.nextToken();
    	}
    	
    	if(strTok.hasMoreTokens()){
    		password = strTok.nextToken();
    	}
    	
        initConnectionProtocol(databaseName);
        initConnection();
        try{
        	this.setUp();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    protected void initConnectionProtocol( String databaseName){
    }

    private void initConnection() {

        try {
            Class.forName(driverName).newInstance();
//            Properties props = new Properties();
            //System.out.println("platform name:"+this.platformName);
            
            if(this.platformName.equals(DERBY)){
                if (userName != null) {
                	//System.out.println("derby trying for "+userName+","+password+","+databaseURL);
                    connection = DriverManager.getConnection(databaseURL, userName, password);
                } else {
                  	//System.out.println("derby trying for "+databaseURL);                                  	
                    connection = DriverManager.getConnection(databaseURL);
                }            	
            }

            connection.setAutoCommit(true);
        } catch (SQLException e) {
        	e.printStackTrace();
            if (e.getNextException() != null) {
                e.getNextException().printStackTrace();
            } else {
                e.printStackTrace();
            }

            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public Connection getConnection(){
    	return this.connection;
    }
    
    protected void setUp() throws Exception {
        System.out.println("Setting up for " + platformName + " run!");
    }

    protected void dropTriggers() {
    }

    protected void createTriggers() {
    }

    protected void dropSequences() {
    }

    protected void createSequences() {
    }


    //
    // This section povides methods that return strings for table creation.
    // Platform-specific sublcasses
    // can override these as necessary
    //

    protected String getCreateCustomer() {
    	return null;
    }
    // /////////////////

    protected String getForeignKeyConstraint(String pkTable, String pkColumn, String foreignKey) {
    	return null;
    }

    protected String getStringColumn(String name, int length) {
    	return null;
    }

    protected String getIntegerColumn(String name) {
    	return null;
    }

    protected String getGeneratedKeyClause() {
    	return null;
    }

    protected String getDecimalColumn(String name, int size1, int size2) {
    	return null;
    }

    protected String getFloatColumn(String name) {
    	return null;
    }

    protected String getTimestampColumn(String name) {
    	return null;
    }        
}