package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Door extends Entity {

  public Door(int x, int y) {
    super(x, y);
  }

  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('D', _("door"));
    g.setTextColor(ANSIColor.YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, 'D');
  }

  @Override
  public void tick() {
  }
  
  @Override
  public String getName() {
    return _("door");
  }
  
  @Override
  public void interact(Entity e) {
    if(e instanceof Character) {
      getEntityMap().getMap().setMessage(_("doorOpen"), 100);
      this.setRemoval(true);
    }
  }


}
