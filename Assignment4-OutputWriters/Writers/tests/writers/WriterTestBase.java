package writers;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

interface WriterTestBase {

  Writer createWriter();
  Writer createWriter(List<Function<String, String>> someFunctions) throws IOException;
  String getContents() throws IOException;

  @Test
  default void canary() {
    assertTrue(true);
  }

  @Test
    default void WriterTestWriteHello() throws IOException {
        Writer writer = createWriter();
        writer.write("Hello");
        writer.close();

        assertEquals("Hello", getContents());
    }

    @Test
    default void WriterTestWriteThere() throws IOException {
        Writer writer = createWriter();
        writer.write("There");
        writer.close();

        assertEquals("There", getContents());
    }

    @Test
    default void WriterTestWriteHelloThere() throws IOException {
        Writer writer = createWriter();
        writer.write("Hello");
        writer.write("There");
        writer.close();

        assertEquals("HelloThere", getContents());
    }

    @Test
    default void WriterTestWriteAfterClose() throws IOException {
        Writer writer = createWriter();
        writer.write("Hello");
        writer.close();
        writer.write("There");

        assertEquals("Hello", getContents());
    }
}
