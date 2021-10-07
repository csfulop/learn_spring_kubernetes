package hu.fcs.spring.kafka;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TestSlowFibonacciCalculator {
    private SlowFibonacciCalculator fib = new SlowFibonacciCalculator();

    @ParameterizedTest
    @CsvSource({
                   "0,0",
                   "1,1",
                   "2,1",
                   "3,2",
                   "4,3",
                   "5,5",
                   "6,8"
               })
    public void testFibonacci(int n, int expected) {
        assertThat(fib.calc(n), is(expected));
    }
}
