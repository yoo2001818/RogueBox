package kr.kkiro.roguebox.game.tile;

import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.game.entity.Entity;
import kr.kkiro.roguebox.util.ANSIColor;

public class BlankTile extends Tile {

  @Override
  public void onChange(TileEntry entry) {
  }

  @Override
  public boolean onInteract(TileEntry entry, Entity entity) {
    return false;
  }

  @Override
  public boolean isWalkable(TileEntry entry, Entity entity) {
    return false;
  }

  @Override
  public void onRender(TileEntry entry, TextGraphics g, int x, int y) {
    g.setTextColor(ANSIColor.WHITE);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, ' ');
  }
  

}
