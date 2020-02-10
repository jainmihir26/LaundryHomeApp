package com.example.stet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DressFragment extends Fragment {


    private ProgressBar mProgressBarLoading;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_fragment_dress);
        mProgressBarLoading = view.findViewById(R.id.progress_bar_dress);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<DataClothSelector> data = new ArrayList<>();
        switch(Helper.service_type){
            case "wash_fold":
                for(int i=0;i<ServiceWashFoldData.idDress.length;i++){
                    data.add(
                            new DataClothSelector(
                                    ServiceWashFoldData.idDress[i],
                                    ServiceWashFoldData.clothArrayDress[i],
                                    ServiceWashFoldData.priceArrayDress[i]
                            )
                    );
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
            case "wash_iron":
                for(int i=0;i<ServiceWashIronData.idDress.length;i++){
                    data.add(
                            new DataClothSelector(
                                    ServiceWashIronData.idDress[i],
                                    ServiceWashIronData.clothArrayDress[i],
                                    ServiceWashIronData.priceArrayDress[i]
                            )
                    );
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
            case "iron":
                for(int i=0;i<ServiceIronData.idDress.length;i++){
                    data.add(
                            new DataClothSelector(
                                    ServiceIronData.idDress[i],
                                    ServiceIronData.clothArrayDress[i],
                                    ServiceIronData.priceArrayDress[i]
                            )
                    );
                }
                mListadapter = new ListAdapter(data);
                mRecyclerView.setAdapter(mListadapter);
                break;
            case "dry_clean":
                for(int i=0;i<ServiceDryCleanData.idDress.length;i++){
                    data.add(
                            new DataClothSelector(
                                    ServiceDryCleanData.idDress[i],
                                    ServiceDryCleanData.clothArrayDress[i],
                                    ServiceDryCleanData.priceArrayDress[i]
                            )
                    );
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

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewCloth = (TextView) itemView.findViewById(R.id.cloth_type_recycler_view_item);
                this.textViewPrice = (TextView) itemView.findViewById(R.id.cloth_price_recycler_view_item);

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
        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewCloth.setText(dataList.get(position).getCloth());
            holder.textViewPrice.setText(dataList.get(position).getPrice());


            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}


