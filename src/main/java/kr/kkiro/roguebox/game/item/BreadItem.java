package kr.kkiro.roguebox.game.item;

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
    return "You ate Bread.";
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
