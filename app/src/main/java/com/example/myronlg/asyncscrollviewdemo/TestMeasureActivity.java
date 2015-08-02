package com.example.myronlg.asyncscrollviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TestMeasureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_measure);
        TextView textView = (TextView) findViewById(R.id.tv_test_measure);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.height = 600;
        textView.setLayoutParams(layoutParams);
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY);
//        textView.measure(widthSpec, heightSpec);
//        textView.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_measure, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
