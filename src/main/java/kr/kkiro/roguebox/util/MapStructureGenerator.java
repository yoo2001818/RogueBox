package kr.kkiro.roguebox.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MapStructureGenerator {
  private MapComponent[][] map;
  private Random random;
  private Stack<MapPosition> stack;
  private int width, height;
  private int entries;
  private int rooms;
  private int branchsize;
  private int counter = -4000;
  private MapPosition startRoom;
  private MapPosition endRoom;
  private MapPosition[] roomList;
  
  public MapStructureGenerator(int width, int height, int entries, int rooms, int branchsize, long seed) {
    random = new Random(seed);
    map = new MapComponent[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        map[y][x] = MapComponent.EMPTY;
      }
    }
    stack = new Stack<MapPosition>();
    int startX = width/2;
    int startY = height/2+1;
    stack.push(new MapPosition(startX, startY));
    this.width = width;
    this.height = height;
    this.entries = entries;
    this.branchsize = branchsize;
    this.rooms = rooms;
    this.roomList = new MapPosition[rooms];
    map[startY][startX] = random(MapComponent.values());
  }
  
  public MapPosition[] getRoomList() {
    return roomList;
  }
  
  public int getRooms() {
    return rooms;
  }
  
  public int getEntries() {
    return entries;
  }
  
  public MapPosition getStartRoom() {
    return startRoom;
  }
  
  public MapPosition getEndRoom() {
    return endRoom;
  }
  
  private MapComponent random(MapComponent[] arr) {
    int density = 0;
    for(MapComponent c : arr) density += c.getDensity();
    int currentDensity = random.nextInt(density + 1);
    for(MapComponent c : arr) {
      currentDensity -= c.getDensity();
      if(currentDensity < 0) return c;
    }
    return arr[0];
  }
  
  private MapComponent randomdensity(MapComponent[] arr) {
    return arr[random.nextInt(arr.length)];
  }
  
  private MapPosition[] existing() {
    List<MapPosition> list = new ArrayList<MapPosition>();
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        if(map[y][x] != MapComponent.EMPTY) list.add(new MapPosition(x, y));
      }
    }
    return list.toArray(new MapPosition[list.size()]);
  }
  
  private MapPosition[] existingBlocked() {
    List<MapPosition> list = new ArrayList<MapPosition>();
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        if(map[y][x] != MapComponent.EMPTY && count_bits(map[y][x].getBitMask()) == 1) list.add(new MapPosition(x, y));
      }
    }
    return list.toArray(new MapPosition[list.size()]);
  }
  
  public int getBranchSize() {
    return branchsize;
  }
  
  public int getCounter() {
    return counter;
  }
  
  private MapComponent getBottom(int x, int y) {
    if((y+1) >= height) return null;
    return map[y+1][x];
  }
  
  private MapComponent getTop(int x, int y) {
    if((y-1) < 0) return null;
    return map[y-1][x];
  }
  
  private MapComponent getRight(int x, int y) {
    if((x+1) >= width) return null;
    return map[y][x+1];
  }
  
  private MapComponent getLeft(int x, int y) {
    if((x-1) < 0) return null;
    return map[y][x-1];
  }
  
  private boolean checkBottom(int x, int y) {
    MapComponent obj = getBottom(x, y);
    if(obj == null) return false;
    return obj.getTop();
  }
  
  private boolean checkTop(int x, int y) {
    MapComponent obj = getTop(x, y);
    if(obj == null) return false;
    return obj.getBottom();
  }
  
  private boolean checkLeft(int x, int y) {
    MapComponent obj = getLeft(x, y);
    if(obj == null) return false;
    return obj.getRight();
  }
  
  private boolean checkRight(int x, int y) {
    MapComponent obj = getRight(x, y);
    if(obj == null) return false;
    return obj.getLeft();
  }
  
  private int count_bits(int n) {     
    int c;
    for (c = 0; n != 0; c++) 
      n &= n - 1;
    return c;
  }
  
  public boolean step() {
    if(stack.isEmpty()) {
      MapPosition[] entryList = existing();
      if(entries <= entryList.length) {
        MapPosition[] entryList2 = existingBlocked();
        startRoom = entryList2[random.nextInt(entryList2.length)];
        endRoom = startRoom;
        for(int i = 0; i < 2; ++i) {
          for(MapPosition p : entryList2) {
            int dist = (startRoom.x - endRoom.x) * (startRoom.x - endRoom.x) + (startRoom.y - endRoom.y) * (startRoom.y - endRoom.y);
            int dist2 = (startRoom.x - p.x) * (startRoom.x - p.x) + (startRoom.y - p.y) * (startRoom.y - p.y);
            if(dist2 > dist) endRoom = p;
          }
          for(MapPosition p : entryList2) {
            int dist = (startRoom.x - endRoom.x) * (startRoom.x - endRoom.x) + (startRoom.y - endRoom.y) * (startRoom.y - endRoom.y);
            int dist2 = (endRoom.x - p.x) * (endRoom.x - p.x) + (endRoom.y - p.y) * (endRoom.y - p.y);
            if(dist2 > dist) startRoom = p;
          }
        }
        entryList = existing();
        for(int i = 0; i< rooms-2; ++i) {
          do {
            MapPosition entry = entryList[random.nextInt(entryList.length)];
            //if(count_bits(map[entry.y][entry.x].getBitMask()) != 1) continue;
            roomList[i] = entry;
            //break;
          } while(startRoom.equals(roomList[i]) || endRoom.equals(roomList[i]));
        }
        roomList[rooms-2] = startRoom;
        roomList[rooms-1] = endRoom;
        return false;
      }
      // find random position
      counter = 0;
      MapPosition entry = entryList[random.nextInt(entryList.length)];
      map[entry.y][entry.x] = randomdensity(MapComponent.getComponents(map[entry.y][entry.x].getBitMask()));
      stack.push(entry);
    }
    MapPosition pos = stack.peek();
    MapComponent comp = map[pos.y][pos.x];
    counter ++;
    if(comp.getTop() && !checkTop(pos.x, pos.y)) {
      MapComponent other = getTop(pos.x, pos.y);
      if(other == null || other != MapComponent.EMPTY || entries <= existing().length || counter >= branchsize || (counter < 0 && counter >= (-4000+branchsize*rooms/4))) {
        map[pos.y][pos.x] = random(MapComponent.getExactComponents(comp.getBitMask() - MapDirection.TOP.getCode()));
        return true;
      }
      map[pos.y-1][pos.x] = random(MapComponent.getComponents(MapDirection.BOTTOM.getCode()));
      stack.push(new MapPosition(pos.x, pos.y-1));
      return true;
    }
    if(comp.getBottom() && !checkBottom(pos.x, pos.y)) {
      MapComponent other = getBottom(pos.x, pos.y);
      if(other == null || other != MapComponent.EMPTY || entries <= existing().length || counter >= branchsize || (counter < 0 && counter >= (-4000+branchsize*rooms/4))) {
        map[pos.y][pos.x] = random(MapComponent.getExactComponents(comp.getBitMask() - MapDirection.BOTTOM.getCode()));
        return true;
      }
      map[pos.y+1][pos.x] = random(MapComponent.getComponents(MapDirection.TOP.getCode()));
      stack.push(new MapPosition(pos.x, pos.y+1));
      return true;
    }
    if(comp.getLeft() && !checkLeft(pos.x, pos.y)) {
      MapComponent other = getLeft(pos.x, pos.y);
      if(other == null || other != MapComponent.EMPTY || entries <= existing().length || counter >= branchsize || (counter < 0 && counter >= (-4000+branchsize*rooms/4))) {
        map[pos.y][pos.x] = random(MapComponent.getExactComponents(comp.getBitMask() - MapDirection.LEFT.getCode()));
        return true;
      }
      map[pos.y][pos.x-1] = random(MapComponent.getComponents(MapDirection.RIGHT.getCode()));
      stack.push(new MapPosition(pos.x-1, pos.y));
      return true;
    }
    if(comp.getRight() && !checkRight(pos.x, pos.y)) {
      MapComponent other = getRight(pos.x, pos.y);
      if(other == null || other != MapComponent.EMPTY || entries <= existing().length || counter >= branchsize || (counter < 0 && counter >= (-4000+branchsize*rooms/4))) {
        map[pos.y][pos.x] = random(MapComponent.getExactComponents(comp.getBitMask() - MapDirection.RIGHT.getCode()));
        return true;
      }
      map[pos.y][pos.x+1] = random(MapComponent.getComponents(MapDirection.LEFT.getCode()));
      stack.push(new MapPosition(pos.x+1, pos.y));
      return true;
    }
    stack.pop();
    return true;
  }
  
  public MapComponent[][] getMap() {
    return map;
  }
  
  public Stack<MapPosition> getStack() {
    return stack;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public static class MapPosition implements Cloneable {
    public int x;
    public int y;
    public MapPosition(int x, int y) {
      this.x = x;
      this.y = y;
    }
    
    @Override
    public MapPosition clone() {
      try {
        return (MapPosition)(super.clone());
      } catch (CloneNotSupportedException e) {
        return null;
      }
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      MapPosition other = (MapPosition) obj;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }
    
  }
  
  public static enum MapDirection {
    
    LEFT(1), RIGHT(2), TOP(4), BOTTOM(8);
    
    private int code;
    
    private MapDirection(int code) {
      this.code = code;
    }
    
    public int getCode() {
      return code;
    }
    
    public static MapDirection reverse(MapDirection e) {
      switch(e) {
        case BOTTOM:
          return TOP;
        case LEFT:
          return RIGHT;
        case RIGHT:
          return LEFT;
        case TOP:
          return TOP;
      }
      return null;
    }
    
  }
  
}
