package composition;

import icon.IconFactory;

public class Leaf {
    protected String name;
    protected IconFactory iconFactory;

    public Leaf(String name, IconFactory iconFactory) {
        this.name = name;
        this.iconFactory = iconFactory;
    }

    public String getName() {
        return name;
    }

    public void draw() {
        //System.out.print(" " + iconFactory.getLeafIcon() + " " + name);
        System.out.printf(" %s %s", iconFactory.getLeafIcon(), name);  // 使用叶子图标
    }
}
