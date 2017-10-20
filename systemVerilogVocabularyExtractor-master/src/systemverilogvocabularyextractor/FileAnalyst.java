/**
 * A classe FileAnalyst encapsula os dados e os métodos necessarios para 
 * receber pacotes ler os arquivos do pacote apartir daí chama as entidades
 * para começar a retirada e a modelagem dos dados no arquivos do projeto.
 */
package systemverilogvocabularyextractor;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author fc.corporation
 */
public class FileAnalyst {
    SearchFiles searchFiles;
    
    /**
     * O construtor da classe recebe um argumento e inicializa o campo searchFiles
 passando o diretorio do projeto
     * @param diretorio diretorio do projeto
     */
    public FileAnalyst(String diretorio){
        searchFiles = new SearchFiles(diretorio); 
        
    }
    /**
     * O método toStringFiles chama o método private toStringFiles passando a lista de
     * pacotes daí então lê o arquivo linha por linha e chama as entidades que analisam
     * essas linhas modelando esses dados.
     */
    public ArrayList<String> toStringFiles(){
        return this.toStringFiles(searchFiles.getbufferFiles());
    }
    //possivelmente a função toStringFiles poderá retornar Strings com o conteúdo
    //dos arquivos ou não( analisar com calma isso mais tarde) DATTEBAYO!!!!!
    private ArrayList<String> toStringFiles(ArrayList<BufferedReader> filesInBuffer){
        String strFile;
        ArrayList<String> lineInFile = new ArrayList<String>();
        int i=0;
        for(BufferedReader fileInPackage: filesInBuffer){
            try {
                while(fileInPackage.ready()){
                    lineInFile.add(fileInPackage.readLine());
                    i++;
                }
                fileInPackage.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        return lineInFile;
    }
}