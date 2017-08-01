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
 * DOMLoaderTest.java
 *  JUnit based test
 *
 * Created on 3 marzo 2003, 15.53
  * @author Valter Crescenzi
 */

package roadrunner.config;

import junit.framework.*;

import java.io.*;
import java.net.URL;
import java.util.logging.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import roadrunner.parser.*;
import roadrunner.Sample;
import roadrunner.Wrapper;

public class DOMLoaderTest extends TestCase {
  
  private static String  wrappersDir = "/home/crescenz/RoadRunner/current/test/fix/wrappers/";
  private static String  samplesDir  = "/home/crescenz/RoadRunner/current/test/fix/samples/";
  private static String  sfn2        = samplesDir  +"2ByName.xhtml";
  private static String  wfn         = wrappersDir + "rufusWrapper1.xml";
  private static Wrapper rufus;
  private static Sample  byname2;
  
  
  public DOMLoaderTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) throws Exception {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(DOMLoaderTest.class);
    
    return suite;
  }
    
  public void testParseHTML() throws Exception {
    System.out.println("testParseHTML");
    rufus = Wrapper.load(new File(wfn));
    byname2 = new Sample(new URL("file://"+sfn2), rufus.getPrefs());
    Token wt = (Token)rufus.getExpression().getRoot().jjtGetChild(0);//should be <html>
    Token st = (Token)byname2.getTokenlist().getTokens().get(0);
    assertEquals(wt.getElement(),st.getElement());
    assertEquals(wt.getAttributes().keySet(),st.getAttributes().keySet());    
    assertEquals(wt.code(),st.code());
  }

  
}
