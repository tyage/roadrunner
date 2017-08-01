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
 * WrapperTest.java
 * JUnit based test
 *
 * Created on 26 febbraio 2003, 14.47
 * @author Valter Crescenzi
 */

package roadrunner;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.net.URL;
import java.net.MalformedURLException;
import junit.framework.*;
import org.xml.sax.SAXException;

import roadrunner.*;
import roadrunner.ast.*;
import roadrunner.config.Constants;
import roadrunner.parser.*;
import roadrunner.config.Config;

public class WrapperTest extends TestCase implements Constants {
    
    private final static Logger log = Logger.getLogger(WrapperTest.class.getName());
    
    private final String rw1 = "rufusWrapper1.xml";
    private final String rw2 = "rufusWrapper2.xml";
    private final String rws = "rufusWithSubtree.xml";
    
    private final String fpw = "players.xml";
    private final String fhw = "history.xml";
    
    private final String rsfn2 = "rufus/2ByName.html";
    private final String rsfn3 = "rufus/3ByName.html";
    private final String rsfn7 = "rufus/7ByName.html";
    private final String rsfn9 = "rufus/9ByName.html";
    
    private final String fps1 = "fifa/Cannavaro.html"; 
    private final String fps2 = "fifa/Del Piero.html"; 
    private final String fps3 = "fifa/Nesta.html"; 
    private final String fps4 = "fifa/Zidane.html"; 
    
    private final String fhs1 = "fifa/Denmark.html";
    
    private Wrapper rufus1;
    private Wrapper rufus2;
    private Wrapper rufusS;
    
    private Wrapper players;
    private Wrapper history;
    
    public WrapperTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(WrapperTest.class);
        
        return suite;
    }
    
    public void setUp() throws Exception {
        System.out.println("testLoad");
        
        this.rufus1 = Wrapper.load(FixtureOnFile.getWrapperFile(rw1));
        log.info("Loaded Rufus Wrapper 1");
        
        this.rufus2 = Wrapper.load(FixtureOnFile.getWrapperFile(rw2));
        log.info("Loaded Rufus Wrapper 2");
        
        this.rufusS = Wrapper.load(FixtureOnFile.getWrapperFile(rws));
        log.info("Loaded Rufus Wrapper S");
        
        this.players = Wrapper.load(FixtureOnFile.getWrapperFile(fpw));
        log.info("Loaded Players Wrapper");
        
        this.history = Wrapper.load(FixtureOnFile.getWrapperFile(fhw));
        log.info("Loaded History Wrapper");
    }
    
    /** Test of getExpression method, of class roadrunner.Wrapper. */
    public void testGetExpression() {
        System.out.println("testGetExpression");
        log.info("Expression of Rufus Wrapper 1:\n"+rufus1.getExpression().dump(""));
        ASTAnd root = rufus1.getExpression().getRoot();
        Token meta = (Token)root.jjtGetChild(5);
        ASTPlus plus = (ASTPlus)root.jjtGetChild(60);
        ASTPlus deepplus = (ASTPlus)plus.jjtGetChild(0).jjtGetChild(7);
        ASTVariant variant = (ASTVariant)deepplus.jjtGetChild(0).jjtGetChild(1);
        assertTrue(meta.isTag());
        assertEquals("_E_",variant.getLabel());
    }
    
    /** Test of getName method, of class roadrunner.Wrapper. */
    public void testGetName() {
        System.out.println("testGetName");
        
        assertEquals("Rufus Wrapper Number One",rufus1.getName());
        assertEquals("Rufus Wrapper Number Two",rufus2.getName());
        assertEquals("Rufus Wrapper With Subtree",rufusS.getName());
        assertEquals("fifaplayers",players.getName());
        assertEquals("fifahistory",history.getName());
    }
    
    /** Test of getPrefs method, of class roadrunner.Wrapper. */
    public void testGetPrefs() {
        System.out.println("testGetPrefs");
        
        assertNotNull(this.rufus1.getPrefs());
        log.info("rufus 1 preferences:\n"+rufus1.getPrefs().toString());
        assertEquals(rufus1.getPrefs().getSet(VARIANT_TAGS).size(),6);
        
        assertNotNull(this.rufus2.getPrefs());
        log.info("rufus 1 preferences:\n"+rufus2.getPrefs().toString());
        assertTrue(rufus2.getPrefs().getSet(ATTS_VALUES).contains("width"));
        
        assertNotNull(this.rufusS.getPrefs());
        log.info("rufus 1 preferences:\n"+rufusS.getPrefs().toString());
        assertTrue(rufusS.getPrefs().getSet(IGNORE_ATTS).contains("selected"));
    }
    
    /** Test of wrap method, of class roadrunner.Wrapper. */
    public void testWrap() throws Exception  {
        System.out.println("testWrap");
        String[] rufusSampleNames = { rsfn2, rsfn3, rsfn7, rsfn9};
        wrapAllSamples(rufus1, rufusSampleNames );
        wrapAllSamples(rufus2, rufusSampleNames );
        wrapAllSamples(rufusS, rufusSampleNames );
        
        String[] playerSampleNames = { fps1, fps2, fps3, fps4};
        wrapAllSamples(players, playerSampleNames);
        
        String[] historySampleNames = { fhs1 };
        wrapAllSamples(history, historySampleNames);
    }
    
    private void wrapAllSamples(Wrapper wrapper, String[] sampleNames) throws Exception {
        log.info("Wrappings performed by "+wrapper.getName());
        for (int i=0; i<sampleNames.length; i++) {
            File samplefile = FixtureOnFile.getSampleFile(sampleNames[i]);
            log.info("Samplefile "+samplefile);
            URL url = new URL("file:"+samplefile.getAbsoluteFile());
            Sample sample = new Sample(url, wrapper.getPrefs());
            log.info("about to wrap sample: "+sample.getName());
            log.info(sample.getTokenlist().toString());
            Instance instance = wrapper.wrap(sample);
            log.finer("instance extracted:\n"+instance.dump(sample.getName()+">"));
        }
        // assert that preferences set by wrapper are the one visible through Config
        String[] keys = roadrunner.config.Constants.keys.asArray();
        for (int i=0; i<keys.length; i++) {
            String fromWPrefs = wrapper.getPrefs().getString(keys[i]);
            String fromDefault = Config.getPrefs().getString(keys[i]);
            if (fromWPrefs!=null) assertEquals(fromDefault, fromWPrefs);
        }
    }
    
    /** Test of saveAs method, of class roadrunner.Wrapper. */
    public void testLoadAndSaveAs()  throws Exception {
        System.out.println("testLoadAndSaveAs");
        // load - save - load again, and diff
        Wrapper wrapper;
        File loaded = FixtureOnFile.getWrapperFile(rw1);
        wrapper = Wrapper.load(loaded);
        File saved1 = File.createTempFile("savedFirst",".xml",null);  saved1.deleteOnExit();
        wrapper.saveAs(saved1);
        wrapper = Wrapper.load(saved1);
        File saved2 = File.createTempFile("savedSecond",".xml",null); saved2.deleteOnExit();
        wrapper.saveAs(saved2);
        
        // assertFile(saved1,saved2);// N.B. è pressapoco impossibile farlo andare a buon fine
    }
    
    public void testLoadWrapLoadWrap() throws Exception {
        Wrapper wrapper = Wrapper.load(FixtureOnFile.getWrapperFile(fpw));
        URL  url = new URL("file:"+FixtureOnFile.getSampleFile(fps1).getAbsoluteFile());
        Sample  sample  = new Sample(url, wrapper.getPrefs());
        wrapper.wrap(sample);
        
        wrapper = Wrapper.load(FixtureOnFile.getWrapperFile(fhw));
        url = new URL("file:"+FixtureOnFile.getSampleFile(fhs1).getAbsoluteFile());
        sample  = new Sample(url, wrapper.getPrefs());
        wrapper.wrap(sample);
    }
    
}
