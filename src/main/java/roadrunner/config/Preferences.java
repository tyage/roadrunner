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
 * Preferences.java
 *
 * Created on 31 gennaio 2003, 10.32
 * @author  Valter Crescenzi
 */

package roadrunner.config;


import java.util.*;
import java.util.logging.*;
import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import roadrunner.util.DOMLoader;
import roadrunner.util.Indenter;

public class Preferences {
    
    static private Logger log = Logger.getLogger(Preferences.class.getName());
    
    final static public String PREFERENCES = "preferences";
    final static public String NAME        = "name";
    final static public String VALUE       = "value";
    final static public String STAR        = "*";
    final static public String NEG         = "-";
    final static public String SEP         = ",";
    
    private Map prefs;
    private String name;
    
    /** Creates a new instance of Preferences */
    public Preferences() {
        this.prefs = new HashMap();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    
    public void load(File file) throws IOException {
        load(file, PREFERENCES);
    }
    public void load(File file, String base) throws IOException {
        decode(new FileReader(file), base);
    }
    
    public void saveAs(File file) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(file));
        out.println("<?xml version='1.0' encoding=\"ISO-8859-1\"?>");
        encode(out);
        out.close();
    }
    
    public void encode(Indenter ind, Writer output) {
        PrintWriter out = new PrintWriter(output);
        out.println(ind+"<"+PREFERENCES+(getName()!=null?" "+NAME+"=\""+getName()+"\">":">"));
        ind.inc();
        Iterator it = this.prefs.keySet().iterator();
        while (it.hasNext()) {
            String key   = (String)it.next();
            String value = (String)this.prefs.get(key);
            out.println(ind+"<"+key+" "+VALUE+"=\""+value+"\"/>");
        }
        ind.dec();
        out.println(ind+"</"+PREFERENCES+">");
    }
    
    public void encode(Writer output) {
        encode(new Indenter(false),output);
    }
    public void decode(Reader in, String base) throws IOException {
        // Create a DOM builder and parse the fragment
        try {
            decode(DOMLoader.parseXML(in), base);
        }
        catch (SAXException saxe) {
            log.severe("Cannot decode preferences from xml source "+saxe.getMessage());
            System.exit(-1);
        }
    }
    public void decode(Document doc) {
        decode(doc, PREFERENCES);
    }
    public void decode(Document doc, String base) {
        Element prefNode = (Element)doc.getElementsByTagName(base).item(0);
        if (prefNode==null) {
            log.severe("Cannot find a root element node named "+base);
            System.exit(-1);
        }
        setName(prefNode);
        NodeList children = prefNode.getChildNodes();
        int len = children.getLength();
        for ( int i = 0; i < len; i++ ) {
            Node node = children.item(i);
            if (node.getNodeType()!=Document.ELEMENT_NODE) continue; //skip spurious nodes
            String key = node.getNodeName().trim();
            String value = node.getAttributes().getNamedItem(VALUE).getNodeValue();
            prefs.put(key,value);
        }
    }
    
    private void setName(Element prefNode) {
        this.setName(prefNode.hasAttribute(NAME)? prefNode.getAttribute(NAME) : null);
    }
    
    public boolean contains(String key) {
        return this.prefs.containsKey(key);
    }
    
    public int getInt(String key) {
        return Integer.parseInt(this.getString(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(this.getString(key));
    }
    
    public boolean getBoolean(String key) {
        return Boolean.valueOf(this.getString(key)).booleanValue();
    }
    
    public String getString(String key) {
        return (String)this.prefs.get(key);
    }
    
    public Set getSet(String key) {
        if (key==null) return Collections.EMPTY_SET;
        String value = this.getString(key);
        if (value==null || value.trim().length()==0)
            return Collections.EMPTY_SET;
        return csv2set(value);
    }
    
    public void put(String key, String value) {
        this.prefs.put(key, value);
    }
    
    public void putAll(Preferences p) {
        this.prefs.putAll(p.prefs);
    }
    
    public void retainAll(Preferences p) {
        this.prefs.keySet().retainAll(p.prefs.keySet());
    }
    
    public boolean equals(Object o) {
        Preferences p = (Preferences)o;
        return p.prefs.equals(this.prefs);
    }
    
    public int hashCode() {
        return this.prefs.hashCode();
    }
    
    private Set csv2set(String string) {
        StringTokenizer scanner = new StringTokenizer(string,SEP);
        Set result = new HashSet();
        while (scanner.hasMoreTokens()) {
            result.add(scanner.nextToken().toLowerCase().trim());
        }
        result.remove(null);
        return result;
    }
    
    public String toString() {
        StringWriter sw = new StringWriter();
        this.encode(sw);
        return  sw.toString();
    }
}
