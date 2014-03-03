package org.wiztools.ant.xpath;

import java.io.Reader;

public interface XPathEvaluator {
   
   String[] evaluate(String xpath, Reader reader, Boolean multiNode) throws XPathEvaluatorException;
   
}
