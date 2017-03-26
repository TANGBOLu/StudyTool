package com.example.tang.cuttlefish.BottomBarFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tang.cuttlefish.R;
import com.example.tang.cuttlefish.RecyclerAdapter.IndexRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tang on 2017/3/11.
 */

public class IndexFragment extends Fragment {
    @BindView(R.id.indexRecyclerView)
    RecyclerView indexRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_activity, container, false);
        ButterKnife.bind(this, view);
        indexRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        IndexRvAdapter adapter=new IndexRvAdapter();
        indexRecyclerView.setAdapter(adapter);
        return view;
    }
}
