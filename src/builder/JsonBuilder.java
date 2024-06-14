package builder;

import composition.Container;
import composition.Leaf;
import factory.Factory;
import icon.IconFactory;

public class JsonBuilder {
    private Factory factory;
    private IconFactory iconFactory;
    private Container rootContainer;

    public JsonBuilder(Factory factory, IconFactory iconFactory) {
        this.factory = factory;
        this.iconFactory = iconFactory;
    }

    public JsonBuilder buildRootContainer(String name) {
        this.rootContainer = factory.createContainer(name, 0, iconFactory);
        return this;
    }

    public JsonBuilder addContainer(Container container) {
        if (this.rootContainer == null) {
            throw new IllegalStateException("Root container is not built");
        }
        rootContainer.addLeaf(container);
        return this;
    }

    public JsonBuilder addLeaf(String name) {
        if (this.rootContainer == null) {
            throw new IllegalStateException("Root container is not built");
        }
        rootContainer.addLeaf(factory.createLeaf(name, iconFactory));
        return this;
    }

    public Container getResult() {
        return rootContainer;
    }
}
