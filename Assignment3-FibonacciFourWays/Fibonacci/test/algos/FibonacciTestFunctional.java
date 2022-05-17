package algos;

public class FibonacciTestFunctional implements FibonacciTestBase {
  @Override
  public Fibonacci createFibonacci() {
    return new FibonacciFunctional();
  }
}
