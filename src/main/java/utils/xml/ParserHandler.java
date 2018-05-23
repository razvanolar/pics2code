package utils.xml;

import model.Box;
import model.Form;
import model.labels.Label;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class ParserHandler extends DefaultHandler {

  private static String LABELS_TAG = "labels";
  private static String LABEL_TAG = "label";
  private static String BOX_TAG = "box";

  private static String NAME_PROP = "name";
  private static String X_MIN_PROP = "xmin";
  private static String Y_MIN_PROP = "ymin";
  private static String X_MAX_PROP = "xmax";
  private static String Y_MAX_PROP = "ymax";

  private List<Form> forms;
  private Form form;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase(LABELS_TAG)) {
      forms = new ArrayList<>();
    } else if (qName.equalsIgnoreCase(LABEL_TAG)) {
      Label label = Label.fromString(attributes.getValue(NAME_PROP));
      if (label == null)
        throw new SAXException("Unable to determine label name");
      form = new Form();
      form.setLabel(label);
    } else if (qName.equalsIgnoreCase(BOX_TAG)) {
      String xminValue = attributes.getValue(X_MIN_PROP);
      String yminValue = attributes.getValue(Y_MIN_PROP);
      String xmaxValue = attributes.getValue(X_MAX_PROP);
      String ymaxValue = attributes.getValue(Y_MAX_PROP);
      if (!Validator.isFloat(xminValue) || !Validator.isFloat(yminValue) || !Validator.isFloat(xmaxValue) || !Validator.isFloat(ymaxValue)) {
        throw new SAXException(String.format(
            "Unable to determine box coordinates for xmin: %s, ymin: %s, xmax: %s, ymax: %s",
            xminValue, yminValue, xmaxValue, ymaxValue));
      }
      Box box = new Box(
          Float.parseFloat(xminValue),
          Float.parseFloat(yminValue),
          Float.parseFloat(xmaxValue),
          Float.parseFloat(ymaxValue));
      form.setBox(box);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equalsIgnoreCase(LABEL_TAG)) {
      if (!Validator.isValidForm(form)) {
        throw new SAXException("Incorrect form. XML file may be corrupted.");
      }
      forms.add(form);
    }
  }

  public List<Form> getForms() {
    return forms;
  }
}
