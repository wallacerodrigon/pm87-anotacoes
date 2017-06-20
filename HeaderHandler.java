package br.org.oab.testews;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * Created by leonardo.segala on 29/03/2017.
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (outboundProperty.booleanValue()) {

			SOAPMessage message = smc.getMessage();

			try {

				SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();

				SOAPElement security =
						header.addChildElement("token", "int", "http://CFOAB.Integracao");
				// Homologação
				security.addTextNode(TesteCna.TOKEN);

				//Print out the outbound SOAP message to System.out
				message.writeTo(System.out);
				//System.out.println("");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {

				//This handler does nothing with the response from the Web Service so
				//we just print out the SOAP message.
				SOAPMessage message = smc.getMessage();
				//message.writeTo(System.out);
				//System.out.println("");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}


		return outboundProperty;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {

	}
}
