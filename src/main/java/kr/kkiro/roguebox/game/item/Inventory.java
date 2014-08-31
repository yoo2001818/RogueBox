package kr.kkiro.roguebox.game.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.kkiro.roguebox.game.entity.Character;

public class Inventory implements Iterable<ItemStack> {
  
  protected List<ItemStack> contents;
  protected ItemBank bank;
  protected Character character;
  
  public Inventory(ItemBank bank) {
    this.bank = bank;
    contents = new ArrayList<ItemStack>();
  }
  
  public ItemBank getBank() {
    return bank;
  } 
  
  public Character getCharacter() {
    return character;
  }
  
  public void setCharacter(Character character) {
    this.character = character;
  }
  
  @Override
  public Iterator<ItemStack> iterator() {
    return contents.iterator();
  }
  
  public boolean contains(Object o) {
    return contents.contains(o);
  }

  public boolean add(ItemStack e) {
    return contents.add(e);
  }

  public ItemStack get(int index) {
    return contents.get(index);
  }
  
  public List<ItemStack> getContents() {
    return contents;
  }

  public ItemStack getStack(int code) {
    for(ItemStack stack : this) {
      if(stack.code == code) return stack;
    }
    return null;
  }
  
  public ItemStack getStack(ItemEntry type) {
    return getStack(getBank().indexOf(type));
  }
  
  public void obtainItem(int code, int quantity) {
    if(quantity <= 0) return;
    ItemStack stack = getStack(code);
    if(stack == null) {
      stack = new ItemStack(this, code, quantity);
      contents.add(stack);
      for(int i = 0; i < quantity; ++i) {
        stack.getItem().obtain(stack);
      }
      return;
    }
    stack.setQuantity(quantity + stack.getQuantity());
    for(int i = 0; i < quantity; ++i) {
      stack.getItem().obtain(stack);
    }
  }
  
  public void removeItem(int code, int quantity) {
    if(quantity <= 0) return;
    ItemStack stack = getStack(code);
    if(stack == null) return;
    stack.setQuantity(stack.getQuantity() - quantity);
    if(stack.getQuantity() <= 0) contents.remove(stack);
  }
  
  public String equipItem(ItemStack stack) {
    if(stack.isEquipable()) {
      ItemEntryEquippable previous = getCharacter().getEquipList().get(((ItemEntryEquippable)stack.getItem()).getEquipType());
      if(previous != null) {
        unEquipItem(previous);
      }
      removeItem(stack.getCode(), 1);
      getCharacter().getEquipList().put(((ItemEntryEquippable)stack.getItem()).getEquipType(), (ItemEntryEquippable)(stack.getItem()));
      return stack.equip();
    }
    return null;
  }
  
  public String unEquipItem(ItemEntryEquippable item) {
    obtainItem(getBank().indexOf(item), 1);
    getCharacter().getEquipList().remove(item.getEquipType());
    return item.unEquip(getStack(item));    
  }
}
