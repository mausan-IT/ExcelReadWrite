
package cl.gmo.pos.venta.web.wscl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "CoreSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CoreSoap {


    /**
     * 
     * @param area
     * @param password
     * @param documentType
     * @param documentContent
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ConvertDocument", action = "http://tempuri.org/ConvertDocument")
    @WebResult(name = "ConvertDocumentResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "ConvertDocument", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ConvertDocument")
    @ResponseWrapper(localName = "ConvertDocumentResponse", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ConvertDocumentResponse")
    public String convertDocument(
        @WebParam(name = "Area", targetNamespace = "http://tempuri.org/")
        String area,
        @WebParam(name = "Password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "DocumentType", targetNamespace = "http://tempuri.org/")
        String documentType,
        @WebParam(name = "DocumentContent", targetNamespace = "http://tempuri.org/")
        String documentContent);

    /**
     * 
     * @param area
     * @param password
     * @param documentType
     * @param documentContent
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ConvertSignDocument", action = "http://tempuri.org/ConvertSignDocument")
    @WebResult(name = "ConvertSignDocumentResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "ConvertSignDocument", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ConvertSignDocument")
    @ResponseWrapper(localName = "ConvertSignDocumentResponse", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ConvertSignDocumentResponse")
    public String convertSignDocument(
        @WebParam(name = "Area", targetNamespace = "http://tempuri.org/")
        String area,
        @WebParam(name = "Password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "DocumentType", targetNamespace = "http://tempuri.org/")
        String documentType,
        @WebParam(name = "DocumentContent", targetNamespace = "http://tempuri.org/")
        String documentContent);

    /**
     * 
     * @param area
     * @param password
     * @param fileName
     * @param caf
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "AddCAF", action = "http://tempuri.org/AddCAF")
    @WebResult(name = "AddCAFResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "AddCAF", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.AddCAF")
    @ResponseWrapper(localName = "AddCAFResponse", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.AddCAFResponse")
    public String addCAF(
        @WebParam(name = "Area", targetNamespace = "http://tempuri.org/")
        String area,
        @WebParam(name = "Password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "CAF", targetNamespace = "http://tempuri.org/")
        String caf,
        @WebParam(name = "FileName", targetNamespace = "http://tempuri.org/")
        String fileName);

    /**
     * 
     * @param area
     * @param password
     * @param fileName
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "RemoveCAF", action = "http://tempuri.org/RemoveCAF")
    @WebResult(name = "RemoveCAFResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "RemoveCAF", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.RemoveCAF")
    @ResponseWrapper(localName = "RemoveCAFResponse", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.RemoveCAFResponse")
    public String removeCAF(
        @WebParam(name = "Area", targetNamespace = "http://tempuri.org/")
        String area,
        @WebParam(name = "Password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "FileName", targetNamespace = "http://tempuri.org/")
        String fileName);

    /**
     * 
     * @param area
     * @param password
     * @param fileName
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ArchiveCAF", action = "http://tempuri.org/ArchiveCAF")
    @WebResult(name = "ArchiveCAFResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "ArchiveCAF", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ArchiveCAF")
    @ResponseWrapper(localName = "ArchiveCAFResponse", targetNamespace = "http://tempuri.org/", className = "cl.gmo.pos.venta.web.wscl.ArchiveCAFResponse")
    public String archiveCAF(
        @WebParam(name = "Area", targetNamespace = "http://tempuri.org/")
        String area,
        @WebParam(name = "Password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "FileName", targetNamespace = "http://tempuri.org/")
        String fileName);

}
