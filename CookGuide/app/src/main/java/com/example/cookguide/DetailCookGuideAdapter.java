package com.example.cookguide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DetailCookGuideAdapter extends FragmentStatePagerAdapter {
    public DetailCookGuideAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DescriptionFragment();
            case 1:
                return new MaterialFragment();
            case 2:
                return new ImplementationFragment();
            default:
                return new DescriptionFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Mô tả chung";
                break;
            case 1:
                title = "Nguyên liệu";
                break;
            case 2:
                title = "Thực hiện";
                break;
        }
        return title;
    }
}
