function validar(){
	if(form1.nombreCajero.value==0||form1.numero_caja.value==0){
		alert("Los campos N° Caja y Cajero son requeridos");
		return false;
	}else{
	 return true;
	}
}