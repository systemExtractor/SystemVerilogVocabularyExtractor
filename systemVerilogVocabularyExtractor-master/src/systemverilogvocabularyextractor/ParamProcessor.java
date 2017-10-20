/**
 * A classe ParamProcessor encapsula os dados e os métodos para poder processar
 * parâmetros em modulos de systemverilog
 */
package systemverilogvocabularyextractor;

import java.util.ArrayList;

/**
 *
 * @author fc.corporation
 */
public class ParamProcessor {
   private FieldProcessor  parametersFormal;
   private boolean beginParam;
   private boolean endParam;
   private static String BEGINPARAM = "(";
   private static String ENDPARAM = ")";
    
   /**
    * O construtor da classe não recebe argumento.
    */
    public ParamProcessor(){
        this.parametersFormal = new FieldProcessor();        
    }
    /**
     * O método setParametersFormal recebe um argumento que é a linha que será 
     * retirada apenas os parâmetros formais de funções, tasks, modules, interfaces
     * classes
     * @param sourceLine linha de código que será processada 
     */
    public void setParametersFormal(String sourceLine){
        final char BEGINPARAM = '(';
        final String ENDPARAM = ")";
        String[] listString;
        sourceLine = sourceLine.substring(sourceLine.indexOf(BEGINPARAM)+1).replace(ENDPARAM, "");
        listString = sourceLine.split(",");
        for(String str: listString){
            this.parametersFormal.setListVariaveis(str);
        }
    }  
    public String toString(){
        return this.parametersFormal.toString();
    }   
    public String toXML(String identation){
        final String IDENTATION = "    ";
        String toXML = identation+"<param>\n"+this.parametersFormal.toXML(identation+IDENTATION);
        toXML += identation+"</param>\n";
        return toXML;
    }
}