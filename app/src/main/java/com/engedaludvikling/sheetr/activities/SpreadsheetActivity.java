package com.engedaludvikling.sheetr.activities;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.engedaludvikling.sheetr.R;
import com.engedaludvikling.sheetr.fragments_spreadsheet.InformationFragment;
import com.engedaludvikling.sheetr.fragments_spreadsheet.InventoryFragment;
import com.engedaludvikling.sheetr.fragments_spreadsheet.ProficienciesFragment;
import com.engedaludvikling.sheetr.fragments_spreadsheet.SpellsFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpreadsheetActivity extends AppCompatActivity {

    @BindView(R.id.character_bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spreadsheet);
        ButterKnife.bind(this);

        disableShiftMode(bottomNavigationView);
        setOnBottomMenuItemClick();
        bottomNavigationView.setSelectedItemId(R.id.character_menu_information);
    }

    // Method for disabling ShiftMode of BottomNavigationView
    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void setOnBottomMenuItemClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentManager fragmentManager = getFragmentManager();
                        switch (item.getItemId()) {
                            case R.id.character_menu_information:
                                fragmentManager.beginTransaction().replace(R.id.character_content_frame, new InformationFragment()).commit();
                                break;
                            case R.id.character_menu_proficiencies:
                                fragmentManager.beginTransaction().replace(R.id.character_content_frame, new ProficienciesFragment()).commit();
                                break;
                            case R.id.character_menu_inventory:
                                fragmentManager.beginTransaction().replace(R.id.character_content_frame, new InventoryFragment()).commit();
                                break;
                            case R.id.character_menu_spells:
                                fragmentManager.beginTransaction().replace(R.id.character_content_frame, new SpellsFragment()).commit();
                                break;
                        }
                        return true;
                    }
                });
    }
}
