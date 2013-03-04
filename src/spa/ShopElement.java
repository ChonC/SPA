package spa;

/**
 * Title:        CityFinder (IDSS Demo Applet)
 * 
 * Description:  ShopElement is a Value Object class to store a shop entity information. 
 *               such as the business contact information. 
 *               It is used by the ShopSAXManager.  When the ShopSAXManager 
 *               reads the near-by business stores XML information, and
 *               finds the business entities that meet
 *               the current searching criteria, the ShopSAXManager creates
 *               this class instances to store the shop entities' information.  
 *               So, they can be displayed on the user's computer screen.
 *               That is why this class has the exact same properties 
 *               as the entity in a XML data source in order to store
 *               the entity information.<br/><br/>               
 *     
 *               
 * @see cityfind.gasfind.GasElement
 *
 * Copyright:    Copyright (c) 2001-2004 Chon D. Chung.
 * @author Chon D. Chung
 * @version Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

abstract public class ShopElement {

  /** shop name. */ 
  public String name;
  /** shop Identification number. */
  public String id;
  /** shop address. */
  public String address;
  /** shop phone number. */
  public String phone;
  /** The shop GPS distance from the current user location. */
  public String distance;
  /** The latitude location value. */
  public String latitude; 
  /** The longitude location value. */
  public String longitude; 
  /** The business entity X-axis (horizontal) position on the displayed map. */
  public String x;
  /** The business entity Y-axis (vertical) position on the displayed map.  */
  public String y;


  /** 
   * Set the shop element's value from the xml element's content. 
   *  
   * @see cityfind.gasfind.GasElement for more information. */
  abstract public void setElementValue(int xmlTagId, String value);

  /** Sets the ID. */
  public void setId(String id){
    this.id = id;
  }
  
  /** This method returns the stored shop entity information as in a readable String format. */
  public String toString(){
      return "Store name: " + name + "\n" +
             " Address: " + address + "\n" +
             " Phone: " + phone + "\n" +
             " Distance: " + distance + " miles\n";
  }
}