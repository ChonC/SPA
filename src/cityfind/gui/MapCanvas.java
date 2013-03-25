package cityfind.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

/**
 * Title:        CityFinder (SPA Demo App)
 * Description:  Displays the selected city map and shops' locations as oval-dots on the map.
 *
 * Copyright:    Copyright (c) 2001-2013 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.7
 */

public class MapCanvas extends Canvas {

  protected Image map;
  /** a List to hold store[] lists */
  protected ArrayList<int[]> shopLists; 
    
  
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
	  int fontSize = (int)Math.round(7 * screenRes / 72.0); //calculate the correct font size
      Font f = new Font("Arial", Font.BOLD, fontSize);
      g.setFont(f);
      
      ArrayList<int[]> reverseList = new ArrayList<int[]>(shopLists); //reverse, so it draws the first one at the top.
      Collections.reverse(reverseList);     
      
      int font_X = -6; //font x position. 

      for (int i=0; i < reverseList.size(); i++){
          int[] store = reverseList.get(i);
          g.setColor(Color.GRAY);
          g.fillOval(store[0]-6, store[1]-6,16,13);//draws the shadow of the dot 
          g.setColor(Color.orange);
          g.fillOval(store[0]-8,store[1]-8,16,13); //draws a oval-dot
          if (store[2]<5) {//if the rank < 5, mark a red dot next to it 
              g.setColor(Color.RED);
              g.fillOval(store[0]-10,store[1]-10,6,6); //draws a oval-dot   
              g.setColor(new Color(203,30,44));
              g.drawOval(store[0]-10,store[1]-10,6,6); //draws a oval-border     	  
          }
          g.setColor(new Color(203,30,44));
          g.drawOval(store[0]-8,store[1]-8,16,13); //draws a oval-border

          g.setColor(new Color(0,   81,   119));
          if (store[2]<10) font_X = -2; //if the rank is a single digit, draws in the center
          g.drawString(String.valueOf(store[2]), store[0]+font_X, store[1]+3); //draw the font (the rank number)
      }
  }
  

  public ArrayList<int[]> getShopLists() {
	return shopLists;
  }


  public Image getMap() {
		return map;
  }
  
}