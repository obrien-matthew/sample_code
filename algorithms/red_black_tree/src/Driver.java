import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import tree.RBTree;

import static java.lang.System.exit;

public class Driver {
  public static void main(String[] args) {
    RBTree tree = new RBTree();
    String pattern = "^[\\w\\d\\/]+\\.txt$";
    if (args[0].matches(pattern) && new File(args[0]).isFile()) {
      try {
        FileReader f = new FileReader(args[0]);
        Scanner s = new Scanner(f);
        s.useDelimiter("\\s+");
        s.useRadix(10);
        ArrayList<Integer> ints = new ArrayList<>();
        while (s.hasNextInt()) {
          ints.add(s.nextInt());
        }
        s.close();

        for (Integer num : ints) {
          try {
            tree.insert(num);
          } catch (IllegalStateException e) {
            System.out.print("Error: Red-Black Tree properties broken: " + e.getMessage() + "\n");
            exit(1);
          }
        }


        System.out.print("Tree built successfully from file.\n");
        System.out.print("Tree black-height: " + tree.blackHeight() + "\n");

      } catch (FileNotFoundException e) {
        System.out.print("Error: File not found. Please restart program.\n");
        exit(1);
      }

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
            case "sort":
              System.out.print(tree.sort());
              break;
            case "insert":
              tree.insert(Integer.parseInt(commands[1]));
              break;
            case "search":
              System.out.print(tree.search(Integer.parseInt(commands[1])));
              break;
            case "max":
              System.out.print(tree.max());
              break;
            case "min":
              System.out.print(tree.min());
              break;
            case "predecessor":
              System.out.print(tree.predecessor(Integer.parseInt(commands[1])));
              break;
            case "successor":
              System.out.print(tree.successor(Integer.parseInt(commands[1])));
              break;
            default:
              System.out.print("Error: Command not found.\n");
          }

          System.out.print("\nTree black-height: " + tree.blackHeight() + "\n");
        } catch (Exception e) {
          System.out.print("\nError: " + e.getMessage());
        }
      }
    } else {
      System.out.print("Usage: rbtree file.txt\n");
    }
  }
}
