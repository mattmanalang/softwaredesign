package writers;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public interface WriterTestCore extends WriterTestBase{
  // NEEDED TO BE REMOVED.

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
