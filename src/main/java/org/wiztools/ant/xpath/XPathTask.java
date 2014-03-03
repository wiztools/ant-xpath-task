package org.wiztools.ant.xpath;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class XPathTask extends Task {

   private String outputProperty = "output";
   private String document;
   private String xpath;
   private XPathEvaluator evaluator;
   private String delimiter = " ";
   private Boolean multiNodeResult = Boolean.FALSE;
   
   public XPathTask() {
      this(new JAXPXPathEvaluator());
   }

   public XPathTask(XPathEvaluator evaluator) {
      this.evaluator = evaluator;
   }

   @Override
   public void execute() throws BuildException {
      checkWeSet(xpath, "xpath");
      checkWeSet(document, "document");
      try {
         setProperty(outputProperty, collect(evaluator.evaluate(xpath, new FileReader(document), multiNodeResult)));
      } catch (XPathEvaluatorException e) {
         throw new BuildException(e);
      } catch (FileNotFoundException e) {
         throw new BuildException("could not find file "+document, e);
      } 
   }

   private String collect(String[] values) {
      String result = "";
      for (String value: values) {
         result += value + delimiter;
      }
      result = result.substring(0, result.length() - delimiter.length());
      return result;
   }

   private void checkWeSet(String argument, String name) {
      if (argument == null) {
         throw new BuildException(name+" is required but has not been set");
      }
   }

   private void setProperty(String name, String value) {
      getProject().setProperty(name, value);
   }
   
   public void setOutputProperty(String outputProperty) {
      log("Setting the output property: " + outputProperty, Project.MSG_VERBOSE);
      this.outputProperty = outputProperty;
   }
   
   public void setDocument(String document) {
      log("evaluating in document: " + document, Project.MSG_VERBOSE);
      this.document = document;
   }
   
   public void setXpath(String xpath) {
      log("evaluating xpath: " + xpath, Project.MSG_VERBOSE);
      this.xpath = xpath;
   }
   
   public void setMultiNodeResult(Boolean result) {
       log("multi-node xpath result: " + result, Project.MSG_VERBOSE);
       this.multiNodeResult = result;
   }

   public void setDelimiter(String delimiter) {
       log("xpath multi-result delimiter: " + delimiter, Project.MSG_VERBOSE);
       this.delimiter = delimiter;
   }
}
