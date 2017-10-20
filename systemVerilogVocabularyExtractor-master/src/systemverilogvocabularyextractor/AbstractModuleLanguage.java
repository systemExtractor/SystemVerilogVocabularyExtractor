/**
 * A classe abstrata Modulo reuni caracteristicas de um modulo genirico
 * de systemverilog, ou seja, um modulo de código que começo e fim comentários
 * relativos à tal modulo suas variaveis locais.
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
abstract class AbstractModuleLanguage {
    protected boolean beginStruct;
    protected boolean endStruct;
    protected String BEGINSTRUCT;
    protected String ENDSTRUCT;
    
    /**
     * O construtor da classe recebe dois parâmetros que são o começo do modulo
     * e o fin do modulo.
     * @param begin começo do modulo
     * @param end fim do modulo
     */
    public AbstractModuleLanguage(String begin, String end){
        this.BEGINSTRUCT = begin;
        this.ENDSTRUCT = end;
        this.beginStruct = false;
        this.endStruct = false;
    }
    /**
     * O método abstrato setFields será o principal da classe, então ha partir
     * dele que será invocado os outros métodos
     * @param sourceLine linha de código que será analisada 
     */
    abstract void setFields(String sourceLine);
    
    /**
     * O métedo setVariableAndCommentlocal vai processar tudo dentro do modulo
     * que não seja o seu nome, ou seja, as variaveis locais, comentarios, varia
     * de modulo para modulo.
     * @param sourceline linha de código que será analisada 
     */
    abstract void setVariableAndCommentlocal(String sourceline);
    
    /**
     * O método isModule verifica se inicio-se um novo modulo systemverilog
     * daí retorna true, caso contrário retorna false
     * @param sourceLine linha de código que será analisada
     * @return um boolean que será true se inicio-se um novo modulo, do contrário
     * será retornado false
     */
    protected boolean isModule(String sourceLine){
        sourceLine = this.filterAccessMode(sourceLine);
        boolean state = false;
        final char STX = 2; //start of text
        final char SOB = 1;//start of heading
        if(sourceLine.startsWith(BEGINSTRUCT) || sourceLine.startsWith(STX+BEGINSTRUCT)
                || sourceLine.startsWith(SOB+BEGINSTRUCT)){
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
     * O método isModule sem argumento é uma sobrecarga do método isModule
     * a finalidade deste é retornar true enquanto o modulo não encerrar
     * @return true se o modulo não terminou, e false se o modulo terminou.
     */
    protected boolean isModule(){
        return this.beginStruct;
    }
    /**
     * O método filterAccessMode filtra os modificadores de acesso dos modulos
     * por enquanto a modelagem do extrator não importa o modo de acesso dos
     * modulos
     * @param sourceLine linha de código que será analisada
     * @return uma Sting que é o codigo ssystemverilog agora sem modificadores de
     * acesso
     */
    protected String filterAccessMode(String sourceLine){
        if(sourceLine.contains(BEGINSTRUCT+" ") && !sourceLine.contains(ENDSTRUCT)&& !sourceLine.contains("=")){
            sourceLine = sourceLine.substring(sourceLine.indexOf(BEGINSTRUCT));
        }
        return sourceLine;
    }
    /**
     * O método filterIdentation filtra toda a identação dos modulos systemverilog
     * começo, meio ou  fim.
     * @param sourceLine linha de código que será retirada a identação
     * @return retorna a linha de código sem identação
     */
    protected String filterIndentation(String sourceLine){
        int i=0;
        final char TAB = 9;
        final String INDENTATIONMAKEWITHSPACES = "  ";
        sourceLine = sourceLine.replace(INDENTATIONMAKEWITHSPACES, "");
        sourceLine = sourceLine.replace(TAB, ' ');
        for(;i < sourceLine.length(); i++){
            char teste = sourceLine.charAt(i);
            if(sourceLine.charAt(i) == ' ' || sourceLine.charAt(i) == 9) {
                continue;
            }else break;
        }
        return sourceLine.substring(i);
    }
    /**
     * O método filterParameter filtra todo tipo de parâmtros na declaração dos
     * modulos systemverilo
     * @param sourceLine linha de código que será retirada os parâmetros
     * @return uma linha de código sem parâmetros
     */
    protected String filterParameter(String sourceLine){
        final String INITIALPARAM = "#";
        final String OTHERINITIALPARAM = "(";
        if(sourceLine.contains(INITIALPARAM))
            sourceLine = sourceLine.substring(0,sourceLine.indexOf(INITIALPARAM));
        else 
            sourceLine = sourceLine.substring(0, sourceLine.indexOf(OTHERINITIALPARAM));
        return sourceLine;
    }
    /**
     * O método filtration junta todos os filtros em um só ligar para facilar
     * no processamento do código que será extraido, dependendo de makeFilterParam
     * a filtragem filtra ou não os parâmetros
     * @param sourceLine linha de código que será filtrada
     * @param makeFilterParam se true é feito a filtragem dos parâmetros se
     * false não será feito
     * @return a String filtrada
     */
    protected String filtration(String sourceLine, boolean makeFilterParam){
        sourceLine = this.filterAccessMode(sourceLine);
        sourceLine = this.filterIndentation(sourceLine);
        if(makeFilterParam)
            sourceLine = this.filterParameter(sourceLine);
        return sourceLine;
    }
}