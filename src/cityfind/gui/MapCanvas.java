package cityfind.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Title:        CityFinder (SPA Demo App)
 * Description:  Displays the selected city map and shops' locations as oval-dots on the map.
 *
 * Copyright:    Copyright (c) 2001-2013 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.7
 */

public class MapCanvas extends Canvas {

  Image map;
  /** a vector to hold store[] lists */
  private ArrayList<int[]> shopLists; 
  
//  = new ArrayList<int[]>();
//  action.add(new String[2]);


  public MapCanvas(Image map) {
    this.map = map;
    shopLists = new ArrayList<int[]>();
  }

  /** resets the canvas by repainting it with a new map. */
  public void reset(Image map){
    this.map = map;
    shopLists = null; 
    shopLists = new ArrayList<int[]>();
    this.repaint();
  }

  /** Adds a store on the map as a oval-dot and the rank number next to it.
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

      shopLists.add(store);
  }

  public void paint(Graphics g){
      g.drawImage(map, 0, 0, this);

      if(shopLists.size()>0)
          drawStores(g);
  }

  /** Draws the stores on the map*/
  private void drawStores(Graphics g){
	  int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();//Using Toolkit class and the calculate the correct font size
	  int fontSize = (int)Math.round(7.0 * screenRes / 72.0); //calculate the correct font size
      Font f = new Font("Arial", Font.BOLD, fontSize);
      g.setFont(f);

      for (int i=0; i < shopLists.size(); i++){
          int[] store = shopLists.get(i);
          g.setColor(Color.GRAY);
          g.fillOval(store[0] -2, store[1] -2,8,8);//draws the shadow of the dot 
          g.setColor(Color.ORANGE);
          g.fillOval(store[0] -4,store[1]-4,8,8); //draws a oval-dot
          g.setColor(Color.DARK_GRAY);
          g.drawOval(store[0] -4,store[1]-4,8,8); //draws a oval-border

          g.setColor(Color.DARK_GRAY);
          g.drawString(String.valueOf(store[2]), store[0] + 6, store[1] + 6); //draw the font (the rank number)
      }
  }
}