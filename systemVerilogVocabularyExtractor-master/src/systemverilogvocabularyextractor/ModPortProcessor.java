/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemverilogvocabularyextractor;
import java.util.ArrayList;
/**
 *
 * @author fc.corporation
 */
public class ModPortProcessor extends AbstractModuleLanguage{
    private ArrayList<ModPortData> arrayModPorts;
    private int size;
    private FieldProcessor fieldProcessorModPort;
    private static final String BEGIMMODPORT = "modport";
    private static final String ENDMODPORT = ");";

    public ModPortProcessor() {
        super(ModPortProcessor.BEGIMMODPORT, ModPortProcessor.ENDMODPORT);
        this.arrayModPorts = new ArrayList<ModPortData>();
        this.fieldProcessorModPort = new FieldProcessor();
    }
    /**
     * 
     * @param sourceLine 
     */
    @Override
    void setFields(String sourceLine) {
        sourceLine = this.filterIndentation(sourceLine);
        ModPortData TempModPort;
        if(this.isModule(sourceLine)){
            String name = sourceLine.substring(sourceLine.indexOf(" "), 
                    sourceLine.indexOf("("));
            TempModPort = new ModPortData(name);
            TempModPort.setFieldsModPort(this.getFieldsOnSameLine(sourceLine));
            this.arrayModPorts.add(TempModPort);
            this.size = arrayModPorts.size();
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    @Override
    void setVariableAndCommentlocal(String linha) {
        if(this.beginStruct){
            this.getDirectionsFields(linha);
        }
        else if(this.endStruct){
            this.arrayModPorts.get(size-1).setFieldsModPort(this.fieldProcessorModPort);
            this.fieldProcessorModPort = new FieldProcessor();
            this.endStruct = false;
        }
    }
    public void getDirectionsFields(String sourceLine){
        this.fieldProcessorModPort.setListVariaveis(sourceLine);
    }
    @Override
    public boolean isModule(String sourceLine){
        this.filterIndentation(sourceLine);
        boolean state = false;
        if(sourceLine.startsWith(BEGINSTRUCT)){
            state = true;
            beginStruct = true;
            endStruct = false;
        }
        else if(sourceLine.startsWith(ENDSTRUCT) && this.beginStruct == true){
            beginStruct = false;
            endStruct = true;
        }
        return state;
    }
    public ModPortData getUltimateModPort(){
        return this.arrayModPorts.get(size-1);
    }
    public FieldProcessor getFieldsOnSameLine(String sourceLine){
        FieldProcessor fieldsInSameLine = new FieldProcessor();
        final String[] LIMITES = {"(", ")"};
        String fieldsOnSameLine = "";
        if(sourceLine.contains(LIMITES[1])){
            fieldsOnSameLine = sourceLine.substring(sourceLine.indexOf(LIMITES[0])+1,
                    sourceLine.indexOf(LIMITES[1]))+";";
        }
        fieldsInSameLine.setListVariaveis(fieldsOnSameLine);
        return fieldsInSameLine;
    }
    @Override
    public String toString(){
        String modPortProcessor = "";
        for(ModPortData modPort: this.arrayModPorts){
            modPortProcessor += modPort;
        }
        return modPortProcessor;
    }
    public String toXML(String identation){
        final String IDENTATION = "    ";
        String toXML = "";
        for(ModPortData modport: arrayModPorts){
            toXML += modport.toXML(IDENTATION);
        }
        return toXML;
    }
}