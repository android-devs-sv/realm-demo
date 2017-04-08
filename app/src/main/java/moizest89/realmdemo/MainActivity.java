package moizest89.realmdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import moizest89.realmdemo.models.Flavour;
import moizest89.realmdemo.models.Pupusa;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private final static String TAG = MainActivity.class.getSimpleName();
    private String[] mFlavour;
    private String[] mTypes;
    private RecyclerView recycler_view;
    private PupusasAdapter adapter;
    private TextView textview_message;
    private NestedScrollView nested_main;
    private TextView textview_total_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //BindView
        this.nested_main = (NestedScrollView) this.findViewById(R.id.nested_main);
        this.textview_message = (TextView) this.findViewById(R.id.textview_message);
        this.textview_total_items = (TextView) this.findViewById(R.id.textview_total_items);
        this.recycler_view = (RecyclerView) this.findViewById(R.id.recycler_view);

        this.realm = MyRealm.with(this);


        this.adapter = new PupusasAdapter(this);
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_view.setAdapter(this.adapter);
        String mMessage = getEmojiByUnicode(0x1F631) +" \n "+ getResources().getString(R.string.main_message_no_item_into_the_list);
        this.textview_message.setText(mMessage);


        //Data
        loadMainData();
        this.mFlavour = this.getResources().getStringArray(R.array.basic_flavour);
        this.mTypes = this.getResources().getStringArray(R.array.basic_types);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPupusaItemDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });





    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_clean_list) {
            cleanList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadMainData(){
        RealmResults<Pupusa> query = realm.where(Pupusa.class).findAll();

        List<Pupusa> pupusas = realm.copyFromRealm(query);
        this.adapter.setData(pupusas);

        viewElementsAtributes();

    }

    private void setTotalItems(){
        RealmResults<Pupusa> result = realm.where(Pupusa.class).findAllAsync();
        result.addChangeListener(new RealmChangeListener<RealmResults<Pupusa>>() {
            @Override
            public void onChange(RealmResults<Pupusa> results) {

                Log.e(TAG, "element: "+results);
                long sum = results.sum(Pupusa.QTY).longValue();
                Log.e(TAG, "qty: "+sum);

                textview_total_items.setText("Total de pupusas: "+sum);
            }
        });

        RealmObjectChangeListener<Pupusa> listener = new RealmObjectChangeListener<Pupusa>() {
            @Override
            public void onChange(Pupusa object, ObjectChangeSet changeSet) {
                Log.e(TAG, "object: "+object);
                Log.e(TAG, "changeSet: "+changeSet);
            }
        };


        result.asObservable()

    }

    private void viewElementsAtributes(){
        if(this.adapter.getSizeElements() > 0) {
            this.nested_main.setVisibility(View.VISIBLE);
            this.textview_message.setVisibility(View.INVISIBLE);
            setTotalItems();
        }else{
            this.nested_main.setVisibility(View.INVISIBLE);
            this.textview_message.setVisibility(View.VISIBLE);
        }
    }

    private void addNewPupusaItemDialog(){

        View view = getLayoutInflater().inflate(R.layout.item_add_item, null);

        final Spinner flavours = (Spinner) view.findViewById(R.id.spinner_flavours);
        final Spinner materials = (Spinner) view.findViewById(R.id.spinner_materials);
        final EditText qty = (EditText) view.findViewById(R.id.edittext_qty);

        flavours.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mFlavour));
        materials.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mTypes));


        new AlertDialog.Builder(this)
            .setTitle("Añadir nuevo item")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Pupusa pupusa = new Pupusa();

                    Flavour flavour = new Flavour(flavours.getSelectedItem().toString());
                    pupusa.setType(materials.getSelectedItem().toString().equals("Maiz")?0:1);
                    pupusa.setFlavour(flavour);
                    pupusa.setQty( Integer.parseInt(qty.getText().toString()));
                    addNewPupusaItem(pupusa);

                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setView(view)
            .create().show();
    }


    private void addNewPupusaItem(Pupusa pp){
        realm.beginTransaction();
        Pupusa pupusa = realm.copyToRealm(pp);
        realm.commitTransaction();
        this.adapter.setItemData(pp);
        viewElementsAtributes();
    }


    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }


    private void cleanList(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("¿Deseas borrar la lista completa?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        realm.delete(Pupusa.class);
                        adapter.clearData();
                        realm.commitTransaction();
                        viewElementsAtributes();

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create().show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(this.realm !=null){
            realm.close();
        }
    }
}
