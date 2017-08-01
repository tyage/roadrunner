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
 * addPlus_wTest.java
 * JUnit based test
 *
 * Created on 2 aprile 2003, 13.21
 * @author Valter Crescenzi
 */

package roadrunner.engine.space.operator;

import junit.framework.*;

import java.net.URL;
import java.util.*;
import java.util.logging.*;

import roadrunner.util.Util;
import roadrunner.bidi.Region;
import roadrunner.ast.Expression;
import roadrunner.ast.ExpressionRegion;
import roadrunner.config.Config;
import roadrunner.parser.*;
import roadrunner.Sample;

import roadrunner.FixtureOnFile;

public class addPlus_sTest extends TestCase {
    
    private final static Logger log = Logger.getLogger(addPlus_sTest.class.getName());
    
    private Sample sample_pls1;
    private Sample sample_pls2;
    
    private static String pls1 = "basic-nest1-plus1.xhtml";
    private static String pls2 = "basic-nest1-plus2.xhtml";
    
    private addPlus_s addplus;
    
    public void setUp() {
        Expression expression = new Expression(sample_pls1.getTokenlist());
        TokenList tokenlist = sample_pls2.getTokenlist();
        
        Parser parser = new Parser(expression);
        parser.parsePrefix(tokenlist);
        List mismatches = parser.getMismatches();
        assertEquals(1, mismatches.size());
        MismatchPoint mismatch = (MismatchPoint)mismatches.get(0);
        List operators = addPlus_s.getFactory(mismatch).createOperators();
        assertEquals(1,operators.size());
        this.addplus = (addPlus_s)operators.get(0);
    }
    
    public addPlus_sTest(java.lang.String testName) throws Exception {
        super(testName);
        this.sample_pls1 = new Sample(new URL("file:"+FixtureOnFile.getBasicFile(pls1)), Config.getPrefs());
        this.sample_pls2 = new Sample(new URL("file:"+FixtureOnFile.getBasicFile(pls2)), Config.getPrefs());
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(addPlus_wTest.class);
        
        return suite;
    }
    
    /** Test of getRegionAfterSquare method, of class roadrunner.engine.addPlus_w. */
    public void testGetRegionAfterSquare() {
        System.out.println("testGetRegionAfterSquare");
        
        TokenListRegion afterSquare = (TokenListRegion)this.addplus.getRegionAfterSquare();
        Token first =(Token)afterSquare.asList().get(0);
        log.info(afterSquare.asList().toString());
        assertEquals("ul",first.getElement());
    }
    
    
    /** Test of next method, of class roadrunner.engine.addPlus_w. */
    public void testNext() {
        System.out.println("testNext");
        
        Expression result = this.addplus.next();
        
        log.info(result.dump(""));
        
        assertTrue(new Parser(result).parsePrefix(this.sample_pls2.getTokenlist())>0);
    }
    
}
