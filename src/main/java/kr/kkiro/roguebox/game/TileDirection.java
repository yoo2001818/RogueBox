package kr.kkiro.roguebox.game;

public enum TileDirection {
  NORTH(0, -1),
  NORTH_EAST(1, -1),
  EAST(1, 0),
  SOUTH_EAST(1, 1),
  SOUTH(0, 1),
  SOUTH_WEST(-1, 1),
  WEST(-1, 0),
  NORTH_WEST(-1, -1);
  
  private int x, y;
  
  private TileDirection(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public static TileDirection getDirection(int x, int y) {
    for(TileDirection e : values()) {
      if(e.x == sign(x) && e.y == sign(y)) return e;
    }
    return null;
  }
  
  private static int sign(int x) {
    return x / Math.abs(x);
  }
  
}
