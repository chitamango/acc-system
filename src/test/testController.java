package test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;

import com.inter.event.EventDispatcher;
import com.inter.event.EventListenerImpl;
import com.inter.event.MessageEvent;
import com.inter.listener.MessageEventListener;
import com.inter.manager.SharedArtifactManager;
import com.inter.rule.AccRuleImpl;





public class testController {

	/**
	 * @param args
	 */
	
	public static MessageEventListener listener;
	  public static EventDispatcher eDispatcher;
	  public static AccRuleImpl ruleEngine;
	  public static SharedArtifactManager sharedArtifactManager;
	
	public static void main(String[] args) throws AxisFault, MalformedURLException {
		// TODO Auto-generated method stub

		
		listener = new EventListenerImpl();
        
    	eDispatcher = new EventDispatcher();
    	
    	ruleEngine = new AccRuleImpl();
    	
    	sharedArtifactManager = new SharedArtifactManager();
    	
    	listener.setSharedArtifactManager(sharedArtifactManager);
    	
    	listener.setRuleEngine(ruleEngine);
    	
    	
    	
    	
    	//SharedArtifactClientManager should create and set RuleEnigne and Process controller here
    	
    	eDispatcher.addEventListener(listener);
    	
    	eDispatcher.setSharedArtifactManager(sharedArtifactManager);
    	
		
		
    	OMFactory fac = OMAbstractFactory.getOMFactory();
    	
    	
    	//payload
    	
    	OMNamespace omNs = fac.createOMNamespace("http://test.purchase", "");
    	OMElement method = fac.createOMElement("makePurchase", omNs);
		OMElement customername = fac.createOMElement("customername", omNs);
		OMElement customeraddress = fac.createOMElement("customeraddress", omNs);
		customername.setText("john smith");
		customeraddress.setText("1/24 Belmont ave");
		method.addChild(customername);
		method.addChild(customeraddress);
		
		URL url = new URL("http://localhost:8000/axis2/services/PurchaseService?wsdl");	
		
		QName serviceName = new QName("http://test.purchase", "PurchaseService");
		
		QName operationName = new QName("http://test.purchase", "makePurchase");
		
		ServiceClient client = new ServiceClient(null, url, serviceName,"PurchaseServiceHttpSoap11Endpoint");
		
		System.out.println(client.sendReceive(operationName, method));
	
	
	
	//	eDispatcher.eventReceived(method, method);
		
		//	MessageEvent me = new MessageEvent(testController.class, method);
		
		//	System.out.println(me.getValueFromPart("customername"));
    	
		
	//	ruleEngine.executeRules(Message, sharedArtifactmanager);
		
	 	OMNamespace omNs1 = fac.createOMNamespace("http://online.test", "");
		OMElement method1 = fac.createOMElement("purchaseOrder", omNs1);
		OMElement item = fac.createOMElement("item", omNs1);
		OMElement quantity = fac.createOMElement("quantity", omNs1);
		item.setText("Iphone5");
		quantity.setText("1");
		method1.addChild(item);
		method1.addChild(quantity); 
		
		eDispatcher.eventReceived(method1, method1);
		
		
	}

}
