package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemEntry;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.util.I18n;

public class TreasureItem extends ItemEntry {

  public TreasureItem() {
    super(I18n._("treasure"), ItemType.TREASURE);
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
