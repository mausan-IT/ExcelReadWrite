
package cl.gmo.pos.venta.web.wscl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "Core", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/Core.asmx.wsdl")
public class Core
    extends Service
{

    private final static URL CORE_WSDL_LOCATION;
    private final static WebServiceException CORE_EXCEPTION;
    private final static QName CORE_QNAME = new QName("http://tempuri.org/", "Core");

    static {
            CORE_WSDL_LOCATION = cl.gmo.pos.venta.web.wscl.Core.class.getResource("/WEB-INF/wsdl/Core.asmx.wsdl");
        WebServiceException e = null;
        if (CORE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find 'WEB-INF/wsdl/Core.asmx.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        CORE_EXCEPTION = e;
    }

    public Core() {
        super(__getWsdlLocation(), CORE_QNAME);
    }

    public Core(WebServiceFeature... features) {
        super(__getWsdlLocation(), CORE_QNAME, features);
    }

    public Core(URL wsdlLocation) {
        super(wsdlLocation, CORE_QNAME);
    }

    public Core(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CORE_QNAME, features);
    }

    public Core(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Core(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CoreSoap
     */
    @WebEndpoint(name = "CoreSoap")
    public CoreSoap getCoreSoap() {
        return super.getPort(new QName("http://tempuri.org/", "CoreSoap"), CoreSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CoreSoap
     */
    @WebEndpoint(name = "CoreSoap")
    public CoreSoap getCoreSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CoreSoap"), CoreSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns CoreSoap
     */
    @WebEndpoint(name = "CoreSoap12")
    public CoreSoap getCoreSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "CoreSoap12"), CoreSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CoreSoap
     */
    @WebEndpoint(name = "CoreSoap12")
    public CoreSoap getCoreSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CoreSoap12"), CoreSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CORE_EXCEPTION!= null) {
            throw CORE_EXCEPTION;
        }
        return CORE_WSDL_LOCATION;
    }

}
