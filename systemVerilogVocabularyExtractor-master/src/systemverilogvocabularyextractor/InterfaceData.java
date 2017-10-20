/**
 * A classe InterfaceData encapsula os dados e os métodos necessarios para 
 * a modelagem de uma interface em systemverilog.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class InterfaceData {
    private String nome;
    private CommentProcessor commentsProcessorInterface;
    private FieldProcessor fieldProcessorInterface;
    private ParamProcessor paramProcessorInterface;
    private MethodProcessor methodProcessorInterface;
    private TaskProcessor taskProcessorInterface;
    private ModPortProcessor modPortProcessorInterface;
    
    /**
     * O contrutor da classe recebe uma String que é o nome da interface
     * daí, cria instancias dos seus atributos
     * @param nome nome da interface
     */
    public InterfaceData(String nome){
        this.nome = nome;
        this.commentsProcessorInterface = new CommentProcessor();
        this.fieldProcessorInterface = new FieldProcessor();
        this.paramProcessorInterface = new ParamProcessor();
        this.methodProcessorInterface = new MethodProcessor();
        this.taskProcessorInterface = new TaskProcessor();
        this.modPortProcessorInterface = new ModPortProcessor();
    }

    /**
     * O método getMethodProcessorInterface retorna o processador de métodos
     * da interface.
     * @return retorna o processador de métodos da interface
     */
    public MethodProcessor getMethodProcessorInterface() {
        return methodProcessorInterface;
    }
    /**
     * O método setMethodProcessorInterface (seta) o processador de métodos 
     * @param methodProcessorInterface referencia de um processador de método 
     * que será passada ao atributo methodProcessorInterface.
     */
    public void setMethodProcessorInterface(MethodProcessor methodProcessorInterface) {
        this.methodProcessorInterface = methodProcessorInterface;
    }
    public void setMethodProcessorInterface(String sourceLine){
        this.methodProcessorInterface.setFields(sourceLine);
    }
    /**
     * O método getTaskProcessorInterface retorna o processador de tasks da interface.
     * @return o processador de método da interface.
     */
    public TaskProcessor getTaskProcessorInterface() {
        return taskProcessorInterface;
    }
    /**
     * O método setTaskProcessorInterface (seta) o atributo taskProcessorInterface.
     * @param taskProcessorInterface referencia de um processador de tasks
     * que será passada ao atributo taskProcessorInterface
     */
    public void setTaskProcessorInterface(TaskProcessor taskProcessorInterface) {
        this.taskProcessorInterface = taskProcessorInterface;
    }
    public void setTaskProcessorInterface(String sourceLine){
        this.taskProcessorInterface.setFields(sourceLine);
    }
    /**
     * O método getCommentsProcessorInterface retorna o processador de
     * comentários da interface
     * @return o processador de comentários da interface
     */
    public CommentProcessor getCommentsProcessorInterface() {
        return commentsProcessorInterface;
    }
    /**
     * O método setCommentsProcessorInterface (seta) o atributo commentsProcessorInterface.
     * @param commentsProcessorInterface referencia de um processador de commentarios.
     */
    public void setCommentsProcessorInterface(CommentProcessor commentsProcessorInterface) {
        this.commentsProcessorInterface = commentsProcessorInterface;
    }
    public void setCommentsProcessorInterface(String sourceLine){
        this.commentsProcessorInterface.setComments(sourceLine);
    }
    /**
     * O método getFieldProcessorInterface retorna o processador de atributos
     * da interface
     * @return o processador de atributos da interface
     */
    public FieldProcessor getFieldProcessorInterface() {
        return fieldProcessorInterface;
    }
    /**
     * O método setFieldProcessorInterface (seta) o atributo fieldProcessorInterface
     * @param fieldProcessorInterface referencia de um processador de atributos.
     */
    public void setFieldProcessorInterface(FieldProcessor fieldProcessorInterface) {
        this.fieldProcessorInterface = fieldProcessorInterface;
    }
    public void setFieldProcessorInterface(String sourceLine){
        this.fieldProcessorInterface.setListVariaveis(sourceLine);
    }
    /**
     * O método getParamProcessorInterface retorna o processador de parâmetos 
     * da interface
     * @return retorna o processador de parâmetros da interface
     */
    public ParamProcessor getParamProcessorInterface() {
        return paramProcessorInterface;
    }
    /**
     * O método setParamProcessorInterface (seta) o atributo paramProcessorInterface.
     * @param paramProcessorInterface referencia de um processador de parâmetros.
     */
    public void setParamProcessorInterface(ParamProcessor paramProcessorInterface) {
        this.paramProcessorInterface = paramProcessorInterface;
    }
    public void setParamProcessorInterface(String sourceLine){
        this.paramProcessorInterface.setParametersFormal(sourceLine);
    }
    public ModPortProcessor getModPortProcessorInterface() {
        return modPortProcessorInterface;
    }

    public void setModPortProcessorInterface(ModPortProcessor modPortProcessorInterface) {
        this.modPortProcessorInterface = modPortProcessorInterface;
    }
    public void setModPortProcessorInterface(String sourceLine){
        this.modPortProcessorInterface.setFields(sourceLine);
    }
    public String toString(){
        String interfaces = "nomeInterface    "+this.nome+"\n";
        interfaces += "fildes     "+"\n"+this.fieldProcessorInterface+"\n";
        interfaces += this.modPortProcessorInterface.toString();
        interfaces += "tasks\n";
        interfaces += this.taskProcessorInterface;
        interfaces += "functions\n";
        interfaces += this.methodProcessorInterface;
        return interfaces;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "<interface nome=\""+this.nome+"\">\n";
        toXML += this.commentsProcessorInterface.toXML(IDENTATION);
        toXML += this.fieldProcessorInterface.toXML(IDENTATION);
        toXML += this.methodProcessorInterface.toXMl(IDENTATION);
        toXML += this.taskProcessorInterface.toXML(IDENTATION);
        toXML += this.modPortProcessorInterface.toXML(IDENTATION);
        toXML += "</interface>\n";
        return toXML;
    }
}