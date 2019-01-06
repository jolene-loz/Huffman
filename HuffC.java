import edu.princeton.cs.algs4.BinaryOut;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
public class HuffC
{
  public static final int MAGIC_NUMBER = 3008;
  public static final boolean DEBUG = false;
  private final String[] args;
  
  public HuffC(String[] paramArrayOfString)
  {
    args = paramArrayOfString;
  }
  private void go()
  {
    FileIOC localFileIOC = new FileIOC();
    FileReader localFileReader = localFileIOC.openInputFile(args[0]);
    SymTable localSymbolTableC = new SymTableC(localFileReader);
    HuffTree localHuffmanTreeC = new HuffTreeC(localSymbolTableC);
    localHuffmanTreeC.computeBitCodes(localSymbolTableC, new BitsC());
    localFileReader = localFileIOC.openInputFile(args[0]);
    BinaryOut localBinaryOut = localFileIOC.openBinaryOutputFile();
    localBinaryOut.write(3008, 16);
    localSymbolTableC.writeFrequencyTable(localBinaryOut);
    int i = 0;
    try {
      while (i != -1) {
        i = localFileReader.read();
        
        if (i != -1) {
          Integer localInteger = new Integer(i);
          STValue localSTValue = localSymbolTableC.get(localInteger);
          Bits localBits = localSTValue.getBits();
          localBits.write(localBinaryOut);
        }
      }
      localFileReader.close();
      localBinaryOut.flush();
      localBinaryOut.close();
    }
    catch (IOException localIOException) {
      System.out.format("go: hit with this IOException\n", new Object[0]);
    }
  }
  
  public static void main(String[] paramArrayOfString) {
    new Huff(paramArrayOfString).go();
  }
}