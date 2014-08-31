package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.ResistanceEffect;
import kr.kkiro.roguebox.util.I18n;

public class ResistancePotionItem extends ItemEntryUseable {

  public ResistancePotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().getStatus().add(new ResistanceEffect(15));
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("drinkMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
    // TODO Auto-generated method stub

  }

}
