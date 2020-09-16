/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.facade;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ProductoExentoDAOImpl;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
public class PosProductoExentoFacade {
	
	
	
	/* Obteniendo Producto Exento */
	public static ProductosBean obtenerProductoExento(String prodCodBarra, String local)
    {
		
		ProductosBean prod = new ProductosBean();
        try {
            ProductoExentoDAOImpl prodExentoImpl = new ProductoExentoDAOImpl();
    		
            prod = prodExentoImpl.obtenerProdExentoTienda(prodCodBarra,local);
            
            
        } catch (Exception e) {
        }    
        return prod;
    }
    
	public static boolean esProductoExentoLocal(String prodCodBarra, String local)
    {
		
		ProductosBean prod = new ProductosBean();
		boolean resp = false;
		
		prod = obtenerProductoExento(prodCodBarra,local);
		 
		
		if(null!=prod.getCod_barra()) {
			
			if(prod.getCod_barra().equals(prodCodBarra)) {
				resp = true;
			}		
		}
        
        return resp;
    }
	
	public static int verificarProductoExento(String prodCodBarra, String local) {
		
		ProductoExentoDAOImpl prodExentoImpl = new ProductoExentoDAOImpl();
		int resp = 0;
		
		try {
			 resp = prodExentoImpl.verificarProdExentoTienda(prodCodBarra, local);
		} catch (Exception e) {
	            
	    }
		 
		 return resp;
	}
	
	public static boolean esProdExento(String prodCodBarra, String local) {
		boolean bEsExento = false;
		int resp = 0;
		
		resp = verificarProductoExento(prodCodBarra,local);
		
		if ((resp == 1)||(resp == 2)) {
			System.out.println("CMRO esProductoExento");
			bEsExento = true;
		}
		return bEsExento;
	}
	
	/* Obteniendo Dcto Máximo de Producto Exento */
	public static double obtenerDctoMaxProdExento(String prodCodBarra, String local)
    {
		double vDtoMaxE = 0;
		
		ProductosBean prod = new ProductosBean();
       
        prod = obtenerProductoExento(prodCodBarra,local);
        vDtoMaxE = prod.getDtoMaxExento();
            
        return vDtoMaxE;
    }
		
}
