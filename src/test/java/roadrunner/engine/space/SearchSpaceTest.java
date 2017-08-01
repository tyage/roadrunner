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
 * SearchSpaceTest.java
 * JUnit based test
 *
 * Created on 16 marzo 2003, 9.36
 */

package roadrunner.engine.space;

import junit.framework.*;

import java.net.URL;
import java.util.*;
import java.util.logging.*;

import roadrunner.Sample;
import roadrunner.bidi.*;
import roadrunner.ast.*;
import roadrunner.parser.*;
import roadrunner.config.*;
import roadrunner.engine.VariantsHunter;

/**
 *
 * @author Valter Crescenzi
 */
public class SearchSpaceTest extends TestCase implements Constants {
    
    private final static Logger log = Logger.getLogger(SearchSpaceTest.class.getName());
    
    public SearchSpaceTest(java.lang.String testName) throws Exception {
        super(testName);        
    }
    
    protected void setUp() {
        Preferences config = Config.getPrefs();
        config.put(FREE_TEXT,"true");
        config.put(EBACKTRACKING,"false");
        config.put(FREE_PLUS,"true");
        config.put(MAX_NUM_OCCUR,"2");
        config.put(SUBTREE,"false");
        config.put(AMBIGUITY,"0");
        config.put(LLK,"5");
        config.put(RRK,"5");
    }
    
    public static void main(java.lang.String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(RealSearchSpaceTest.class);
        suite.addTestSuite(BasicSearchSpaceTest.class);
        return suite;
    }
    
    protected Expression test2Shot(String desc, Sample sample1, Sample sample2, Direction dir) {
        log.info("Samples"+dir+": "+sample1.getName()+" "+sample2.getName());
        TokenList tl1 = sample1.getTokenlist();
        TokenList tl2 = sample2.getTokenlist();
        //( Sample 1 vs Sample 2)
        SearchSpace space12 = new SearchSpace(new Expression(tl1), tl2, dir, 0);
        Expression solution = space12.next();
        log.info(solution.dump(desc+dir));
        assertTrue(new Parser(solution).parseAllExpression(tl2));
        //find variants so that equals() of Expression can work
        TokenList[] tls = { tl1, tl2 };
        VariantsHunter hunter = new VariantsHunter(Collections.singleton(solution), tls);
        Set results = hunter.insertVariants();
        assertTrue(results.size()==1);
        Expression result = (Expression)results.iterator().next();
        return result;
    }
    
    protected Expression test3Shot(String desc, Sample sample1, Sample sample2, Sample sample3, Direction dir) {
        log.info("3 Samples"+dir+": "+sample1.getName()+" "+sample2.getName()+" "+sample3.getName());
        TokenList tl1 = sample1.getTokenlist();
        TokenList tl2 = sample2.getTokenlist();
        TokenList tl3 = sample3.getTokenlist();
        //( Sample 1 vs Sample 2)
        SearchSpace space12 = new SearchSpace(new Expression(tl1), tl2, dir, 0);
        space12.setBacktracking(false);
        Expression solution12 = space12.next();
        log.info("Result after two samples:");
        log.info(solution12.toString());
        assertTrue(new Parser(solution12).parseAllExpression(tl2));
        // (Sample 1 vs Sample 2) vs Sample3
        SearchSpace space123 = new SearchSpace(solution12, tl3, dir, 0);
        space123.setBacktracking(false);
        Expression solution123 = space123.next();
        log.info(solution123.dump(desc+dir));
        assertTrue(new Parser(solution123).parseAllExpression(tl3));
        
        //find variants so that equals() of Expression can work
        TokenList[] tls = { tl1, tl2, tl3};
        VariantsHunter hunter = new VariantsHunter(Collections.singleton(solution123), tls);
        Set results = hunter.insertVariants();
        assertTrue(results.size()==1);
        Expression result123 = (Expression)results.iterator().next();
        return result123;
    }
    
    
    protected void testAll2Shot(String desc, Sample sa, Sample sb) {
        
        Expression lrab = test2Shot(desc,sa,sb,Direction.LEFT2RIGHT);
        Expression rlab = test2Shot(desc,sa,sb,Direction.RIGHT2LEFT);
        
        assertEquals(lrab,rlab);
        
        Expression lrba = test2Shot(desc,sb,sa,Direction.LEFT2RIGHT);
        Expression rlba = test2Shot(desc,sb,sa,Direction.RIGHT2LEFT);
        assertEquals(lrba,rlba);
        
        assertEquals(lrab, rlba);
    }
    
    protected void testAll3Shot(String desc, Sample sa, Sample sb, Sample sc) {
        testAll3ShotWithDir(desc, sa, sb, sc, Direction.LEFT2RIGHT);
        testAll3ShotWithDir(desc, sa, sb, sc, Direction.RIGHT2LEFT);
    }
    
    protected void testAll3ShotWithDir(String desc, Sample sa, Sample sb, Sample sc, Direction dir) {
        Expression lrabc = test3Shot(desc,sa,sb,sc,dir);
        Expression lracb = test3Shot(desc,sa,sb,sc,dir);
        assertEquals(lrabc,lracb);
        Expression lrbac = test3Shot(desc,sa,sb,sc,dir);
        Expression lrbca = test3Shot(desc,sa,sb,sc,dir);
        assertEquals(lrbac,lrbca);
        Expression lrcab = test3Shot(desc,sa,sb,sc,dir);
        Expression lrcba = test3Shot(desc,sa,sb,sc,dir);
        assertEquals(lrcab,lrcba);
    }
    
    protected void testAllSolutions(int n, Sample sample1, Sample sample2, Direction dir) {
        log.info("All solutions for samples"+dir+": "+sample1.getName()+" "+sample2.getName());
        Expression result;
        Expression exp = new Expression(sample1.getTokenlist().asRegion());
        TokenList tl = sample2.getTokenlist();
        
        SearchSpace space = new SearchSpace(exp,tl, 0);
        space.setBacktracking(false);
        int count = 0;
        while (space.hasNext() && count<n) {
            result = space.next();
            log.info(result.dump("Solution "+count+") "));
            count++;
            assertTrue(new Parser(result).parseAllExpression(tl));
        }
    }
    
}
