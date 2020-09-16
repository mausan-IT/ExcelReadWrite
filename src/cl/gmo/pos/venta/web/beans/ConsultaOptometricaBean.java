package cl.gmo.pos.venta.web.beans;


import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;

public class ConsultaOptometricaBean {

	private GraduacionesBean graduacion;
	private String derivacion;
	private boolean clienteFirmo = false;
	private boolean filtroAzul = false;
	private boolean antireflejo = false;
	private boolean bifocal = false;
	private boolean progresivos = false;
	private boolean fotosensible = false;
	private ClienteBean cliente;
	private int numCodigo;
	
	//campos AVSR (AVSC), AVCC, AVCCL de Tipo Texto para la Ficha, en GraduacionesBean los campos son tipo Double.
	//para no cambiar el funcionamiento de graduaciones se incorporan estos campos en este bean.
	
	private String strOD_avsc;
	private String strOD_avcc;
	private String strOD_avcl;
	private String strOI_avsc;
	private String strOI_avcc;
	private String strOI_avcl;
	
	
	
	public GraduacionesBean getGraduacion(){
		return graduacion;
	}
		
	public void setGraduacion(GraduacionesBean oGraduacion) {
		this.graduacion = oGraduacion;
	}
	
	public ClienteBean getCliente(){
		return cliente;
	}
		
	public void setCliente(ClienteBean oCliente) {
		this.cliente = oCliente;
	}
	
	
	public String getDerivacion() {
		return derivacion;
	}
	public void setDerivacion(String sDerivacion) {
		this.derivacion = sDerivacion;
	}	
	
	public boolean getClienteFirmo() {
		return clienteFirmo;
	}
	
	public void setClienteFirmo(boolean bClienteFirmo) {
		this.clienteFirmo = bClienteFirmo;
	}
	
	public boolean getFiltroAzul() {
		return filtroAzul;
	}
	
	public void setFiltroAzul(boolean bFiltroAzul) {
		this.filtroAzul = bFiltroAzul;
	}
	
	public boolean getAntireflejo() {
		return antireflejo;
	}
	
	public void setAntireflejo(boolean bAntireflejo) {
		this.antireflejo = bAntireflejo;
	}
	
	public boolean getBifocal() {
		return bifocal;
	}
	
	public void setBifocal(boolean bBifocal) {
		this.bifocal = bBifocal;
	}
	
	public boolean getProgresivos() {
		return progresivos;
	}
	
	public void setProgresivos(boolean bProgresivos) {
		this.progresivos = bProgresivos;
	}
	
	public boolean getFotosensible() {
		return fotosensible;
	}
	
	public void setFotosensible(boolean bFotosensible) {
		this.fotosensible = bFotosensible;
	}
	
	/*** Datos del Cliente para Comprobante ***/
	
	public String getNombreCliente() {
		return cliente.getNombre();
	}
	
	public void setNombreCliente(String sNombreCli) {
		this.cliente.setNombre(sNombreCli);
	}
	
	public String getApellidoCliente() {
		return cliente.getApellido();
	}
	
	public void setApellidoCliente(String sApellidoCli) {
		this.cliente.setApellido(sApellidoCli);
	}
	
	public String getNifCliente() {
		return cliente.getNif();
	}
	
	public void setNifCliente(String sNifCli) {
		this.cliente.setNif(sNifCli);
	}
	
	public String getDivNifCliente() {
		return cliente.getDvnif();
	}
	
	public void setDivNifCliente(String sNifCli) {
		this.cliente.setDvnif(sNifCli);
	}
	
	public String getTelfCliente() {
		return cliente.getFono_movil();
	}
	
	public void setTelfCliente(String sTelfCli) {
		this.cliente.setFono_movil(sTelfCli);
	}
	
	public int getNumCodigo() {
		return this.numCodigo;
	}
	
	public void setNumCodigo(int iNumCod) {
		this.numCodigo = iNumCod;
	}
	
	//Nuevos campos tipo Texto
	
	//str OD_AVSC
	public String getStrOD_avsc() {
		return strOD_avsc;
	}
	
	public void setStrOD_avsc(String oStrOD_avsc) {
		strOD_avsc = oStrOD_avsc;
	}
	
	//str OD_AVCC
	public String getStrOD_avcc() {
		return strOD_avcc;
	}
	
	public void setStrOD_avcc(String oStrOD_avcc) {
		strOD_avcc = oStrOD_avcc;
	}
	
	//str OD_AVCL
	public String getStrOD_avcl() {
		return strOD_avcl;
	}
	
	public void setStrOD_avcl(String oStrOD_avcl) {
		strOD_avcl = oStrOD_avcl;
	}
	
	
	//str OI_AVSC
	public String getStrOI_avsc() {
		return strOI_avsc;
	}
	
	public void setStrOI_avsc(String oStrOI_avsc) {
		strOI_avsc = oStrOI_avsc;
	}
	
	//str OI_AVCC
	public String getStrOI_avcc() {
		return strOI_avcc;
	}
	
	public void setStrOI_avcc(String oStrOI_avcc) {
		strOI_avcc = oStrOI_avcc;
	}
	
	//str OI_AVCL
	public String getStrOI_avcl() {
		return strOI_avcl;
	}
	
	public void setStrOI_avcl(String oStrOI_avcl) {
		strOI_avcl = oStrOI_avcl;
	}
	
	
}
