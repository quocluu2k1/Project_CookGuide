package com.example.cookguide.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cookguide.profile_fragment.MarkbookFragment;
import com.example.cookguide.profile_fragment.UserProfileFragment;

public class UserprofileAdapter extends FragmentStatePagerAdapter {
    public UserprofileAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserProfileFragment();
            case 1:
                return new MarkbookFragment();
            default:
                return new UserProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Hồ sơ";
                break;
            case 1:
                title = "Món đã lưu";
                break;
        }
        return title;
    }
}