package Management;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    public String name;
    public String content;
    public Map<Integer, Menu> map;

    public Menu (String name, String content) {
        this.name = name;
        this.content = content;
        this.map = new HashMap<>();
    }
}
