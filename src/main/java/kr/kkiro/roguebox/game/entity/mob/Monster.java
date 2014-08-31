package kr.kkiro.roguebox.game.entity.mob;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.Entity;
import kr.kkiro.roguebox.game.entity.InteractableEntity;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Monster extends InteractableEntity {

  public boolean mad = false;
  
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

  @Override
  public void interact(Entity e) {
    if(e instanceof Character) {
      ((Character) e).damage(this);
    }
  }
  
  @Override
  public void damage(InteractableEntity killer) {
    int prevHealth = health;
    super.damage(killer);
    if(killer instanceof Character) {
      if(prevHealth != health) {
        getEntityMap().getMap().setMessage(_("monsterHitSuccess", getName()), 15);
      } else {
        getEntityMap().getMap().setMessage(_("monsterHitFail", getName()), 15);
      }
    }
  }
 
  @Override
  public void kill(InteractableEntity killer) {
    getEntityMap().getMap().setMessage(_("monsterKill", getName()), 16); 
    /*if(killer instanceof Character) {
      Character c = (Character) killer;
    }*/
    this.setRemoval(true);
  }
  
  public void processCharacter(Character c) {
    int diffX = c.getX() - this.x;
    int diffY = c.getY() - this.y;
    if(Math.abs(diffX) + Math.abs(diffY) > 20) return;
    if(Math.abs(diffX) + Math.abs(diffY) <= 6) {
      if(Math.abs(diffX) + Math.abs(diffY) == 6) return;
      diffX = -diffX;
      diffY = -diffY;
      if(Math.abs(diffX) + Math.abs(diffY) <= 3) {
        mad = true;
        diffX = -diffX;
        diffY = -diffY;
      }
    } else {
      mad = false;
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
  
  @Override
  public void tick() {
    for(Entity e : entityMap) {
      if(e instanceof Character) {
        Character c = (Character) e;
        processCharacter(c);
      }
    }
  }

  @Override
  public String getName() {
    return _("monster");
  }

}
