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
 * LLCheckerTest.java
 * JUnit based test
 *
 * Created on 8 ottobre 2003, 14.29
 * @author Valter Crescenzi
 */

package roadrunner.engine;

import junit.framework.*;

import java.util.*;
import java.util.logging.*;

import roadrunner.bidi.*;
import roadrunner.ast.*;
import roadrunner.parser.Token;

public class LLCheckerTest extends TestCase {
    
    public LLCheckerTest(java.lang.String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(LLCheckerTest.class);
        return suite;
    }
    
    
    //         And
    //   /    |    \   \
    //  A    Hook   C   Variant
    //        \
    //        And
    //         \
    //          B
    
    /** Test of getPrefixes method, of class roadrunner.engine.LLChecker. */
    public void testGetPrefixesWithHook() {
        System.out.println("testGetPrefixesWithHook");
        Expression exp = ASTBuilderTest.getFixExpression();
        Direction dir = Direction.LEFT2RIGHT;
        LLChecker checker = new LLChecker(3,dir);
        List prefixes = checker.getPrefixes(exp.asRegion());
        assertEquals(2, prefixes.size());
        
        System.out.println(dir+" "+prefixes);
        
        dir = Direction.RIGHT2LEFT;
        checker = new LLChecker(3,dir);
        prefixes = checker.getPrefixes(exp.asRegion());
        assertEquals(2, prefixes.size());
        
        System.out.println(dir+" "+prefixes);
    }
    
    /** Test of getPrefixes method, of class roadrunner.engine.LLChecker. */
    public void testGetPrefixesWithPlus() {
        System.out.println("testGetPrefixesWithPlus");
        //      And
        //   /    |    \         \
        //  A    Plus  C  Variant
        //        \
        //        And
        //         \
        //          B
        Expression exp = ASTBuilderTest.getFixExpressionWithPlus();
        Direction dir = Direction.LEFT2RIGHT;
        LLChecker checker = new LLChecker(3,dir);
        int [] indicesOfB = {1,0,0};
        Node.Path pathOfB = new PathArray(indicesOfB);
        List prefixes = checker.getPrefixes(exp.asRegion());
        System.out.println(dir+" "+prefixes);
        assertEquals(2,prefixes.size());
        List prefix = (List)prefixes.get(0);
        assertEquals(3,prefix.size());
        assertEquals(exp.getNode(pathOfB), prefix.get(1));
        prefix = (List)prefixes.get(1);
        assertEquals(exp.getNode(pathOfB), prefix.get(1));
        
        checker = new LLChecker(2,dir);
        prefixes = checker.getPrefixes(exp.asRegion());
        assertEquals(1,prefixes.size());
    }
    
    
    // Add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
