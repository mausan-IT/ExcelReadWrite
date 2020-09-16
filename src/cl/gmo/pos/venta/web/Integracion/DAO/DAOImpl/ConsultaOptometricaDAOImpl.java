package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ConsultaOptometricaDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ConsultaOptometricaBean;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;

public class ConsultaOptometricaDAOImpl implements ConsultaOptometricaDAO{
	Logger log = Logger.getLogger( this.getClass() );
	
	
	/***********************************************************************************
	 * Método:   traeGraduacionesCliente.
	 * Objetivo: permite ejecutar el procedimiento en BD que dado un cliente, retorna el 
	 *           listado de graduaciones de ese, cliente que han sido
	 *           registradas a través del formulario de Consulta Optométrica
	 * @param cliente
	 * @return ArrayList<GraduacionesBean>
	 ***********************************************************************************/
	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception
	{
		
		log.info("GraduacionesDAOImpl:traeGraduacionesCliente inicio");
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		ArrayList<GraduacionesBean> lista_graduaciones = new ArrayList<GraduacionesBean>();
		
		
		try {
			log.info("ConsultaOptometricaDAOImpl:traeGraduacionesCliente conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call GMO.SP_COP_SEL_GRADUACION_CLI(?,?)}");
			cs.setString(1, cliente);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			int x = 0;
			
			while (rs.next()) {
				
				log.debug("ConsultaOptometricaDAOImpl:traeGraduacionesCliente entrando ciclo while");
				GraduacionesBean graduacion = new GraduacionesBean();
				
				graduacion.setCodigo(x);
				graduacion.setFecha(util.formatoFecha((rs.getDate("COP_FECHA"))));
				graduacion.setDoctor(rs.getString("DOCTOR"));
				graduacion.setNumero(rs.getInt("COP_NUMERO"));
				graduacion.setOrden(Constantes.STRING_BLANCO);
				graduacion.setOD_adicion(rs.getDouble("COP_ODADICION"));
				graduacion.setOD_cilindro(rs.getDouble("COP_ODCILINDRO"));
				graduacion.setOD_eje(rs.getInt("COP_ODEJE"));
				graduacion.setOD_esfera(rs.getDouble("COP_ODESFERA"));
				graduacion.setOD_diametro(0.0);
				graduacion.setOD_esfera_cerca(rs.getDouble("COP_ODESFERAC"));
				graduacion.setOD_n(rs.getDouble("COP_ODNP1"));
				graduacion.setOD_p(rs.getDouble("COP_ODNP2"));
				graduacion.setOI_adicion(rs.getDouble("COP_OIADICION"));
				graduacion.setOI_cilindro(rs.getDouble("COP_OICILINDRO"));
				graduacion.setOI_eje(rs.getInt("COP_OIEJE"));
				graduacion.setOI_esfera(rs.getDouble("COP_OIESFERA"));
				graduacion.setOI_diametro(0.0);
				graduacion.setOI_esfera_cerca(rs.getDouble("COP_OIESFERAC"));
				graduacion.setOI_n(rs.getDouble("COP_OINP1"));
				graduacion.setOI_p(rs.getDouble("COP_OINP2"));
				graduacion.setOD_obser(rs.getString("COP_ODOBSER"));
				graduacion.setOI_obser(rs.getString("COP_OIOBSERVA"));
				graduacion.setDiferente_add(rs.getBoolean("COP_DIFADICION"));

				lista_graduaciones.add(x,graduacion);
				
				x += 1;
				
			}
			
			return lista_graduaciones;
			
		} catch(SQLException e){        
			log.error("ConsultaOptometricaDAOImpl:traeGraduacionesCliente error controlado",e);
            throw new Exception("Error en DAO: SP_COP_SEL_GRADUACION_CLI");
        }finally{
            try{
                if (null != rs){
                	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionesCliente cierre ResultSet");
                    rs.close();
                }
                if (null != cs){
                	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionesCliente cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionesCliente cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("GraduacionesDAOImpl:traeGraduacionesCliente error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
	}

	
	/***********************************************************************************
	 * Método:   ingresaConsultaOptometrica.
	 * Objetivo: permite ejecutar el procedimiento en BD que ingresa una Consulta
	 *           Optométrica
	 * @param consultaOpt
	 * @return boolean, indica si se efectuó o no el ingreso de la Consulta Optométrica 
	 ***********************************************************************************/
	public boolean ingresaConsultaOptometrica(ConsultaOptometricaBean consultaOpt) throws Exception{

		Connection conn = null;
		CallableStatement cs = null;
		int retorno = Constantes.INT_CERO;
		boolean respuesta = false;
		GraduacionesBean graduacion = new GraduacionesBean();
		graduacion = consultaOpt.getGraduacion();
			
		
		try {
			log.info("ConsultaOptometricaDAOImpl:ingresaConsultaOptometrica conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_COP_INS_GRADUACION_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			
			cs.setInt(1,graduacion.getCliente());
			cs.setString(2, graduacion.getFecha());
			cs.setInt(3, graduacion.getNumero());
			cs.setString(4, graduacion.getDoctor());
			cs.setString(5, graduacion.getFecha_prox_revision());
			cs.setString(6, graduacion.getTipo());
			
			if(null != graduacion.getOD_esfera()){
				cs.setDouble(7, graduacion.getOD_esfera());
			}else{
				cs.setString(7, null);
			}			
			
			if(null != graduacion.getOD_cilindro()){
				cs.setDouble(8, graduacion.getOD_cilindro());
			}else{
				cs.setString(8, null);
			}			
			
			cs.setString(9, graduacion.getAgente());
			
			if(null != graduacion.getOD_eje()){
				cs.setInt(10, graduacion.getOD_eje());
			}else{
				cs.setString(10, null);
			}
			
			
			if(null != graduacion.getOD_adicion()){
				cs.setDouble(11, graduacion.getOD_adicion());
			}else{
				cs.setString(11, null);
			}
			
			if(null !=graduacion.getOD_esfera_cerca() ){
				cs.setDouble(12, graduacion.getOD_esfera_cerca());
			}else{
				cs.setString(12, null);
			}
			
			if(null != graduacion.getOD_n()){
				cs.setDouble(13,graduacion.getOD_n());
			}else{
				cs.setString(13, null);
			}		
			
			cs.setString(14, graduacion.getOD_obser());
			
			if(null != graduacion.getOI_esfera()){
				cs.setDouble(15, graduacion.getOI_esfera());
			}else{
				cs.setString(15, null);
			}
			
			if(null != graduacion.getOI_cilindro()){
				cs.setDouble(16, graduacion.getOI_cilindro());
			}else{
				cs.setString(16, null);
			}
			
			if(null !=graduacion.getOI_eje()){
				cs.setInt(17, graduacion.getOI_eje());
			}else{
				cs.setString(17, null);
			}
			
			if(null != graduacion.getOI_adicion()){
				cs.setDouble(18, graduacion.getOI_adicion());
			}else{
				cs.setString(18, null);
			}
			
			if(null != graduacion.getOI_esfera_cerca()){
				cs.setDouble(19, graduacion.getOI_esfera_cerca());
			}else{
				cs.setString(19, null);
			}
			
			if(null != graduacion.getOI_n()){
				cs.setDouble(20,graduacion.getOI_n());
			}else{
				cs.setString(20,null);
			}
			
			cs.setString(21, graduacion.getOI_obser());
			
			if(null != graduacion.getOD_p()){
				cs.setDouble(22, graduacion.getOD_p());
			}else{
				cs.setString(22, null);
			}
			
			if(null != graduacion.getOI_p()){
				cs.setDouble(23, graduacion.getOI_p());
			}else{
				cs.setString(23, null);
			}
			
			/*
			if(null != graduacion.getOD_avsc()){
				cs.setDouble(24,graduacion.getOD_avsc());
			}else{
				cs.setString(24,null);
			}
			
			if(null != graduacion.getOI_avsc()){
				cs.setDouble(25,graduacion.getOI_avsc());
			}else{
				cs.setString(25,null);
			}
			
			if(null !=graduacion.getOD_avcc()){
				cs.setDouble(26,graduacion.getOD_avcc());
			}else{
				cs.setString(26,null);
			}
			
			if(null != graduacion.getOI_avcc()){
				cs.setDouble(27,graduacion.getOI_avcc());
			}else{
				cs.setString(27, null);
			}			
			*/
			
			if(null != consultaOpt.getStrOD_avsc()){
				cs.setString(24,consultaOpt.getStrOD_avsc());
			}else{
				cs.setString(24,null);
			}
			
			if(null != consultaOpt.getStrOI_avsc()){
				cs.setString(25,consultaOpt.getStrOI_avsc());
			}else{
				cs.setString(25,null);
			}
			
			
			if(null !=consultaOpt.getStrOD_avcc()){
				cs.setString(26,consultaOpt.getStrOD_avcc());
			}else{
				cs.setString(26,null);
			}
			
			
			if(null != consultaOpt.getStrOI_avcc()){
				cs.setString(27,consultaOpt.getStrOI_avcc());
			}else{
				cs.setString(27, null);
			}
			
			
			if(null == graduacion.getOD_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOD_cantidad())){
				cs.setString(28,null);
			}else{
				cs.setInt(28, Integer.parseInt(graduacion.getOD_cantidad()));
			}
			
			if(null == graduacion.getOD_base() || (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOD_base()))){
				cs.setString(29, null);
			}else{
				cs.setString(29, graduacion.getOD_base());
			}			
			
			if(null == graduacion.getOD_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOD_altura())){
				cs.setString(30, null);
			}else{
				cs.setDouble(30, Double.parseDouble(graduacion.getOD_altura()));
			}			
			
			if(null == graduacion.getOI_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOI_cantidad())){
				cs.setString(31,null);
			}else{
				cs.setInt(31, Integer.parseInt(graduacion.getOI_cantidad()));
			}	
			
			if(null == graduacion.getOI_base()|| (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOI_base()))){
				cs.setString(32, null);
			}else{
				cs.setString(32, graduacion.getOI_base());
			}
			
			
			if(null == graduacion.getOI_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOI_altura())){
				cs.setString(33, null);
			}else{
				cs.setDouble(33, Double.parseDouble(graduacion.getOI_altura()));
			}
			
			
			cs.setString(34, graduacion.getFecha_emision());
			
			cs.registerOutParameter(35, Types.NUMERIC);
			cs.setString(36, graduacion.getFecha_ant());
			if (graduacion.isDiferente_add()) {
				cs.setInt(37, 1);
			}
			else
			{
				cs.setInt(37, 0);
			}
			
			if (consultaOpt.getClienteFirmo()) {
				cs.setInt(38, 1);
			}
			else
			{
				cs.setInt(38, 0);
			}
			
			/*
			if(null !=graduacion.getOD_avcl()){
				cs.setDouble(39,graduacion.getOD_avcl());
			}else{
				cs.setString(39,null);
			}
			
			if(null != graduacion.getOI_avcl()){
				cs.setDouble(40,graduacion.getOI_avcl());
			}else{
				cs.setString(40, null);
			}	
			*/
			
			if(null !=consultaOpt.getStrOD_avcl()){
				cs.setString(39,consultaOpt.getStrOD_avcl());
			}else{
				cs.setString(39,null);
			}
			
			if(null != consultaOpt.getStrOI_avcl()){
				cs.setString(40,consultaOpt.getStrOI_avcl());
			}else{
				cs.setString(40, null);
			}
			
			cs.setString(41, consultaOpt.getDerivacion());
			
			if (consultaOpt.getFiltroAzul()) {
				cs.setInt(42, 1);
			}
			else
			{
				cs.setInt(42, 0);
			}
			
			if (consultaOpt.getAntireflejo()) {
				cs.setInt(43, 1);
			}
			else
			{
				cs.setInt(43, 0);
			}
			
			if (consultaOpt.getBifocal()) {
				cs.setInt(44, 1);
			}
			else
			{
				cs.setInt(44, 0);
			}
			
			if (consultaOpt.getProgresivos()) {
				cs.setInt(45, 1);
			}
			else
			{
				cs.setInt(45, 0);
			}
			
			if (consultaOpt.getFotosensible()) {
				cs.setInt(46, 1);
			}
			else
			{
				cs.setInt(46, 0);
			}
			
			cs.execute();
			
			retorno = cs.getInt(35);
			
			
			if(retorno == Constantes.INT_CERO){				
				respuesta = true;
			}else if(Constantes.INT_MENOS_UNO == retorno){
				respuesta = false;
			}
		
			
			
			
		} catch(SQLException e){     
			log.error("ConsultaOptometricaDAOImpl:ingresaConsultaOptometrica error controlado",e);
			respuesta = false;
            throw new Exception("Error en DAO: SP_COP_INS_GRADUACION_CLI");
        }finally{
            try{
                if (null != cs){
                	log.warn("ConsultaOptometricaDAOImpl:ingresaConsultaOptometrica cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("ConsultaOptometricaDAOImpl:ingresaConsultaOptometrica cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("ConsultaOptometricaDAOImpl:ingresaGraduacion error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en ingresaConsultaOptometrica()");
            }            
        }
        
        return respuesta;
	}

	
	/***********************************************************************************
	 * Método:   traeNumeroGraduacion. REVISAR
	 * Objetivo: permite ejecutar el procedimiento en BD que ingresa una Consulta
	 *           Optométrica
	 * @param consultaOpt
	 * @return boolean, indica si se efectuó o no el ingreso de la Consulta Optométrica 
	 ***********************************************************************************/
	public int traeNumeroGraduacion(String cliente, String fecha) throws Exception{
				
		log.info("GraduacionesDAOImpl:traeNumeroGraduacion inicio");
		Connection conn = null;
		CallableStatement cs = null;
		
		int numero=0;
		try{
			log.info("GraduacionesDAOImpl:traeNumeroGraduacion conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_BUSCAR_SEL_NUMERO_COP(?,?,?)}");
			cs.setString(1, cliente);
			cs.setString(2, fecha);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.execute();
			numero = cs.getInt(3);
			
		}catch(Exception ex){
			log.error("ConsultaOptometricaDAOImpl:traeNumeroConsulta error controlado",ex);
		}finally{
			try{
                if (null != cs){
                	log.warn("ConsultaOptometricaDAOImpl:traeNumeroConsulta cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("ConsultaOptometricaDAOImpl:traeNumeroConsulta cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	throw new Exception("Error en DAO al Cerrar conexion en traeNumeroConsulta");
            }     
		}
		return numero;
	}

	
	/**********************************************************************************************
	 * Método:   modificaConsultaOptometrica. 
	 * Objetivo: permite ejecutar el procedimiento en BD que modifica una Consulta
	 *           Optométrica
	 * @param consultaOpt
	 * @return boolean, indica si se efectuó o no la modificación de la Consulta Optométrica en BD
	 **********************************************************************************************/
	 public boolean modificaConsultaOptometrica(ConsultaOptometricaBean consultaOpt) throws Exception{
		 
			log.warn("ConsultaOptometricaDAOImpl:modificaConsultaOptometrica inicio");
			Connection conn = null;
			CallableStatement cs = null;
			int retorno = Constantes.INT_CERO;
			boolean respuesta = false;
			
			GraduacionesBean graduacion = new GraduacionesBean();
			graduacion = consultaOpt.getGraduacion();
			
			
			try {
				log.info("ConsultaOptometricaDAOImpl:modificaConsultaOptometrica conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_COP_INS_GRADUACION_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
				
				
				cs.setInt(1,graduacion.getCliente());
				cs.setString(2, graduacion.getFecha());
				cs.setInt(3, graduacion.getNumero());
				cs.setString(4, graduacion.getDoctor());
				cs.setString(5, graduacion.getFecha_prox_revision());
				cs.setString(6, graduacion.getTipo());
				
				if(null != graduacion.getOD_esfera()){
					cs.setDouble(7, graduacion.getOD_esfera());
				}else{
					cs.setString(7, null);
				}			
				
				if(null != graduacion.getOD_cilindro()){
					cs.setDouble(8, graduacion.getOD_cilindro());
				}else{
					cs.setString(8, null);
				}			
				
				cs.setString(9, graduacion.getAgente());
				
				if(null != graduacion.getOD_eje()){
					cs.setInt(10, graduacion.getOD_eje());
				}else{
					cs.setString(10, null);
				}
				
				
				if(null != graduacion.getOD_adicion()){
					cs.setDouble(11, graduacion.getOD_adicion());
				}else{
					cs.setString(11, null);
				}
				
				if(null !=graduacion.getOD_esfera_cerca() ){
					cs.setDouble(12, graduacion.getOD_esfera_cerca());
				}else{
					cs.setString(12, null);
				}
				
				if(null != graduacion.getOD_n()){
					cs.setDouble(13,graduacion.getOD_n());
				}else{
					cs.setString(13, null);
				}		
				
				cs.setString(14, graduacion.getOD_obser());
				
				if(null != graduacion.getOI_esfera()){
					cs.setDouble(15, graduacion.getOI_esfera());
				}else{
					cs.setString(15, null);
				}
				
				if(null != graduacion.getOI_cilindro()){
					cs.setDouble(16, graduacion.getOI_cilindro());
				}else{
					cs.setString(16, null);
				}
				
				if(null != graduacion.getOI_eje()){
					cs.setInt(17, graduacion.getOI_eje());
				}else{
					cs.setString(17, null);
				}
				
				if(null != graduacion.getOI_adicion()){
					cs.setDouble(18, graduacion.getOI_adicion());
				}else{
					cs.setString(18, null);
				}
				
				if(null != graduacion.getOI_esfera_cerca()){
					cs.setDouble(19, graduacion.getOI_esfera_cerca());
				}else{
					cs.setString(19, null);
				}
				
				if(null != graduacion.getOI_n()){
					cs.setDouble(20,graduacion.getOI_n());
				}else{
					cs.setString(20,null);
				}
				
				cs.setString(21, graduacion.getOI_obser());
				
				if(null != graduacion.getOD_p()){
					cs.setDouble(22, graduacion.getOD_p());
				}else{
					cs.setString(22, null);
				}
				
				if(null != graduacion.getOI_p()){
					cs.setDouble(23, graduacion.getOI_p());
				}else{
					cs.setString(23, null);
				}
				
				/*
				if(null != graduacion.getOD_avsc()){
					cs.setDouble(24,graduacion.getOD_avsc());
				}else{
					cs.setString(24,null);
				}
				*/
				
				if(null != consultaOpt.getStrOD_avsc()){
					cs.setString(24,consultaOpt.getStrOD_avsc());
				}else{
					cs.setString(24,null);
				}
				
				/*
				if(null != graduacion.getOI_avsc()){
					cs.setDouble(25,graduacion.getOI_avsc());
				}else{
					cs.setString(25,null);
				}
				*/
				if(null != consultaOpt.getStrOI_avsc()){
					cs.setString(25,consultaOpt.getStrOI_avsc());
				}else{
					cs.setString(25,null);
				}
				
				/*
				if(null !=graduacion.getOD_avcc()){
					cs.setDouble(26,graduacion.getOD_avcc());
				}else{
					cs.setString(26,null);
				}
				*/
				if(null !=consultaOpt.getStrOD_avcc()){
					cs.setString(26,consultaOpt.getStrOD_avcc());
				}else{
					cs.setString(26,null);
				}
				
				/*
				if(null != graduacion.getOI_avcc()){
					cs.setDouble(27,graduacion.getOI_avcc());
				}else{
					cs.setString(27, null);
				}
				*/
				if(null != consultaOpt.getStrOI_avcc()){
					cs.setString(27,consultaOpt.getStrOI_avcc());
				}else{
					cs.setString(27, null);
				}
				
				
				if(null == graduacion.getOD_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOD_cantidad())){
					cs.setString(28,null);
				}else{
					cs.setInt(28, Integer.parseInt(graduacion.getOD_cantidad()));
				}
				
				if(null == graduacion.getOD_base() || (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOD_base()))){
					cs.setString(29, null);
				}else{
					cs.setString(29, graduacion.getOD_base());
				}			
				
				if(null == graduacion.getOD_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOD_altura())){
					cs.setString(30, null);
				}else{
					cs.setDouble(30, Double.parseDouble(graduacion.getOD_altura()));
				}			
				
				if(null == graduacion.getOI_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOI_cantidad())){
					cs.setString(31,null);
				}else{
					cs.setInt(31, Integer.parseInt(graduacion.getOI_cantidad()));
				}	
				
				if(null == graduacion.getOI_base()|| (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOI_base()))){
					cs.setString(32, null);
				}else{
					cs.setString(32, graduacion.getOI_base());
				}
				
				
				if(null == graduacion.getOI_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOI_altura())){
					cs.setString(33, null);
				}else{
					cs.setDouble(33, Double.parseDouble(graduacion.getOI_altura()));
				}
				
				
				cs.setString(34, graduacion.getFecha_emision());
				
				cs.registerOutParameter(35, Types.NUMERIC);
				
				cs.setString(36, graduacion.getFecha_ant());
				
				if (graduacion.isDiferente_add()) {
					cs.setInt(37, 1);
				}
				else
				{
					cs.setInt(37, 0);
				}
				
				if (consultaOpt.getClienteFirmo()) {
					cs.setInt(38, 1);
				}
				else
				{
					cs.setInt(38, 0);
				}
				
				/*
				if(null !=graduacion.getOD_avcl()){
					cs.setDouble(39,graduacion.getOD_avcl());
				}else{
					cs.setString(39,null);
				}
				
				if(null != graduacion.getOI_avcl()){
					cs.setDouble(40,graduacion.getOI_avcl());
				}else{
					cs.setString(40, null);
				}
				*/
				
				if(null !=consultaOpt.getStrOD_avcl()){
					cs.setString(39,consultaOpt.getStrOD_avcl());
				}else{
					cs.setString(39,null);
				}
				
				if(null != consultaOpt.getStrOI_avcl()){
					cs.setString(40,consultaOpt.getStrOI_avcl());
				}else{
					cs.setString(40, null);
				}
				
				
				cs.setString(41, consultaOpt.getDerivacion());
				
				if (consultaOpt.getFiltroAzul()) {
					cs.setInt(42, 1);
				}
				else
				{
					cs.setInt(42, 0);
				}
				
				if (consultaOpt.getAntireflejo()) {
					cs.setInt(43, 1);
				}
				else
				{
					cs.setInt(43, 0);
				}
				
				if (consultaOpt.getBifocal()) {
					cs.setInt(44, 1);
				}
				else
				{
					cs.setInt(44, 0);
				}
				
				if (consultaOpt.getProgresivos()) {
					cs.setInt(45, 1);
				}
				else
				{
					cs.setInt(45, 0);
				}
				
				if (consultaOpt.getFotosensible()) {
					cs.setInt(46, 1);
				}
				else
				{
					cs.setInt(46, 0);
				}
				
				cs.execute();
				
				retorno = cs.getInt(35);
				
				
				if(retorno == Constantes.INT_CERO){				
					respuesta = true;
				}else if(Constantes.INT_MENOS_UNO == retorno){
					respuesta = false;
				}
			
				
				
				
			} catch(SQLException e){     
				log.error("ConsultaOptometricaDAOImpl:modificaConsultaOptometrica error controlado",e);
				respuesta = false;
	            throw new Exception("Error en DAO: SP_GRAD_INS_GRAD_CLIENTES");
	        }finally{
	            try{
	                if (null != cs){
	                	log.warn("ConsultaDAOImpl:modificaConsultaOptometrica cierre CallableStatement");
	                    cs.close();
	                }
	                if (null != conn){
	                	log.warn("ConsultaOptometricaDAOImpl:modificaConsultaOptometrica cierre Connection");
	                	conn.close();
	                } 
	            }catch(SQLException e){
	            	log.error("ConsultaOptometricaDAOImpl:ingresaGraduacion error", e);
	            	throw new Exception("Error en DAO al Cerrar conexion en modificaConsultaOptometrica()");
	            }            
	        }
	        
	        return respuesta;
		}

	 
	 
	 /************************************* Nuevos Médodos para Consulta Optometrica **************/
	 
	 
	 /**********************************************************************************************
	  * Método:   traeConsultaMasRecienteCliente.  REVISAR
	  * Objetivo: permite ejecutar el procedimiento en BD que retorna los datos de la graduación
	  *           registrada en la Consulta Optométrica más reciente de un cliente en particular
	  * @param cliente
	  * @return GraduacionesBean
	  **********************************************************************************************/
	 public GraduacionesBean traeConsultaMasRecienteCliente(String cliente) throws Exception
		{
			
			log.info("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente inicio");
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			Utils util = new Utils();
			GraduacionesBean graduacion = new GraduacionesBean();
			
			try {
				log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_CONSULTA_SEL_MAS_RECIENTE(?,?)}");
				cs.setString(1, cliente);
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				
				cs.execute();
				rs = (ResultSet)cs.getObject(2);
				while (rs.next()) {
					log.debug("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente entrando ciclo while");
					graduacion.setCodigo(0);
					graduacion.setFecha(util.formatoFecha((rs.getDate("COP_FECHA"))));
					
					if(null != rs.getString("DOCTOR")){
						graduacion.setDoctor( rs.getString("DOCTOR_NOM") + " " + rs.getString("DOCTOR"));
					}else{
						graduacion.setDoctor( rs.getString("DOCTOR_NOM") + " " + "");
					}
					
					
					
					graduacion.setNumero(rs.getInt("COP_NUMERO"));
					graduacion.setOrden(rs.getInt("COP_NUMERO")+"");
					//graduacion.setOrden("");
					graduacion.setOD_adicion(rs.getDouble("COP_ODADICION"));
					graduacion.setOD_cilindro(rs.getDouble("COP_ODCILINDRO"));
					graduacion.setOD_eje(rs.getInt("COP_ODEJE"));
					graduacion.setOD_esfera(rs.getDouble("COP_ODESFERA"));
					graduacion.setOD_diametro(0.0);
					graduacion.setOD_esfera_cerca(rs.getDouble("COP_ODESFERAC"));
					graduacion.setOD_n(rs.getDouble("COP_ODNP1"));
					graduacion.setOD_p(rs.getDouble("COP_ODNP2"));
					graduacion.setOI_adicion(rs.getDouble("COP_OIADICION"));
					graduacion.setOI_cilindro(rs.getDouble("COP_OICILINDRO"));
					graduacion.setOI_eje(rs.getInt("COP_OIEJE"));
					graduacion.setOI_esfera(rs.getDouble("COP_OIESFERA"));
					graduacion.setOI_diametro(0.0);
					graduacion.setOI_esfera_cerca(rs.getDouble("COP_OIESFERAC"));
					graduacion.setOI_n(rs.getDouble("COP_OINP1"));
					graduacion.setOI_p(rs.getDouble("COP_OINP2"));
				}
				return graduacion;
				
			} catch(SQLException e){       
				log.error("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente error controlado",e);
		        throw new Exception("Error en DAO: SP_CONSULTA_SEL_MAS_RECIENTE");
		    }finally{
		        try{
		            if (null !=  rs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente cierre ResultSet");
		                rs.close();
		            }
		            if (null !=  cs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente cierre CallableStatement");
		                cs.close();
		            }
		            if (null !=  conn){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente cierre Connection");
		            	conn.close();
		            } 
		        }catch(SQLException e){
		        	log.error("ConsultaOptometricaDAOImpl:traeConsultaMasRecienteCliente error", e);
		        	throw new Exception("Error en DAO al Cerrar conexion en traeConsultaMasRecienteCliente()");
		        }            
		    }
		}
	 
	 
	 /**********************************************************************************************
	  * Método:   traeGraduacionFecha.  
	  * Objetivo: permite ejecutar el procedimiento en BD que retorna los datos de la Consulta 
	  *           Optométrica de un cliente dada su identificador, una fecha y el número de la
	  *           Consulta
	  * @param cliente
	  * @param fecha
	  * @param numero
	  * @return ConsultaOptometricaBean
	  **********************************************************************************************/
	 public ConsultaOptometricaBean traeGraduacionFecha(String cliente, String fecha, Double numero)throws Exception
		{
			
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			Utils util = new Utils();
			
			ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
			GraduacionesBean graduacion = new GraduacionesBean();
			
			try {
				log.info("ConsultaOptometricaDAOImpl:traeGraduacionFecha conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_COP_SEL_GRADUACION_FECHA(?,?,?,?)}");
				cs.setString(1, cliente);
				cs.setString(2, fecha);
				cs.setDouble(3, numero);
				
				cs.registerOutParameter(4, OracleTypes.CURSOR);
				
				cs.execute();
				rs = (ResultSet)cs.getObject(4);
				while (rs.next()) {
					log.debug("ConsultaOptometricaDAOImpl:traeGraduacionFecha entrando ciclo while");
					graduacion.setCodigo(0);
					graduacion.setAgente(rs.getString("COP_AGENTE"));
					graduacion.setFecha(util.formatoFecha((rs.getDate("COP_FECHA"))));
					graduacion.setDoctor(rs.getString("DOCTOR"));
					graduacion.setCod_doctor(rs.getString("CODDOCTOR"));				
					graduacion.setNumero(rs.getInt("COP_NUMERO"));
					graduacion.setOrden(Constantes.STRING_BLANCO);
					
					if(null != rs.getString("COP_ODADICION")){
						graduacion.setOD_adicion(rs.getDouble("COP_ODADICION"));
					}else{
						graduacion.setOD_adicion(null);
					}
					
					if(null != rs.getString("COP_ODCILINDRO")){
						graduacion.setOD_cilindro(rs.getDouble("COP_ODCILINDRO"));
					}else{
						graduacion.setOD_cilindro(null);
					}
					
					if(null != rs.getString("COP_ODEJE")){
						graduacion.setOD_eje(rs.getInt("COP_ODEJE"));
					}else{
						graduacion.setOD_eje(null);
					}
					
					if(null !=rs.getString("COP_ODESFERA")){
						graduacion.setOD_esfera(rs.getDouble("COP_ODESFERA"));
					}else{
						graduacion.setOD_esfera(null);
					}
					
					graduacion.setOD_diametro(0.0);
					
					if(null != rs.getString("COP_ODESFERAC")){
						graduacion.setOD_esfera_cerca(rs.getDouble("COP_ODESFERAC"));
					}else{
						graduacion.setOD_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_ODNP1")){
						graduacion.setOD_n(rs.getDouble("COP_ODNP1"));
					}else{
						graduacion.setOD_n(null);
					}
					
					if(null != rs.getString("COP_ODNP2")){
						graduacion.setOD_p(rs.getDouble("COP_ODNP2"));
					}else{
						graduacion.setOD_p(null);
					}
					
					/*
					if(null != rs.getString("COP_ODAVSC")){
						graduacion.setOD_avsc(rs.getDouble("COP_ODAVSC"));
					}else{
						graduacion.setOD_avsc(null);
					}
					
					if(null != rs.getString("COP_ODAVCC")){
						graduacion.setOD_avcc(rs.getDouble("COP_ODAVCC"));
					}else{
						graduacion.setOD_avcc(null);
					}	
					
					if(null != rs.getString("COP_ODAVCL")){
						graduacion.setOD_avcl(rs.getDouble("COP_ODAVCL"));
					}else{
						graduacion.setOD_avcl(null);
					}
					*/
					
					
					if(null != rs.getString("COP_ODAVSC")){
						consultaOpt.setStrOD_avsc(rs.getString("COP_ODAVSC"));
					}else{
						consultaOpt.setStrOD_avsc(Constantes.STRING_BLANCO);
					}
					
					if(null != rs.getString("COP_ODAVCC")){
						consultaOpt.setStrOD_avcc(rs.getString("COP_ODAVCC"));
					}else{
						consultaOpt.setStrOD_avcc(Constantes.STRING_BLANCO);
					}	
					
					if(null != rs.getString("COP_ODAVCL")){
						consultaOpt.setStrOD_avcl(rs.getString("COP_ODAVCL"));
					}else{
						consultaOpt.setStrOD_avcl(Constantes.STRING_BLANCO);
					}
					
					
					graduacion.setOD_obser(rs.getString("COP_ODOBSER"));
					graduacion.setOD_cantidad(String.valueOf(rs.getInt("COP_ODCANTIDAD")));
					graduacion.setOD_base(rs.getString("COP_ODBASE"));
					
					graduacion.setOD_altura(rs.getString("COP_ODALTURA"));
					
					if(null !=rs.getString("COP_OIADICION")){
						graduacion.setOI_adicion(rs.getDouble("COP_OIADICION"));
					}else{
						graduacion.setOI_adicion(null);
					}
					
					if(null != rs.getString("COP_OICILINDRO")){
						graduacion.setOI_cilindro(rs.getDouble("COP_OICILINDRO"));
					}else{
						graduacion.setOI_cilindro(null);
					}
					
					if(null !=rs.getString("COP_OIEJE")){
						graduacion.setOI_eje(rs.getInt("COP_OIEJE"));
					}else{
						graduacion.setOI_eje(null);
					}
					
					
					if(null != rs.getString("COP_OIESFERA")){
						graduacion.setOI_esfera(rs.getDouble("COP_OIESFERA"));
					}else{
						graduacion.setOI_esfera(null);
					}
					
					
					graduacion.setOI_diametro(0.0);
					
					if(null != rs.getString("COP_OIESFERAC")){
						graduacion.setOI_esfera_cerca(rs.getDouble("COP_OIESFERAC"));
					}else{
						graduacion.setOI_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_OINP1")){
						graduacion.setOI_n(rs.getDouble("COP_OINP1"));
					}else{
						graduacion.setOI_n(null);
					}					
						
					if(null != rs.getString("COP_OINP2")){
						graduacion.setOI_p(rs.getDouble("COP_OINP2"));
					}else{
						graduacion.setOI_p(null);
					}
					
					/*
					if(null !=rs.getString("COP_OIAVSC")){
						graduacion.setOI_avsc(rs.getDouble("COP_OIAVSC"));
					}else{
						graduacion.setOI_avsc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCC")){
						graduacion.setOI_avcc(rs.getDouble("COP_OIAVCC"));
					}else{
						graduacion.setOI_avcc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCL")){
						graduacion.setOI_avcl(rs.getDouble("COP_OIAVCL"));
					}else{
						graduacion.setOI_avcl(null);
					}
					*/
					
					if(null !=rs.getString("COP_OIAVSC")){
						consultaOpt.setStrOI_avsc(rs.getString("COP_OIAVSC"));
					}else{
						consultaOpt.setStrOI_avsc(Constantes.STRING_BLANCO);
					}
					
					if(null !=rs.getString("COP_OIAVCC")){
						consultaOpt.setStrOI_avcc(rs.getString("COP_OIAVCC"));
					}else{
						consultaOpt.setStrOI_avcc(Constantes.STRING_BLANCO);
					}
					
					if(null !=rs.getString("COP_OIAVCL")){
						consultaOpt.setStrOI_avcl(rs.getString("COP_OIAVCL"));
					}else{
						consultaOpt.setStrOI_avcl(Constantes.STRING_BLANCO);
					}
					
					
					graduacion.setOI_obser(rs.getString("COP_OIOBSERVA"));
					graduacion.setOI_cantidad(String.valueOf(rs.getInt("COP_OICANTIDAD")));
					graduacion.setOI_base(rs.getString("COP_OIBASE"));
					graduacion.setOI_altura(rs.getString("COP_OIALTURA"));
					graduacion.setTipo(rs.getString("COP_TIPO"));
					graduacion.setDiferente_add(rs.getBoolean("COP_DIFADICION"));
					graduacion.setFecha_emision(util.formatoFecha((rs.getDate("COP_FECEMI"))));
					graduacion.setFecha_prox_revision(util.formatoFecha((rs.getDate("COP_FECREV"))));
					
					consultaOpt.setClienteFirmo(rs.getBoolean("COP_FIRMO"));
					consultaOpt.setDerivacion(rs.getString("COP_DERIVACION"));
					consultaOpt.setNumCodigo(rs.getInt("COP_CDG"));
					
					consultaOpt.setFiltroAzul(rs.getBoolean("COP_FILTROAZUL"));
					consultaOpt.setAntireflejo(rs.getBoolean("COP_ANTIREFLEJO"));
					consultaOpt.setBifocal(rs.getBoolean("COP_BIFOCAL"));
					consultaOpt.setProgresivos(rs.getBoolean("COP_PROGRESIVOS"));
					consultaOpt.setFotosensible(rs.getBoolean("COP_FOTOSENSIBLE"));
					
				}
				
				consultaOpt.setGraduacion(graduacion);
				
				return consultaOpt;
				
			} catch(SQLException e){  
				log.error("ConsultaOptometricaDAOImpl:traeGraduacionFecha error controlado",e);
		        throw new Exception("Error en DAO: SP_COP_SEL_GRADUACION_FECHA");
		    }finally{
		        try{
		            if (null != rs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionFecha cierre ResultSet");
		                rs.close();
		            }
		            if (null != cs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionFecha cierre CallableStatement");
		                cs.close();
		            }
		            if (null != conn ){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionFecha cierre Connection");
		            	conn.close();
		            } 
		        }catch(SQLException e){
		        	log.error("ConsultaOptometricaDAOImpl:traeGraduacionFecha error", e);
		        	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionFecha()");
		        }            
		    }
		    
		}
	 
	 /**********************************************************************************************
	  * Método:   traeGraduacionCodigo.  
	  * Objetivo: permite ejecutar el procedimiento en BD que retorna los datos de la Consulta 
	  *           Optométrica dado su código interno
	  * @param dCodigo
	  * @return ConsultaOptometricaBean
	  **********************************************************************************************/
	 public ConsultaOptometricaBean traeGraduacionCodigo(int dCodigo)throws Exception
		{
			
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			Utils util = new Utils();
			
			ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
			GraduacionesBean graduacion = new GraduacionesBean();
			ClienteBean cliente = new ClienteBean();
			
			try {
				log.info("ConsultaOptometricaDAOImpl:traeGraduacionCodigo conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_COP_SEL_GRADUACION_CDG(?,?)}");
				
				cs.setDouble(1, dCodigo);
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				
				cs.execute();
				rs = (ResultSet)cs.getObject(2);
				while (rs.next()) {
					log.debug("ConsultaOptometricaDAOImpl:traeGraduacionCodigo entrando ciclo while");
					graduacion.setCodigo(0);
					graduacion.setAgente(rs.getString("COP_AGENTE"));
					graduacion.setFecha(util.formatoFecha((rs.getDate("COP_FECHA"))));
					graduacion.setDoctor(rs.getString("DOCTOR"));
					graduacion.setCod_doctor(rs.getString("CODDOCTOR"));				
					graduacion.setNumero(rs.getInt("COP_NUMERO"));
					graduacion.setOrden(Constantes.STRING_BLANCO);
					
					if(null != rs.getString("COP_ODADICION")){
						graduacion.setOD_adicion(rs.getDouble("COP_ODADICION"));
					}else{
						graduacion.setOD_adicion(null);
					}
					
					if(null != rs.getString("COP_ODCILINDRO")){
						graduacion.setOD_cilindro(rs.getDouble("COP_ODCILINDRO"));
					}else{
						graduacion.setOD_cilindro(null);
					}
					
					if(null != rs.getString("COP_ODEJE")){
						graduacion.setOD_eje(rs.getInt("COP_ODEJE"));
					}else{
						graduacion.setOD_eje(null);
					}
					
					if(null !=rs.getString("COP_ODESFERA")){
						graduacion.setOD_esfera(rs.getDouble("COP_ODESFERA"));
					}else{
						graduacion.setOD_esfera(null);
					}
					
					graduacion.setOD_diametro(0.0);
					
					if(null != rs.getString("COP_ODESFERAC")){
						graduacion.setOD_esfera_cerca(rs.getDouble("COP_ODESFERAC"));
					}else{
						graduacion.setOD_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_ODNP1")){
						graduacion.setOD_n(rs.getDouble("COP_ODNP1"));
					}else{
						graduacion.setOD_n(null);
					}
					
					if(null != rs.getString("COP_ODNP2")){
						graduacion.setOD_p(rs.getDouble("COP_ODNP2"));
					}else{
						graduacion.setOD_p(null);
					}
					
					/*
					if(null != rs.getString("COP_ODAVSC")){
						graduacion.setOD_avsc(rs.getDouble("COP_ODAVSC"));
					}else{
						graduacion.setOD_avsc(null);
					}
					
					if(null != rs.getString("COP_ODAVCC")){
						graduacion.setOD_avcc(rs.getDouble("COP_ODAVCC"));
					}else{
						graduacion.setOD_avcc(null);
					}	
					
					if(null != rs.getString("COP_ODAVCL")){
						graduacion.setOD_avcl(rs.getDouble("COP_ODAVCL"));
					}else{
						graduacion.setOD_avcl(null);
					}
					*/
					consultaOpt.setStrOD_avsc(rs.getString("COP_ODAVSC"));
					consultaOpt.setStrOD_avcc(rs.getString("COP_ODAVCC"));
					consultaOpt.setStrOD_avcl(rs.getString("COP_ODAVCL"));
					
					graduacion.setOD_obser(rs.getString("COP_ODOBSER"));
					graduacion.setOD_cantidad(String.valueOf(rs.getInt("COP_ODCANTIDAD")));
					graduacion.setOD_base(rs.getString("COP_ODBASE"));
					
					graduacion.setOD_altura(rs.getString("COP_ODALTURA"));
					
					if(null !=rs.getString("COP_OIADICION")){
						graduacion.setOI_adicion(rs.getDouble("COP_OIADICION"));
					}else{
						graduacion.setOI_adicion(null);
					}
					
					if(null != rs.getString("COP_OICILINDRO")){
						graduacion.setOI_cilindro(rs.getDouble("COP_OICILINDRO"));
					}else{
						graduacion.setOI_cilindro(null);
					}
					
					if(null !=rs.getString("COP_OIEJE")){
						graduacion.setOI_eje(rs.getInt("COP_OIEJE"));
					}else{
						graduacion.setOI_eje(null);
					}
					
					
					if(null != rs.getString("COP_OIESFERA")){
						graduacion.setOI_esfera(rs.getDouble("COP_OIESFERA"));
					}else{
						graduacion.setOI_esfera(null);
					}
					
					
					graduacion.setOI_diametro(0.0);
					
					if(null != rs.getString("COP_OIESFERAC")){
						graduacion.setOI_esfera_cerca(rs.getDouble("COP_OIESFERAC"));
					}else{
						graduacion.setOI_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_OINP1")){
						graduacion.setOI_n(rs.getDouble("COP_OINP1"));
					}else{
						graduacion.setOI_n(null);
					}					
						
					if(null != rs.getString("COP_OINP2")){
						graduacion.setOI_p(rs.getDouble("COP_OINP2"));
					}else{
						graduacion.setOI_p(null);
					}
					
					/*
					if(null !=rs.getString("COP_OIAVSC")){
						graduacion.setOI_avsc(rs.getDouble("COP_OIAVSC"));
					}else{
						graduacion.setOI_avsc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCC")){
						graduacion.setOI_avcc(rs.getDouble("COP_OIAVCC"));
					}else{
						graduacion.setOI_avcc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCL")){
						graduacion.setOI_avcl(rs.getDouble("COP_OIAVCL"));
					}else{
						graduacion.setOI_avcl(null);
					}
					*/
					
					consultaOpt.setStrOD_avsc(rs.getString("COP_OIAVSC"));
					consultaOpt.setStrOD_avcc(rs.getString("COP_OIAVCC"));
					consultaOpt.setStrOD_avcl(rs.getString("COP_OIAVCL"));
					
					graduacion.setOI_obser(rs.getString("COP_OIOBSERVA"));
					graduacion.setOI_cantidad(String.valueOf(rs.getInt("COP_OICANTIDAD")));
					graduacion.setOI_base(rs.getString("COP_OIBASE"));
					graduacion.setOI_altura(rs.getString("COP_OIALTURA"));
					graduacion.setTipo(rs.getString("COP_TIPO"));
					graduacion.setDiferente_add(rs.getBoolean("COP_DIFADICION"));
					graduacion.setFecha_emision(util.formatoFecha((rs.getDate("COP_FECEMI"))));
					graduacion.setFecha_prox_revision(util.formatoFecha((rs.getDate("COP_FECREV"))));
					
					cliente.setNombre(rs.getString("NOMBRECLI"));
					cliente.setApellido(rs.getString("APELLIDOCLI"));
					cliente.setNif(rs.getString("NIFCLI"));
					cliente.setDvnif(rs.getString("LETRANIFCLI"));
					cliente.setFono_movil(rs.getString("TELFCLI"));
					
					consultaOpt.setClienteFirmo(rs.getBoolean("COP_FIRMO"));
					consultaOpt.setDerivacion(rs.getString("COP_DERIVACION"));

					consultaOpt.setFiltroAzul(rs.getBoolean("COP_FILTROAZUL"));
					consultaOpt.setAntireflejo(rs.getBoolean("COP_ANTIREFLEJO"));
					consultaOpt.setBifocal(rs.getBoolean("COP_BIFOCAL"));
					consultaOpt.setProgresivos(rs.getBoolean("COP_PROGRESIVOS"));
					consultaOpt.setFotosensible(rs.getBoolean("COP_FOTOSENSIBLE"));
				    
				}
				
				consultaOpt.setGraduacion(graduacion);
				consultaOpt.setCliente(cliente);
				
				return consultaOpt;
				
			} catch(SQLException e){  
				log.error("ConsultaOptometricaDAOImpl:traeGraduacionCodigo error controlado",e);
		        throw new Exception("Error en DAO: SP_COP_SEL_GRADUACION_CDG");
		    }finally{
		        try{
		            if (null != rs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionCodigo cierre ResultSet");
		                rs.close();
		            }
		            if (null != cs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionCodigo cierre CallableStatement");
		                cs.close();
		            }
		            if (null != conn ){
		            	log.warn("ConsultaOptometricaDAOImpl:traeGraduacionCodigo cierre Connection");
		            	conn.close();
		            } 
		        }catch(SQLException e){
		        	log.error("ConsultaOptometricaDAOImpl:traeGraduacionCodigo error", e);
		        	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionCodigo()");
		        }            
		    }
		    
		}
	 
	 
	 /**********************************************************************************************
	  * Método:   traeConsultaMasReciente.  
	  * Objetivo: permite ejecutar el procedimiento en BD que retorna los datos de la Consulta 
	  *           Optométrica más reciente de un cliente
	  * @param cdgCliente
	  * @return ConsultaOptometricaBean
	  **********************************************************************************************/
	 public ConsultaOptometricaBean traeConsultaMasReciente(String cdgCliente)throws Exception
		{
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			Utils util = new Utils();
			
			ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
			GraduacionesBean graduacion = new GraduacionesBean();
			ClienteBean cliente = new ClienteBean();
			
			try {
				log.info("ConsultaOptometricaDAOImpl:traeGraduacionCodigo conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_COP_SEL_GRAD_MAS_RECIENTE(?,?)}");
				
				cs.setString(1, cdgCliente);
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				
				cs.execute();
				rs = (ResultSet)cs.getObject(2);
				while (rs.next()) {
					log.debug("ConsultaOptometricaDAOImpl:traeConsultaMasReciente entrando ciclo while");
					graduacion.setCodigo(0);
					graduacion.setAgente(rs.getString("COP_AGENTE"));
					graduacion.setFecha(util.formatoFecha((rs.getDate("COP_FECHA"))));
					graduacion.setDoctor(Constantes.STRING_BLANCO);
					graduacion.setCod_doctor(Constantes.STRING_BLANCO);				
					graduacion.setNumero(rs.getInt("COP_NUMERO"));
					graduacion.setOrden(Constantes.STRING_BLANCO);
					
					if(null != rs.getString("COP_ODADICION")){
						graduacion.setOD_adicion(rs.getDouble("COP_ODADICION"));
					}else{
						graduacion.setOD_adicion(null);
					}
					
					if(null != rs.getString("COP_ODCILINDRO")){
						graduacion.setOD_cilindro(rs.getDouble("COP_ODCILINDRO"));
					}else{
						graduacion.setOD_cilindro(null);
					}
					
					if(null != rs.getString("COP_ODEJE")){
						graduacion.setOD_eje(rs.getInt("COP_ODEJE"));
					}else{
						graduacion.setOD_eje(null);
					}
					
					if(null !=rs.getString("COP_ODESFERA")){
						graduacion.setOD_esfera(rs.getDouble("COP_ODESFERA"));
					}else{
						graduacion.setOD_esfera(null);
					}
					
					graduacion.setOD_diametro(0.0);
					
					if(null != rs.getString("COP_ODESFERAC")){
						graduacion.setOD_esfera_cerca(rs.getDouble("COP_ODESFERAC"));
					}else{
						graduacion.setOD_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_ODNP1")){
						graduacion.setOD_n(rs.getDouble("COP_ODNP1"));
					}else{
						graduacion.setOD_n(null);
					}
					
					if(null != rs.getString("COP_ODNP2")){
						graduacion.setOD_p(rs.getDouble("COP_ODNP2"));
					}else{
						graduacion.setOD_p(null);
					}
					
					/*
					if(null != rs.getString("COP_ODAVSC")){
						graduacion.setOD_avsc(rs.getDouble("COP_ODAVSC"));
					}else{
						graduacion.setOD_avsc(null);
					}
					
					if(null != rs.getString("COP_ODAVCC")){
						graduacion.setOD_avcc(rs.getDouble("COP_ODAVCC"));
					}else{
						graduacion.setOD_avcc(null);
					}	
					
					if(null != rs.getString("COP_ODAVCL")){
						graduacion.setOD_avcl(rs.getDouble("COP_ODAVCL"));
					}else{
						graduacion.setOD_avcl(null);
					}	
					*/
					consultaOpt.setStrOD_avsc(rs.getString("COP_ODAVSC"));
					consultaOpt.setStrOD_avcc(rs.getString("COP_ODAVCC"));
					consultaOpt.setStrOD_avcl(rs.getString("COP_ODAVCL"));
					graduacion.setOD_obser(rs.getString("COP_ODOBSER"));
					graduacion.setOD_cantidad(String.valueOf(rs.getInt("COP_ODCANTIDAD")));
					graduacion.setOD_base(rs.getString("COP_ODBASE"));
					
					graduacion.setOD_altura(rs.getString("COP_ODALTURA"));
					
					if(null !=rs.getString("COP_OIADICION")){
						graduacion.setOI_adicion(rs.getDouble("COP_OIADICION"));
					}else{
						graduacion.setOI_adicion(null);
					}
					
					if(null != rs.getString("COP_OICILINDRO")){
						graduacion.setOI_cilindro(rs.getDouble("COP_OICILINDRO"));
					}else{
						graduacion.setOI_cilindro(null);
					}
					
					if(null !=rs.getString("COP_OIEJE")){
						graduacion.setOI_eje(rs.getInt("COP_OIEJE"));
					}else{
						graduacion.setOI_eje(null);
					}
					
					
					if(null != rs.getString("COP_OIESFERA")){
						graduacion.setOI_esfera(rs.getDouble("COP_OIESFERA"));
					}else{
						graduacion.setOI_esfera(null);
					}
					
					
					graduacion.setOI_diametro(0.0);
					
					if(null != rs.getString("COP_OIESFERAC")){
						graduacion.setOI_esfera_cerca(rs.getDouble("COP_OIESFERAC"));
					}else{
						graduacion.setOI_esfera_cerca(null);
					}
					
					if(null != rs.getString("COP_OINP1")){
						graduacion.setOI_n(rs.getDouble("COP_OINP1"));
					}else{
						graduacion.setOI_n(null);
					}					
						
					if(null != rs.getString("COP_OINP2")){
						graduacion.setOI_p(rs.getDouble("COP_OINP2"));
					}else{
						graduacion.setOI_p(null);
					}
					
					/*
					if(null !=rs.getString("COP_OIAVSC")){
						graduacion.setOI_avsc(rs.getDouble("COP_OIAVSC"));
					}else{
						graduacion.setOI_avsc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCC")){
						graduacion.setOI_avcc(rs.getDouble("COP_OIAVCC"));
					}else{
						graduacion.setOI_avcc(null);
					}
					
					if(null !=rs.getString("COP_OIAVCL")){
						graduacion.setOI_avcl(rs.getDouble("COP_OIAVCL"));
					}else{
						graduacion.setOI_avcl(null);
					}
					*/
					
					consultaOpt.setStrOD_avsc(rs.getString("COP_OIAVSC"));
					consultaOpt.setStrOD_avcc(rs.getString("COP_OIAVCC"));
					consultaOpt.setStrOD_avcl(rs.getString("COP_OIAVCL"));
					
					graduacion.setOI_obser(rs.getString("COP_OIOBSERVA"));
					graduacion.setOI_cantidad(String.valueOf(rs.getInt("COP_OICANTIDAD")));
					graduacion.setOI_base(rs.getString("COP_OIBASE"));
					graduacion.setOI_altura(rs.getString("COP_OIALTURA"));
					graduacion.setTipo(rs.getString("COP_TIPO"));
					graduacion.setDiferente_add(rs.getBoolean("COP_DIFADICION"));
					graduacion.setFecha_emision(util.formatoFecha((rs.getDate("COP_FECEMI"))));
					graduacion.setFecha_prox_revision(util.formatoFecha((rs.getDate("COP_FECREV"))));
					
					cliente.setNombre(rs.getString("NOMBRECLI"));
					cliente.setApellido(rs.getString("APELLIDOCLI"));
					cliente.setNif(rs.getString("NIFCLI"));
					cliente.setDvnif(rs.getString("LETRANIFCLI"));
					cliente.setFono_movil(rs.getString("TELFCLI"));
					
					consultaOpt.setClienteFirmo(rs.getBoolean("COP_FIRMO"));
					consultaOpt.setDerivacion(rs.getString("COP_DERIVACION"));
					
					consultaOpt.setFiltroAzul(rs.getBoolean("COP_FILTROAZUL"));
					consultaOpt.setAntireflejo(rs.getBoolean("COP_ANTIREFLEJO"));
					consultaOpt.setBifocal(rs.getBoolean("COP_BIFOCAL"));
					consultaOpt.setProgresivos(rs.getBoolean("COP_PROGRESIVOS"));
					consultaOpt.setFotosensible(rs.getBoolean("COP_FOTOSENSIBLE"));
				    
				}
				
				consultaOpt.setGraduacion(graduacion);
				consultaOpt.setCliente(cliente);
				
				return consultaOpt;
				
			} catch(SQLException e){  
				log.error("ConsultaOptometricaDAOImpl:traeConsultaMasReciente error controlado",e);
		        throw new Exception("Error en DAO: SP_COP_SEL_GRAD_MAS_RECIENTE");
		    }finally{
		        try{
		            if (null != rs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasReciente cierre ResultSet");
		                rs.close();
		            }
		            if (null != cs){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasReciente cierre CallableStatement");
		                cs.close();
		            }
		            if (null != conn ){
		            	log.warn("ConsultaOptometricaDAOImpl:traeConsultaMasReciente cierre Connection");
		            	conn.close();
		            } 
		        }catch(SQLException e){
		        	log.error("ConsultaOptometricaDAOImpl:traeConsultaMasReciente error", e);
		        	throw new Exception("Error en DAO al Cerrar conexion en traeConsultaMasReciente()");
		        }            
		    }
		    
		}
	 
	 public ArrayList<AgenteBean> traeTecnicos(String local) throws Exception
	    {
	    	log.info("ConsultaOptometricaDAOImpl:traeTecnicos inicio");
	        ArrayList<AgenteBean> lista_Tecnicos = new ArrayList<AgenteBean>();
	        Connection conn = null;
	        ResultSet rs = null;
	        CallableStatement st= null;
	       
	        try {
	        	log.info("ConsultaOptometricaDAOImpl:traeTecnicos conectando base datos");
	            conn = ConexionFactory.INSTANCE.getConexion();
	            //st = conn.prepareCall("{call SP_UTIL_SEL_AGENTES(?,?)}");
	            st = conn.prepareCall("{call SP_COP_SEL_TECNICOS(?,?)}");
	            st.setString(1, local);
	            st.registerOutParameter(2, OracleTypes.CURSOR);
	            st.execute();
	            rs = (ResultSet)st.getObject(2);
	           
	            AgenteBean agenteTecnico;
	           
	            while (rs.next())
	            {
	            	log.debug("UtilesDAOImpl:traeAgentes entrando ciclo while");
	                agenteTecnico = new AgenteBean();
	                agenteTecnico.setUsuario(rs.getString("IDENT"));
	                agenteTecnico.setNombre_completo(rs.getString("DESCRIPTION"));
	                lista_Tecnicos.add(agenteTecnico);
	            }
	           
	            return lista_Tecnicos;
	           
	        } catch (Exception e) {
	        	log.error("ConsultaOptmetricaDAOImpl:traeTecnicos error controlado",e);
	             throw new Exception("Error en DAO, SP_COP_SEL_TECNICOS");
	        } finally {
	               try{
	                if (null != rs){
	                	log.warn("ConsultaOptmetricaDAOImpl:traeTecnicos cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("ConsultaOptmetricaDAOImpl:traeTecnicos cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("ConsultaOptmetricaDAOImpl:traeTecnicos cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("ConsultaOptmetricaDAOImpl:traeTecnicos error", e);
	            }
	        }
	       
	    }
}
