package writers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class Writer {
  private boolean open = true;
  List<Function<String, String>> functions;

  Writer() { functions = new ArrayList<>(); }

  Writer(List<Function<String, String>> someFunctions) { functions = someFunctions; }

  public void close() { open = false; }

  private String applyTransformations (String text) {
    for (Function<String, String> transformer:functions) {
      text = transformer.apply(text);
    }
    return text;
  }

  public void write(String text) throws IOException {
    if (open) {
      String modifiedText = applyTransformations(text);
      writeContents(modifiedText);
    }
  }

  abstract protected void writeContents(String text) throws IOException;
}
