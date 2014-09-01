package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionItem;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.PoisonEffect;
import kr.kkiro.roguebox.game.status.StatusEffect;

public class PoisonPotionItem extends PotionItem {

  public PoisonPotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public StatusEffect getEffect() {
    return new PoisonEffect(5);
  }

}
