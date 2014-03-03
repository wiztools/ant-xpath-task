package org.wiztools.ant.xpath;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author subwiz
 */
public class JAXPXPathEvaluator implements XPathEvaluator {
    
    private static final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    private static final XPathFactory factory = XPathFactory.newInstance();
    private static final XPath xpath = factory.newXPath();
    /*static{
        domFactory.setNamespaceAware(true);
    }*/

    @Override
    public String[] evaluate(String xpathStr, Reader reader, Boolean isMultiNodeResult) throws XPathEvaluatorException {
        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(reader));
            if(isMultiNodeResult) {
                Object result = xpath.evaluate(xpathStr, doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                List<String> ll = new ArrayList<String>();
                for (int i = 0; i < nodes.getLength(); i++) {
                    ll.add(nodes.item(i).getNodeValue());
                }
                return ll.toArray(new String[]{});
            }
            else {
                Object result = xpath.evaluate(xpathStr, doc, XPathConstants.STRING);
                return new String[]{result.toString()};
            }
        }
        catch(ParserConfigurationException ex) {
            throw new XPathEvaluatorException(ex);
        }
        catch(IOException ex) {
            throw new XPathEvaluatorException(ex);
        }
        catch(SAXException ex) {
            throw new XPathEvaluatorException(ex);
        }
        catch(XPathExpressionException ex) {
            throw new XPathEvaluatorException(ex);
        }
    }
}
