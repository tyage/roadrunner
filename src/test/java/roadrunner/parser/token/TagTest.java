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
 * TagTest.java
 * JUnit based test
 *
 * Created on 27 febbraio 2003, 12.38
 * @author Valter Crescenzi
 */

package roadrunner.parser.token;

import junit.framework.*;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import roadrunner.*;
import roadrunner.ast.*;

public class TagTest extends TestCase {
  
  public TagTest(java.lang.String testName) {
    super(testName);
    
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(TagTest.class);
    return suite;
  }
  
  /** Test of factory class method, of class roadrunner.parser.TagFactory. */
  public void testFactory() {
    System.out.println("testFactory");
    Map atts = new HashMap(); atts.put("href","http://boh");
    Tag tag0 = TagFactory.getInstance().createTag(new String("a"),atts); // variant tag
    Tag tag1 = TagFactory.getInstance().createTag("a", Collections.EMPTY_MAP);
    Tag tag2 = TagFactory.getInstance().createTag("a", Collections.EMPTY_MAP);
    Tag tag3 = TagFactory.getInstance().createTag("a", new HashMap(atts));
    Map imgatts = new HashMap(); imgatts.put("src", "http://ariboh");
    Tag tag4 = TagFactory.getInstance().createTag("img", imgatts);
    assertTrue(tag1==tag2);
    assertTrue(tag1!=tag3);
    assertTrue(tag3.getAttributes().containsKey("href"));
  }
  
  
}
