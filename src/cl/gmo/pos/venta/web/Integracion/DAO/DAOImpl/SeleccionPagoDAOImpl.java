package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;



import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.SeleccionPagoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.PagoBean;

public class SeleccionPagoDAOImpl implements SeleccionPagoDAO {
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public  String[] validaDocumento(String tipo_documento, String codigo_local,
			int numero_documento) throws Exception {

		Connection con = null;
		CallableStatement cs = null;
		int validado = Constantes.INT_CERO;
		String msje = Constantes.STRING_BLANCO;
		String cdg = Constantes.STRING_BLANCO;
		String tipo = Constantes.STRING_BLANCO;
		String fecha = Constantes.STRING_BLANCO;
		String resultado[] = new String[6];
		
		Utils utiles = new Utils();
		System.out.println("paso validaDocumento");
		System.out.println(tipo_documento+"<==>"+codigo_local+"<==>"+numero_documento);
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PAGO_SEL_VALIDA_DOCUMENTO(?,?,?,?,?,?,?,?)}");
			System.out.println("paso ");

			cs.setString(1, tipo_documento);
			cs.setInt(2, numero_documento);
			cs.setString(3, codigo_local);
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.DATE);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			
			cs.execute();
			
			validado = (Integer)cs.getObject(4);
			msje = cs.getString(5);
			cdg = cs.getString(6);
			fecha = utiles.formatoFecha(cs.getDate(7));
			tipo = cs.getString(8);
				
			resultado[0] = String.valueOf(validado);
			resultado[1] = msje;
			resultado[2] = cdg;
			resultado[3] = fecha;  
			resultado[4] = tipo;
			resultado[5] = String.valueOf(numero_documento);
			
		} catch (Exception e) {
			resultado[0] = "0";
			resultado[1] = "Error en la validacion de documento. contactese con sistemas";
	        throw new Exception("Error en DAO, SP_PAGO_SEL_VALIDA_DOCUMENTO");
	        
	        
		} finally {
	          try{
	           if (null != cs){
	        	   log.warn("SeleccionPagoDAOImpl:validaDocumento cierre CallableStatement");
	               cs.close();
	           }              
	           if (null != con){
	        	   log.warn("SeleccionPagoDAOImpl:validaDocumento cierre Connection");
	        	   con.close();
	           }
	           return resultado;
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	        
		}
		return resultado;
	}
	
	public ArrayList<PagoBean> traePagos(String codigo_venta, String tipo)throws Exception
	{
		Utils util = new Utils();
		ArrayList<PagoBean> lista_Pagos = new ArrayList<PagoBean>();
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		System.out.println("paso traePagos");

		try {
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PAGO_SEL_PAGOS_VTA(?,?,?)}");
			cs.setString(1, codigo_venta);
			cs.setString(2, tipo);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			System.out.println("paso traePagos ==> codigoventa =>"+codigo_venta+"<==> tipo ==>"+tipo);
			cs.execute();
			
			rs = (ResultSet)cs.getObject(3);
			
			if (tipo.equals(Constantes.STRING_DIRECTA)) {
				
				while (rs.next()) {
					PagoBean pago = new PagoBean();
					pago.setCod_venta(codigo_venta);
					pago.setAgente(rs.getString("AGENTE"));
					pago.setCambio(rs.getInt("CAMBIO"));
					pago.setCantidad(rs.getInt("CANTIDAD"));
					pago.setCantidad_divisa(rs.getInt("CANTIDAD_DIVISA"));
					pago.setCod_caja(rs.getInt("CAJA"));
					pago.setConfidencial(rs.getString("CONFIDENCIAL"));
					pago.setDevolucion(rs.getString("DEVOLUCION"));
					pago.setDivisa(rs.getString("DIVISA"));
					pago.setFecha( util.formatoFecha(rs.getDate("FECHA")));
					pago.setForma_pago(rs.getString("FPAGO"));
					pago.setNumero_bono(rs.getString("NROBONO"));
					pago.setDescuento(rs.getDouble("DESCUENTO"));
					pago.setDetalle_forma_pago(rs.getString("DET_FPAGO"));
					
					lista_Pagos.add(pago);
				}
			}
			else
			{
				while (rs.next()) {
					PagoBean pago = new PagoBean();
					pago.setCod_venta(codigo_venta);
					pago.setForma_pago(rs.getString("FPAGO"));
					pago.setDivisa(rs.getString("DIVISA"));
					pago.setCod_caja(rs.getInt("CAJA"));
					pago.setCantidad(rs.getInt("CANTIDAD"));
					pago.setCantidad_divisa(rs.getInt("CANTIDAD_DIVISA"));
					pago.setAgente(rs.getString("AGENTE"));
					pago.setDescuento(rs.getDouble("DESCUENTO"));
					pago.setDetalle_forma_pago(rs.getString("DET_FPAGO"));
					pago.setFecha( util.formatoFecha(rs.getDate("FECHA")));
					pago.setAgente(rs.getString("AGENTE"));
					if (rs.getInt("DOCUMENTO") == 0) {
						pago.setTiene_doc(false);
					}
					else
					{
						pago.setTiene_doc(true);
					}
					lista_Pagos.add(pago);
				}
			}
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_SEL_PAGOS_VTA");
		} finally {
	          try{
	           
	           if (null != rs){
	        	   log.warn("SeleccionPagoDAOImpl:traePagos cierre Resultset");
                   rs.close();
               }
	           if (null != cs){
	        	   log.warn("SeleccionPagoDAOImpl:traePagos cierre CallableStatement");
	               cs.close();
	           }              
	           if (null != con){
	        	   log.warn("SeleccionPagoDAOImpl:traePagos cierre Connection");
	        	   con.close();
	           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}
		return lista_Pagos;
	}
	
	/*
	 * LMARIN 20140708.
	 * Se Modifica el metodo para devolver diferentes casos al intentar modificar o anular la forma de pago 20140708.
	 */
	public String eliminaFormaPago(String cdg_vta, String fecha_pedido, String f_pago, String local, String fech_pago,String tipo_accion,String autorizador)throws Exception{
			
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String  respuesta = null;
		System.out.println("paso eliminaFormaPago");
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("psao eliminaFormaPago");

			cs = con.prepareCall("{call SP_PAGO_DEL_FORMA_PAGO(?,?,?,?,?,?,?,?)}");
			cs.setString(1, cdg_vta);
			cs.setString(2, fecha_pedido);
			cs.setString(3, f_pago);
			cs.setString(4, local);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.setString(6, fech_pago);
			cs.setString(7, tipo_accion);
			cs.setString(8, autorizador);
			
			cs.execute();
			
			String respuesta_sp = (String)cs.getObject(5);
			
			respuesta = respuesta_sp;
			/*if(Constantes.STRING_TRUE_MAY.equals(respuesta_sp)){
				respuesta = true;
			}else if(Constantes.STRING_FALSE_MAY.equals(respuesta_sp)){
				respuesta = false;
			}*/			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_DEL_PAGOS_VTA");
		} finally {
	          try{
	        	  if (null != rs){
		        	   log.warn("SeleccionPagoDAOImpl:EliminaformaPago cierre Resultset");
	                   rs.close();
	               }
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:EliminaformaPago cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:EliminaformaPago cierre Connection");
		        	   con.close();
		           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}	
		
		return respuesta;
	}


	public String TieneDocumentos(String codigo) throws Exception {
		String tiene_documentos = Constantes.STRING_BLANCO;
		Connection con = null;
		CallableStatement cs = null;
		int cantidad = Constantes.INT_CERO;
		System.out.println("paso TieneDocumentos");
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("psao TieneDocumentos");

			cs = con.prepareCall("{call SP_PAGO_SEL_VERIFICA_DOC(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			cantidad = cs.getInt(2);
			
			if (cantidad > 0) {
				tiene_documentos = Constantes.STRING_TRUE;
			}else{
				tiene_documentos = Constantes.STRING_FALSE;
			}
			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_SEL_VERIFICA_DOC");
		} finally {
	          try{
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:TieneDocumentos cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:TieneDocumentos cierre Connection");
		        	   con.close();
		           } 
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}
		return tiene_documentos;
	}

	
	public boolean eliminaFormaPagoAlbaranes(String cdg_vta, String fecha_pedido, String f_pago, String local, String fech_pago, String tipo)throws Exception{
			
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		boolean respuesta = false;
		//cdg_vta = "09/0002442";
		System.out.println("paso eliminaFormaPagoAlbaranes");
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("psao eliminaFormaPagoAlbaranes");

			cs = con.prepareCall("{call SP_PAGO_DEL_FPAGO_ALBARANES(?,?,?,?,?,?,?)}");
			cs.setString(1, cdg_vta);
			cs.setString(2, fecha_pedido);
			cs.setString(3, f_pago);
			cs.setString(4, local);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.setString(6, fech_pago);
			cs.setString(7, tipo);
			
			cs.execute();
			
			String respuesta_sp = (String)cs.getObject(5);
			
			if(Constantes.STRING_TRUE_MAY.equals(respuesta_sp)){
				respuesta = true;
			}else if(Constantes.STRING_FALSE_MAY.equals(respuesta_sp)){
				respuesta = false;
			}			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_SEL_PAGOS_VTA");
		} finally {
	          try{
	        	  if (null != rs){
		        	   log.warn("SeleccionPagoDAOImpl:eliminaFormaPagoAlbaranes cierre Resultset");
	                   rs.close();
	               }
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:eliminaFormaPagoAlbaranes cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:eliminaFormaPagoAlbaranes cierre Connection");
		        	   con.close();
		           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}	
		
		return respuesta;
	}

	public boolean cambiaFolioDocumento(String cdg, String fecha,
			String tipo_negocio, String documento, int numero_boleta) throws Exception {
		
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		boolean respuesta = false;
		System.out.println("paso cambiaFolioDocumento");
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("psao cambiaFolioDocumento");

			cs = con.prepareCall("{call SP_PAGO_UPD_CAMBIO_DOCUMENTO(?,?,?,?,?,?)}");
			cs.setString(1, cdg);
			cs.setString(2, fecha);
			cs.setString(3, tipo_negocio);
			cs.setInt(4, Integer.parseInt(documento));
			cs.setInt(5, numero_boleta);
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			
			cs.execute();
			
			int respuesta_sp = cs.getInt(6);
			
			if(Constantes.INT_UNO == respuesta_sp){
				respuesta = true;
			}else{
				respuesta = false;
			}			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_UPD_CAMBIO_DOCUMENTO");
		} finally {
	          try{
	        	  if (null != rs){
		        	   log.warn("SeleccionPagoDAOImpl:cambiaFolioDocumento cierre Resultset");
	                   rs.close();
	               }
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:cambiaFolioDocumento cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:cambiaFolioDocumento cierre Connection");
		        	   con.close();
		           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}	
		
		return respuesta;
	}
	
	
	
	/**
	 * @author Luis Marin
	 * @date 20150524
	 * M�todo que devuleve datos de boleta electronica
	 * @param  String tipo
	 * @param  String nroDoc
	 * @param String local
	 * @return  ArrayList<BoletaBean>
	 * @throws Exception 
	 */
	public  ArrayList<BoletaBean> reimpresionBoletaelec(String tipo ,String nroDoc,String local) throws Exception{
		
		ArrayList<BoletaBean> boletaslist = new ArrayList<BoletaBean>();
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		System.out.println("paso reimpresionBoletaelec entro =>"+tipo+" - "+nroDoc+" - "+local);
		
		String strStoreProcedure = "";

		try {
			con = ConexionFactory.INSTANCE.getConexion();
			
			if((null!=tipo) && (!("".equalsIgnoreCase(tipo))) && ("O".equals(tipo))){
				strStoreProcedure = "{call SP_REIMPRESION_GE(?,?,?,?)}";
			}
			else {
				strStoreProcedure = "{call SP_REIMPRESION_BE(?,?,?,?)}";
			}
			cs = con.prepareCall(strStoreProcedure);
			cs.setString(1, tipo);
			cs.setString(2, nroDoc);
			cs.setString(3,local);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.execute();
			
			rs = (ResultSet)cs.getObject(4);			
				
			while (rs.next()) {
					BoletaBean boleta = new BoletaBean();
					boleta.setNif(rs.getString("NIF"));
					boleta.setDv(rs.getString("LETRANIF"));
					boleta.setNumero(Integer.parseInt(rs.getString("NUMERO")));
					boletaslist.add(boleta);
			}
		
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_REIMPRESION_BE");
		} finally {
	          try{
	           
	           if (null != rs){
	        	   log.warn("SeleccionPagoDAOImpl:reimpresionBoletaelec cierre Resultset");
                   rs.close();
               }
	           if (null != cs){
	        	   log.warn("SeleccionPagoDAOImpl:reimpresionBoletaelec cierre CallableStatement");
	               cs.close();
	           }              
	           if (null != con){
	        	   log.warn("SeleccionPagoDAOImpl:reimpresionBoletaelec cierre Connection");
	        	   con.close();
	           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}
		for(BoletaBean b: boletaslist){
			System.out.println(" salida reimpresiondao ==>"+b.getNif()+" - "+b.getDv()+" - "+b.getNumero());
		}
		return boletaslist;
	}
	
	/**
	 * @author Luis Marin
	 * @date 20150524
	 * M�todo que devuleve datos de boleta electronica
	 * @param  String tipo
	 * @param  String nroDoc
	 * @param String local
	 * @return  ArrayList<BoletaBean>
	 * @throws Exception 
	 */
	public  ArrayList<BoletaBean> reimpresionGuiaTraslado(String tipo ,String nroDoc,String local) throws Exception{
		
		ArrayList<BoletaBean> boletaslist = new ArrayList<BoletaBean>();
		
		//System.out.println("CMRO paso reimpresionGuiaTraslado entro =>"+tipo+" - "+nroDoc+" - "+local);
		
		String[] arrRut = Constantes.STRING_RUT_LUXOTTICA.split("-");
		//String[] arrRut2 = Constantes.STRING_RUT_GUIA_T2.split("-");
		String[] arrRut2 = "76249448-5".split("-");
		
		//CMRO
			//System.out.println("CMRO Nif = "+arrRut[0]+", Dv ="+arrRut[1]+", nroDoc ="+nroDoc);
		//CMRO
		
		String sUrl = "http://10.216.4.9/PDF/"+Constantes.STRING_WS_DTYPE_GE+"%20"+Constantes.STRING_RUT_LUXOTTICA+"%20"+nroDoc+Constantes.STRING_EXTENSION_PDF;
		String sUrl2 = "http://10.216.4.9/PDF/"+Constantes.STRING_WS_DTYPE_GE+"%20"+"76249448-5"+"%20"+nroDoc+Constantes.STRING_EXTENSION_PDF;
		
		
		BoletaBean boleta = new BoletaBean();
		if (this.existeArchivo(sUrl)){
			//CMRO
			System.out.println ("CMRO existeArchivo como Lux, sUrl ="+sUrl); 
			System.out.println ("CMRO existeArchivo como Lux, sUrl2 ="+sUrl2);
			//CMRO
			
			boleta.setNif(arrRut[0]);
			boleta.setDv(arrRut[1]);
			boleta.setNumero(Integer.parseInt(nroDoc));
		}
		else {
			if (this.existeArchivo(sUrl2)){
				boleta.setNif(arrRut2[0]);
				boleta.setDv(arrRut2[1]);
				boleta.setNumero(Integer.parseInt(nroDoc));
			}else {
				boleta.setNif("0");
				boleta.setDv("0");
				boleta.setNumero(0);
			}
		}
		boletaslist.add(boleta);
		
		for(BoletaBean b: boletaslist){
			System.out.println(" salida reimpresiondao ==>"+b.getNif()+" - "+b.getDv()+" - "+b.getNumero());
		}
		return boletaslist;
	}
	
	
	/*
	 * M�todo que valida dto cliente 
	 * @param  String rut
	 * @return String 
	 */
	public String validaDtoCliente(String rut,String convenio) throws Exception{
		Connection con = null;
		CallableStatement st = null;
		String ret = "";
		try{
			System.out.println("SP_SOLICITA_DTO_CLIENTE ==> "+rut+"-"+convenio);
			con = ConexionFactory.INSTANCE.getConexion();
			st = con.prepareCall("{call SP_SOLICITA_DTO_CLIENTE(?,?,?)}");
			st.setString(1, rut);
			st.setString(2, convenio);
			st.registerOutParameter(3, OracleTypes.VARCHAR);
			
			st.execute();
			
			ret = st.getString(3);
			
			System.out.println("validaDtoCliente ===> "+ret);
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_SOLICITA_DTO_CLIENTE");
		} finally {
	          try{
	        	  if (null != st){
		        	   log.warn("SeleccionPagoDAOImpl:validaDtoCliente cierre Resultset");
	                   st.close();
	               }
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:validaDtoCliente cierre CallableStatement");
		               con.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:validaDtoCliente cierre Connection");
		        	   con.close();
		           }
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}	
		
		st.close();
		
		return ret;
	}
	
	/*******************************************************************************
	/* METODO: tieneEsteTipoDoc
	/* OBJETIVO: Retorna true si el encargo tiene el tipo de documento a verificar en la tabla PEDVTNUM
	/* PARAMETROS: codigo = al n�mero del encargo
	               cTipoDoc = al tipo de documento, el cual puede ser 
				   R (Recibo de Recaudaci�n
				   O (Gu�a de Despacho)
				   B (Boleta)
	/***************************************************************************/
		public int tieneEsteTipoDoc(String codigo, String cTipoDoc) throws Exception {
		/*int tieneEsteDoc = Constantes.INT_CERO;
		Connection con = null;
		CallableStatement cs = null;
		int cantidad = Constantes.INT_CERO;
		System.out.println("paso TieneEsteTipoDoc");
		
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("en el try TieneEsteTipoDoc");

			cs = con.prepareCall("{call SP_PAGO_SEL_TIENE_TIPODOC(?,?,?)}");
			
			cs.setString(1, codigo);
			cs.setString(2, cTipoDoc);
			cs.registerOutParameter(3, OracleTypes.NUMERIC);
			
			//CMRO
			System.out.println("SP_PAGO_SEL_TIENE_TIPODOC codigo = "+codigo+" , cTipoDoc = "+cTipoDoc);
			//CMRO
			
			cs.execute();
			
			cantidad = cs.getInt(3);
			
			tieneEsteDoc = cantidad;
				
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_SEL_TIENE_TIPODOC");
		} finally {
	          try{
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:SP_PAGO_SEL_TIENE_TIPODOC cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:SP_PAGO_SEL_TIENE_TIPODOC cierre Connection");
		        	   con.close();
		           } 
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}
		return tieneEsteDoc;*/
		
		
		//String tiene_documentos = Constantes.STRING_BLANCO;
		Connection con = null;
		CallableStatement cs = null;
		int cantidad = Constantes.INT_CERO;
		//System.out.println("CMRO tieneEsteTipoDoc");
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			

			cs = con.prepareCall("{call SP_PAGO_SEL_VERIFICA_DOC(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			cantidad = cs.getInt(2);
			
			//System.out.println("CMRO cantidad = "+cantidad);
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error en DAO, SP_PAGO_SEL_VERIFICA_DOC");
		} finally {
	          try{
		           if (null != cs){
		        	   log.warn("SeleccionPagoDAOImpl:TieneDocumentos cierre CallableStatement");
		               cs.close();
		           }              
		           if (null != con){
		        	   log.warn("SeleccionPagoDAOImpl:TieneDocumentos cierre Connection");
		        	   con.close();
		           } 
	       }catch(Exception e){
	          e.printStackTrace();
	       }
	      
		}
		return cantidad;
		
	}
		
		public static boolean existeArchivo(String vUrl){
			boolean resp = false;
			
			try {
				System.out.println("CMRO en existeArchivo");
				URL url = new URL(vUrl);
				URLConnection uc = url.openConnection();

		        BufferedReader in = new BufferedReader(new InputStreamReader(
		                uc.getInputStream()));
				resp = true;
		        in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
}
