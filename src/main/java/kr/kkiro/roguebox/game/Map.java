package kr.kkiro.roguebox.game;

import kr.kkiro.roguebox.game.entity.Entity;

public class Map {
  
  protected TileMap tileMap;
  protected EntityMap entityMap;
  protected String message;
  
  public Map(TileMap tileMap, EntityMap entityMap) {
    this.tileMap = tileMap;
    this.entityMap = entityMap;
    tileMap.setMap(this);
    entityMap.setMap(this);
  }
  
  public EntityMap getEntityMap() {
    return entityMap;
  }
  
  public TileMap getTileMap() {
    return tileMap;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public void tick() {
    for(Entity e : entityMap) {
      e.tick();
    }
  }
  
}
