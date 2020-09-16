package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ConsultaOptometricaDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ConsultaOptometricaBean;



public class PosConsultaOptometricaFacade {


	/***********************************************************************************
	 * M�todo:   traeGraduacionesClientes.
	 * Objetivo: permite obtener listado de graduaciones de un cliente que han sido
	 *           registradas en el formulario de Consulta Optom�trica
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
	 * M�todo:   ingresaConsultaOptometrica.
	 * Objetivo: permite invocar el m�todo DAO que permite ingresar una Consulta 
	 *           Optom�trica
	 * @param consultaOpt
	 * @return boolean, indica si se efectu� el registro de la Consulta Optom�trica o no
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
	 * M�todo:   traeGraduacionFecha.
	 * Objetivo: invoca el m�todo DAO que permite obtener los datos de una Consulta
	 *           Optom�trica dado el cliente, la fecha y el n�mero de la graduaci�n
	 * @param cliente
	 * @param fecha
	 * @param numero de graduaci�n en una Consulta Optom�trica
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
	 * M�todo:   traeGraduacionCodigo.
	 * Objetivo: invoca el m�todo DAO que permite obtener los datos de una Consulta
	 *           Optom�trica dado el c�digo interno de dicha Consulta
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
	 * M�todo:   traeNumeroGraduacion. REVISAR
	 * Objetivo: invoca el m�todo DAO que permite obtener el n�mero de graduaci�n de una 
	 *           Consulta Optom�trica
	 * @param cliente
	 * @param fecha
	 * @return int, n�mero de graduaci�n de una Consulta Optom�trica
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
	 * M�todo:   modificaConsultaOptometrica.
	 * Objetivo: invoca el m�todo DAO que permite modificar los datos de una Consulta
	 *           Optom�trica 
	 * @param consultaOpt
	 * @return boolean, indica si se efectu� o no la modificaci�n de la Consulta Opt
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
	  * M�todo:   traeConsultaMasRecienteCliente.
	  * Objetivo: invoca el m�todo DAO que permite obtener los datos de una Consulta
	  *           Optom�trica m�s reciente de un Cliente
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
	  * M�todo:   traeTecnicos.
	  * Objetivo: invoca el m�todo DAO que permite obtener los T�cnicos de la Tienda indicada
	  *           en el par�metro local.
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
