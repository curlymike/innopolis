package part1.lesson06.task02;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class MainTest {

  @Test
  public void test001() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Main.writeText(baos, 2000, new String[]{"Hello", "Goodbye"}, 3);

    System.out.println("---");
    System.out.println("Got " + baos.toByteArray().length + " bytes");
    System.out.println("---");

    BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));

    String str;

    while ((str = br.readLine()) != null) {
      System.out.println(str);
    }
  }
}