package cityfind.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.util.Vector;

/**
 * Title:        CityFinder (IDSS Demo Applet)
 * Description:  Displays the selected city map and stores' locations as red-dots on the map.
 *
 * Copyright:    Copyright (c) 2001-2002 Chon D. Chung.
 * @author Chon D. Chung
 * @version Demo 0.1
 */

public class MapCanvas extends Canvas {

  Image map;
  /** a vector to hold store[] lists */
  Vector storeLists;

  public MapCanvas(Image map) {
    this.map = map;
    storeLists = new Vector(20,10);
  }

  /** resets the canvas by repainting it with a new map. */
  public void reset(Image map){
    this.map = map;
    storeLists = null;
    storeLists = new Vector(20,10);
    this.repaint();
  }

  /** Add a store on the graphic map as a red dot and the rank number next to it.
   *  @param x x-position
   *  @param y y-position
   *  @param rank the store rank of search relevance among stores
   *  @param id store id
   */
  public void addStore(int x, int y, int rank, int id){
      /** an array that holds store x-position, y-position, rank, id */
      int[] store = new int[4];
      store[0] = x;
      store[1] = y;
      store[2] = rank;
      store[3] = id;

      storeLists.addElement(store);
  }

  public void paint(Graphics g){
      g.drawImage(map, 0, 0, this);

      if(storeLists.size()>0)
          drawStores(g);
  }

  /** Draws the stores on the maps*/
  private void drawStores(Graphics g){
      Font f = new Font("TimesRoman", Font.PLAIN, 9);
      g.setFont(f);

      for (int i=0; i < storeLists.size(); i++){
          int[] store = (int[]) storeLists.elementAt(i);
          g.setColor(Color.black);
          g.fillOval(store[0] -2, store[1] -2,6,6);//draw a shadow dot
          g.setColor(Color.red);
          g.fillOval(store[0] -3,store[1]-3,6,6); //draw a red dot

          g.setColor(Color.black);
          g.drawString(String.valueOf(store[2]), store[0] + 8, store[1] + 8);
      }
  }
}