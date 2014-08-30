package kr.kkiro.roguebox.curses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CContainer extends CInteractable implements Iterable<CComponent> {

  protected List<CComponent> childrens = new ArrayList<CComponent>();
  
  @Override
  public void render(TextGraphics g) {
    for(CComponent c : childrens) {
      if(!c.visible) continue;
      c.render(g.subSet(c.x, c.y, c.getWidth(g), c.getHeight(g)));
    }
  }

  public int size() {
    return childrens.size();
  }

  public boolean contains(Object o) {
    return childrens.contains(o);
  }

  public boolean add(CComponent e) {
    e.setParent(this);
    return childrens.add(e);
  }

  public boolean remove(Object o) {
    return childrens.remove(o);
  }

  public void clear() {
    childrens.clear();
  }

  public CComponent get(int index) {
    return childrens.get(index);
  }

  public CComponent set(int index, CComponent element) {
    return childrens.set(index, element);
  }

  public void add(int index, CComponent element) {
    element.setParent(this);
    childrens.add(index, element);
  }

  public CComponent remove(int index) {
    return childrens.remove(index);
  }

  public int indexOf(Object o) {
    return childrens.indexOf(o);
  }
  
  public CInteractable prevFocus() {
    boolean searchedEnt = false;
    ListIterator<CComponent> it = childrens.listIterator(childrens.size());
    while(it.hasPrevious()) {
      CComponent c = it.previous();
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(searchedEnt) {
          if(!ci.isEnabled()) continue;
          ci.setFocused(true);
          if(ci instanceof CContainer) {
            CContainer cc = (CContainer)ci;
            CInteractable entry = cc.prevFocus();
            if(entry != null) return entry;
          }
          return ci;
        }
        if(ci.isFocused()) {
          searchedEnt = true;
          if(ci instanceof CContainer) {
            CContainer cc = (CContainer)ci;
            CInteractable entry = cc.prevFocus();
            if(entry != null) return entry;
          }
          ci.setFocused(false);
        }
      }
    }
    //Failback
    it = childrens.listIterator(childrens.size());
    while(it.hasPrevious()) {
      CComponent c = it.previous();
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(!ci.isEnabled()) continue;
        ci.setFocused(true);
        if(ci instanceof CContainer) {
          CContainer cc = (CContainer)ci;
          CInteractable entry = cc.prevFocus();
          if(entry != null) return entry;
        }
        return ci;
      }
    }
    return null;
  }
  
  public CInteractable nextFocus() {
    boolean searchedEnt = false;
    for(CComponent c : childrens) {
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(searchedEnt) {
          if(!ci.isEnabled()) continue;
          ci.setFocused(true);
          if(ci instanceof CContainer) {
            CContainer cc = (CContainer)ci;
            CInteractable entry = cc.nextFocus();
            if(entry != null) return entry;
          }
          return ci;
        }
        if(ci.isFocused()) {
          searchedEnt = true;
          if(ci instanceof CContainer) {
            CContainer cc = (CContainer)ci;
            CInteractable entry = cc.nextFocus();
            if(entry != null) return entry;
          }
          ci.setFocused(false);
        }
      }
    }
    //Failback
    for(CComponent c : childrens) {
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(!ci.isEnabled()) continue;
        ci.setFocused(true);
        if(ci instanceof CContainer) {
          CContainer cc = (CContainer)ci;
          CInteractable entry = cc.nextFocus();
          if(entry != null) return entry;
        }
        return ci;
      }
    }
    return null;
  }
  
  public CInteractable currentFocus() {
    for(CComponent c : childrens) {
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(ci.isFocused()) {
          if(ci instanceof CContainer) {
            CContainer cc = (CContainer)ci;
            CInteractable entry = cc.currentFocus();
            if(entry != null) return entry;
          }
          return ci;
        }
      }
    }
    //Failback
    for(CComponent c : childrens) {
      if(c instanceof CInteractable) {
        CInteractable ci = (CInteractable)c;
        if(ci instanceof CContainer) {
          CContainer cc = (CContainer)ci;
          CInteractable entry = cc.currentFocus();
          if(entry != null) return entry;
        }
        return ci;
      }
    }
    return null;
  }

  public void placeCenter(CComponent c, TextGraphics g) {
    c.x = (int)Math.floor(this.getWidth(g)/2f - c.getWidth(g)/2f);
    c.y = (int)Math.floor(this.getHeight(g)/2f - c.getHeight(g)/2f);
  }
  
  @Override
  public Iterator<CComponent> iterator() {
    return childrens.iterator();
  }

}
