package cityfind.gasfind;


import spa.ShopElement;

import cityfind.gasfind.sax.GasXMLTag;

/**
 * Title:        GasElement
 * Description:  GasElement is used to store a Gas Shop (station) information. 
 *               It is used by the GasSAXManager to store a gas station contact information.  
 *               When the GasSAXManager reads the near by gas stations XML information
 *               and finds the station that met the current searching criteria, 
 *               the GasSAXManager creates this GasElement instance to store the station 
 *               information.  Then the stored information can be displayed on the 
 *               user computer screen.
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class GasElement extends ShopElement {

  /** The gas station brand name. */
  public String brand;
  /** The unleaded gas price. */
  public String unlead;
  /** The plusUnleaded gas price. */
  public String plusUnlead;
  /** The premiumUnleaded gas price. */
  public String premiumUnlead;
  /** The diesel gas price. */
  public String diesel;

  /** Default constructor. */
  public GasElement() {}

  /** Sets the gas station element's value from the XML element's content. */
  public void setElementValue(int xmlTagId, String value) {
        switch(xmlTagId){
            case GasXMLTag.TAG_ID_NAME:
                name = value; break;
            case GasXMLTag.TAG_ID_PHONE:
                phone = value; break;
            case GasXMLTag.TAG_ID_ADDRESS:
                address = value; break;
            case GasXMLTag.TAG_BRAND:
                brand = value; break;
            case GasXMLTag.TAG_ID_DISTANCE:
                distance = value; break;
            case GasXMLTag.TAG_UNLEAD:
                unlead = value; break;
            case GasXMLTag.TAG_PLUS_UNLEAD:
                plusUnlead = value; break;
            case GasXMLTag.TAG_PREMIUM_UNLEAD:
                premiumUnlead = value; break;
            case GasXMLTag.TAG_DIESEL:
                diesel = value; break;
            case GasXMLTag.TAG_ID_X:
                x = value; break;
            case GasXMLTag.TAG_ID_Y:
                y = value; break;
            default:   break;
        }//End of switch
  }

  /** Returns the stored Gas-station information as in a readable String format. */
  public String toString() {

      String gasStore = " Name: " + name + " (id: " + id + ")" + "\n\n" +
                        " Address: " + address + "\n" +
                        " Phone: " + phone + "\n" +
                        " Brand: " + brand + "\n" +
                        " Distance: " + distance + " miles\n" +
                        "-----------------------\n";

      if (unlead != null) gasStore += "    Unlead: $ " + unlead + "\n";
      if (plusUnlead != null) gasStore += "    Plus Unlead: $ " + plusUnlead + "\n";
      if (premiumUnlead != null) gasStore += "    Premium Unlead: $ " + premiumUnlead + "\n";
      if (diesel != null) gasStore += "    Diesel: $ " + diesel + "\n";
      gasStore += "-----------------------";

      return gasStore;
  }
}