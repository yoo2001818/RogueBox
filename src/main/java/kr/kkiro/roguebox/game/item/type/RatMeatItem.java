package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.status.PoisonEffect;
import kr.kkiro.roguebox.util.I18n;

public class RatMeatItem extends ItemEntryUseable {

  public RatMeatItem() {
    super(I18n._("ratMeat"), ItemType.FOOD);
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().heal(6);
    stack.getInventory().getCharacter().getStatus().add(new PoisonEffect(3));
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("eatMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
