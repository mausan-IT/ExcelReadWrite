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
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;
import cl.gmo.pos.venta.web.helper.GraduacionesHelper;

public class ContactologiaDispatchActions extends DispatchAction{
	Logger log = Logger.getLogger( this.getClass() );
	public ActionForward cargaFormulario(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	    {
		log.info("ContactologiaDispatchActions:cargaFormulario inicio");
			
			GraduacionesHelper helper = new GraduacionesHelper();
			ContactologiaForm formulario = (ContactologiaForm)form;
			formulario.setEstaGrabado(0);
			//formulario.setListaOftalmologos(new ArrayList<OftalmologoBean>());			
			formulario.setCliente(request.getParameter(Constantes.STRING_CLIENTE));
			ArrayList<ContactologiaBean> lista_contacto = helper.traeContactologiaCliente(formulario);
			if(null !=lista_contacto){
				formulario.setListaContactologia(lista_contacto);
			}else{
				formulario.setListaContactologia(new ArrayList<ContactologiaBean>());
			}
			formulario.setGrabar(Constantes.STRING_TRUE);
			log.info("ContactologiaDispatchActions:cargaFormulario fin");
			return mapping.findForward(Constantes.FORWARD_CONTACTOLOGIA);
		
	    }
	
	public ActionForward ingresaContactologia(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	    {
		log.info("ContactologiaDispatchActions:ingresaContactologia inicio");
			try{
				ContactologiaForm formulario = (ContactologiaForm)form;
				GraduacionesHelper helper = new GraduacionesHelper();
				formulario.setEstaGrabado(0);
				
				if(Constantes.STRING_ACTION_INGRESO_CONTACTOLOGIA.equals(formulario.getAccion())){	
					formulario.setEstaGrabado(1);
					formulario.setGrabar(Constantes.STRING_TRUE);
					String nombre_doctor = formulario.getNombre_doctor();
					//formulario.setListaOftalmologos(new ArrayList<OftalmologoBean>());
					boolean respuesta = false;
					respuesta = helper.ingresaContactologia(formulario);
					//formulario.cleanForm();	
					ArrayList<ContactologiaBean> lista_contacto = helper.traeContactologiaCliente(formulario);
					if(null !=lista_contacto){
						formulario.setListaContactologia(lista_contacto);
					}else{
						formulario.setListaContactologia(new ArrayList<ContactologiaBean>());
					}
					if(respuesta){
						formulario.setExito(Constantes.STRING_TRUE);
					}else{
						formulario.setExito(Constantes.STRING_FALSE);
					}
					formulario.setNombre_doctor(nombre_doctor);
					
				}else if(Constantes.STRING_ACTION_VER_GRADUACION.equals(formulario.getAccion())){
					
					ArrayList<ContactologiaBean> lista_contacto = helper.traeContactologiaCliente(formulario);
					formulario.setGrabar(Constantes.STRING_FALSE);
					//formulario.setListaOftalmologos(new ArrayList<OftalmologoBean>());
					if(null !=lista_contacto){
						formulario.setListaContactologia(lista_contacto);
						helper.traeContactoCliente(formulario);
					}else{
						formulario.setListaContactologia(new ArrayList<ContactologiaBean>());
					}
				}else if(Constantes.STRING_ACTION_MODIFICAR_GRADUACION.equals(formulario.getAccion())){
					formulario.setEstaGrabado(1);
					//formulario.setListaOftalmologos(new ArrayList<OftalmologoBean>());
					boolean respuesta = helper.modificaContactologia(formulario);
					//formulario.cleanForm();	
					ArrayList<ContactologiaBean> lista_contacto = helper.traeContactologiaCliente(formulario);
					if(null !=lista_contacto){
						formulario.setListaContactologia(lista_contacto);
					}else{
						formulario.setListaContactologia(new ArrayList<ContactologiaBean>());
					}
					if(respuesta){
						formulario.setExito(Constantes.STRING_TRUE);
					}else{
						formulario.setExito(Constantes.STRING_FALSE);
						formulario.setEstaGrabado(2);
					}
					
				}
				
				
			}catch(Exception ex){
				log.error("ContactologiaDispatchActions:ingresaContactologia error catch",ex);
			}
			log.info("ContactologiaDispatchActions:ingresaContactologia fin");
			return mapping.findForward(Constantes.FORWARD_CONTACTOLOGIA);
		
	    }
	
	
	public ActionForward buscarDoctorAjax(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		log.info("GraduacionesDispatchActions:cargaFormulario  inicio");	
		ContactologiaForm formulario = (ContactologiaForm)form;
		formulario.setEstaGrabado(2);
		HttpSession session = request.getSession();
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));   
    	String nifdoctor=request.getParameter("nifdoctor");
    	Utils helper = new Utils();
    	ArrayList<OftalmologoBean> listaMedicos = null;
    	
    	listaMedicos = helper.traeMedicos(nifdoctor);
    	HashMap hm = new HashMap();
    	
    	if(null != listaMedicos && listaMedicos.size()>0){
    		for(OftalmologoBean bean: listaMedicos){
    			hm.put("nifdoctor", bean.getNif());
    			formulario.setNifdoctor(bean.getNif());
    			hm.put("codigodoctor", bean.getCodigo());
    			formulario.setDoctor(bean.getCodigo());
    			hm.put("dvnifdoctor", bean.getLnif());
    			formulario.setDvnifdoctor(bean.getLnif());
    			hm.put("nombredoctor", bean.getNombre() +" "+bean.getApelli());
    			formulario.setNombre_doctor( bean.getNombre() +" "+bean.getApelli());
    		}
    		
    	}else{
    		hm.put("nifdoctor", "");
			hm.put("codigodoctor", "");
			hm.put("dvnifdoctor", "");
			hm.put("nombredoctor", "");
    	}
    	try{
	    	
			
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    	JSONObject json = JSONObject.fromObject(hm);
		response.setHeader("X-JSON", json.toString());
		
		log.info("GraduacionesDispatchActions:cargaFormulario  fin");
		return mapping.findForward(Constantes.FORWARD_GRADUACION);
	}
	
}
