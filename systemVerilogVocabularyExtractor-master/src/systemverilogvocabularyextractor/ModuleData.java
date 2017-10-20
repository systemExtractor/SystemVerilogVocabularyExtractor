/**
 * A classe ModuleData encapsula os dados e os métodos para a modelagem de um
 * module em systemverilog.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 *      All rights reserveds.
 */
public class ModuleData {
    private String nome;
    private MethodProcessor methodProcessorModuleData;
    private FieldProcessor fieldProcessorModuleData;
    private CommentProcessor commentProcessorModuleData;
    private static final String BEGINMODULE = "module";
    private static final String ENDMODULE = "endmodule";
    //private EnumProcessor empr;
    //private InterfaceProcessor iepr;
    
    /**
     * O construtor da classe recebe um argumento que é o nome do module
     * dai ele instancia seus atributos
     * @param nome nome do modulo
     */
    public ModuleData(String nome){
        this.nome = nome;
        this.methodProcessorModuleData = new MethodProcessor();
        this.fieldProcessorModuleData = new FieldProcessor();
        this.commentProcessorModuleData = new CommentProcessor();
    }
    /**
     * O método getMethodProcessorModuleData retorna o processador de métodos
     * do module
     * @return o processador de métodos do module
     */
    public MethodProcessor getMethodProcessorModuleData() {
        return methodProcessorModuleData;
    }
    /**
     * O método setMethodProcessorModuleData (seta) o processador de métodos.
     * @param methodProcessorModuleData referencia de um processador de métodos
     */
    public void setMethodProcessorModuleData(MethodProcessor methodProcessorModuleData) {
        this.methodProcessorModuleData = methodProcessorModuleData;
    }
    /**
     * O método setMethodProcessorModuleData chama internamente o método setFields
     * de methodProcessorModuleData assim não precisa passar uma instancia de um
     * objeto do tipo MethodProcessor
     * @param sourceLine linha de código que será analisada
     */
    public void setMethodProcessorModuleData(String sourceLine){
        this.methodProcessorModuleData.setFields(sourceLine);
    }
    /**
     * O método getFieldProcessorModuleData retorna  o processador de fields
     * do module
     * @return retorna o processador de fields do module.
     */
    public FieldProcessor getFieldProcessorModuleData() {
        return fieldProcessorModuleData;
    }
    /**
     * O método setFieldProcessorModuleData recebe um argumento que é uma referencia
     * a um processador de fields
     * @param fieldProcessorModuleData referencia à um processador de fields.
     */
    public void setFieldProcessorModuleData(FieldProcessor fieldProcessorModuleData) {
        this.fieldProcessorModuleData = fieldProcessorModuleData;
    }
    /**
     * O método setFieldProcessorModuleData chama internamente o método 
     * setListVariaveis de fieldProcessorModuleData, não precisando de uma
     * instancia de um objeto do tipo FieldProcessor
     * @param sourceLine linha de código que será analisada
     */
    public void setFieldProcessorModuleData(String sourceLine){
        this.fieldProcessorModuleData.setListVariaveis(sourceLine);
    }
    /**
     * O método getCommentProcessorModuleData retorna um processador de comentário
     * do module
     * @return retorna um processador de comentários do module
     */
    public CommentProcessor getCommentProcessorModuleData() {
        return commentProcessorModuleData;
    }
    /**
     * O método setCommentProcessorModuleData (seta) o atributo commentProcessorModuleData
     * @param commentProcessorModuleData referencia à um processador de comentário
     */
    public void setCommentProcessorModuleData(CommentProcessor commentProcessorModuleData) {
        this.commentProcessorModuleData = commentProcessorModuleData;
    }
    /**
     * O método toString retorna uma String formatada de acordo com 
     * necessidades
     * @return String formatada
     */
    public String toString(){
        String module = "---------nameModule-------------\n";
        module += this.nome;
        module += "------------FieldsModule--------------\n";
        module += this.fieldProcessorModuleData;
        module += "---------ModuleFunctions-------------\n";
        module += this.methodProcessorModuleData;
        module += "----------commentsModule--------------\n";
        module += "----------commentsModule---------------\n";
        module += this.commentProcessorModuleData;
        return module;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "<module nome=\""+this.nome+"\">\n";
        toXML += this.commentProcessorModuleData.toXML(IDENTATION);
        toXML += this.fieldProcessorModuleData.toXML(IDENTATION);
        toXML += this.methodProcessorModuleData.toXMl(IDENTATION);
        toXML += "</module>\n";
        return toXML;
    }
}