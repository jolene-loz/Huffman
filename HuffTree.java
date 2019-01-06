//extends Comparable:  trees are compared by their integer weights.
public abstract interface HuffTree extends Comparable<HuffTree>
{
  public abstract int getWeight();
  public abstract char getSym();
  public abstract HuffTree getLeft();
  public abstract HuffTree getRight();
  public abstract boolean isLeaf(); 
  public abstract void computeBitCodes(SymTable paramSymbTable, Bits paramBits);
  public abstract String toString();
}