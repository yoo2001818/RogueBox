package kr.kkiro.roguebox.game;

import kr.kkiro.roguebox.game.tile.Tile;

public class TileEntry {
  
  protected int x, y;
  protected int tile;
  protected TileMap tileMap;
  protected int tileData;
  public boolean revealed;
  
  public TileEntry(int x, int y, TileMap tileMap, int tile) {
    this.x = x;
    this.y = y;
    this.tile = tile;
    this.tileMap = tileMap;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getTileId() {
    return tile;
  }
  
  public void setTileId(int tile) {
    this.tile = tile;
    tileMap.getBank().get(tile).onChange(this);
  }
  
  public Tile getTile() {
    return tileMap.getBank().get(tile);
  }
  
  public void setTile(Tile tile) {
    this.tile = tileMap.getBank().indexOf(tile);
    tile.onChange(this);
  }
  
  public TileMap getTileMap() {
    return tileMap;
  }
  
  public TileEntry getRelative(int x, int y) {
    return tileMap.get(this.x + x, this.y + y);
  }
  
  public int getTileData() {
    return tileData;
  }
  
  public void setTileData(int tileData) {
    this.tileData = tileData;
  }
  
}
