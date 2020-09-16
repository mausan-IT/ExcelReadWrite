
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

<style type="text/css">
	td.imagen{
		background-color: #FFFFFF;
	}
	
	p.texto{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
		font-color: #808080;	
	}
	
	p.textoDer{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
		font-color: #808080;
		text-align: right;
		
	}
	
	p.textoIzq{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
		font-color: #808080;
		text-align: left;
		
	}
	
	p.textoBold{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
		font-color: #DCDCDC;
		font-weight: bold;
	}
	
	p.textoBoldDer{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
		font-color: #DCDCDC;
		font-weight: bold;
		text-align: right;
	}
	
	p.outset {border-style: outset;}
	
	td.formato{ height: 30px;}
	
	td.formato2{ 
				height: 30px;
				width: 20%;
	           }
	
	table.formulario{
	    border-color: #DCDCDC;
		border-width: 1px;
		border-style: solid solid solid solid;
		width: 450px;
	}
	
	p.titulo{
		display:block;
		background-color: #00008B;
	}
	
	td.linea{
		height: 30px;
		border-bottom:1px solid #DCDCDC;
	}
	
	td.lineaLarga{
		height: 30px;
		border-bottom:1px solid #808080;
		width: 300px;
	}
	
	td.lineaCorta{
		height: 30px;
		border-bottom:1px solid #7C817E;
		width: 50px;
	}
	
	@media print { 
 #cabecera, #pie { 
  display: none;
 } 
}
</style>

		
<title>Comprobante Consulta Optometrica</title>

<script language="javascript">	

	function imprComprobante(muestra){
		
		window.print();
		
	}
		
</script>


</head>
<body>

	<html:form action="/ConsultaOptometrica?method=IngresaConsultaOptometrica" styleId="form1">
	   <div id="cabecera">
			<table width="100%" border="0">
				<tr>
					<td align="right" bgcolor="#373737" id="txt1" width="850">
						<p class="titulo">
							<a href="#"	onclick="javascript:imprComprobante('comprobante')">
							 <img src="images/printer.png" width="20" height="20"	border="0" title="Imprimir guia">
						</a>
						</p>
					</td> 
				</tr>
			</table>
		</div>
				
		<!-- FORMATO COMPROBANTE -->
		<div id="comprobante">
			<table align="center" class="formulario">
				<tr>
					<td class="imagen" align="center" colspan="3"><img src="images/TituloComprobante.JPG" width="378" height="65" border="0"></td>
				</tr>
				<tr>
					<td colspan="3">
					
						<table width="100%" border="0">
							<tr>
								<td class="formato"><p class="textoBold">NOMBRE:</p></td>
								<td class="lineaLarga" colspan="5"><p class="texto"><bean:write name="consultaOptometricaForm" property="nombreCliente" /></p></td>
							</tr>
							<tr>
								<td class="formato"><p class="textoBold">RUN:</p></td>
								<td class="lineaCorta"><p class="texto"><bean:write name="consultaOptometricaForm" property="runCliente" /></p></td>
								<td class="formato"><p class="textoBold">FECHA:</p></td>
								<td class="lineaCorta"><p class="texto"><bean:write name="consultaOptometricaForm" property="fechaEmision" /></p></td>
								<td class="formato"><p class="textoBold">TELF.:</p></td>
								<td class="lineaCorta"><p class="texto"><bean:write name="consultaOptometricaForm" property="telfCliente" /></p></td>
							</tr>
						</table>
						
					</td>
				</tr>
				<tr>
					<td colspan="3">
					
						<table width="100%" border="0">
							<tr>
								<td class="formato" width="10%"><p class="textoBold">Rx:</p></td>
								<td class="formato" colspan="6"></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato" colspan="6"><p class="textoBold">OD:</p></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato2"><p class="textoBoldDer">ESF</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OD_esfera" /></p></td>
								<td class="formato2"><p class="textoBoldDer">CYL</p></td>
								<td class="lineaCorta" colspan="2"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OD_cilindro" />
								 x <bean:write name="consultaOptometricaForm" property="OD_eje" /></p></td>
								 <td class="formato"></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato" colspan="6"><p class="textoBold">OI:</p></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato2"><p class="textoBoldDer">ESF</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OI_esfera" /></p></td>
								<td class="formato2"><p class="textoBoldDer">CYL </p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OI_cilindro" />
								 x <bean:write name="consultaOptometricaForm" property="OI_eje" /></p></td>
								 <td class="formato" colspan="3"></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato"><p class="textoBold">ADD:</p></td>
								<td class="lineaLarga" colspan="5">
									<p class="texto">
										<bean:write name="consultaOptometricaForm" property="OD_adicion" /> /
										<bean:write name="consultaOptometricaForm" property="OI_adicion" />
									</p>
								</td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato"><p class="textoBold">DP Lejos:</p></td>
								<td class="formato"><p class="textoBoldDer">OD</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OD_dnpl" /></p></td>
								<td class="formato"><p class="textoBoldDer">OI</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OI_dnpl" /></p></td>
								<td class="formato"></td>
							</tr>
							<tr>
								<td class="formato"></td>
								<td class="formato"><p class="textoBold">DP Cerca:</p></td>
								<td class="formato"><p class="textoBoldDer">OD</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OD_dnpc" /></p></td>
								<td class="formato"><p class="textoBoldDer">OI</p></td>
								<td class="lineaCorta"><p class="textoIzq"><bean:write name="consultaOptometricaForm" property="OI_dnpc" /></p></td>
								<td class="formato"></td>
							</tr>
						</table>
						
					</td>
				</tr>
				<tr>
					<td colspan="3">
							
						<table width="100%" border="0">
							<tr>
								<td class="formato"><p class="textoBoldDer">INDICACIONES:</p></td>
								<td class="lineaLarga" colspan="7"><p class="texto"><bean:write name="consultaOptometricaForm" property="derivacion" /></p></td>
							</tr>
							<tr><td colspan="8"></td></tr>
							<tr>
								<td colspan="8" class="formato"><p class="textoBold">Recomendaciones:</p></td>
							</tr>
							<tr>
								<td class="formato"><p class="textoBoldDer">Filtro Azul:</p></td>
								<td class="lineaCorta">
									<p class="texto">
										<logic:equal name="consultaOptometricaForm" property="filtroAzul" value="true">
											X
										</logic:equal>
									</p>
								</td>
								<td class="formato"><p class="textoBoldDer">Antireflejo:</p></td>
								<td class="lineaCorta">
									<p class="texto">
										<logic:equal name="consultaOptometricaForm" property="antireflejo" value="true">
											X
										</logic:equal>
									</p>
								<td>
								<td class="formato"><p class="textoBoldDer">Bifocal:</p></td>
								<td class="lineaCorta">
									<p class="texto">
										<logic:equal name="consultaOptometricaForm" property="bifocal" value="true">
											X
										</logic:equal>
									</p>
								</td>
								<td class="formato"><p class="textoBoldDer">Progresivos:</p></td>
								<td class="lineaCorta">
									<p class="texto">
										<logic:equal name="consultaOptometricaForm" property="progresivos" value="true">
											X
										</logic:equal>
									</p>
								</td>
								<td class="formato"><p class="textoBoldDer">Fotosensible:</p></td>
								<td class="lineaCorta">
									<p class="texto">
										<logic:equal name="consultaOptometricaForm" property="fotosensible" value="true">
											X
										</logic:equal>
									</p>
								</td>
							</tr>
						</table>
						
					</td>
				</tr>
				<tr>
					<td colspan="3"><br></td>
				</tr>
				<tr>
					<td width="10%" class="formato"></td>
					<td class="formato" align="left">
						<p class="textoBoldDer">FIRMA TECNÓLOGO MÉDICO:</p>
					</td>
					<td class="lineaLarga"><p class="texto"></p></td>
				</tr>
				<tr>
					<td width="10%" class="formato"></td>
					<td align="left" class="formato">
						<p class="textoBoldDer">FIRMA CLIENTE:</p>
					</td>
					<td class="lineaLarga"><p class="texto"></p></td>
				</tr>
			</table>
		</div>
	<!-- FIN DE FORMATO COMPROBANTE -->
	</html:form>
</body>
</html:html>