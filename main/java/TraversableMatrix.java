import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements adapter/wrapper/decorator design pattern
 */
public class TraversableMatrix implements Traversable<Index> {
    protected final Matrix matrix;
    protected Index startIndex;
    protected List<Index> banList = new ArrayList<>();

    public TraversableMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Index getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Index startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Node<Index> getOrigin() throws NullPointerException{
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new Node<>(this.startIndex);

    }

    @Override
    public int getValue(Index index) {
        return this.matrix.getValue(index);
    }

    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> someNode,boolean includeDiagonal) {
        List<Node<Index>> reachableIndex = new ArrayList<>();
        for (Index index : this.matrix.getNeighbors(someNode.getData(),includeDiagonal)) {
            if (matrix.getValue(index) == 1) {
                Node<Index> indexNode = new Node<>(index, someNode);
                reachableIndex.add(indexNode);
            }
        }
        return reachableIndex;
    }

    @Override
    public Collection<DirectNode<Index>> getDirectedNeighborNodes(DirectNode<Index> someNode,boolean includeDiagonal) {
        return this.matrix.getNeighbors(someNode.getData(), includeDiagonal)
                .stream().filter(p->!banList.contains(p)).map(index -> new DirectNode<>(index, someNode)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return matrix.toString();
    }

    public boolean isValidIndex(Index index){
        return matrix.isValidIndex(index);
    }
}