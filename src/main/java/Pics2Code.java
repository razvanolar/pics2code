import model.Form;
import model.FormWithText;
import model.Row;
import utils.*;
import utils.text_extractor.TextExtractor;
import utils.xml.ParserXML;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.List;

public class Pics2Code {

  public static void main(String[] args) {
    try {
      consoleMessage("Load project settings");
      ProjectUtils project = new ProjectUtils();

      consoleMessage("Check parameters");
      ParametersHandler paramHandler = new ParametersHandler(args);
      paramHandler.parseParameters();

      consoleMessage("Read image");
      BufferedImage image = ImageIO.read(paramHandler.getInputImage());

      consoleMessage("Parse XML");
      ParserXML parser = new ParserXML();
      List<Form> forms = parser.parse(paramHandler.getInputXML());
      consoleMessage("Compute bias values");
      forms = FormUtils.computeBias(forms, image.getWidth(), image.getHeight());

      consoleMessage("Extract text");
      TextExtractor textExtractor = new TextExtractor();
      List<FormWithText> formsWithText = textExtractor.extract(image, forms);

      // Skip divider operations for a while
      Divider divider = new Divider();
      List<Row> rows = divider.getRows(formsWithText);
      //

      consoleMessage("Generate code");
      String code = CodeGeneratorUtils.generate(formsWithText);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(code), null);

      consoleMessage("Create Android project");
      project.createAndroidProject(paramHandler.getOutDirectory());
      project.writeContentXml(paramHandler.getOutDirectory(), code);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void consoleMessage(String msg) {
    System.out.println(String.format("# %s", msg));
  }
}
