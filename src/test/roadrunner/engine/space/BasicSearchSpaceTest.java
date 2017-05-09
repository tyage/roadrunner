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
 * BasicSearchSpaceTest.java
 * JUnit based test
 *
 * Created on 16 marzo 2003, 9.36
 * @author Valter Crescenzi
 */

package roadrunner.engine.space;

import junit.framework.*;

import java.util.*;
import java.util.logging.*;
import java.net.URL;

import roadrunner.Sample;
import roadrunner.config.Config;
import roadrunner.config.Constants;
import roadrunner.bidi.Direction;
import roadrunner.ast.Expression;
import roadrunner.parser.Parser;
import roadrunner.parser.TokenList;

import roadrunner.FixtureOnFile;

public class BasicSearchSpaceTest extends SearchSpaceTest implements Constants {
    
    private final static Logger log = Logger.getLogger(BasicSearchSpaceTest.class.getName());
    
    
    protected static Sample createBasicSample(String filename) throws Exception {
        return new Sample(new URL("file:"+FixtureOnFile.getBasicFile(filename)), Config.getPrefs());
    }
    
    private Sample sample_opt0 = createBasicSample("basic-nest0-opt0.xhtml");
    private Sample sample_opt1 = createBasicSample("basic-nest0-opt1.xhtml");
    
    private Sample sample_pls1 = createBasicSample("basic-nest1-plus1.xhtml");
    private Sample sample_pls2 = createBasicSample("basic-nest1-plus2.xhtml");
    
    private Sample sample_plsws1 = createBasicSample("basic-nest1-pluswithsep1.xhtml");
    private Sample sample_plsws2 = createBasicSample("basic-nest1-pluswithsep2.xhtml");
    private Sample sample_plsws3 = createBasicSample("basic-nest1-pluswithsep3.xhtml");
    
    private Sample sample_plsas1 = createBasicSample("basic-nest1-ambiguoussep1.xhtml");
    private Sample sample_plsas2 = createBasicSample("basic-nest1-ambiguoussep2.xhtml");
    private Sample sample_plsas3 = createBasicSample("basic-nest1-ambiguoussep3.xhtml");
    
    private Sample sample_optpls0 = createBasicSample("basic-nest1-optplus0.xhtml");
    private Sample sample_optpls1 = createBasicSample("basic-nest1-optplus1.xhtml");
    private Sample sample_optpls2 = createBasicSample("basic-nest1-optplus2.xhtml");
    
    private Sample sample_plsplsr11  = createBasicSample("basic-nest2-rightborderplus11.xhtml");
    private Sample sample_plsplsr222 = createBasicSample("basic-nest2-rightborderplus222.xhtml");
    private Sample sample_plsplsl11  = createBasicSample("basic-nest2-leftborderplus11.xhtml");
    private Sample sample_plsplsl222 = createBasicSample("basic-nest2-leftborderplus222.xhtml");
    
    private Sample sample_pls12  = createBasicSample("basic-nest2-plus12.xhtml");
    private Sample sample_pls211 = createBasicSample("basic-nest2-plus211.xhtml");
    
    private Sample sample_pls111   = createBasicSample("basic-nest3-plus111.xhtml");
    private Sample sample_pls1211  = createBasicSample("basic-nest3-plus1211.xhtml");
    private Sample sample_pls21111 = createBasicSample("basic-nest3-plus21111.html");
    private Sample sample_pls21212 = createBasicSample("basic-nest3-plus21212.html");
    
    private List tripleNested;
    
    public BasicSearchSpaceTest(java.lang.String testName) throws Exception {
        super(testName);
        this.tripleNested = new LinkedList();
        this.tripleNested.add(sample_pls111);
        this.tripleNested.add(sample_pls1211);
        this.tripleNested.add(sample_pls21111);
        this.tripleNested.add(sample_pls21212);
    }
    
    public static void main(java.lang.String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BasicSearchSpaceTest.class);
        return suite;
    }
    
    public void testBasicHook() {
        System.out.println("testBasicHook");
        
        testAll2Shot("", sample_opt0, sample_opt1);
    }
    
    public void testBasicPlus() {
        System.out.println("testBasicPlus");
        
        testAll2Shot("", sample_pls1, sample_pls2);
    }
    
    public void testBasicPlusWithSep() {
        System.out.println("testBasicPlusWithSep");
        
        testAll3Shot("", sample_plsws1, sample_plsws2, sample_plsws3);
    }
    
    public void testBasicPlusWithAmbiguousSep() {
        System.out.println("testBasicPlusWithAmbiguousSep");
        
        Config.getPrefs().put(CBACKTRACKING,"true");
        Config.getPrefs().put(FREE_PLUS,"false");
        Config.getPrefs().put(MAX_NUM_OCCUR,"1");
        Config.getPrefs().put(SUBTREE,"true");
        Config.getPrefs().put(AMBIGUITY,"2");
        testAll3Shot("", sample_plsas1, sample_plsas2, sample_plsas3);
        // assert no subtree in the resulting exp
    }
    
    public void testBasicHookOfPlus() {
        System.out.println("testBasicHookOfPlus");
        
        testAll3Shot("", sample_optpls0, sample_optpls1, sample_optpls2);
    }
    
    public void testBasicBorderRightPlusOfPlus() {
        System.out.println("testBasicBorderRightPlusOfPlus");
        
        testAll2Shot("", sample_plsplsr11, sample_plsplsr222);
    }
    
    public void testBasicBorderLeftPlusOfPlus() {
        System.out.println("testBasicBorderLeftPlusOfPlus");
        
        testAll2Shot("", sample_plsplsl11, sample_plsplsl222);
    }
    
    public void testBasicNestedPlus() {
        System.out.println("testBasicNestedPlus");
        
        testAll2Shot("", sample_pls211, sample_pls12);
    }
    
    public void testDoubleNestedPlus() {
        System.out.println("testDoubleNestedPlus");
        
        testAll2Shot("", sample_pls21212, sample_pls1211);
    }
    
    /** Test of getBasicAllSolutions method, of class roadrunner.engine.space.SearchSpace. */
    public void testBasicAllSolutions() {
        System.out.println("*********************");
        System.out.println("testBasicAllSolutions");
        System.out.println("*********************");
        
        log.info("******* testBasicAllSolutions ******");
        testAllSolutions(20, sample_pls1, sample_pls2, Direction.LEFT2RIGHT);
    }
    
    /** Test of getNestedAllSolutions method, of class roadrunner.engine.SearchSpace. */
    public void testNestedAllSolutions() {
        System.out.println("**********************");
        System.out.println("testNestedAllSolutions");
        System.out.println("**********************");
        
        log.info("******* testNestedAllSolutions ******");
        Iterator it = this.tripleNested.iterator();
        Sample sample = (Sample)it.next();
        Expression expression = new Expression(sample.getTokenlist());
        do {
            sample = (Sample)it.next();
            TokenList tokenlist = sample.getTokenlist();
            SearchSpace space = new SearchSpace(expression, tokenlist, 0);
            space.setBacktracking(false);
            int count = 0;
            while (space.hasNext() && count<20) {
                expression = space.next();
                log.info(expression.dump("Solution "+count+") "));
                assertTrue(new Parser(expression).parseAllExpression(tokenlist));
                count++;
            }
        } while(it.hasNext());
    }
    
}
