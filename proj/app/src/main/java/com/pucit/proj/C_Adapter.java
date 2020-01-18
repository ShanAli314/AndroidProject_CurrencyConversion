package com.pucit.proj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucit.proj.DataModel.Country;

import java.util.List;

public class C_Adapter extends RecyclerView.Adapter<C_Adapter.C_ViewHolder> {

    List<Country> cList;
    Context context;

    public C_Adapter(List<Country> cList,Context context) {
        this.cList=cList;
        this.context = context;
    }

    @NonNull
    @Override
    public C_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_country_icons,parent,false);
        C_ViewHolder c_viewHolder= new C_ViewHolder(view);
        return c_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final C_ViewHolder holder, final int position) {
        final MainActivity mainActivity = (MainActivity) context;
        final TextView ethView=mainActivity.findViewById(R.id.local_currency_view);
        final TextView bitView=mainActivity.findViewById(R.id.bitcoin_view);

        final Country country=cList.get(position);



        holder.c_btn.setImageResource(country.getPicID());
        holder.c_btn.setId(position);
        holder.c_txt.setText(country.getName());

        holder.c_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               // Toast.makeText(view.getContext(), "You clicked on " +Integer.toString(position)+" id="+holder.c_btn.getId() , Toast.LENGTH_SHORT).show();
                TextView cTV=mainActivity.findViewById(R.id.showing_coutry_name);
                cTV.setText(country.getName()+" : "+country.getCurrencyCode());

                bitView.setText("00.00");
                ethView.setText("00.00");
                if(mainActivity.isBitCurrency)
                    mainActivity.getBitCoinValue(country.getCurrencyCode());
                if(mainActivity.isEthCurrency)
                    mainActivity.getEtherumValue(country.getCurrencyCode());

            }
        });
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    public class C_ViewHolder extends RecyclerView.ViewHolder{
        ImageButton c_btn;
        TextView c_txt;
        public C_ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_btn =itemView.findViewById(R.id.c_image_button_rv);
            c_txt=itemView.findViewById(R.id.c_textview_rv);

        }
    }
}