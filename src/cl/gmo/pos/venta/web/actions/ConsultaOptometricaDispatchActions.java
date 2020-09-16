package cl.gmo.pos.venta.web.actions;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.ConsultaOptometricaForm;
import cl.gmo.pos.venta.web.helper.ConsultaOptometricaHelper;

public class ConsultaOptometricaDispatchActions extends DispatchAction{
	Logger log = Logger.getLogger( this.getClass() );
	public ConsultaOptometricaDispatchActions(){}
	
	/***********************************************************************************
	 * Método: cargaFormulario, se encarga de mostrar los datos iniciales del formulario
	 *         si se tiene la información del cliente, muestra los datos asociados al
	 *         cliente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return formulario
	 */
	public ActionForward cargaFormulario(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		log.info("ConsultaOptometricaDispatchActions:cargaFormulario  inicio");	
		ConsultaOptometricaHelper helper = new ConsultaOptometricaHelper();
		ConsultaOptometricaForm formulario = (ConsultaOptometricaForm)form;
		HttpSession session = request.getSession();
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(0);		
		try
		{
			if(null != formulario.getNombre() &&  null != formulario.getApellido()){
				formulario.setNombre_cliente(formulario.getNombre()+" "+ formulario.getApellido());
			}
			
			if(0 == formulario.getCliente()){
				ArrayList<GraduacionesBean> listaGraduaciones = new ArrayList<GraduacionesBean>();				
				formulario.setListaGraduaciones(listaGraduaciones);	
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setPagina("");
				formulario.setNombre_cliente("");
				formulario.setApellido("");
				formulario.setNombre("");
				
			}else{
				ArrayList<GraduacionesBean> listaGraduaciones = new ArrayList<GraduacionesBean>();				
				formulario.setListaGraduaciones(listaGraduaciones);	
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				
				formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				
				formulario.setPagina("");
				
				
				if(Constantes.STRING_CERO.equals(formulario.getCerrarPagina())){
					formulario.setCerrarPagina(Constantes.STRING_CERO);
				}else{
					formulario.setCerrarPagina(Constantes.STRING_UNO);
				}
			}
			
			formulario.setListaAgentes(helper.traeTecnicos(local));
			formulario.setFecha_graduacion(helper.traeFechaHoyFormateadaString());
			formulario.setListaCantidadOD(helper.traeListaCantidad());
			formulario.setListaBaseOD(helper.traeListaBase());
			formulario.setListaCantidadOI(helper.traeListaCantidad());
			formulario.setListaBaseOI(helper.traeListaBase());
			
		}catch(Exception ex){
			log.error("ConsultaOptometricaDispatchActions:cargaFormulario  error catch",ex);
		}
		log.info("ConsultaOptometricaDispatchActions:cargaFormulario  fin");
		return mapping.findForward(Constantes.FORWARD_CONSULTA_OPT);
	}

	
	/***********************************************************************************************
	 * Método: IngresaConsultaOptometrica, se encarga de guardar los datos de la Consulta Optometrica
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 */
	public ActionForward IngresaConsultaOptometrica(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		
		log.info("ConsultaOptometricaDispatchActions:IngresaGraduacion  inicio");
		ConsultaOptometricaHelper helper = new ConsultaOptometricaHelper();
		ConsultaOptometricaForm formulario = (ConsultaOptometricaForm)form;
		HttpSession session = request.getSession();
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(0);
		
				
		try
		{
			
			if(Constantes.STRING_ACTION_VER_GRADUACION.equals(formulario.getAccion())){
				
				formulario = helper.traeGraduacionFecha(formulario);
				formulario.setListaAgentes(helper.traeTecnicos(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				formulario.setPagina(Constantes.STRING_NO_GRABAR);
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());			
				
				
			}else if(Constantes.STRING_INSERTAR_CONSULTA_OPTOMETRICA.equals(formulario.getAccion())){
				boolean respuesta = false;
				respuesta  = helper.ingresaConsultaOptometrica(formulario);	
				GraduacionesBean graduacion = helper.traeUltimaGraduacionCliente(String.valueOf(formulario.getCliente()));
				//formulario.cleanForm();
				
				if(respuesta){
					formulario.setExito(Constantes.STRING_TRUE);
					formulario.setPuedeImprimirse(Constantes.STRING_TRUE);
				}else{
					formulario.setExito(Constantes.STRING_FALSE);
					formulario.setPuedeImprimirse(Constantes.STRING_FALSE);
				}
				
				formulario.setListaAgentes(helper.traeTecnicos(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setEstaGrabado(0);
				
				//formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				formulario.setPagina(Constantes.STRING_NO_GRABAR);
				formulario.setNumero_graduacion(graduacion.getNumero());
				formulario.setFecha_graduacion(graduacion.getFecha());
				
			}else if(Constantes.FORWARD_CONTACTOLOGIA.equals(formulario.getAccion())){
				
				return mapping.findForward(Constantes.FORWARD_CONTACTOLOGIA);
				
			}else if("modificarConsultaOpt".equals(formulario.getAccion())){
								
				boolean respuesta = false;
				respuesta  = helper.modificaConsultaOptometrica(formulario);	
				//formulario.cleanForm();
				
				if(respuesta){
					formulario.setExito(Constantes.STRING_TRUE);
					formulario.setPuedeImprimirse(Constantes.STRING_TRUE);
				}else{
					formulario.setExito(Constantes.STRING_FALSE);
					formulario.setPuedeImprimirse(Constantes.STRING_FALSE);
				}
				
				formulario.setListaAgentes(helper.traeTecnicos(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setEstaGrabado(0);
			}
			else if(Constantes.FORWARD_CONSULTA_OPT.equals(formulario.getAccion())){
				
				return mapping.findForward(Constantes.FORWARD_CONSULTA_OPT);
				
			}
			
		}catch(Exception ex){
			log.error("ConsultaOptometricaDispatchActions:IngresaConsultaOptometrica  error catch",ex);
		}
		
		return mapping.findForward(Constantes.FORWARD_CONSULTA_OPT);
	}


	/***********************************************************************************
	 * Método: buscarDoctorAjax, muestra los datos del doctor
	 *         este método se tomó de GraduacionesDispatchActions.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return formulario
	 */
	public ActionForward buscarDoctorAjax(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		log.info("ConsultaOptometricaDispatchActions:buscarDoctorAjax  inicio");	
		ConsultaOptometricaForm formulario = (ConsultaOptometricaForm)form;
		formulario.setEstaGrabado(2);
		HttpSession session = request.getSession();
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));   
    	String nifdoctor=request.getParameter("nifdoctor");
    	Utils helper = new Utils();
    	ArrayList<OftalmologoBean> listaMedicos = null;
    	
    	listaMedicos = helper.traeMedicos(nifdoctor);
    	HashMap hm = new HashMap();
    	
    	if(null != listaMedicos && listaMedicos.size()>0 ){
    		for(OftalmologoBean bean: listaMedicos){
    			hm.put("nifdoctor", bean.getNif());
    			formulario.setNifdoctor(bean.getNif());
    			hm.put("codigodoctor", bean.getCodigo());
    			formulario.setDoctor(bean.getCodigo());
    			hm.put("dvnifdoctor", bean.getLnif());
    			formulario.setDvnifdoctor(bean.getLnif());
    			hm.put("nombredoctor", bean.getNombre() +" "+bean.getApelli());
    			
    		}
    	}else{
    		hm.put("nifdoctor", "");
			hm.put("codigodoctor", "");
			hm.put("dvnifdoctor", "");
			hm.put("nombredoctor", "");
    	}
    	try{
	    	formulario.setListaAgentes(helper.traeAgentes(local));		
			formulario.setFecha_graduacion(helper.traeFechaHoyFormateadaString());
			formulario.setListaCantidadOD(helper.traeListaCantidad());
			formulario.setListaBaseOD(helper.traeListaBase());
			formulario.setListaCantidadOI(helper.traeListaCantidad());
			formulario.setListaBaseOI(helper.traeListaBase());
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    	JSONObject json = JSONObject.fromObject(hm);
		response.setHeader("X-JSON", json.toString());
		
		log.info("ConsultaOptometricaDispatchActions:buscarDoctorAjax  fin");
		return mapping.findForward(Constantes.FORWARD_CONSULTA_OPT);
	}
	
	
	/**********************************************************************************
	 * Método: imprimirComprobante, se encarga de mostrar el comprobante de la Consulta
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return impresión del Comprobante en pantalla
	 */
	public ActionForward imprimirComprobante(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		
		log.info("ConsultaOptometricaDispatchActions:ImprimirComprobante  inicio");
		ConsultaOptometricaHelper helper = new ConsultaOptometricaHelper();
		ConsultaOptometricaForm formulario = (ConsultaOptometricaForm)form;
		
		String numCod=request.getParameter("num");
		String numCliente = request.getParameter("cli");
		
		int iNumCod = Integer.parseInt(numCod);
		int iNumCliente = Integer.parseInt(numCliente);
		
		formulario.setNumCodigo(iNumCod);
		formulario.setCliente(iNumCliente);
		
		
		if(0!=iNumCod) {
			
			formulario = helper.traeGraduacionCodigo(formulario);
		}else {
			
			formulario = helper.traeConsultaMasReciente(formulario);
		}
		
		return mapping.findForward(Constantes.FORWARD_COMPROBANTE_OPT);
	}
}
