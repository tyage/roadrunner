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
 * RealSearchSpaceTest.java
 * JUnit based test
 *
 * Created on 16 marzo 2003, 9.36
 */

package roadrunner.engine.space;

import junit.framework.*;

import java.net.URL;
import java.util.logging.*;

import roadrunner.ast.Expression;
import roadrunner.parser.Parser;
import roadrunner.parser.TokenList;
import roadrunner.Sample;
import roadrunner.config.*;

import roadrunner.FixtureOnFile;

/**
 *
 * @author Valter Crescenzi
 */
public class RealSearchSpaceTest extends SearchSpaceTest implements Constants {
    
    private final static Logger log = Logger.getLogger(RealSearchSpaceTest.class.getName());
        
    private Sample byname2 = createRealSample("rufus/2ByName.html");
    private Sample byname3 = createRealSample("rufus/3ByName.html");
    private Sample byname9 = createRealSample("rufus/9ByName.html");
    private Sample bynameY = createRealSample("rufus/YByName.html");
    
    public RealSearchSpaceTest(java.lang.String testName) throws Exception {
        super(testName);
    }
    
    private Sample createRealSample(String filename) throws Exception {
        return new Sample(new URL("file:"+FixtureOnFile.getSampleFile(filename)), Config.getPrefs());
    }
    
    public static void main(java.lang.String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(RealSearchSpaceTest.class);
        return suite;
    }
    
    public void testWithRealSamples23() {
        System.out.println("*********************");
        System.out.println("testWithRealSamples23");
        System.out.println("*********************");
        
        testWithRealSamples(byname2, byname3);
    }
    
    public void testWithRealSamples29() {
        System.out.println("*********************");
        System.out.println("testWithRealSamples29");
        System.out.println("*********************");
        
        testWithRealSamples(byname2, byname9);
    }
    
    public void testWithRealSamples2Y() {
        System.out.println("*********************");
        System.out.println("testWithRealSamples2Y");
        System.out.println("*********************");
        
        testWithRealSamples(byname2, bynameY);
    }
    
    private void testWithRealSamples(Sample sample1, Sample sample2) {
        log.info("Real Samples: "+sample1+" "+sample2);
        Config.getPrefs().put(FREE_TEXT,"false");
        Config.getPrefs().put(FREE_PLUS,"false");
        Config.getPrefs().put(MAX_NUM_OCCUR,"1");
        Config.getPrefs().put(AMBIGUITY,"1");
        Expression result;
        Expression exp = new Expression(sample1.getTokenlist());
        TokenList  tl  = sample2.getTokenlist();
        
        SearchSpace space = new SearchSpace(exp, tl, 0);
        int count = 0;
        while (space.hasNext() && count<5) {
            result = space.next();
            log.info(result.dump("Rufus Solution "+count+") "));
            assertTrue(new Parser(result).parseAllExpression(tl));
            count++;
        }
    }
    
    // Add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
}
