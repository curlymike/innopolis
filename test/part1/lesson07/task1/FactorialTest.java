package part1.lesson07.task1;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class FactorialTest {

  @Test
  public void compute001() {
    assertThat(Factorial.compute(4)).isEqualTo(BigInteger.valueOf(24));
  }

  @Test
  public void compute002() {
    assertThat(Factorial.compute(5)).isEqualTo(BigInteger.valueOf(120));
  }

  @Test
  public void compute003() {
    assertThat(Factorial.compute(6)).isEqualTo(BigInteger.valueOf(720));
  }

  @Test
  public void compute004() {
    assertThat(Factorial.compute(7)).isEqualTo(BigInteger.valueOf(5040));
  }

  @Test
  public void compute005() {
    assertThat(Factorial.compute(8)).isEqualTo(BigInteger.valueOf(40320));
  }

  @Test
  public void compute006() {
    assertThat(Factorial.compute(16)).isEqualTo(BigInteger.valueOf(20922789888000L));
  }

}