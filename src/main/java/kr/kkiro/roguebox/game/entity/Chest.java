package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.item.ItemPicker;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Chest extends Entity {

  public Chest(int x, int y) {
    super(x, y);
  }

  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('C' ,_("chest"));
    g.setTextColor(ANSIColor.LIGHT_YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, 'C');
  }

  @Override
  public void interact(Entity e) {
    if(e instanceof Character) {
      getEntityMap().getMap().setMessage(_("chestOpen"), 100);
      ItemPicker.chestOpen(((Character)e).getInventory());
      this.setRemoval(true);
    }
  }
  
  @Override
  public void tick() {
  }

  @Override
  public String getName() {
    return _("chest");
  }
}
