/**
 * A classe Comentario emcapsula os dados e os métodos necessarios para a partir
 * da análise de uma linha conseguir retirar comentários tanto de bloco como de linha
 */
package systemverilogvocabularyextractor;

/**
 *
 * @author fc.corporation
 */
public class CommentProcessor {
    private String commentBlock;
    private String commentLine;
    private boolean beginComments;
    private boolean endsComments;
    static private final String BEGINCOMMENTSBLOCK = "/*";
    static private final String ENDSCOMMENTSBLOCK = "*/";
    static private final String LINECOMMENT = "//";
    
    /**
     * O construtor da classe não recebe argumentos, mas, inicializa os campos 
     * commentBlock e commentLine.
     */
    public CommentProcessor(){
        this.commentBlock = "";
        this.commentLine = "";
    }
    public void setBeginComments(boolean beginComments){
        this.beginComments = beginComments;
    }
    public void setEndComments(boolean endComments){
        this.endsComments = endComments;
    }
    /**
     * O método setComments recebe um argumento analisando se é um comentário de
     * bloco ou comentário de linha
     * @param linha linha que será analisada
     */
    public void setComments(String linha){
        this.setCommentBlock(linha);
        if(this.beginComments == false && this.endsComments == false)
            this.setCommentLine(linha);
    }
    /**
     * O método setCommentBlock verifica se a linha comeca com o símbolo de comentário
     * de bloco daí ele concatena todos as linhas seguidas até a linha que contiver
     * o símbolo de final de comentário de bloco
     * @param linha trecho do código
     */
    private void setCommentBlock(String linha){
        this.isCommentBlock(linha);
        if(this.beginComments && !this.endsComments){
            this.commentBlock += linha.replace(this.BEGINCOMMENTSBLOCK, "");
        }
        else if(this.endsComments){
            this.beginComments = false;
            this.endsComments = false;
            this.commentBlock += linha.replace(this.ENDSCOMMENTSBLOCK, "");
        }
    }
    /**
     * O método isCommentBlock verifica se no código iniciou um comentário de bloco
     * ou se o comentário terminou
     * @param linha trecho do código a ser analisado
     * @return 
     */
    public boolean isCommentBlock(String linha){
        if(linha.contains(BEGINCOMMENTSBLOCK)){
            this.beginComments = true;
            this.endsComments = false;
        }
        else if(linha.endsWith(ENDSCOMMENTSBLOCK)){
            this.endsComments = true;
            this.beginComments = false;
        }
        return this.beginComments;
    }
    /**
     * O método setCommentLine averigua se a linha de código é um comentário de 
     * linha
     * @param linha trecho de código que será análisado
     */
    private void setCommentLine(String linha){
        if(linha.contains(this.LINECOMMENT)){
            this.commentLine += linha.substring(linha.indexOf(LINECOMMENT));
        }
    }
    public boolean getBeginComments(){
        return this.beginComments;
    }
    public boolean getEndCommens(){
        return this.endsComments;
    }
    public String toString(){
        return this.commentBlock+"\n"+commentLine+"\n";
    }
    public String toXML(String identation){
        String toXML = identation+"<comments comm=\""+this.commentBlock+this.commentLine+"\"/>\n";
        return toXML;
    }
}