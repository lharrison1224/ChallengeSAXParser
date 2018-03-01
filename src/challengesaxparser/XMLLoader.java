/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challengesaxparser;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author dale
 * http://www.saxproject.org/apidoc/org/xml/sax/helpers/DefaultHandler.html
 * https://docs.oracle.com/javase/8/docs/api/org/xml/sax/SAXException.html#SAXException--
 */
public class XMLLoader {
    static String text = "";
    static ArrayList<String> stack = new ArrayList<>();
    final static String spacing = "   ";
    
    public static String load(File xmlCourseFile) throws Exception {
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
               
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    for(int i = 0; i < stack.size(); i++){
                        text = text + spacing;
                    }
                    text = text + "<" + qName;
                    stack.add(qName);
                    
                    if(attributes != null && attributes.getLength() != 0){
                        text = text + " ";
                        for(int i = 0; i < attributes.getLength(); i++){
                            text = text + attributes.getQName(i) + "=";
                            text = text + attributes.getValue(i);
                        }
                    }
                    
                    text = text + ">\n";
                }
                
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(qName.equals(stack.get(stack.size()-1))){
                        stack.remove(stack.size()-1);
                    } //else {
//                        SAXException exception = new SAXException("Closing tag has no opening tag");
//                        throw exception;
//                    }
                    
                    for(int i = 0; i < stack.size(); i++){
                        text = text + spacing;
                    }
                    
                    text = text + "</" + qName + ">\n";
                }
                
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    String string = new String(ch, start, length);
                    string = string.replace("\n", "").replace("\r", "").replace("\t", "");
                    if(string != null && !string.equals("")){
                        for(int i = 0; i < stack.size(); i++){
                            text = text + spacing;
                        }
                        text = text + new String(ch, start, length) + "\n";
                    }
                        
                }
            };
            
            saxParser.parse(xmlCourseFile.getAbsoluteFile(), handler);
            
        } catch (Exception e) {
            throw e;
        }
        
      return text; 
    }
}
