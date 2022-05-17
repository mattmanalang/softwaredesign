package algos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibonacciDynamicRecursion implements Fibonacci{
  private List<BigInteger> values = new ArrayList<BigInteger>(List.of(BigInteger.ONE, BigInteger.ONE));

  public BigInteger fibonacciAtPosition(int position) {
    var lastIndex = values.size() - 1;

    if (position > lastIndex) {
      values.add(getCurrentFibonacci(position));
    }
    
    return values.get(position);
  }
}
