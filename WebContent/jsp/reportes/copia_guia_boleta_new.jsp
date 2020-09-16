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

//OJO la Nota de Crédito debe mantenerse como antes 
//REVISAR

		var $j = jQuery.noConflict();
		
		function trimBoletaGuia(s){
			s = s.replace(/\s+/gi, ''); //sacar espacios repetidos dejando solo uno
			s = s.replace(/^\s+|\s+$/gi, ''); //sacar espacios blanco principio y final
			return s;
		}
		
		//CMRO
		
		function mostrarDocumento(){
			
			//alert("CMRO en mostrarDocumento");
			
			var numDoc = document.getElementById('numeroBoleta').value;
			var tipo = document.getElementById('tipo').value;
			//var vUrl = "http://10.216.4.24/";
			var vUrl = document.getElementById('vUrl').value;
			var vRutLuxottica = document.getElementById('vRutLux').value;
			var vPrefBoleta = document.getElementById('vPrefBoleta').value;
			var vPrefGuia = document.getElementById('vPrefGuia').value;
			var vPrefNC= document.getElementById('vPrefNC').value;
			var vSeparador = document.getElementById('vSeparador').value;
			var vExtDoc = document.getElementById('vExtDoc').value;
			
			/*
			alert("CMRO numDoc = "+numDoc);
			alert("CMRO tipo = "+tipo);
			alert("CMRO vUrl = "+vUrl);
			alert("CMRO vRutLuxottica = "+vRutLuxottica);
			alert("CMRO vPrefBoleta = "+vPrefBoleta);
			alert("CMRO vPrefGuia = "+vPrefGuia);
			alert("CMRO vPrefNC = "+vPrefNC);
			alert("CMRO vSeparador = "+vSeparador);
			alert("CMRO vExtDoc = "+vExtDoc);
			*/
			
			if(""==numDoc){
				alert("Debe ingresar un número de documento");
			}else{
					if(""== tipo){
						alert("Debe seleccionar un tipo de documento");
					}else{
						
						if ((null!= tipo) && ("" != tipo) && ("N" == tipo)){
							//alert("CMRO tipo = "+tipo);
							verificaDocumento();
						}else{
						
							//alert("CMRO numDoc = "+numDoc+" tipo = "+tipo);
							
							var urlDoc = "";
							
							switch(tipo){
								case "B":
									urlDoc = vUrl+vPrefBoleta+vSeparador+numDoc+vExtDoc;
									break;
								case "G":
									urlDoc = vUrl+vPrefGuia+vSeparador+numDoc+vExtDoc;
									break;
								case "T":
									urlDoc = vUrl+vPrefGuia+vSeparador+vRutLuxottica+vSeparador+numDoc+vExtDoc;
									break;
								default:
									alert("No existe el documento electrónico.");
								break;
							}
						
							urlDoc = urlDoc.replace("\n","");
							
							//alert("CMRO urlDoc = "+urlDoc);
							
						    
								setTimeout(function(){ 
									window.onerror = mostrarError;
									window.open(urlDoc,"_blank");
									window.focus();
								}, 7000);
							
					}
				}
			}
		}
		
		function mostrarError()
	    {
	        window.alert("No existe el documento electrónico.");
	        return true;
	    }
		//CMRO
		
		function verificaDocumento()
		{	
			var numeroBoleta = document.getElementById('numeroBoleta').value;
			var tipo = document.getElementById('tipo').value;
			numeroBoleta = trimBoletaGuia(numeroBoleta);
			var nboleta = $j.trim($j("#numeroBoleta").val());
			var tipodoc = $j.trim($j("#tipo").val());
			
			if(""==numeroBoleta){
				alert("Debe ingresar un número de documento");
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
						  		//var urlbol = "http://10.216.4.24/";
						  		var urlbol = document.getElementById('vUrl').value;
						  		
						  		var url = "<%=request.getContextPath()%>/CreaCopiaGuiaBoletaServlet?tipo="+tipodoc+"&numero="+nboleta;
						  		var extension =".pdf";
								if(data=="" || data == null){
									alert("No existe el documento electrónico.");	
								}else{
									switch(tipodoc){
										case "B":
											window.open(urlbol+data+extension,"_blank"); 
											window.focus();
										break;
										case "N":
											window.open(urlbol+data+extension,"_blank"); 
											window.focus();
										break;
										case "O":
											window.open(url,"_blank"); 
											window.focus();
										break;
										default:
											alert("No existe el documento electrónico.");
										break;
									}
									
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
					
					if ("N" == tipo){
						
						var numero=document.getElementById('numeroBoleta').value; 
						var url = "/CreaCopiaGuiaBoletaServlet?tipo="+tipo+"&numero="+numero;
						
						if(""==tipo || ""==numero ){
								alert("Debe ingresar número de documento y tipo de documento");
						}else{
								var voucher;
			       				voucher = window.open("<%=request.getContextPath()%>"+url);   
						}
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
<html:hidden property="vRutLux" value="<%=Constantes.STRING_RUT_LUXOTTICA%>" styleId="vRutLux"/>
<html:hidden property="vPrefBoleta" value="<%=Constantes.STRING_WS_DTYPE%>" styleId="vPrefBoleta"/>
<html:hidden property="vPrefGuia" value="<%=Constantes.STRING_WS_DTYPE_GE%>" styleId="vPrefGuia"/>
<html:hidden property="vPrefNC" value="<%=Constantes.STRING_WS_DTYPE_NC%>" styleId="vPrefNC"/>
<html:hidden property="vSeparador" value="<%=Constantes.STRING_ESPACIO_EN_BLANCO%>" styleId="vSeparador"/>
<html:hidden property="vExtDoc" value="<%=Constantes.STRING_EXTENSION_PDF%>" styleId="vExtDoc"/>
<table width="100%" cellspacing="0">
            <tr>
              	<td align="left" bgcolor="#373737" id="txt4" >IMPRIMIR BOLETA - GUIA - NOTA DE CREDITO</td> 
              	<td align="right" bgcolor="#373737" id="txt4">
              		<a href="#" onclick="mostrarDocumento()">
      						<img src="images/lupa.png" width="15" height="15" border="0" title="Buscar Documento" >
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
				<td id="txt5" bgcolor="#c1c1c1">Número Documento</td>
				<td id="txt5" bgcolor="#c1c1c1"><html:text property="numeroBoleta"
						styleClass="fto" styleId="numeroBoleta"/></td>
			</tr>
			<tr>
				<td id="txt5" bgcolor="#c1c1c1">Tipo Documento</td>
				<td id="txt5" bgcolor="#c1c1c1">
						<html:select property="documento"
						 styleClass="fto" styleId="tipo">
							<html:option value="">Selecionar</html:option>
							<html:option value="B">Boleta</html:option>
							<html:option value="G">Guía de Despacho Venta</html:option>
							<html:option value="T">Guía de Despacho Traslado</html:option>
							<html:option value="N">Nota de Crédito</html:option>
						</html:select>
				</td>
			</tr>
			
		</table>

</html:form>
</body>
</html>