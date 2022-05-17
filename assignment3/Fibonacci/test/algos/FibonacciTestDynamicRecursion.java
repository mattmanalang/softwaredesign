package algos;

public class FibonacciTestDynamicRecursion implements FibonacciTestBase{
  @Override
  public Fibonacci createFibonacci() {
    return new FibonacciDynamicRecursion();
  }
}
