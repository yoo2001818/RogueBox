package kr.kkiro.roguebox.util;

import java.util.ArrayList;
import java.util.List;

public enum MapComponent {
  EMPTY(false, false, false, false, ' ', 0),
  ROOM(false, false, false, false, '▋', 0),
  VERTICAL(false, false, true, true, '│', 100),
  HORIZONTAL(true, true, false, false, '─', 100),
  DOWN_RIGHT(false, true, false, true, '┌', 20),
  UP_RIGHT(false, true, true, false, '└', 20),
  DOWN_LEFT(true, false, false, true, '┐', 20),
  UP_LEFT(true, false, true, false, '┘', 20),
  VERTICAL_LEFT(true, false, true, true, '┤', 0),
  VERTICAL_RIGHT(false, true, true, true, '├', 0),
  HORIZONTAL_DOWN(true, true, false, true, '┬', 0),
  HORIZONTAL_TOP(true, true, true, false, '┴', 0),
  VERTICAL_HORIZONTAL(true, true, true, true, '┼', 0),
  UP(false, false, true, false, '╵', 0),
  DOWN(false, false, false, true, '╷', 0),
  LEFT(true, false, false, false, '╴', 0),
  RIGHT(false, true, false, false, '╶', 0);
  
  private int bitMask = 0;
  private char icon = ' ';
  private int density = 0;
  
  private MapComponent(boolean left, boolean right, boolean top, boolean bottom, char icon, int density) {
    bitMask += left ? 1 : 0;
    bitMask += right ? 2 : 0;
    bitMask += top ? 4 : 0;
    bitMask += bottom ? 8 : 0;
    this.icon = icon;
    this.density = density;
  }
  
  public int getDensity() {
    return density;
  }
  
  public boolean getLeft() {
    return (bitMask & 1) != 0;
  }
  
  public boolean getRight() {
    return (bitMask & 2) != 0;
  }
  
  public boolean getTop() {
    return (bitMask & 4) != 0;
  }
  
  public boolean getBottom() {
    return (bitMask & 8) != 0;
  }
  
  public int getBitMask() {
    return bitMask;
  }
  
  public char getIcon() {
    return icon;
  }
  
  public static MapComponent[] getComponents(int code) {
    List<MapComponent> list = new ArrayList<MapComponent>();
    for(MapComponent p : values()) {
      if(p.getBitMask() == 0) continue;
      if((p.getBitMask() & code) == code) list.add(p);
    }
    return list.toArray(new MapComponent[list.size()]);
  }
  
  public static MapComponent[] getExactComponents(int code) {
    List<MapComponent> list = new ArrayList<MapComponent>();
    for(MapComponent p : values()) {
      if(p.getBitMask() == code) list.add(p);
    }
    return list.toArray(new MapComponent[list.size()]);
  }
  
}
