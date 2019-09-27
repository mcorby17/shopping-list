package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ITEM_REQUEST = 1;
    public static final String ITEM_LIST = "com.example.shoppinglist.MainActivity.ITEM_LIST";

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private ArrayList<String> mListItems = new ArrayList<>();

    private LinearLayout mMainListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainListLayout = findViewById(R.id.list);

        // Create the previous list items if they were there
        if (savedInstanceState != null)
        {
            mListItems = savedInstanceState.getStringArrayList(ITEM_LIST);

            for (String itemText : mListItems)
            {
                mMainListLayout.addView(createListItem(itemText));
            }
        }
    }

    public void getListItem(View view)
    {
        Intent intent = new Intent(this, ChooseItem.class);
        startActivityForResult(intent, ITEM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ITEM_REQUEST &&
            resultCode == RESULT_OK)
        {
            String itemDesc = data.getStringExtra(ChooseItem.ITEM_TEXT);

            if (itemDesc != null)
            {
                mListItems.add(itemDesc);
                mMainListLayout.addView(createListItem(itemDesc));
            }
            else
            {
                Log.e(LOG_TAG, "Couldn't find item text in reply bundle!");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save all the item texts to pull out in onCreate
        outState.putStringArrayList(ITEM_LIST, mListItems);
    }

    private TextView createListItem(String itemDescription)
    {
        TextView textView = new TextView(this);

        // Set LinearLayout-specific params
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                              ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(8,8,8,8);

        // Populate the textView
        textView.setLayoutParams(layoutParams);
        textView.setText(itemDescription);
        textView.setPadding(8,8,8,8);
        textView.setBackgroundResource(R.color.colorPrimaryDark);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        return textView;
    }
}
