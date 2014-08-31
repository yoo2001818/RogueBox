package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.StrengthEffect;
import kr.kkiro.roguebox.util.I18n;

public class StrengthPotionItem extends ItemEntryUseable {

  public StrengthPotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().getStatus().add(new StrengthEffect(20));
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("drinkMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
    // TODO Auto-generated method stub

  }

}
