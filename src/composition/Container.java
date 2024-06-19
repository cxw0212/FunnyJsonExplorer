package composition;

import iterator.Iterator;
import icon.IconFactory;

import java.util.ArrayList;
import java.util.List;

public class Container extends Leaf {
    private List<Leaf> leaves;
    private int level;
    private IconFactory iconFactory;

    public Container(String name, IconFactory iconFactory, int level) {
        super(name, iconFactory);
        this.iconFactory = iconFactory;
        this.leaves = new ArrayList<>();
        this.level = level;
    }

    public void addLeaf(Leaf leaf) {
        leaves.add(leaf);
    }

    public List<Leaf> getLeaves() {
        return leaves;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void draw() {
        if (leaves.isEmpty()) {
            System.out.printf(" %s %s", iconFactory.getLeafIcon(), name);  // 使用叶子图标
        } else {
            System.out.printf(" %s %s", iconFactory.getContainerIcon(), name);  // 使用容器图标
        }
    }

    public Iterator<Leaf> createIterator() {
        return new ContainerIterator();
    }

    private class ContainerIterator implements Iterator<Leaf> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < leaves.size();
        }

        @Override
        public Leaf next() {
            if (hasNext()) {
                return leaves.get(index++);
            }
            return null;
        }
    }
}
