package writers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Function;

public class FileWriter extends Writer {
  private final String fileName;

  FileWriter(String fileName) {
     super();
    this.fileName = fileName;
  }

  FileWriter(String fileName, List<Function<String, String>> someFunctions){
    super(someFunctions);
    this.fileName = fileName;
  }

  public void writeContents(String text) throws IOException {
    Files.writeString(Paths.get(fileName), text, StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
  }
}
