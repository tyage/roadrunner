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
 * PreferencesTest.java
 * JUnit based test
 *
 * Created on 23 febbraio 2003, 17.56
 * @author Valter Crescenzi
 */

package roadrunner.config;

import junit.framework.*;

import java.util.*;
import java.util.logging.*;
import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import roadrunner.config.Constants;

public class PreferencesTest extends TestCase implements Constants {
  
  final static private String prefsDir = "/home/crescenz/RoadRunner/current/test/fix/preferences/";
  final static private String upfn = prefsDir+"u.xml";
  
  private Preferences prefs;
  
  public void setUp() throws Exception {
    prefs = new Preferences();
    prefs.load(new File(upfn));
  }
  
  public PreferencesTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(PreferencesTest.class);
    
    return suite;
  }
  
  /** Test of contains method, of class roadrunner.Preferences. */
  public void testContains() {
    System.out.println("testContains");
    
    assertTrue(this.prefs.contains(FREE_TEXT));
    assertTrue(!this.prefs.contains(MAX_NUM_OCCUR));
  }
  
  
  /** Test of getBoolean method, of class roadrunner.Preferences. */
  public void testGetBoolean() {
    System.out.println("testGetBoolean");
    
    assertTrue(this.prefs.getBoolean(FREE_TEXT));
  }

  /** Test of getString method, of class roadrunner.Preferences. */
  public void testGetString() {
    System.out.println("testGetString");
    
    assertEquals(this.prefs.getString(BROWSER),"mozilla");
  }
  
  /** Test of getSet method, of class roadrunner.Preferences. */
  public void testGetSet() {
    System.out.println("testGetSet");
    String [] pTags = {"sup","p","i","a"};
    assertEquals(this.prefs.getSet(FREETEXT_TAGS), new HashSet(Arrays.asList(pTags)));
    assertEquals(this.prefs.getSet(IGNORE_TAGS), Collections.EMPTY_SET);
    
    assertEquals(Config.getPrefs().getSet(IGNORE_TAGS), Collections.EMPTY_SET);    
  }
    
  /** Test of saveAs method, of class roadrunner.Preferences. */
  public void testSaveAs() throws IOException {
    System.out.println("testSaveAs");
    File file = new File(prefsDir+"testprefs.xml");
    file.deleteOnExit();
    this.prefs.saveAs(file);
    Preferences saved = this.prefs;
    Preferences loaded = new Preferences();
    loaded.load(file);
    assertEquals(saved.getBoolean(AMBIGUITY),loaded.getBoolean(AMBIGUITY));
    assertEquals(saved.getSet(IGNORE_TAGS),loaded.getSet(IGNORE_TAGS));
  }
  
    /** Test of default values handling, of class roadrunner.Preferences. */
  public void testDefaultsHandling() {
    System.out.println("testDefaultsHandling");

    assertEquals(Config.getPrefs().getInt(MAX_NUM_OCCUR),4);
  }

}
