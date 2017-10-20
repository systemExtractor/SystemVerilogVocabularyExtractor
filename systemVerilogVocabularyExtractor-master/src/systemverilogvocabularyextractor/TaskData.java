/**
 * A classe TaskData encapsula os dados e os métodos necessarios para a modelagem
 * de uma task em systemverilog extendo de MethodData
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class TaskData extends MethodData{
    
    /**
     * O construtor da classe que recebe um argumento que é o nome da task
     * @param name nome da task
     */
    public TaskData(String name) {
        super(name);
    }
    @Override
    public String toString(){
        String metodo ="nome da task: "+this.name+"\n";
        metodo += "----------------parametros----------------------------+\n";
        metodo += this.param;
        metodo += "--------------variaveis Locais----------------------\n";
        metodo += this.localField+"\n";
        metodo += "--------------Comentarios----------------------\n";
        metodo += this.commentsFunction;
        metodo += "-----------------------------------------------------\n";
        return metodo;
    }   
    @Override
    public String toXML(String identation){
        final String IDENTATION = "    ";
        String toXML = identation+"<task name=\""+this.name+"\">\n";
        toXML += this.param.toXML(IDENTATION+identation);
        toXML += this.commentsFunction.toXML(IDENTATION+identation);
        toXML += this.localField.toXML(IDENTATION+identation);
        toXML += identation+"</task>\n";
        return toXML;
    }
}