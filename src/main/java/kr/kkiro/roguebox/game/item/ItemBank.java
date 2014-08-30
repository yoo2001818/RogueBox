package kr.kkiro.roguebox.game.item;

public class ItemBank {
  
  private ItemEntry[] table;
  
  public ItemBank(int size) {
    table = new ItemEntry[size];
  }
  
  public void set(int code, ItemEntry item) {
    // TODO make exception for this
    if(table[code] != null) throw new RuntimeException("Code "+code+" already assigned");
    table[code] = item;
  }
  
  public ItemEntry get(int code) {
    return table[code];
  }
  
  public void remove(int code) {
    table[code] = null;
  }
  
  public void remove(ItemEntry item) {
    int index = indexOf(item);
    if(index == -1) return;
    table[index] = null;
  }
  
  public int size() {
    return table.length;
  }
  
  public int indexOf(ItemEntry item) {
    for(int i = 0; i < table.length; ++i) {
      if(table[i] == item) return i;
    }
    return -1;
  }
  
  public int lastIndexOf(ItemEntry item) {
    for(int i = table.length-1; i >= 0; --i) {
      if(table[i] == item) return i;
    }
    return -1;
  }
  
  public ItemEntry[] getArray() {
    return table;
  }
  
}
