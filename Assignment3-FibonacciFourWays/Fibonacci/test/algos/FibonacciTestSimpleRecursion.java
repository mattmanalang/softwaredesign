package algos;

public class FibonacciTestSimpleRecursion implements FibonacciTestBase{
  @Override
  public Fibonacci createFibonacci() {
    return new FibonacciSimpleRecursion();
  }
}
