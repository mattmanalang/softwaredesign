package algos;

import java.math.BigInteger;

public class FibonacciSimpleRecursion implements Fibonacci{
  public BigInteger fibonacciAtPosition(int position) {
    if (position == 0 || position == 1) {
      return BigInteger.ONE;
    }
    
    return getCurrentFibonacci(position);
  }
}
