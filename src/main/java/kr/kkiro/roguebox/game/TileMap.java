package kr.kkiro.roguebox.game;

import kr.kkiro.roguebox.game.tile.TileBank;


public class TileMap {
  protected TileEntry[][] tileArray;
  protected int width, height;
  protected TileBank tileBank;
  protected Map map;
  
  public TileMap(int width, int height, TileBank bank, int fill) {
    this.width = width;
    this.height = height;
    tileBank = bank; 
    tileArray = new TileEntry[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        tileArray[y][x] = new TileEntry(x, y, this, fill);
      }
    }
  }
  
  public void setMap(Map map) {
    this.map = map;
  }
  
  public Map getMap() {
    return map;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public TileBank getBank() {
    return tileBank;
  }
  
  public TileEntry get(int x, int y) {
    if(x < 0 || x >= width || y < 0 || y >= height) return null;
    return tileArray[y][x];
  }
  
}
