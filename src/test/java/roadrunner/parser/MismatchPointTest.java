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
 * MismatchPointTest.java
 * JUnit based test
 *
 * Created on 9 marzo 2003, 11.10
 * @author Valter Crescenzi
 */

package roadrunner.parser;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.net.URL;
import junit.framework.*;

import roadrunner.*;
import roadrunner.bidi.*;
import roadrunner.ast.*;
import roadrunner.parser.*;
import roadrunner.engine.*;

public class MismatchPointTest extends TestCase {
    
    private final static Logger log = Logger.getLogger(ParserTest.class.getName());
    
    private static String  wfn  = "rufusWrapper1.xml";
    private static String  sfnY = "rufus/YByName.html";
    
    private Wrapper rufus;
    private Sample  bynameY;
    private List    mismatches;
    private MismatchPoint first;
    
    public MismatchPointTest(java.lang.String testName) throws Exception {
        super(testName);
        this.rufus = Wrapper.load(FixtureOnFile.getWrapperFile(wfn));
        this.bynameY = new Sample(new URL("file:"+FixtureOnFile.getSampleFile(sfnY)), rufus.getPrefs());
        Parser parser = new Parser(rufus.getExpression());
        parser.parse(bynameY.getTokenlist());
        this.mismatches = parser.getMismatches();
        this.first = (MismatchPoint)this.mismatches.get(0);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MismatchPointTest.class);
        
        return suite;
    }
    
    /** Test of getFirstMismatchingNode method, of class roadrunner.parser.MismatchPoint. */
    public void testGetFirstMismatchingNode() {
        System.out.println("testGetFirstMismatchingNode");
        
        log.info(rufus.getExpression().dump("wrapper>"));
        log.info(bynameY.getTokenlist().toString());
        log.info(this.mismatches.toString());
        
        Node firstNode = first.getFirstMismatchingNode();
        assertEquals(((Token)firstNode).getElement(),"td");
        Map attrs = new HashMap(); attrs.put("width","95"); attrs.put("bgcolor",null);
        assertEquals(((Token)firstNode).getAttributes(), attrs);
    }
    
    /** Test of getFirstMismatchingToken method, of class roadrunner.parser.MismatchPoint. */
    public void testGetFirstMismatchingToken() {
        System.out.println("testGetFirstMismatchingToken");
        Token firstToken = first.getFirstMismatchingToken();
        assertEquals(firstToken.getElement(),"img");
    }
    
    
    /** Test of getLastMatchingNode method, of class roadrunner.parser.MismatchPoint. */
    public void testGetLastMatchingNode() {
        System.out.println("testGetLastMatchingNode");
        ASTToken a = (ASTToken)first.getLastMatchingNode();
        assertEquals("a", a.getElement());
        assertTrue(a.isEndTag());
    }
    
    /** Test of getLastMatchingToken method, of class roadrunner.parser.MismatchPoint. */
    public void testGetLastMatchingToken() {
        System.out.println("testGetLastMatchingToken");
        Token a = first.getLastMatchingToken();
        
        assertEquals("a", a.getElement());
        assertTrue(a.isEndTag());
    }
    
    /** Test of getEmptyBorderRegion method, of class roadrunner.parser.MismatchPoint. */
    public void testGetEmptyBorderRegion() {
        System.out.println("testGetEmptyBorderRegion");
        
        ExpressionRegion empty = first.getEmptyBorderRegion();
        assertEquals(empty.getLeftBorderPos(),empty.getRightBorderPos());
        List baseList = empty.getBase(rufus.getExpression()).jjtGetChildren();
        ListIterator lit = baseList.listIterator(first.getPosBeforeFirstMismatchingNode());
        assertEquals(first.getLastMatchingNode(),lit.previous());
        lit.next();
        assertEquals(first.getFirstMismatchingNode(),lit.next());
    }
    
    /** Test of getMatchingExpression method, of class roadrunner.parser.MismatchPoint. */
    public void testGetMatchingExpression() {
        System.out.println("testGetMatchingExpression");
        
        Region matchingExp = first.getMatchingExpression();
        assertEquals(4,matchingExp.asList().size());
    }
    
    /** Test of getMismatchingExpression method, of class roadrunner.parser.MismatchPoint. */
    public void testGetMismatchingExpression() {
        System.out.println("testGetMismatchingExpression");
        
        Region mismatchingExp = first.getMismatchingExpression();
        assertEquals(1,mismatchingExp.asList().size());
    }
    
    /** Test of getMatchingTokenlist method, of class roadrunner.parser.MismatchPoint. */
    public void testGetMatchingTokenlist() {
        System.out.println("testGetMatchingTokenlist");
        List matchingTlist = first.getMatchingTokenlist().asList();
        assertEquals(matchingTlist.get(first.getTokenIndex()-1), first.getLastMatchingToken());
    }
    
    /** Test of getMismatchingTokenlist method, of class roadrunner.parser.MismatchPoint. */
    public void testGetMismatchingTokenlist() {
        System.out.println("testGetMismatchingTokenlist");
        
        List mismatchingTlist = first.getMismatchingTokenlist().asList();
        assertEquals(mismatchingTlist.get(0), first.getFirstMismatchingToken());
    }
    
    
    /** Test of getAmbiguityMismatches method, of class roadrunner.parser.MismatchPoint. */
    public void testGetAmbiguityMismatches() {
        System.out.println("testGetAmbiguityMismatches");
        //TODO
        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    //
    //  // Add test methods here, they have to start with 'test' name.
    //  // for example:
    //  // public void testHello() {}
    //
    
}
