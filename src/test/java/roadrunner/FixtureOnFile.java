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
 * FixtureOnFile.java
 *
 * Created on February 26, 2004, 9:55 PM
 * @author  Valter Crescenzi
 */

package roadrunner;


import java.io.File;
import java.io.FileNotFoundException;

import roadrunner.util.Util;

public class FixtureOnFile {
    
    static {
        System.setProperty("rr.home", ".");
    }
    
    public final static String wrappersDir = "src/test/java/fix/wrappers/";
    public final static String samplesDir  = "src/test/java/fix/samples/";
    public final static String basicDir    = "src/test/java/fix/basic/";
    public final static String prefsDir    = "src/test/java/fix/preferences/";
        
    static public File getWrapperFile(String name) throws FileNotFoundException {
        return Util.searchInRRHOME(wrappersDir+name);
    }
    
    static public File getSampleFile(String name) throws FileNotFoundException {
        return Util.searchInRRHOME(samplesDir+name);
    }

    static public File getPreferencesFile(String name) throws FileNotFoundException {
        return Util.searchInRRHOME(prefsDir+name);
    }
    
    static public File getBasicFile(String name) throws FileNotFoundException {
        return Util.searchInRRHOME(basicDir+name);
    }
}
