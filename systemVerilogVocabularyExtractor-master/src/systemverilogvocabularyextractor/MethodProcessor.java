/**
 * A classe MethodProcessor encapsula os dados e os métodos necessarios para
 * retirar tudo de importante de um bloco de código que seja uma função
 * comentários, parâmetros, variáveis locais, enums, constantes, e seu retorno 
 * mesmo sendo void.
 */
package systemverilogvocabularyextractor;
import java.util.ArrayList;
/**
 *
 * @author fc.corporation
 */
public class MethodProcessor extends AbstractModuleLanguage{
    private ArrayList<MethodData> arrayMethodData;
    private int size;
    
    /**
     * O construtor da classe  inicializa os atributos da classe.
     */
    public MethodProcessor(){
        super("function", "endfunction");
        this.arrayMethodData = new ArrayList<MethodData>();
        this.size = 0;
    }
    
    public void isCommentFunction(String linha){
        //funçao que tera um finalidade uma hora ou outra
    }
    /**
     * O método setFields processa o nome da função e seu tipo de retorno
     * daí então chama o método setVariableAndCommentlocal que vai retirar 
     * os comentários dentro da função, junto com as variavéis locais
     * @param sourceLine linha de código que será analisada 
     */
    @Override
    public void setFields(String sourceLine){
        final String SPACE = " ";
        final String PARENTESES = "(";
        String[] listWord;
        MethodData methodData = null;
        String linha = this.filterIndentation(sourceLine);
        linha = this.filterAccessMode(linha);
        if(isModule(linha)){
            linha = linha.substring(0, linha.indexOf(PARENTESES));
            listWord = linha.split(SPACE);
            if(listWord.length == 3){
                methodData = new MethodData(listWord[listWord.length-1], this.getReturn(listWord));
                methodData.setParam(sourceLine);
                this.arrayMethodData.add(methodData);
                this.size= this.arrayMethodData.size()-1;
            }
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    /**
     * O método isModule verifica se inicio-se uma nova função systemverilog
     * iniciando-a ele retorna true, caso contrário retorna false.
     * @param sourceLine linha de código que será analisada
     * @return um boolean que será true se inicio-se uma nova função, caso contrário false
     */
    @Override
    public boolean isModule(String sourceLine){
        sourceLine = this.filterIdentation(sourceLine);
        final String ISNOTFUNCTION = "new";
        boolean state = false;
        if(sourceLine.startsWith(BEGINSTRUCT) && !sourceLine.contains(ISNOTFUNCTION)){
            state = true;
            beginStruct = true;
            endStruct = false;
        }
        else if(sourceLine.startsWith(ENDSTRUCT)){
            beginStruct = false;
            endStruct = true;
        }
        return state;
    }
    /**
     * O método getSize retorna o tamanho do array de methodData até o momento
     * de sua invocação 
     * @return um inteiro que é o tamanho d array
     */
    public int getSize(){
        return this.size;
    }
    /**
     * O método getUltimateMethod retorna o ultimo MethodData do array
     * @return retorna o ultimo MethodData do array
     */
    public MethodData getUltimateMethod(){
        return this.arrayMethodData.get(size);
    }
    /**
     * O método isModule diferentemente do que recebe argumentos ele não checa
     * se uma função systemverilog inicio, e sim se ela ainda não encerrou
     * @return um boolean que será true caso o processamento em cima da função
     * ainda não tenha acabado, e retornará false se o fizer.
     */
    public boolean isModule(){
        return this.beginStruct;
    }
    /**
     * O método setVariableAndCommentlocal (seta) as variáveis e os comentários
     * locais a função que estão sendo extraida
     * @param sourceLine linha de código que será analisada.
     */
    @Override
    public void setVariableAndCommentlocal(String sourceLine){
        if(beginStruct && !endStruct){
            this.arrayMethodData.get(arrayMethodData.size()-1).setLocalField(sourceLine);
            this.arrayMethodData.get(arrayMethodData.size()-1).setCommentLocal(sourceLine);
        }
        else if(endStruct){
            beginStruct = false;
            endStruct = false;
        }
    }
    /**
     * O método getReturn concatena as String do array passado como parâmetro
     * fazendo um retorno completo ou seja não somente o tipo como junções de
     * tipos também serão processados EX.: function long int nameExample(),
     * function logic[x:0] nameExample()
     * @param listWord lista de tipos que serão concatenados
     * @return uma String que é a junção dos tipos passado por parâmetros
     */
    private String getReturn(String[] listWord){
        String returnOfFunction = "";
        for(int i=1;i < listWord.length-1;i++){
            returnOfFunction += listWord[i];
        }
        return returnOfFunction;
    }
    /**
     * O método toString retorna uma string formatada
     * @return retorna uma String formatada
     */
    public String toString(){
        String methodProcessor = "";
        for(MethodData mtd: this.arrayMethodData){
            methodProcessor += mtd+"\n";
        }
        return methodProcessor;
    }
    /**
     * o método filterIdentation retira toda a identação do começo como do final
     * da linha de código systemverilog
     * @param sourceLine linha de código que será analisada
     * @return uma String que é a linha de código sem identação
     */
    public String filterIdentation(String sourceLine){
        return sourceLine.trim();
    }
    public String toXMl(String identation){
        String toXMl = "";
        for(MethodData methods: arrayMethodData){
            toXMl += methods.toXML(identation);
        }
        return toXMl;
    }
}