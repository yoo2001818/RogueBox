package kr.kkiro.roguebox.game.entity.mob;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.InteractableEntity;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class ChestMimic extends Monster {

  protected boolean coverBlown = false;
  
  public ChestMimic(int x, int y) {
    super(x, y);
    this.defense = 0;
    this.strength = 3;
    this.health = 6;
  }
  
  @Override
  public String getName() {
    if(!coverBlown) {
      return _("chest");
    }
    return _("mimic");
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    g.setTextColor(ANSIColor.LIGHT_YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    if(!coverBlown) {
      DefinedIcon.put('C' ,_("chest"));
      g.drawChar(x, y, 'C');
    } else {
      DefinedIcon.put('c' ,_("mimic"));
      g.drawChar(x, y, 'c'); 
    }
  }
  
  @Override
  public void processCharacter(Character c) {
    if(coverBlown) {
      //Just head to character.
      int diffX = c.getX() - this.x;
      int diffY = c.getY() - this.y;
      if(diffX < 0) {
        this.translate(-1, 0);
      } else if(diffX > 0) {
        this.translate(1, 0);
      }
      if(diffY < 0) {
        this.translate(0, -1);
      } else if(diffY > 0) {
        this.translate(0, 1);
      }   
    }
  }
  
  @Override
  public void kill(InteractableEntity killer) {
    super.kill(killer);
  }

  @Override
  public void damage(InteractableEntity killer) {
    if(!coverBlown) {
      coverBlown = true;
      getEntityMap().getMap().setMessage(_("mimicReveal"), 100);
    }
    super.damage(killer);
  }

}
