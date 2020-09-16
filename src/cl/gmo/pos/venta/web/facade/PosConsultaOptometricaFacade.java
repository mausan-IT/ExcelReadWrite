package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ConsultaOptometricaDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ConsultaOptometricaBean;



public class PosConsultaOptometricaFacade {


	/***********************************************************************************
	 * Método:   traeGraduacionesClientes.
	 * Objetivo: permite obtener listado de graduaciones de un cliente que han sido
	 *           registradas en el formulario de Consulta Optométrica
	 * @param cliente
	 * @return ArrayList<GraduacionesBean>
	 ***********************************************************************************/
	public static ArrayList<GraduacionesBean> traeGraduacionesClientes(String cliente) throws Exception{
		
		ConsultaOptometricaDAOImpl consultaOpt = new ConsultaOptometricaDAOImpl();
		ArrayList<GraduacionesBean> lista_Graduaciones = new ArrayList<GraduacionesBean>();
		try {
			lista_Graduaciones = consultaOpt.traeGraduacionesCliente(cliente);
		} catch (Exception e){
            e.printStackTrace();
            throw new Exception("PosGraduacionesFacade: traeGraduacionesClientes");
        }
		return lista_Graduaciones;
	}
	

	/***********************************************************************************
	 * Método:   ingresaConsultaOptometrica.
	 * Objetivo: permite invocar el método DAO que permite ingresar una Consulta 
	 *           Optométrica
	 * @param consultaOpt
	 * @return boolean, indica si se efectuó el registro de la Consulta Optométrica o no
	 ***********************************************************************************/
	public static boolean ingresaConsultaOptometrica(ConsultaOptometricaBean consultaOpt){
		ConsultaOptometricaDAOImpl consultaOptDao = new ConsultaOptometricaDAOImpl();
		boolean respuesta = false;
		try{
			
			respuesta = consultaOptDao.ingresaConsultaOptometrica(consultaOpt);
			
		}catch(Exception ex){
			ex.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}

	
	/***********************************************************************************
	 * Método:   traeGraduacionFecha.
	 * Objetivo: invoca el método DAO que permite obtener los datos de una Consulta
	 *           Optométrica dado el cliente, la fecha y el número de la graduación
	 * @param cliente
	 * @param fecha
	 * @param numero de graduación en una Consulta Optométrica
	 * @return ConsultaOptometricaBean
	 ***********************************************************************************/
	public static ConsultaOptometricaBean traeGraduacionFecha(String cliente, String fecha, Double numero){
		
		ConsultaOptometricaBean consultaOptResult = new ConsultaOptometricaBean();
		
		try {
            ConsultaOptometricaDAOImpl consultaOpt = new ConsultaOptometricaDAOImpl();
            consultaOptResult = consultaOpt.traeGraduacionFecha(cliente, fecha, numero);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return consultaOptResult;
	}
	
	
	/***********************************************************************************
	 * Método:   traeGraduacionCodigo.
	 * Objetivo: invoca el método DAO que permite obtener los datos de una Consulta
	 *           Optométrica dado el código interno de dicha Consulta
	 * @param dCodigo
	 * @return ConsultaOptometricaBean
	 ***********************************************************************************/
	public static ConsultaOptometricaBean traeGraduacionCodigo(int dCodigo){
		
		ConsultaOptometricaBean consultaOptResult = new ConsultaOptometricaBean();
		
		try {
            ConsultaOptometricaDAOImpl consultaOpt = new ConsultaOptometricaDAOImpl();
            consultaOptResult = consultaOpt.traeGraduacionCodigo(dCodigo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return consultaOptResult;
	}
	
	
	/***********************************************************************************
	 * Método:   traeNumeroGraduacion. REVISAR
	 * Objetivo: invoca el método DAO que permite obtener el número de graduación de una 
	 *           Consulta Optométrica
	 * @param cliente
	 * @param fecha
	 * @return int, número de graduación de una Consulta Optométrica
	 ***********************************************************************************/
	public static int traeNumeroGraduacion(String cliente, String fecha){
		
		int numero = 0;
		try {
            ConsultaOptometricaDAOImpl consultaOptDao = new ConsultaOptometricaDAOImpl();
            numero = consultaOptDao.traeNumeroGraduacion(cliente, fecha);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return numero;
	}
	
	 
	/***********************************************************************************
	 * Método:   modificaConsultaOptometrica.
	 * Objetivo: invoca el método DAO que permite modificar los datos de una Consulta
	 *           Optométrica 
	 * @param consultaOpt
	 * @return boolean, indica si se efectuó o no la modificación de la Consulta Opt
	 ***********************************************************************************/
	 public static boolean modificaConsultaOptometrica(ConsultaOptometricaBean consultaOpt){
		 
			ConsultaOptometricaDAOImpl consultaOptDao = new ConsultaOptometricaDAOImpl();
			boolean respuesta = false;
			try{
				
				respuesta = consultaOptDao.modificaConsultaOptometrica(consultaOpt);
				
			}catch(Exception ex){
				ex.printStackTrace();
				respuesta = false;
			}
			return respuesta;
		}
	 
	 
	 
	 /***********************************************************************************
	  * Método:   traeConsultaMasRecienteCliente.
	  * Objetivo: invoca el método DAO que permite obtener los datos de una Consulta
	  *           Optométrica más reciente de un Cliente
	  * @param cliente
	  * @return ConsultaOptometricaBean
	  ***********************************************************************************/
	public static ConsultaOptometricaBean traeConsultaMasRecienteCliente(String cliente) throws Exception{
			
		ConsultaOptometricaDAOImpl consultaOpt = new ConsultaOptometricaDAOImpl();
		ConsultaOptometricaBean consultaMasReciente = new ConsultaOptometricaBean();
			
		try{
			consultaMasReciente = consultaOpt.traeConsultaMasReciente(cliente);
		} catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosConsultaOptometricaFacade: traeConsultaMasRecienteCliente");
	      }
		
		return consultaMasReciente;
	}
	 
	
	/***********************************************************************************
	  * Método:   traeTecnicos.
	  * Objetivo: invoca el método DAO que permite obtener los Técnicos de la Tienda indicada
	  *           en el parámetro local.
	  * @param local
	  * @return ArrayList<AgenteBean>
	  ***********************************************************************************/
	 public static ArrayList<AgenteBean> traeTecnicos(String local)
	    {
	        ArrayList<AgenteBean> lista_Tecnicos = new ArrayList<AgenteBean>();
	        try {
	            ConsultaOptometricaDAOImpl consultaOpt = new ConsultaOptometricaDAOImpl();
	            lista_Tecnicos = consultaOpt.traeTecnicos(local);
	        } catch (Exception e) {
	            e.printStackTrace();
	            lista_Tecnicos = null;
	        }
	        return lista_Tecnicos;
	    }
}
