<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/subModal.css" />
<link rel="stylesheet" type="text/css" href="css/formatosFormularios.css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/subModal.js"></script>				
<title>Validador Encargo</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<table width="280" height="25" cellspacing="0" align="center"> 
	<tr>
		<td colspan="1" bgcolor="#373737" id="txt4">Ingresa  Pedido relacionado al Motivo</td>
		<td colspan="1" bgcolor="#373737" id="txt4" align="right">
			<a id="cerrar" href="#" > 
				<img src="images/cancel.png" width="14" height="14" border="0"> 
			</a>
		</td>		
	</tr>
</table>
<table width="280" height="65" cellspacing="0" align="center""> 
	<tr class="txt">
		<td id="txt5" bgcolor="#c1c1c1" align="left">
			 <p>Encargo :</p>
		</td>
		<td id="txt5" bgcolor="#c1c1c1" align="left">
			<input type="text" name="encargo" value="" Class="fto" size="15" id="encargo" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" />
		</td>

	</tr>		
	<tr>
		<td id="txt5" bgcolor="#c1c1c1" align="right" colspan="2">
			<a href="#" onclick="" id="enviar"> 
				<img src="images/check.png" width="15" height="15" border="0" title="Validar" />
			</a>
		</td>
	</tr>

</table>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script>
  //PLACEHOLDER 
  $(document).ready(function(){
	  function add() {
	    if($(this).val() === ''){
	      $(this).val($(this).attr('placeholder')).addClass('placeholder');
	    }
	  }

	  function remove() {
	    if($(this).val() === $(this).attr('placeholder')){
	      $(this).val('').removeClass('placeholder');
	    }
	  }

	  // Create a dummy element for feature detection
	  if (!('placeholder' in $('<input>')[0])) {

	    // Select the elements that have a placeholder attribute
	    $('input[placeholder], textarea[placeholder]').blur(add).focus(remove).each(add);

	    // Remove the placeholder text before the form is submitted
	    $('form').submit(function(){
	      $(this).find('input[placeholder], textarea[placeholder]').each(remove);
	    });
	  }
	});

	$("#enviar").on("click",function(){
  		
	  			$.ajax({
					  type: "POST",
					  url: "<%=request.getContextPath()%>/SeleccionPago.do?method=validacion_encargo_fpago",
					  dataType: "text",
					  data:"encargo_rel="+$("#encargo").val()+"&serie="+$("#serie",window.parent.document).val()+"&motivo="+$("#tipo_pedido",window.parent.parent.document).val(),
					  asycn:false,
					  success: function(data){
						  switch(parseInt(data)) {
						  
						  		case 1:
									 $("#encargo_padre",window.parent.parent.document).val($("#encargo").val());
									 window.parent.hidePopWin(true);
									 
							  	break;
						  		case 2:
						  			 alert ("El encargo asociado ya fue ocupado,\n no es posible volver a utilizarlo.");
						  			$("#forma_pago",window.parent.document).val("0");
							  	break;
						  		case 3:
						  			 alert ("El encargo asociado no esta entregado.");
						  			$("#forma_pago",window.parent.document).val("0");
							  	break;
						  	
						  		case 4:
						  			alert("El encargo asociado no es valido (Fecha no corresponde al rango permito para este Motivo)");
								  	$("#forma_pago",window.parent.document).val("0");
							  	break;
						  		case 5:
						  			alert("El Cliente del encargo asociado "+$("#encargo").val()+"\n no corresponde al cliente del encargo "+$("#serie",window.parent.document).val());
								  	$("#forma_pago",window.parent.document).val("0");
							  	break;
						  		case 6:
						  			alert("El encargo asociado "+$("#encargo").val()+" no existe");
								  	$("#forma_pago",window.parent.document).val("0");
							  	break;
							  	default:
							  		alert("Problema al asociar encargo (err nc)");
							  		$("#forma_pago",window.parent.document).val("0");
							  	break;
						  }
					
				 	  }
			 	});
	});	
	
	$("#cerrar").on("click",function(){
		if($("#rut_vs",window.parent.document).val() == "" || $("#rut_vs",window.parent.document).val() == null){
			$("#forma_pago",window.parent.document).val("0");
			window.parent.hidePopWin(true);		
		}else{
			window.parent.hidePopWin(true);	
		}
	});
	
	$(".num").on('keypress',function(e){  k = (document.all) ? e.keyCode : e.which;if (k==8 || k==0 ) return true;patron = /[0-9]/;n = String.fromCharCode(k);return patron.test(n);}); 

</script>
</body>
</html>