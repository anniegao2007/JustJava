package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

//Displays order form to order coffee
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    //Method called when order button is clicked
    public void submitOrder(View view)
    {
        CheckBox wc = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox choco = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText name = (EditText) findViewById(R.id.name_edittext);
        boolean hasWC = wc.isChecked();
        boolean hasChoco = choco.isChecked();
        String person = name.getText().toString();
        int price = price(hasWC, hasChoco);

        String priceMessage = createOrderSummary(hasWC, hasChoco, person, price);

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + person);
        email.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(email.resolveActivity(getPackageManager()) != null)
        {
            startActivity(email);
        }
    }

    private String createOrderSummary(boolean hasWC, boolean hasChoco, String person, int price)
    {

        return "Name: " + person
                + "\nAdd whipped cream? " + hasWC
                + "\nAdd chocolate? " + hasChoco
                + "\nQuantity: " + quantity
                + "\nTotal: $" + price
                + "\nThank you!!";
    }

    //Displays given quantity value on the screen
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int price(boolean hasWC, boolean hasChoco)
    {
        int price = 3;
        if(hasWC) {price++;}
        if(hasChoco) {price += 2;}
        return price * quantity;
    }

    public void increment(View view)
    {
        if(quantity <= 100)
        {
            quantity++;
        }
        display(quantity);
    }

    public void decrement(View view)
    {
        if(quantity > 0)
        {
            quantity--;
        }
        display(quantity);
    }
}
