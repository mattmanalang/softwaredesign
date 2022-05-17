package algos;

import java.math.BigInteger;
import java.util.List;

public class FibonacciImperative implements Fibonacci {
  public BigInteger fibonacciAtPosition(int position) {
    var values = List.of(BigInteger.ONE, BigInteger.ONE); 
        
    for (int i = 1; i < position; i++) { 
      values = List.of(values.get(1), values.get(0).add(values.get(1)));
    }

    return values.get(1);
  }  
}
