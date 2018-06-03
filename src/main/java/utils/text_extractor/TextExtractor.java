package utils.text_extractor;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import model.Box;
import model.Form;
import model.FormWithText;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class TextExtractor {

  public List<FormWithText> extract(BufferedImage inputImage, List<Form> forms) {
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      List<Pair<Form, AnnotateImageRequest>> requests = computeRequests(inputImage, forms);
      TextExtractorThreadPool extractor = new TextExtractorThreadPool(vision, requests);
      List<Future<FormWithText>> futures = extractor.start();

      List<FormWithText> result = new ArrayList<>();

      for (Future<FormWithText> future : futures) {
        FormWithText formWithText = future.get();
        result.add(formWithText);
      }

      extractor.stop();

      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private List<Pair<Form, AnnotateImageRequest>> computeRequests(BufferedImage inputImage, List<Form> forms) throws Exception {
    List<Pair<Form, AnnotateImageRequest>> requests = new ArrayList<>();

    for (Form form : forms) {
      if (!form.getLabel().hasText()) {
        requests.add(new Pair<>(form, null));
        continue;
      }

      Box box = form.getBox();
      int xmin = (int) box.getXmin();
      int xmax = (int) box.getXmax();
      int ymin = (int) box.getYmin();
      int ymax = (int) box.getYmax();

      BufferedImage img = inputImage.getSubimage(xmin, ymin, xmax - xmin, ymax - ymin);

      byte[] bytes = convertToBytes(img);
      ByteString imgBytes = ByteString.copyFrom(bytes);
      Image visionImage = Image.newBuilder().setContent(imgBytes).build();

      Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
      AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat)
          .setImage(visionImage)
          .build();
      requests.add(new Pair<>(form, request));
    }

    return requests;
  }

  private static byte[] convertToBytes(BufferedImage image) throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(image, "png", out);
    out.flush();
    byte[] bytes = out.toByteArray();
    out.close();
    return bytes;
  }

  private static BufferedImage convertToImage(byte[] bytes) throws Exception {
    InputStream in = new ByteArrayInputStream(bytes);
    BufferedImage image = ImageIO.read(in);
    in.close();
    return image;
  }
}
