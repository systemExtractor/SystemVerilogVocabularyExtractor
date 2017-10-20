/**
 * A classe InterfaceProcessor encapsula os campos e os método necessarios para
 * a modelagem de um processador de interface extendendo da classe abstrata Modulo.
 */
package systemverilogvocabularyextractor;
import java.util.ArrayList;
/**
 *
 * @author fc.corporation
 */
public class InterfaceProcessor extends AbstractModuleLanguage{
    private ArrayList<InterfaceData> arrayInterface;
    private CommentProcessor genericCommentProcessorInterface;
    private static final String BEGIMINTERFACE = "interface";
    private static final String ENDINTERFACE = "endinterface";
    private int size;
    
    /**
     * O construtor da classe que não recebe nemhum argumento e instacia 
     * seus objetos.
     */
    public InterfaceProcessor(){
        super(InterfaceProcessor.BEGIMINTERFACE, InterfaceProcessor.ENDINTERFACE);
        this.arrayInterface = new ArrayList<InterfaceData>();
        this.genericCommentProcessorInterface = new CommentProcessor();
    }   
    /**
     * O método
     * @param sourceLine 
     */
    @Override
    void setFields(String sourceLine) {
        InterfaceData tempInterfece;
        if(this.isModule(sourceLine)){
            String[] wordsInline = sourceLine.split(" ");
            tempInterfece = new InterfaceData(wordsInline[1]);
            this.arrayInterface.add(tempInterfece);
            this.size++;
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    /**
     * 
     * @param sourceLine 
     */
    @Override
    void setVariableAndCommentlocal(String sourceLine) {
        if(beginStruct && !endStruct){
            InterfaceData interfaceTemp = this.arrayInterface.get(size-1);
            if(this.genericCommentProcessorInterface.isCommentBlock(sourceLine))
                this.genericCommentProcessorInterface.setComments(sourceLine);
            else{
                interfaceTemp.setTaskProcessorInterface(sourceLine);
                interfaceTemp.setModPortProcessorInterface(sourceLine);
                interfaceTemp.setMethodProcessorInterface(sourceLine);
                if(!interfaceTemp.getMethodProcessorInterface().isModule() && 
                        !interfaceTemp.getTaskProcessorInterface().isModule() &&
                        !interfaceTemp.getModPortProcessorInterface().isModule())
                    interfaceTemp.setFieldProcessorInterface(sourceLine);
                this.genericCommentProcessorInterface = this.assginGenericsComments(
                        this.genericCommentProcessorInterface, interfaceTemp, sourceLine);
            }
        }
        else if(endStruct){
            beginStruct = false;
            endStruct = false;
        }
    }
    public CommentProcessor assginGenericsComments(CommentProcessor genericComments, 
            InterfaceData tempInterfaceData, String sourceLine){
        CommentProcessor retornsComment = genericComments;
        if(tempInterfaceData.getMethodProcessorInterface().isModule(sourceLine)){
            tempInterfaceData.getMethodProcessorInterface().getUltimateMethod().setCommentLocal(genericComments);
            retornsComment = this.resetCommentsConfig(genericComments, false);
        }
        else if(tempInterfaceData.getTaskProcessorInterface().isModule(sourceLine)){
            tempInterfaceData.getTaskProcessorInterface().getUltimateTaskData().setCommentLocal(genericComments);
            retornsComment = this.resetCommentsConfig(genericComments, false);
        }
        else if(tempInterfaceData.getModPortProcessorInterface().isModule(sourceLine)){
            tempInterfaceData.getModPortProcessorInterface().getUltimateModPort().setCommentsProcesorModPort(genericComments);
            retornsComment = this.resetCommentsConfig(genericComments, false);
        }
        return retornsComment;
    }
    public CommentProcessor resetCommentsConfig(CommentProcessor genericComments, boolean state){
        genericComments.setBeginComments(state);
        genericComments.setEndComments(state);
        return new CommentProcessor();
        
    }
    public String toString(){
        String interfaceProcesor = "";
        for(InterfaceData interfaceData: this.arrayInterface){
            interfaceProcesor += interfaceData;
        }
        return interfaceProcesor;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "";
        for(InterfaceData intf: this.arrayInterface){
            toXML += intf.toXML();
        }
        return toXML;
    }
}