/**
 * A classe Package emcapsula os dados e os métodos necessarios
 * para buscar todos os arquivos *.sv/*.svh pertencentes ao pacote 
 * abri-los e salva-los em um ArrayList<>.
 */
package systemverilogvocabularyextractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author fc.corporation
 */
public class PackageProcessor extends AbstractModuleLanguage{
    private CommentProcessor genericsCommentProcessor;
    private ArrayList<PackageData> arrayPackage; 
    private ArrayList<String> nameInPackage;
    private final static String BEGINPACKAGE = "package";
    private final static String ENDPACKAGE = "endpackage";
    private VerificationSintax verificationSimtax;
    private int size;
    /**
     * O construtor da classe não recebe nemhum argumento e inicializa todos os 
     * campos da classe
     */
    public PackageProcessor( ){
        super(PackageProcessor.BEGINPACKAGE, PackageProcessor.ENDPACKAGE);
        this.genericsCommentProcessor = new CommentProcessor();
        arrayPackage = new ArrayList<>();
        this.nameInPackage = new ArrayList<>();
        this.verificationSimtax = new VerificationSintax();
        verificationSimtax.setAvlTreeSintax(verificationSimtax.setWordsKeys());
    }
    @Override
    void setFields(String sourceLine) {
        final char SPACE = ' ';
        PackageData packageTemp;
        String name;
        if(this.isModule(sourceLine)){
            name = sourceLine.substring(sourceLine.indexOf(SPACE));
            packageTemp = new PackageData(name);
            this.arrayPackage.add(packageTemp);
            this.size++;
        }
        this.setVariableAndCommentlocal(sourceLine);
    }
    @Override
    void setVariableAndCommentlocal(String sourceLine) {
        if(this.beginStruct && !this.endStruct){
            PackageData tempPackage = this.getUltimatePackageData();
            this.nameFilesInPackage(sourceLine);
            if(this.genericsCommentProcessor.isCommentBlock(sourceLine)){
                this.genericsCommentProcessor.setComments(sourceLine);
            }
            else{
                tempPackage.setMethodProcessorPackageData(sourceLine);
                tempPackage.setTaskProcessorPackageData(sourceLine);
               if(!tempPackage.getMethodProcessorPackageData().isModule() &&
                    !tempPackage.getTaskProcessorPackageData().isModule()){
                    this.getUltimatePackageData().setFieldProcessorPackageData(sourceLine);
                }
            }
            this.genericsCommentProcessor = this.assginGenericsComments(genericsCommentProcessor, tempPackage, sourceLine);
        }
        else if(this.endStruct){
            this.getUltimatePackageData().setFilesInPackage(this.filtroAspas(nameInPackage));
            this.nameInPackage = new ArrayList<>();
            this.beginStruct = false;
            this.endStruct = false;
        }
    }
    public CommentProcessor assginGenericsComments(CommentProcessor genericComments, 
            PackageData tempPackageData, String sourceLine){
        CommentProcessor retornsComment = genericComments;
        if(tempPackageData.getMethodProcessorPackageData().isModule(sourceLine)){
            tempPackageData.getMethodProcessorPackageData().getUltimateMethod().setCommentLocal(genericComments);
            retornsComment = this.resetCommentsConfig(genericComments, false);
        }
        else if(tempPackageData.getTaskProcessorPackageData().isModule(sourceLine)){
            tempPackageData.getTaskProcessorPackageData().getUltimateTaskData().setCommentLocal(genericComments);
            retornsComment = this.resetCommentsConfig(genericComments, false);
        }
        return retornsComment;
    }
    public CommentProcessor resetCommentsConfig(CommentProcessor genericComments, boolean state){
        genericComments.setBeginComments(state);
        genericComments.setEndComments(state);
        return new CommentProcessor();
    }
    /**
     * O método nameFilesInPackage faz uma filtragem das palavras reservadas de
     * Systemverilog e de UVM retornando um ArrayList<String> só com os nomes dos
     * arquivos.
     * @param linhasDoArquivo array com todas as linhas do arquivo pkg.sv/svh.
     * @return um array com os nomes dos arquivos.
     */
    private void nameFilesInPackage(String sourceLine){
        String[] sufixName = {".sv\"", ".svh\""};
        //ArrayList<String> nomes = new ArrayList<String>();
            String[] listWords = sourceLine.split(" ");
            for(int j=0;j < listWords.length;j++){
                try {
                    if(verificationSimtax.sytemVerilogSintax(listWords[j]) == false && verificationSimtax.UVMsintax(listWords[j]) == false){
                        if(listWords[j].endsWith(sufixName[0]) || listWords[j].endsWith(sufixName[1]))
                            this.nameInPackage.add(listWords[j]);
                    }
                }catch(StringIndexOutOfBoundsException ioe){
                    continue;
                }
        }
    }
    /**
     * O método filtroAspas recebe um array contendo o nome dos arquivos, no entanto
     * os nomes ainda possuem aspas daí o filtro de aspas retirar-ló-ei-las 
     * @param nomesDosArquivos array com todos os nomes dos arquivos
     * @return um array com os nomes dos arquivos sem aspas
     */
    private ArrayList<String> filtroAspas(ArrayList<String> nomesDosArquivos){
        ArrayList<String> wordsFiltrade = new ArrayList<String>();
        for(int i=0;i < nomesDosArquivos.size();i++){
            String strTemp = nomesDosArquivos.get(i).replace("\"", "");
            wordsFiltrade.add(i, strTemp);
        }
        return wordsFiltrade;
    }
    public PackageData getUltimatePackageData(){
        return this.arrayPackage.get(size-1);
    }
    public String toString(){
        String pacote = "";
        for(PackageData pack: this.arrayPackage){
            pacote += pack;
        }
        return pacote;
    }
    public String toXML(){
        final String IDENTATION = "    ";
        String toXML = "";
        for(PackageData pkg: arrayPackage){
            toXML += pkg.toXML();
        }
        return toXML;
    }
}