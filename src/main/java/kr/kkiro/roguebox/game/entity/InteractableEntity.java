package kr.kkiro.roguebox.game.entity;

import java.util.Random;

import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.util.RandomProvider;

public abstract class InteractableEntity extends Entity {

  public int health = 4;
  public int strength = 2;
  public int defense = 2;
  
  protected Random random = RandomProvider.getRandom();
  
  public InteractableEntity(int x, int y) {
    super(x, y);
  }
  
  @Override
  public boolean translate(int x, int y) {
    TileEntry tile = getEntityMap().getMap().getTileMap().get(this.x+x, this.y+y);
    if(!tile.getTile().isWalkable(tile, this)) return false;
    Entity others = getEntityMap().get(this.x+x, this.y+y);
    if(others != null) {
      interact(others);
      if(!(others instanceof InteractableEntity)) {
        others.interact(this);
      }
      return true;
    }
    super.translate(x, y);
    tile.getTile().onInteract(tile, this);
    return true;
  }
  
  public void damage(InteractableEntity killer) {
    if(random.nextInt(Math.max(0, this.defense) + Math.max(0, killer.strength)) <= Math.max(0, killer.strength)) {
      this.health -= 1;
      if(this.health <= 0) kill(killer);
    }
  }
  
  public void kill(InteractableEntity killer) {
    
  }
  

}
