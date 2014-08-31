package kr.kkiro.roguebox.game.entity.mob;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.InteractableEntity;
import kr.kkiro.roguebox.game.item.ItemPicker;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Rat extends Monster {
  
  public Rat(int x, int y) {
    super(x, y);
    this.defense = 5;
    this.strength = 2;
    this.health = 2;
  }
  
  @Override
  public String getName() {
    return _("rat");
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    g.setTextColor(ANSIColor.LIGHT_RED);
    g.setBackColor(ANSIColor.BLACK);
    DefinedIcon.put('R' ,_("rat"));
    g.drawChar(x, y, 'R'); 
  }
  
  @Override
  public void kill(InteractableEntity killer) {
    ItemPicker.ratKill(((Character)killer).getInventory());
    super.kill(killer);
  }

}
