package com.example.stet.Fragment;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stet.DataClothSelector;
import com.example.stet.Helper.ServiceTypes;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;

import com.example.stet.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {


    private ProgressBar mProgressBarLoading;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private TotalChangeBottom totalChangeBottom;

    public interface TotalChangeBottom{
        public void total_count_change_bottom(int total,int count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_fragment_bottom);
        mProgressBarLoading = view.findViewById(R.id.progress_bar_bottom);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<DataClothSelector> data = new ArrayList<>();
        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        switch(ServiceTypes.service_type){
            case "wash_fold":
                Cursor cursor_wf = clothSelectorDbHelper.readContacts(sqLiteDatabase);
                while (cursor_wf.moveToNext())
                {
                    String category = cursor_wf.getString(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
                    String sub_category = cursor_wf.getString(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY));
                    if (category.equals("wash_fold") && sub_category.equals("bottom")){
                        data.add(
                                new DataClothSelector(
                                        cursor_wf.getInt(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID)),
                                        cursor_wf.getString(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME)),
                                        cursor_wf.getInt(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE)),
                                        cursor_wf.getInt(cursor_wf.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT))
                                )
                        );
                    }
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;

            case "wash_iron":
                Cursor cursor_wi = clothSelectorDbHelper.readContacts(sqLiteDatabase);

                while (cursor_wi.moveToNext())
                {
                    String sub_category = cursor_wi.getString(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY));
                    String category = cursor_wi.getString(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
                    if (category.equals("wash_iron") && sub_category.equals("bottom")){
                        data.add(
                                new DataClothSelector(
                                        cursor_wi.getInt(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID)),
                                        cursor_wi.getString(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME)),
                                        cursor_wi.getInt(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE)),
                                        cursor_wi.getInt(cursor_wi.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT))
                                )
                        );
                    }
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
            case "iron":
                Cursor cursor_i = clothSelectorDbHelper.readContacts(sqLiteDatabase);
                while (cursor_i.moveToNext())
                {
                    String sub_category = cursor_i.getString(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY));
                    String category = cursor_i.getString(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
                    if (category.equals("iron") && sub_category.equals("bottom")){
                        data.add(
                                new DataClothSelector(
                                        cursor_i.getInt(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID)),
                                        cursor_i.getString(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME)),
                                        cursor_i.getInt(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE)),
                                        cursor_i.getInt(cursor_i.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT))
                                )
                        );
                    }
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
            case "dry_clean":
                Cursor cursor_dc = clothSelectorDbHelper.readContacts(sqLiteDatabase);

                while (cursor_dc.moveToNext())
                {
                    String sub_category = cursor_dc.getString(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY));
                    String category = cursor_dc.getString(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
                    if (category.equals("dry_clean") && sub_category.equals("bottom")){
                        data.add(
                                new DataClothSelector(
                                        cursor_dc.getInt(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID)),
                                        cursor_dc.getString(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME)),
                                        cursor_dc.getInt(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE)),
                                        cursor_dc.getInt(cursor_dc.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT))
                                )
                        );
                    }
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
        }

        return view;
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataClothSelector> dataList;

        public ListAdapter(ArrayList<DataClothSelector> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewCloth;
            TextView textViewPrice;
            TextView textViewQuantity;
            Button plus,minus;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewCloth = (TextView) itemView.findViewById(R.id.cloth_type_recycler_view_item);
                this.textViewPrice = (TextView) itemView.findViewById(R.id.cloth_price_recycler_view_item);
                this.textViewQuantity = (TextView) itemView.findViewById(R.id.quantity_text_view);
                this.plus = (Button) itemView.findViewById(R.id.plus_button);
                this.minus = (Button) itemView.findViewById(R.id.minus_button);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cloth_selector_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewCloth.setText(dataList.get(position).getCloth());
            holder.textViewPrice.setText(Integer.toString(dataList.get(position).getPrice()));
            holder.textViewQuantity.setText(Integer.toString(dataList.get(position).getQuantity()));

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int initial_count = Integer.parseInt(holder.textViewQuantity.getText().toString());
                    int final_count = initial_count+1;
                    int cloth_id = dataList.get(position).getId();

                    holder.textViewQuantity.setText(Integer.toString(final_count));

                    ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
                    SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
                    clothSelectorDbHelper.updateClothCount(cloth_id,final_count,sqLiteDatabase);

                    changeTotalCost();
                    clothSelectorDbHelper.close();
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(holder.textViewQuantity.getText().toString()) != 0) {
                        int initial_count = Integer.parseInt(holder.textViewQuantity.getText().toString());
                        int final_count = initial_count-1;
                        int cloth_id = dataList.get(position).getId();

                        holder.textViewQuantity.setText(Integer.toString(final_count));

                        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
                        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
                        clothSelectorDbHelper.updateClothCount(cloth_id,final_count,sqLiteDatabase);
                        changeTotalCost();
                        clothSelectorDbHelper.close();
                    }
                }
            });




        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;

        try{
            totalChangeBottom = (TotalChangeBottom) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implements that interface method");
        }
    }


    public void changeTotalCost(){

        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);
        int total_cost = 0;
        int total_count = 0;
        while (cursor.moveToNext())
        {
            int cloth_price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
            total_count += cloth_count;
        }

        totalChangeBottom.total_count_change_bottom(total_cost,total_count);
    }
}



