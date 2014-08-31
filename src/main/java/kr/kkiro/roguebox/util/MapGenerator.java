package kr.kkiro.roguebox.util;

import java.util.Random;

import kr.kkiro.roguebox.game.EntityMap;
import kr.kkiro.roguebox.game.TileMap;
import kr.kkiro.roguebox.game.entity.Treasure;
import kr.kkiro.roguebox.game.entity.mob.ChestMimic;
import kr.kkiro.roguebox.game.entity.mob.Monster;
import kr.kkiro.roguebox.game.tile.TileBank;
import kr.kkiro.roguebox.util.MapStructureGenerator.MapPosition;

public class MapGenerator {
  
  protected MapStructureGenerator generator;
  protected TileMap tilemap;
  protected int sizeX, sizeY;
  protected int wallCode;
  protected int floorCode;
  protected int tunnelCode;
  protected Random random;
  
  public MapGenerator(MapStructureGenerator generator, TileBank bank, int sizeX, int sizeY, int wallCode, int floorCode, int tunnelCode, long seed) {
    this.generator = generator;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    tilemap = new TileMap(sizeX * generator.getWidth(), sizeY * generator.getHeight(), bank, 0);
    this.wallCode = wallCode;
    this.floorCode = floorCode;
    this.tunnelCode = tunnelCode;
    random = new Random(seed);
  }
  
  public TileMap getTileMap() {
    return tilemap;
  }
  
  public MapStructureGenerator getGenerator() {
    return generator;
  }
 
  public void generateEntity(EntityMap entityMap) {
    int halfSizeX = sizeX / 2;
    int halfSizeY = sizeY / 2;    
    for(MapPosition p : generator.getRoomList()) {
      if(p.equals(generator.getStartRoom()) || p.equals(generator.getEndRoom())) continue;
      Monster monster = new Monster(p.x * sizeX + halfSizeX, p.y * sizeY + halfSizeY);
      entityMap.add(monster);
      if(random.nextInt(255) > 128) {
        Treasure treasure = new Treasure(p.x * sizeX + random.nextInt(sizeX-4)+2, p.y * sizeY + random.nextInt(sizeY-4) + 2);
        entityMap.add(treasure);
      }
      if(random.nextInt(255) > 128) {
        ChestMimic mimic = new ChestMimic(p.x * sizeX + random.nextInt(sizeX-4)+2, p.y * sizeY + random.nextInt(sizeY-4) + 2);
        entityMap.add(mimic);
      }
    } 
  }
  
  public void generate() {
    //Draw routings and rooms
    int halfSizeX = sizeX / 2;
    int halfSizeY = sizeY / 2;
    for(int y = 0; y < generator.getHeight(); ++y) {
      for(int x = 0; x < generator.getWidth(); ++x) {
        int baseX = x * sizeX;
        int baseY = y * sizeY;
        MapComponent component = generator.getMap()[y][x];
        if(component == MapComponent.EMPTY) continue;
        if(component.getTop()) {
          drawLine(tunnelCode, baseX + halfSizeX, baseY, baseX + halfSizeX, baseY + halfSizeY);
        }
        if(component.getBottom()) {
          drawLine(tunnelCode, baseX + halfSizeX, baseY + halfSizeY, baseX + halfSizeX, baseY + sizeY);
        }
        if(component.getLeft()) {
          drawLine(tunnelCode, baseX, baseY + halfSizeY, baseX + halfSizeX, baseY + halfSizeY);
        }
        if(component.getRight()) {
          drawLine(tunnelCode, baseX + halfSizeX, baseY + halfSizeY, baseX + sizeX, baseY + halfSizeY);
        }
        if(component == MapComponent.HORIZONTAL || component == MapComponent.VERTICAL) {
          // Generate small room, without walls
          int width = random.nextInt(halfSizeX/2-1)*2+1+2;
          int height = random.nextInt(halfSizeY/2-1)*2+1+2;
          drawRect(tunnelCode, baseX + halfSizeX - width/2, baseY + halfSizeY - height/2, width, height);
        }
      }
    }
    for(int i = 0; i < generator.getRoomList().length; ++i) {
      MapPosition position = generator.getRoomList()[i];
      //int startX = position.x * sizeX + random.nextInt(halfSizeX-2)+1;
      //int startY = position.y * sizeY + random.nextInt(halfSizeY-2)+1;
      //int endX = position.x * sizeX + halfSizeX + random.nextInt(halfSizeX-2);
      //int endY = position.x * sizeY + halfSizeY + random.nextInt(halfSizeY-2);
      //drawLine(floorCode, startX, startY, endX-1, endY-1);
      drawRect(wallCode, position.x * sizeX + 1, position.y * sizeY + 1, sizeX - 2, sizeY - 2);
      drawRect(floorCode, position.x * sizeX + 2, position.y * sizeY + 2, sizeX - 4, sizeY - 4);
      MapComponent component = generator.getMap()[position.y][position.x];
      if(component.getTop()) tilemap.get(position.x*sizeX + halfSizeX, position.y*sizeY + 1).setTileId(floorCode);
      if(component.getBottom()) tilemap.get(position.x*sizeX + halfSizeX, position.y*sizeY + sizeY - 2).setTileId(floorCode);
      if(component.getLeft()) tilemap.get(position.x*sizeX + 1, position.y*sizeY + halfSizeY).setTileId(floorCode);
      if(component.getRight()) tilemap.get(position.x*sizeX + sizeX - 2, position.y*sizeY + halfSizeY).setTileId(floorCode);
    }
    
  }
  
  protected void drawLine(int code, int x1, int y1, int x2, int y2) { 
    for(int y = Math.min(y1, y2); y <= Math.max(y1, y2); ++y) {
      for(int x = Math.min(x1, x2); x <= Math.max(x1, x2); ++x) {
        tilemap.get(x, y).setTileId(code);
      }
    }
  }
  
  protected void drawRect(int code, int x, int y, int w, int h) {
    for(int x1 = 0; x1 < w; ++x1) {
      for(int y1 = 0; y1 < h; ++y1) {
        tilemap.get(x + x1, y + y1).setTileId(code);
      }
    }
  }

}
