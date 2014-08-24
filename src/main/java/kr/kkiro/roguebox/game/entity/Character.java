package kr.kkiro.roguebox.game.entity;

import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;
import static kr.kkiro.roguebox.util.I18n._;

public class Character extends InteractableEntity {

  public Character(int x, int y) {
    super(x, y);
  }

  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('@', _("character"));
    g.setTextColor(ANSIColor.LIGHT_YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '@');
  }
  
  @Override
  public boolean translate(int x, int y) {
    Entity others = getEntityMap().get(this.x+x, this.y+y);
    if(others != null) {
      if(others instanceof Monster) {
        getEntityMap().remove(others);
        getEntityMap().getMap().setMessage(_("monsterKill"));
      }
      if(others instanceof Stairs) {
        getEntityMap().getMap().setMessage(_("gameClearPrototype"));
      }
      if(others instanceof Treasure) {
        getEntityMap().remove(others);
        getEntityMap().getMap().setMessage(_("treasureFound"));
      }
    }
    return super.translate(x, y);
  }

  @Override
  public void tick() {
  }
  
  public void revealMap(int range) {
    TileEntry entry = getEntityMap().getMap().getTileMap().get(x, y);
    revealMap(entry, 60, 0, 0);
  }
  
  public void revealMap(TileEntry entry, int ttl, int px, int py) {
    if(ttl < 0) {
      return;
    }
    if(entry.getTileId() == 0) return;
    int loss = 6;
    //if(px == 0 || py == 0) loss = 10;
    //if(entry.getTileId() == 3) loss = 3;
    //if(entry.revealed) loss = 6;
    if(entry.getTileId() == 3) loss = 10;
    if(entry.getTileId() != 3 && entry.getRelative(px, py).getTileId() == 3) loss = 36;
    entry.revealed = true;
    entry.setTileData(ttl/6);
    if(px != -1) revealMap(entry.getRelative(-1, 0), ttl - loss, 1, py);
    if(px != 1) revealMap(entry.getRelative(1, 0), ttl - loss, -1, py);
    if(py != -1) revealMap(entry.getRelative(0, -1), ttl - loss, px, 1);
    if(py != 1) revealMap(entry.getRelative(0, 1), ttl - loss, px, -1);
    /*if(px != -1 && py != -1) revealMap(entry.getRelative(-1, -1), ttl - loss, 1, 1);
    if(px != -1 && py != 1) revealMap(entry.getRelative(-1, 1), ttl - loss, 1, -1);
    if(px != 1 && py != -1) revealMap(entry.getRelative(1, -1), ttl - loss, -1, 1);
    if(px != 1 && py != 1) revealMap(entry.getRelative(1, 1), ttl - loss, -1, -1);*/
  }

}
