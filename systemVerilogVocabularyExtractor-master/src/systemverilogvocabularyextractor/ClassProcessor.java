/**
 * A classe ClassProcessor tem atributos e métodos suficientes para todo o processamento
 * há partir de linhas de codigos montar tudo de relativo a uma classe de 
 * systemVerilog.
 */
package systemverilogvocabularyextractor;

import java.util.ArrayList;

/**
 *
 * @author fc.corporation
 */
public class ClassProcessor extends AbstractModuleLanguage{
    private int size;
    private ArrayList<ClassData> arrayClassData;
    private VerificationSintax vfs;
    private CommentProcessor genericsCommentsClassProcessor;
    private static final String BEGINCLASS = "class";
    private static final String ENDCLASS = "endclass";
    
    /**
     * O construtor da classe que inicializa todos os campos e chama o método
     * setAvlTreeSintax de vfs passando como argumento o retorno da função
     * setWordsKeys também de vfs.
     */
    public ClassProcessor(){
        super(BEGINCLASS, ENDCLASS);
        this.arrayClassData = new ArrayList<ClassData>();
        this.genericsCommentsClassProcessor = new CommentProcessor();
        vfs = new VerificationSintax();
        vfs.setAvlTreeSintax(vfs.setWordsKeys());
    }
    /**
     * O método setClassComents recebe um argumento do tipo CommentProcessor
     * e então passa sua referencia para o ultimo elemento de arrayClassData
     * @param classComments referencia do processador de commentarios 
     */
    public void setClassComents(CommentProcessor classComments){
        this.arrayClassData.get(size).setCommentProcessorClassData(classComments);
    }
    /**
     * O método setClassesProperties verifica se inicio uma classe iniciando-a
     * chama o método setFields e apos isso incrementa o tamanho
     * @param sourceLine linha de codigo que será analisada
     */
    public void setClassesProperties(String sourceLine){
        if(this.isModule(sourceLine)){
            this.setFields(sourceLine);
            size = this.arrayClassData.size()-1;
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    /**
     * O método setFields é sobrescrito, este método recebe uma string e 
     * retira dela o nome da classe junto com sua herança caso ela exista
     * também retira os parâmetros da classe.
     * @param lineOrigin linha de código que será analisada.
     */
    @Override
    public void setFields(String lineOrigin) {
        lineOrigin = this.filterAccessMode(lineOrigin);
        lineOrigin = this.filterIndentation(lineOrigin);
        ClassData csdt = new ClassData();
        ArrayList<String> properties = new ArrayList<String>();
        String[] wordsInLine = lineOrigin.split(" ");
        int index=1;
        properties.add(wordsInLine[1]);
        for(;index < wordsInLine.length;index++){
        try{
            if(wordsInLine[index].equals("extends") && index != wordsInLine.length-1){
                properties.add(wordsInLine[index+1]);
                break;
            }
        }catch(ArrayIndexOutOfBoundsException aio){}
        }
        csdt.setName(properties.get(0));
        if(properties.size()== 2)
            csdt.setSuperClasse(properties.get(properties.size()-1));
        this.arrayClassData.add(csdt);
    }
    /**
     * O método setVariableAndCommentlocal modela todo interior de uma classe
     * systemverilog, ou seja, há partir de outros métodos ele modela
     * funções tastks e seus respctivos comentários.
     * @param sourceLine linha de código que será analisada.
     */
    @Override
    public void setVariableAndCommentlocal(String sourceLine) {
        if(beginStruct && !endStruct){
            ClassData classTemp = this.arrayClassData.get(this.arrayClassData.size()-1);
            if(this.genericsCommentsClassProcessor.isCommentBlock(sourceLine))
                this.genericsCommentsClassProcessor.setComments(sourceLine);
            else{
                classTemp.setMethodProcessorClassData(sourceLine);
                classTemp.setTaskProcessorClassData(sourceLine);
                if(!classTemp.getMethodProcessorClassData().isModule() && 
                        !classTemp.getTaskProcessorClassData().isModule())
                    classTemp.setFieldProcessorClassData(sourceLine);
                else if(classTemp.getMethodProcessorClassData().isModule(sourceLine)){
                    this.genericsCommentsClassProcessor.setBeginComments(false);
                    this.genericsCommentsClassProcessor.setEndComments(false);
                    classTemp.getMethodProcessorClassData().getUltimateMethod()
                            .setCommentLocal(this.genericsCommentsClassProcessor);
                    this.genericsCommentsClassProcessor = new CommentProcessor();
                }
                else if(classTemp.getTaskProcessorClassData().isModule(sourceLine)){
                    this.genericsCommentsClassProcessor.setBeginComments(false);
                    this.genericsCommentsClassProcessor.setEndComments(false);
                    classTemp.getTaskProcessorClassData().getUltimateTaskData()
                            .setCommentLocal(genericsCommentsClassProcessor);
                    this.genericsCommentsClassProcessor = new CommentProcessor();
                }
            }
        }
        else if(endStruct){
            beginStruct = false;
            endStruct = false;
        }
    }
    /**
     * O método setSuperClass recebe um argumento e então retira a superClass
     * da classe que será analisada
     * @param sourceLine linha de código.
     */
    public void setSuperClass(String sourceLine){
        sourceLine = this.filterIndentation(sourceLine);
        final String EXTENDS = "extends";
        final int SIZEEXTENDS = EXTENDS.length();
        final char BEGINPARAM = 35; //in ascii 35 = #
        final String STRINGBEGINPARAM = "#";
        String superClass = null;
        if(sourceLine.contains(EXTENDS)){
            superClass = sourceLine.substring(sourceLine.indexOf(EXTENDS)+SIZEEXTENDS);
        }
        else superClass = sourceLine.substring(0, sourceLine.indexOf(" "));
        this.arrayClassData.get(this.size).setSuperClasse(superClass);
    }
    /**
     * O método getSuperClass retorna a superClass da ultima classe extraida 
     * até o momento da chamada desta função.
     * @return uma String que é a superClass da ultima classe extráida
     */
    public String getSuperClass(){
        return this.arrayClassData.get(size).getSuperClass();
    }
    /**
     * O método toString retorna uma string formatada de acordo com a necessidade
     * atual
     * @return uma String que é tudo das classes que foi extraida. 
     */
    @Override   
    public String toString(){
        String classProc = "";
        for(ClassData str: this.arrayClassData){
            classProc += str+"\n";
        }
        return classProc;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "";
        for(ClassData cls: this.arrayClassData){
            toXML += cls.toXML();
        }
        return toXML;
    }
}