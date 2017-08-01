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
 * CharacteristicSamplerTest.java
 * JUnit based test
 *
 * Created on 2 aprile 2003, 15.09
 * @author Valter Crescenzi
 */

package roadrunner.engine.sampler;

import junit.framework.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import roadrunner.bidi.Direction;
import roadrunner.bidi.Region;
import roadrunner.ast.*;
import roadrunner.parser.Parser;
import roadrunner.parser.Token;
import roadrunner.parser.TokenList;
import roadrunner.Wrapper;

import roadrunner.FixtureOnFile;

public class CharacteristicSamplerTest extends TestCase {
    
    private final static Logger log = Logger.getLogger(CharacteristicSamplerTest.class.getName());
    
    private static String wfn = "rufusWithSubtree.xml";
    
    public CharacteristicSamplerTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(CharacteristicSamplerTest.class);
        
        return suite;
    }
    
    /** Test of getCharacteristicSampleIterator method, of class roadrunner.engine.CharacteristicSampler. */
    public void testGetCharacteristicSampleIterator() throws Exception {
        System.out.println("testGetCharacteristicSampleIterator");
        
        Wrapper wrapper = Wrapper.load(FixtureOnFile.getWrapperFile(wfn));
        Parser parserlr = new Parser(wrapper.getExpression(),Direction.LEFT2RIGHT);
        Parser parserrl = new Parser(wrapper.getExpression(),Direction.RIGHT2LEFT);
        CharacteristicSet samples = new CharacteristicSet(wrapper.getExpression());
        Iterator it = samples.listIterator();
        int c=0;
        while (it.hasNext()) {
            TokenList tl = (TokenList)it.next();
            log.info("sample "+c+"\n");
            log.info(tl.getTokens().toString());
            assertTrue(parserlr.parse(tl));
            assertTrue(parserrl.parse(tl));
            c++;
        }
        assertEquals(2,c);
        
    }
    
    
    // Add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
    
}
