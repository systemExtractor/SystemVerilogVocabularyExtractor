/**
 * A classe TaskProcessor extrai tudo de relativo à uma task systemverilog.
 * e encapsula tudo isso em um arrayList<TaskData>
 */
package systemverilogvocabularyextractor;

import java.util.ArrayList;

/**
 *
 * @author fc.corporation
 */
public class TaskProcessor extends AbstractModuleLanguage{
    private ArrayList<TaskData> arrayTaskData;
    private int size;
    private String delay;
    
    /**
     * O construtor da classe não recebe nemhum argumento e inicializa o campo
     * arrayTaskData
     */
    public TaskProcessor() {
        super("task", "endtask");
        arrayTaskData = new ArrayList<TaskData>();
    }
    /**
     * O método setFields seta o nome da task junto com seus parâmetros
     * daí chama o método setVariableAndCommentlocal onde é extraido tudo
     * de importante do interior da task
     * @param sourceLine linha de código à ser processada 
     */
    @Override
    void setFields(String sourceLine) {
        final String SPACE = " ";
        final String PARENTESES = "(";
        String[] listWord;
        TaskData taskdata = null;
        String linha = this.filterIndentation(sourceLine);
        linha = this.filterAccessMode(linha);
        if(isModule(linha)){
            linha = linha.substring(0, linha.indexOf(PARENTESES));
            listWord = linha.split(SPACE);
            if(listWord.length == 2){
                taskdata = new TaskData(listWord[listWord.length-1]);
                taskdata.setParam(sourceLine);
                this.arrayTaskData.add(taskdata);
            }
            this.size++;
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    /**
     * O método verifica se não foi encontrada um (endtask) daí
     * é chamada os métodos setLocalField de TaskData, o método setCommentLocal
     * @param souceLine linha de código que será processada
     */
    @Override
    public void setVariableAndCommentlocal(String souceLine) {
        if(beginStruct && !endStruct){
            this.arrayTaskData.get(arrayTaskData.size()-1).setLocalField(souceLine);
            this.arrayTaskData.get(arrayTaskData.size()-1).setCommentLocal(souceLine);
        }
        else if(endStruct){
            beginStruct = false;
            endStruct = false;
        } 
    }
    /**
     * O método isModule verifica se não foi encontrado um endtask
     * retornando true, ou false caso tenha sido encontrada um endtask
     * @return retorna um boolean que será true se não tiver sido encontrado 
     * um endtask, caso tenha sido encontrado retorna false
     */
    public boolean isModule(){
        return this.beginStruct;
    }
    public TaskData getUltimateTaskData(){
        return this.arrayTaskData.get(this.size-1);
    }
    public String toString(){
        String task = "";
        for(TaskData tkda: this.arrayTaskData){
            task += tkda;
        }
        return task;
    }
    public String toXML(String identation){
        String toXML = "";
        for(TaskData tasks: this.arrayTaskData){
            toXML += tasks.toXML(identation);
        }
        return toXML;
    }
}