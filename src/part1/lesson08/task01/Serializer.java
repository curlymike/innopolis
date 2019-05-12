package part1.lesson08.task01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {

  public static void serialize(Subject subj, String file) throws IOException {
    try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(Paths.get(file)))) {
      dos.writeInt(subj.getNumber());
      dos.writeBoolean(subj.status());
      dos.writeUTF(subj.getDescription());
      dos.writeLong(subj.getValue());
    }
  }

  public static Subject deSerialize(String file) throws IOException {
    try (DataInputStream dis = new DataInputStream(Files.newInputStream(Paths.get(file)))) {
      return new Subject(dis.readInt(), dis.readBoolean(), dis.readUTF(), dis.readLong());
    }
  }

}
