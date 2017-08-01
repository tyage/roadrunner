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
 * BidirectionalListFactoryTest.java
 * JUnit based test
 *
 * Created on 28 febbraio 2003, 12.05
 * @author Valter Crescenzi
 */

package roadrunner.bidi;

import junit.framework.*;

import java.util.*;

public class BidirectionalListFactoryTest extends TestCase {
    
  private BidirectionalList l2r_bidilist1;
  private BidirectionalList r2l_bidilist2;
  private List list1;
  private List list2;
  private Object[] array1 = {"a","b","c"};
  private Object[] array2 = {new Integer(1), new Integer(2), new Integer(3) };
  
  public void setUp() {
    this.list1 = new LinkedList(Arrays.asList(array1));
    this.list2 = new LinkedList(Arrays.asList(array2));
    this.l2r_bidilist1 = BidirectionalListFactory.newListView(Direction.LEFT2RIGHT, list1);
    this.r2l_bidilist2 = BidirectionalListFactory.newListView(Direction.RIGHT2LEFT, list2);
  }
  
  public BidirectionalListFactoryTest(java.lang.String testName) {
    super(testName);
  }
  
  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(BidirectionalListFactoryTest.class);
    
    return suite;
  }
  
  //  /** Test of X method, of class roadrunner.BidirectionalList */
  //  public void testX() {
  //    System.out.println("X");
  //
  //  }
  
  /** Test of listIterator method, of class roadrunner.BidirectionalList */
  public void testListIterator() {
    System.out.println("testListIterator");
    
    ListIterator lit1 = l2r_bidilist1.listIterator(Direction.RIGHT2LEFT);
    assertTrue(lit1.hasNext());
    assertEquals(lit1.next(),array1[2]);
    assertEquals(lit1.next(),array1[1]);
    assertEquals(lit1.next(),array1[0]);
    assertTrue(!lit1.hasNext());
    
    ListIterator lit3 = l2r_bidilist1.listIterator(Direction.LEFT2RIGHT);
    assertTrue(lit3.hasNext());
    assertEquals(lit3.next(),array1[0]);
    assertEquals(lit3.next(),array1[1]);
    assertEquals(lit3.next(),array1[2]);
    assertTrue(!lit3.hasNext());
    
    
    ListIterator lit2 = r2l_bidilist2.listIterator();
    assertTrue(lit2.hasNext());
    assertEquals(lit2.next(),array2[2]);
    assertEquals(lit2.next(),array2[1]);
    assertEquals(lit2.next(),array2[0]);
    assertTrue(!lit2.hasNext());

    ListIterator lit4 = r2l_bidilist2.listIterator(Direction.RIGHT2LEFT);
    assertTrue(lit4.hasNext());
    assertEquals(lit4.next(),array2[2]);
    assertEquals(lit4.next(),array2[1]);
    assertEquals(lit4.next(),array2[0]);
    assertTrue(!lit4.hasNext());
  
    ListIterator lit5 = r2l_bidilist2.listIterator(Direction.LEFT2RIGHT);
    assertTrue(lit5.hasNext());
    assertEquals(lit5.next(),array2[0]);
    assertEquals(lit5.next(),array2[1]);
    assertEquals(lit5.next(),array2[2]);
    assertTrue(!lit5.hasNext());
  }
  
  /** Test of subListTo methods, of class roadrunner.BidirectionalList */
  public void testSubListTo() {
    System.out.println("SubListTo");
    
    List sublist1 = l2r_bidilist1.subListTo(2);
    assertEquals(sublist1.size(),2);
    assertEquals(sublist1.get(0),array1[0]);
    assertEquals(sublist1.get(1),array1[1]);
    
    // Other direction...
    List sublist2 = r2l_bidilist2.subListTo(2);
    assertEquals(sublist2.size(),1);
    assertEquals(sublist2.get(0),array2[2]);
    
  }
  
  /** Test of subListToIndex methods, of class roadrunner.BidirectionalList */
  public void testSubListToIndex() {
    System.out.println("SubListToIndex");
    
    List sublist = l2r_bidilist1.subListToIndex(1);
    assertEquals(sublist.size(),1);
    assertEquals(sublist.get(0),array1[0]);

    // Other direction...
    List sublist2 = r2l_bidilist2.subListToIndex(2);
    assertEquals(sublist2.size(),0);    
  }
  
  
  /** Test of subListFrom methods, of class roadrunner.BidirectionalList */
  public void testSubListFrom() {
    System.out.println("SubListFrom");
    
    List sublist = l2r_bidilist1.subListFrom(2);
    assertEquals(sublist.size(),1);
    assertEquals(sublist.get(0),array1[2]);
    
    // Other direction...
    List sublist2 = r2l_bidilist2.subListFrom(1);
    assertEquals(sublist2.size(),1);
    assertEquals(sublist2.get(0),array2[0]);
  }
   
  
}
