package kr.kkiro.roguebox.game;

public abstract class StatusEffect {

  protected int ticksLeft = 0;
  
  public StatusEffect(int ticksLeft) {
    this.ticksLeft = ticksLeft;
  }

  public abstract void tick();
  public abstract void dispose();
  public abstract void register();
  
  public int getTicksLeft() {
    return ticksLeft;
  }
  
  public boolean isAlive() {
    return ticksLeft >= 0;
  }
}
