package hu.fcs.spring.kafka;

public class SlowFibonacciCalculator implements FibonacciCalculator {
    @Override
    public long calc(long n) {
        if (n < 0) {
            throw new IllegalArgumentException("This implementation does not calculate negative Fibonacci numbers");
        }
        if (n <= 1) {
            return n;
        }
        return calc(n - 1) + calc(n - 2);
    }
}
