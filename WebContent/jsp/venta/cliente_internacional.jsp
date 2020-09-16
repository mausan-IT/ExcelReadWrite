<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
	<title>Cliente Internacional</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/formatosFormularios.css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/subModal.js"></script>
	<script language="javascript" src="js/utils.js"></script>		
</head>
<body>
<table width="100%" cellspacing="0">
     <tr>
      	<td align="left" bgcolor="#373737" id="txt4" rowSpan="1" colSpan="1" >Cliente Internacional</td> 	        
		<td width="30" align="right" bgcolor="#373737" id="txt1">
			<a id="cerrar" href="javascript:void(0)"> 
			<img src="images/cancel.png" width="15" height="15" border="0" title="Cerrar"> </a>
		</td>
    </tr>
		</table>	    	
	    <table width="400px" cellspacing="1">    	
 		<tr bgcolor="#c1c1c1">
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<p>DNI / PASAPORTE:</p>					
			</td>
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<input type="text" id="dni_pas"  maxlength="30" />					
			</td>							
		<tr>
		<tr bgcolor="#c1c1c1">
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<p>Nombre :</p>					
			</td>
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<input type="text"  id="nombre_internacional"  maxlength="50" />					
			</td>							
		<tr>	
		<tr bgcolor="#c1c1c1">
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<p>Email:</p>					
			</td>
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<input type="text"   id="email_inter"  maxlength="50"/>					
			</td>							
		<tr>	
		<tr bgcolor="#c1c1c1">
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<p>Pa&iacute;s:</p>					
			</td>
			<td align="left" bgcolor="#c1c1c1" id="txt5" rowSpan="1" colSpan="1" width="50%">
				<select  id="nacionalidad" class="fto" id="txt5" style="width:150px;" >   
					<option value="">Selecciona Pa&iacute;s</option>
					<option value="AD">Andorra</option>
					<option value="AE">Emiratos &Aacute;rabes</option>
					<option value="AF">Afghanistan</option>
					<option value="AG">Antigua y Barbuda</option>
					<option value="AI">Anguilla</option>
					<option value="AL">Albania</option>
					<option value="AM">Armenia</option>
					<option value="AN">Antillas Holandesas</option>
					<option value="AO">Angola</option>
					<option value="AR">Argentina</option>
					<option value="AS">Samoa (American)</option>
					<option value="AT">Austria</option>
					<option value="AU">Australia</option>
					<option value="AW">Aruba</option>
					<option value="AZ">Azerbaiy&aacute;n</option>
					<option value="BA">Bosnia y Herzegovina</option>
					<option value="BB">Barbados</option>
					<option value="BD">Bangladesh</option>
					<option value="BE">B&eacute;lgica</option>
					<option value="BF">Burkina Faso</option>
					<option value="BG">Bulgaria</option>
					<option value="BH">Bahrain</option>
					<option value="BI">Burundi</option>
					<option value="BJ">Benin</option>
					<option value="BM">Bermuda</option>
					<option value="BN">Brunei</option>
					<option value="BO">Bolivia</option>
					<option value="BQ">Bonaire San Eustaquio y Saba</option>
					<option value="BR">Brasil</option>
					<option value="BS">Bahamas</option>
					<option value="BT">But&aacute;n</option>
					<option value="BW">Botswana</option>
					<option value="BY">Bielorrusia</option>
					<option value="BZ">Belize</option>
					<option value="CA">Canad&aacute;</option>
					<option value="CC">Islas Cocos (Keeling)</option>
					<option value="CF">Rep&uacute;blica Central Africana</option>
					<option value="CG">Congo</option>
					<option value="CH">Suiza</option>
					<option value="CI">Costa de Marfil</option>
					<option value="CK">Islas Cook</option>
					<option value="CL">Chile</option>
					<option value="CM">Camer&uacute;n</option>
					<option value="CN">China</option>
					<option value="CO">Colombia</option>
					<option value="CR">Costa Rica</option>
					<option value="CS">Serbia y Montenegro</option>
					<option value="CU">Cuba</option>
					<option value="CV">Cabo Verde</option>
					<option value="CX">Christmas Island</option>
					<option value="CY">Chipre</option>
					<option value="CZ">Rep&uacute;blica Checa</option>
					<option value="DE">Alemania</option>
					<option value="DJ">Djibouti</option>
					<option value="DK">Dinamarca</option>
					<option value="DM">Dominica</option>
					<option value="DO">Rep&uacute;blica Dominicana</option>
					<option value="DZ">Argelia</option>
					<option value="EC">Ecuador</option>
					<option value="EE">Estonia</option>
					<option value="EG">Egipto</option>
					<option value="ER">Eritrea</option>
					<option value="ES">Espa&ntilde;a</option>
					<option value="ET">Etiop&iacute;a</option>
					<option value="FI">Finlandia</option>
					<option value="FJ">Fiji</option>
					<option value="FK">Islas Falkland</option>
					<option value="FM">Micronesia</option>
					<option value="FO">Islas Faroe</option>
					<option value="FR">Francia</option>
					<option value="GA">Gab&oacute;n</option>
					<option value="GB">Gran Breta&ntilde;a</option>
					<option value="GD">Grenada</option>
					<option value="GE">Georgia</option>
					<option value="GF">Guiana Francesa</option>
					<option value="GH">Ghana</option>
					<option value="GI">Gibraltar</option>
					<option value="GL">Greenland</option>
					<option value="GM">Gambia</option>
					<option value="GN">Guinea</option>
					<option value="GP">Guadalupe</option>
					<option value="GQ">Guinea Ecuatorial</option>
					<option value="GR">Grecia</option>
					<option value="GT">Guatemala</option>
					<option value="GU">Guam</option>
					<option value="GW">Guinea Bissau</option>
					<option value="GY">Guyana</option>
					<option value="HK">Hong Kong</option>
					<option value="HN">Honduras</option>
					<option value="HR">Croacia</option>
					<option value="HT">Hait&iacute;</option>
					<option value="HU">Hungr&iacute;a</option>
					<option value="ID">Indonesia</option>
					<option value="IE">Irlanda</option>
					<option value="IL">Israel</option>
					<option value="IN">India</option>
					<option value="IQ">Irak</option>
					<option value="IR">Ir&aacute;n</option>
					<option value="IS">Islandia</option>
					<option value="IT">Italia</option>
					<option value="JM">Jamaica</option>
					<option value="JO">Jordania</option>
					<option value="JP">Jap&oacute;n</option>
					<option value="KE">Kenya</option>
					<option value="KH">Camboya</option>
					<option value="KI">Kiribati</option>
					<option value="KM">Islas Comoro</option>
					<option value="KN">San Crist&oacute;bal y Nieves</option>
					<option value="KP">Corea del Norte</option>
					<option value="KR">Corea del Sur</option>
					<option value="KW">Kuwait</option>
					<option value="KY">Islas Caim&aacute;n</option>
					<option value="KZ">Kazakhstan</option>
					<option value="LA">Laos</option>
					<option value="LB">L&iacute;bano</option>
					<option value="LC">Santa Luc&iacute;a</option>
					<option value="LI">Liechtenstein</option>
					<option value="LK">Sri Lanka</option>
					<option value="LL">Islas Malvinas</option>
					<option value="LR">Liberia</option>
					<option value="LS">Lesotho</option>
					<option value="LT">Lituania</option>
					<option value="LU">Luxemburgo</option>
					<option value="LV">Latvia</option>
					<option value="LY">Libia</option>
					<option value="MA">Marruecos</option>
					<option value="MC">Monaco</option>
					<option value="MD">Moldavia</option>
					<option value="ME">Montenegro</option>
					<option value="MG">Madagascar</option>
					<option value="MH">Islas Marshall</option>
					<option value="MK">Macedonia</option>
					<option value="ML">Mali</option>
					<option value="MM">Myanmar</option>
					<option value="MN">Mongolia</option>
					<option value="MO">Macao</option>
					<option value="MP">Islas Mariana del Norte</option>
					<option value="MQ">Martinica</option>
					<option value="MR">Mauritania</option>
					<option value="MS">Montserrat</option>
					<option value="MT">Malta</option>
					<option value="MU">Mauritius</option>
					<option value="MV">Maldivas</option>
					<option value="MW">Malawi</option>
					<option value="MX">M&eacute;xico</option>
					<option value="MY">Malasia</option>
					<option value="MZ">Mozambique</option>
					<option value="NA">Namibia</option>
					<option value="NC">New Caledonia</option>
					<option value="NE">Niger</option>
					<option value="NF">Isla Norfolk</option>
					<option value="NG">Nigeria</option>
					<option value="NI">Nicaragua</option>
					<option value="NL">Holanda</option>
					<option value="NO">Noruega</option>
					<option value="NP">Nepal</option>
					<option value="NR">Nauru</option>
					<option value="NU">Niue</option>
					<option value="NZ">Nueva Zelandia</option>
					<option value="OM">Oman</option>
					<option value="PA">Panam&aacute;</option>
					<option value="PE">Per&uacute;</option>
					<option value="PF">Polinesia Francesa</option>
					<option value="PG">Papua Nueva Guinea</option>
					<option value="PH">Filipinas</option>
					<option value="PK">Pakist&aacute;n</option>
					<option value="PL">Polonia</option>
					<option value="PM">Saint Pierre and Miquelon</option>
					<option value="PN">Pitcairn Islands</option>
					<option value="PR">Puerto Rico</option>
					<option value="PT">Portugal</option>
					<option value="PW">Palau</option>
					<option value="PY">Paraguay</option>
					<option value="QA">Qatar</option>
					<option value="RE">Reunion</option>
					<option value="RO">Rumania</option>
					<option value="RS">Serbia</option>
					<option value="RU">Rusia</option>
					<option value="RW">Rwanda</option>
					<option value="SA">Arabia Saudita</option>
					<option value="SB">Solomon Islands</option>
					<option value="SC">Seychelles</option>
					<option value="SD">Sud&aacute;n</option>
					<option value="SE">Suecia</option>
					<option value="SG">Singapore</option>
					<option value="SH">Saint Helena</option>
					<option value="SI">Eslovenia</option>
					<option value="SK">Eslovaquia</option>
					<option value="SL">Sierra Leone</option>
					<option value="SM">San Marino</option>
					<option value="SN">Senegal</option>
					<option value="SO">Somalia</option>
					<option value="SR">Surinam</option>
					<option value="ST">Sao Tome and Principe</option>
					<option value="SV">El Salvador</option>
					<option value="SY">Siria</option>
					<option value="SZ">Swaziland</option>
					<option value="TC">Islas Turcos y Caicos</option>
					<option value="TD">Chad</option>
					<option value="TG">Togo</option>
					<option value="TH">Tailandia</option>
					<option value="TJ">Tadzhikistan</option>
					<option value="TL">Timor Oriental</option>
					<option value="TM">Turkmenistan</option>
					<option value="TN">Tunisia</option>
					<option value="TO">Tonga</option>
					<option value="TR">Turqu&iacute;a</option>
					<option value="TT">Trinidad y Tobago</option>
					<option value="TV">Tuvalu</option>
					<option value="TW">Taiwan</option>
					<option value="TZ">Tanzania</option>
					<option value="UA">Ucrania</option>
					<option value="UG">Uganda</option>
					<option value="US">Estados Unidos</option>
					<option value="UY">Uruguay</option>
					<option value="UZ">Uzbekistan</option>
					<option value="VA">Ciudad del Vaticano</option>
					<option value="VC">Saint Vincent and Grenadines</option>
					<option value="VE">Venezuela</option>
					<option value="VG">Islas V&iacute;rgenes Brit&aacute;nicas</option>
					<option value="VI">Islas V&iacute;rgenes de los Estados Unidos</option>
					<option value="VN">Vietnam</option>
					<option value="VU">Vanuatu</option>
					<option value="WF">Wallis and Futuna Islands</option>
					<option value="WK">Wake Island</option>
					<option value="WS">Samoa (Western)</option>
					<option value="YE">Yemen</option>
					<option value="YU">Serbia</option>
					<option value="ZA">Sud&aacute;frica</option>
					<option value="ZM">Zambia</option>
					<option value="ZR">Zaire</option>
					<option value="ZW">Zimbabwe</option>
				</select>
			</td>							
		<tr>	
	  	<tr>
			<td align="left" bgcolor="#c1c1c1" id="txt5" width="100%" colspan="2">   			
				<input type="button" id="ingresar" value="Ingresar" >
			</td>		
		</tr>
</table>
</body>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>	
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script>

	$("#cerrar").on("click",function(){
		
		window.parent.hidePopWin(true);		
	});
	
	$("#ingresar").on("click",function(){
		$("#dni_pas",window.parent.document).val($("#dni_pas").val());
		$("#nacionalidad",window.parent.document).val($("#nacionalidad").val());
		$("#email_inter",window.parent.document).val($("#email_inter").val());
		$("#nombre_internacional",window.parent.document).val($("#nombre_internacional").val());
		alert("Cliente ingresado exitosamente.");
		window.parent.hidePopWin(true);		
	});
	
</script>
</html>