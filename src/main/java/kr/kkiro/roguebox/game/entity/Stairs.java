package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Stairs extends Entity {

  public Stairs(int x, int y) {
    super(x, y);
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('t', _("stairs"));
    g.setTextColor(ANSIColor.CYAN);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, 't');
  }

  @Override
  public void tick() {
  }

}
