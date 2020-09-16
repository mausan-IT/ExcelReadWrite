
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

<style type="text/css">
#ad{
		padding-top:220px;
		padding-left:10px;
	}
	
	td.tituloConsulta{
		background-color: #00008B;
	}
</style>

		<link href="css/estilos.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="css/subModal.css" />
		<link rel="stylesheet" type="text/css" href="css/formatosFormularios.css" />
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/subModal.js"></script>
		<script type="text/javascript" src="js/prototype.js"></script>
		<script language="javascript" src="js/utils.js"></script>
		<script language="javascript" src="js/validaciones-graduacion.js"></script>
		<script language="javascript" src="js/mantenedores/cliente_consulta_optometrica.js"></script>
		

<link rel="stylesheet" type="text/css" href="css/jquery.datepick.css"/>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript" src="js/jquery.datepick-es.js"></script>

<title><bean:message key="title.pos"/></title>

<script language="javascript">	
		
		var $j = jQuery.noConflict();

		function cargaCliente(cliente)
		{				
			document.getElementById("clienteagregadoId").value = cliente[0];
			document.getElementById("codigo_cliente").value = cliente[0];				
        	document.getElementById("nifagregadoId").value = cliente[1];
        	document.getElementById("nombre_cliente").value = cliente[2]; 
        	var cerrarPagina = document.getElementById('cerrarPagina').value;
			       	        	      	
        	location.href("<%=request.getContextPath()%>/ConsultaOptometrica.do?method=cargaFormulario&cliente="+cliente[0]+"&nombre="+cliente[2]+"&apellido="+cliente[3]+"&cerrarPagina="+cerrarPagina+"");
        	//document.forms[0].submit();
	
		}

        $j(function() {
			$j('#fechaEmision').datepick();			
		});
		function cerrar_ventanas()
        	{		
        			var estaGrabado = document.getElementById('estaGrabado').value;
        			var pagina = document.getElementById('cerrarPagina').value;
        			
        			
        			
        			if(estaGrabado == 0){
        			
        				var boton = confirm("¿Desea salir de la página actual sin guardar?");
        				
        				
        				
        				if(pagina == 1){
        					
							
							if (boton)
			        		{
								
								//window.parent.hidePopWin(false);
								 window.parent.hidePopWin(true);
							}	
						}else{
							
			        		if (boton)
			        		{	
			        			
			        			 window.parent.hidePopWin(true);
			        			
			        			
			        		}
			        		else
			        		{
			        			
			        			self.close();
			        		}
	        			}   
        				
        			
        			}else{
        				
	        			if(pagina == 1){
	        				
							var boton = confirm("\u00BFEst\u00E1 seguro(a) de cerrar la ventana?");
							if (boton)
			        		{
								
								window.parent.hidePopWin(false);
							}	
						}else{
							
							var boton = confirm("\u00BFEst\u00E1 seguro(a) de cerrar la ventana?");
			        		if (boton)
			        		{	
			        			
			        			parent.carga_url_padre('<%=request.getContextPath()%>/Menu.do?method=CargaMenu');
			        			
			        		}
			        		else
			        		{
			        			
			        			self.close();
			        		}
	        			}        			
        			}					
        		
        	}
		
		function busqueda_cliente()
			{							
				rut  = "";				
				showPopWin("<%=request.getContextPath()%>/BusquedaClientes.do?method=cargaBusquedaClientes&nif="+rut+"", 714, 425, cargaCliente, false);			   	
			   	
			}
			
			
			function buscarMedico()
			{								
				showPopWin("<%=request.getContextPath()%>/BusquedaMedicos.do?method=buscar", 714, 425, cargaMedico, false);
			}
		
			
		
		
		// Ingresar Consulta Optométrica
		function ingresar_consultaOpt(){
			var estaGrabado = document.getElementById('estaGrabado').value;
			
			var cliente = document.getElementById('codigo_cliente').value;
			var nombre_cliente = document.getElementById('nombre_cliente').value;
			var cerrarPagina = document.getElementById('cerrarPagina').value;
			
			if(estaGrabado == 2){
				var boton = confirm("¿Desea salir de la página actual sin guardar?");
				
				if(boton){
					//showPopWin("<%=request.getContextPath()%>/ConsultaOptometrica.do?method=cargaFormulario+"&cerrarPagina="+cerrarPagina+"",790, 435, null, false);
					showPopWin("<%=request.getContextPath()%>/ConsultaOptometrica.do?method=cargaFormulario&cerrarPagina=0",790, 435, null, false);
				}
				
			}else{
				//showPopWin("<%=request.getContextPath()%>/ConsultaOptometrica.do?method=cargaFormulario&cliente="+cliente+"&nombre_cliente="+nombre_cliente+"&cerrarPagina="+cerrarPagina+"",790, 435, null, false);
				showPopWin("<%=request.getContextPath()%>/ConsultaOptometrica.do?method=cargaFormulario&cerrarPagina=0",790, 435, null, false);
			}
		}
		
		// Fin Ingresar Consulta Optométrica
		
		function carga_url_padre_traspaso_vta_presupuesto(url)
        {
	    	parent.carga_url_padre_encargo(url);
        }
        function carga_url_padre_traspaso_vta_presupuesto_padre(url)
        {
	    	location.href(url);
        }	
        function buscarDoctorAjax()
        {        	
        	var nifdoctor = document.getElementById('nifdoctor').value;	
        	if("" != nifdoctor){
        		document.getElementById('nombre_doctorDIV').innerHTML = "";
        		document.getElementById('doctor').value = "";
        		document.getElementById('nifdoctor').value = "";
        		document.getElementById('nifdoctor').value = "Buscando...";
        		new Ajax.Request('<html:rewrite page="/ConsultaOptometrica.do?method=buscarDoctorAjax"/>', {
				      parameters: {nifdoctor: nifdoctor},      
				      onComplete: function(transport, json) {
				      	if("" != json.nifdoctor){
						      	document.getElementById('nifdoctor').value = json.nifdoctor;
						      	document.getElementById('doctor').value = json.codigodoctor;
						      	document.getElementById('dvnifdoctor').value = json.dvnifdoctor;					      	
						      	document.getElementById('nombre_doctorDIV').innerHTML = json.nombredoctor;
				      	}else{
				      		alert("El doctor con rut "+nifdoctor+" no existe");
				      		document.getElementById('nifdoctor').value = "";
					      	document.getElementById('doctor').value = "";
					      	document.getElementById('dvnifdoctor').value = "";					      	
					      	document.getElementById('nombre_doctorDIV').innerHTML = "";
				      	}					      			         		         
				      }
				   });
        		
        	}else{
        		alert("Debe ingresr rut de doctor.");
        	}	
        }
        
        function nuevoDoctor(){        	
        	showPopWin("<%=request.getContextPath()%>/Medico.do?method=cargaFormulario&pagina=optometria",790, 435, recibeNuevoDoctor, false);
        }        
        
        function recibeNuevoDoctor(doctor){
        	
        	document.getElementById('nifdoctor').value = doctor[1];
			document.getElementById('doctor').value = doctor[0];
			document.getElementById('dvnifdoctor').value = doctor[4];					      	
			document.getElementById('nombre_doctorDIV').innerHTML = doctor[2] +" "+ doctor[3];
			
        }
        
        function imprimirComprobanteConsulta(){
        	var numCod = document.getElementById('numCodigo').value;
			var numCliente = document.getElementById('cliente').value;
        	
        	var urlAccion = "/ConsultaOptometrica.do?method=imprimirComprobante&num="+numCod+"&cli="+numCliente;
        	window.open("<%=request.getContextPath()%>"+urlAccion,"Comprobante" , "width=550,height=700,scrollbars=NO");
        }
        </script> 
        
</head>
<body onload="inicio_pagina();if(history.length>0)history.go(+1)">

<html:form action="/ConsultaOptometrica?method=IngresaConsultaOptometrica" styleId="form1">
<html:hidden property="cerrarPagina" styleId="cerrarPagina"  />
<html:hidden property="pagina" styleId="pagina"  />
<html:hidden property="cliente" styleId="codigo_cliente"  />
<html:hidden property="accion" styleId="accion"/>
<html:hidden property="fecha_graduacion" styleId="fecha_graduacion"/>
<html:hidden property="numero_graduacion" styleId="numero_graduacion" />
<html:hidden property="exito" styleId="exito" />
<html:hidden property="codigo_cliente_agregado" styleId="clienteagregadoId"/>
<html:hidden property="nif_cliente_agregado" styleId="nifagregadoId"/>
<html:hidden property="existe_graduacion" styleId="existe_graduacion"/>
<html:hidden property="cod_doctor" styleId="doctor"/>
<html:hidden property="numCodigo" styleId="numCodigo"/>
<html:hidden property="puedeEditarse" styleId="puedeEditarse"/>
<html:hidden property="puedeImprimirse" styleId="puedeImprimirse"/>


 
<html:hidden property="estaGrabado" styleId="estaGrabado"/>

<table width="100%" cellspacing="0">

            <tr>
				<td align="left" id="txt4" width="90%" height="24" style="height: 24px" class="tituloConsulta">
					<bean:message key="cliente.consultaOptometrica.titCliente" />
				</td>
				<td align="right" id="txt4" height="24" class="tituloConsulta">
		    		<div  id="graduaId" style="display:none" >	
			            <a href="#"	onclick="nuevo_graduacion()">
							<img src="images/nuevo.png" width="20" height="20"	border="0" title="Nueva Consulta Optometrica" >
						</a>
					</div>	
    			</td>
    			<td width="30" align="left" id="txt4" height="24" class="tituloConsulta">
    				<div id="imprimirId" style="display:none" >
		    			<a href="#" onclick="imprimirComprobanteConsulta();">
		      				<img src="images/printer.png" width="15" height="15" border="0" title="Imprimir Comprobante">
		    			</a>
	    			</div>
    			</td>
              	<td align="right" id="txt4" height="24" class="tituloConsulta">
	              	<a href="#" onclick="guardarConsultaOpt();">
	      				<img src="images/check.png" width="15" height="15" border="0" title="Guardar Consulta Optometrica" >
	    			</a>
    			</td>
    			<td width="30" align="right" bgcolor="#00008B" id="txt4" height="24" class="tituloConsulta">
	    			<a href="#" onclick="cerrar_ventanas();">
	      				<img src="images/cancel.png" width="15" height="15" border="0" title="Salir de Consulta Optometrica" >
	    			</a>
    			</td>
    		</tr>
</table>
<table cellspacing="1" width="100%" border="0">
	
	<tr>
		<td id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.cliente"/></td>
		<td id="txt5" bgcolor="#c1c1c1" colspan="9">
			<html:text property="nombre_cliente" size="30" styleId="nombre_cliente"  styleClass="fto"/>
			<a href="#" onclick="javascript:busqueda_cliente()">
      			<img src="images/lupa.png" width="15" height="15" border="0" title="Busqueda de Cliente" >
    		</a>
		</td>
		<td id="txt5" bgcolor="#c1c1c1" width="150px">
			<logic:equal name="consultaOptometricaForm" property="diferenteAdd" value="true">
				<html:checkbox property="diferenteAdd" styleId="diferenteADD">Diferente Adicion</html:checkbox>
			</logic:equal>
			<logic:notEqual name="consultaOptometricaForm" property="diferenteAdd" value="true">
				<html:checkbox property="diferenteAdd" styleId="diferenteADD">Diferente Adicion</html:checkbox>
			</logic:notEqual>
		</td>
	</tr>
	<tr>
		<td colspan="11" align="left" bgcolor="#778899" id="txt4"><bean:message key="cliente.consultaOptometrica.graduacion"/></td>
	</tr>
	
  <tr>
    <td id="txt5" bgcolor="#c1c1c1">&nbsp;</td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.esfera"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.cilindro"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.eje"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.cerca"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.adicion"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.dn.pl"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.dn.pc"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.avsr"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.avcc"/></td>
    <td  id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.avcl"/></td>
  </tr>
  <tr>
    <td id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.od"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_esfera" size="5" styleId="OD_esfera" styleClass="fto" onblur="validaEsfera(this,'derecha');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_cilindro" size="5" styleId="OD_cilindro" styleClass="fto" onblur="validaCilindro(this, 'derecho');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_eje" size="5" styleId="OD_eje" disabled="false"   styleClass="fto" onblur="validaEje(this, 'derecho');"  onkeypress="return validar_numerico(event)" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_cerca"  size="5" styleId="OD_cerca" styleClass="fto" onblur="validacionCerca(this, 'derecha');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_adicion"  size="5" styleId="OD_adicion" styleClass="fto" onblur="validaAdicion(this, 'derecha');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_dnpl" styleId="OD_dnpl"  size="5" styleClass="fto" onblur="validaDNPL(this, 'derecha');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_dnpc" styleId="OD_dnpc" size="5" styleClass="fto" onblur="validaDNPC(this, 'derecha');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_avsc"  styleId="OD_avsc"  size="5" styleClass="fto"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_avcc"  styleId="OD_avcc"  size="5" styleClass="fto"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OD_avcl"  styleId="OD_avcl"  size="5" styleClass="fto"/></td>
  </tr>
  <tr>
    <td id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.observacion"/></td>
    <td id="txt5" bgcolor="#c1c1c1" colspan="10"><html:text property="OD_observaciones"  size="100" styleClass="fto" onblur="javascript:this.value=this.value.toUpperCase();"/>
    </td>
  </tr>
  <tr>
    <td id="txt5" bgcolor="#c1c1c1"><bean:message key="cliente.consultaOptometrica.oi"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_esfera"  size="5" styleId="OI_esfera" styleClass="fto" onblur="validaEsfera(this,'izquierda');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_cilindro"  size="5" styleId="OI_cilindro" styleClass="fto" onblur="validaCilindro(this, 'izquierda');"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_eje"  size="5" styleId="OI_eje"  disabled="false"   styleClass="fto" onblur="validaEje(this, 'izquierdo');"  onkeypress="return validar_numerico(event)" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_cerca"  size="5" styleId="OI_cerca" styleClass="fto" onblur="validacionCerca(this, 'izquierda');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_adicion" styleId="OI_adicion"  size="5" styleClass="fto" onblur="validaAdicion(this, 'izquierda');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_dnpl" styleId="OI_dnpl" size="5" styleClass="fto" onblur="validaDNPL(this, 'izquierda');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_dnpc" styleId="OI_dnpc" size="5" styleClass="fto" onblur="validaDNPC(this, 'izquierda');" /></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_avsc"  styleId="OI_avsc"  size="5" styleClass="fto"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_avcc"  styleId="OI_avcc"  size="5" styleClass="fto"/></td>
    <td id="txt5" bgcolor="#c1c1c1"><html:text property="OI_avcl"  styleId="OI_avcl"  size="5" styleClass="fto"/></td>
  </tr>
  <tr>
    <td id="txt5" bgcolor="#c1c1c1" ><bean:message key="cliente.consultaOptometrica.observacion"/></td>
    <td id="txt5" bgcolor="#c1c1c1" colspan="10"><html:text property="OI_observaciones" size="100" styleClass="fto" onblur="javascript:this.value=this.value.toUpperCase();"/></td>
  </tr>
   <tr>
    <td id="txt5" bgcolor="#c1c1c1" ><bean:message key="cliente.consultaOptometrica.derivacion"/></td>
    <td id="txt5" bgcolor="#c1c1c1" colspan="10">
    	<html:text property="derivacion" size="100" styleClass="fto" onblur="javascript:this.value=this.value.toUpperCase();"/>
    	<!-- html:textarea styleId="derivacion" property="derivacion" cols="55" rows="5" onblur="javascript:this.value=this.value.toUpperCase();"/-->


    </td>
  </tr>
</table>

	<table width="100%" border="0" cellspacing="1">
			<tr>
				<td id="txt5" bgcolor="#c1c1c1" align="left" colspan="3"><bean:message
						key="cliente.consultaOptometrica.atendido.por" /></td>
				<td id="txt5" bgcolor="#c1c1c1" align="left" width="350"><html:select
						property="agente" name="consultaOptometricaForm" styleClass="fto"
						styleId="agente" title="">
						<html:option value="-1">
							<bean:message key="cliente.consultaOptometrica.seleccione.agente" />
						</html:option>
						<html:optionsCollection name="consultaOptometricaForm"
							property="listaAgentes" label="usuario" value="usuario"
							styleClass="fto" />
					</html:select>
				</td>
				<td id="txt5" bgcolor="#c1c1c1" align="left" style="width: 95px"><bean:message
						key="cliente.consultaOptometrica.f.emision" /></td>
				<td id="txt5" bgcolor="#c1c1c1" align="left"><html:text
						property="fechaEmision" readonly="true" size="10"
						name="consultaOptometricaForm" styleClass="fto" styleId="fechaEmision"
						title="Haga click aquí para abrir calendario"
						onchange="suma_fecha();" /></td>
				<td id="txt5" bgcolor="#c1c1c1" align="left" colspan="3">F. Prox. Revisión</td>
				<td id="txt5" bgcolor="#c1c1c1" align="left"><html:text
						property="fechaProxRevision" readonly="true"
						styleId="fechaProxRevision" styleClass="fto" /></td>
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1" colspan="6" align="left"></td>
				<td colspan="4" id="txt5" bgcolor="#c1c1c1" style="width: 511px" width="511">
					<logic:equal name="consultaOptometricaForm" property="clienteFirmo" value="true">
						<html:checkbox property="clienteFirmo" styleId="clienteFirmo">Cliente Firmo Consentimiento</html:checkbox>
					</logic:equal>
					<logic:notEqual name="consultaOptometricaForm" property="clienteFirmo" value="true">
						<html:checkbox property="clienteFirmo" styleId="clienteFirmo">Cliente Firmo Consentimiento</html:checkbox>
					</logic:notEqual>
				</td>	
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1" align="left" colspan="10">Recomendaciones:</td>
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1" align="center" colspan="10">
					<table width="450" border="0" cellspacing="0">
						<tr>
							<td id="txt5" bgcolor="#c1c1c1" align="left" width="90px">
								<logic:equal name="consultaOptometricaForm" property="filtroAzul" value="true">
									<html:checkbox property="filtroAzul" styleId="filtroAzul">Filtro Azul</html:checkbox>
								</logic:equal>
								<logic:notEqual name="consultaOptometricaForm" property="filtroAzul" value="true">
									<html:checkbox property="filtroAzul" styleId="filtroAzul">Filtro Azul</html:checkbox>
								</logic:notEqual>
							</td>
							<td id="txt5" bgcolor="#c1c1c1" align="left" width="90px">
								<logic:equal name="consultaOptometricaForm" property="antireflejo" value="true">
									<html:checkbox property="antireflejo" styleId="antireflejo">Antireflejo</html:checkbox>
								</logic:equal>
								<logic:notEqual name="consultaOptometricaForm" property="antireflejo" value="true">
									<html:checkbox property="antireflejo" styleId="antireflejo">Antireflejo</html:checkbox>
								</logic:notEqual>
							</td>		
							<td id="txt5" bgcolor="#c1c1c1" align="left" width="90px">
								<logic:equal name="consultaOptometricaForm" property="bifocal" value="true">
									<html:checkbox property="bifocal" styleId="bifocal">Bifocal</html:checkbox>
								</logic:equal>
								<logic:notEqual name="consultaOptometricaForm" property="bifocal" value="true">
									<html:checkbox property="bifocal" styleId="bifocal">Bifocal</html:checkbox>
								</logic:notEqual>
							</td>
							<td id="txt5" bgcolor="#c1c1c1" align="left" width="90px">
								<logic:equal name="consultaOptometricaForm" property="progresivos" value="true">
									<html:checkbox property="progresivos" styleId="progresivos">Progresivos</html:checkbox>
								</logic:equal>
								<logic:notEqual name="consultaOptometricaForm" property="progresivos" value="true">
									<html:checkbox property="progresivos" styleId="progresivos">Progresivos</html:checkbox>
								</logic:notEqual>
							</td>
							<td id="txt5" bgcolor="#c1c1c1" align="left" width="90px">
								<logic:equal name="consultaOptometricaForm" property="fotosensible" value="true">
									<html:checkbox property="fotosensible" styleId="fotosensible">Fotosensible</html:checkbox>
								</logic:equal>
								<logic:notEqual name="consultaOptometricaForm" property="fotosensible" value="true">
									<html:checkbox property="fotosensible" styleId="fotosensible">Fotosensible</html:checkbox>
								</logic:notEqual>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1" align="left" colspan="10"><br></td>
			</tr>
			<tr>
					<td colspan="10" align="center" bgcolor="#00008B" id="txt4">Histórico</td>
			</tr>
			<tr>
  				<td id="txt5" bgcolor="#c1c1c1" colspan="10" align="center">
			  		<table width="80%" cellspacing="0" >
						<tr>
							<td scope="col" id="txt4" bgcolor="#778899" width="40%" align="center"><bean:message key="cliente.consultaOptometrica.fecha"/></td>
						    <td scope="col" id="txt4" bgcolor="#778899" width="40%" align="center"><bean:message key="cliente.consultaOptometrica.numero"/></td>
						    <td scope="col" id="txt4" bgcolor="#778899" align="center">Detalle</td>
						</tr>
					</table>
					<div id="scroll_historico">
						<table width="100%" height="100%">
							<% int i=1; %>
							<logic:present property="listaGraduaciones"
									name="consultaOptometricaForm">
								<logic:iterate id="listaGraduaciones"  property="listaGraduaciones" name="consultaOptometricaForm">
									<bean:define id="numero" type="String">
										<bean:write name="listaGraduaciones" property="numero" />
									</bean:define>
									<bean:define id="fecha" type="String">
										<bean:write name="listaGraduaciones" property="fecha" />
									</bean:define>
									<tr>
										<td scope="col" id="txt5" bgcolor="#c1c1c1" width="40%">
										<bean:write name="listaGraduaciones" property="fecha" /></td>
										<td  scope="col" id="txt5" bgcolor="#c1c1c1"  align="center" width="40%"><bean:write name="listaGraduaciones" property="numero" /></td>
										<td scope="col" id="txt5" bgcolor="#c1c1c1" width="" align="left">
											<a href="#" onclick="ver_graduacion('${numero}','${fecha}')">
												ver
											</a>
										</td>
									</tr>
									<%i++; %> 
								</logic:iterate>
							</logic:present>
						</table>
					</div>
  				</td>
  			</tr>
		</table>
	</html:form>
</body>
</html:html>