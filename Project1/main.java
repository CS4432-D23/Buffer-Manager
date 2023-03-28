package Project1;
import java.io.File;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {


        Frame testFrame = new Frame();
        testFrame.setContent(1);

        

        // Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the number of frames: ");
        // int numFrames = sc.nextInt();
        // BufferPool bufferPool = new BufferPool(numFrames);

        // while(true) {

        //     System.out.println("Enter the operation: ");
        //     String operation = sc.next();
        //     if (operation.toUpperCase().equals("PIN")) {
        //         System.out.println("Enter the page number: ");
        //         int pageNumber = sc.nextInt();
        //         bufferPool.pin(pageNumber);
        //     } else if (operation.toUpperCase().equals("UNPIN")) {
        //         System.out.println("Enter the page number: ");
        //         int pageNumber = sc.nextInt();
        //         bufferPool.unpin(pageNumber);
        //     } else if (operation.toUpperCase().equals("EXIT")) {
        //         break;
        //     } else {
        //         System.out.println("Invalid operation");
        //     }

        // }

    }   


    public void checkFileDir() {
      
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
