package part1.lesson10.task01.server;

import org.junit.Test;

import java.lang.reflect.Method;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {
  @Test
  public void isValidNameTest001() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Hello")).isEqualTo(true);
  }

  @Test
  public void isValidNameTest002() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "HelloHelloHelloHelloHelloHelloHelloHello!")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest003() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "1Hello")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest004() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "!Hello")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest005() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Hel!o")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest006() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Hello!")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest007() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Hello-hello")).isEqualTo(true);
  }

  @Test
  public void isValidNameTest008() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Hello-hello-")).isEqualTo(false);
  }

  @Test
  public void isValidNameTest009() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "Миха")).isEqualTo(true);
  }

  @Test
  public void isValidNameTest010() throws Exception {
    Method m = Client.class.getDeclaredMethod("isValidName", String.class);
    m.setAccessible(true);
    assertThat(m.invoke(null, "миха")).isEqualTo(true);
  }



}