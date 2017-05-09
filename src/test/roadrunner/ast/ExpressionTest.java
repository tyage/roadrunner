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
 * ExpressionTest.java
 * JUnit based test
 *
 * Created on 16 marzo 2003, 10.55
 * @author Valter Crescenzi
 */

package roadrunner.ast;

import junit.framework.*;

import java.util.*;
import java.util.logging.*;

import roadrunner.Sample;
import roadrunner.bidi.*;
import roadrunner.parser.*;
import roadrunner.parser.token.*;

public class ExpressionTest extends TestCase {
      
  private final static Logger log = Logger.getLogger(ExpressionTest.class.getName());
      
  public ExpressionTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(ExpressionTest.class);
    
    return suite;
  }
  
  private Expression exp;
  private Node.Path pathOfB;
  private Region regionOfB;
  private Region regionOfCVar;
  private TokenFactory factory;
  
  protected void setUp() {
    this.exp = ASTBuilderTest.getFixExpression();
    int [] indicesOfBpath = {1,0,0};
    this.pathOfB = new PathArray(indicesOfBpath);
    int [] indicesOfBparent = {1,0};
    Node.Path pathOfBparent = new PathArray(indicesOfBparent);
    this.regionOfB = new ExpressionRegion(this.exp, pathOfBparent, 0, 1);
    this.regionOfCVar = new ExpressionRegion(this.exp, Node.EMPTY_PATH, 2, 4);
    this.factory = TokenFactory.getInstance();
  }
  
  //         And
  //   /    |    \   \
  //  A    Hook   C   Variant
  //        \
  //        And
  //         \
  //          B
  
  
  /** Test of asRegion method, of class roadrunner.ast.Expression. */
  public void testAsRegion() {
    System.out.println("testAsRegion");
    List list = this.exp.asRegion().asList(this.exp);
    Iterator it = list.iterator();
    int one[] = new int[1];
    one[0]=0;
    //
    assertEquals(this.exp.getNode(new PathArray(one)), it.next());
    one[0]++;
    //
    assertEquals(this.exp.getNode(new PathArray(one)), it.next());
    one[0]++;
    //
    assertEquals(this.exp.getNode(new PathArray(one)), it.next());
    one[0]++;
    //
    assertEquals(this.exp.getNode(new PathArray(one)), it.next());
    one[0]++;    
  }
  
  /** Test of replaceNode method, of class roadrunner.ast.Expression. */
  public void testReplaceNode() {
    System.out.println("testReplaceNode");
    Node newNode = factory.createTextToken("new text", 2);
    this.exp.replaceNode(this.pathOfB, newNode);
    assertEquals(exp.getNode(pathOfB), newNode);
  }
  
  /** Test of addPlus method, of class roadrunner.ast.Expression. */
  public void testAddPlus() {
    System.out.println("testAddPlus");
    
    Node underThePlus = factory.createTextToken("under the plus", 0);
    ASTAnd newSquare = new ASTAnd(Collections.singletonList(underThePlus));
    
    Expression result = this.exp.addPlus((ExpressionRegion)regionOfB, new Expression(newSquare));
    int indices[] = {1,0,0,0,0};
    Node.Path underThePlusPath = new PathArray(indices);
    log.info(result.dump("addPlus>"));
    assertEquals(result.getNode(underThePlusPath),underThePlus);
  }
  
  /** Test of addHook method, of class roadrunner.ast.Expression. */
  public void testAddHook() {
    System.out.println("testAddHook");
    
    Node underTheHook = factory.createTextToken("under the hook", 0);
    ASTAnd newSquare = new ASTAnd(Collections.singletonList(underTheHook));
    
    Expression result = this.exp.addHook((ExpressionRegion)regionOfB, new Expression(newSquare));
    int indices[] = {1,0,0,0,0};
    Node.Path underTheHookPath = new PathArray(indices);
    log.info(result.dump("addHook>"));
    assertEquals(result.getNode(underTheHookPath),underTheHook);
  }
  
  public void testAddHook2() {
    System.out.println("testAddHook2");
    
    Node underTheHook2 = factory.createTextToken("under the hook2", 0);
    ASTAnd newSquare = new ASTAnd(Collections.singletonList(underTheHook2));
    
    Expression result = this.exp.addHook((ExpressionRegion)regionOfCVar, new Expression(newSquare));
    int indices[] = {2,0,0};
    Node.Path underTheHook2Path = new PathArray(indices);
    log.info(result.dump("addHook2>"));
    assertEquals(result.getNode(underTheHook2Path),underTheHook2);
  }
    
  public void testExpressionEquals() {
      System.out.println("testExpressionEquals");
      
      assertEquals(ASTBuilderTest.getFixExpression(), 
                   ASTBuilderTest.getFixExpression());
      assertEquals(ASTBuilderTest.getFixExpressionWithPlus(), 
                   ASTBuilderTest.getFixExpressionWithPlus());
      assertFalse(ASTBuilderTest.getFixExpression().equals(ASTBuilderTest.getFixExpressionWithPlus()));
  }
  
  // Add test methods here, they have to start with 'test' name.
  // for example:
  // public void testHello() {}
  
}
