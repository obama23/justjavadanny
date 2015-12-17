package com.example.android.dannyjustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText getName= (EditText)findViewById(R.id.name_field);
        String nameValue = getName.getText().toString();

        EditText getCard = (EditText)findViewById(R.id.creditcard_field);

        String cardValue = getCard.getText().toString();

        EditText getExpiration = (EditText)findViewById(R.id.expiration_field);

        String expirationValue = getExpiration.getText().toString();

        CheckBox whippedCreamCheckbox= (CheckBox)findViewById(R.id.whipped_cream_checkbox);
       boolean hasWhippedCream= whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();

        CheckBox sprinklesCheckbox= (CheckBox)findViewById(R.id.sprinkles_checkbox);
        boolean hasSprinkles= sprinklesCheckbox.isChecked();

        CheckBox creamCheckBox=(CheckBox)findViewById(R.id.cream_checkbox);
        boolean hasCream=creamCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate,hasSprinkles,hasCream);
        String priceMessage = createdOrderSummary(nameValue,price, hasWhippedCream, hasChocolate,hasSprinkles,hasCream,cardValue, expirationValue);
        displayMessage(priceMessage); Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }}

        private int calculatePrice (boolean addWhippedcream, boolean addChocolate, boolean addSprinkles, boolean addCream){
       int basePrice=5;

        if(addWhippedcream){basePrice=basePrice +1;}
            if (addChocolate){basePrice=basePrice+2;}
            if (addSprinkles){basePrice=basePrice+1;}
            if (addCream){basePrice=basePrice+3;}
        return quantity*basePrice;

   }
    //plus button
    public void increment(View view) {
        quantity = quantity+1;
        display(quantity);

    }
// minus button
    public void decrement(View view) {
       if (quantity==1){ Toast.makeText(this, "You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
       return;}
        quantity=quantity-1;
        display(quantity);

    }
     private String createdOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate,boolean addSprinkles, boolean addCream, String cardNumber, String expiration){
         String priceMessage ="\nName:"+name;
         priceMessage+="\nAdd whipped cream? "+ addWhippedCream;
         priceMessage+="\nAdd chocolate? "+ addChocolate;
         priceMessage+="\nAdd sprinkles? "+ addSprinkles;
         priceMessage+="\nAdd cream? "+ addCream;
         priceMessage= priceMessage+"\nQuantity"+quantity;
         priceMessage= priceMessage+ "\nTotal: $"+price;
         priceMessage = priceMessage + "\n\nCredit Card: " + cardNumber;
         priceMessage = priceMessage + "\nExp. Date: " + expiration;
         priceMessage= priceMessage+ "\nThank you!";
         displayMessage(priceMessage);
         return priceMessage;
     }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}

