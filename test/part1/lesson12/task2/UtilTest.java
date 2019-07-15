package part1.lesson12.task2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {

  @Test
  void replace001() throws Exception {
    String str = "This is a test string for testing purposes";
    String expected = "This is a cool string for cooling purposes";
    String subj = "test";
    String replacement = "cool";
    assertThat(Util.replace(str.getBytes(), subj.getBytes(), replacement.getBytes())).isEqualTo(expected.getBytes());
  }

    @Test
  void replace002() throws Exception {
    String str = "aaa bbb ccc bbb aaa";
    String expected = "xxx bbb ccc bbb xxx";
    String subj = "aaa";
    String replacement = "xxx";
    assertThat(Util.replace(str.getBytes(), subj.getBytes(), replacement.getBytes())).isEqualTo(expected.getBytes());
  }

    @Test
  void replace003() throws Exception {
    String str = "aaa bbb ccc bbb aaa";
    String expected = "xxxxxx bbb ccc bbb xxxxxx";
    String subj = "aaa";
    String replacement = "xxxxxx";
    assertThat(Util.replace(str.getBytes(), subj.getBytes(), replacement.getBytes())).isEqualTo(expected.getBytes());
  }

    @Test
  void replace004() throws Exception {
    String str = "aaa bbb ccc bbb aaa z";
    String expected = "xxxxxx bbb ccc bbb xxxxxx z";
    String subj = "aaa";
    String replacement = "xxxxxx";
    assertThat(Util.replace(str.getBytes(), subj.getBytes(), replacement.getBytes())).isEqualTo(expected.getBytes());
  }

    @Test
  void replace005() throws Exception {
    String str = "aaaaaa bbb ccc bbb aaa zzz";
    String expected = "ffffff bbb ccc bbb fff zzz";
    String subj = "aaa";
    String replacement = "fff";
    assertThat(Util.replace(str.getBytes(), subj.getBytes(), replacement.getBytes())).isEqualTo(expected.getBytes());
  }








}