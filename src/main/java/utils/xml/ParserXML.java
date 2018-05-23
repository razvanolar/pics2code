package utils.xml;

import model.Form;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class ParserXML {

  private SAXParser parser;

  public ParserXML() throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    parser = factory.newSAXParser();
  }

  public List<Form> parse(File xmlFile) throws Exception {
    ParserHandler handler = new ParserHandler();
    parser.parse(xmlFile, handler);
    return handler.getForms();
  }
}
