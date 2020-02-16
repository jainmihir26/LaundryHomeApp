package com.example.stet.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stet.DataClothSelector;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataClothCart;
import com.example.stet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private Button placeOrderButton;
    private TextView addMoreTextView;
    private TextView totalAmountTextView;
    private RecyclerView cartItemsRecyclerView;
    private ListAdapter mListadapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart,container,false);

        placeOrderButton = v.findViewById(R.id.place_order_button);
        addMoreTextView = v.findViewById(R.id.add_more_textView);
        totalAmountTextView = v.findViewById(R.id.total_amount_textView);


        totalAmountTextView.setText(total_cost+" $");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);


        ArrayList<DataClothCart> data = new ArrayList<>();
        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

        while (cursor.moveToNext())
        {
            String cloth_name = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME));
            String service_type = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));


            if(count !=0){
                data.add(
                        new DataClothCart(id,cloth_name+" "+count,service_type,price*count)
                );
            }

        }
        cursor.close();



        mListadapter = new ListAdapter(data);
        cartItemsRecyclerView.setAdapter(mListadapter);

        addMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
            }
        });


        return v;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataClothCart> dataList;

        public ListAdapter(ArrayList<DataClothCart> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewClothQuantity;
            TextView textViewServiceType;
            TextView textViewClothCost;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewClothQuantity = (TextView) itemView.findViewById(R.id.cloth_quntity_type_recycler_view_item);
                this.textViewServiceType = (TextView) itemView.findViewById(R.id.service_type_cart);
                this.textViewClothCost = (TextView) itemView.findViewById(R.id.cost_per_item_textView);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart_item, parent, false);

            ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewClothQuantity.setText(dataList.get(position).getClothQuantity());
            holder.textViewServiceType.setText(dataList.get(position).getServiceType());
            holder.textViewClothCost.setText(Integer.toString(dataList.get(position).getCost())+" $");

        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}



