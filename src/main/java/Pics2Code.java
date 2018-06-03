import model.Form;
import model.FormWithText;
import model.Row;
import utils.CodeGeneratorUtils;
import utils.Divider;
import utils.FormUtils;
import utils.ParametersHandler;
import utils.text_extractor.TextExtractor;
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
      forms = FormUtils.computeBias(forms, image.getWidth(), image.getHeight());

      TextExtractor textExtractor = new TextExtractor();
      List<FormWithText> formsWithText = textExtractor.extract(image, forms);

      // Skip divider operations for a while
      Divider divider = new Divider();
      List<Row> rows = divider.getRows(formsWithText);
      //

      String code = CodeGeneratorUtils.generate(formsWithText);
      System.out.println(code);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
