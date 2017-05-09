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
 * ParserTest.java
 * JUnit based test
 *
 * Created on 28 febbraio 2003, 11.31
 * @author Valter Crescenzi
 */

package roadrunner.parser;

import junit.framework.*;

import java.util.*;
import java.util.logging.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

import roadrunner.*;
import roadrunner.ast.*;
import roadrunner.bidi.*;
import roadrunner.parser.MismatchPoint;
import roadrunner.Wrapper;
import roadrunner.Sample;

import roadrunner.FixtureOnFile;

public class ParserTest extends TestCase {
    
    private final static Logger log = Logger.getLogger(ParserTest.class.getName());
    
    private static String  wfn  = "rufusWrapper1.xml";
    private static String  sfn2 = "rufus/2ByName.html";
    private static String  sfnY = "rufus/YByName.html";
    private static Wrapper rufus;
    private static Sample byname2;
    private static Sample bynameY;
    
    private Parser parser;
    private Parser parserrl;
    
    public void setUp() {
        this.parser = new Parser(rufus.getExpression());
        this.parserrl = new Parser(rufus.getExpression(), Direction.RIGHT2LEFT);
    }
    
    public ParserTest(java.lang.String testName) throws Exception {
        super(testName);
        rufus = Wrapper.load(FixtureOnFile.getWrapperFile(wfn));
        byname2 = new Sample(new URL("file:"+FixtureOnFile.getSampleFile(sfn2)), rufus.getPrefs());
        bynameY = new Sample(new URL("file:"+FixtureOnFile.getSampleFile(sfnY)), rufus.getPrefs());
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ParserTest.class);
        return suite;
    }
    
    /** Test of parse method, of class roadrunner.parser.Parser. */
    public void testParse() {
        System.out.println("testParse");
        
        assertNotNull(byname2.getTokenlist());
        assertEquals(true,parser.parse(byname2.getTokenlist()));
        assertEquals(true,parserrl.parse(byname2.getTokenlist()));
    }
    
    /** Test of parseIntra method, of class roadrunner.parser.Parser. */
    public void testParseIntra() {
        System.out.println("testParseIntra");
        int tokens = byname2.getTokenlist().size();
        TokenList subtokenlistlr = new TokenList(byname2.getTokenlist().getTokens().subList(0,tokens-3));
        Parser parserlr = new Parser(rufus.getExpression().asRegion(), Direction.LEFT2RIGHT);
        assertTrue(parserlr.parseAllTokenlist(subtokenlistlr));
        assertFalse(parserlr.parse(subtokenlistlr));
        
        TokenList subtokenlistrl = new TokenList(byname2.getTokenlist().getTokens().subList(3,tokens));
        Parser parserrl = new Parser(rufus.getExpression().asRegion(), Direction.RIGHT2LEFT);
        assertTrue(parserrl.parseAllTokenlist(subtokenlistrl));
        assertFalse(parserrl.parse(subtokenlistrl));
    }
    
    /** Test of getMismatches method, of class roadrunner.parser.Parser. */
    public void testGetMismatches() {
        System.out.println("testGetMismatches");
        boolean found_img = false;
        assertFalse(parser.parse(bynameY.getTokenlist()));
        List mismatches = parser.getMismatches();
        log.info("Mismatches: "+mismatches);
        Iterator it = mismatches.iterator();
        while (it.hasNext()) {
            MismatchPoint mismatch = (MismatchPoint)it.next();
            Node node = mismatch.getFirstMismatchingNode();
            Token token = bynameY.getTokenlist().getToken(mismatch.getTokenIndex());
            log.info(mismatch.toString());
            if (token.getElement().equals("img")) found_img=true; //Rufus *new!* images
        }
        assertTrue(found_img);
    }
    
    
    public void testGetRegions() {
        TokenListRegion subtokenlist;
        ExpressionRegion subexpression;
        log.info("testGetRegions ->");
        subtokenlist = (TokenListRegion)byname2.getTokenlist().asRegion().subRegion(0, 8);
        subexpression = (ExpressionRegion)rufus.getExpression().asRegion().subRegion(0,10);
        log.info("Sub tokenlist: "+subtokenlist); log.info("Sub exp: "+subexpression);
        Parser parserlr = new Parser(subexpression, Direction.LEFT2RIGHT);
        
        assertTrue(parserlr.parseAllTokenlist(new TokenList(subtokenlist.asList())));
        
        ExpressionRegion matchingExplr = parserlr.getMatchingRegion();
        ExpressionRegion mismatchingExplr = parserlr.getRemainingRegion();
        log.info(matchingExplr.asList().toString());
        log.info(mismatchingExplr.asList().toString());
        
        assertEquals("head",((Token)new LinkedList(matchingExplr.asList()).getLast()).getElement());
        assertEquals("body",((Token)new LinkedList(mismatchingExplr.asList()).getFirst()).getElement());
        assertEquals(2,mismatchingExplr.asList().size());
        
        log.info("testGetRegions <-");
        int sub_exp_size = rufus.getExpression().asRegion().size();
        int sub_tl_size = byname2.getTokenlist().size();
        subtokenlist = (TokenListRegion)byname2.getTokenlist().asRegion().subRegion(sub_tl_size-85, sub_tl_size);
        subexpression = (ExpressionRegion)rufus.getExpression().asRegion().subRegion(sub_exp_size-76, sub_exp_size);
        log.info("Sub tokenlist: "+subtokenlist); log.info("Sub exp: "+subexpression);
        Parser parserrl = new Parser(subexpression, Direction.RIGHT2LEFT);
        
        assertTrue(parserrl.parseAllTokenlist(new TokenList(subtokenlist.asList())));
        
        ExpressionRegion matchingExprl = parserrl.getMatchingRegion();
        ExpressionRegion mismatchingExprl = parserrl.getRemainingRegion();
        log.info(matchingExprl.asList().toString());
        log.info(mismatchingExprl.asList().toString());
        
        assertEquals("meta",((Token)new LinkedList(matchingExprl.asList()).getFirst()).getElement());
        assertEquals("meta",((Token)new LinkedList(mismatchingExprl.asList()).getLast()).getElement());
        assertEquals(3,mismatchingExprl.asList().size());
    }
    
    
    /* parserrl... */
    
}
