package kr.kkiro.roguebox.game.item;

import kr.kkiro.roguebox.game.status.StatusEffect;
import kr.kkiro.roguebox.util.I18n;

public abstract class PotionItem extends ItemEntryUseable {

  protected boolean eaten = false;
  
  public PotionItem(String name, ItemType type) {
    super(name, type);
  }

  public abstract StatusEffect getEffect();
  
  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().getStatus().add(getEffect());
    stack.getInventory().removeItem(stack.getCode(), 1);
    eaten = true;
    return I18n._("drinkMsg", getName());
  }
  
  @Override
  public String getDesc() {
    if(eaten) {
      return getEffect().getName();
    }
    return super.getDesc();
  }

  @Override
  public void obtain(ItemStack stack) {
    // TODO Auto-generated method stub

  }

}
