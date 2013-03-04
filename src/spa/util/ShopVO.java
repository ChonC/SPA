package spa.util;

/**
 * <p>Title: CityFinder (SPA Demo Applet) </p>
 * <p>Description: ShopVO is a Value Object class that stores a shop-entity ID and one
 *                 of its property value in order to compare multiple entities by 
 * 				   their properties' value. When the ShopSAXReader instance received
 *                 a user request to sort the different business entities by
 *                 one of their property values, like price, the ShopSAXReader instance reads
 *                 the business entities' information from the XML data source, and
 *                 uses this class to store those entities' ID and one of their property
 *                 value for the further sorting. </p>
 *
 * <p>Copyright: Copyright (c) 2001-2004 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.7
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 *
 */

public class ShopVO {

  /** business entity ID number */
  private int id;
  /** property value, such as price */
  private float propertyValue;
  /** the sorting rank.  If more than one entities have the same property values,
   *  they should have the same rank value to indicate it. */
  private int sortRank;
	  
  /** default constructor.
   *  @param id     			business entity id
   *  @param propertyValue    	one of the business entity property value
   */
  public ShopVO(int id, float _propertyValue) {
    this.id = id;
    propertyValue = _propertyValue;
  }

  /** Get id.
   * @return id */
  public int getId() {
    return id;
  }

  /**
   * Set the business store id.
   * @param id    business store id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Set the property value.
   * @param _propertyValue    property value
   */
  public void setPropertyValue(float _propertyValue) {
    this.propertyValue = _propertyValue;
  }

  /**
   * Get Property value
   * @return property value
   */
  public float getPropertyValue() {
    return propertyValue;
  }

  /**
   * When we sort multiple BizVO objects, if more than one BizVO objects have
   * the same property values, we should give them the same rank value to indicate it.
   *
   * @param sortRank the sorted rank in a group
   */
  public void setSortRank(int sortRank) {
    this.sortRank = sortRank;
  }

  /**
   * Get the sorted rank.
   * @return sortRank the sorted rank in a group
   */
  public int getSortRank() {
    return sortRank;
  }
}