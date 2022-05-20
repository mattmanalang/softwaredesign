# Fibonacci Four Ways

<p>The goal of this assignment was to write four implementations of a function to compute Fibonacci number at a given position while reusing as much code as possible.</p>

<p>For this assignment, the Fibonacci series of numbers starts with 1, 1, 2, 3, 5, 8, 13, 21, ... where the values at position 0 is 1, and the value at position 1 is also 1. The values at each position after are a sum of the values at the previous two positions.<br>
<em>As a side note, some Fibonacci series start with a 0 at position 0 instead of a 1 like in this assignment.</em></p>
  
The four implementations are described as such:
1. **Imperative Iteration**: Given a position *n*, loop through totaling values until you reach the value for the given position.
2. **Functional Iteration**: Given a position *n*, loop through totaling values until you reach the value for the given position. Avoid explicit mutability.
3. **Simple Recursion**: Given a position, compute the value at a position using the expression fib(n - 1) + fib(n - 2).
4. **Memoized Recursion**: Given a position, look up to see if the value has been precomputed and if so return that value. Otherwise, use recursion to compute the value. 

<p> Currently, according to the evaluation that was left, there is poor quality of design for the memoized recursion without duplication. The assignment submitted is not using recursion.
