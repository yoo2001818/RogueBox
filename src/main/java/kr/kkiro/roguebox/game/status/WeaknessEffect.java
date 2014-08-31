package kr.kkiro.roguebox.game.status;

import kr.kkiro.roguebox.util.I18n;

public class WeaknessEffect extends StatusEffect {

  public WeaknessEffect(int ticksLeft) {
    super(ticksLeft);
  }

  @Override
  public void tick() {
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
    return I18n._("weaknessEffect");
  };

}
