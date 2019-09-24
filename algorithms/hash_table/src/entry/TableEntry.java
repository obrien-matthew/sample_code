package entry;

import java.util.ArrayList;

public class TableEntry implements IEntry {
  private String key;
  private int count;
  private ArrayList<Integer> pos;
  private IEntry next;

  public TableEntry(String k, int v, IEntry n, int index) {
    this.key = k;
    this.count = v;
    this.next = n;
    this.pos = new ArrayList<Integer>();
    this.pos.add(index);
  }

  public TableEntry insert(String k, int value, int index) {
    if (this.key.equals(k)) {
      this.count += value;
      this.pos.add(index);
    } else {
      this.next = this.next.insert(k, value, index);
    }

    return this;
  }

  public IEntry getNext() {
    return this.next;
  }

  public IEntry delete(String k) {
    if (this.key.equals(k)) {
      return this.next;
    }

    this.next = this.next.delete(k);
    return this;
  }

  public void increase(int index) {
    this.count += 1;
    this.pos.add(index);

    return;
  }

  public TableEntry find(String k) {
    if (this.key.equals(k)) {
      return this;
    }

    return this.next.find(k);
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int num : this.pos) {
      s.append(num + ", ");
    }
    s.delete(s.length()-2, s.length());
    String str = s.toString();
    return (this.key + ": " + this.count + " [" + str + "]\n");
  }

}
