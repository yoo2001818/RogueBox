package kr.kkiro.roguebox.game;

public abstract class StatusEffect {

  protected int ticksLeft = 0;
  
  public StatusEffect(int ticksLeft) {
    this.ticksLeft = ticksLeft;
  }

}
