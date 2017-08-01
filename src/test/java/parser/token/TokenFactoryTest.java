/*
     RoadRunner - an automatic wrapper generation system for Web data sources
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     This program is  free software;  you can  redistribute it and/or
     modify it  under the terms  of the GNU General Public License as
     published by  the Free Software Foundation;  either version 2 of
     the License, or (at your option) any later version.

     This program is distributed in the hope that it  will be useful,
     but  WITHOUT ANY WARRANTY;  without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
     General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program; if not, write to the:

     Free Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

     ----

     RoadRunner - un sistema per la generazione automatica di wrapper su sorgenti Web
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     Questo  programma è  software libero; è  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come è pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  è distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILITÀ  o di  APPLICABILITÀ PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne può
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/*
 * TokenFactoryTest.java
 * JUnit based test
 *
 * Created on 12 marzo 2003, 11.24
 *@author Valter Crescenzi
 */

package roadrunner.parser.token;

import junit.framework.*;

import java.util.*;

import org.w3c.dom.*;

import roadrunner.util.Util;
import roadrunner.parser.Token;

public class TokenFactoryTest extends TestCase {
  
  public TokenFactoryTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(TokenFactoryTest.class);
    
    return suite;
  }
  
  /** Test of createOpenTagToken method, of class roadrunner.parser.TokenFactory. */
  public void testCreateOpenTagToken() {
    System.out.println("testCreateOpenTagToken");
    
    TokenFactory factory = TokenFactory.getInstance();
    Map atts = new HashMap(); atts.put("href","http://boh");
    Map atts2 = new HashMap(); atts2.put("href","http://boh2");
    Token tag1 = factory.createOpenTagToken("a", Collections.EMPTY_MAP, 0);
    Token tag2 = factory.createOpenTagToken("a", Collections.EMPTY_MAP,0);
    Token tag3 = factory.createOpenTagToken("a",atts,0);
    Token tag4 = factory.createOpenTagToken("a",atts,1);
    Token tag5 = factory.createOpenTagToken("a",atts2,1);
    assertTrue(tag1==tag2);
    assertTrue(tag1!=tag3);
    assertTrue(tag3.getAttributes().containsKey("href"));
    assertTrue(tag4!=tag3);   
    assertTrue(tag5!=tag4); // possible variants
    assertEquals(tag4.getAttributes().get("href"), "http://boh");
    assertEquals(tag5.getAttributes().get("href"), "http://boh2");
  } 
  
  /** Test of createCloseTagToken method, of class roadrunner.parser.TokenFactory. */
  public void testCreateCloseTagToken() {
    System.out.println("testCreateCloseTagToken");
    TokenFactory factory = TokenFactory.getInstance();
    assertEquals(
    -(factory.createOpenTagToken("boh", Collections.EMPTY_MAP, 0)).code(), 
     (factory.createCloseTagToken("boh", Collections.EMPTY_MAP, 0)).code());
  }

}
