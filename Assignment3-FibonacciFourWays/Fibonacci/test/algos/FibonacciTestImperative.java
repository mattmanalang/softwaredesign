package algos;

public class FibonacciTestImperative implements FibonacciTestBase {
  @Override
  public Fibonacci createFibonacci() {
    return new FibonacciImperative();
  }
}
