package entry;

public interface IEntry {

  TableEntry insert(String key, int value, int index);

  IEntry delete(String key);

  TableEntry find(String key);

}
