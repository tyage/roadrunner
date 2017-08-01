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

     Questo  programma Ã¨  software libero; Ã¨  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come Ã¨ pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  Ã¨ distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILITÃ  o di  APPLICABILITÃ PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne puÃ²
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/*
 * RegionTest.java
 * JUnit based test
 *
 * Created on 27 febbraio 2003, 22.07
 *  @author Valter Crescenzi
 */

package roadrunner;

import java.util.*;
import junit.framework.*;

import roadrunner.ast.*;
import roadrunner.parser.*;
import roadrunner.util.Util;
import roadrunner.util.Indenter;

public class RegionTest extends TestCase {
  
  private Expression exp;
  private ExpressionRegion HookC;
  private ExpressionRegion B;
  private ExpressionRegion AC;
  
  public RegionTest(java.lang.String testName) {
    super(testName);

    this.exp = ASTBuilderTest.getFixExpression();
    System.out.println(exp.getRoot().dump(new Indenter(">")));
    
    this.HookC = new ExpressionRegion(this.exp,Node.EMPTY_PATH, 1, 3);
    this.AC = new ExpressionRegion(this.exp,Node.EMPTY_PATH, 0, 3);
    int[] toand = {1,0}; 
    this.B = new ExpressionRegion(this.exp,new PathArray(toand),0,1);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(RegionTest.class);
    
    return suite;
  }
  
  /** Test of getBase method, of class roadrunner.ast.Region. */
  public void testGetBase() {
    System.out.println("testGetBase");
    
    assertEquals(this.exp.getRoot().jjtGetChild(1).jjtGetChild(0), this.B.getBase(this.exp));
    assertEquals(this.exp.getRoot(), this.HookC.getBase());
    assertEquals(this.exp.getRoot(), this.AC.getBase(this.exp));
  }
  
  /** Test of getBasePath method, of class roadrunner.ast.Region. */
  public void testGetBasePath() {
    System.out.println("testGetBasePath");
    int cmpInd[] = {1,0};
    assertEquals(cmpInd[0], this.B.getBasePath().indices()[0]);
    assertEquals(cmpInd[1], this.B.getBasePath().indices()[1]);
    assertEquals(Node.EMPTY_PATH, this.HookC.getBasePath());
    assertEquals(Node.EMPTY_PATH, this.AC.getBasePath());    
  }
  
  /** Test of getLeft/RightBorderPos method, of class roadrunner.ast.Region. */
  public void testGetBorderPos() {
    System.out.println("testGetLeft&RightBorderPos");
    
    assertEquals(0, this.B.getLeftBorderPos());
    assertEquals(1, this.B.getRightBorderPos());
  }
  
  /** Test of asList method, of class roadrunner.ast.Region. */
  public void testAsList() {
    System.out.println("testAsList");
    
    assertEquals(3, this.AC.asList(this.exp).size());
    assertEquals(2, this.HookC.asList(this.exp).size());
    assertEquals(1, this.B.asList(this.exp).size());
  }
  
  
}
