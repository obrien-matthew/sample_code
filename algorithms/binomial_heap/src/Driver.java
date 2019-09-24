import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


import static java.lang.System.exit;

public class Driver {
  public static void main(String[] args) {
    BiHeap tree = new BiHeap();

      boolean run = true;
      while (run) {
        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
        System.out.print("\nEnter a command: ");
        String command = in.next();
        command = command.toLowerCase();
        String[] commands = command.split(" ");

        try {
          switch (commands[0]) {
            case "exit":
              run = false;
              break;
            case "insert":
              tree.insert(new HeapNode(Integer.parseInt(commands[1])));
              break;
            case "min":
              System.out.print(tree.minimum());
              break;
            case "extract":
              System.out.print(tree.extractMin().getKey());
              break;
            case "height":
              System.out.print(tree.height());
              break;
            default:
              System.out.print("Error: Command not found.\n");
          }

          //System.out.print("\nHeap height: " + tree.height() + "\n");
        } catch (Exception e) {
          System.out.print("\nError: " + e.getMessage());
        }
      }
  }
}
