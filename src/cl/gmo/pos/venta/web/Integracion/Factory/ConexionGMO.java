package cl.gmo.pos.venta.web.Integracion.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.openjpa.lib.log.Log;
/**
 *
 * @author Advise68
 */
public class ConexionGMO implements ConexionFactory{
    
    public Connection getConexion() {
    	
		/*Connection con = null;
    	String usuario="gmo";
    	String pass="249gmo.,";
    	String driver="oracle.jdbc.driver.OracleDriver";
    	try{	
    		Class.forName(driver).newInstance();
    		//DEV
    		con =DriverManager.getConnection("jdbc:oracle:thin:@10.216.4.34:1521:POSCL",usuario,pass);
    		//PROD
    	   
    	}catch(Exception e) {    
    		
    		 System.out.println("Error de conexion");
    	}
    	return con;*/
    	
    	Connection con = null; 
        try{
         
        DataSource dataSource = null;
              InitialContext context = null;
              context = new InitialContext();
              dataSource = (DataSource) context.lookup("jdbc/gmo");
              con = dataSource.getConnection();
         }catch(SQLException e){
             e.printStackTrace();
         }catch(NamingException e){
        	 e.printStackTrace();
         }
         return con;
    }

    
}
