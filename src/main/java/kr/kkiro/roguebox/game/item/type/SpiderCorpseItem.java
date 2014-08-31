package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntryUseable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.status.PoisonEffect;
import kr.kkiro.roguebox.game.status.WeaknessEffect;
import kr.kkiro.roguebox.util.I18n;

public class SpiderCorpseItem extends ItemEntryUseable {

  public SpiderCorpseItem() {
    super(I18n._("spiderCorpse"), ItemType.FOOD);
  }

  @Override
  public String use(ItemStack stack) {
    stack.getInventory().getCharacter().heal(2);
    stack.getInventory().getCharacter().getStatus().add(new PoisonEffect(5));
    stack.getInventory().getCharacter().getStatus().add(new WeaknessEffect(3));
    stack.getInventory().removeItem(stack.getCode(), 1);
    return I18n._("eatMsg", getName());
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
