package kr.kkiro.roguebox.game.entity.mob;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.InteractableEntity;
import kr.kkiro.roguebox.game.item.ItemPicker;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Zombie extends Monster {
  
  public Zombie(int x, int y) {
    super(x, y);
    this.defense = 3;
    this.strength = 1;
    this.health = 4;
  }
  
  @Override
  public String getName() {
    return _("zombie");
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    g.setTextColor(ANSIColor.LIGHT_GREEN);
    g.setBackColor(ANSIColor.BLACK);
    DefinedIcon.put('Z' ,_("zombie"));
    g.drawChar(x, y, 'Z'); 
  }
  
  @Override
  public void kill(InteractableEntity killer) {
    ItemPicker.zombieKill(((Character)killer).getInventory());
    super.kill(killer);
  }

}
