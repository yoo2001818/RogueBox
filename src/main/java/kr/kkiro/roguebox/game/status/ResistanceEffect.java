package kr.kkiro.roguebox.game.status;

import kr.kkiro.roguebox.util.I18n;

public class ResistanceEffect extends StatusEffect {

  public ResistanceEffect(int ticksLeft) {
    super(ticksLeft);
  }

  @Override
  public void tick() {
  }

  @Override
  public void dispose() {
    getCharacter().defense -= 5;
  }

  @Override
  public void register() {
    getCharacter().defense += 5;
  }
  
  public String getName() {
    return I18n._("resistanceEffect");
  };

}
