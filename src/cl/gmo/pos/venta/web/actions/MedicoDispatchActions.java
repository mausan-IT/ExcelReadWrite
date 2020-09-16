package cl.gmo.pos.venta.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.MedicoForm;
import cl.gmo.pos.venta.web.helper.MedicoHelper;

public class MedicoDispatchActions extends DispatchAction {
	Logger log = Logger.getLogger( this.getClass() );
	
	public ActionForward cargaFormulario(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) 
	{
		log.info("MedicoDispatchActions:cargaFormulario  inicio");
		MedicoForm formulario= (MedicoForm)form;
		MedicoHelper helper = new MedicoHelper();
		//formulario.setCodigo(helper.traeCodigoDoctor());
		formulario.setListaProvincia(helper.traeProvincias());
		log.info("MedicoDispatchActions:cargaFormulario  fin");
		formulario.setEstaGrabado(0);
		
		return mapping.findForward(Constantes.FORWARD_MEDICO);
	}
	
	public ActionForward ingresaMedico(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		log.info("MedicoDispatchActions:ingresaMedico  inicio");
		MedicoForm formulario= (MedicoForm)form;
		MedicoHelper helper = new MedicoHelper();		
		
		formulario.setEstaGrabado(0);
		
		if(Constantes.STRING_ACTION_INGRESA_MEDICO.equals(formulario.getAccion())){
			
			int respuesta = helper.ingresaMedico(formulario);
			formulario.setEstaGrabado(1);		
			if(respuesta == 0 || respuesta == 2){
				formulario.setCodigo_medico_agregado(formulario.getCodigo());
				formulario.setNif_medico_agregado(formulario.getRut());
				formulario.setDvnif_medico_agregado(formulario.getDv());
				formulario.setNombre_medico_agregado(formulario.getNombres());
				formulario.setApellido_medico_agregado(formulario.getApellidos());
			}
			String pagina = formulario.getPagina();
			//formulario.cleanForm();// no limpiar pantalla al ingresar un doc
			formulario.setPagina(pagina);
			formulario.setCodigo(helper.traeCodigoDoctor());
			formulario.setListaProvincia(helper.traeProvincias());
			formulario.setExito(String.valueOf(respuesta));
			
		}else if(Constantes.STRING_ACTION_TRAE_MEDICO_SELECCIONADO.equals(formulario.getAccion())){
			
			formulario = helper.traeMedico(formulario);
			formulario.setListaProvincia(helper.traeProvincias());		
		}
		log.info("MedicoDispatchActions:ingresaMedico  fin");
		return mapping.findForward(Constantes.FORWARD_MEDICO);
	}
	
	
	
}
