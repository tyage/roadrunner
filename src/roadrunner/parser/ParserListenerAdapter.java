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

     Questo  programma �  software libero; �  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come � pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  � distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILIT�  o di  APPLICABILIT� PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne pu�
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/*
 * ParserListenerAdapter.java
 *
 * @author  Valter Crescenzi
 * Created on 24 aprile 2003, 20.51
 */

package roadrunner.parser;

import roadrunner.ast.*;

public class ParserListenerAdapter implements ParserListener {
  
  public void startAnd(ASTAnd and)      { startNode(and);  }
  public void startHook(ASTHook hook)   { startNode(hook); }
  public void startPlus(ASTPlus plus)   { startNode(plus); }
  public void startSubtree(ASTSubtree s) { startNode(s);    }
  public void startVariant(ASTVariant v) { startNode(v);    }
  public void startToken(ASTToken node)  { startNode((Node)node); }
  
  public void endAnd(ASTAnd and, boolean matches)                 { endNode(and, matches); }
  public void endHook(ASTHook hook, boolean matches, int times)   { endNode(hook, matches); }  
  public void endPlus(ASTPlus plus, boolean matches, int times)   { endNode(plus, matches); }  
  public void endSubtree(ASTSubtree s, boolean matches, int f, int l) { endNode(s, matches); }  
  public void endVariant(ASTVariant v, boolean matches, Token t)  { endNode(v, matches);     }
  public void endToken(ASTToken node, boolean matches, Token t) { endNode((Node)node, matches); }
  
  public void startNode(Node node)                {}
  public void endNode(Node node, boolean matches) {}  
  
}
