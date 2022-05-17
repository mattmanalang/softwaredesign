package writers;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface WriterAddOnsTest extends WriterTestBase {

    @Test
    default void WriterTestUpperCase() throws IOException {
        Writer writer = createWriter(
                List.of(String::toUpperCase)
        );
        writer.write("Hello");
        writer.close();

        assertEquals("HELLO", getContents());
    }

    @Test
    default void WriterTestLowerCase() throws IOException {
        Writer writer = createWriter(
                List.of(String::toLowerCase)
        );
        writer.write("Hello");
        writer.close();

        assertEquals("hello", getContents());
    }

    @Test
    default void WriterTestStupidRemover() throws IOException {
        Writer writer = createWriter(
                List.of(text -> text.replaceAll("stupid", "s*****"))
        );
        writer.write("This is stupid!");
        writer.close();

        assertEquals("This is s*****!", getContents());
    }

    @Test
    default void WriterTestDuplicateRemover() throws IOException {
        Writer writer = createWriter(
                List.of(text -> text.replaceAll("(?i)\\b([a-z]+)\\b(?:\\s+\\1\\b)+", "$1"))
        );
        writer.write("This is is cool cool");
        writer.close();

        assertEquals("This is cool", getContents());
    }

    @Test
    default void WriterTestTwoTransformerFunctions() throws IOException {
        Writer writer = createWriter(
                List.of(text -> text.replaceAll("stupid", "s*****"),
                        String::toUpperCase)
        );
        writer.write("This is stupid!");
        writer.close();

        assertEquals("THIS IS S*****!", getContents());
    }
}
