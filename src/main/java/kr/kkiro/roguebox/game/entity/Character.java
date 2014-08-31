package kr.kkiro.roguebox.game.entity;

import static kr.kkiro.roguebox.util.I18n._;

import java.util.EnumMap;

import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.TileEntry;
import kr.kkiro.roguebox.game.entity.mob.Monster;
import kr.kkiro.roguebox.game.item.EquipType;
import kr.kkiro.roguebox.game.item.Inventory;
import kr.kkiro.roguebox.game.item.ItemEntryEquippable;
import kr.kkiro.roguebox.game.status.StatusManager;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;

public class Character extends InteractableEntity {

  protected Inventory inventory;
  protected EnumMap<EquipType, ItemEntryEquippable> equipList;
  protected StatusManager status;
  public int maxHealth = 20;
  
  public Character(int x, int y, Inventory inventory) {
    super(x, y);
    this.equipList = new EnumMap<EquipType, ItemEntryEquippable>(EquipType.class);
    this.inventory = inventory;
    inventory.setCharacter(this);
    this.status = new StatusManager();
    status.setCharacter(this);
    this.health = 20;
    this.strength = 5;
    this.defense = 5;
  }
  
  public EnumMap<EquipType, ItemEntryEquippable> getEquipList() {
    return equipList;
  }

  public Inventory getInventory() {
    return inventory;
  }
  
  public StatusManager getStatus() {
    return status;
  }
  
  @Override
  public void render(TextGraphics g, int x, int y) {
    DefinedIcon.put('@', _("character"));
    g.setTextColor(ANSIColor.LIGHT_YELLOW);
    g.setBackColor(ANSIColor.BLACK);
    g.drawChar(x, y, '@');
  }
  
  @Override
  public String getName() {
    return _("character");
  }
  
  @Override
  public void damage(InteractableEntity killer) {
    int prevHealth = health;
    super.damage(killer);
    if(prevHealth != health) {
      getEntityMap().getMap().setMessage(_("playerHitSuccess", killer.getName()), 10);
    } else {
      getEntityMap().getMap().setMessage(_("playerHitFail", killer.getName()), 10);
    }
  }
  
  /*@Override
  public boolean translate(int x, int y) {
    Entity others = getEntityMap().get(this.x+x, this.y+y);
    if(others != null) {
      if(others instanceof Monster) {
        getEntityMap().remove(others);
        getEntityMap().getMap().setMessage(_("monsterKill"));
        getInventory().obtainItem(2, 1);
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
  }*/
  
  @Override
  public void interact(Entity e) {
    if(e instanceof Monster) {
      ((Monster) e).damage(this);
    }
  }
  
  public void heal(int amount) {
    this.health += amount;
    this.health = Math.min(this.maxHealth, this.health);
  }

  @Override
  public void tick() {
    if(this.health <= 3) {
      getEntityMap().getMap().setMessage(_("healthLow"), 40);
    }
    status.tick();
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
    if(entry.getTileId() == 3) loss = 10;
    if(entry.getTileId() != 3 && entry.getRelative(px, py).getTileId() == 3) loss = 36;
    entry.revealed = true;
    entry.setTileData(ttl/6);
    if(px != -1) revealMap(entry.getRelative(-1, 0), ttl - loss, 1, py);
    if(px != 1) revealMap(entry.getRelative(1, 0), ttl - loss, -1, py);
    if(py != -1) revealMap(entry.getRelative(0, -1), ttl - loss, px, 1);
    if(py != 1) revealMap(entry.getRelative(0, 1), ttl - loss, px, -1);
  }

}
