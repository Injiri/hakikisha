package org.aplusscreators.hakikisha.adapters.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.aplusscreators.hakikisha.views.fragment.AllTransactionsFragment;
import org.aplusscreators.hakikisha.views.fragment.ExpenseFragment;
import org.aplusscreators.hakikisha.views.fragment.IncomeTransactionsFragment;

public class TransactionsViewPagerAdapter extends FragmentStatePagerAdapter {

    public TransactionsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Sales";
            case 2:
                return "Purchases";
            default:
                return "Unknown";
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllTransactionsFragment();
            case 1:
                return new IncomeTransactionsFragment();
            case 2:
                return new ExpenseFragment();
            default:
                return new AllTransactionsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
