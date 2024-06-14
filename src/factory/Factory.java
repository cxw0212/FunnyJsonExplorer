package factory;

import composition.Container;
import composition.Leaf;
import icon.IconFactory;
import java.util.List;

public interface Factory {
    Container createContainer(String name, int level, IconFactory iconFactory);
    Leaf createLeaf(String name, IconFactory iconFactory);
    void drawContainer(Container container, int indent, boolean isLast, List<Boolean> drawPipes);
    void drawLeaf(Leaf leaf, int indent, boolean isLast, List<Boolean> drawPipes);
}
