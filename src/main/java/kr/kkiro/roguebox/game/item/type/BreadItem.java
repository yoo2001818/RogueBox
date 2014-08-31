package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.util.I18n;

public class BreadItem extends ItemEntryUseable {

  public BreadItem() {
    super(I18n._("bread"), ItemType.FOOD);
  }
  
  @Override
  public String getDesc() {
    return I18n._("breadDesc"); 
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().heal(6);
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("eatMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
