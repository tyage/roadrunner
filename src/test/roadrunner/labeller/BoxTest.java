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
 * BoxTest.java
 * JUnit based test
 *
 * Created on December 14, 2003, 3:44 PM
 * @author Valter Crescenzi
 */

package roadrunner.labeller;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.util.*;
import junit.framework.*;

public class BoxTest extends TestCase {
    
    public BoxTest(java.lang.String testName) {
        super(testName);
    }
    
    private Box obox;
    
    public static Test suite() {
        TestSuite suite = new TestSuite(BoxTest.class);
        return suite;
    }
    
    public void setUp() {
        this.obox = new Box(0,0, 10, 10);
    }
    
    /** Test of getCenter method, of class roadrunner.labeller.Box. */
    public void testGetCenter() {
        System.out.println("testGetCenter");
        
        assertEquals(new Point2D.Double(5,5),this.obox.getCenter());
    }
    
    /** Test of distance method, of class roadrunner.labeller.Box. */
    public void testDistance() {
        System.out.println("testDistance");
        
        assertEquals("zero distance", 0.0, this.obox.distance(new Box(0,10, 10,20)), 0.0);
        assertEquals("horizontal distance", 80.0, this.obox.distance(new Box(90,0, 90,10)), 0.0);
        assertEquals("diagonal distance", 50.0, this.obox.distance(new Box(40,50, 50,80)), 0.0);
    }
    
    /** Test of alignment method, of class roadrunner.labeller.Box. */
    public void testAlignment() {
        System.out.println("testAlignment");
        
        assertEquals("perfectly horizontally aligned", 0, new Box(0,0,10,10).alignment(new Box(100,0,110,10)), 0.0);
        assertEquals("perfectly vertically aligned", 0, new Box(0,0,10,10).alignment(new Box(0,100,10,110)), 0.0);
        assertEquals("perfectly diagonally aligned", Math.PI/4, new Box(0,0,0,0).alignment(new Box(10,10,10,10)), 0.0001);
        assertEquals("centers are horizontally aligned", 0, new Box(100,100,130,130).alignment(new Box(200,110,300,120)), 0.0000001);
    }
    
    /** Test of isInBetween method, of class roadrunner.labeller.Box. */
    public void testIsInBetween() {
        System.out.println("testIsInBetween");
        
        //two boxes which do not overlap vertically
        Box box1 = new Box(0,10,10,20);
        Box box2 = new Box(20,30,30,40);
        assertTrue(new Box(10,20,20,30).isInBetween(box1,box2));
        assertTrue(new Box(10,20,20,30).isInBetween(box2,box1));
        assertFalse(new Box(15,10,20,20).isInBetween(box1,box2));
        assertFalse(new Box(15,10,20,20).isInBetween(box2,box1));
        
        //two boxes which overlap vertically
        Box box3 = new Box(0,0,10,40);
        Box box4 = new Box(50,10,60,30);
        assertTrue(new Box(20,15,40,25).isInBetween(box3,box4));
        assertTrue(new Box(20,15,40,25).isInBetween(box4,box3));
        assertFalse(new Box(20,0,40,8).isInBetween(box3,box4));
        assertFalse(new Box(20,0,40,8).isInBetween(box4,box3));
    }
    
    /** Test of compareTo method, of class roadrunner.labeller.Box. */
    public void testCompareTo() {
        System.out.println("testCompareTo");
        
        assertTrue(new Box(0,0,10,10).compareTo(new Box(30,30, 40, 40))<0);
        assertTrue(new Box(30,30, 40, 40).compareTo(new Box(0,0,10,10))>0);
        assertTrue(new Box(0,0, 10, 10).compareTo(new Box(0,0,10,10))==0);
        
        assertTrue(new Box(0,0, 10, 10).compareTo(new Box(0,1,10,10))<0);
        assertTrue(new Box(0,0, 10, 10).compareTo(new Box(1,0,10,10))<0);        
        assertTrue(new Box(0,0, 10, 10).compareTo(new Box(0,0,10,11))<0);        
        assertTrue(new Box(0,0, 10, 10).compareTo(new Box(0,0,11,10))<0);
        
        assertTrue(new Box(0,0, 0,0).compareTo(new Box(0,0,0,0))==0);       
    }
    
    // Add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    public static void main(String[] args) {
        new BoxTest("test x debug").testAlignment();
    }
    
}
