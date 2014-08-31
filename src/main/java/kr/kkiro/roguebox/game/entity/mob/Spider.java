package kr.kkiro.roguebox.game.entity.mob;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.InteractableEntity;
import kr.kkiro.roguebox.game.item.ItemPicker;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Spider extends Monster {
  
  public Spider(int x, int y) {
    super(x, y);
    this.defense = 4;
    this.strength = 4;
    this.health = 3;
  }
  
  @Override
  public String getName() {
    return _("spider");
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    g.setTextColor(ANSIColor.LIGHT_RED);
    g.setBackColor(ANSIColor.BLACK);
    DefinedIcon.put('S' ,_("spider"));
    g.drawChar(x, y, 'S'); 
  }
  
  @Override
  public void kill(InteractableEntity killer) {
    ItemPicker.spiderKill(((Character)killer).getInventory());
    super.kill(killer);
  }

}
