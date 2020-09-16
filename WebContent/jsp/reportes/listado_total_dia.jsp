<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" /> 
        <link href="css/estilos.css" rel="stylesheet" type="text/css" />
		<link href="css/formatosFormularios.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="js/utils.js"></script>
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		
		
		<title><bean:message key="title.pos"/></title>
<script type="text/javascript">
			
			
			load();
            function validaBuscar(tipo)
            {
                document.forms[0].submit();
            }
            function cerrar()
           {
           		parent.carga_url_padre('<%=request.getContextPath()%>/Menu.do?method=CargaMenu');
           }
           function estado(error)
           {
           		if (error != "error") {
					alert(error);
				}
           }
           function imprimirTotalDia(){
           			var voucher;
        		voucher = window.open("<%=request.getContextPath()%>/CreaListadoTotalDiaServlet");
           }
        </script>
         
       

</head>
	<bean:define id="error" type="String">
		<bean:write property="error" name="listadoTotalDiaForm"/>
	</bean:define>
	
    <body onload="javascript:estado('${error}')">
	<html:form action="/ListadoTotalDia?method=buscar">
	<table width="100%" cellspacing="0">
            <tr>
              	<td align="left" bgcolor="#373737" id="txt4" ><bean:message key="listado.total.dia.listado.dia"/></td> 
              	<td align="right" bgcolor="#373737" id="txt4">
              		<a href="#" onclick="validaBuscar()" id="enviar">
      						<img src="images/lupa.png" width="15" height="15" border="0" title="Buscar listado total del d�a" >
    				</a>
    			</td>
    			<td width="30" align="right" bgcolor="#373737" id="txt4" >
    				<a href="#" onclick="imprimirTotalDia()">
      						<img src="images/printer.png" width="15" height="15" border="0" title="Imprimir listado total del d�a">
    				</a>
    			</td>
    			<td width="30" align="right" bgcolor="#373737" id="txt1"><a
					href="#" onclick="cerrar()"> <img src="images/cancel.png" width="15"
						height="15" border="0" title="Cerrar"> </a></td>
              </tr>
     </table>
            
		<table width="100%" border="0" cellspacing="1">
		  <tr>
		    <td id="txt5" bgcolor="#c1c1c1" ><bean:message key="listado.total.fecha.inicio"/></td>
		    <td id="txt5" bgcolor="#c1c1c1" >
		    	<html:text property="fecha_inicio" value="" styleClass="fto" styleId="popupDatepicker" readonly="true"/>
		    </td>

		  </tr>
		  
		  <tr>
		    <td id="txt5" bgcolor="#c1c1c1" ></td>
		    <td id="txt5" bgcolor="#c1c1c1" ></td>
		   
		  </tr>
		</table>
		<br>
		
      <div id="scrolling_articulos_buscar">
                <table width="100%" cellspacing="0" cellpadding="0" >
                    <logic:present property="listaTotalDiaEntrega" name="listadoTotalDiaForm">
                    <tr bgcolor="#66FFFF">
							<td scope="col" id="txt4" bgcolor="#373737" style="width: 90px" width="9%">Codigo</td>
							<td scope="col" id="txt4" bgcolor="#373737" width="148"	style="width: 148px">Tipo Angente</td>
							<td scope="col" id="txt4" bgcolor="#373737" width="55" style="width: 55px">Total</td>
							<td scope="col" id="txt4" bgcolor="#373737" width="103">Cobrado</td>
                    		<td scope="col" id="txt4" bgcolor="#373737" >Forma Pago</td>
                    		<td scope="col" id="txt4" bgcolor="#373737" >N. Doct.</td>
                    		<td scope="col" id="txt4" bgcolor="#373737" >Tipo</td>
                    		<td scope="col" id="txt4" bgcolor="#373737" >Monto Doct.</td>
               		 </tr>
               		 
                     <tr>
                       			<td  colspan="8">&nbsp;</td>
                     </tr>
                     <tr>
                       			<td id="txt5" bgcolor="white" colspan="8">Entregas</td>
                     </tr>
                       	<logic:iterate id="totalDiaEntrega" property="listaTotalDiaEntrega" name="listadoTotalDiaForm">
                       		
                        	<tr bgcolor="#66FFFF">
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="codigo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="tipoAgente"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="total"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="cobrado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="fPagado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="numeroDoc"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="tipo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEntrega" property="montoDoc"/></td>
                           	</tr>
                    	</logic:iterate>
                    </logic:present>
                    <logic:present property="listaTotalDiaVenta" name="listadoTotalDiaForm">
                    <tr>
                       			<td  colspan="8">&nbsp;</td>
                       		</tr>
                       		<tr>
                       			<td id="txt5" bgcolor="white" colspan="8">Ventas Directas</td>
                       		</tr>
                       	<logic:iterate id="totalDiaVentas" property="listaTotalDiaVenta" name="listadoTotalDiaForm">
                       		
                        	<tr bgcolor="#66FFFF">
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaVentas" property="codigo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaVentas" property="tipoAgente"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="total"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="cobrado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="fPagado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="numeroDoc"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="tipo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaVentas" property="montoDoc"/></td>
                           	</tr>
                    	</logic:iterate>
                    </logic:present>
                    <logic:present property="listaTotalDiaEncargo" name="listadoTotalDiaForm">
                    <tr>
                       			<td  colspan="8">&nbsp;</td>
                       		</tr>
                       		<tr>
                       			<td id="txt5" bgcolor="white" colspan="8">Encargos</td>
                       		</tr>
                       	<logic:iterate id="totalDiaEncargo" property="listaTotalDiaEncargo" name="listadoTotalDiaForm">
                       		
                        	<tr bgcolor="#66FFFF">
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaEncargo" property="codigo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaEncargo" property="tipoAgente"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="total"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="cobrado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="fPagado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="numeroDoc"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="tipo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaEncargo" property="montoDoc"/></td>
                           	</tr>
                    	</logic:iterate>
                    </logic:present>
                    <logic:present property="listaTotalDiaAnticipo" name="listadoTotalDiaForm">
                    <tr>
                       			<td  colspan="8">&nbsp;</td>
                       		</tr>
                       		<tr>
                       			<td id="txt5" bgcolor="white" colspan="8">Anticipos encargos anteriores</td>
                       		</tr>
                       	<logic:iterate id="totalDiaAnticipo" property="listaTotalDiaAnticipo" name="listadoTotalDiaForm">
                       		
                        	<tr bgcolor="#66FFFF">
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaAnticipo" property="codigo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaAnticipo" property="tipoAgente"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="total"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="cobrado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="fPagado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="numeroDoc"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="tipo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaAnticipo" property="montoDoc"/></td>
                           	</tr>
                    	</logic:iterate>
                    </logic:present>
                    <logic:present property="listaTotalDiaDevolucion" name="listadoTotalDiaForm">
                    <tr>
                       			<td  colspan="8">&nbsp;</td>
                       		</tr>
                       		<tr>
                       			<td id="txt5" bgcolor="white" colspan="8">Devoluciones</td>
                       		</tr>
                       	<logic:iterate id="totalDiaDevolucion" property="listaTotalDiaDevolucion" name="listadoTotalDiaForm">
                       		<tr bgcolor="#66FFFF">
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaDevolucion" property="codigo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1"><bean:write name="totalDiaDevolucion" property="tipoAgente"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="total"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="cobrado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="fPagado"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="numeroDoc"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="tipo"/></td>
                            	<td id="txt5" bgcolor="#c1c1c1" ><bean:write name="totalDiaDevolucion" property="montoDoc"/></td>
                           	</tr>
                    	</logic:iterate>
                    </logic:present> 
               </table>
          </div>
	</html:form>

</body>

<link rel="stylesheet" href="css/jquery-ui.min.css" />
<style>
   	/*Se modifica el tama�o de datepicker*/
	div.ui-datepicker{
	 font-size:9px;
	}
</style>
<script src="js/jquery-ui.min.js"></script>
<script>
	//Actualizaci�n de DatePicker
	var $j = jQuery.noConflict();
	var d = new Date();
				$j("#popupDatepicker").datepicker({
					changeYear: true,
					changeMonth:true,
					yearRange: '2000:'+d.getFullYear(),
					maxDate: '+0d'
	});
	$j.datepicker.regional['es'] = {
						
						monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
						monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
						dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi\u00e9rcoles', 'Jueves', 'Viernes', 'S\u00e1bado'],
						dayNamesShort: ['Dom','Lun','Mar','Mi\u00e9','Juv','Vie','S\u00e1b'],
						dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','S\u00e1'],
						weekHeader: 'Sm',
						dateFormat: 'dd/mm/yy',
						firstDay: 1,
						isRTL: false,
						showMonthAfterYear: false,
						yearSuffix: ''
	};
	$j.datepicker.setDefaults($j.datepicker.regional['es']);
</script>
</html:html>