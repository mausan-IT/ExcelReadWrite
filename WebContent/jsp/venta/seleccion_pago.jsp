	
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	
	<!DOCTYPE html>
	<html:html>
	
	<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/subModal.css" />
	<link rel="stylesheet" type="text/css" href="css/formatosFormularios.css" />
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>	
	<script type="text/javascript" src="js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>		
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/subModal.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/venta/seleccion_pago.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/jquery.datepick.css"/>
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css"/>
	
	<script type="text/javascript" src="js/jquery.datepick.js""></script>
	<script type="text/javascript" src="js/jquery.datepick-es.js"></script>
	<script type="text/javascript">
		var $j = jQuery.noConflict();
		load();
		
		
	
		$j(function() {
			$j('#popupDatepicker').datepick();
		});
	
		function calculaTotal() {
			var descuento_max = document.getElementById('descuento_max').value;
			var dto = parseFloat(document.getElementById('descuentoTotal').value);
			document.getElementById('descuentoTotal').value = dto;
			if (parseInt(dto) <= parseInt(descuento_max)) {
				calcula();
				calcula_anticipo_minimo();
			} else {
				if(document.getElementById('origen').value == "PEDIDO")
				{
					var url = "<%=request.getContextPath()%>/SeleccionPago.do?method=cargaAutorizadorDescuento";		
						showPopWin(url, 690, 130, devuelve_descuento, false);
	
				}
				else
				{
					alert("El descuento debe ser menor o igual al " + descuento_max + "%");
					document.getElementById('descuentoTotal').value = 0.0;
				}
			}
		}
		
		function imprimirBoleta() 
		{
	        	$j("#accion",window.parent.document).val("Pago_Exitoso");
	        	
	        	$j("#accion").val("valida_boleta");
	        	
	        	document.forms[0].submit();
		}

		/*ASEBASTIAN 20190812*/
		function imprimirGuiaElectronica() 
		{
	        	$j("#accion",window.parent.document).val("Pago_Exitoso");
	        	
	        	$j("#accion").val("valida_guia");
	        	
	        	document.forms[0].submit();
		}
		
		/*LMARIN 20140416*/
		function reimprimirBoleta() 
		{
				$j.ajax({
					  type: "POST",
					  url: 'SeleccionPago.do?method=reimpresionBoletaelec',
					  dataType: "text",
					  data:"boleta_fecha="+$j("#fpago").val()+"&serie="+$j("#serie").val()+"&boleta_tienda=T002",
					  asycn:false,
					  success: function(data){				  		
					  		if(data != ""){
					  			var tipoimpresion = $j("#tipoimp").val() == "1" ? "Carta": "Termica";
	 							var urlbol = "http://srvgmosignaturepos/PRD/"+tipoimpresion+"/"+data+".pdf";
					  			window.open(urlbol);
					  			dialogo("<%=request.getContextPath()%>/SeleccionPago.do?method=imprime_documento&tipo=BOLETA");				  			
					  		}
				 	  }
			 	});
				
		}
		
 		function imprimirGuia() 
		{
			//var voucher;
	       	//voucher = window.open("<%=request.getContextPath()%>/CreaGuiaServlet");
	       	window.open("<%=request.getContextPath()%>/SeleccionPago.do?method=imprime_documento&tipo=GUIA");
			//popup('Numero_Documento' , ancho, alto);
			//CMRO respetando el flujo original
			document.getElementById('accion').value = "valida_boleta";
			document.seleccionPagoForm.submit();
		}
		
		function eliminar_pago_albaran(forma_pago, fecha_pago){
			document.getElementById('accion').value="eliminarFormaPagoSeleccionPago";
			document.getElementById('f_pago').value=forma_pago;	
			document.getElementById('fech_pago').value=fecha_pago;
			var x = document.forms[0];		
			x.action = "<%=request.getContextPath()%>/SeleccionPago.do?method=eliminaFPagoBoleta";
			x.submit();	
		}
	</script>
	<style type="text/css">
	<!--
	#blanket {
	   display: block;
	   position: absolute;
	   top: 0%;
	   left: 0%;
	   width: 100%;
	   height: 100%;
	   background-color: black;
	   z-index:9001;
	   opacity:0.6;
	   filter:alpha(opacity=60);
	}
	#documentos {
	position:absolute;
		z-index: 9002; /*ooveeerrrr nine thoussaaaannnd*/
	}
	
	#Numero_Documento {
	position:absolute;
		z-index: 9003; /*ooveeerrrr nine thoussaaaannnd*/
	}
	#Confirmacion {
	position:absolute;
		z-index: 9004; /*ooveeerrrr nine thoussaaaannnd*/
	}
	#Documento_cambio_folio {
	position:absolute;
		z-index: 9005; /*ooveeerrrr nine thoussaaaannnd*/
	}
	-->
	</style>
	  <title><bean:message key="title.pos"/></title>
	
	
	
	</head>
	<body>
		<bean:define id="msje" type="String">
			<bean:write name="mensaje" scope="session"/>
		</bean:define>
	<logic:equal scope="session" name="error" value="BLOQUEADO">
		<body onload="venta_bloqueada();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="inicio">
		<body onload="inicio();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="pago_repetido">
		<body onload="pago_repetido();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="error_fecha_anterior">
		<body onload="pago_fecha_anterior();if(history.length>0)history.go(+1)">
	</logic:equal>
	
	<logic:equal scope="session" name="error" value="ELIMINA_PAGO_FALLA">
		<body onload="inicio_pagina();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="ELIMINA_PAGO_EXITO">
		<body onload="inicio_pagina();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="imprime_guia">
		<body onload="imprimirGuia();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="BOLETA_FALLA">
		<body onload="vuelve_confirmacion('falla', '${msje}');if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="BOLETA_EXITO">
		<body onload="vuelve_confirmacion('exito', '${msje}');if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="BOLETA_CAMBIO_FOLIO">
		<body onload="pregunta_cambio_folio('${msje}');if(history.length>0)history.go(+1)">
	</logic:equal>
	
	<logic:equal scope="session" name="error" value="PAGADO_TOTAL">
		<body onload="generaBoleta2();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="valida_100">
		<body onload="valida_100();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="ERROR_OA">
		<body onload="error_fpago_oa();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="error_fpago_convenio_oa">
		<body onload="error_fpago_oa_convenio();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="error_fpago_convenio_oa_2">
		<body onload="error_fpago_oa_convenio_2();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="caja_cerrada">
		<body onload="error_caja_cerrada();if(history.length>0)history.go(+1)">
	</logic:equal>
	<logic:equal scope="session" name="error" value="error_guia_pago_oa">
		<body onload="error_guia_pago_oa();if(history.length>0)history.go(+1)">
	</logic:equal>
	
	
	
	
	
		<html:form action="/SeleccionPago?method=IngresaPago" styleId="form1">
			<html:hidden property="accion" value="" styleId="accion"/>
			<html:hidden property="estado" styleId="estado" name="seleccionPagoForm"/>
			<html:hidden property="tipo_doc" styleId="tipo_doc"/>
			<html:hidden property="tiene_pagos" styleId="tiene_pagos" name="seleccionPagoForm"/>
			<html:hidden property="origen" styleId="origen"/>
			<html:hidden property="tiene_pagos_actuales" styleId="tiene_pagos_actuales" name="seleccionPagoForm"/>
			<html:hidden property="giro_descripcion" styleId="giro_descripcion"/>
			<html:hidden property="provincia_descripcion" styleId="provincia_descripcion"/>
			<html:hidden property="f_pago" value="" styleId="f_pago"/>
			<html:hidden property="fech_pago" value="" styleId="fech_pago"/>
			<html:hidden property="exitopago"  styleId="exitopago"/>
			<html:hidden property="tiene_documentos" styleId="tiene_doc" name="seleccionPagoForm"/>
			<html:hidden property="tiene_fomas_pago" styleId="tiene_fomas_pago" name="seleccionPagoForm"/>
			
			<html:hidden property="autorizador"  value="" styleId="autorizador" name="seleccionPagoForm"/>		
			<html:hidden property="tipoaccion"  value="" styleId="tipoaccion" name="seleccionPagoForm"/>
		    <html:hidden property="numero_boleta_up"  value="" styleId="numero_boleta_up" name="seleccionPagoForm"/>
		    <html:hidden property="rut_vs"  value="" styleId="rut_vs" name="seleccionPagoForm"/>
		    <html:hidden property="monto_des_vs"  value="" styleId="monto_des_vs" name="seleccionPagoForm"/>
		    <html:hidden property="imprimio_guia" styleId="imprimio_guia"/>
		    <html:hidden property="imprimio_recibo" styleId="imprimio_recibo"/>
		    <html:hidden property="solo_guia" styleId="solo_guia"/>
		    <html:hidden property="solo_boleta" styleId="solo_boleta"/>
			
			
			
			<logic:equal value="PEDIDO" name="seleccionPagoForm" property="origen">
				<html:hidden property="porcentaje_descuento_max" styleId="descuento_max" name="ventaPedidoForm"/>
				<html:hidden property="tipo_pedido" styleId="tipo_pedido" name="ventaPedidoForm"/>
				
			</logic:equal>
			<logic:equal value="DIRECTA" name="seleccionPagoForm" property="origen">
				<html:hidden property="porcentaje_descuento_max" styleId="descuento_max" name="ventaDirectaForm"/>
				<html:hidden property="descuento" styleId="dto" name="seleccionPagoForm"/>
			</logic:equal>
			<logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
				<html:hidden property="porcentaje_descuento_max" styleId="descuento_max" name="seleccionPagoForm"/>
			</logic:equal>
			<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
				<html:hidden property="porcentaje_descuento_max" styleId="descuento_max" name="seleccionPagoForm"/>
			</logic:equal>
			<table width="680" cellspacing="0" align="center">
				<tr>
					<td  align="left" bgcolor="#373737" id="txt4"><bean:message key="seleccion.pago"/></td>
					<td  align="right" bgcolor="#373737" id="txt4">
					
						 		<logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
	                            	<a href="" onclick="refreshAlbaran()"> 
											<img src="images/cancel.png" width="15" height="15" border="0" title="Cerrar"> 
									</a>
	                            </logic:equal>
	                           
	                            
	                            <logic:notEqual value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
	                            
	                            	<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
		                            	<a href="" onclick="refreshAlbaran()"> 
											<img src="images/cancel.png" width="15" height="15" border="0" title="Cerrar"> 
										</a>
	                            	</logic:equal>
	                            	<logic:notEqual value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
			                            <a href="" onclick="refresh()"> 
											<img src="images/cancel.png" width="15" height="15" border="0" title="Cerrar"> 
										</a>
		                            </logic:notEqual>
	                            </logic:notEqual>
					
					
						
						
						
						
					</td>
				</tr>
				</table>
			<table width="680" cellspacing="1" align="center">
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.fecha"/></td>
					<td colspan="3" id="txt5" bgcolor="#c1c1c1">
					<logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
						<html:text readonly="true"	property="fecha"  size="8" styleClass="fto fecha_enc" />&nbsp;&nbsp;&nbsp;
					</logic:equal>
					<logic:notEqual value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
						<html:text readonly="true"	property="fecha"  size="8" styleClass="fto fecha_enc" styleId="popupDatepicker"/>&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
							
							 
					<bean:message key="seleccion.numero.serie"/> <html:text property="serie" styleId="serie" size="10" styleClass="fto" readonly="true"/></td>
				</tr>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.nif"/></td>
					<td id="txt5" bgcolor="#c1c1c1"><html:text property="nif" styleId="nif" size="15" styleClass="fto" readonly="true" />
					</td>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.razon.social"/></td>
					<td id="txt5" bgcolor="#c1c1c1"><html:text property="razon"
							size="35" styleClass="fto" readonly="true" />
					</td>
				</tr>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.direccion"/></td>
					<td id="txt5" bgcolor="#c1c1c1"><html:text property="direccion"  readonly="true"  
							size="30" styleClass="fto"/>
					</td>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.provincia"/></td>
					<td id="txt5" bgcolor="#c1c1c1">
						<html:text property="provincia_descripcion" readonly="true" styleClass="fto"></html:text>
					</td>
				</tr>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1" height="23" style="height: 23px"><bean:message
							key="seleccion.poblacion" />
					</td>
					<td id="txt5" bgcolor="#c1c1c1" height="23"><html:text  property="poblacion" styleClass="fto" readonly="true" />
					</td>
					<td id="txt5" bgcolor="#c1c1c1" align="left" height="23"><bean:message key="seleccion.giro"/></td>
					<td id="txt5" bgcolor="#c1c1c1" height="23">
						<html:text property="giro_descripcion" styleId="giro"  readonly="true" styleClass="fto"></html:text>
						</td>
				</tr>
				</table>
				<table width="680" cellspacing="0" align="center">
				<tr>
					<td align="left" bgcolor="#373737" id="txt4" width="75%"><bean:message key="seleccion.cobro"/>
					
					</td>
					<td bgcolor="#373737" id="txt4" align="right" width="5%">
					<logic:notEqual value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
						<a id="gurdar" href="#"  style="color:#FFFFFF;
																		font-family:Helvetica, Arial, sans-serif;
																		font-size:10px;
																		font-weight:bold;
																		height:10px;
																		padding:1px;">
							<bean:message key="seleccion.guardar"/>
						</a>
					</logic:notEqual>	
					<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
						<a id="gurdar" href="#" onclick="guarda_PagoAlbaran()" style="color:#FFFFFF;
																		font-family:Helvetica, Arial, sans-serif;
																		font-size:10px;
																		font-weight:bold;
																		height:10px;
																		padding:1px;">
							<bean:message key="seleccion.guardar"/>
						</a>
					</logic:equal>
					</td>
					<td bgcolor="#373737" id="txt4" align="right" width="5%">	
					<logic:notEqual value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">	
								
							<a id="imprimir" href="#" onclick="generaBoleta()" style="color:#FFFFFF;
																				font-family:Helvetica, Arial, sans-serif;
																				font-size:10px;
																				font-weight:bold;
																				height:10px;
																				padding:1px;">
								<bean:message key="seleccion.imprimir"/>
							</a>
					</logic:notEqual>	
					</td>
					<!--<td bgcolor="#373737" id="txt4" align="right" width="5%">
						<a id="reimprimir" href="javascript:void(0)"  style="color:#FFFFFF;font-family:Helvetica, Arial, sans-serif;font-size:10px;font-weight:bold;height:10px;">
								<p>ReimprImir</p>
						</a>		
					</td>-->
				</tr>
				</table>
				<html:hidden property="v_total" styleId="v_total" name="seleccionPagoForm"/>
				<table  width="680" cellspacing="1" align="center">
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.valor.total.pago"/></td>
					<td id="txt5" bgcolor="#c1c1c1">
					<html:text
							name="seleccionPagoForm" property="v_total_parcial" styleId="sumaParcial" readonly="true" size="10" styleClass="fto"/>
							&nbsp; 
							<!-- 
							<bean:message key="seleccion.total.sd"/> = 
							<logic:equal value="PEDIDO" name="seleccionPagoForm" property="origen">
							<html:text name="ventaPedidoForm" property="total" styleId="sumaTotal" readonly="true" size="10" styleClass="fto"/>
							</logic:equal>
							 -->
							<logic:equal value="DIRECTA" name="seleccionPagoForm" property="origen">
							<html:text name="ventaDirectaForm" property="sumaTotal" styleId="sumaTotal" readonly="true" size="10" styleClass="fto"/>
							</logic:equal>
							<logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
							<html:text name="seleccionPagoForm" property="suma_total_albaranes" styleId="sumaTotal" readonly="true" size="10" styleClass="fto"/>
							</logic:equal>
							<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
							<html:text name="seleccionPagoForm" property="suma_total_albaranes" styleId="sumaTotal" readonly="true" size="10" styleClass="fto"/>
							</logic:equal>
							
					</td>
					<td id="txt5" bgcolor="#c1c1c1" rowspan="5"  colspan="2">
					 
	<!-- TABLA QUE REGISTRA LOS PAGOS REALIZADOS -->
	
			<table  width="100%" cellspacing="0" cellpadding="0">
	        			<tr id="thead">
	                    <th scope="col" id="txt4" bgcolor="#373737" width="30%"><bean:message key="seleccion.fecha"/></th>
	                    <th scope="col" id="txt4" bgcolor="#373737" width="30%"><bean:message key="seleccion.forma.pago"/></th>
	                    <th scope="col" id="txt4" bgcolor="#373737" width="40%"><bean:message key="seleccion.cantidad"/></th>
	                    <th scope="col" id="txt4" bgcolor="#373737" width="40%"><bean:message key="seleccion.eliminar.pago"/></th>
	                </tr>
	           </table>
	           <logic:present property="listaPagos" name="seleccionPagoForm">
	                <div id="scrolling_pagos">
	                <table id="detalle_pagos" width="100%" cellspacing="0">
	                <logic:iterate id="pagos" property="listaPagos" name="seleccionPagoForm">
	                <bean:define id="forma_pago" type="String">
	                	<bean:write name="pagos" property="forma_pago"/>
	                </bean:define>
	                <bean:define id="fech_pago" type="String">
	                	<bean:write name="pagos" property="fecha" format="dd/MM/yyyy" />
	                </bean:define>
	                
	                        <tr >
	                            <td id="txt5" bgcolor="#c1c1c1" width="30%"><bean:write name="pagos" property="fecha" format="dd/MM/yyyy" /><input type="hidden" id="fpago" value="${fech_pago}" /></td>
	                            <td id="txt5" bgcolor="#c1c1c1" width="30%" align="center"><bean:write name="pagos" property="detalle_forma_pago" /><input type="hidden" id="dpago"  value="${forma_pago}"/></td>
	                            <td id="txt5" bgcolor="#c1c1c1" width="40%" align="center"><bean:write name="pagos" property="cantidad" format="$###,###.##" /></td>
	                            <td id="txt5" bgcolor="#c1c1c1" width="40%" align="center">
	                            
	                            <logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
	                            	<a href="#" id="el" onclick="eliminar_pago_albaran('${forma_pago}','${fech_pago}');" >
		                            	<img src="images/cancel.png" width="15" height="15" border="0" title="Eliminar Pago">
		                            </a>
	                            </logic:equal>
	                           
	                            
	                            <logic:notEqual value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
	                            
	                            	<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
		                            	<a href="#" id="el" onclick="eliminar_pago_albaran('${forma_pago}','${fech_pago}');" >
			                            	<img src="images/cancel.png" width="15" height="15" border="0" title="Eliminar Pago">
			                            </a>
	                            	</logic:equal>
	                            	<logic:notEqual value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
			                            <a href="#" id="el"  onclick="eliminar_pago('${forma_pago}','${fech_pago}','<%=request.getContextPath()%>');" >
			                            	<img src="images/cancel.png" width="15" height="15" border="0" title="Eliminar Pago">
			                            </a>
		                            </logic:notEqual>
	                            </logic:notEqual>
	                            
	                            	
	                            </td>
	                        </tr>
	                    </logic:iterate>
	               </table> 
	               </div> 
	            </logic:present> 
	<!-- FIN -->
					</td>
				</tr>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.valor.pagar"/></td>
					<td id="txt5" bgcolor="#c1c1c1"><html:text size="10" name="seleccionPagoForm" property="v_a_pagar" styleId="sumaPagar" value="" onkeypress="return validar_numerico(event)" onblur="javascript: valida_monto()" styleClass="fto"/>
						<logic:equal value="PEDIDO" name="seleccionPagoForm" property="origen">
							<bean:message key="seleccion.valor.anticipo"/>
								<html:text name="seleccionPagoForm" property="anticipo_pedido" styleId="anticipo_def" readonly="true" size="10" styleClass="fto" />
								<html:hidden name="seleccionPagoForm" property="porcentaje_anticipo_pedido" styleId="porc_anticipo_def" styleClass="fto"/>
						</logic:equal>
					
					</td>
				</tr>
				<logic:equal value="DIRECTA" name="seleccionPagoForm" property="origen">
				<tr>
					
					<td id="txt5" bgcolor="#c1c1c1">
					
						<bean:message key="seleccion.descuento"/></td>
					<td id="txt5" bgcolor="#c1c1c1">
						<logic:equal property="tiene_pagos" name="seleccionPagoForm" value="0">
							<input type="text" value='<bean:write name="seleccionPagoForm" property="descuento"/>' size="5" class="fto" id="descuentoTotal" onblur="javascript: calculaTotalvtaDirecta()" onkeypress="return validar_numerico(event)"/>%
						</logic:equal>	
						<logic:equal property="tiene_pagos" name="seleccionPagoForm" value="1">
							<html:text	name="seleccionPagoForm" property="descuento" size="5"
								styleId="descuentoTotal" readonly="true" styleClass="fto"/>%
						</logic:equal>
						
					
					</td>
						
				</tr>
				</logic:equal>
				<logic:equal value="ALBARAN_DIRECTA" name="seleccionPagoForm" property="origen">
				<tr>
					
					<td id="txt5" bgcolor="#c1c1c1">
					
						<bean:message key="seleccion.descuento"/></td>
					<td id="txt5" bgcolor="#c1c1c1">
						<logic:equal property="tiene_pagos" name="seleccionPagoForm" value="0">
							<html:text	styleClass="fto" name="seleccionPagoForm" property="descuento" size="5"
								styleId="descuentoTotal" onblur="javascript: calculaTotal()" value="0"
								onkeypress="return validar_numerico(event)" />%
						</logic:equal>	
						<logic:equal property="tiene_pagos" name="seleccionPagoForm" value="1">
							<html:text	name="seleccionPagoForm" property="descuento" size="5"
								styleId="descuentoTotal" readonly="true" styleClass="fto"/>%
						</logic:equal>					
					
					</td>
						
				</tr>
				</logic:equal>
				<logic:equal value="ALBARAN_DEVOLUCION" name="seleccionPagoForm" property="origen">
				<tr>
					
					<td id="txt5" bgcolor="#c1c1c1">
					
					<bean:message key="seleccion.descuento"/></td> 
					<td id="txt5" bgcolor="#c1c1c1">
						<logic:equal property="tiene_pagos" name="seleccionPagoForm" value="0">
							<html:text	styleClass="fto" name="seleccionPagoForm" property="descuento" size="5"
								styleId="descuentoTotal" onblur="javascript: calculaTotal()" value="0"
								onkeypress="return validar_numerico(event)" readonly="true" />%
						</logic:equal>			
					</td>
						
				</tr>
				</logic:equal>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.diferencia"/></td>
					<td id="txt5" bgcolor="#c1c1c1">
						
					<html:text property="diferencia" size="10" name="seleccionPagoForm" styleId="diferencia" readonly="true" styleClass="fto"/>
					
					<bean:define id="diferencia_total" type="String">
	                     <bean:write name="seleccionPagoForm" property="diferencia"/>
	                </bean:define>
	                <html:hidden value ='${diferencia_total}' styleId="diferencia_total" property="diferencia_total"/>
	                
					</td>
				</tr>
				<tr>
					<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.forma.pago"/></td>
					<td id="txt5" bgcolor="#c1c1c1" style="position:relative;">
						<html:select property="forma_pago" styleId="forma_pago" styleClass="fto" value="">
							<html:option value="0"><bean:message key="seleccion.forma.pago"/></html:option>
							<html:optionsCollection property="listaFormasPago" name="seleccionPagoForm" label="descripcion" value="id"/>
						</html:select>
						<div id="crb_input" style="display:none; float:left;">&nbsp;<label>N� Doc Isapre</label><html:text property="n_isapre" styleId="n_isapre" size="15" styleClass="fto" maxlength="14" onkeypress="return validar_numerico(event);" ></html:text> </div>
					</td>
				</tr>
				<!--  Agrego forma de pago GARANTIA-->
				<tr id="fpago_seg" style="display:none">
					<td id="txt5" bgcolor="#c1c1c1">Detalle F. Pago Seguro:</td>
					<td id="txt5" bgcolor="#c1c1c1" style="position:relative;">
						<html:select property="forma_pago_seg" styleId="forma_pago_seg" styleClass="fto" value="">
							<html:option value="0"><bean:message key="seleccion.forma.pago"/></html:option>
							<html:optionsCollection property="listaFormasPago" name="seleccionPagoForm" label="descripcion" value="id"/>
						</html:select>
						<div id="crb_input" style="display:none; float:left;">&nbsp;<label>N� Doc Isapre</label><html:text property="n_isapre" styleId="n_isapre" size="15" styleClass="fto" maxlength="14" onkeypress="return validar_numerico(event);" ></html:text> </div>
					</td>
				</tr>
			</table>
			<div id="blanket" style="display:none;"></div>
			<div id="documentos" style="display: none" align="center">
				<table width="200"  cellspacing="0"> 
					<tr>
						<td colspan="1" bgcolor="#373737" id="txt4"><bean:message key="seleccion.documento"/></td>
						<td colspan="1" bgcolor="#373737" id="txt4">
							<a href="#" onclick="cierra_ventana('documentos')"> 
							<img src="images/cancel.png" width="10" height="10" border="0"> 
						</a>
						</td>
					</tr>
				</table>
				<div id="prueba"></div>
				<table width="200" cellspacing="0"> 
				    <logic:equal value="true" property="solo_recaudacion" name="seleccionPagoForm">
					<tr>
						<td id="txt5" bgcolor="#c1c1c1" align="left"><input type="radio"
							name="radio" id="radio" value="radio3"   onclick="seleccion_documento('G')"/> <bean:message key="seleccion.recaudacion"/></td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="true" property="solo_recaudacion" name="seleccionPagoForm">
						<logic:notEqual value="true" property="solo_guia" name="seleccionPagoForm">
							<tr>
								<td id="txt5" bgcolor="#c1c1c1" align="left"><input type="radio"
							name="radio" id="radio" value="radio"   onclick="seleccion_documento('B')"/> <bean:message key="seleccion.boleta"/></td>
							</tr>
						</logic:notEqual>
						<logic:notEqual value="true" property="solo_boleta"    name="seleccionPagoForm">
							<tr>
								<td id="txt5" bgcolor="#c1c1c1" align="left">
									<logic:equal value="PEDIDO" property="origen" name="seleccionPagoForm">
									<input type="radio"	name="radio" id="radio2" value="radio" onclick="seleccion_documento('G')"/> <bean:message key="seleccion.guia.despacho"/>
									</logic:equal>
								</td>
							</tr>
						</logic:notEqual>
					</logic:notEqual>
						<tr>
						<td id="txt5" bgcolor="#c1c1c1" align="right">
							<a href="#" onclick="numeroDocumento()" id="enviar"> 
								<img src="images/checkprint.png" width="15" height="15" border="0" title="Confirmar Impresi�n" />
						</a>
						</td>
					</tr>
				</table>
			</div>
			<div id="Documento_cambio_folio" style="display: none" align="center">
				<table width="200" cellspacing="1">
					<tr>
						<td colspan="3" align="center" bgcolor="#373737" id="txt4"><bean:message key="seleccion.documento.cambio.folio"/></td>
					</tr>
					<tr>
						<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.numero"/></td>
						<td id="txt5" bgcolor="#c1c1c1">
							<html:text property="numero_boleta_conf" styleId="numero_1_cambio" value="" size="10"  styleClass="fto" onkeypress="return validar_numerico(event)"/>
						</td>
					</tr>																																																																																																																																																																																																																																																																																																																							
					<tr>
						<td id="txt5" bgcolor="#c1c1c1" colspan="2" align="right">
							<a href="#" onclick="javascript: confirmaCambioFolio()"> <img
								src="images/check.png" width="15" height="15" border="0">
						</a></td>
					</tr>
				</table>
			</div>
			<div id="Numero_Documento" style="display: none">
				<table width="200" cellspacing="1">
					<tr>
						<td colspan="3" align="center" bgcolor="#373737" id="txt4"><bean:message key="seleccion.numero.documento"/></td>
					</tr>
					<tr>
						<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.numero"/></td>
						<td id="txt5" bgcolor="#c1c1c1">
							<html:text property="numero_boleta" styleId="numero_1" value="" size="10"  styleClass="fto" onkeypress="return validar_numerico(event)"/>
						</td>
					</tr>
					<tr>
						<td id="txt5" bgcolor="#c1c1c1"><bean:message key="seleccion.numero"/></td>
						<td id="txt5" bgcolor="#c1c1c1">
							<html:text property="numero_boleta_conf" styleId="numero_2" value="" size="10"  styleClass="fto" onkeypress="return validar_numerico(event)"/>
						</td>
					</tr>																																																																																																																																																																																																																																																																																																																							
					<tr>
						<td id="txt5" bgcolor="#c1c1c1" colspan="2" align="right"><a href="#"
							onclick="javascript: confirmaNumeroDocumento()"> <img
								src="images/check.png" width="15" height="15" border="0">
						</a></td>
					</tr>
				</table>
			</div>
			<div id="Confirmacion" style="display: none">
				<table width="200" cellspacing="1">
					<tr>
						<td colspan="2" align="center" bgcolor="#373737" id="txt4"><p id="msj_impresion"></p></td>
					</tr>
					<tr>
						<td id="txt5" bgcolor="#c1c1c1" align="center">
							<a class ="ticket" data-value = "S" href="javascript:void(0)"> 
						    <img src="images/check.png" width="15" height="15"
								border="0"> </a></td>
						<td id="txt5" bgcolor="#c1c1c1" align="center">
							<a class ="ticket" data-value = "N" href="#"> <img src="images/cancel.png" width="15"
						height="15" border="0"> </a></td>
					</tr>
					
				</table>
			</div>
	</html:form>
	<script>					 	
			 	var convenio = $j.trim($j('#convenio',window.parent.document).val());
			 	var tipoconvenio = $j.trim($j('#isapre',window.parent.document).val());
			 	var diferencia = $j.trim($j("#diferencia").val());
			 	var paso_grp = 0;
			 	var cont = 0;
			 	var dent = 0;
			 	
			 	//alert("CMRO convenio = "+convenio);
			 	//alert("CMRO tipoconvenio = "+tipoconvenio);
			 	//alert("CMRO origen = "+document.getElementById('origen').value);
				
				//verificarFormasDePago();
			 	
			 	var vFamilia ="";
				$j("#contenido tr",window.parent.document).each(function(a){
		      	    vFamilia  = $j(this).find("td > a").attr("data-grupfam");				      								
				});
			 	
			 	//GROUPON 
			 	$j("#detalle_pagos #dpago").each(function(){
			 		if($j.trim($j(this).val()) == "GRPON" || $j.trim($j(this).val()) == "ISAPR" ){
			 			paso_grp = 1;
			 		}
			 	});
			 	
			 	//GARANTIAS 20170811
			 	$j("#contenido tr",window.parent.document).each(function(a){
			 		var familia  = $j(this).find("td > a").attr("data-grupfam");
			 		
			 		if(familia == "DES"){
			 			//alert("CMRO 1");
					 	$j("#forma_pago option").each(function(i){
							  	 if($j(this).val() == "GAR" || $j(this).val() =="ISAPR" || $j(this).val() =="EXCED" || $j(this).val() =="CRB"  ){						  		   
						  			$j(this).attr("disabled","disabled");
						  		 }					  		   
							 
						});
					}
			 	});
			
			    if(paso_grp != 1 ){	    
				 	if($j.cookie('convenio') != "sdg"){
				 	
				 		//GROUPON
				 		//LMARIN SE EXTRAE EL CONVENIO 50471 DE DTOS CON FORMA DE PAGO J&J 
					 	if(convenio =="50368" || convenio =="50369"  || convenio =="50472" || convenio =="50473" || convenio =="50474" ){  
							  $j("#forma_pago option").each(function(i){
							  		if($j(this).val() !="GRPON" && $j(this).val() !="0"){
							  		    tmp ="paso";
							  			$j(this).attr("disabled","disabled");
							  		}
							  });
							  $j.cookie('convenio','sdg');
						 }else{
						 	borra_grpn();
						 }
						 
						 if(convenio =="51001" || convenio =="51002"){  
							  $j("#forma_pago option").each(function(i){
							  		if($j(this).val() !="OA" && $j(this).val() !="0"){
							  		    tmp ="paso";
							  			$j(this).attr("disabled","disabled");
							  		}
							  });
							  $j.cookie('convenio','sdg');
						 }
						 
					
						 if(convenio =="50472"){
							$j("#sumaPagar").val("80000").attr("readonly",true);
						 }
						 if(convenio =="50473"){
							$j("#sumaPagar").val("120000").attr("readonly",true);
						 }
						 if(convenio =="50474"){
							$j("#sumaPagar").val("160000").attr("readonly",true);
						 }
						 if(convenio =="50368"){
							$j("#sumaPagar").val("30000").attr("readonly",true);
						 }
						 if(convenio =="50369"){
							$j("#sumaPagar").val("15000").attr("readonly",true);
						 }
						 if(convenio =="51001"){
							$j("#sumaParcial").val("41650").attr("readonly",true);
							$j("#sumaPagar").val("41650").attr("readonly",true);
							$j("#diferencia").val("41650").attr("readonly",true);
							$j("#v_total").val("41650").attr("readonly",true);
							$j("#diferencia_total").val("41650").attr("readonly",true);
						 }
						 if(convenio =="51002"){
							//$j("#sumaPagar").val("83300").attr("readonly",true);
							$j("#sumaParcial").val("83300").attr("readonly",true);
							$j("#sumaPagar").val("83300").attr("readonly",true);
							$j("#diferencia").val("83300").attr("readonly",true);
							$j("#v_total").val("83300").attr("readonly",true);
							$j("#diferencia_total").val("83300").attr("readonly",true);
						 }
						 
						 // CRUZ BLANCA
						 // 20141203 - SE MODIFICA A PETICION DE PAULO BARRERA.
						 
						 var familia ="";
						 $j("#contenido tr",window.parent.document).each(function(a){
			      	    	familia  = $j(this).find("td > a").attr("data-grupfam");				      								
						 });
						 
						 if($j("#tiene_doc").val()=="false"){
							 //alert("CMRO 2");
						 		 if(tipoconvenio =="S"){
						 			//alert("CMRO 3");
						 		 
						 			 if(familia !="DES"){
						 				//alert("CMRO 4");
						 			 
						 		 	 	 $j("#crb_input").css("display","block");
						 			 }
						 			 
									 $j("#forma_pago option").each(function(i){
									  		if($j(this).val() !="ISAPR" && $j(this).val() !="EXCED" && $j(this).val() !="CRB" && $j(this).val() !="0"){
									  			
									  			//alert("CMRO 5");
									  			$j(this).attr("disabled","disabled");
									  		}
									 });
									 $j.cookie('convenio','sdg');
								  }else{
								  	 $j("#forma_pago option").each(function(i){
									  		
									  			if(familia == "DES"){
									  				//alert("CMRO 6");
									  			
									  				if ($j(this).val()!="GAR" && $j(this).val()!="0"){
									  					
									  					//alert("CMRO 7");
									  					$j(this).attr("disabled","disabled");
									  				}
									  			}
									  			else{
									  				/* COMENTE EL 14/11/2019
									  				if(null!=tipoconvenio && "" != trim(tipoconvenio)){
									  					alert("CMRO 8");
									  					if(($j(this).val() !="ISAPR" || $j(this).val() !="EXCED" || $j(this).val() !="CRB") && $j(this).val()!="0" ){
								  							$j(this).attr("disabled","disabled");
								  		 				}
								  	 
									  				}
									  				*/
									  				//alert("CMRO en el else tipoconvenio = "+tipoconvenio);
									  				if(null!=tipoconvenio && "" != trim(tipoconvenio)){
									  					//alert("CMRO 8");
									  					if(($j(this).val() !="OA") && ($j(this).val() !="OASD") && ($j(this).val()!="0")){
									  						//alert("CMRO if OA");
								  							$j(this).attr("disabled","disabled");
								  		 				}else{
								  		 					//alert("CMRO else de OA");
								  		 					$j(this).removeAttr("disabled","disabled");
								  		 				}
								  	 
									  				}
									  			}
									  });
								  }
						 }else{
						 		borra_crb();		 		
						 		
						 }
					 	 
					}
				}else{
					borra_grpn();
				}
				
				function borra_grpn(){
					$j("#forma_pago option").each(function(i){
						  		if($j(this).val() =="GRPON"){					  		    
						  			$j(this).remove();
						  		}
				    });
				}
				
				
				//valida doc convenio cruz blanca
			 	if($j("#tiene_doc").val()=="false"){
			 		 if(tipoconvenio =="S"){
			 			 //alert("CMRO tipoconvenio == S");
			 			  if(familia !="DES"){
			 		 	  		$j("#crb_input").css("display","block");
			 			  }
						  $j("#forma_pago option").each(function(i){
						  		if($j(this).val() !="ISAPR" && $j(this).val() !="EXCED" && $j(this).val() !="CRB" && $j(this).val() !="0"){						  		   
						  			$j(this).attr("disabled","disabled");
						  		}
						  });
					  }else{
						  //alert("CMRO else");
					  	  $j("#forma_pago option").each(function(i){
						  		if($j(this).val() =="ISAPR"  || $j(this).val() =="EXCED" || $j(this).val() =="CRB" ){						  		   
						  			$j(this).attr("disabled","disabled");
						  		}
						  });
					  }
			 	}else{
			 		borra_crb();		 		
			 		
			 	}
				
				
				//GROUPON 
				function borra_crb(){
					$j.cookie("crb","crb_2");
					var alb = $j("#origen").val();
					
					 var familia = "";
			 		 $j("#contenido tr",window.parent.document).each(function(a){
				      	   familia  = $j(this).find("td > a").attr("data-grupfam");				      					    				          
				     });
			 		 
			 			
					if(alb.toLowerCase().indexOf("albaran") < 0){
						$j("#forma_pago option").each(function(i){
							if($j(this).val() =="ISAPR"  || $j(this).val() =="EXCED"){	
									
									//MEJORA ISAPRE + VENTA SEGURO
								 	if(familia!="DES"){
					  					$j(this).remove();
								 	}else{
								 		$j(this).removeAttr("disabled","disabled");
								 	}														
					  		}else{
					  			$j(this).attr("enabled","enabled");
					  		}
							
					    });
					}
				}
				
				$j("#reimprimir").on('click',function(){
					reimprimirBoleta(); 
				});
				$j("#detalle_pagos #dpago").each(function(){
			 		cont++;
			 	});
				
			 	if(cont == "0"){
			 		$j("#reimprimir").css("display","none");
			 	}
			 	//agrego clase para mostrar siempre la ultima forma de pago
			 	$j("#detalle_pagos #el").each(function(i){
			 		if((cont-1) != i){
			 			$j(this).css("display","none");
			 		}
			 	});	
			 	
			 	/*if($j.cookie('crb') == "crb_2"){		 	
		 		  if($j("#tiene_doc").val()=="true"){
		 			  if(familia != "DES"){
			 			$j("#n_isapre").attr("readonly",true);
		 			  }
			 	  }
			 	}*/	
			 	
			 	//valido ingreso de voucher  no vacio 
			 	$j("#gurdar").on("click",function(){		//Inicio Funci�n Guardar
			 		/*
			 		if(tieneConvenioGuiaPorFacturar() && ($j("#diferencia").val() != "0")){ //Tiene Convenio Guia Por Facturar
			 			
			 				 alert("Este encargo es una Gu�a Por Facturar, por lo que debe cancelar el 100% del Encargo");
			 		
			 		}else{ */       // No tiene Conv Guia por Facturar o diferencia <> 0
			 			
			 		
				 		 var familia ="";
						 $j("#contenido tr",window.parent.document).each(function(a){
			      	    	familia  = $j(this).find("td > a").attr("data-grupfam");				      								
						 });
				 		
				 		 if($j("#tiene_doc").val() == "false" && $j("#forma_pago").val() =="GAR" ){
						 		if($j("#forma_pago_seg").val() !="0"){
									 		var tmpcrb = 1;
									 		
									    	$j("#detalle_pagos #dpago").each(function(){
								    			 if($j(this).val()=="ISAPR" || $j(this).val() == "EXCED"){
								    				 tmpcrb++;			 	
								    			 }		 					
								 			});
								 			
									 		
									 		if(tipoconvenio =="S" && cont == "0" && familia !="DES"){//&& cont == "0"){
									 			if($j.trim($j("#n_isapre").val()) == ""){
									 				alert("Debes ingresar el N� Doc Isapre para guardar pago.");
									 			}else{
									 				 if(tmpcrb >= 2 ){
									 				 	$j.cookie('imp_guia','7');
									 				 	$j.cookie('preg','1');
									 				 	setImprimioGuia(); //NEW
									 				 	generaBoleta(); 		 									   		
												   	 }else{
												   	 	guarda_Pago();
												   	 	$j.cookie('preg','7');					   	 	
												   	 }				   	
									 			}
									 		}else{
									 			guarda_Pago();
									 		}	
						 		}else{
						 	
					 				alert("Debes agregar el detalle de la forma de Pago Seguro.");
					 					
						 		}
				 		}else{
				 			//alert("CMRO en el else del onclick del Guardar");
					 		if(esVentaDeSeguro() && (!tienePagosPorImprimir())){	
					 			
					 		    //alert("CMRO en guardar es SEG y primer pago");
					 			guarda_Pago();
					 			//numeroDocumento(); //CMRO importante: esto normalmente se invoca al seleccionar el documento a imprimir
					 		
					 		}else{
					 			//alert("CMRO en onclick de Guardar else de VentaDeSeguro");
						 			var tmpcrb = 1;
						 		
							 		
							    	$j("#detalle_pagos #dpago").each(function(){
						    			 if($j(this).val()=="ISAPR" || $j(this).val() == "EXCED"){
						    				 tmpcrb++;			 	
						    			 }		 					
						 			});
						 			
							 		
							 		if(tipoconvenio =="S" && cont == "0"){
							 			if($j.trim($j("#n_isapre").val()) == ""){
							 				alert("Debes ingresar el N� Doc Isapre para guardar pago.");
							 			}else{
							 				 if(tmpcrb >= 2 ){
							 				 	$j.cookie('imp_guia','7');
							 				 	$j.cookie('preg','1');
							 				 	setImprimioGuia();
							 				 	generaBoleta(); 		 									   		
										   	 }else{
										   	 	guarda_Pago();
										   	 	$j.cookie('preg','7');					   	 	
										   	 }				   	
							 			}
							 		}else{
							 			guarda_Pago();
							 		}	
					 		}
				 		}
				 	/*}*/
					 
			 	});	//END del onclick sobre el enlace guardar
			 	
			 	var tipod = $j("#tipo_doc").val();
			 	
			    if(tipod == "B"){
			    	$j("#msj_impresion").text("Deseas imprimir ticket de Cambio?");
			    }else{
			    	$j("#msj_impresion").text("�Impresi�n Correcta?");
			    }
			    
			    //LMARIN 
			    
			    $j(".ticket").on('click',function(){
			    	//CMRO var archTicket = "\\\\D:\\TICKET_CAMBIO\\2_0047433.pdf";
			    	//var archTicket("file:///D:/TICKET_CAMBIO/2_0047434.pdf");
			    	var tipodoc = $j("#tipo_doc").val();
			    	$j.cookie("estado_boleta","");
			    	if(tipodoc =="B"){
			    			returnVal = "Pago_Exitoso";
			    			window.parent.hidePopWin(true);	
			    			$j(".pantalla2",window.parent.document).css("display","block");
			    				 //CMRO EVALUAR TICKET DE CAMBIO   				
					    		/*if($j(this).attr("data-value") == "S"){
					  				$j.ajax({
									  	type: "POST",
									  	url: 'VentaPedido.do?method=imprime_ticket_cambio',
									  	dataType: "text",
									  	data:"imprime=1",
									  	asycn:false,
									 	success: function(data){
											switch(data){
												case "-1":
													//alert("CMRO imprime ticket resp = -1");
													returnVal = "Pago_Exitoso";
													 window.parent.hidePopWin(true);
													//console.log("no se pudo generar el archivo");
												break;
												case "1":
													alert("CMRO imprime ticket resp = 1");
													returnVal = "Pago_Exitoso";
													window.parent.hidePopWin(true);
													//console.log("no se elimino el archivo");
												break;
												case "2":*/
													//CMRO
													/*
													alert("CMRO imprime ticket resp = 2");
													var urlTicket = archTicket;
													alert("CMRO urlTicket = "+urlTicket);
					  								window.open(urlTicket);
					  								*/
													//CMRO
													//returnVal = "Pago_Exitoso";
													//window.parent.hidePopWin(true);
													//console.log("se elimino el archivo");
												/*break;
												default:
													returnVal = "Pago_Exitoso";
													window.parent.hidePopWin(true);
													//alert("Capa 8.");
												break;
											}
											
										}													  												  					 	  
						 			});	
				    		}else{
				    			returnVal = "Pago_Exitoso";
				    			window.parent.hidePopWin(true);
				    		}*/
				    		//CMRO END EVALUAR TICKET DE CAMBIO
				    }else{    				    				  
					     if($j(this).attr("data-value") == "S"){
					        returnVal = "Pago_Exitoso";
					     	window.parent.hidePopWin(true);	
							//alert("CMRO ticket en el else");
			    			$j(".pantalla2",window.parent.document).css("display","block");
					     	
					     }else{
					    	//CMRO
					    	alert("solo_guia = "+document.getElementById('solo_guia').value);
					    	alert("solo_boleta = "+document.getElementById('solo_boleta').value);
					    	//CMRO
					     
					     	popup('Confirmacion' , ancho, alto);
							popup('documentos' , ancho, alto);
					     }	
				    }
			    	
			    });
			    		   
			    //Modificaci�n ISAPR		    		    
			    
			    if(tipoconvenio =="S"){			    
			    		if($j.cookie('preg') == '7'){
							   	 	if(confirm("Deseas imprimir Gu�a de despacho ?") == true){
							   	 		//alert("CMRO aqui antes de generaBoleta");
							   	 		$j.cookie('imp_guia','7');
							   	 		setImprimioGuia();
					 					generaBoleta();			 								       		    		 							
							   		}else{				
							   			guarda_Pago();
							   			 $j("#forma_pago option").each(function(i){
										  		if($j(this).val() !="ISAPR" && $j(this).val() !="EXCED" && $j(this).val() !="0"){						  		   
										  			$j(this).attr("disabled","disabled");
										  		}
										 });
							   		} 	
							   		$j.cookie('preg','');
					   	}
			    				    				    			    	
			   	    			 							
						if($j.cookie('imp_guia') == '7'){
				 					 				//****CMRO Si imprime guia se habilitan opciones distintas a ISAPRE	 								   			 				
							    borra_crb();
										
							    $j("#forma_pago option").each(function(i){
							  			if($j(this).val() !="ISAPR" && $j(this).val() !="EXCED" && $j(this).val() !="CRB" && $j(this).val() !="0"){						  		   
								  			$j(this).removeAttr("disabled");
							 	}
							});
												
				 		} 
						
						//CMRO Aqui muestra ventana de impresi�n de doc si la diferencia es = 0
			 			if($j("#diferencia").val() == "0"){
							$j("#imprimir").css("display","block");
						}else{
							
							if(familia=="S"){
								$j("#imprimir").css("display","none");
							}
						}						
							 		    																																				 	
			 	}		   		   	
			 	
			 	//FUERZO PRIMER PAGO VENTA SEGURO
			 	//COMPRUEBO SI HAY VENTA DE SEGURO
				 $j("#contenido tr",window.parent.document).each(function(a){
		      	   var familia  = $j(this).find("td > a").attr("data-grupfam");
		      	   var articulo = $j(this).find("td > a").attr("data-barra");
		      	   var precio = $j.trim($j(this).find("td > a").attr("data-precio"));
		           if(familia == "DES"){
		           		//hab1();
		           		if($j("#tiene_doc").val() == "false"){
	       					$j("#sumaPagar").val(parseInt(precio)).attr("readonly",true);
	       					
		           		}
						
		       	   }
			     });
			 	
				 //console.log("tipo_pedido ==>"+$j("#tipo_pedido",window.parent.document).val());
				 /*CMRO old1 if($j("#tipo_pedido",window.parent.document).val() == 'SEG'){
					 					 
					//CMRO
					//alert("CMRO en tipo_pedido == SEG");
					//CMRO
					 
				 	var valor = parseInt($j("#anticipo_def").val());
					
				 	alert("CMRO valor");
					
					if($j("#tiene_doc").val() == "false"){
						$j("#sumaPagar").val(valor).attr("readonly",true);
						
						$j("#forma_pago option").each(function(i){
							if( ($j(this).val() == "RSIN") || ($j(this).val() == 0)){
								
								alert("CMRO es RSIN");
								$j(this).removeAttr('disabled','disabled');
							}else{
								$j(this).attr('disabled','disabled');
							}
						}); end old1 CMRO*/
						
						/* CMRO old2
						$j("#forma_pago option").each(function(i){
						  	 if($j(this).val() != "RSIN"){					  		   
						  		$j(this).remove();
						  	 }
						  	
						  
						
						  	 
					    });
						end old2 */
	           	/*CMRO old3	}
				 } end old3 CMRO */
				 
				 
				 //FORMA DE PAGO VENTA PERSONAL
				 var local_n = $j.trim($j("#local",window.parent.document).val());
				 $j("#forma_pago").on("change",function(){
					  if($j(this).val() =="8"){
						 if(local_n !="T100"){
							  if(parseInt($j.trim($j("#sumaPagar").val())) > 0){
						      	showPopWin('<%=request.getContextPath()%>/VentaPedido.do?method=abre_valida_usuario_vp', 300, 130,null,false);
						  	  }else{
						  		  alert("EL monto ingresado a pagar deber mayor a 0.");
						  		  $j("#forma_pago").val("0");
						  	  }
						 }
					  }else if($j(this).val() =="ANULA"){
					      
						  if($j("#tiene_doc").val() == "false"){							    
								  if($j.trim($j("#origen").val())!="ALBARAN_DEVOLUCION"){
									  var tipopago = $j.trim($j('#tipo_pedido',window.parent.document).val());
									  var motivo = $j("#tipo_pedido",window.parent.document).val();
									  //var motivo = $j("#motivo",window.parent.document).val();
									  if(tipopago =="0"){
										  alert("No se puede cobrar con la forma de pago Anula Cambio sin seleccionar un motivo.");
										  $j(this).val("0");
									  }else{
												  $j.ajax({
													  	type: "POST",
													  	url: 'SeleccionPago.do?method=exige_validacion_fpago',
													  	dataType: "text",
													  	data:"motivo="+motivo+"&fpago="+$j(this).val(),
													  	asycn:false,
													 	success: function(data){												 		
													 	    var res = parseInt(data); 
															switch(res){
																case 1:																
															      	showPopWin('<%=request.getContextPath()%>/SeleccionPago.do?method=abre_valida_encargo_mf', 300, 130,null,false);																
																break;
																default:
																	alert("El motivo no aplica para cobro con forma de pago Anula Cambio.");
																  	$j("#forma_pago").val("0");
																break;
															}
															
														}													  												  					 	  
										 			});
									  	}
							  	}
						  }	  
				  	  }else if($j(this).val() =="ISAPR"){
				  			$j("#crb_input").css("display","block");
				  	  }
				 });
				 //SEGUNDA FORMA DE PAGO GARANTIA
				 $j("#forma_pago").on("change",function(){			 		 					 
			 		 if($j(this).val() == "GAR"){					  		   
				  		$j("#fpago_seg").css("display","block");
				  		$j("#forma_pago_seg option").each(function(i){
						  	 if($j(this).val() == "GAR" || $j(this).val() =="CRB" || $j(this).val() =="EXCED" || $j(this).val() =="ANULA" || $j(this).val() =="ISAPR" ){					  		   
						  		$j(this).attr("disabled","disabled");
						  	 }
						  	 
					    });
				  	 }else{
				  		$j("#fpago_seg").css("display","none");	
				  	 }
			 	 	
		 		 });
				 
				 //QUITO FORMA DE PAGO GARANTIA CUANDO NO ES PRIMERA FORMA DE PAGO
				 if(tipoconvenio !="S"){	
					//alert("CMRO pasa por aqui");
					var familia ="";
					$j("#contenido tr",window.parent.document).each(function(a){
			      	    familia  = $j(this).find("td > a").attr("data-grupfam");				      								
					});
					
				 	if($j("#tiene_doc").val()=="true"){
					 	$j("#forma_pago option").each(function(i){				 
						 	if($j(this).val() == "GAR" || $j(this).val() == "ISAPR" || $j(this).val() == "CRB" || $j(this).val() == "EXCED"){					  		   
						  		$j(this).attr('disabled','disabled');
					  		 }
						 });
				 	}else{
				 		
				 		//alert("CMRO paso por este else");
				 		
				 		if(familia=="DES"){
				 			$j("#forma_pago option").each(function(i){				 
							 	if( familia == "DES" && $j(this).val() == "GAR"){					  		   
							  		$j(this).removeAttr('disabled','disabled');
						  		 }
							 });
				 		}
				 	}
				 }else{
					 if($j("#tiene_doc").val()=="true"){
						 
						 //alert("CMRO tieneDoc");
						 
						 	/*$j("#forma_pago option").each(function(i){				 
							 	
							 
						 		if( ($j(this).val() != "CRB") && ($j(this).val() != "ISAPR") && ($j(this).val() != "EXCED") && ($j(this).val() != "GAR") &&  ($j(this).val() != "OA") && ( $j(this).val() != "OASD") && ($j(this).val() != 0)){
						 		
						 			//alert("CMRO aqui activo boletas");
						 		
					 				$j(this).removeAttr('disabled','disabled');
					 			}else{
						 		
						 			//alert("CMRO aqui deshab");
						 			$j(this).attr('disabled','disabled');
						 		}
						 	 });*/
						 	 deshabilitarOpcsDistintasA("BOLETAS");
					 }else{
						 
						 //alert("CMRO NoTieneDoc");
						 
						 $j("#forma_pago option").each(function(i){			
							 
						 		//alert("CMRO aqui");
							 	if(($j(this).val() == "GAR") && (vFamilia == "DES")){	
							 		
							 		//alert("CMRO aqui 2");
							  		$j(this).removeAttr('disabled','disabled');
						  		 }
							 	else {
							 		
							 		//alert("CMRO 9");
							 		if(($j(this).val() == "ISAPR") || ($j(this).val() == "EXCED") || ($j(this).val() == "CRB") || ($j(this).val() == "0")){					  		   
								  		$j(this).removeAttr('disabled','disabled');
							  		 }
							 		else{
							 			$j(this).attr('disabled','disabled');
							 		}
							 	}
						 });
					 }
				 }
			 	
				 /*******************************************************************************************************/
					/* OBJETIVO: CORREGIR COMPORTAMIENTO DEL SISTEMA AL APLICAR EL SEGURO DE GARANTIA                      */
					/* ACTUALIZACION: DICIEMBRE 2019                                                                       */
					/*******************************************************************************************************/
					
					function mostrarFP_Recaudacion(){
						
						//alert("CMRO en mostrarFP_Recaudacion");
						
						$j("#forma_pago option").each(function(i){
							if(($j(this).val() == "RSIN") || ($j(this).val() == 0) ){
								//alert("CMRO habilito RSIN");
								$j(this).removeAttr('disabled','disabled');
							}else{
								
								//alert("CMRO deshab RSIN");
								$j(this).attr('disabled','disabled');
							}
						});
					}
				 
					function validarAplicacionGarantia(){
						
						//alert("CMRO en validarAplicacionGarantia");
						
						var vTipoPedido = $j("#tipo_pedido",window.parent.document).val();
						
						if( vTipoPedido == "SEG"){
							 	var valor = parseInt($j("#anticipo_def").val());
							
						 		validarPagoGarantia($j("#sumaParcial").val(), $j("#sumaPagar").val());
							
								if(!tienePagosPorImprimir()){
									$j("#sumaPagar").val(valor).attr("readonly",true);
								}else{
									$j("#sumaPagar").val(valor).attr("readonly",false);
								}
						}
					}
				 
				/************************************************************************************************************************** 
				 * FUNCION:    deshabilitarOpcsDistintasA
				 * PARAMETROS: vTipo
				 * OBJETIVO:   deshabilitar las opciones del select de Formas de Pagos DISTINTAS a la opci�n que se pasa como par�metro
				 **************************************************************************************************************************/
					 
				 function deshabilitarOpcsDistintasA(vTipo){
					 
				 	if (null == vTipo) vTipo = "";
						
					//alert("en deshabilitarFormasDePago");
					//alert("CMRO vTipo = "+vTipo);
					 	
						$j("#forma_pago option").each(function(i){				 							    
							
								if(vTipo == "ISAPRES"){
									if(($j(this).val() != "ISAPR") && ($j(this).val() != "EXCED") && ($j(this).val() != "CRB") && ($j(this).val() != 0)){
										$j(this).attr('disabled','disabled');
						 			}
								}else{
									if(vTipo == "CONV"){
							 			if(($j(this).val() != "OA") && ( $j(this).val() != "OASD") && ($j(this).val() != 0)){
							 				$j(this).attr('disabled','disabled');
							 			}
									}else{
										if(vTipo == "BOLETAS"){
											
											//alert("CMRO vTipo  BOL");
											
											//
											if( ($j(this).val() != "CRB") && ($j(this).val() != "ISAPR") && ($j(this).val() != "EXCED") && ($j(this).val() != "GAR") &&  ($j(this).val() != "OA") && ( $j(this).val() != "OASD") && ($j(this).val() != 0)){
										 		$j(this).removeAttr('disabled','disabled');
										 		//alert("CMRO If ");
								 			}else{
								 				
								 				//alert("CMRO else");
									 			$j(this).attr('disabled','disabled');
									 		}
											//
							 			}
							 		}
								}
							
						 });
				 }
					 
				 			
				
				/*********************************** NUEVAS FUNCIONES **************************************************/
				/* OBJETIVO: MEJORAR LA FUNCIONALIDAD DE HABILITAR Y DESHABILITAR FORMAS DE PAGO                       */
				/* ACTUALIZACION: NOVIEMBRE 2019                                                                       */
				/*******************************************************************************************************/
				
				 function setImprimioGuia(){
					
					 	//alert("CMRO setImprimioGuia");
					 
						// anterior $j("#imprimio_guia").val("2");
						if (tienePagos()){
							
							if (tieneGarantia()){
								$j("#imprimio_guia").val("1");
							}else{
								$j("#imprimio_guia").val("2");
							}
						}
				}
				
					
				
				 function validarImprimioRecibo(){
					 var resp = false;
				 
					 if ($j('#imprimio_recibo').val() == 1) {resp = true};
					 
					 return resp;
				 }
				 
				 function setImprimioRecibo(){
					 $j("#imprimio_recibo").val("1");
				 }
				 
					function validarImprimioGuia(){
						/*
						alert("CMRO validarImprimioGuia");
						alert("CMRO imprimio_guia = "+$j("#imprimio_guia").val()); 
						alert("CMRO document ="+document.getElementById('imprimio_guia').value);
						*/
						
						//Si el encargo tiene garantia vCant = 2
						//Si el encargo no tiene garantia vCant = 1
						//Esto debe validarse es la clase
						
						var vCant = 0;
						
						if (tieneGarantia()){ vCant = 2;}else{vCant = 1;}
						
						//alert("CMRO vCant = "+vCant);
						
						//1 en imprimio_guia indica que en ese encargo ya se imprimi� la Gu�a de Despacho
						if ($j('#imprimio_guia').val() == vCant){
							//alert("CMRO si imprimio guia");
							return true;
						}else{
							
							//alert("CMRO no imprimioGuia");
							return false;
						}
						
						/*
						var resp = false;
						if ($j('#imprimio_guia').val() == 1) { resp = true;}
						
						return resp;
						*/
					}
				

				function mostrarFP_Boletas(){
					
					//alert("CMRO mostrarFP_Boletas");
					
					$j("#forma_pago option").each(function(i){
						if( ($j(this).val() != "CRB") && ($j(this).val() != "ISAPR") && ($j(this).val() != "EXCED") && ($j(this).val() != "GAR") &&  ($j(this).val() != "OA") && ( $j(this).val() != "OASD") && ($j(this).val() != "RSIN") && ($j(this).val() != 0)){
							$j(this).removeAttr('disabled','disabled');
						}else{
							$j(this).attr('disabled','disabled');
						}
					});
				}
				
				function mostrarFP_Todas(){
					
					//alert("CMRO mostrarFP_Todas");
					
					$j("#forma_pago option").each(function(i){
						$j(this).attr('disabled','disabled');
					});
				}
				

				function mostrarFP_CRB(){
					
					//alert("CMRO mostrarFP_CRB");
					
					var ocupadaCRB = formaPagoOcupada("CRB");
					var ocupadaISAPR = formaPagoOcupada("ISAPR");
					var ocupadaEXCED = formaPagoOcupada("EXCED");
					var ocupadaISAPRES = ocupadaCRB || ocupadaISAPR;
					
					//CMRO
					/*
					alert("CMRO ocupadaCRB = "+ocupadaCRB);
					alert("CMRO ocupadaISAPR = "+ocupadaISAPR);
					alert("CMRO ocupadaEXCED = "+ocupadaEXCED);
					alert("CMRO ocupadaISAPRES = "+ocupadaISAPRES);
					*/
					//CMRO
										

					$j("#forma_pago option").each(function(i){
						if(((!ocupadaISAPRES) && ($j(this).val() == "CRB")) || ((!ocupadaISAPRES) && ($j(this).val() == "ISAPR")) || ((!ocupadaEXCED) && ($j(this).val() == "EXCED")) || ($j(this).val() == 0) ){
							
							//alert("CMRO habilito CRB");
							$j(this).removeAttr('disabled','disabled');
						}else{
							
							//alert("CMRO deshab CRB");
							$j(this).attr('disabled','disabled');
						}
					});
				}

				function mostrarFP_ISAPRE(){
					
					//alert("CMRO mostrarFP_ISAPRE");
					
					$j("#forma_pago option").each(function(i){
						if( ($j(this).val() == "ISAPR") || ($j(this).val() == 0)){
							$j(this).removeAttr('disabled','disabled');
						}else{
							$j(this).attr('disabled','disabled');
						}
					});
				}

				function mostrarFP_CONV_OA(){
					
					//alert("CMRO mostrarFP_CONV_OA");
					
					$j("#forma_pago option").each(function(i){
						if( ($j(this).val() == "OA") || ($j(this).val() == "OASD") || ($j(this).val() == 0)){
							$j(this).removeAttr('disabled','disabled');
						}else{
							$j(this).attr('disabled','disabled');
						}
					});
				}

				function mostrarFP_GAR(){
					
					//alert("CMRO mostrarFP_GAR");
					
					$j("#forma_pago option").each(function(i){
						if( ($j(this).val() == "GAR") || ($j(this).val() == 0)){
							$j(this).removeAttr('disabled','disabled');
						}else{
							$j(this).attr('disabled','disabled');
						}
					});
				}

				function tieneGarantia(){
					
					//alert("CMRO tieneGarantia");
					var existePago = 0;
					
					var resp = false;
					
					$j("#contenido tr",window.parent.document).each(function(a){
						var familia  = $j(this).find("td > a").attr("data-grupfam");
					});
							 		
					if(familia == "DES"){ existePago = 1;}
					
					//NEW Enero 2020
					$j("#detalle_pagos #dpago").each(function(){
						if($j(this).val()=="GAR"){
							existePago++;			 	
						}		 					
					});
					//END NEW
					
					if (existePago >0){ resp = true};
					//CMRO
					//alert("CMRO tieneGarantia, resp = "+resp);
					//CMRO
					
					return resp;
				}

				//Convenio con Factura: son convenios que generan Guia de Venta para luego generar una Factura
				function tieneConvenioConFactura(){
					
					//alert("CMRO tieneConvenioConFactura");
					
					var resp = false;
					var conv = $j.trim($j('#convenio',window.parent.document).val());
					var arrConv = ["50412","52","88780","27","28","32","34","40","44","1047","1065","134","144","183","176","198","212","213","803","50297","77777"];
					
					if ((null != conv) && ("" != conv)) {
						//alert("CMRO conv = -"+trim(conv)+"-");
						
						for (var posicion = 0; posicion < arrConv.length; posicion++) {
							   //alert("CMRO posicion = "+posicion);
							   if(trim(conv) == arrConv[posicion]){
								   resp = true;
								   break;
							   }							   
						}						
					}
				
					//alert("CMRO tieneConvenioConFactura resp ="+resp);
					return resp;
				}

				function esConvenioCRB(vConvenio){
					var resultado = false;
					
					if (null!= vConvenio){ resultado = ("50412" == vConvenio)};
					//alert("CMRO esConvenioCRB resultado ="+resultado);
					return resultado;
				}

				function esISAPRE(){
					
					//alert("CMRO esISAPRE");
					
					var resp = false;
					var tipoConv = $j.trim($j('#isapre',window.parent.document).val());
					
					if((null != tipoConv) && ( "S" == tipoConv)){ resp = true;}
					
					return resp;
				}
				
				

									 
				function formaPagoOcupada(vFormaPago){
					
					//alert("CMRO formaPagoOcupada");
					var resp = false;
					var existe = 0;
					
					if (null == vFormaPago) vFormaPago = "";
					
					$j("#detalle_pagos #dpago").each(function(){
						if($j(this).val()== vFormaPago){existe++;}		 					
					});
					
					if(existe >0 ) { resp = true; }
					
					return resp;
				}
								
				function tienePagos(){
					
					var resp = false;
					//alert("CMRO en tienePagos");
					if($j("#tiene_doc").val() == "true") {resp = true;}
				    //alert("CMRO en tienePagos, resp = "+resp); 
					return resp;
				}

				function esEncargo(){
					
					//alert("CMRO esEncargo");
					var resp = false;
					
					if ((null != document.getElementById('origen')) && ("PEDIDO" == document.getElementById('origen').value)){
						resp = true;
					}
					
					return resp;
				}

				function esVentaDeSeguro(){
					//alert("CMRO en esVentaDeSeguro");
					
					var resp = false;
					if($j("#tipo_pedido",window.parent.document).val() == 'SEG'){
						resp = true;
					}
					//alert("CMRO esVentaDeSeguro? "+resp);
					return resp;
				}
				
				function tieneConvenioGuiaPorFacturar(){
					
					//alert("CMRO tieneConvenioGuiaPorFacturar");
					
					var resp = false;
					var conv = $j.trim($j('#convenio',window.parent.document).val());
					
					if ((null != conv) && ("" != conv)){ 
						if("77777" == conv){resp = true;}
					}
					
					//alert("CMRO resp = "+resp);
										
					return resp;
				}
				
				//Convenio Con Boleta: son Convenios que generan s�lo Boleta
				//NEW CMRO Enero 2020
				function tieneConvenioConBoleta(){
					
					//alert("CMRO tieneConvenioConBoleta");
				
					var resp = false;
					var conv = $j.trim($j('#convenio',window.parent.document).val());
					var arrConvB = ["4","56","181","201","792"];
					
					if ((null != conv) && ("" != conv)) {
						//alert("CMRO conv = -"+trim(conv)+"-");
						
						for (var posicion = 0; posicion < arrConvB.length; posicion++) {
							   //alert("CMRO posicion = "+posicion);
							   if(trim(conv) == arrConvB[posicion]){
								   resp = true;
								   break;
							   }
							   
						}						
					}
					
					//CMRO
					//alert("CMRO tieneConvenioConBoleta, resp = "+resp);
					//CMRO
					
					return resp;
					
				}
				
				//Convenio Promoci�n: Es un item que est� en la tabla ConvenioCB y no es Convenio con Factura ni Convenio con Boleta
				//NEW CMRO Enero 2020
				function tieneConvenioPromocion(){
					var resp = false;
					var conv = $j.trim($j('#convenio',window.parent.document).val());
					
					if ((null != conv) && ("" != conv)) {
						
						if ((!tieneConvenioConFactura()) && (!tieneConvenioConBoleta())){
							resp = true;
						}								
					}
					
					return resp;
				}
				
		/* Function Original		
				
				function mostrarFormasDePago(){
					
					//alert("CMRO en mostrarFormasDePago");
									
					//ant del 17012020 if(esEncargo() && (!esVentaDeSeguro())){
				if(esEncargo()){			
						
					if (!esVentaDeSeguro()){
						if (tieneGarantia()){
							if (!tienePagos()){ //es el primer pago
								mostrarFP_GAR();
							}else{
								if(tieneConvenioConFactura()){
									if(tieneConvenioGuiaPorFacturar()){
										
										mostrarFP_Boleta();
									}else{
											if (esISAPRE()){
												if (esConvenioCRB(convenio)){
													if (!validarImprimioGuia()){
														
													//CMRO
													alert("CMRO aqui1");
													//CMRO
														mostrarFP_CRB();
													}else{
														
														//CMRO
														alert("CMRO aqui2");
														//CMRO
														mostrarFP_Boletas();
													}
												}else{
													if(!validarImprimioGuia()){
														mostrarFP_ISAPRE();
													}else{
														mostrarFP_Boletas();
													}
												}
											}else{
												if(tieneConvenioConBoleta()){ //NEW Enero 2020
													
													//CMRO
													//alert("CMRO alert1");
													//CMRO
													
													if(!tienePagosPorImprimir()){
														
														//CMRO
														//alert("CMRO alert2");
														//CMRO
														mostrarFP_CONV_OA();
													}else{
														mostrarFP_Boletas();
													}
												}else{
													if(!validarImprimioGuia()){
														mostrarFP_CONV_OA();
													}else{
														mostrarFP_Boletas();
													}
												}
											}
								  	}//END DEL ELSE NO TIENE CONVENIO GUIA POR FACTURAR
								}else{ //else de tieneConvenioConFactura
									
										if(tieneConvenioPromocion()){
											mostrarFP_Todas();
										}else{
											mostrarFP_Boletas();
										}
									
								}
							}
						}else{ // No tiene Garantia
							if(tieneConvenioConFactura()){
								
								//alert("CMRO tieneConvenio");
							
								if(tieneConvenioGuiaPorFacturar()){
									
									//alert("CMRO tieneConvenioGuiaPorFacturar");
									mostrarFP_Boletas();
								}else{	
									//CMRO
									//alert("CMRO No tiene Garantia y Si tieneConvenio");
									//CMRO
									if (esISAPRE()){
										
										//CMRO
										//alert("CMRO No tiene Garantia y Si tieneConvenio ISAPRE");
										//CMRO
										if (esConvenioCRB(convenio)){
											
											//CMRO
											//alert("CMRO No tiene Garantia y Si tieneConvenio CRB");
											//CMRO
											if ((!tienePagos()) || (!validarImprimioGuia())){ //es el primer pago o no ha impreso guia
												mostrarFP_CRB();
											}else{
												mostrarFP_Boletas();
											}
										}else{
											if(!tienePagos()){ //es el primer pago
												mostrarFP_ISAPRE();
											}else{
												mostrarFP_Boletas();
											}
										}
									}else{ //Es Convenio distinto a Isapre
										
										//NEW Enero 2020
										if(tieneConvenioConBoleta()){ //NEW Enero 2020
											if(!tienePagosPorImprimir()){
												mostrarFP_CONV_OA();
											}else{
												mostrarFP_Boletas();
											}
										}else{
										//NEW Enero 2020
									
											if(!tienePagos() || (!validarImprimioGuia())){  //es el primer pago
												mostrarFP_CONV_OA();
											}else{
												mostrarFP_Boletas();
											}
										}
									}
									}//END DEL ELSE NO ES CONVENIO GUIA POR FACTURAR
								}else{
									mostrarFP_Boletas();
										
								}
						}//End No tiene garantia
					} //End No es VentaDeSeguro
					else{
					 	if(esVentaDeSeguro()){
					 		if (!tienePagosPorImprimir()){ //Validando Caso de Aplicaci�n de Garant�a
					 			mostrarFP_Recaudacion();
					 		}else{
					 			mostrarFP_Boletas();
					 		}
					 	}else{
					 		mostrarFP_Boletas();
					 	}
					}
				  }//End EsEncargo
				}//End mostrarFormasDePago
				
				End function Original*/ 
				
	function tieneConvenio(){
		
		//alert("CMRO tieneConvenio new");
	
		var resp = false;
		var conv = $j.trim($j('#convenio',window.parent.document).val());
					
		if ((null != conv) && ("" != conv)) {
			resp = true;
		}
	
		//alert("CMRO tieneConvenio new, resp ="+resp);
	
		return resp;
}


function mostrarFormasDePago(){
					
	//alert("CMRO en mostrarFormasDePago2");
									
	if(esEncargo()){
		
		//alert("CMRO en mostrarFormasDePago, es Encargo");
						
		if(!esVentaDeSeguro()){
											 
			if (tieneGarantia()){
				if (!tienePagos()){ //es el primer pago
					mostrarFP_GAR();
				}else{
					if(tieneConvenio()){
						//alert("CMRO en mostrar tieneConvenio");
						if (tieneConvenioConFactura()){
							//***** CONVENIOS CON FACTURA ********
							if(tieneConvenioGuiaPorFacturar()){
								mostrarFP_Boleta();
							}else{
								if (esISAPRE()){
											if (esConvenioCRB(convenio)){
												if (!validarImprimioGuia()){
													
												//CMRO
												//alert("CMRO aqui1");
												//CMRO
													mostrarFP_CRB();
												}else{
													
													//CMRO
													//alert("CMRO aqui2");
													//CMRO
													mostrarFP_Boletas();
												}
											}else{
												if(!validarImprimioGuia()){
													mostrarFP_ISAPRE();
												}else{
													mostrarFP_Boletas();
												}
											}
								}else{ //No es ISAPRE
											if(!validarImprimioGuia()){
										   
												mostrarFP_CONV_OA();
											}else{
												mostrarFP_Boletas();
											}
								}
							}//END DEL ELSE NO TIENE CONVENIO GUIA POR FACTURAR
							
							//***** CONVENIO CON FACTURA
						}else{ //No es ConvenioConFactura
							
						//alert("CMRO en mostrar no es ConvenioConFactura");
							if(tieneConvenioConBoleta()){ //Es ConvenioConBoleta
								
								//alert("CMRO en mostrar es ConvenioConBoleta");
								if(!tienePagosPorImprimir()){
									mostrarFP_CONV_OA();
								}else{
									mostrarFP_Boletas();
								}
							}
							else{ //No es ConvenioConBoleta ni ConFactura => EsPromocion
								
								//alert("CMRO en mostrar SI Garantia y NO es ConvenioConBoleta");
								mostrarFP_Boletas();
							}
						}
						
					}else{//No tiene Convenio
						mostrarFP_Boletas();
					}
				}//tiene garantia y la pago
				}else{ // No tiene Garantia
					if(tieneConvenio()){
								
								//alert("CMRO tieneConvenio");
								
								//****** CONVENIOS CON FACTURA
						if (tieneConvenioConFactura()){
							
								if(tieneConvenioGuiaPorFacturar()){
									
									//alert("CMRO tieneConvenioGuiaPorFacturar");
									mostrarFP_Boletas();
								}else{	
									//CMRO
									//alert("CMRO No tiene Garantia y Si tieneConvenio");
									//CMRO
									if (esISAPRE()){
										
										//CMRO
										//alert("CMRO No tiene Garantia y Si tieneConvenio ISAPRE");
										//CMRO
										if (esConvenioCRB(convenio)){
											
											//CMRO
											//alert("CMRO No tiene Garantia y Si tieneConvenio CRB");
											//CMRO
											if ((!tienePagos()) || (!validarImprimioGuia())){ //es el primer pago o no ha impreso guia
												mostrarFP_CRB();
											}else{
												mostrarFP_Boletas();
											}
										}else{
											if(!tienePagos()){ //es el primer pago
												mostrarFP_ISAPRE();
											}else{
												mostrarFP_Boletas();
											}
										}
									}else{ //Es Convenio distinto a Isapre
										if(!tienePagos()){  //es el primer pago

											mostrarFP_CONV_OA();

										}else{

											mostrarFP_Boletas();
										}
									}
									}//END DEL ELSE NO ES CONVENIO GUIA POR FACTURAR
									
								//**** END CONVENIOS CON FACTURA
						}else{
							//No es ConvenioConFactura
							if(tieneConvenioConBoleta()){ //Es ConvenioConBoleta
								if(!tienePagosPorImprimir()){
									mostrarFP_CONV_OA();
								}else{
									mostrarFP_Boletas();
								}
							}
							else{ //No es ConvenioConBoleta ni ConFactura => EsPromocion
								
								//alert("CMRO en mostrar NO Garantia y NO es ConvenioConBoleta");
								mostrarFP_Boletas();
							}
							
						}
								
						}else{
							mostrarFP_Boletas();
		  
					}
				}//End No tiene garantia
		}else{ //Es Venta de Seguro
					 	
			if (!tienePagosPorImprimir()){ //Validando Caso de Aplicaci�n de Garant�a
				mostrarFP_Recaudacion();
			}else{
				mostrarFP_Boletas();
			}
					 	
		}
  } //End es Encargo
}//End mostrarFormasDePago
				
				
				
				
				
				mostrarFormasDePago();
				validarAplicacionGarantia();
				
				
		</script>
	</body>
	</html:html>
