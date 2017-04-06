package moizest89.realmdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moizest89.realmdemo.models.Flavour;
import moizest89.realmdemo.models.Pupusa;

/**
 * Created by moizest89 on 4/6/17.
 */

public class PupusasAdapter extends RecyclerView.Adapter<PupusasAdapter.Holder>{


    private List<Pupusa> data = new ArrayList<>();
    private Context context;

    public PupusasAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pupusas_list, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Pupusa pupusa = this.data.get(position);
        holder.text_view_qty.setText(String.valueOf(pupusa.getQty()));
        holder.text_view_material.setText(pupusa.getType() == 0? "Maiz": "Arroz");

        Flavour flavour = pupusa.getFlavour();

        holder.text_view_flavour.setText(flavour.getName());


    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }


    public void setData(List<Pupusa> mData){
        this.data = mData;
        this.notifyDataSetChanged();
    }

    public void setItemData(Pupusa item){
        this.data.add(item);
        this.notifyItemInserted(data.size() + 1);
    }


    public int getSizeElements(){
        return this.data.size();
    }

    public void clearData(){
        this.data.clear();
        this.notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder{

        TextView text_view_qty, text_view_flavour, text_view_material;

        public Holder(View itemView) {
            super(itemView);

            this.text_view_qty = (TextView) itemView.findViewById(R.id.text_view_qty);
            this.text_view_flavour = (TextView) itemView.findViewById(R.id.text_view_flavour);
            this.text_view_material = (TextView) itemView.findViewById(R.id.text_view_material);

        }
    }
}
