/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ProductosDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
public class ProductoExentoDAOImpl{
	
	Logger log = Logger.getLogger( this.getClass() );
	
       public ProductosBean obtenerProdExentoTienda(String cod_barra, String local) throws Exception
    {
    	log.warn("ProductosDAOImpl:validaProdExentoTienda inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
        
        try {
        	log.warn("ProductosDAOImpl:validaProdExentoTienda conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
        	log.warn("EXEC SP_BUSCAR_SEL_ARTI_EXENTO('"+cod_barra+"','"+local+"');");
            String sql = "{call SP_BUSCAR_SEL_ARTI_EXENTO(?,?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, cod_barra);
            st.setString(2, local);
            st.registerOutParameter(3, OracleTypes.CURSOR);
             
            st.execute();
            
            rs = (ResultSet)st.getObject(3);
            
            System.out.println("en ProductoExentoDAOImpl obtenerProdExentoTienda antes del while");
            while (rs.next())
              {
            	log.warn("ProductosDAOImpl:validaProdExentoTienda entrando ciclo while");            	
            	prod.setCod_barra(rs.getString("CODIGOBARRAS"));
                prod.setCodigo(rs.getString("CDG"));
                prod.setDtoMaxExento(rs.getDouble("DTOEMAX"));
                prod.setEsProdExento(Constantes.STRING_TRUE);
            }
            
            return prod;
                
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:validaProdExentoTienda error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTI_EXENTO"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:validaProdExentoTienda cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:validaProdExentoTienda cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:validaProdExentoTienda cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:validaProdExentoTienda error", e);
            }
        }
        
    }
    
    
   /*
    * Método: verificarProdExentoTienda
	* VALORES POSIBLES PARA LA RESPUESTA
	*   0 ==> PRODUCTO NO ES EXENTO
	*   1 ==> PRODUCTO EXENTO PARA EL LOCAL SUMINISTRADO
	*   2 ==> PRODUCTO ES EXENTO PERO NO ESTA AUTORIZADO PARA ESE LOCAL
	*  -1 ==> ERROR
	*/
       public int verificarProdExentoTienda(String cod_barra, String local) throws Exception
       {
    	   log.warn("ProductosDAOImpl:validaProdExentoTienda inicio");
           Connection con = null;
           CallableStatement st = null;
           int resp = 0;
           
           try {
           		log.warn("ProductosDAOImpl:validaProdExentoTienda conectando base datos");
           		con = ConexionFactory.INSTANCE.getConexion();
           		log.warn("EXEC SP_VERIFICAR_SEL_ARTI_EXENTO('"+cod_barra+"','"+local+"');");
           		String sql = "{call SP_VERIFICAR_SEL_ARTI_EXENTO(?,?,?)}";
           		st = con.prepareCall(sql);
           		st.setString(1, cod_barra);
           		st.setString(2, local);
           		st.registerOutParameter(3, Types.NUMERIC);
                
           		st.execute();
           		
           		System.out.println("CMRO verificarProdExentoTienda");
               
           		resp = st.getInt(3);
           		
           		
               
           } catch (Exception e) {
   				log.error("ProductosDAOImpl:verificaProdExentoTienda error controlado",e);
               //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
   			} finally {
                 try{
   	               		if (null != st){
   	               			log.warn("ProductosDAOImpl:verificaProdExentoTienda cierre CallableStatement");
   	               			st.close();
   	               		}  
   	               		if (null != con){
   	               			log.warn("ProductosDAOImpl:verificaProdExentoTiendao cierre Connection");
   	               			con.close();
   	               		}
                 }catch(Exception e){
               	  	log.error("ProductosDAOImpl:verificaProdExentoTienda error", e);
                 }
   		
   			}
   		
           	return resp;
           
       }
       
       public int obtenerDtoMaxProdETienda(String cod_barra, String local) throws Exception
       {
    	   log.warn("ProductosDAOImpl:validaProdExentoTienda inicio");
           Connection con = null;
           CallableStatement st = null;
           int resp = 0;
           
           try {
           		log.warn("ProductosDAOImpl:validaProdExentoTienda conectando base datos");
           		con = ConexionFactory.INSTANCE.getConexion();
           		log.warn("EXEC SP_VERIFICAR_SEL_ARTI_EXENTO('"+cod_barra+"','"+local+"');");
           		String sql = "{call SP_VERIFICAR_SEL_ARTI_EXENTO(?,?,?)}";
           		st = con.prepareCall(sql);
           		st.setString(1, cod_barra);
           		st.setString(2, local);
           		st.registerOutParameter(3, Types.NUMERIC);
                
           		st.execute();
           		
           		resp = st.getInt(3);
           		
               
           } catch (Exception e) {
   				log.error("ProductosDAOImpl:verificaProdExentoTienda error controlado",e);
               //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
   			} finally {
                 try{
   	               		if (null != st){
   	               			log.warn("ProductosDAOImpl:verificaProdExentoTienda cierre CallableStatement");
   	               			st.close();
   	               		}  
   	               		if (null != con){
   	               			log.warn("ProductosDAOImpl:verificaProdExentoTiendao cierre Connection");
   	               			con.close();
   	               		}
                 }catch(Exception e){
               	  	log.error("ProductosDAOImpl:verificaProdExentoTienda error", e);
                 }
   		
   			}
   		
           	return resp;
           
       }
}
