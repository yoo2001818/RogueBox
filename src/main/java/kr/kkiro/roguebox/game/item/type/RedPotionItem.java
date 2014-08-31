package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.status.HealEffect;
import kr.kkiro.roguebox.util.I18n;

public class RedPotionItem extends ItemEntryUseable {

  public RedPotionItem() {
    super(_("redPotion"), ItemType.POTION);
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().getStatus().add(new HealEffect(10));
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("drinkMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
    // TODO Auto-generated method stub

  }

}
