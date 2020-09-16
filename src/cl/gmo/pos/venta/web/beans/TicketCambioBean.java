package cl.gmo.pos.venta.web.beans;

public class TicketCambioBean {

	String boleta 	="";
	String encargo 	="";
	String fecha 	="";
	String mensaje 	="";
	
	
	public String getBoleta() {
		return boleta;
	}
	public void setBoleta(String boleta) {
		this.boleta = boleta;
	}
	public String getEncargo() {
		return encargo;
	}
	public void setEncargo(String encargo) {
		this.encargo = encargo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
