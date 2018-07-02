package utils.text_extractor;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import model.Form;
import model.FormWithText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class TextExtractorThreadPool {

  private ImageAnnotatorClient visionClient;
  private List<Callable<FormWithText>> callables;
  private ExecutorService executor;

  public TextExtractorThreadPool(ImageAnnotatorClient visionClient, List<Pair<Form, AnnotateImageRequest>> requests) {
    this.visionClient = visionClient;
    this.callables = new ArrayList<>(requests.size());
    initCallables(requests);
  }

  private void initCallables(List<Pair<Form, AnnotateImageRequest>> requests) {
    for (Pair<Form, AnnotateImageRequest> pair : requests) {
      Callable<FormWithText> callable = () -> {
        try {
          if (pair.getSecondValue() == null)
            return new FormWithText(pair.getFirstValue(), null);

          BatchAnnotateImagesResponse response = visionClient.batchAnnotateImages(Collections.singletonList(pair.getSecondValue()));
          List<AnnotateImageResponse> responsesList = response.getResponsesList();

          if (responsesList.isEmpty())
            return new FormWithText(pair.getFirstValue(), null);

          AnnotateImageResponse res = responsesList.get(0);
          if (res.hasError())
            return new FormWithText(pair.getFirstValue(), null);

          String text = res.getFullTextAnnotation().getText();
          if (text.endsWith("\n"))
            text = text.substring(0, text.length() - 1);
          return new FormWithText(pair.getFirstValue(), text);
        } catch (Exception e) {
          return null;
        }
      };
      callables.add(callable);
    }
  }

  public List<Future<FormWithText>> start() throws Exception {
    if (callables == null || callables.isEmpty())
      throw new Exception("No callable defined");
    executor = Executors.newFixedThreadPool(callables.size());
    return executor.invokeAll(callables);
  }

  public void stop() {
    if (executor == null || executor.isShutdown())
      return;
    executor.shutdownNow();
  }
}
