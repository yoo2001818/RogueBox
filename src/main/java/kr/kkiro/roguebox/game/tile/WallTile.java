package kr.kkiro.roguebox.game.tile;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class WallTile extends BlankTile {

  @Override
  public void onRender(TileEntry entry, TextGraphics g, int x, int y) {
    DefinedIcon.put('+', _("wall"));
    g.setTextColor(ANSIColor.WHITE);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '+');
  }

}
