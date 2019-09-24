import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import entry.IEntry;
import entry.NullEntry;
import entry.TableEntry;

public class Table {
  private IEntry[] tableArray;
  private int maxHash;

  public Table(int max) {
    this.maxHash = max-1;
    this.tableArray = new IEntry[this.maxHash];
    Arrays.fill(this.tableArray, new NullEntry());
  }

  private int hash(String key) {
    int asciiSum = 0;
    for (char c : key.toCharArray()) {
      asciiSum += (int) c;
    }

    return asciiSum % this.maxHash;
  }

  public void insert(String key, int value, int index) {
    key = key.toLowerCase();
    int h = hash(key);
    tableArray[h] = this.tableArray[h].insert(key, value, index);
    return;
  }

  public void delete(String key) {
    int h = hash(key);
    tableArray[h] = this.tableArray[h].delete(key);
    return;
  }

  public void increase(String key, int index) {
    TableEntry e = this.find(key);
    e.increase(index);
    return;
  }

  public TableEntry find(String key) {
    key = key.toLowerCase();
    int h = hash(key);
    return this.tableArray[h].find(key);
  }

  public void listAllKeys() throws IOException {
    String p = System.getProperty("user.dir");
    String filename = "out.txt";
    File f = new File(p + "/" + filename);
    f.delete();
    FileWriter fw = new FileWriter(filename);
    for (int i = 0; i < tableArray.length; i++) {
      IEntry e = tableArray[i];
      while (e.getClass() != NullEntry.class) {               // use while loop instead of recursion
        TableEntry te = (TableEntry) e;                       // to avoid stack and memory overflow
        fw.write(te.toString());                              // for smaller hashes
        e = te.getNext();
      }
      fw.flush();;
    }
    fw.close();
    return;
  }
}
