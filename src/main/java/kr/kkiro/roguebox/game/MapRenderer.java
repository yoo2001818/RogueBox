package kr.kkiro.roguebox.game;

import kr.kkiro.roguebox.curses.CInteractable;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Entity;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class MapRenderer extends CInteractable {

  protected Map map;
  public int cameraX, cameraY;
  
  public MapRenderer(Map map, int width, int height) {
    this.map = map;
    this.width = width;
    this.height = height;
  }

  @Override
  public void render(TextGraphics g) {
    DefinedIcon.clear();
    g.setBackColor(ANSIColor.BLACK);
    g.fillRect();
    //Draw tilemap
    TileMap tileMap = map.getTileMap();
    //Relocate Camera
    //cameraX = Math.max(0, Math.min(tileMap.getWidth()-getWidth(g), cameraX));
    //cameraY = Math.max(0, Math.min(tileMap.getHeight()-getHeight(g), cameraY));
    int maxX = cameraX + getWidth(g);
    int maxY = cameraY + getHeight(g);
    for(int y = cameraY; y < maxY; ++y) {
      for(int x = cameraX; x < maxX; ++x) {
        TileEntry tile = tileMap.get(x, y);
        if(tile == null) continue;
        if(!tile.revealed) continue;
        tile.getTile().onRender(tile, g, x-cameraX, y-cameraY);
      }
    }
    //Draw Entity
    EntityMap entityMap = map.getEntityMap();
    for(Entity entity : entityMap) {
      if(entity.getX() >= cameraX && entity.getX() < maxX && 
          entity.getY() >= cameraY && entity.getY() < maxY) {
        TileEntry tile = tileMap.get(entity.getX(), entity.getY());
        if(tile == null) continue;
        if(!tile.revealed) continue;
        entity.render(g, entity.getX()-cameraX, entity.getY()-cameraY);
      }
    }
  }
  
  public Map getMap() {
    return map;
  }
  
  public void setMap(Map map) {
    this.map = map;
  }
  
}
