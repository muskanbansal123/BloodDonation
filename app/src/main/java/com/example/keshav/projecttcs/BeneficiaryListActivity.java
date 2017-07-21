package com.example.keshav.projecttcs;

/**
 * Created by keshav on 17-07-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BeneficiaryListActivity extends MainActivity implements BeneficiaryRecyclerAdapter.ItemClickCallBack{


    //private AppCompatActivity activity = BeneficiaryListActivity.this;
    Context context = BeneficiaryListActivity.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<DonorDeatils> listBeneficiary;
    private List<DonorDeatils> newDList;
    private ArrayList<AcceptRequest> requestList;
    private BeneficiaryRecyclerAdapter beneficiaryRecyclerAdapter;
    List<String> endUserID = new ArrayList<String>();

    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String Uid = firebaseUser.getUid();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewBeneficiary = (RecyclerView) findViewById(R.id.recyclerViewBeneficiary);
        listBeneficiary = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(beneficiaryRecyclerAdapter);
        getDetails();
        beneficiaryRecyclerAdapter.notifyDataSetChanged();


    }

    private void getDetails(){
        listBeneficiary.clear();
        dref.child("Donor").child("endUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    endUserID.add(dataSnapshot1.getKey());

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dataRef = database.getReference("endUsers").child(dataSnapshot1.getKey());
                    dataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapShot : dataSnapshot.child("Donor_deatils").getChildren()){
                                requestList.clear();
                                List<AcceptRequest> newList = new ArrayList<>();
                                newList.clear();

                                for (DataSnapshot Snapshot : postSnapShot.getChildren()){
                                    String t = (String) Snapshot.child("name").getValue();
                                    String c = (String) Snapshot.child("contact").getValue();
                                    String ci = (String) Snapshot.child("message").getValue();

                                    AcceptRequest ar = new AcceptRequest(t, c, ci, (Boolean) Snapshot.child("requestPending").getValue());
                                    requestList.add(ar);
                                }

                                for (AcceptRequest x : requestList)
                                    newList.add(x);

                                String name = (String) dataSnapshot.child("Donor_details").child("name").getValue();
                                String contact = (String) dataSnapshot.child("Donor_details").child("contact").getValue();
                                String city = (String) dataSnapshot.child("Donor_details").child("city").getValue();
                                String bloodgroup = (String) dataSnapshot.child("Donor_details").child("bloodgroup").getValue();
                                String email = (String) dataSnapshot.child("Donor_details").child("email").getValue();

                                DonorDeatils x = new DonorDeatils(name, city, contact, bloodgroup, email);
                                x.setRequests(newList);

                                newDList.clear();
                                newDList.addAll(listBeneficiary);
                                newDList.add(x);
                                listBeneficiary.clear();
                                listBeneficiary.addAll(newDList);
                                beneficiaryRecyclerAdapter.notifyDataSetChanged();


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onSecondaryIconClick(int position) {

    }
}
