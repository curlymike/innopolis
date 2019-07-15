package part1.lesson12.task2;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;

/**
 * Этот класс позволяет скомпилировать Java код не сохраняя файл на диск.
 * Для этого используется два анонимных класса отнаследованных от
 * SimpleJavaFileObject и ForwardingJavaFileManager объекты которых передаются
 * в системный JavaCompiler и "подсовывают" ему ByteArrayOutputStream с Java кодом
 * который необходимо скомпилировать.
 * Это конечно не production-grade решение, а скорее "хак", но т.к. цель ДЗ вызвать ошибку OutOfMemoryError в Metaspace - свое дело он делает.
 */

public class MyCompiler {

  /**
   * Компилирует Java код в байткод.
   * @param pkg название пакета полностью.
   * @param className имя класса.
   * @param sourceCode Java код класса className со значением package pkg который необходимо скомпилировать.
   * @return Массив байт скомпилированного байткода.
   */

  public static byte[] compile(String pkg, String className, String sourceCode) {

    String fullClassPath = pkg.replace('.', '/') + "/" + className + ".java";
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

    SimpleJavaFileObject simpleJavaFileObject = new SimpleJavaFileObject(URI.create(fullClassPath), javax.tools.JavaFileObject.Kind.SOURCE) {
      @Override
      public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
      }

      @Override
      public OutputStream openOutputStream() {
        return byteArrayOutputStream;
      }
    };

    JavaFileManager javaFileManager = new ForwardingJavaFileManager<StandardJavaFileManager>(javaCompiler.getStandardFileManager(null, null, null)) {
      @Override
      public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        return simpleJavaFileObject;
      }
    };

    javaCompiler.getTask(null, javaFileManager, null, null, null, Collections.singletonList(simpleJavaFileObject)).call();

    return byteArrayOutputStream.toByteArray();
  }
}
