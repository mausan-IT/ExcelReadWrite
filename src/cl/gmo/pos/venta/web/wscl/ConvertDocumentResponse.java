
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
 *         &lt;element name="ConvertDocumentResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "convertDocumentResult"
})
@XmlRootElement(name = "ConvertDocumentResponse")
public class ConvertDocumentResponse {

    @XmlElement(name = "ConvertDocumentResult")
    protected String convertDocumentResult;

    /**
     * Obtiene el valor de la propiedad convertDocumentResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvertDocumentResult() {
        return convertDocumentResult;
    }

    /**
     * Define el valor de la propiedad convertDocumentResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvertDocumentResult(String value) {
        this.convertDocumentResult = value;
    }

}
