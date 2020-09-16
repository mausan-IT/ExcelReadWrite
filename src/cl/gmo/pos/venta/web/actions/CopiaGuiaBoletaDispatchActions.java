package cl.gmo.pos.venta.web.actions;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.commons.validator.UrlValidator;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.facade.PosSeleccionPagoFacade;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;
import cl.gmo.pos.venta.web.helper.CreaCopiaGuiaBoletaHelper;

import cl.gmo.pos.venta.web.helper.SeleccionPagoHelper;

public class CopiaGuiaBoletaDispatchActions extends DispatchAction {

	Logger log = Logger.getLogger( this.getClass() );
	CreaCopiaGuiaBoletaHelper helper = new CreaCopiaGuiaBoletaHelper();
	public ActionForward cargaFormulario(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		
		log.info("CopiaGuiaBoletaDispatchActions:cargaFormulario inicio");
		CopiaGuiaBoletaForm formulario = (CopiaGuiaBoletaForm)form;
		formulario.setNumeroBoleta(Constantes.STRING_CERO);
		log.info("CopiaGuiaBoletaDispatchActions:cargaFormulario fin");
		return mapping.findForward(Constantes.STRING_ACTION_COPIA_GUIA_BOLETA);
	
	}
	
	public ActionForward validaDocuento(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		log.info("CopiaGuiaBoletaDispatchActions:validaDocuento inicio");
		CopiaGuiaBoletaForm formulario = (CopiaGuiaBoletaForm)form;
		String numero = request.getParameter("numeroBoleta").toString();
		String tipo = request.getParameter("tipo").toString();
		HttpSession session = request.getSession(true);
		ArrayList<BoletaBean> arrBoletas= new ArrayList<BoletaBean>();
		String numerotipodoc="";
		
	
		formulario.setNumeroBoleta(numero);
		formulario.setDocumento(tipo);
		System.out.println("tipo doc  ==> "+tipo);
		
		arrBoletas = PosSeleccionPagoFacade.reimpresionBoletaelec(tipo, numero,session.getAttribute(Constantes.STRING_SUCURSAL).toString());
		
		
		switch(tipo) {
			case "B":
				numerotipodoc = Constantes.STRING_WS_DTYPE;
				break;
			case "O":
				numerotipodoc = Constantes.STRING_WS_DTYPE_GE;
				break;
			case "N":
				numerotipodoc = "NC/"+Constantes.STRING_WS_DTYPE_NC;
				break;
			case "T":
				numerotipodoc = Constantes.STRING_WS_DTYPE_GE;
				break;
			default:
				numerotipodoc = "";
				break;
		}
		
		/*
		System.out.println("Constantes.STRING_WS_DTYPE ==> "+Constantes.STRING_WS_DTYPE);
		System.out.println("Constantes.STRING_WS_DTYPE_GE ==> "+Constantes.STRING_WS_DTYPE_GE);
		System.out.println("Constantes.STRING_WS_DTYPE_NC ==> "+Constantes.STRING_WS_DTYPE_NC);
		*/
		
		//System.out.println("numerotipodoc ==> "+numerotipodoc);
		
		
	        
	        
		for(int cont = 0; cont < arrBoletas.size();cont++) {
			BoletaBean b = arrBoletas.get(cont);
			String sSeparador = "|";
			
			if(b.getNumero() == 0) {
				response.getWriter().print("");
			}else {	
					if(cont == 0) {
						response.getWriter().print(numerotipodoc+" "+b.getNif()+"-"+b.getDv()+" "+b.getNumero());
					}else {
						response.getWriter().print(sSeparador+numerotipodoc+" "+b.getNif()+"-"+b.getDv()+" "+b.getNumero());
					}
			}
		}
		
		
		log.info("CopiaGuiaBoletaDispatchActions:validaDocuento fin");
		return null;
		
		
				
	}
}
