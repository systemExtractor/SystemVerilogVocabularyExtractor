/**
 * A classe MethodData encapsula os dados e os métodos necessarios para 
 * a modelagem de um método de systemverilog no caso uma função.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class MethodData {
    protected ParamProcessor param;
    protected String name;
    private String retorno;
    protected CommentProcessor commentsFunction;
    protected FieldProcessor localField;
    
    //private EnumProcessor enums;
    
    /**
     * O construtor da classe que inicializa todos os campos da classe
     * @param name noma da função extraida
     * @param retorno tipo do retorno que a função tem
     */
    public MethodData(String name, String retorno){
        this.name = name;
        this.retorno = retorno;
        this.commentsFunction = new CommentProcessor();
        this.localField = new FieldProcessor();
        this.param = new ParamProcessor();
    }
    /**
     * O construtor da classe que recebe apenas um argumento mas, também instacia
     * todos os objetos necessarios para a modelagem
     * @param name nome da função extraida
     */
    public MethodData(String name){
        this.name = name;
        this.commentsFunction = new CommentProcessor();
        this.localField = new FieldProcessor();
        this.param = new ParamProcessor();
    }
    /**
     * O método setParam chama internamente o método setParametersFormal de param
     * daí todo o processamento é feito internamente ao objeto param que é do 
     * tipo ParamProcessor
     * @param sourceLine linha de código que será analisada 
     */
    public void setParam(String sourceLine){
        param.setParametersFormal(sourceLine);
    }
    /**
     * O método setLocalField vai procesar todas as variaveis que local a determinada
     * função que está sendo extraida.
     * @param sourceLine linha de código analisada 
     */
    public void setLocalField(String sourceLine){
        localField.setListVariaveis(sourceLine);
    }
    /**
     * O método setCommentLocal extraí os comentários locais a função que está 
     * sendo extraida.
     * @param sourceLine linha de código que será analisada
     */
    public void setCommentLocal(String sourceLine){
        commentsFunction.setComments(sourceLine);
    }
    /**
     * O método setCommentLocal (seta) o atributo commentsFunction
     * @param commentsFunction referencia a um processador de comentarios
     */
    public void setCommentLocal(CommentProcessor commentsFunction){
        this.commentsFunction = commentsFunction;
    }
    /**
     * O método toString retorna uma String formatada
     * @return retorna uma String formatada
     */
    public String toString(){
        String metodo ="nome da função: "+this.name+" retorno: "+ this.retorno+"\n";
        metodo += "----------------Functionparameters----------------------------+\n";
        metodo += this.param;
        metodo += "--------------functionLocalFields----------------------\n";
        metodo += this.localField+"\n";
        metodo += "--------------functionComentarios----------------------\n";
        metodo += this.commentsFunction;
        metodo += "-----------------------------------------------------\n";
        return metodo;
    }
    public String toXML(String identation){
        final String IDENTATION = "    ";
        String toXml = identation+"<function return=\""+this.retorno+"\" name=\""+this.name+"\">\n";
        toXml += this.param.toXML(IDENTATION+identation);
        toXml += this.commentsFunction.toXML(IDENTATION+identation);
        toXml += this.localField.toXML(IDENTATION+identation);
        toXml += IDENTATION+"</function>\n";
        return toXml;
    }
}