package utils;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import model.Box;
import model.Form;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextExtractor {

  public void extract(BufferedImage inputImage, List<Form> forms) {
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      List<AnnotateImageRequest> requests = computeRequests(inputImage, forms);

      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responsesList = response.getResponsesList();

      for (AnnotateImageResponse res : responsesList) {
        if (res.hasError()) {
          System.out.println(res.getError().getMessage());
          return;
        }

        for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
//          annotation.getAllFields().forEach((k, v) -> {
//            System.out.printf("%s : %s\n", k, v.toString());
//          });
          System.out.println(annotation.getDescription());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<AnnotateImageRequest> computeRequests(BufferedImage inputImage, List<Form> forms) throws Exception {
    List<AnnotateImageRequest> requests = new ArrayList<>();

    for (Form form : forms) {
      if (!form.getLabel().hasText())
        continue;

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
      requests.add(request);
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
