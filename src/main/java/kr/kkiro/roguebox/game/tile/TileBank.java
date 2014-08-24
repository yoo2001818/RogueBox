package kr.kkiro.roguebox.game.tile;

public class TileBank {
  
  private Tile[] table;
  
  public TileBank(int size) {
    table = new Tile[size];
  }
  
  public void set(int code, Tile tile) {
    // TODO make exception for this
    if(table[code] != null) throw new RuntimeException("Code "+code+" already assigned");
    table[code] = tile;
  }
  
  public Tile get(int code) {
    return table[code];
  }
  
  public void remove(int code) {
    table[code] = null;
  }
  
  public void remove(Tile tile) {
    int index = indexOf(tile);
    if(index == -1) return;
    table[index] = null;
  }
  
  public int size() {
    return table.length;
  }
  
  public int indexOf(Tile tile) {
    for(int i = 0; i < table.length; ++i) {
      if(table[i] == tile) return i;
    }
    return -1;
  }
  
  public int lastIndexOf(Tile tile) {
    for(int i = table.length-1; i >= 0; --i) {
      if(table[i] == tile) return i;
    }
    return -1;
  }
  
  public Tile[] getArray() {
    return table;
  }
  
}
