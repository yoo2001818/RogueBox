package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Treasure extends Entity {

  public Treasure(int x, int y) {
    super(x, y);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('$' ,_("treasure"));
    g.setTextColor(ANSIColor.LIGHT_YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '$');
  }

  @Override
  public void interact(Entity e) {
    if(e instanceof Character) {
      getEntityMap().getMap().setMessage(_("treasureFound"), 100);
      ((Character)e).getInventory().obtainItem(3, 1);
      this.setRemoval(true);
    }
  }
  
  @Override
  public void tick() {
  }

  @Override
  public String getName() {
    return _("treasure");
  }
}
