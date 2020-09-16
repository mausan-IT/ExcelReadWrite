package cl.gmo.pos.venta.web.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaFacade;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;
import cl.gmo.pos.venta.web.helper.SeleccionPagoHelper;

public class SeleccionPagoDispatchActions extends DispatchAction{
	Logger log = Logger.getLogger( this.getClass() );
	SeleccionPagoHelper helper = new SeleccionPagoHelper();
	public SeleccionPagoDispatchActions(){}

	private void carga_formulario(SeleccionPagoForm formulario, HttpSession session, String fecha_formulario)
	{
		log.info("SeleccionPagoDispatchActions:carga_formulario  inicio");
		formulario = helper.cargaInicial(formulario, session, fecha_formulario);
		log.info("SeleccionPagoDispatchActions:carga_formulario  fin");
	}
	
	
	public ActionForward cargaAutorizadorDescuento(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 1");
		log.info("SeleccionPagoDispatchActions:cargaAutorizadorDescuento  inicio");
		log.info("SeleccionPagoDispatchActions:cargaAutorizadorDescuento  fin");
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		formulario.setTipo_pedido(request.getParameter("tipo").toString());
		//formulario.getTipo_pedido();
		return mapping.findForward(Constantes.FORWARD_AUTORIZADOR);
		
	}
	
	public ActionForward IngresaPago(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2");
				
		log.info("SeleccionPagoDispatchActions:IngresaPago  inicio");
		HttpSession session  = request.getSession(true);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		String accion = formulario.getAccion();
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BLANCO);
		session.setAttribute(Constantes.STRING_DESCUENTO,formulario.getDescuento());
		
		String pago_seguro = "N";
		String vtipoped  = "N";
		
		ArrayList<ProductosBean> lista_productos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
		
		//asigno tipoped
		if(formulario.getTipo_pedido() != null) {
			if(formulario.getTipo_pedido().equals("SEG")) {
				vtipoped = "S";
			}
		}
		
		for(ProductosBean b : lista_productos){
			if(b.getFamilia().equals("DES") && formulario.getTiene_documentos().equals("false")){//SE AGREGA FAMILIA INEXISTENTE PARA QUE EL PROGRAMA NO PASE POR ACA 20170622
				pago_seguro = "S";
			}
		}
		
		/*if (null != session.getAttribute(Constantes.STRING_ESTADO_FORM)) {
			formulario.setEstado_formulario_origen(session.getAttribute(Constantes.STRING_ESTADO_FORM).toString());
		}
		else
		{*/
			formulario.setEstado_formulario_origen(Constantes.STRING_BLANCO);
		//}
			
		if (Constantes.STRING_DESCUENTO_VENTA_DIRECTA.equals(accion)) {
			System.out.println("PASO POR SPAGO 2 1");

			double cant = formulario.getDescuento();
			this.carga_formulario(formulario, session, formulario.getFecha());
			helper.aplicaDescuentoVentaDirecta(session, formulario, cant);
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}
		if (Constantes.STRING_CONFIRMA_CAMBIO_BOLETA.equals(accion)) {
			System.out.println("PASO POR SPAGO 2 2");
			String resultado[] = new String[6];
			resultado = (String[])session.getAttribute(Constantes.STRING_RESULTADO_CAMBIO_FOLIO);
			String resultado_temp[] = new String[6];
			resultado_temp = helper.validaDocumento(formulario, formulario.getNumero_boleta_conf(), local);
			this.carga_formulario(formulario, session, formulario.getFecha());
			helper.realiza_cambio_boleta(resultado, formulario.getNumero_boleta_conf(), session, resultado_temp);
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}
		if (Constantes.STRING_VALIDA_BOLETA.equals(accion)) 
		{
			System.out.println("PASO POR SPAGO 2 3");
			String resultado[] = new String[6];
			this.carga_formulario(formulario, session, formulario.getFecha());
			System.out.println("Numero de boleta ==>"+formulario.getNumero_boleta()+"<==>"+local);
			resultado = helper.validaDocumento(formulario, formulario.getNumero_boleta(), local);
					
			//LMARIN 20150527 / PASO LOS DATOS PARA GENERAR LA BOLETA ELECTRONICA EN VENTAS DIRECTAS
			
			session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_EXITO);
			session.setAttribute(Constantes.STRING_DOCUMENTO, formulario.getNumero_boleta());
			session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, formulario.getTipo_doc());
			
			helper.TraeBoleta(formulario, session);
						
			session.setAttribute("SeleccionPagoForm", formulario);
			
			
			
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}
		if (Constantes.STRING_VOLVER.equals(accion)) 
		{
			System.out.println("PASO POR SPAGO 2 4");

			//return mapping.findForward(Constantes.FORWARD_DIRECTA);
		}
		if(Constantes.STRING_PAGAR.equals(accion))
		{
			System.out.println("PASO POR SPAGO 2 5");

			if (Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getEstado_formulario_origen())) 
			{
				System.out.println("PASO POR SPAGO 2 5 1");

				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BLOQUEA);
			}
			else
			{
				System.out.println("PASO POR SPAGO 2 5 2");
				if (helper.validaCaja(local, formulario.getFecha())) 
				{
					System.out.println("PASO POR SPAGO 2 5 2 1");

					if (Constantes.STRING_PEDIDO.equals(formulario.getOrigen())) 
					{
						System.out.println("PASO POR SPAGO 2 5 2 2");
						if (!formulario.getTiene_documentos().equals(Constantes.STRING_TRUE)) {
							System.out.println("PASO POR SPAGO CONVENIO");
							formulario.setConvenio(helper.traeConvenio(session));
						
						}
						else
						{
							System.out.println("PASO POR SPAGO NO CONVENIO");
							formulario.setConvenio(new ConvenioBean());
						}
						
						boolean valida_fpago_OA = helper.validaFormaPagoOA(formulario.getForma_pago(), formulario.getConvenio());
						
						if (!valida_fpago_OA) {
							session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA);
							formulario.setForma_pago(Constantes.STRING_CERO);
						}
						else
						{							
								if (helper.validaPrimerPagoConvenio(formulario, session)) {
									//valida la fecha									
									formulario.setListaPagos(helper.agregaPago(formulario, session));
									session.setAttribute("N_ISAPRE", formulario.getN_isapre());
									session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
								}
								
								System.out.println("Venta seguro ==> "+session.getAttribute("venta_Seguro"));
								
								//LMARIN BOLETA ELECTRONICA 20150601
								
								helper.TraeBoleta(formulario, session);
								System.out.println("Paso antes de setear SeleccionPagoForm");
								session.setAttribute("SeleccionPagoForm",formulario);
						}
						
						//CMRO New
							formulario.setImprimio_guia(helper.tieneEsteTipoDoc(formulario.getSerie(), "O"));
						//CMRO New
					}
					else
					{
						System.out.println("PASO POR SPAGO 2 5 2 3");
						if (!Constantes.STRING_ALBARAN_DEVOLUCION.equals(formulario.getOrigen())) {
							if (formulario.getForma_pago().equals("OA") || formulario.getForma_pago().equals("OASD")) 
							{
								session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA);
								formulario.setForma_pago(Constantes.STRING_CERO);
							}
							else
							{
								formulario.setListaPagos(helper.agregaPago(formulario, session));
								session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
								
							}
						}
						else
						{
							formulario.setListaPagos(helper.agregaPago(formulario, session));
							session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
						}
						
					}
				}
				else
				{
					System.out.println("PASO POR SPAGO 2 5 2 4");
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FECHA_PAGO);
				}
				
			}
			System.out.println("PASO POR SPAGO 2 5 3");
			this.carga_formulario(formulario, session, formulario.getFecha());
			if (null != formulario.getConvenio() && null != formulario.getConvenio().getId() && !(Constantes.STRING_BLANCO.equals(formulario.getConvenio().getId()))) {
				System.out.println("PASO POR SPAGO 2 5 4");
				System.out.println("convenio =====> "+ formulario.getConvenio().getId() );
				if (Constantes.STRING_FALSE.equals(formulario.getTiene_documentos())) {
					if ("S".equals(formulario.getConvenio().getImprime_guia()) && formulario.getTiene_documentos().equals(Constantes.STRING_FALSE)) {
						formulario.setSolo_guia(Constantes.STRING_TRUE);
						System.out.println("convenio 2 5 4 SOLO GUIA=====> " );
					}
					else
					{
						formulario.setSolo_boleta(Constantes.STRING_TRUE);
						System.out.println("convenio 2 5 4 SOLO BOLETA =====> " );
					}
				}
			}
			System.out.println("PASO POR SPAGO 2 5 5");
			System.out.println("convenio 2 5 4 SOLO BOLETA =====> "+formulario.getSolo_boleta()+"<====> SOLO guia =====> "+formulario.getSolo_guia());
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());			
			session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
			session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
			
			if(formulario.getListaPagos().size() > 0){
				System.out.println("PASO POR SPAGO 2 5 6");
				try{
					int crb = 0;
					for(PagoBean b : formulario.getListaPagos()){
						if(b.getForma_pago().equals("ISAPR") && b.isTiene_doc() == true){
							crb = 1;
						}
					}
					if(formulario.getConvenio().getIsapre().equals("S") &&  crb == 1){
						System.out.println("paso solo boleta");
						formulario.setSolo_boleta(Constantes.STRING_TRUE);
					}else{
						formulario.setTiene_fomas_pago("SI");
					}
				}catch(Exception e){
					System.out.println("Excepcion controlada==>"+e.getMessage());
					formulario.setTiene_fomas_pago("SI");
				}
				//formulario.setTiene_fomas_pago("SI");
			}else{
				System.out.println("PASO POR SPAGO 2 5 7");
				formulario.setTiene_fomas_pago("NO");
				
			}
			//OBLIGO A QUE PRIMER PAGO DEL SEGURO SEA POR GUIA , LMARIN 20170615
			
			
			//System.out.println("formulario.getTipo_pedido() 2018 =====> "+formulario.getTipo_pedido());
			
			if(pago_seguro.equals("S") || (vtipoped.equals("S") && formulario.getTiene_pagos() == 0 )){
				formulario.setSolo_boleta(Constantes.STRING_FALSE);
				//CMRO formulario.setSolo_guia(Constantes.STRING_TRUE);
				formulario.setSolo_guia(Constantes.STRING_FALSE);
				formulario.setSolo_recaudacion(Constantes.STRING_TRUE);
			}
			
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}
		if("pagarDevolcion".equals(accion)){
			System.out.println("PASO POR SPAGO 2 5 8");
			this.carga_formulario(formulario, session, formulario.getFecha());
			formulario.setListaPagos(helper.agregaPago(formulario, session));
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
			
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
			session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
			session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
			
			return mapping.findForward("pago_devolucion");			
		}	

	
		
		if(Constantes.STRING_IMPRIME_GUIA.equals(accion))
		{
		
		  System.out.println("PASO POR SPAGO 2 5 9");
		  this.carga_formulario(formulario, session, formulario.getFecha());
		  if(pago_seguro.equals("S") || vtipoped.equals("S")){
			    session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_IMPRIME_GUIA);
				//CMRO formulario.setTipo_doc(Constantes.CHAR_G);
				formulario.setTipo_doc(Constantes.CHAR_R);
		  }else{
		  		if (null == formulario.getConvenio().getId() || Constantes.STRING_BLANCO.equals(formulario.getConvenio().getId())) {
					//20140630  cruz blanca 50412 || se agrega venta seguro
					if (formulario.getDiferencia() > 0  && !formulario.getConvenio().getIsapre().equals("S") || pago_seguro.equals("S") || vtipoped.equals("S")) {
						System.out.println("paso impresion docuemnto vta seguro 1");
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_VALIDACION_TOTAL_PAGADO);				
					}
					else
					{
						session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_IMPRIME_GUIA);
						formulario.setTipo_doc(Constantes.CHAR_G);
					}
				}
				else
				{
					
					if (helper.verifica_OA_pagos(formulario)) 
					{
						session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_IMPRIME_GUIA);
						formulario.setTipo_doc(Constantes.CHAR_G);
					}
					else
					{
						//20140630  cruz blanca 50412 || se agrega venta seguro
						if (formulario.getDiferencia() > 0 && !formulario.getConvenio().getIsapre().equals("S") || pago_seguro.equals("S") || vtipoped.equals("S")) {
							System.out.println("paso impresion docuemnto vta seguro 2");
							session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_VALIDACION_TOTAL_PAGADO);
						}
						else
						{
							session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
							session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_IMPRIME_GUIA);
							formulario.setTipo_doc(Constantes.CHAR_G);
						}
					}
				}
				
		  	}
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}
		if (Constantes.STRING_VALIDA_FPAGO.equals(formulario.getAccion())) 
		{
			System.out.println("PASO POR SPAGO 2 5 10");
			/*
			this.carga_formulario(formulario, session);
			if (Constantes.STRING_PEDIDO.equals(formulario.getOrigen())) {
				boolean valida_fpago_OA = helper.validaFormaPagoOA(formulario.getForma_pago(), formulario.getConvenio());
				if (!valida_fpago_OA) {
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA);
					formulario.setForma_pago(Constantes.STRING_CERO);
				}
			}
			return mapping.findForward(Constantes.FORWARD_PAGO);
			*/
		}
		/*
		 * FECHA : 20140407
		 * DEVELOPER : @LMARIN 									 
		 * SOLICITANTE : OPERACIONES
		 * DESCRIPCION :Se modifica para restringir las formas de pago y anulaciones  
		 * 
		 */
		if(Constantes.STRING_ACTION_ELIMINAR_FORMA_PAGO.equals(formulario.getAccion()) || "eliminarFormaPagoBoleta".equals(formulario.getAccion())){
									
				System.out.println("PASO POR SPAGO 2 5 11");
									
						
				if (Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getEstado_formulario_origen())) 
				{
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BLOQUEA);
				}
				else
				{
					ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
					formulario.setListaPagos(listaPagos);
					//REALIZO RESPALDO DE BOLETA SI LO QUE REALIZO EN CAMBIO DE FORMA DE PAGO
					//SE COMENTA COPIA DE BOLETA 20140708
				    /*helper.respaldo_boleta(formulario);*/						
					
					String respuesta  = helper.eliminaFormaPago(formulario, local);
					
					
					if(respuesta.equals("TRUE")){
						formulario.setExitopago(Constantes.STRING_TRUE_MAY);
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ELIMINA_PAGO_EXITOSO);
					}else if(respuesta.equals("TRUE_2")){
						formulario.setExitopago("TRUE_2");
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ELIMINA_PAGO_EXITOSO);
					}else if(respuesta.equals("LIBERADO_1")){
						formulario.setExitopago("LIBERADO_1");
						session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
					}else if(respuesta.equals("LIBERADO_2")){
						formulario.setExitopago("LIBERADO_2");
						session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
					}else if(respuesta.equals("CERRADA")){
						formulario.setExitopago("CERRADA");
						session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
					}else if(respuesta.equals("ERROR")){
						formulario.setExitopago(Constantes.STRING_FALSE_MAY);
						session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
					}else{
						formulario.setExitopago("ERROR(1)");
						session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
					}
					//****************************************			
					//formulario.setListaPagos(helper.traePagos(formulario.getSerie(), "PEDIDO"));	
				}		
				
				session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
					
				if(Constantes.STRING_ACTION_ELIMINAR_FORMA_PAGO.equals(formulario.getAccion())){
					this.carga_formulario(formulario, session, formulario.getFecha());
					if (formulario.getDiferencia() == Constantes.INT_CERO) {
						formulario.setEstado(Constantes.STRING_PAGADO_TOTAL);
					}
					else
					{
						formulario.setEstado(Constantes.STRING_BLANCO);
					}
					session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
					session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
				}else{
					formulario.setLista_boletas(helper.traeListaBoletas(formulario.getSerie()));
				}
					
					
				if (Constantes.STRING_DIRECTA.equals(formulario.getOrigen())) {
					if (null == formulario.getListaPagos() || formulario.getListaPagos().size()==0)
					{
						helper.aplicaDescuentoVentaDirecta(session, formulario, 0);
					}
				}
	
				session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
					
				//****************************************
				log.info("SeleccionPagoDispatchActions:IngresaPago  fin");
				if("eliminarFormaPagoBoleta".equals(formulario.getAccion())){
					return mapping.findForward(Constantes.FORWARD_PAGO_BOLETA);
				}else{
					return mapping.findForward(Constantes.FORWARD_PAGO);
				}
			
		}
		else
		{
			System.out.println("PASO POR SPAGO 2 5 12");
			session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
			log.info("SeleccionPagoDispatchActions:IngresaPago  fin");		
			
			return mapping.findForward(Constantes.FORWARD_DIRECTA_PAGADA);
		}
	}

	public ActionForward cargaFormulario(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 13");
		//STRING_FORMA_PAGO_ORIGEN
		
		log.info("SeleccionPagoDispatchActions:cargaFormulario  inicio");	
		HttpSession session = request.getSession(true);		
		session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_INICIO);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		String fecha_alb = formulario.getFecha();
		String origen_form = session.getAttribute(Constantes.STRING_ORIGEN).toString();
		formulario.setOrigen(session.getAttribute(Constantes.STRING_ORIGEN).toString());
		this.carga_formulario(formulario, session, helper.traeFechaHoyFormateadaString());
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		VentaDirectaBean ventaBean = new VentaDirectaBean();		
    	ventaBean = PosVentaFacade.traeDatosGenericosVentaDirecta(local);
    	formulario.setPorcentaje_descuento_max(ventaBean.getPorcentaje_descuento_max());
		session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
		session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
		session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
		if (formulario.getDiferencia() == 0) {
			formulario.setEstado(Constantes.STRING_PAGADO_TOTAL);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_BLANCO);
		}
		if("ALBARAN_DIRECTA".equalsIgnoreCase(origen_form)){
			formulario.setFecha(fecha_alb);
		}


		log.info("SeleccionPagoDispatchActions:cargaFormulario  fin");
		return mapping.findForward(Constantes.FORWARD_PAGO);
	}
	
	public ActionForward cargaFormularioAlbaranDirecta(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 14");
		log.info("SeleccionPagoDispatchActions:cargaFormulario  inicio");	
		HttpSession session = request.getSession(true);
		session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_INICIO);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		String fecha_alb = formulario.getFecha();
		String origen_form = session.getAttribute(Constantes.STRING_ORIGEN).toString();
		formulario.setOrigen(session.getAttribute(Constantes.STRING_ORIGEN).toString());
		this.carga_formulario(formulario, session, helper.traeFechaHoyFormateadaString());
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		VentaDirectaBean ventaBean = new VentaDirectaBean();		
    	ventaBean = PosVentaFacade.traeDatosGenericosVentaDirecta(local);
    	formulario.setPorcentaje_descuento_max(ventaBean.getPorcentaje_descuento_max());
		session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
		session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
		session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
		if (formulario.getDiferencia() == 0) {
			formulario.setEstado(Constantes.STRING_PAGADO_TOTAL);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_BLANCO);
		}
		if("ALBARAN_DIRECTA".equalsIgnoreCase(origen_form)){
			formulario.setFecha(fecha_alb);
		}

		log.info("SeleccionPagoDispatchActions:cargaFormulario  fin");
		return mapping.findForward(Constantes.FORWARD_PAGO);
	}
	
	public ActionForward cargaFormularioCobroAlbaran(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 15");
		log.info("SeleccionPagoDispatchActions:cargaFormulario  inicio");	
		HttpSession session = request.getSession(true);
		
		session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_INICIO);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		String fecha = formulario.getFecha();
		session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());
		formulario.setOrigen(session.getAttribute(Constantes.STRING_ORIGEN).toString());
		this.carga_formulario(formulario, session, formulario.getFecha());
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		VentaDirectaBean ventaBean = new VentaDirectaBean();		
    	ventaBean = PosVentaFacade.traeDatosGenericosVentaDirecta(local);
    	formulario.setPorcentaje_descuento_max(ventaBean.getPorcentaje_descuento_max());
		session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
		session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
		session.setAttribute(Constantes.STRING_CABECERA_GUIA, formulario);
		if (formulario.getDiferencia() == 0) {
			formulario.setEstado(Constantes.STRING_PAGADO_TOTAL);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_BLANCO);
		}
		formulario.setFecha(fecha);
		log.info("SeleccionPagoDispatchActions:cargaFormulario  fin");
		return mapping.findForward(Constantes.FORWARD_PAGO);
	}
	
	
	public ActionForward cargaMostrarPagosBoletas(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 16");
		log.info("SeleccionPagoDispatchActions:cargaFormulario  inicio");	
		HttpSession session = request.getSession(true);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		
		try{
			String tipo = request.getParameter("tipo").toString();
			String cdg_vta = formulario.getSerie();
			formulario.setListaPagos(helper.traePagos(cdg_vta, tipo));
			formulario.setLista_boletas(helper.traeListaBoletas(cdg_vta));
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return mapping.findForward("cargaMostrarPagosBoletas");
	}
	
	public ActionForward cargaMostrarPagosBoletasAlbaranes(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 17");
		log.info("SeleccionPagoDispatchActions:cargaFormulario  inicio");	
		HttpSession session = request.getSession(true);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		
		try{
			String tipo = request.getParameter("tipo").toString();
			String cdg_vta = formulario.getSerie();
			formulario.setListaPagos(helper.traePagos(cdg_vta, tipo));
			formulario.setLista_boletas(helper.traeListaBoletasAlbaranes(cdg_vta, "DIRECTA"));
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, formulario.getListaPagos());
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return mapping.findForward("cargaMostrarPagosBoletasAlbaranes");
	}
	
	public ActionForward imprime_documento(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 18");
		log.info("SeleccionPagoDispatchActions:Imprime docuento  inicio");	
		HttpSession session = request.getSession(true);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		this.carga_formulario(formulario, session, formulario.getFecha());
		String tipo_doc = request.getParameter(Constantes.STRING_TIPO).toString();
		
		
			if (tipo_doc.equals(Constantes.STRING_BOLETA)) {
				helper.TraeBoleta(formulario, session);
				return mapping.findForward(Constantes.STRING_PLANTILLA_BOLETA);
			}
			else
			{
				helper.TraeGuia(formulario, session);
				return mapping.findForward(Constantes.STRING_PLANTILLA_GUIA);
			}
	}
	
	public ActionForward eliminaFPagoBoleta(ActionMapping mapping,
			ActionForm form,	
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		System.out.println("PASO POR SPAGO 2 5 19");
		HttpSession session = request.getSession(true);
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		
		try{
			//if para determinar si la solicitud de eliminacion viene de la pagina de mostrar_pagos_boleta_albaranes
			//o biene de la pagian de seleccion_pagoFFF
			if("eliminarFormaPagoBoletaAlbaranes".equals(formulario.getAccion())){
				SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
				ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
				formulario.setListaPagos(listaPagos);
				boolean respuesta  = helper.eliminaFormaPagoAlbaranes(formulario, local, "DIRECTA");
				formulario.setLista_boletas(helper.traeListaBoletasAlbaranes(formulario.getSerie(), "DIRECTA"));
				ArrayList<PagoBean> lista_pagos = helperPagos.traePagos(formulario.getSerie(), "DIRECTA");
				session.setAttribute(Constantes.STRING_LISTA_PAGOS,lista_pagos);
				formulario.setRespuesta_fpago_albaran(String.valueOf(respuesta));
				
				if(null != lista_pagos){
					formulario.setListaPagos(lista_pagos);
				}else{
					formulario.setListaPagos(new ArrayList<PagoBean>());
				}
				
				if(formulario.getListaPagos().size() > 0){
					formulario.setTiene_fomas_pago("SI");
				}else{
					formulario.setTiene_fomas_pago("NO");
				}
			}else{
				//selecccion_pago
				
				SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
				ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
				formulario.setListaPagos(listaPagos);
				boolean respuesta  = helper.eliminaFormaPagoAlbaranes(formulario, local, "DIRECTA");
				formulario.setLista_boletas(helper.traeListaBoletasAlbaranes(formulario.getSerie(), "DIRECTA"));
				ArrayList<PagoBean> lista_pagos = helperPagos.traePagos(formulario.getSerie(), "DIRECTA");
				session.setAttribute(Constantes.STRING_LISTA_PAGOS,lista_pagos);
				if(formulario.getListaPagos().size() > 0){
					formulario.setTiene_fomas_pago("SI");
				}else{
					formulario.setTiene_fomas_pago("NO");
				}
				
				
				VentaDirectaBean ventaBean = new VentaDirectaBean();		
		    	ventaBean = PosVentaFacade.traeDatosGenericosVentaDirecta(local);
		    	formulario.setPorcentaje_descuento_max(ventaBean.getPorcentaje_descuento_max());
		    	
		    	formulario.setListaFormasPago(PosUtilesFacade.traeFormasPago());
				session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, formulario.getListaFormasPago());
				session.setAttribute(Constantes.STRING_CABECERA_BOLETAS, formulario);
				if(respuesta){
					formulario.setExitopago(Constantes.STRING_TRUE_MAY);
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ELIMINA_PAGO_EXITOSO);
				}else{
					formulario.setExitopago(Constantes.STRING_FALSE_MAY);
					session.setAttribute(Constantes.STRING_ERROR, "ELIMINA_PAGO_FALLA");
				}
				helperPagos.cargaInicial(formulario, session, formulario.getFecha());
				
				if (formulario.getDiferencia() == Constantes.INT_CERO) {
					formulario.setEstado(Constantes.STRING_PAGADO_TOTAL);
				}
				else
				{
					formulario.setEstado(Constantes.STRING_BLANCO);
				}
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if("eliminarFormaPagoSeleccionPago".equals(formulario.getAccion())){
			
			return mapping.findForward(Constantes.FORWARD_PAGO);
		}else{
			return mapping.findForward("cargaMostrarPagosBoletasAlbaranes");
		}
	}
	
	/*
	 * FECHA : 20140410
	 * DEVELOPER : @LMARIN 									 
	 * SOLICITANTE : OPERACIONES
	 * DESCRIPCION :Se modifica para restringir las formas de pago y anulacions    
	 */
	public ActionForward valida_des_forma_pago(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
		
		 return mapping.findForward(Constantes.FORWARD_AUTORIZADOR_FPAGO);
	}
	 /*
	 * FECHA : 20140417
	 * DEVELOPER : @LMARIN 									 
	 * SOLICITANTE : OPERACIONES
	 * DESCRIPCION :Se genera una pantalla en donde solo se ingresaran nuevamente los numeros de boleta
	 * 
	 */
	public ActionForward muestra_caja__reasigna_boleta(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
	 
	  return mapping.findForward(Constantes.STRING_ACTION_REASIGNA_BOLETA);
    }
	
	
	public ActionForward reimprimeboleta(ActionMapping mapping,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response) throws Exception
	{
		  int valor = 0;
		 
		  SeleccionPagoForm formulario = (SeleccionPagoForm)form;	
		  
		  
		  valor = helper.reimprimeboleta(formulario);
		  
		  response.getWriter().print(valor);		
		  return null;
		 		  
	}
	
	/*
	 * CONVENIO CLIENTES - CRUZ BLANCA 20141121
	 * Metodo que exige cliente para hacer DTO
	 */
	public ActionForward exige_valida_dto(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
	 
		String PASO ="";
		SeleccionPagoForm formulario = (SeleccionPagoForm)form;
		if(formulario.getAccion().equals("valida")){
			PASO = Constantes.STRING_ACTION_VALIDA_DTO;
		}	
		
		return mapping.findForward(PASO);
	 		  
    }
	
	/*
	 *LMARIN BOLETA ELECTRONICA 20150525 
	 *
	 */
	public ActionForward reimpresionBoletaelec(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
	  HttpSession session = request.getSession(true);
	  ArrayList<BoletaBean> boleta= new ArrayList<BoletaBean>();
	 
	  SeleccionPagoForm formulario = (SeleccionPagoForm)form;	
	  formulario.setBoleta_tienda(session.getAttribute(Constantes.STRING_SUCURSAL).toString());	  
	  boleta = helper.reimpresionBoletaelec(formulario);
	 
	  for(BoletaBean b : boleta){
		  response.getWriter().print("39 "+b.getNif()+"-"+b.getDv()+" "+b.getNumero());		

	  }
	  
	  //response.getWriter().print("39 "+boleta.get(0).getNif()+"-"+boleta.get(0).getDv()+" "+boleta.get(0).getNumero());		
	  return null;
	 		  
    }
	public ActionForward validaDtoCliente(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		
	  HttpSession session  = request.getSession(true);
	  String valor = "";
	 
	  SeleccionPagoForm formulario = (SeleccionPagoForm)form;	
	  	  
	  valor = helper.validaDtoCliente(formulario);
	  session.setAttribute("DTOWEB", valor);
	  System.out.println("Valor ===> "+valor);
	  
	  response.getWriter().print(valor);		
	  return null;
	 		  
    }
	
	
}
