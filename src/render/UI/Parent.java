package render.UI;

import java.util.List;

public interface Parent {
    List<Child> getChildren();
    void addChild(Child c);
}
