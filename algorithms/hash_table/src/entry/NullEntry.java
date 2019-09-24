package entry;

public class NullEntry implements IEntry {


  public NullEntry() {}


  public TableEntry insert(String key, int value, int index) {
    return new TableEntry(key, value, this, index);
  }

  public IEntry delete(String key) throws IllegalArgumentException {
    throw new IllegalArgumentException("Key not found.\n");
  }

  public TableEntry find(String key) throws IllegalArgumentException {
    throw new IllegalArgumentException("Key not found.\n");
  }

}
