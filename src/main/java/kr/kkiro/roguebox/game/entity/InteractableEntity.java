package kr.kkiro.roguebox.game.entity;

import kr.kkiro.roguebox.game.TileEntry;

public abstract class InteractableEntity extends Entity {

  public InteractableEntity(int x, int y) {
    super(x, y);
  }
  
  @Override
  public boolean translate(int x, int y) {
    TileEntry tile = getEntityMap().getMap().getTileMap().get(this.x+x, this.y+y);
    if(!tile.getTile().isWalkable(tile, this)) return false;
    Entity others = getEntityMap().get(this.x+x, this.y+y);
    if(others != null) return true;
    super.translate(x, y);
    tile.getTile().onInteract(tile, this);
    return true;
  }

}
