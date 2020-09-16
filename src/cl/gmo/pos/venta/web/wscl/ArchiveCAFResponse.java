
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
 *         &lt;element name="ArchiveCAFResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "archiveCAFResult"
})
@XmlRootElement(name = "ArchiveCAFResponse")
public class ArchiveCAFResponse {

    @XmlElement(name = "ArchiveCAFResult")
    protected String archiveCAFResult;

    /**
     * Obtiene el valor de la propiedad archiveCAFResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchiveCAFResult() {
        return archiveCAFResult;
    }

    /**
     * Define el valor de la propiedad archiveCAFResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchiveCAFResult(String value) {
        this.archiveCAFResult = value;
    }

}
