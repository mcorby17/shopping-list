package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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

    private TextView mFindStoreTextView;

    private final String mGeoPrefix = "geo:0,0?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainListLayout = findViewById(R.id.list);
        mFindStoreTextView = findViewById(R.id.searchStoreText);

        // Create the previous list items if they were there
        if (savedInstanceState != null)
        {
            mListItems = savedInstanceState.getStringArrayList(ITEM_LIST);

            try
            {
                for (String itemText : mListItems)
                {
                    mMainListLayout.addView(createListItem(itemText));
                }
            }
            catch (Exception e)
            {
                Log.e(LOG_TAG, "mListItems was null, cannot loop over it");
                e.printStackTrace();
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
            String itemDesc = null;

            try
            {
                itemDesc = data.getStringExtra(ChooseItem.ITEM_TEXT);
            }
            catch (Exception e)
            {
                Log.e(LOG_TAG, "Unable to get list item description out of Intent");
                e.printStackTrace();
            }

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

    public void findStore(View view) {

        // Get text out of textView
        String text = mFindStoreTextView.getText().toString();
        String uriText = mGeoPrefix + text;

        // Make geo URI
        Uri uri = Uri.parse(uriText);

        // Create new implicit intent
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
}
