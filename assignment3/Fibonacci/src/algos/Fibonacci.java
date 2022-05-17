package algos;

import java.math.BigInteger;

interface Fibonacci {
  BigInteger fibonacciAtPosition(int position);
  
  default BigInteger getCurrentFibonacci(int position) {
    return fibonacciAtPosition(position - 1).add(fibonacciAtPosition(position - 2));
  }
}
