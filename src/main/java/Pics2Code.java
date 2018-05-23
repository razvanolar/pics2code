import model.Form;
import model.Row;
import utils.Divider;
import utils.ParametersHandler;
import utils.TextExtractor;
import utils.xml.ParserXML;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

public class Pics2Code {

  public static void main(String[] args) {
    try {
      ParametersHandler paramHandler = new ParametersHandler(args);
      paramHandler.parseParameters();
      System.out.println(paramHandler.getOutDirectory().getAbsolutePath());

      BufferedImage image = ImageIO.read(paramHandler.getInputImage());

      ParserXML parser = new ParserXML();
      List<Form> forms = parser.parse(paramHandler.getInputXML());

      TextExtractor textExtractor = new TextExtractor();
      textExtractor.extract(image, forms);

      Divider divider = new Divider();
      List<Row> rows = divider.getRows(forms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
