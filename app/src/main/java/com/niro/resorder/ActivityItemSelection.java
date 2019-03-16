package com.niro.resorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.niro.resorder.adapter.ItemSelectionAdapter;
import com.niro.resorder.pojo.Item;

import java.util.ArrayList;
import java.util.List;


public class ActivityItemSelection extends AppCompatActivity implements ItemSelectionAdapter.SelectionDelegate {

    private ItemSelectionAdapter selectionAdapter;
    private List<Item> selectedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        assignViews();
    }

    private void assignViews(){
        RecyclerView orderListRV = findViewById(R.id.orderList);
        selectedItemList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();

        for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }


        selectionAdapter = new ItemSelectionAdapter(itemList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setHasFixedSize(true);
        orderListRV.setAdapter(selectionAdapter);

    }

    @Override
    public void selectedItems(Item item) {
        if (selectedItemList.size()>0){
            int itemCount = 0;
            int index = -1;

            for (Item itemPojo:selectedItemList){

                if (itemPojo.getItemNumber().equalsIgnoreCase(item.getItemNumber())){
                    index = itemCount;
                }
                itemCount++;
            }

            if (index == -1){
                //Toast.makeText(getContext(), "please Select item" ,Toast.LENGTH_SHORT).show();
                selectedItemList.add(item);
                //setOrderTotal();
            }else {
                selectedItemList.get(index).setItemQty(selectedItemList.get(index).getItemQty()+item.getItemQty());
                selectedItemList.get(index).setSelingPrice(selectedItemList.get(index).getSelingPrice()+item.getItemPrice());
                //od.setOrderDetailsItemSellingPrice(od.getOrderDetailsItemPrice());
                setOrderTotal();
            }

        }else {

            item.setSelingPrice(item.getItemPrice()*item.getItemQty());
            //od.setOrderDetailsItemSellingPrice(od.getOrderDetailsItemPrice());
            selectedItemList.add(item);
            setOrderTotal();
        }
       // noOfItem.setText(calNoOfItem(selectIdlist));
        selectionAdapter.notifyDataSetChanged();
    }

    private void setOrderTotal(){
        Double orderTotal = 0.0;
        //Double discount = 0.0  ;
        for (Item item:selectedItemList){


            orderTotal= orderTotal + item.getSelingPrice();
        }

        Log.e("Totoal",""+orderTotal);
    }
}
