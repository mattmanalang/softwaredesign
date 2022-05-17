package writers;

import java.util.List;
import java.util.function.Function;

public class StringWriter extends Writer {
  private StringBuilder contents = new StringBuilder();

  StringWriter() { super(); }

  StringWriter(List<Function<String, String>> someFunctions) {
    super(someFunctions);
  }

  public void writeContents(String text) {
    contents.append(text);  
  }

  public String getContents() { return contents.toString(); }
}
