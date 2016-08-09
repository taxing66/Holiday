package com.joiway.devin.holiday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.model.CrimesBean;
import com.joiway.devin.holiday.model.single.CrimesBeanLab;

import java.util.List;

/**
 * Created by 德华 on 2016/8/2.
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CrimesBeanLab mCrimesBeanLab;
    private RecyclerViewCrimesAdapter mRecyclerViewCrimesAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerViewCrimesAdapter.notifyItemChanged(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_crimes,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_crimes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mCrimesBeanLab =CrimesBeanLab.getCrimesBeanLab();
        mRecyclerViewCrimesAdapter = new RecyclerViewCrimesAdapter(mCrimesBeanLab.getCrimesBeanList());
        mRecyclerView.setAdapter(mRecyclerViewCrimesAdapter);

        return view;
    }

    private class RecyclerViewCrimesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<CrimesBean> mCrimesBeanList;
        public RecyclerViewCrimesAdapter(List<CrimesBean> crimesBeanList) {
         this.mCrimesBeanList = crimesBeanList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_item_title,parent,false);

            return new CrimeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
               CrimeViewHolder crimeViewHolder = (CrimeViewHolder) holder;
            crimeViewHolder.setData(mCrimesBeanList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimesBeanList.size();
        }
    }

    class  CrimeViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvTitle;
        public CrimeViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
        public  void  setData(CrimesBean crimesBean){
            mTvTitle.setText(crimesBean.getTitle());
        }
    }
}
