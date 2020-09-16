<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import='cl.gmo.pos.venta.utils.Constantes' %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title><bean:message key="title.pos"/></title>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>	
<script language="javascript">

		var $j = jQuery.noConflict();
		
		function trimBoletaGuia(s){
			s = s.replace(/\s+/gi, ''); //sacar espacios repetidos dejando solo uno
			s = s.replace(/^\s+|\s+$/gi, ''); //sacar espacios blanco principio y final
			return s;
		}
		
		

		
		
		
		function verificaDocumento()
		{	
			//alert("CMRO verificaDocumento");
			
			var numeroBoleta = document.getElementById('numeroBoleta').value;
			var tipo = document.getElementById('tipo').value;
			numeroBoleta = trimBoletaGuia(numeroBoleta);
			var nboleta = $j.trim($j("#numeroBoleta").val());
			var tipodoc = $j.trim($j("#tipo").val());
			
			if(""==numeroBoleta){
				alert("Debe ingresar un numero de boleta o guia");
			}else{
				if(""== tipo){
					alert("Debe seleccionar un tipo de documento");
				}else{
						$j.ajax({
							  type: "POST",
							  url: 'CopiaGuiaBoleta.do?method=validaDocuento',
							  dataType: "text",
							  data:"numeroBoleta="+nboleta+"&tipo="+tipodoc ,
							  asycn:false,
							  success: function(data){
							  
							  		//var urlbol = "http://10.216.4.16/";
							  		
									var urlbol = document.getElementById('vUrl').value;
							  		
							  		//alert("CMRO urlbol = "+urlbol);
							  		
							  		var extension = document.getElementById('vExtDoc').value;
							  		
							  		
							  		//alert("CMRO data = "+data);
									if(data=="" || data == null){
										alert("No existe el documento electrónico.");	
									}else{		
 
									if (tipodoc =="T") urlbol = "http://10.216.4.9/PDF/"; //Solo activar esta instruccion en Producción
										
									    var sNombres = data.split("|");
									    
										window.open(urlbol+data+extension,"_blank"); 
										window.focus();
									}
							  }													  												  					 	  
				 		});		
					
				}
        	}
		}
		
		function cerrar()
        {
       			parent.carga_url_padre('<%=request.getContextPath()%>/Menu.do?method=CargaMenu');
        }
		function imprimir()
		{
			if (document.getElementById('numeroBoleta').value == "") {
				document.getElementById('numeroBoleta').value = "0";
				alert("Documento no existe");
			}
			else
			{
				if (document.getElementById('numeroBoleta').value != "0") {
					var tipo=document.getElementById('tipo').value; 
					var numero=document.getElementById('numeroBoleta').value; 
					var url = "/CreaCopiaGuiaBoletaServlet?tipo="+tipo+"&numero="+numero;
					if(""==tipo || ""==numero ){
							alert("Debe ingresar numero de documento y tipo de documento");
					}else{
							var voucher;
		       				voucher = window.open("<%=request.getContextPath()%>"+url);   
					}
				}
				
			}
			
		}
		
		
		

		
</script>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/estilos.css" rel="stylesheet" type="text/css" />
		<link href="css/formatosFormularios.css" rel="stylesheet" type="text/css" />
</head>
<body onload="imprimir()">
<html:form action="/CopiaGuiaBoleta?method=cargaFormulario">
<html:hidden property="vUrl" value="<%=Constantes.STRING_URL_SIGNATURE%>" styleId="vUrl"/>
<html:hidden property="vExtDoc" value="<%=Constantes.STRING_EXTENSION_PDF%>" styleId="vExtDoc"/>
<table width="100%" cellspacing="0">
            <tr>
              	<td align="left" bgcolor="#373737" id="txt4" >IMPRIMIR DOCUMENTO</td> 
              	<td align="right" bgcolor="#373737" id="txt4">
              		<a href="#" onclick="verificaDocumento()">
      						<img src="images/lupa.png" width="15" height="15" border="0" title="Buscar Boleta o Guía" >
    				</a>
    			</td>
    			<td width="30" align="right" bgcolor="#373737" id="txt1"><a
					href="#" onclick="cerrar()"> <img src="images/cancel.png" width="15"
						height="15" border="0" title="Cerrar"> </a></td>
              </tr>
     </table>
		<table width="50%" border="0" cellspacing="1" align="center">
		<tr>
			<td >&nbsp;</td>
		</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1">Numero Boleta</td>
				<td id="txt5" bgcolor="#c1c1c1"><html:text property="numeroBoleta"
						styleClass="fto" styleId="numeroBoleta"/></td>
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1">Tipo Documento</td>
				<td id="txt5" bgcolor="#c1c1c1">
						<html:select property="documento"
						 styleClass="fto" styleId="tipo">
							<html:option value="">Seleccionar</html:option>
							<html:option value="B">Boleta</html:option>
							<html:option value="T">Guia de Despacho Traslado</html:option>
							<html:option value="O">Guia de Despacho Ventas</html:option>
							<html:option value="N">Nota de Crédito</html:option>
							
						</html:select>
				</td>
			</tr>
			
		</table>

</html:form>
</body>
</html>