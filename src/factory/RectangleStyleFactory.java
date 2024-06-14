package factory;

import composition.Container;

import java.util.List;
import java.util.Stack;

public class RectangleStyleFactory extends AbstractStyleFactory {
    private int nodeCount = 0; // 记录节点数
    private int totalNodes = 0; // 总节点数

    @Override
    protected void drawContainer(Container container, int indent, boolean isLast, List<Boolean> drawPipes, boolean isRoot) {
        // 计算总节点数
        totalNodes = countNodes(container) - 1; // 不计算虚拟根节点
        nodeCount = 0; // 重置节点计数器
        Stack<NodeInfo> stack = new Stack<>();
        stack.push(new NodeInfo(container, indent, isLast, true));

        while (!stack.isEmpty()) {
            NodeInfo current = stack.pop();
            if (!current.isRoot) {
                printIndent(current.indent, current.isLast, drawPipes, current.isRoot);
                current.container.draw();
                printDashes(current.container.getName().length(), current.indent, current.isLast, current.container.getLeaves().isEmpty());
                System.out.println();
                nodeCount++;
            }

            List<composition.Leaf> children = current.container.getLeaves();
            for (int i = children.size() - 1; i >= 0; i--) {
                composition.Leaf child = children.get(i);
                boolean isLastChild = (i == children.size() - 1);
                drawPipes.add(!current.isLast);
                if (child instanceof Container) {
                    stack.push(new NodeInfo((Container) child, current.indent + 1, isLastChild, false));
                } else {
                    stack.push(new NodeInfo(child, current.indent + 1, isLastChild));
                }
            }
        }
    }

    private void printIndent(int indent, boolean isLast, List<Boolean> drawPipes, boolean isRoot) {
        if (nodeCount == 0) {
            System.out.print("┌──");
        } else if (nodeCount == totalNodes - 1) {
            System.out.print("└──");
            for (int i = 1; i < indent; i++) {
                System.out.print("─┴─");
            }
        } else {
            if (1 >= indent) {
                System.out.print("├──");
            } else {
                System.out.print("│");
                for (int i = 1; i < indent; i++) {
                    if (i == 1) System.out.print("   ");
                    else System.out.print("│  ");
                }
                System.out.print("├─");
            }
        }
    }

    private void printDashes(int nameLength, int indent, boolean isLast, boolean noKids) {
        int maxLength = 80; // 最大长度
        int indentLength = indent * 3 + 4; // 缩进长度（每个缩进3个字符，再加上前导字符长度）
        int remainingLength = maxLength - nameLength - indentLength; // 计算剩余长度

        for (int i = 0; i < remainingLength / 2; i++) {
            System.out.print("──");
        }
        if (remainingLength % 2 != 0) {
            System.out.print("─");
        }

        if (nodeCount == 0) {
            System.out.print("┐");
        } else if (nodeCount == totalNodes - 1) {
            System.out.print("┘");
        } else {
            System.out.print("┤");
        }
    }

    private int countNodes(Container container) {
        int count = 1; // 当前容器本身
        for (composition.Leaf leaf : container.getLeaves()) {
            if (leaf instanceof Container) {
                count += countNodes((Container) leaf);
            } else {
                count++;
            }
        }
        return count;
    }

    private class NodeInfo {
        Container container;
        composition.Leaf leaf;
        int indent;
        boolean isLast;
        boolean isRoot;

        NodeInfo(Container container, int indent, boolean isLast, boolean isRoot) {
            this.container = container;
            this.indent = indent;
            this.isLast = isLast;
            this.isRoot = isRoot;
        }

        NodeInfo(composition.Leaf leaf, int indent, boolean isLast) {
            this.leaf = leaf;
            this.indent = indent;
            this.isLast = isLast;
            this.isRoot = false;
        }
    }
}
