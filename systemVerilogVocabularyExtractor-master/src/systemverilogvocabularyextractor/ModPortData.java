/**
 * A classe ModPortData guarda os dados e os métodos para a modelagem de um 
 * modpord de systemverilog.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class ModPortData {
    private String nome;
    private CommentProcessor commentsProcesorModPort;
    private FieldProcessor fieldsModPort;
    
    /**
     * O construtor da classe recebe um argumento que é o nome do modport
     * depois ele inicializa os outros atributos da classe
     * @param nome nome do modport
     */
    public ModPortData(String nome){
        this.nome = nome;
        this.commentsProcesorModPort = new CommentProcessor();
        this.fieldsModPort = new FieldProcessor();
    }
    /**
     * o método getCommentsProcesorModPort retorna o processador de comentários
     * do modport
     * @return retorna o processador de comentários do modport
     */
    public CommentProcessor getCommentsProcesorModPort() {
        return commentsProcesorModPort;
    }
    /**
     * O método setCommentsProcesorModPort inicializa o atributo commentsProcesorModPort
     * @param commentsProcesorModPort referencia à um processador de comentários
     */
    public void setCommentsProcesorModPort(CommentProcessor commentsProcesorModPort) {
        this.commentsProcesorModPort = commentsProcesorModPort;
    }
    /**
     * O método getFieldsModPort retorna o processador de atributos do modport
     * @return retorna o processador de atributos do modport
     */
    public FieldProcessor getFieldsModPort() {
        return fieldsModPort;
    }
    /**
     * O método setFieldsModPort inicializa o campo fieldsModPort
     * @param fieldsModPort referencia à um processador de fields
     */
    public void setFieldsModPort(FieldProcessor fieldsModPort) {
        this.fieldsModPort = fieldsModPort;
    }
    /**
     * O método toString retorna uma String formatada
     * @return retorna uma String formatada
     */
    public String toString(){
        String modPort = "-----------------------------\n";
        modPort += "name modPort: "+ this.nome+"\n";
        modPort += "---------param modPort----------\n";
        modPort += this.fieldsModPort;
        modPort += "--------commentsModPort----------\n";
        modPort += this.commentsProcesorModPort;
        return modPort;
    }
    public String toXML(String identation){
        final String IDENTATAION = "    ";
        String toXML = identation+"<modport name=\""+this.nome+"\">\n";
        toXML += this.fieldsModPort.toXML(identation+IDENTATAION);
        toXML += identation+"</modport>\n";
        return toXML;
    }
}