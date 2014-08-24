package kr.kkiro.roguebox.game.tile;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.game.entity.Entity;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class TunnelTile extends Tile {

  @Override
  public void onChange(TileEntry entry) {
  }

  @Override
  public boolean onInteract(TileEntry entry, Entity entity) {
    return true;
  }

  @Override
  public boolean isWalkable(TileEntry entry, Entity entity) {
    return true;
  }

  @Override
  public void onRender(TileEntry entry, TextGraphics g, int x, int y) {
    DefinedIcon.put('#', _("tunnel"));
    g.setTextColor(ANSIColor.GRAY);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '#');
  }

}
