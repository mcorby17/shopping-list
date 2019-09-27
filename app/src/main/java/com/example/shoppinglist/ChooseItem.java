package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseItem extends AppCompatActivity {

    public static final String ITEM_TEXT = "com.example.shoppinglist.ChooseItem.ITEM_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);
    }

    // When a button is pressed, I need to take the text and
    // put it in a response
    public void itemReply(View view)
    {
        // Get button text
        Button button = (Button) view;
        String itemDesc = button.getText().toString();

        // Create a response containing the text
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ITEM_TEXT, itemDesc);
        setResult(RESULT_OK, intent);
        finish();
    }
}
