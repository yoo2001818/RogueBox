package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Monster extends InteractableEntity {

  public Monster(int x, int y) {
    super(x, y);
  }

  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('%', _("monster"));
    g.setTextColor(ANSIColor.LIGHT_GREEN);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '%');
  }

  private int showedMessage = 0;
  private boolean mad = false;
  
  @Override
  public void tick() {
    for(Entity e : entityMap) {
      if(e instanceof Character) {
        Character c = (Character) e;
        int diffX = c.x - this.x;
        int diffY = c.y - this.y;
        if(Math.abs(diffX) + Math.abs(diffY) > 20) return;
        if(Math.abs(diffX) + Math.abs(diffY) <= 6) {
          if(showedMessage <= 0) {
            showedMessage = 20;
            getEntityMap().getMap().setMessage(_("monsterGrowl"));
          }
          if(Math.abs(diffX) + Math.abs(diffY) == 6) return;
          diffX = -diffX;
          diffY = -diffY;
          if(Math.abs(diffX) + Math.abs(diffY) <= 3) {
            if(mad == false) {
              getEntityMap().getMap().setMessage(_("monsterAttack")); 
            }
            mad = true;
            diffX = -diffX;
            diffY = -diffY;
          }
          if(Math.abs(diffX) + Math.abs(diffY) <= 1) { 
            getEntityMap().getMap().setMessage(_("characterOuch")); 
          }
        } else {
          mad = false;
          showedMessage -= 1;
        }
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
  }

}
