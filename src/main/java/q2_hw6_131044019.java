/**
 * Created by GozdeDogan on 19.04.2017.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by GozdeDogan on 18.04.2017.
 */
public class q2_hw6_131044019 {
    public static void main(String[] args){
        HuffmanTree.HuffData[] huffdata;
        int numOfLines = findNumOfLines("freq.txt");
        huffdata = new HuffmanTree.HuffData[numOfLines];
        readFile("freq.txt", huffdata);

       /* System.out.println("size:" + huffdata.length);
        for(int i=0; i<huffdata.length; i++){
            System.out.println("symbol:" + huffdata[i].getSymbol() + "\tweight:" + huffdata[i].getWeight());
        }*/


        HuffmanTree huffTree = new HuffmanTree();
        huffTree.buildTree(huffdata);

        /*System.out.println("huffman tree:");
        System.out.println("\n" + huffTree.toString() + "\n\n");*/

        System.out.println("gozde:" + huffTree.encode("gozde"));
        System.out.println("dogan:" + huffTree.encode("dogan"));
        System.out.println("131044019:" + huffTree.encode("131044019")+ "\n");

        System.out.println("data:" + huffTree.encode("data"));
        System.out.println("structures:" + huffTree.encode("structures"));
        System.out.println("and:" + huffTree.encode("and"));
        System.out.println("algorithms:" + huffTree.encode("algorithms"));
        System.out.println("cse222:" + huffTree.encode("cse222"));
    }

    /**
     * dosyadaki satir sayisini buluyor ona gore huffData arrayini olusturacak
     * @param fileName
     * @return
     */
    private static int findNumOfLines(String fileName){
        int numOfLines = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = new String();
            while((line = bufferedReader.readLine()) != null)
                numOfLines++;

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        return numOfLines;
    }

    /**
     * verilen dosyadan symbol ve weight degerlerini okuyup huffData arrayine kaydeder
     * @param fileName
     * @param huffdata
     */
    private static void readFile(String fileName, HuffmanTree.HuffData[] huffdata){
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = new String();
            int size=0;
            char symbol = '#';
            double weight = 0.0;

            while((line = bufferedReader.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line, " ");
                if(tok.hasMoreElements()){
                    symbol = ((String)tok.nextElement()).charAt(0);
                }

                if(tok.hasMoreElements()){
                    weight = Double.parseDouble((String) tok.nextElement());
                }

                if(symbol != '#' && weight != 0.0)
                    huffdata[size++] = new HuffmanTree.HuffData(weight, symbol);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}

