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
 * TestOfTheYearTest.java
 * JUnit based test
 *
 * Created on 16 ottobre 2003, 17.54
 * @author Valter Crescenzi
 */

package roadrunner.engine;

import junit.framework.*;

import java.io.*;
import java.util.*;

import roadrunner.ast.Expression;
import roadrunner.parser.TokenList;
import roadrunner.Wrapper;
import roadrunner.engine.sampler.*;

import roadrunner.FixtureOnFile;

public class TestOfTheYearTest extends TestCase {
    
    private static String  wfilename   = "rufusWrapper2.xml";
    
    public TestOfTheYearTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(TestOfTheYearTest.class);
        return suite;
    }
    
    // Add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    public void testOfTheYear() throws Exception {
        Wrapper wrapper = Wrapper.load(FixtureOnFile.getWrapperFile(wfilename));
        CharacteristicSet samples = new CharacteristicSet(wrapper.getExpression());
        Iterator it = samples.listIterator();
        List chi = new LinkedList();
        while (it.hasNext()) {
            chi.add(it.next());
        }
        TokenList[] tokenlists = (TokenList[])chi.toArray(new TokenList[0]);
        Engine engine = new Engine(wrapper.getExpression(), tokenlists,0);
        Set solutions = engine.match();
        assertTrue(solutions.size()>0);
        // Assert that original expression exists in the set of solutions
        assertTrue(solutions.contains(wrapper.getExpression()));
        
        // Assert that it is the FIRST solution!        
        Expression solution = (Expression)solutions.iterator().next();        
        assertEquals(wrapper.getExpression(), solution);
        System.out.println(solution.dump(""));
    }
    
    
}
