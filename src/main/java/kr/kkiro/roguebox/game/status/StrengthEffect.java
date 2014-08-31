package kr.kkiro.roguebox.game.status;

import kr.kkiro.roguebox.util.I18n;

public class StrengthEffect extends StatusEffect {

  public StrengthEffect(int ticksLeft) {
    super(ticksLeft);
  }

  @Override
  public void tick() {
  }

  @Override
  public void dispose() {
    getCharacter().strength -= 5;
  }

  @Override
  public void register() {
    getCharacter().strength += 5;
  }
  
  public String getName() {
    return I18n._("strengthEffect");
  };

}
