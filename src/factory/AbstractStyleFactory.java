package factory;

import composition.Container;
import composition.Leaf;
import icon.IconFactory;

import java.util.List;

public abstract class AbstractStyleFactory implements Factory {

    @Override
    public Container createContainer(String name, int level, IconFactory iconFactory) {
        return new Container(name, iconFactory, level);
    }

    @Override
    public Leaf createLeaf(String name, IconFactory iconFactory) {
        return new Leaf(name, iconFactory);
    }

    @Override
    public void drawContainer(Container container, int indent, boolean isLast, List<Boolean> drawPipes) {
        drawContainer(container, indent, isLast, drawPipes, true);
    }

    protected void drawContainer(Container container, int indent, boolean isLast, List<Boolean> drawPipes, boolean isRoot) {
        if (!isRoot) {
            printIndent(container.getLevel(), isLast, drawPipes);
            container.draw();
            System.out.println();
        }

        for (int i = 0; i < container.getLeaves().size(); i++) {
            Leaf leaf = container.getLeaves().get(i);
            boolean isLastLeaf = (i == container.getLeaves().size() - 1);
            drawPipes.add(!isLast);
            if (leaf instanceof Container) {
                drawContainer((Container) leaf, indent + 1, isLastLeaf, drawPipes, false);
            } else {
                drawLeaf(leaf, indent + 1, isLastLeaf, drawPipes);
            }
            drawPipes.remove(drawPipes.size() - 1);
        }
    }

    @Override
    public void drawLeaf(Leaf leaf, int indent, boolean isLast, List<Boolean> drawPipes) {
        printIndent(indent, isLast, drawPipes);
        leaf.draw();
        System.out.println();
    }

    protected void printIndent(int level, boolean isLast, List<Boolean> drawPipes) {
        for (int i = 0; i < level; i++) {
            if (i < drawPipes.size() && drawPipes.get(i)) {
                System.out.print("│  ");
            } else {
                System.out.print("   ");
            }
        }
        System.out.print(isLast ? "└── " : "├── ");
    }
}
