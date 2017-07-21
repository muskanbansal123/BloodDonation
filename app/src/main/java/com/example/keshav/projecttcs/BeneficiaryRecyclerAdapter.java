package com.example.keshav.projecttcs;

/**
 * Created by keshav on 17-07-2017.
 */

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class BeneficiaryRecyclerAdapter extends RecyclerView.Adapter<BeneficiaryRecyclerAdapter.BeneficiaryViewHolder> {

    private ArrayList<DonorDeatils> listBeneficiary;
    DonorDeatils donorDeatils;
    private Context mContext;
    private ItemClickCallBack itemClickCallBack;

    public BeneficiaryRecyclerAdapter(ArrayList<DonorDeatils> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;

    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewAddress;
        public AppCompatTextView textViewCountry;
        public  ImageView overflow;
        LinearLayout rl;

        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewAddress = (AppCompatTextView) view.findViewById(R.id.textViewAddress);
            textViewCountry = (AppCompatTextView) view.findViewById(R.id.textViewCountry);
            rl = (LinearLayout) view.findViewById(R.id.singleDataLinearLayout);
            rl.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.singleDataLinearLayout)
                itemClickCallBack.onItemClick(getAdapterPosition());
        }
    }


    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_beneficiary_recycler, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {

        donorDeatils = listBeneficiary.get(position);

        holder.textViewName.setText(donorDeatils.getUsername());
        holder.textViewEmail.setText(donorDeatils.getEmail());
        holder.textViewAddress.setText(donorDeatils.getCity());
        holder.textViewCountry.setText(donorDeatils.getBloodgroup());


    }


    @Override
    public int getItemCount() {
        return listBeneficiary.size();
    }

    public interface ItemClickCallBack{
        void onItemClick(int position);
        void onSecondaryIconClick(int position);
    }

    public void setItemClickCallBack(final ItemClickCallBack itemClickCallBack){
        this.itemClickCallBack = itemClickCallBack;
    }
}
