package writers;

import java.util.List;
import java.util.function.Function;

public class StringWriterTest implements WriterAddOnsTest {
  StringWriter writer;
  @Override
  public Writer createWriter(){
    writer = new StringWriter();
    return writer;
  }

  @Override
  public Writer createWriter(List<Function<String, String>> someFunctions) {
    writer = new StringWriter(someFunctions);
    return writer;
  }

  @Override
  public String getContents() {
    return writer.getContents();
  }
}
