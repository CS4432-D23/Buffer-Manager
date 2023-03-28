import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of frames: ");
        int numFrames = sc.nextInt();
        BufferPool bufferPool = new BufferPool(numFrames);

        while(true) {

            System.out.println("Enter the operation: ");
            String operation = sc.next();
            if (operation.toUpperCase().equals("PIN")) {
                System.out.println("Enter the page number: ");
                int pageNumber = sc.nextInt();
                bufferPool.pin(pageNumber);
            } else if (operation.toUpperCase().equals("UNPIN")) {
                System.out.println("Enter the page number: ");
                int pageNumber = sc.nextInt();
                bufferPool.unpin(pageNumber);
            } else if (operation.toUpperCase().equals("EXIT")) {
                break;
            } else {
                System.out.println("Invalid operation");
            }

        }

    }
}
