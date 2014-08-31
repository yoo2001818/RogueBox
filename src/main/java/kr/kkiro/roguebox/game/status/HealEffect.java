package kr.kkiro.roguebox.game.status;

import kr.kkiro.roguebox.util.I18n;

public class HealEffect extends StatusEffect {

  public HealEffect(int ticksLeft) {
    super(ticksLeft);
  }

  @Override
  public void tick() {
    getCharacter().heal(1);
  }

  @Override
  public void dispose() {
    getCharacter().strength += 2;
  }

  @Override
  public void register() {
    getCharacter().strength -= 2;
  }
  
  public String getName() {
    return I18n._("healEffect");
  };

}
