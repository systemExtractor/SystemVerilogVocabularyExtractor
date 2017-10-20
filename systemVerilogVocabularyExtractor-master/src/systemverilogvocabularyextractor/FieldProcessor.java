/**
 * A classe AnalystVariable emcapsula os dados e os métodos necessarios
 * para a partir da linha recebida analísa se há ou não uma declaração de variável
 * não sendo necessário averiguar sua utilidade no código.
 */
package systemverilogvocabularyextractor;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 *
 * @author fc.corporation
 */
public class FieldProcessor {
    ArrayList<FieldData> listVariaveis;
    VerificationSintax vfs;
    
    /**
     * O construtor da classe que inicializa os campos listVariaveis e vfs
     * também chama o método e vfs que é setAvlTreeSintax para inicializar a árvore
     * com a sintaxe Systemverilog.
     */
    public FieldProcessor(){
        listVariaveis = new ArrayList<FieldData>();
        vfs = new VerificationSintax();
        vfs.setAvlTreeSintax(vfs.setWordsKeys());
    }
    /**
     * O método setListVariaveis faz toda a filtragem em cima da linha, ela sendo
     * uma variável, daí então chega se há mais de um tipo primitivo ele concatena-os
     * e após isso ele monta a lista de Variaveis.
     * @param sourceLine linha do arquivo que será analisada
     */
    public void setListVariaveis(String sourceLine){
        sourceLine = this.filtroValores(sourceLine);
        sourceLine = this.filterComments(sourceLine);
        ArrayList<String> wordsFiltrade = new ArrayList<>();
        if(this.isVariable(sourceLine) || this.isTypeWithVector(sourceLine)){
            try{
                wordsFiltrade = this.getAllTypesVariable(sourceLine);
            }catch(java.lang.StringIndexOutOfBoundsException sioe){}
            for(int i=1;i < wordsFiltrade.size();i++){
                if(wordsFiltrade.get(0) != ""){
                    try{
                        String tiposConcatenados = wordsFiltrade.get(0);
                        while(vfs.sytemVerilogSintax(wordsFiltrade.get(i)) == true){
                            tiposConcatenados += " "+wordsFiltrade.get(i);
                            i++;
                        }
                        if(wordsFiltrade.get(i).contains("[")){
                            tiposConcatenados +=" "+wordsFiltrade.get(i);
                            i+=1;
                        }
                        FieldData tempVar = new FieldData(tiposConcatenados, wordsFiltrade.get(i));
                        this.listVariaveis.add(tempVar);
                    }catch(IndexOutOfBoundsException ioe){
                        break;
                    }
                }
            }
        }
    }
    /**
     * O método filtragem executa todos os sub-filtros de forma organizada
     * filtrando assim tudo de desnecessario da linha retornando um array
     * @param sourceLine linha que será filtrada
     * @return um array só com tipos e nomes de variáveis
     */
    private ArrayList<String> filtragem(String sourceLine){
        sourceLine = this.filterIdentation(sourceLine);
        String filtradeWord = this.filtroPontoVirgula(this.filtroValores(sourceLine));
        ArrayList<String> listFiltradeWord = this.filtroIdentacao(filtradeWord);
        return this.filterComman(listFiltradeWord);
    }
    /**
     * O método filterComman  faz o slice na vírgula ou retira a vírgula existente no 
     * array que este método recebe retornando um novo array onde não há virgulas.
     * @param listFiltradeWord lista de nomes onde já foram executaos alguns filtros.
     * @return um array onde não há mais virgulas entre as palavras, palavras essas que
     * agora estao divididas em elementos diferentes.
     */
    private ArrayList<String> filterComman(ArrayList<String> listFiltradeWord){
        ArrayList<String> copyListFiltradeWord = new ArrayList<String>();
        final char COMMAN = ',';
        final String STRCOMMAN = ",";
        for(String str: listFiltradeWord){
            if(str.equals(STRCOMMAN))
                continue;
            if(str.contains(STRCOMMAN)){
                String[] listSrtTemp = str.split(STRCOMMAN);
                for(String s: listSrtTemp){
                    copyListFiltradeWord.add(s);
                }
            }
            else copyListFiltradeWord.add(str);
        }
        return copyListFiltradeWord;
    }
    /**
     * O método filtroIdentacao recebe um argumento e retira toda a identação e
     * também todos os espaços desnecessarios
     * @param sourceLine linha que será retirado a identação
     * @return um array com nomes sem espaços
     */
    private ArrayList<String> filtroIdentacao(String sourceLine){
        int i=0;
        final char TAB = 9;
        final char SPACE = 32;
        final String ident = "  ";
        sourceLine = sourceLine.trim();
        sourceLine = sourceLine.replace(TAB, SPACE);
        sourceLine = sourceLine.replace(ident, "");
        String[] listStrline = sourceLine.split(" ");
        ArrayList<String> withoutIndentation = new ArrayList<String>();
        for(String str: listStrline){
            if(!str.equals("")){
                withoutIndentation.add(str);
            }
        }
        return withoutIndentation;
    }
    /**
     * O método filterIdentation recebe uma argumento e retira toda a identação
     * que linha que lhe é passada como parâmetro não importa se é no começo 
     * meio ou fim
     * @param sourceLine linha de código que será retirada a identação
     * @return uma String sem idemtação
     */
    private String filterIdentation(String sourceLine){
        final String IDENTATION = "  ";
        return sourceLine.replace(IDENTATION, "");
    }
    /**
     * O método filtroPontoVirgula retira o ";" do final de cada linha
     * passada como parâmetro 
     * @param SourceLine linha que será retirao o ";"
     * @return uma String onde não há mais ";"
     */
    private String filtroPontoVirgula(String SourceLine){
        return SourceLine.replace(";", "");
    }
    /**
     * O método filtroValores filtra os valores que ha após o símbolo de "="
     * em todas as variaveis da linha
     * @param sourceLine linha que será retirada os valores
     * @return string só com o tipo e os nomes das variáveis
     */
    private String filtroValores(String sourceLine){
        String withoutValues = sourceLine;
        final String ILLEGALSTRING = "=";
        final char ILLEGALWORDCHAR = '=';
        final char COMMAN = ',';
        if(sourceLine.contains(ILLEGALSTRING)){
            withoutValues = "";
            for(int i=0;i < sourceLine.length();i++){
                if(sourceLine.charAt(i) == ILLEGALWORDCHAR){
                    while(sourceLine.charAt(i) != COMMAN){
                        if(sourceLine.charAt(i) == ';' || i+1 == sourceLine.length())
                            break;
                        i++;
                    }
                }
                withoutValues += sourceLine.charAt(i);
            }
            withoutValues = withoutValues.replace(" ,", ",");
            withoutValues = withoutValues.replace(" ;", ";");
        }
       return withoutValues; 
    }
    /**
     * O método isVariable verifica se a linha passada como parâmetro pode ter ou
     * não uma variável baseada numa lista de símbolos que caracterizam a ausência
     * de variável.
     * @param sourceLine linha que será averiguada se há ou não variável
     * @return true se há uma variável do contrário retorna false
     */
    private boolean isVariable(String sourceLine){
        boolean state = true;
        String[] isNotVariable = {"class","#","return","(", "{",">",
            ":", "}", ")", "<", "`include", "package", "function", "task", "interface",
            "modport"};
        if(sourceLine.equals(" ")){
            return false;
        }
        for(String i: isNotVariable){
            if(sourceLine.contains(i)){
                state = false;
                return state;
            }
        }
        return state;
    }
    /**
     * O método filterValuesReturnedFunctions filtra as variáveis que estão 
     * recebendo algum valor do retorno de funções/métodos.
     * @param sourceLine linha que será filtrada
     * @return uma String só com o tipo e o nome da variável
     */
    private String filterValuesReturnedFunctions(String sourceLine){
        final String EQUALS = "=";
        final String PARENTHESES = "(";
        String withoutValuesReturnedOfFunctions = sourceLine;
        if(sourceLine.contains(EQUALS) && sourceLine.contains(PARENTHESES)){
            withoutValuesReturnedOfFunctions = sourceLine.substring(0, sourceLine.indexOf(EQUALS)-1)+";";
        }
        return withoutValuesReturnedOfFunctions;
    }
    /**
     * O método filterComments recebe um argumento que é uma linha de código
     * systemverilog da ele filtra comentarios de linha que podem estar logo
     * após o final da linha código.
     * @param sourceLine linha de código que será processada
     * @return uma String que é somente código systemverilog sem comentários de linha 
     * presentes nela.
     */
    private String filterComments(String sourceLine){
        final String ILLEGALCHARSEQUENCE = "//";
        if(sourceLine.contains(ILLEGALCHARSEQUENCE))
            sourceLine = sourceLine.substring(0,sourceLine.indexOf(ILLEGALCHARSEQUENCE));
        return sourceLine;
    }
    /**
     * O método getTypeWithVector recebe um argumento que é uma linha de código
     * daí ele retorna o tipo com sua declaração de vetor junto.
     * @param sourceLine linha que será analisada
     * @return uma String que é o tipo com sua declaração de vetor
     */
    public String getTypeWithVector(String sourceLine){
        sourceLine = this.filterIdentation(sourceLine);
        String typeWithVector = sourceLine.substring(0, sourceLine.indexOf("]"));
        String nome = sourceLine.substring(sourceLine.indexOf("]"));
        
        return typeWithVector;
    }
    /**
     * O método isTypeWithVector verifica se a linha passada como parâmetro 
     * é um tipo com uma declaração de vetor junta a ele.
     * @param sourceLine linha de código que será analisada
     * @return um boolean true se o tipo for um array ou false se não for.
     */
    private boolean isTypeWithVector(String sourceLine){
        boolean state = false;
        sourceLine = this.filterIdentation(sourceLine);
        final String ILLEGALCHAR = "=";
        final String[] VECTOR = {"[", "]"};
        if(sourceLine.contains(VECTOR[0]) && sourceLine.contains(VECTOR[1])){
            String subLine = sourceLine.substring(sourceLine.indexOf(VECTOR[1]));
            if(!subLine.startsWith("] ") || !subLine.startsWith("]")){
                return false;
            }
            else return true;
        }
        return state;
    }
    public ArrayList<String> getAllTypesVariable (String sourceLine) 
            throws java.lang.StringIndexOutOfBoundsException{
        sourceLine = sourceLine.trim();
        String type = "";
        final String COMMAN = ",";
        final String FINALSOURCELINE = ";";
        if(sourceLine.contains(COMMAN)){
            String partialString = sourceLine.substring(0, sourceLine.indexOf(COMMAN));
            type = partialString.substring(0, partialString.lastIndexOf(" "));
        }
        else if(sourceLine.contains(FINALSOURCELINE)){
            String partialString = sourceLine.substring(0, sourceLine.indexOf(FINALSOURCELINE));
            type = partialString.substring(0, partialString.lastIndexOf(" "));
        }
        type = type.trim();
        ArrayList<String> result = this.filtragem(sourceLine.substring(type.length()));
        result.add(0, type);
        return result;
    }
    /**
     * O método toString retorna a String formatada de acordo com a necessidade
     * atual
     * @return uma String formata.
     */
    public String toString(){
        String AnalystVariable = "";
        for(FieldData var: listVariaveis){
            AnalystVariable += var;
        }
        return AnalystVariable;
    }
    public String toXML(String identation){
        String toXml = "";
        for(FieldData fields: listVariaveis){
            toXml = fields.toXml(identation);
        }
        return toXml;
    }
}