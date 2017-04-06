package moizest89.realmdemo.models;

import io.realm.RealmObject;

/**
 * Created by moizest89 on 4/6/17.
 */

public class Pupusa extends RealmObject {

    private long id;
    private Flavour flavour;
    private int type;
    private int qty;

    public Pupusa() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Flavour getFlavour() {
        return flavour;
    }

    public void setFlavour(Flavour flavour) {
        this.flavour = flavour;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

