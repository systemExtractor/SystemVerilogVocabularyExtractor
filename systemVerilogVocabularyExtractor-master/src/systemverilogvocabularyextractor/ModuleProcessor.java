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
public class ModuleProcessor extends AbstractModuleLanguage{
    private ArrayList<ModuleData> arrayModuleData;
    private CommentProcessor commentsFunction;
    private int size;
    private static final String BEGINMODULE = "module";
    private static final String ENDMODULE = "endmodule";

    public ModuleProcessor() {
        super(ModuleProcessor.BEGINMODULE, ModuleProcessor.ENDMODULE);
        this.arrayModuleData = new ArrayList<ModuleData>();
        this.commentsFunction = new CommentProcessor();
    }
    public void setModuleProperties(String sourceLine){
        if(this.isModule(sourceLine))
            this.setFields(sourceLine);
        this.setVariableAndCommentlocal(sourceLine);
    }
    @Override
    void setFields(String sourceLine) {
        sourceLine = this.filtration(sourceLine, false);
        String nameModule = sourceLine.substring(sourceLine.indexOf(" "));
        ModuleData tempModuleData = new ModuleData(nameModule);
        this.arrayModuleData.add(tempModuleData);
        this.size+=1;
    }
    @Override
    void setVariableAndCommentlocal(String sourceLine) {
        if(this.beginStruct && !this.endStruct){
            ModuleData referenceModule = this.arrayModuleData.get(this.size-1);
            if(this.commentsFunction.isCommentBlock(sourceLine))
                this.commentsFunction.setComments(sourceLine);
            else{
                referenceModule.setMethodProcessorModuleData(sourceLine);
                if(!referenceModule.getMethodProcessorModuleData().isModule())
                    referenceModule.setFieldProcessorModuleData(sourceLine);
                if(referenceModule.getMethodProcessorModuleData().isModule(sourceLine)){
                    this.commentsFunction.setBeginComments(false);
                    this.commentsFunction.setEndComments(false);
                    referenceModule.getMethodProcessorModuleData().getUltimateMethod().setCommentLocal(this.commentsFunction);
                    this.commentsFunction = new CommentProcessor();
                }
            }
        }
    }
    public void setModuleComments(CommentProcessor comments){
        this.arrayModuleData.get(size-1).setCommentProcessorModuleData(comments);
    }
    @Override
    public String toString(){
        String modules = "";
        for(ModuleData mdl: arrayModuleData){
            modules += mdl;
        }
        return modules;
    }
    public String toXML() {
        String toXML = "";
        for(ModuleData m: this.arrayModuleData){
            toXML += m.toXML();
        }
        return toXML;
    }
}