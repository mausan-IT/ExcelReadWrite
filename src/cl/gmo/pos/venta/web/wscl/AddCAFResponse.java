
package cl.gmo.pos.venta.web.wscl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddCAFResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addCAFResult"
})
@XmlRootElement(name = "AddCAFResponse")
public class AddCAFResponse {

    @XmlElement(name = "AddCAFResult")
    protected String addCAFResult;

    /**
     * Obtiene el valor de la propiedad addCAFResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddCAFResult() {
        return addCAFResult;
    }

    /**
     * Define el valor de la propiedad addCAFResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddCAFResult(String value) {
        this.addCAFResult = value;
    }

}
