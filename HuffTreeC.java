import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.SortedSet;
@SuppressWarnings("unchecked")

public class HuffTreeC implements HuffTree
{
  private int weight;
  private char symbol;
  private HuffTree left;
  private HuffTree right;
  
  public int getWeight() {
    return weight; //gets the nodes integer weight 
  }
  public char getSym() {
    return symbol; 
  }
  
  //for interior nodes
  public HuffTree getLeft() {
    return left; 
  }
  public HuffTree getRight() {
    return right; 
  }
  
  public boolean isLeaf() { 
    return getLeft() == null; 
  }
  

  public HuffTreeC(char paramChar, int paramInt)
  {
    weight = paramInt;
    symbol = paramChar;
    left = null;
    right = null;
  }
  

  public HuffTreeC(int paramInt, HuffTree paramHuffTree1, HuffTree paramHuffTree2)
  {
    weight = paramInt;
    symbol = '\000';
    left = paramHuffTree1;
    right = paramHuffTree2;
  }
  



  public HuffTreeC(SymTable paramSymTable)
  {
    PriorityQueue localPriorityQueue = new PriorityQueue();

    Object localObject1;
    Object localObject2;

    SortedSet localSortedSet = paramSymTable.keySet();
    for (localObject1 = localSortedSet.iterator(); ((Iterator)localObject1).hasNext();) { 
      localObject2 = (Integer)((Iterator)localObject1).next();
      char c = (char)((Integer)localObject2).intValue();
      STValue localSTValue = paramSymTable.get((Integer)localObject2);
      int j = localSTValue.getFrequency(); //gets frequency of sym table vals
      HuffTreeC localHuffmanTreeC = new HuffTreeC(c, j);
      localPriorityQueue.add(localHuffmanTreeC); //HuffmanTree is inserted into priority queue
      
    }
    
    //if Huffman tree has the same weight as the other trees in the queue it should be inserted behind the others. otherwise follow the condition below.      
    while (localPriorityQueue.size() > 1) 
    {
      localObject1 = (HuffTree)localPriorityQueue.poll();
      localObject2 = (HuffTree)localPriorityQueue.poll();
      
      int i = ((HuffTree)localObject1).getWeight() + ((HuffTree)localObject2).getWeight();
      
      localPriorityQueue.add(new HuffTreeC(i, (HuffTree)localObject1, (HuffTree)localObject2));
    }
    
    localObject1 = (HuffTree)localPriorityQueue.poll();
    weight = ((HuffTree)localObject1).getWeight(); 
    symbol = ((HuffTree)localObject1).getSym();
    left = ((HuffTree)localObject1).getLeft();
    right = ((HuffTree)localObject1).getRight();
  }

  public void computeBitCodes(SymTable paramSymTable, Bits paramBits)
  {
    if (isLeaf()) {
      Integer localInteger = new Integer(getSym());
      STValue localSTValue = paramSymTable.get(localInteger);
      localSTValue.setBits(paramBits);

    }
    else
    {
      getLeft().computeBitCodes(paramSymTable, paramBits.addBit(0));
      getRight().computeBitCodes(paramSymTable, paramBits.addBit(1));
    }
  }
  
  //to help with debugging
  public String toString() {
    int i = getWeight();
    if (isLeaf()) {
      return String.format("%c:%d", new Object[] { Character.valueOf(getSym()), Integer.valueOf(i) });
    }
    
    String str1 = getLeft().toString();
    String str2 = getRight().toString();
    return String.format("HT(%d, %s, %s)", new Object[] { Integer.valueOf(i), str1, str2 });
  }
 

  public int compareTo(HuffTree paramHuffmanTree)
  {
    return getWeight() - paramHuffmanTree.getWeight();
  }
}