package writers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;

public class FileWriterTest implements WriterAddOnsTest {
  String fileName = "fileWriterOut";

  @BeforeEach
  public void init() throws IOException {
    Files.deleteIfExists(Paths.get(fileName));
  }

  @Override
  public Writer createWriter(){
    return new FileWriter(fileName);
  }

  @Override
  public Writer createWriter(List<Function<String, String>> someFunctions) {
    return new FileWriter(fileName, someFunctions);
  }

  @Override
  public String getContents() throws IOException {
    Path filePath = Paths.get("./".concat(fileName));
    return Files.readString(filePath);
  }
}
