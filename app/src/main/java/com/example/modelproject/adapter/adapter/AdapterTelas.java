package com.example.modelproject.adapter.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class AdapterTelas extends FragmentPagerAdapter {

    private List<Fragment> telas;

    public AdapterTelas(FragmentManager fm, List<Fragment> telas) {
        super(fm);
        this.telas = telas;
    }

    @Override
    public Fragment getItem(int position) {
        return telas.get(position);
    }

    @Override
    public int getCount() {
        return telas.size();
    }
}
