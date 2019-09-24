import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;

public class Driver {
  public static void main(String[] args) {
    int maxHash = 10000;
    if (args.length > 1 && args[1] != null) {
      maxHash = Integer.parseInt(args[1]);
    }

    Table hashTable = new Table(maxHash);
    String pattern = "^[\\w\\d\\/]+\\.txt$";
    if (args.length >= 1 && args[0].matches(pattern) && new File(args[0]).isFile()) {
      try {
        FileReader f = new FileReader(args[0]);
        Scanner s = new Scanner(f);
        s.useDelimiter("[\\s+\\W]");
        ArrayList<String> words = new ArrayList<>();
        while (s.hasNext()) {                             // "\\w+'\\w+|\\w+"
          words.add(s.next());
        }
        s.close();

        int index = 0;
        for (String word : words) {
          if (!word.equals("")) {
            try {
              hashTable.increase(word, index);
            } catch (IllegalArgumentException e) {
              hashTable.insert(word, 1, index);
            }
            index += 1;
          }
        }

        System.out.print("Hash table with a max hash value of " + maxHash
                + " built successfully from file.\n");

      } catch (FileNotFoundException e) {
        System.out.print("Error: File not found. Please restart program.\n");
        exit(1);
      }

      try {
        hashTable.listAllKeys();
        System.out.print("File written successfully.\n");
        exit(0);
      } catch (IOException e) {
        System.out.print("Error writing file: " + e.getMessage()
                + ". Please restart the program.\n");
        exit(1);
      }

    } else {
      System.out.print("Usage: hash file.txt maxhash\nArgs: ");
      for (String arg : args) {
        System.out.print(Arrays.asList(args).indexOf(arg) + " | " + arg + "\n");
      }
    }
  }

}
