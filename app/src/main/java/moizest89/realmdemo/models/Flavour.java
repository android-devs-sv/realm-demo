package moizest89.realmdemo.models;

import io.realm.RealmObject;

/**
 * Created by moizest89 on 4/6/17.
 */

public class Flavour extends RealmObject {

    private String name;
    private boolean isDefault = true;

    public Flavour() {
    }

    public Flavour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
