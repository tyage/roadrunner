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
 * FreeTextHunterTest.java
 * JUnit based test
 *
 * Created on 28 febbraio 2003, 17.38
 * @author Valter Crescenzi
 */

package roadrunner.parser;

import junit.framework.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import roadrunner.config.*;
import roadrunner.labeller.Box;
import roadrunner.util.DOMLoader;

import roadrunner.FixtureOnFile;

public class FreeTextHunterTest extends TestCase {
    
    private final static Logger log = Logger.getLogger(FreeTextHunterTest.class.getName());
    
    private static String sample_fn  = "freetext.html";
    private static String prefs_fn   = "freepref.xml";
    
    private Document freedoc;
    private Preferences freeprefs;
    
    public void setUp() throws Exception {
        FileReader reader = new FileReader(FixtureOnFile.getSampleFile(sample_fn));
        this.freedoc = DOMLoader.parseHTML(reader);
        this.freeprefs = new Preferences();
        this.freeprefs.load(FixtureOnFile.getPreferencesFile(prefs_fn));
    }
    
    
    public FreeTextHunterTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(FreeTextHunterTest.class);
        
        return suite;
    }
    
    /** Test of getCollapsedDocument method, of class roadrunner.parser.FreeTextHunter. */
    public void testGetCollapsedDocument() throws Exception {
        System.out.println("testGetCollapsedDocument");
        
        FreeTextHunter hunter = new FreeTextHunter(this.freedoc,this.freeprefs);
        Document collapsed = hunter.getCollapsedDocument();
        assertNotNull(collapsed);
        log.info(collapsed.getDocumentElement().toString());
        Element strong = (Element)collapsed.getElementsByTagName("STRONG").item(0);
        assertNotNull(strong);
        String content = strong.getChildNodes().item(1).getNodeValue();
        // 'Yah!' and 'collapsed' must end up in the same DOM text node
        assertTrue(content.indexOf("Yah!")!=-1 && content.indexOf("collapsed")!=-1);
    }
    
    public void testFreeTextAndBoundingBoxes() throws Exception {
        System.out.println("testFreeTextAndBoundingBoxes");
        
        FileReader reader = new FileReader(FixtureOnFile.getSampleFile(sample_fn));
        Lexer lexer = new Lexer(reader, this.freeprefs);
        List tokens = lexer.getTokens();
        Map token2bb = lexer.getTokens2BoundingBoxes();
        Token yah = findTextTokenContaining(tokens, "Yah!");
        assertNotNull("The expected text token containing \'Yah!\' has not been detected!", yah);
        Box yahbox = (Box)token2bb.get(yah);
        assertEquals(new Box(10,10, 90,30), yahbox);
        Token x = findTextTokenContaining(tokens, "X");
        assertNotNull("The expected text token containing \'X\' has not been detected!", x);
        Box xbox = (Box)token2bb.get(x);
        assertEquals(new Box(0,0, 20,20), xbox);
        Token zxy = findTextTokenContaining(tokens, "ZXY");
        assertNotNull("The expected text token containing \'ZXY\' has not been detected!", zxy);
        Box zxybox = (Box)token2bb.get(zxy);
        assertEquals(new Box(20,20, 30,30), zxybox);
    }
    
    private Token findTextTokenContaining(List tokens, String wanted) {
        Iterator it = tokens.iterator();
        while (it.hasNext()) {
            Token token = (Token)it.next();
            if (token.isPCDATA() && token.getText().indexOf(wanted)!=-1) {
                return token;
            }
        }
        return null;
    }
    
    
}
