package composition;

import java.util.ArrayList;
import java.util.List;
import icon.IconFactory;

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

}
