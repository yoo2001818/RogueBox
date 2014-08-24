package kr.kkiro.roguebox.game.tile;

import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.game.entity.Entity;

public abstract class Tile {
  
  public abstract void onChange(TileEntry entry);
  public abstract boolean onInteract(TileEntry entry, Entity entity);
  public abstract boolean isWalkable(TileEntry entry, Entity entity);
  public abstract void onRender(TileEntry entry, TextGraphics g, int x, int y);
  
}
