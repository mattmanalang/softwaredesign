package algos;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

interface FibonacciTestBase {
  @Test
  default void canary() {
    assertTrue(true);
  }

  Fibonacci createFibonacci();

  @Test
  public default void testForFibonacciPositions() { 
    var fibonacci = createFibonacci();

    assertAll( 
      () -> assertEquals(BigInteger.ONE, fibonacci.fibonacciAtPosition(0)),
      () -> assertEquals(BigInteger.ONE, fibonacci.fibonacciAtPosition(1)),
      () -> assertEquals(BigInteger.valueOf(2), fibonacci.fibonacciAtPosition(2)),
      () -> assertEquals(BigInteger.valueOf(8), fibonacci.fibonacciAtPosition(5)),
      () -> assertEquals(BigInteger.valueOf(89), fibonacci.fibonacciAtPosition(10)),
      () -> assertEquals(BigInteger.valueOf(433494437), fibonacci.fibonacciAtPosition(42))
    );
  }
}
