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

     Questo  programma ÃÂÃÂ¨  software libero; ÃÂÃÂ¨  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come ÃÂÃÂ¨ pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  ÃÂÃÂ¨ distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILITÃÂÃÂ  o di  APPLICABILITÃÂÃÂ PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne puÃÂÃÂ²
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/*
 * ASTBuilderTest.java
 * JUnit based test
 *
 * Created on 27 febbraio 2003, 11.12
 * @author Valter Crescenzi
 */

package roadrunner.ast;

import junit.framework.*;

import java.util.*;

import roadrunner.parser.*;
import roadrunner.parser.token.*;

public class ASTBuilderTest extends TestCase {
    
    public ASTBuilderTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ASTBuilderTest.class);
        
        return suite;
    }
    
    /** Test of class roadrunner.ast.ASTBuilder. */
    public void testBuilder() {
        System.out.println("testBuilder");
        System.out.println(getFixExpression().getRoot().dump());
    }
    
    public static Expression getFixExpression() {
        ASTBuilder builder = new ASTBuilder();
        TokenFactory factory = TokenFactory.getInstance();
        
        //      And
        //   /    |    \         \
        //  A    Hook   C  Variant
        //        \
        //        And
        //         \
        //          B
        
        ASTAnd and = builder.createAnd();
        builder.addNode(and);
        builder.startNodeScope(and);
        Node token1 = builder.createTokenNode(factory.createTextToken("A",0));
        builder.addNode(token1);
        ASTHook hook = builder.createHook();
        builder.addNode(hook);
        builder.startNodeScope(hook);
        ASTAnd and2 = builder.createAnd();
        builder.addNode(and2);
        builder.startNodeScope(and2);
        Node token2 = builder.createTokenNode(factory.createTextToken("B",0));
        builder.addNode(token2);
        builder.endNodeScope(and2);
        builder.endNodeScope(hook);
        Node token3 = builder.createTokenNode(factory.createTextToken("C",0));
        ASTVariant variant = builder.createVariant(factory.createOpenTagToken("IMG",Collections.EMPTY_MAP,0), "label");
        builder.addNode(token3);
        builder.addNode(variant);
        builder.endNodeScope(and);
        Node root = builder.getRoot();
        assertEquals(and,root);
        assertEquals(token1,root.jjtGetChild(0));
        assertEquals(token2,root.jjtGetChild(1).jjtGetChild(0).jjtGetChild(0));
        assertEquals(token3,root.jjtGetChild(2));
        return new Expression((ASTAnd)root);
    }
    
    public static Expression getFixExpressionWithPlus() {
        ASTBuilder builder = new ASTBuilder();
        TokenFactory factory = TokenFactory.getInstance();
        //      And
        //   /    |    \         \
        //  A    Plus  C  Variant
        //        \
        //        And
        //         \
        //          B
        
        ASTAnd and = builder.createAnd();
        builder.addNode(and);
        builder.startNodeScope(and);
        Node token1 = builder.createTokenNode(factory.createTextToken("A",0));
        builder.addNode(token1);
        ASTPlus plus = builder.createPlus();
        builder.addNode(plus);
        builder.startNodeScope(plus);
        ASTAnd and2 = builder.createAnd();
        builder.addNode(and2);
        builder.startNodeScope(and2);
        Node token2 = builder.createTokenNode(factory.createTextToken("B",0));
        builder.addNode(token2);
        builder.endNodeScope(and2);
        builder.endNodeScope(plus);
        Node token3 = builder.createTokenNode(factory.createTextToken("C",0));
        ASTVariant variant = builder.createVariant(factory.createOpenTagToken("IMG",Collections.EMPTY_MAP,0), "label");
        builder.addNode(token3);
        builder.addNode(variant);
        builder.endNodeScope(and);
        Node root = builder.getRoot();
        assertEquals(and,root);
        assertEquals(token1,root.jjtGetChild(0));
        assertEquals(token2,root.jjtGetChild(1).jjtGetChild(0).jjtGetChild(0));
        assertEquals(token3,root.jjtGetChild(2));
        return new Expression((ASTAnd)root);
    }
    
}
