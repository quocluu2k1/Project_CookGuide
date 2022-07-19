package com.example.cookguide.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookguide.main_fragment.CommentFragment;
import com.example.cookguide.main_fragment.HomeFragment;
import com.example.cookguide.main_fragment.ProfileFragment;
import com.example.cookguide.main_fragment.SearchFragment;

public class ViewPagerMainAdapter extends FragmentPagerAdapter {

    public ViewPagerMainAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new CommentFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


}
