package Project1;

import java.io.File;
import java.util.Scanner;

public class main {
  public static void main(String[] args) {

    // Frame testFrame = new Frame();
    // testFrame.setContent(1);

    Scanner sc = new Scanner(System.in);
    System.out.print("Buffer Pool Size: ");
    int numFrames = sc.nextInt();
    BufferPool bufferPool = BufferPool.initalize(numFrames);
    sc.nextLine();

    while (true) {

      System.out.println("");
      System.out.println("The program is ready for the next command");
      System.out.print("> ");
      String operation = sc.nextLine();
      // System.out.println(operation);
      System.out.println("");

      String[] command = operation.split(" ", 3);

      // System.out.println(command.length);

      if (command[0].toUpperCase().equals("GET") && command.length == 2) {
        int recordNumber = Integer.parseInt(command[1]);
        String record = bufferPool.get(recordNumber);
        System.out.println(record);
        // System.out.println(record.getBytes().length); // making sure the record is 40
        // bytes
        // System.out.println(Integer.parseInt(command[1]));

      } else if (command[0].toUpperCase().equals("SET")) {
        int recordNumber = Integer.parseInt(command[1]);
        String record = command[2];
        System.out.println(bufferPool.set(recordNumber, record));

      } else if (command[0].toUpperCase().equals("PIN")) {
        System.out.println(bufferPool.pin(Integer.parseInt(command[1])));
    
      } else if (command[0].toUpperCase().equals("UNPIN")) {
        System.out.println(bufferPool.unpin(Integer.parseInt(command[1])));
      }

      else if (command[0].toUpperCase().equals("SHOW") && command.length == 1) {
        bufferPool.show();
      }

      else if (command[0].toUpperCase().equals("EXIT") && command.length == 1) {
        break;
      } else {
        System.out.println("INVALID COMMAND - TRY AGAIN");
      }

    }

    sc.close();

  }

  public static void checkFileDir() {

    File currentDir = new File(".");
    File[] files = currentDir.listFiles();

    for (File file : files) {
      if (file.isFile()) {
        System.out.println("File: " + file.getName());
      } else if (file.isDirectory()) {
        System.out.println("Directory: " + file.getName());
      }
    }
  }
}
