package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.GraduacionesBean;

public interface ConsultaOptometricaDAO {
	
	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception;
	

}
