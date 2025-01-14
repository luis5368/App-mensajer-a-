package com.example.appmensa20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AccesoTabsAdapter extends FragmentPagerAdapter {
    public AccesoTabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
            case 1:
                GruposFragment gruposFragment = new GruposFragment();
                return gruposFragment;
            case 2:
                ContactosFragment contactosFragment = new ContactosFragment();
                return contactosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Chats";
            case 1:

                return "Grupos";
            case 2:

                return "Contactos";
            default:
                return null;
        }

    }
}
