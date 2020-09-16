package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ConsultaOptometricaBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosMedicoFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosConsultaOptometricaFacade;
import cl.gmo.pos.venta.web.forms.ConsultaOptometricaForm;

public class ConsultaOptometricaHelper extends Utils{

	/***********************************************************************************
	 * Método:   traeGraduacionesCliente.
	 * Objetivo: retorna todas las graduaciones registradas en el sistema, del cliente 
	 *           que se suministra como parámetro.
	 * @param cliente
	 * @return ArrayList<GraduacionesBean>
	 ***********************************************************************************/
	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception{
		
		ArrayList<GraduacionesBean> lista_graduaciones = new ArrayList<GraduacionesBean>();
		lista_graduaciones = PosConsultaOptometricaFacade.traeGraduacionesClientes(cliente);
		
		return lista_graduaciones;
	}

	
	/***********************************************************************************
	 * Método:   traeGraduacionFecha.
	 * Objetivo: retorna un Formulario de Consulta Optométrica con los datos de la 
	 *           graduación dado el cliente, la fecha y el número de la graduación
	 * @param cliente
	 * @param fecha
	 * @param número de graduación
	 * @return ConsultaOptometricaForm
	 ***********************************************************************************/
	public ConsultaOptometricaForm traeGraduacionFecha(ConsultaOptometricaForm formulario) {
		
		ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
		GraduacionesBean graduacion = new GraduacionesBean();
		
		try{
			
			consultaOpt = PosConsultaOptometricaFacade.traeGraduacionFecha(String.valueOf(formulario.getCliente()), formulario.getFecha_graduacion(), formulario.getNumero_graduacion());
			
			graduacion = consultaOpt.getGraduacion();
			
			int numerograd = 0;
			
			if(0 != formulario.getNumero_graduacion() && !("".equals(formulario.getNumero_graduacion()))){
				numerograd = (int) formulario.getNumero_graduacion();
			}else{
				numerograd =-1;
			}			
			 
			String fech = formulario.getFecha_graduacion() ;
			String cliente = String.valueOf(formulario.getCliente());
			String pagina = "OPTOMETRIA";
			
			/***/
			formulario.setExiste_graduacion("false");
			/***/
				
			formulario.setCod_doctor(graduacion.getCod_doctor());
			formulario.setTipo(graduacion.getTipo());
			formulario.setFechaEmision(graduacion.getFecha_emision());
			formulario.setFechaProxRevision(graduacion.getFecha_prox_revision());
			formulario.setAgente(graduacion.getAgente());
			formulario.setDoctor(graduacion.getDoctor());
			
			if(null !=graduacion.getOD_esfera()){
				formulario.setOD_esfera(graduacion.getOD_esfera().toString());
			}else{
				formulario.setOD_esfera(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_cilindro()){
				formulario.setOD_cilindro(graduacion.getOD_cilindro().toString());
			}else{
				formulario.setOD_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_eje()){
				formulario.setOD_eje(graduacion.getOD_eje().toString());
			}else{
				formulario.setOD_eje(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_esfera_cerca()){
				formulario.setOD_cerca(graduacion.getOD_esfera_cerca().toString());
			}else{
				formulario.setOD_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_adicion()){
				formulario.setOD_adicion(graduacion.getOD_adicion().toString());
			}else{
				formulario.setOD_adicion(Constantes.STRING_BLANCO);		
			}
			
			if(null != graduacion.getOD_n()){
				formulario.setOD_dnpl(graduacion.getOD_n().toString());
			}else{
				formulario.setOD_dnpl(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_p()){
				formulario.setOD_dnpc(graduacion.getOD_p().toString());
			}else{
				formulario.setOD_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOD_avsc()){
				formulario.setOD_avsc(consultaOpt.getStrOD_avsc());
			}else{
				formulario.setOD_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOD_avcc()){
				formulario.setOD_avcc(consultaOpt.getStrOD_avcc());
			}else{
				formulario.setOD_avcc(Constantes.STRING_BLANCO);
			}			
			
			if(null != consultaOpt.getStrOD_avcl()){
				formulario.setOD_avcl(consultaOpt.getStrOD_avcl());
			}else{
				formulario.setOD_avcl(Constantes.STRING_BLANCO);
			}	
			
			formulario.setOD_cantidad(graduacion.getOD_cantidad());
			formulario.setOD_base(graduacion.getOD_base());
			
			if(null != graduacion.getOD_altura()){
				formulario.setOD_altura(graduacion.getOD_altura());
			}else{
				formulario.setOD_altura(Constantes.STRING_BLANCO);
			}
			
			
			formulario.setOD_observaciones(graduacion.getOD_obser());
			
			if(null != graduacion.getOI_esfera()){
				formulario.setOI_esfera(graduacion.getOI_esfera().toString());
			}else{
				formulario.setOI_esfera(Constantes.STRING_BLANCO);
			}
						
			if(null != graduacion.getOI_cilindro()){
				formulario.setOI_cilindro(graduacion.getOI_cilindro().toString());
			}else{
				formulario.setOI_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_eje()){
				formulario.setOI_eje(graduacion.getOI_eje().toString());
			}else{
				formulario.setOI_eje(Constantes.STRING_BLANCO);
			}
			
			if(null !=graduacion.getOI_esfera_cerca()){
				formulario.setOI_cerca(graduacion.getOI_esfera_cerca().toString());
			}else{
				formulario.setOI_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_adicion()){
				formulario.setOI_adicion(graduacion.getOI_adicion().toString());
			}else{
				formulario.setOI_adicion(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_n()){
				formulario.setOI_dnpl(graduacion.getOI_n().toString());
			}else{
				formulario.setOI_dnpl(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_p()){
				formulario.setOI_dnpc(graduacion.getOI_p().toString());
			}else{
				formulario.setOI_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOI_avsc()){
				formulario.setOI_avsc(consultaOpt.getStrOI_avsc());
			}else{
				formulario.setOI_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOI_avcc()){
				formulario.setOI_avcc(consultaOpt.getStrOI_avcc());
			}else{
				formulario.setOI_avcc(Constantes.STRING_BLANCO);			
			}
			
			if(null != consultaOpt.getStrOI_avcl()){
				formulario.setOI_avcl(consultaOpt.getStrOI_avcl());
			}else{
				formulario.setOI_avcl(Constantes.STRING_BLANCO);			
			}
			
			formulario.setDiferenteAdd(graduacion.isDiferente_add());
			
			
			formulario.setOI_cantidad(graduacion.getOI_cantidad());
			
			
			formulario.setOI_base(graduacion.getOI_base());
			
			if(null != graduacion.getOI_altura()){
				formulario.setOI_altura(graduacion.getOI_altura());
			}else{
				formulario.setOI_altura(Constantes.STRING_BLANCO);
			}
			formulario.setOI_observaciones(graduacion.getOI_obser());
			
			OftalmologoBean medico = PosMedicoFacade.traeMedico(formulario.getCod_doctor());
			
			formulario.setNifdoctor(medico.getNif());
			formulario.setDvnifdoctor(medico.getLnif());
			formulario.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
			
			formulario.setDerivacion(consultaOpt.getDerivacion());
			formulario.setClienteFirmo(consultaOpt.getClienteFirmo());
			
			if(consultaOpt.getClienteFirmo()) {
				formulario.setPuedeEditarse(Constantes.STRING_FALSE);
			}else {
				formulario.setPuedeEditarse(Constantes.STRING_TRUE);
			}
			
			formulario.setNumCodigo(consultaOpt.getNumCodigo());
			
			formulario.setFiltroAzul(consultaOpt.getFiltroAzul());
			formulario.setAntireflejo(consultaOpt.getAntireflejo());
			formulario.setBifocal(consultaOpt.getBifocal());
			formulario.setProgresivos(consultaOpt.getProgresivos());
			formulario.setFotosensible(consultaOpt.getFotosensible());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return formulario;
	}


	/***********************************************************************************
	 * Método:   ingresaConsultaOptometrica.
	 * Objetivo: retorna un Formulario de Consulta Optométrica con los datos de la 
	 *           graduación dado el cliente, la fecha y el número de la graduación
	 * @param cliente
	 * @param fecha
	 * @param número de graduación
	 * @return ConsultaOptometricaForm
	 ***********************************************************************************/
	public boolean ingresaConsultaOptometrica(ConsultaOptometricaForm formulario){
		
		ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
		GraduacionesBean graduacion = new GraduacionesBean();
		boolean respuesta = false;
		try{
			
			int numero = PosGraduacionesFacade.traeNumeroGraduacion(String.valueOf(formulario.getCliente()), traeFechaHoyFormateadaString());	//revisar		
			
			graduacion.setCliente(formulario.getCliente());
			graduacion.setFecha(traeFechaHoyFormateadaString());			
			graduacion.setNumero(-1);
			graduacion.setDoctor(formulario.getCod_doctor());	
			graduacion.setFecha_prox_revision(formulario.getFechaProxRevision());
			graduacion.setTipo(formulario.getTipo());
			
			if(null != formulario.getOD_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_esfera()))){
				graduacion.setOD_esfera(Double.valueOf(formulario.getOD_esfera()));
			}else{
				graduacion.setOD_esfera(null);
			}
				
			if(null != formulario.getOD_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cilindro()))){
				graduacion.setOD_cilindro(Double.valueOf(formulario.getOD_cilindro()));
			}else{
				graduacion.setOD_cilindro(null);
			}	
			
			graduacion.setAgente(formulario.getAgente());
			
			if(null != formulario.getOD_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_eje()))){
				graduacion.setOD_eje(Integer.valueOf(formulario.getOD_eje()));
			}else{
				graduacion.setOD_eje(null);
			}
			
			if(null != formulario.getOD_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_adicion()))){
				graduacion.setOD_adicion(Double.valueOf(formulario.getOD_adicion()));
			}else{
				graduacion.setOD_adicion(null);
			}
			
			if(null != formulario.getOD_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cerca()))){
				graduacion.setOD_esfera_cerca(Double.valueOf(formulario.getOD_cerca()));
			}else{
				graduacion.setOD_esfera_cerca(null);
			}
			
			if(null !=formulario.getOD_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpl())) ){
				graduacion.setOD_n(Double.valueOf(formulario.getOD_dnpl()));
			}else{
				graduacion.setOD_n(null);
			}
			
			if(null != formulario.getOD_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpc()))){
				graduacion.setOD_p(Double.valueOf(formulario.getOD_dnpc()));
			}else{
				graduacion.setOD_p(null);
			}
			
			
			
			if(null != formulario.getOD_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_observaciones()))){
				graduacion.setOD_obser(formulario.getOD_observaciones().toUpperCase());
			}else{
				graduacion.setOD_obser(Constantes.STRING_BLANCO);
			}
			
			/*
			if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				graduacion.setOD_avsc(Double.valueOf(formulario.getOD_avsc()));
			}else{
				graduacion.setOD_avsc(null);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				graduacion.setOD_avcc(Double.valueOf(formulario.getOD_avcc()));
			}else{
				graduacion.setOD_avcc(null);
			}	
			
			if(null != formulario.getOD_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcl()))){
				graduacion.setOD_avcl(Double.valueOf(formulario.getOD_avcl()));
			}else{
				graduacion.setOD_avcl(null);
			}
			*/
			
			if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				consultaOpt.setStrOD_avsc(formulario.getOD_avsc());
			}else{
				consultaOpt.setStrOD_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				consultaOpt.setStrOD_avcc(formulario.getOD_avcc());
			}else{
				consultaOpt.setStrOD_avcc(Constantes.STRING_BLANCO);
			}	
			
			if(null != formulario.getOD_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcl()))){
				consultaOpt.setStrOD_avcl(formulario.getOD_avcl());
			}else{
				consultaOpt.setStrOD_avcl(Constantes.STRING_BLANCO);
			}
			
			/*
			consultaOpt.setStrOD_avsc(formulario.getOD_avsc());
			consultaOpt.setStrOD_avcc(formulario.getOD_avcc());
			consultaOpt.setStrOD_avcl(formulario.getOD_avcl());
			*/
			
			graduacion.setOD_cantidad(formulario.getOD_cantidad());
			graduacion.setOD_base(formulario.getOD_base());
			graduacion.setOD_altura(formulario.getOD_altura());
			
			if(null != formulario.getOI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_esfera()))){
				graduacion.setOI_esfera(Double.valueOf(formulario.getOI_esfera()));
			}else{
				graduacion.setOI_esfera(null);
			}
			
			
			if(null != formulario.getOI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cilindro()))){
				graduacion.setOI_cilindro(Double.valueOf(formulario.getOI_cilindro()));
			}else{
				graduacion.setOI_cilindro(null);
			}
			
			if(null != formulario.getOI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_eje()))){
				graduacion.setOI_eje(Integer.valueOf(formulario.getOI_eje()));
			}else{
				graduacion.setOI_eje(null);
			}
			
			if(null != formulario.getOI_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_adicion()))){
				graduacion.setOI_adicion(Double.valueOf(formulario.getOI_adicion()));
			}else{
				graduacion.setOI_adicion(null);
			}	
			
			if(null !=formulario.getOI_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cerca()))){
				graduacion.setOI_esfera_cerca(Double.valueOf(formulario.getOI_cerca()));
			}else{
				graduacion.setOI_esfera_cerca(null);
			}
			
			if(null !=formulario.getOI_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpl())) ){
				graduacion.setOI_n(Double.valueOf(formulario.getOI_dnpl()));
			}else{
				graduacion.setOI_n(null);
			}
			
			if(null != formulario.getOI_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpc()))){
				graduacion.setOI_p(Double.valueOf(formulario.getOI_dnpc()));
			}else{
				graduacion.setOI_p(null);
			}
			
			
			
			if(null != formulario.getOI_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_observaciones()))){
				graduacion.setOI_obser(formulario.getOI_observaciones().toUpperCase());
			}else{
				graduacion.setOI_obser(Constantes.STRING_BLANCO);
			}
			
			/*
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				graduacion.setOI_avsc(Double.valueOf(formulario.getOI_avsc()));
			}else{
				graduacion.setOI_avsc(null);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				graduacion.setOI_avcc(Double.valueOf(formulario.getOI_avcc()));
			}else{
				graduacion.setOI_avcc(null);
			}
			
			if(null != formulario.getOI_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcl()))){
				graduacion.setOI_avcl(Double.valueOf(formulario.getOI_avcl()));
			}else{
				graduacion.setOI_avcl(null);
			}
			*/
			
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				consultaOpt.setStrOI_avsc(formulario.getOI_avsc());
			}else{
				consultaOpt.setStrOI_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				consultaOpt.setStrOI_avcc(formulario.getOI_avcc());
			}else{
				consultaOpt.setStrOI_avcc(Constantes.STRING_BLANCO);
			}	
			
			if(null != formulario.getOI_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcl()))){
				consultaOpt.setStrOI_avcl(formulario.getOI_avcl());
			}else{
				consultaOpt.setStrOI_avcl(Constantes.STRING_BLANCO);
			}
			/*
			consultaOpt.setStrOI_avsc(formulario.getOI_avsc());
			consultaOpt.setStrOI_avcc(formulario.getOI_avcc());
			consultaOpt.setStrOI_avcl(formulario.getOI_avcl());
			*/
			
			graduacion.setOI_cantidad(formulario.getOI_cantidad());
			graduacion.setOI_base(formulario.getOI_base());
			graduacion.setOI_altura(formulario.getOI_altura());
			
			graduacion.setFecha_emision(formulario.getFechaEmision());
			graduacion.setDiferente_add(formulario.isDiferenteAdd());
			
			consultaOpt.setGraduacion(graduacion);
			consultaOpt.setClienteFirmo(formulario.getClienteFirmo());
			consultaOpt.setDerivacion(formulario.getDerivacion());
			
			consultaOpt.setFiltroAzul(formulario.getFiltroAzul());
			consultaOpt.setAntireflejo(formulario.getAntireflejo());
			consultaOpt.setBifocal(formulario.getBifocal());
			consultaOpt.setProgresivos(formulario.getProgresivos());
			consultaOpt.setFotosensible(formulario.getFotosensible());
			
			
			respuesta = PosConsultaOptometricaFacade.ingresaConsultaOptometrica(consultaOpt);		
			
		}catch(Exception ex){
			respuesta = false;
			ex.printStackTrace();
		}
		
		return respuesta;
	}

	/***********************************************************************************
	 * Método:   modificaConsultaOptometrica.
	 * Objetivo: permite preparar los datos para modificar la Consulta Optométrica
	 * @param formulario
	 * @return boolean que indica si se efectuó o no la modificación
	 ***********************************************************************************/
	public boolean modificaConsultaOptometrica(ConsultaOptometricaForm formulario){
	
		ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
		GraduacionesBean graduaciones = new GraduacionesBean();
		boolean respuesta = false;
		try{
			
			int numero = PosConsultaOptometricaFacade.traeNumeroGraduacion(String.valueOf(formulario.getCliente()), traeFechaHoyFormateadaString());	//revisar		
			
			graduaciones.setCliente(formulario.getCliente());
			
			
			graduaciones.setFecha(traeFechaHoyFormateadaString());
			graduaciones.setFecha_ant(formulario.getFecha_graduacion());
			
			int numer = (int) formulario.getNumero_graduacion();
			graduaciones.setNumero(numer);
			
			
			graduaciones.setDoctor(formulario.getCod_doctor());	
			graduaciones.setFecha_prox_revision(formulario.getFechaProxRevision());
			graduaciones.setTipo(formulario.getTipo());
			
			if(null != formulario.getOD_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_esfera()))){
				graduaciones.setOD_esfera(Double.valueOf(formulario.getOD_esfera()));
			}else{
				graduaciones.setOD_esfera(null);
			}
				
			if(null != formulario.getOD_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cilindro()))){
				graduaciones.setOD_cilindro(Double.valueOf(formulario.getOD_cilindro()));
			}else{
				graduaciones.setOD_cilindro(null);
			}	
			
			graduaciones.setAgente(formulario.getAgente());
			
			if(null != formulario.getOD_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_eje()))){
				graduaciones.setOD_eje(Integer.valueOf(formulario.getOD_eje()));
			}else{
				graduaciones.setOD_eje(null);
			}
			
			if(null != formulario.getOD_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_adicion()))){
				graduaciones.setOD_adicion(Double.valueOf(formulario.getOD_adicion()));
			}else{
				graduaciones.setOD_adicion(null);
			}
			
			if(null != formulario.getOD_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cerca()))){
				graduaciones.setOD_esfera_cerca(Double.valueOf(formulario.getOD_cerca()));
			}else{
				graduaciones.setOD_esfera_cerca(null);
			}
			
			if(null !=formulario.getOD_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpl())) ){
				graduaciones.setOD_n(Double.valueOf(formulario.getOD_dnpl()));
			}else{
				graduaciones.setOD_n(null);
			}
			
			if(null != formulario.getOD_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpc()))){
				graduaciones.setOD_p(Double.valueOf(formulario.getOD_dnpc()));
			}else{
				graduaciones.setOD_p(null);
			}
			
			
			
			if(null != formulario.getOD_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_observaciones()))){
				graduaciones.setOD_obser(formulario.getOD_observaciones().toUpperCase());
			}else{
				graduaciones.setOD_obser(Constantes.STRING_BLANCO);
			}
			
			/*if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				graduaciones.setOD_avsc(Double.valueOf(formulario.getOD_avsc()));
			}else{
				graduaciones.setOD_avsc(null);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				graduaciones.setOD_avcc(Double.valueOf(formulario.getOD_avcc()));
			}else{
				graduaciones.setOD_avcc(null);
			}*/
			
			
			if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				consultaOpt.setStrOD_avsc(formulario.getOD_avsc());
			}else{
				consultaOpt.setStrOD_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				consultaOpt.setStrOD_avcc(formulario.getOD_avcc());
			}else{
				consultaOpt.setStrOD_avcc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOD_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcl()))){
				consultaOpt.setStrOD_avcl(formulario.getOD_avcl());
			}else{
				consultaOpt.setStrOD_avcl(Constantes.STRING_BLANCO);
			}
			
			
			graduaciones.setOD_cantidad(formulario.getOD_cantidad());
			graduaciones.setOD_base(formulario.getOD_base());
			graduaciones.setOD_altura(formulario.getOD_altura());
			
			if(null != formulario.getOI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_esfera()))){
				graduaciones.setOI_esfera(Double.valueOf(formulario.getOI_esfera()));
			}else{
				graduaciones.setOI_esfera(null);
			}
			
			
			if(null != formulario.getOI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cilindro()))){
				graduaciones.setOI_cilindro(Double.valueOf(formulario.getOI_cilindro()));
			}else{
				graduaciones.setOI_cilindro(null);
			}
			
			if(null != formulario.getOI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_eje()))){
				graduaciones.setOI_eje(Integer.valueOf(formulario.getOI_eje()));
			}else{
				graduaciones.setOI_eje(null);
			}
			
			if(null != formulario.getOI_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_adicion()))){
				graduaciones.setOI_adicion(Double.valueOf(formulario.getOI_adicion()));
			}else{
				graduaciones.setOI_adicion(null);
			}	
			
			if(null !=formulario.getOI_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cerca()))){
				graduaciones.setOI_esfera_cerca(Double.valueOf(formulario.getOI_cerca()));
			}else{
				graduaciones.setOI_esfera_cerca(null);
			}
			
			if(null !=formulario.getOI_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpl())) ){
				graduaciones.setOI_n(Double.valueOf(formulario.getOI_dnpl()));
			}else{
				graduaciones.setOI_n(null);
			}
			
			if(null != formulario.getOI_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpc()))){
				graduaciones.setOI_p(Double.valueOf(formulario.getOI_dnpc()));
			}else{
				graduaciones.setOI_p(null);
			}
			
			
			
			if(null != formulario.getOI_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_observaciones()))){
				graduaciones.setOI_obser(formulario.getOI_observaciones().toUpperCase());
			}else{
				graduaciones.setOI_obser(Constantes.STRING_BLANCO);
			}
			
			/*
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				graduaciones.setOI_avsc(Double.valueOf(formulario.getOI_avsc()));
			}else{
				graduaciones.setOI_avsc(null);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				graduaciones.setOI_avcc(Double.valueOf(formulario.getOI_avcc()));
			}else{
				graduaciones.setOI_avcc(null);
			}
			*/
			
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				consultaOpt.setStrOI_avsc(formulario.getOI_avsc());
			}else{
				consultaOpt.setStrOI_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				consultaOpt.setStrOI_avcc(formulario.getOI_avcc());
			}else{
				consultaOpt.setStrOI_avcc(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOI_avcl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcl()))){
				consultaOpt.setStrOI_avcl(formulario.getOI_avcl());
			}else{
				consultaOpt.setStrOI_avcl(Constantes.STRING_BLANCO);
			}
			
					
			graduaciones.setOI_cantidad(formulario.getOI_cantidad());
			graduaciones.setOI_base(formulario.getOI_base());
			graduaciones.setOI_altura(formulario.getOI_altura());
			graduaciones.setDiferente_add(formulario.isDiferenteAdd());
					
			graduaciones.setFecha_emision(formulario.getFechaEmision());
			graduaciones.setDiferente_add(formulario.isDiferenteAdd());
			
			consultaOpt.setGraduacion(graduaciones);
			consultaOpt.setClienteFirmo(formulario.getClienteFirmo());
			consultaOpt.setDerivacion(formulario.getDerivacion());
			
			consultaOpt.setFiltroAzul(formulario.getFiltroAzul());
			consultaOpt.setAntireflejo(formulario.getAntireflejo());
			consultaOpt.setBifocal(formulario.getBifocal());
			consultaOpt.setProgresivos(formulario.getProgresivos());
			consultaOpt.setFotosensible(formulario.getFotosensible());
			
			respuesta = PosConsultaOptometricaFacade.modificaConsultaOptometrica(consultaOpt);		
			
		}catch(Exception ex){
			respuesta = false;
			ex.printStackTrace();
		}
		
		return respuesta;
	}

	

   
   
	/***********************************************************************************
	 * Método:   traeGraduacionCodigo.
	 * Objetivo: permite ontener los datos de Consulta Optométrica, dado el código interno
	 *           de la Consulta Optométrica
	 * @param formulario
	 * @return ConsultaOptometricaForm
	 ***********************************************************************************/
	
	public ConsultaOptometricaForm traeGraduacionCodigo(ConsultaOptometricaForm formulario) {
		
		ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
		GraduacionesBean graduacion = new GraduacionesBean();
		
		try{
			
			consultaOpt = PosConsultaOptometricaFacade.traeGraduacionCodigo(formulario.getNumCodigo());
			
			graduacion = consultaOpt.getGraduacion();
			
			formulario.setCliente(graduacion.getCliente());
			
			
			formulario.setCod_doctor(graduacion.getCod_doctor());
			formulario.setTipo(graduacion.getTipo());
			formulario.setFechaEmision(graduacion.getFecha_emision());
			formulario.setFechaProxRevision(graduacion.getFecha_prox_revision());
			formulario.setAgente(graduacion.getAgente());
			formulario.setDoctor(graduacion.getDoctor());
			
			if(null !=graduacion.getOD_esfera()){
				formulario.setOD_esfera(graduacion.getOD_esfera().toString());
			}else{
				formulario.setOD_esfera(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_cilindro()){
				formulario.setOD_cilindro(graduacion.getOD_cilindro().toString());
			}else{
				formulario.setOD_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_eje()){
				formulario.setOD_eje(graduacion.getOD_eje().toString());
			}else{
				formulario.setOD_eje(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_esfera_cerca()){
				formulario.setOD_cerca(graduacion.getOD_esfera_cerca().toString());
			}else{
				formulario.setOD_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_adicion()){
				formulario.setOD_adicion(graduacion.getOD_adicion().toString());
			}else{
				formulario.setOD_adicion(Constantes.STRING_BLANCO);		
			}
			
			if(null != graduacion.getOD_n()){
				formulario.setOD_dnpl(graduacion.getOD_n().toString());
			}else{
				formulario.setOD_dnpl(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_p()){
				formulario.setOD_dnpc(graduacion.getOD_p().toString());
			}else{
				formulario.setOD_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOD_avsc()){
				formulario.setOD_avsc(consultaOpt.getStrOD_avsc());
			}else{
				formulario.setOD_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOD_avcc()){
				formulario.setOD_avcc(consultaOpt.getStrOD_avcc());
			}else{
				formulario.setOD_avcc(Constantes.STRING_BLANCO);
			}			
			
			if(null != consultaOpt.getStrOD_avcl()){
				formulario.setOD_avcl(consultaOpt.getStrOD_avcl());
			}else{
				formulario.setOD_avcl(Constantes.STRING_BLANCO);
			}	
			
			formulario.setOD_cantidad(graduacion.getOD_cantidad());
			formulario.setOD_base(graduacion.getOD_base());
			
			if(null != graduacion.getOD_altura()){
				formulario.setOD_altura(graduacion.getOD_altura());
			}else{
				formulario.setOD_altura(Constantes.STRING_BLANCO);
			}
			
			
			formulario.setOD_observaciones(graduacion.getOD_obser());
			
			if(null != graduacion.getOI_esfera()){
				formulario.setOI_esfera(graduacion.getOI_esfera().toString());
			}else{
				formulario.setOI_esfera(Constantes.STRING_BLANCO);
			}
						
			if(null != graduacion.getOI_cilindro()){
				formulario.setOI_cilindro(graduacion.getOI_cilindro().toString());
			}else{
				formulario.setOI_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_eje()){
				formulario.setOI_eje(graduacion.getOI_eje().toString());
			}else{
				formulario.setOI_eje(Constantes.STRING_BLANCO);
			}
			
			if(null !=graduacion.getOI_esfera_cerca()){
				formulario.setOI_cerca(graduacion.getOI_esfera_cerca().toString());
			}else{
				formulario.setOI_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_adicion()){
				formulario.setOI_adicion(graduacion.getOI_adicion().toString());
			}else{
				formulario.setOI_adicion(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_n()){
				formulario.setOI_dnpl(graduacion.getOI_n().toString());
			}else{
				formulario.setOI_dnpl(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_p()){
				formulario.setOI_dnpc(graduacion.getOI_p().toString());
			}else{
				formulario.setOI_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOI_avsc()){
				formulario.setOI_avsc(consultaOpt.getStrOI_avsc());
			}else{
				formulario.setOI_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != consultaOpt.getStrOI_avcc()){
				formulario.setOI_avcc(consultaOpt.getStrOI_avcc());
			}else{
				formulario.setOI_avcc(Constantes.STRING_BLANCO);			
			}
			
			if(null != consultaOpt.getStrOI_avcl()){
				formulario.setOI_avcl(consultaOpt.getStrOI_avcl());
			}else{
				formulario.setOI_avcl(Constantes.STRING_BLANCO);			
			}
			
			formulario.setDiferenteAdd(graduacion.isDiferente_add());
			
			
			formulario.setOI_cantidad(graduacion.getOI_cantidad());
			
			
			formulario.setOI_base(graduacion.getOI_base());
			
			if(null != graduacion.getOI_altura()){
				formulario.setOI_altura(graduacion.getOI_altura());
			}else{
				formulario.setOI_altura(Constantes.STRING_BLANCO);
			}
			formulario.setOI_observaciones(graduacion.getOI_obser());
			
			OftalmologoBean medico = PosMedicoFacade.traeMedico(formulario.getCod_doctor());
			
			formulario.setNifdoctor(medico.getNif());
			formulario.setDvnifdoctor(medico.getLnif());
			formulario.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
			
			formulario.setDerivacion(consultaOpt.getDerivacion());
			formulario.setClienteFirmo(consultaOpt.getClienteFirmo());
			
			formulario.setNombreCliente(consultaOpt.getNombreCliente()+" "+consultaOpt.getApellidoCliente());
			formulario.setRunCliente(consultaOpt.getNifCliente()+"-"+consultaOpt.getDivNifCliente());
			formulario.setTelfCliente(consultaOpt.getTelfCliente());
			formulario.setNumCodigo(consultaOpt.getNumCodigo());
			
			formulario.setFiltroAzul(consultaOpt.getFiltroAzul());
			formulario.setAntireflejo(consultaOpt.getAntireflejo());
			formulario.setBifocal(consultaOpt.getBifocal());
			formulario.setProgresivos(consultaOpt.getProgresivos());
			formulario.setFotosensible(consultaOpt.getFotosensible());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return formulario;
	}

	
	/***********************************************************************************
	 * Método:   traeConsultaMasReciente.
	 * Objetivo: permite obtener los datos de la Consulta Optométrica más recientemente
	 *           registrada de un cliente en particular
	 * @param formulario
	 * @return ConsultaOptometricaForm
	 ***********************************************************************************/
	public ConsultaOptometricaForm traeConsultaMasReciente(ConsultaOptometricaForm formulario) {
	
	ConsultaOptometricaBean consultaOpt = new ConsultaOptometricaBean();
	GraduacionesBean graduacion = new GraduacionesBean();
	
	try{
		
		consultaOpt = PosConsultaOptometricaFacade.traeConsultaMasRecienteCliente(String.valueOf(formulario.getCliente()));
		
		graduacion = consultaOpt.getGraduacion();
		
		formulario.setCliente(graduacion.getCliente());
		
		
		formulario.setCod_doctor(graduacion.getCod_doctor());
		formulario.setTipo(graduacion.getTipo());
		formulario.setFechaEmision(graduacion.getFecha_emision());
		formulario.setFechaProxRevision(graduacion.getFecha_prox_revision());
		formulario.setAgente(graduacion.getAgente());
		formulario.setDoctor(graduacion.getDoctor());
		
		if(null !=graduacion.getOD_esfera()){
			formulario.setOD_esfera(graduacion.getOD_esfera().toString());
		}else{
			formulario.setOD_esfera(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOD_cilindro()){
			formulario.setOD_cilindro(graduacion.getOD_cilindro().toString());
		}else{
			formulario.setOD_cilindro(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOD_eje()){
			formulario.setOD_eje(graduacion.getOD_eje().toString());
		}else{
			formulario.setOD_eje(Constantes.STRING_BLANCO);			
		}
		
		if(null != graduacion.getOD_esfera_cerca()){
			formulario.setOD_cerca(graduacion.getOD_esfera_cerca().toString());
		}else{
			formulario.setOD_cerca(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOD_adicion()){
			formulario.setOD_adicion(graduacion.getOD_adicion().toString());
		}else{
			formulario.setOD_adicion(Constantes.STRING_BLANCO);		
		}
		
		if(null != graduacion.getOD_n()){
			formulario.setOD_dnpl(graduacion.getOD_n().toString());
		}else{
			formulario.setOD_dnpl(Constantes.STRING_BLANCO);			
		}
		
		if(null != graduacion.getOD_p()){
			formulario.setOD_dnpc(graduacion.getOD_p().toString());
		}else{
			formulario.setOD_dnpc(Constantes.STRING_BLANCO);
		}
		
		
		if(null != consultaOpt.getStrOD_avsc()){
			formulario.setOD_avsc(consultaOpt.getStrOD_avsc());
		}
		else{
			formulario.setOD_avsc(Constantes.STRING_BLANCO);
		}
		
		
		if(null != consultaOpt.getStrOD_avcc()){
			formulario.setOD_avcc(consultaOpt.getStrOD_avcc());
		}
		else{
			formulario.setOD_avcc(Constantes.STRING_BLANCO);
		}			
		
		
		if(null != consultaOpt.getStrOD_avcl()){
			formulario.setOD_avcl(consultaOpt.getStrOD_avcl());
		}
		else{
			formulario.setOD_avcl(Constantes.STRING_BLANCO);
		}	
		
		formulario.setOD_cantidad(graduacion.getOD_cantidad());
		formulario.setOD_base(graduacion.getOD_base());
		
		if(null != graduacion.getOD_altura()){
			formulario.setOD_altura(graduacion.getOD_altura());
		}else{
			formulario.setOD_altura(Constantes.STRING_BLANCO);
		}
		
		
		formulario.setOD_observaciones(graduacion.getOD_obser());
		
		if(null != graduacion.getOI_esfera()){
			formulario.setOI_esfera(graduacion.getOI_esfera().toString());
		}else{
			formulario.setOI_esfera(Constantes.STRING_BLANCO);
		}
					
		if(null != graduacion.getOI_cilindro()){
			formulario.setOI_cilindro(graduacion.getOI_cilindro().toString());
		}else{
			formulario.setOI_cilindro(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOI_eje()){
			formulario.setOI_eje(graduacion.getOI_eje().toString());
		}else{
			formulario.setOI_eje(Constantes.STRING_BLANCO);
		}
		
		if(null !=graduacion.getOI_esfera_cerca()){
			formulario.setOI_cerca(graduacion.getOI_esfera_cerca().toString());
		}else{
			formulario.setOI_cerca(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOI_adicion()){
			formulario.setOI_adicion(graduacion.getOI_adicion().toString());
		}else{
			formulario.setOI_adicion(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOI_n()){
			formulario.setOI_dnpl(graduacion.getOI_n().toString());
		}else{
			formulario.setOI_dnpl(Constantes.STRING_BLANCO);
		}
		
		if(null != graduacion.getOI_p()){
			formulario.setOI_dnpc(graduacion.getOI_p().toString());
		}else{
			formulario.setOI_dnpc(Constantes.STRING_BLANCO);
		}
		
		if(null != consultaOpt.getStrOI_avsc()){
			formulario.setOI_avsc(consultaOpt.getStrOI_avsc());
		}
		else{
			formulario.setOI_avsc(Constantes.STRING_BLANCO);
		}
		
		if(null != consultaOpt.getStrOI_avcc()){
			formulario.setOI_avcc(consultaOpt.getStrOI_avcl());
		}
		else{
			formulario.setOI_avcc(Constantes.STRING_BLANCO);			
		}
		
	
		if(null != consultaOpt.getStrOI_avcl()){
			formulario.setOI_avcl(consultaOpt.getStrOI_avcl());
		}
		else{
			formulario.setOI_avcl(Constantes.STRING_BLANCO);			
		}
		
		formulario.setDiferenteAdd(graduacion.isDiferente_add());
		
		
		formulario.setOI_cantidad(graduacion.getOI_cantidad());
		
		
		formulario.setOI_base(graduacion.getOI_base());
		
		if(null != graduacion.getOI_altura()){
			formulario.setOI_altura(graduacion.getOI_altura());
		}else{
			formulario.setOI_altura(Constantes.STRING_BLANCO);
		}
		formulario.setOI_observaciones(graduacion.getOI_obser());
		
		OftalmologoBean medico = PosMedicoFacade.traeMedico(formulario.getCod_doctor());
		
		formulario.setNifdoctor(medico.getNif());
		formulario.setDvnifdoctor(medico.getLnif());
		formulario.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
		
		formulario.setDerivacion(consultaOpt.getDerivacion());
		formulario.setClienteFirmo(consultaOpt.getClienteFirmo());
		
		formulario.setNombreCliente(consultaOpt.getNombreCliente()+" "+consultaOpt.getApellidoCliente());
		formulario.setRunCliente(consultaOpt.getNifCliente()+"-"+consultaOpt.getDivNifCliente());
		formulario.setTelfCliente(consultaOpt.getTelfCliente());
		formulario.setNumCodigo(consultaOpt.getNumCodigo());
		
		formulario.setFiltroAzul(consultaOpt.getFiltroAzul());
		formulario.setAntireflejo(consultaOpt.getAntireflejo());
		formulario.setBifocal(consultaOpt.getBifocal());
		formulario.setProgresivos(consultaOpt.getProgresivos());
		formulario.setFotosensible(consultaOpt.getFotosensible());
		
		
	}catch(Exception ex){
		ex.printStackTrace();
	}
	return formulario;
}
	
	public ArrayList<AgenteBean> traeTecnicos(String local) throws Exception {
		ArrayList<AgenteBean> listaTecnicos = new ArrayList<AgenteBean>();
		listaTecnicos = PosConsultaOptometricaFacade.traeTecnicos(local);
		return listaTecnicos;
	}
}
