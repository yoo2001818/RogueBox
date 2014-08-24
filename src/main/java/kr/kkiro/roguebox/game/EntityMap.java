package kr.kkiro.roguebox.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.kkiro.roguebox.game.entity.Entity;

public class EntityMap implements Iterable<Entity> {
  
  protected List<Entity> list;
  protected Map map;
  
  public EntityMap() {
    list = new ArrayList<Entity>();
  }
  
  public Map getMap() {
    return map;
  }
  
  public void setMap(Map map) {
    this.map = map;
  }

  public int size() {
    return list.size();
  }

  public boolean contains(Entity o) {
    return list.contains(o);
  }

  @Override
  public Iterator<Entity> iterator() {
    return list.iterator();
  }

  public boolean add(Entity e) {
    e.setEntityMap(this);
    return list.add(e);
  }

  public boolean remove(Entity o) {
    return list.remove(o);
  }

  public void clear() {
    list.clear();
  }

  public Entity get(int index) {
    return list.get(index);
  }
  
  public Entity get(int x, int y) {
    for(Entity e : this) {
      if(e.getX() == x && e.getY() == y) return e;
    }
    return null;
  }

  public Entity remove(int index) {
    return list.remove(index);
  }

  public int indexOf(Entity o) {
    return list.indexOf(o);
  }

  public int lastIndexOf(Entity o) {
    return list.lastIndexOf(o);
  }
  
  
  
}
