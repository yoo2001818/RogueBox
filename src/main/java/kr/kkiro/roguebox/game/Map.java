package kr.kkiro.roguebox.game;

import java.util.Iterator;

import kr.kkiro.roguebox.game.entity.Entity;

public class Map {
  
  protected TileMap tileMap;
  protected EntityMap entityMap;
  protected String message;
  protected int messagePriority;
  public boolean cleared = false;
  
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
  
  public void setMessage(String message, int priority) {
    if(this.messagePriority > priority) return;
    this.message = message;
    this.messagePriority = priority;
  }
  
  public int getMessagePriority() {
    return messagePriority;
  }
  
  public void setMessagePriority(int messagePriority) {
    this.messagePriority = messagePriority;
  }
  
  public void tick() {
    Iterator<Entity> iterator = entityMap.iterator();
    while(iterator.hasNext()) {
      Entity e = iterator.next();
      e.tick();
      if(e.isRemoval()) iterator.remove();
    }
  }
  
}
