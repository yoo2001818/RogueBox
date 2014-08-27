package kr.kkiro.roguebox.game.entity;

import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.EntityMap;

public abstract class Entity {
  
  protected EntityMap entityMap;
  protected int x, y;
  
  public Entity(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public EntityMap getEntityMap() {
    return entityMap;
  }
  
  public void setEntityMap(EntityMap entityMap) {
    this.entityMap = entityMap;
  }
  
  public int getX() {
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public boolean translate(int x, int y) {
    this.x += x;
    this.y += y;
    return true;
  }
  
  public void interact(Entity e) {
  }
  public abstract void render(TextGraphics g, int x, int y);
  public abstract void tick();
}
