package kr.kkiro.roguebox.game.status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.kkiro.roguebox.game.entity.Character;

public class StatusManager implements Iterable<StatusEffect> {

  protected List<StatusEffect> contents = new ArrayList<StatusEffect>();
  protected Character character;
  
  public StatusManager() {
  }
  
  public void setCharacter(Character character) {
    this.character = character;
  }
  
  public Character getCharacter() {
    return character;
  }
  
  public List<StatusEffect> getContents() {
    return contents;
  }
  
  public void add(StatusEffect effect) {
    contents.add(effect);
    effect.register();
  }
  
  public void tick() {
    Iterator<StatusEffect> iterator = this.iterator();
    while(iterator.hasNext()) {
      StatusEffect effect = iterator.next();
      effect.tick();
      effect.setTicksLeft(effect.getTicksLeft()-1);
      if(!effect.isAlive()) {
        effect.dispose();
        iterator.remove();
      }
    }
  }
  
  @Override
  public Iterator<StatusEffect> iterator() {
    return contents.iterator();
  }

}
