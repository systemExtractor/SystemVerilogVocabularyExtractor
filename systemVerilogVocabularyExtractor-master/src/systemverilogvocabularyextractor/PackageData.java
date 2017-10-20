/**
 * A classe PackageData encapsula os dados e os métodos necessarios para a modelagem
 * de um package em systemverilog
 */
package systemverilogvocabularyextractor;
import java.util.ArrayList;
/**
 *
 * @author fc.corporation
 */
public class PackageData {
    private String namePackage;
    private ArrayList<String> filesInPackage;
    private MethodProcessor methodProcessorPackageData;
    private TaskProcessor taskProcessorPackageData;
    private FieldProcessor fieldProcessorPackageData;
    private CommentProcessor commentProcessorPackageData;
    
    public PackageData(String namePackage){
        this.namePackage = namePackage;
        this.filesInPackage = new ArrayList<>();
        this.methodProcessorPackageData = new MethodProcessor();
        this.taskProcessorPackageData = new TaskProcessor();
        this.fieldProcessorPackageData = new FieldProcessor();
        this.commentProcessorPackageData = new CommentProcessor();
    }

    public void setFilesInPackage(ArrayList<String> filesInPackage) {
        this.filesInPackage = filesInPackage;
    }

    public void setMethodProcessorPackageData(MethodProcessor methodProcessorPackageData) {
        this.methodProcessorPackageData = methodProcessorPackageData;
    }
    public void setMethodProcessorPackageData(String sourceLine){
        this.methodProcessorPackageData.setFields(sourceLine);
    }

    public void setTaskProcessorPackageData(TaskProcessor taskProcessorPackageData) {
        this.taskProcessorPackageData = taskProcessorPackageData;
    }
    public void setTaskProcessorPackageData(String sourceLine){
        this.taskProcessorPackageData.setFields(sourceLine);
    }

    public void setFieldProcessorPackageData(FieldProcessor fieldProcessorPackageData) {
        this.fieldProcessorPackageData = fieldProcessorPackageData;
    }
    public void setFieldProcessorPackageData(String sourceLine){
        this.fieldProcessorPackageData.setListVariaveis(sourceLine);
    }

    public void setCommentProcessorPackageData(CommentProcessor commentProcessorPackageData) {
        this.commentProcessorPackageData = commentProcessorPackageData;
    }
    public void setCommentProcessorPackageData(String sourceLine){
        this.commentProcessorPackageData.setComments(sourceLine);
    }
    
    public MethodProcessor getMethodProcessorPackageData() {
        return methodProcessorPackageData;
    }

    public TaskProcessor getTaskProcessorPackageData() {
        return taskProcessorPackageData;
    }
    
    public String toString(){
        String packages = "-----------nome do pacote-----------\n";
        packages += this.namePackage+"\n";
        packages += "------------fields---------------\n";
        packages += this.fieldProcessorPackageData;
        packages += "----------arquivos do pacote------------\n";
        for(String str: filesInPackage){
            packages += str+"\n";
        }
        packages += "----------functions-------------\n";
        packages += this.methodProcessorPackageData;
        packages += "----------tasks----------------\n";
        packages += this.taskProcessorPackageData;
        return packages;
    }
    public String filesInPackageToXML(){
        final String IDENTATION = "    ";
        String toXML = "<file fl=\"";
        for(String s:filesInPackage){
            toXML += s+"\n";
        }
        toXML += "\"/>";
        return toXML;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "<pkg name=\""+this.namePackage+"\">\n";
        toXML += this.commentProcessorPackageData.toXML(IDENTATION);
        toXML += this.fieldProcessorPackageData.toXML(IDENTATION);
        toXML += this.methodProcessorPackageData.toXMl(IDENTATION);
        toXML += this.taskProcessorPackageData.toXML(IDENTATION);
        toXML +=this.filesInPackageToXML();
        toXML += "</pkg>";
        return toXML;
    }
}