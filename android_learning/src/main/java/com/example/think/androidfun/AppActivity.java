package com.example.think.androidfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.androidfun.touchevent.RecyclerPlus;
import com.example.think.androidfun.touchevent.ScrollPlus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Add 3.0
 *
 * Add 4.0  ADD 44444
 */
public class AppActivity extends AppCompatActivity {

    @Bind(R.id.r1)
    RecyclerPlus recyclerView;

    @Bind(R.id.sc1)
    ScrollPlus scrollPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);


        RedAdapter redAdapter=new RedAdapter();
        LinearLayoutManager linearLayoutManage=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManage);
        recyclerView.setAdapter(redAdapter);

        scrollPlus.setInnerView(recyclerView);

    }

    private static class RedAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.r1,parent,false);
            return new RedViewHolder(view);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 50;
        }

        public static class RedViewHolder extends RecyclerView.ViewHolder{

            public RedViewHolder(View itemView) {
                super(itemView);
            }
        }

    }






}
