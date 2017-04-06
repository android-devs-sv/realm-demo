package moizest89.realmdemo;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by moizest89 on 4/6/17.
 */

public class MyRealm {

    private static Realm realm;

    public static Realm with(Context context){
        if(realm == null){
            realm.init(context);
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    private MyRealm(){
        throw new AssertionError("No instances.");
    }


}
