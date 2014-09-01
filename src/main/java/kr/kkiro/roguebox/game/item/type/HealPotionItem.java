package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionItem;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.HealEffect;
import kr.kkiro.roguebox.game.status.StatusEffect;

public class HealPotionItem extends PotionItem {

  public HealPotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public StatusEffect getEffect() {
    return new HealEffect(10);
  }


}
