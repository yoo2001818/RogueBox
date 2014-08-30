package kr.kkiro.roguebox.game.status;

import kr.kkiro.roguebox.game.entity.Character;

public abstract class StatusEffect {

  protected int ticksLeft = 0;
  protected StatusManager manager;
  
  public StatusEffect(int ticksLeft) {
    this.ticksLeft = ticksLeft;
  }

  public abstract void tick();
  public abstract void dispose();
  public abstract void register();
  
  public int getTicksLeft() {
    return ticksLeft;
  }
  
  public void setTicksLeft(int ticksLeft) {
    this.ticksLeft = ticksLeft;
  }
  
  public boolean isAlive() {
    return ticksLeft >= 0;
  }
  
  public StatusManager getManager() {
    return manager;
  }
  
  public void setManager(StatusManager manager) {
    this.manager = manager;
  }
  
  public Character getCharacter() {
    return manager.character;
  }
}
