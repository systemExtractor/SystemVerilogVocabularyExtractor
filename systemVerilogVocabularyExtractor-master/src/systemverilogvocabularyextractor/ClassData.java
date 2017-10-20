/**
 * A classe ClassData encapsula os dados e os métodos para a modelagem
 * de uma classe de systemverilog.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class ClassData {
    private String name;
    private String superclass;
    private FieldProcessor fieldProcessorClassData;
    private MethodProcessor methodProcessorClassData;
    private TaskProcessor taskProcessorClassData;
    private CommentProcessor commentProcessorClassData;
    private ParamProcessor paramProcessorClassData;
    
    /**
     * O construtor da classe inicializa todos os campos da classe  
     * @param name nome da classe
     * @param superclass superclass ou classe pai
     */
    ClassData(String name, String superclass){
        this.name = name;
        this.superclass = superclass;
        this.fieldProcessorClassData = new FieldProcessor();
        this.methodProcessorClassData = new MethodProcessor();
        this.taskProcessorClassData = new TaskProcessor();
        this.commentProcessorClassData = new CommentProcessor();
        this.paramProcessorClassData = new ParamProcessor();
    }
    /**
     * O construtor da classe que inicializa somente os objetos
     * não recebendo nemhum argumento 
     */
    ClassData(){
        this.fieldProcessorClassData = new FieldProcessor();
        this.methodProcessorClassData = new MethodProcessor();
        this.taskProcessorClassData = new TaskProcessor();
        this.commentProcessorClassData = new CommentProcessor();
        this.paramProcessorClassData = new ParamProcessor();
    }
    /**
     * o método FieldProcessor retorna o processador de atributos
     * @return processador de atributos
     */
    public FieldProcessor getFieldProcessorClassData() {
        return fieldProcessorClassData;
    }
    /**
     * O método getMethodProcessorClassData retorna o processador de métodos
     * @return o processador de métodos
     */
    public MethodProcessor getMethodProcessorClassData() {
        return methodProcessorClassData;
    }
    /**
     * O método getTaskProcessorClassData  retorna o processador de task
     * @return O processador de tasks
     */
    public TaskProcessor getTaskProcessorClassData() {
        return taskProcessorClassData;
    }
    /**
     * O método getCommentProcessorClassData retorna o processador de comentários
     * @return processador de comentarios
     */
    public CommentProcessor getCommentProcessorClassData() {
        return commentProcessorClassData;
    }
    /**
     * O método getParamProcessorClassData retorna o processador de parametros
     * @return o processador de parametros
     */
    public ParamProcessor getParamProcessorClassData() {
        return paramProcessorClassData;
    }
    /**
     * O método getSuperClass retorna a superClass de classe que foi encapsulada
     * por ClassData
     * @return a superClass da classe
     */
    public String getSuperClass(){
        return this.superclass;
    }
    /**
     * O método setSuperClasse recebe um argumento e inicializa o campo superClasse
     * @param superClasse superClass da classe
     */
    public void setSuperClasse(String superClasse){
        this.superclass = superClasse;
    }
    /**
     * O método setName inicializa o campo name da classe
     * @param name nome da classe
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * O método setFieldProcessorClassData recebe um argumento que é uma string
     * internamente chama a função setListVariaveis de fieldProcessorClassData
     * @param linha linha que será analisada por setListVariaveis
     */
    public void setFieldProcessorClassData(String linha) {
        this.fieldProcessorClassData.setListVariaveis(linha);
    }
    /**
     * O método setMethodProcessorClassData recebe uma string e procesa a mesma
     * com setFields de methodProcessorClassData
     * @param linha linha de codigo que será analisada
     */
    public void setMethodProcessorClassData(String linha){
        this.methodProcessorClassData.setFields(linha);
    }
    /**
     * O método setMethodProcessorClassData recebe um MethodProcessor
     * e inicializa o campo methodProcessorClassData
     * @param methodProcessorClassData processador de Métodos
     */
    public void setMethodProcessorClassData(MethodProcessor methodProcessorClassData) {
        this.methodProcessorClassData = methodProcessorClassData; 
    }
    /**
     * O método setTaskProcessorClassData recebe um argumento e a processa 
     * usando setFields de setTaskProcessorClassData
     * @param linha linha de codigo que será analida
     */
    public void setTaskProcessorClassData(String linha) {
        this.taskProcessorClassData.setFields(linha);
    }
    /**
     * O método setCommentProcessorClassData recebe um commentProcessor
     * e inicializa o campo commentProcessorClassData
     * @param comments CommentProcessor
     */
    public void setCommentProcessorClassData(CommentProcessor comments) {
        this.commentProcessorClassData = comments;
    }
    /**
     * O método setParameterProcessorClassData recebe uma linha de codigo 
     * e a processor chamando o método setParametersFormal de paramProcessorClassData
     * @param linha linha de codigo que será analisada
     */
    public void setParameterProcessorClassData(String linha){
        this.paramProcessorClassData.setParametersFormal(linha);
    }
    /**
     * O método toString formata como será a saidá de ClassData
     * @return uma string que contém a string formatada
     */
    public String toString(){
           String classData = "class :"+this.name+" classExtends :"+this.superclass+"\n";
           classData += "----------------ClassFields----------------\n";
           classData += this.fieldProcessorClassData+"\n";
           classData += "----------------ClassComments----------------\n";
           classData += this.commentProcessorClassData+"\n";
           classData += "----------------ClassMethods------------------\n";
           classData += this.methodProcessorClassData+"\n";
           classData += "------------Classtasks---------------------\n";
           classData += this.taskProcessorClassData;
           return classData;
    }  
    public String toXML(){
        final String identation = "    ";
        String toXML = "<class name=\""+this.name+"\" superClass=\""+superclass+"\">\n";
        toXML += this.commentProcessorClassData.toXML(identation);
        toXML += this.fieldProcessorClassData.toXML(identation);
        toXML += this.methodProcessorClassData.toXMl(identation);
        toXML += this.taskProcessorClassData.toXML(identation);
        toXML += "</class>\n";
        return toXML;
    }
}