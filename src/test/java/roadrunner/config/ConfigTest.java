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

     Questo  programma Ã¨  software libero; Ã¨  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come Ã¨ pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  Ã¨ distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILITÃ  o di  APPLICABILITÃ PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne puÃ²
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/*
 * ConfigTest.java
 * JUnit based test
 *
 * Created on 23 febbraio 2003, 17.48
 * @author Valter Crescenzi
 */

package roadrunner.config;

import junit.framework.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.w3c.dom.Node;

import roadrunner.FixtureOnFile;

public class ConfigTest extends TestCase {
  
  final static private String upfn = "u.xml";
  
  public ConfigTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(ConfigTest.class);
    
    return suite;
  }
    
  /** Test of getPrefs method, of class roadrunner.Config. */
  public void testGetPrefs() {
    System.out.println("testGetPrefs");
    Config.getPrefs();
  }
  
  /** Test of load method, of class roadrunner.Config. */
  public void testLoad() throws IOException {
    System.out.println("testLoad");
    Config.load(FixtureOnFile.getPreferencesFile(upfn));
  }
  
  
  // Add test methods here, they have to start with 'test' name.
  // for example:
  // public void testHello() {}
    
  
}
