package iterator;

import composition.Container;
import composition.Leaf;

import java.util.List;
import java.util.Stack;

public class ContainerIterator implements java.util.Iterator<Leaf> {
    private Stack<Leaf> stack = new Stack<>();

    public ContainerIterator(Container root) {
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Leaf next() {
        Leaf current = stack.pop();
        if (current instanceof Container) {
            List<Leaf> children = ((Container) current).getLeaves();
            for (int i = children.size() - 1; i >= 0; i--) {
                stack.push(children.get(i));
            }
        }
        return current;
    }
}
