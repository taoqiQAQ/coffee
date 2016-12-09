package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 在下面添加您的包。 软件包名称可以在项目的AndroidManifest.xml文件中找到。
   *这是我们的示例使用的包名称：
   * <p>
   * package com.example.android.justjava;
   * /

 / **
   *此应用程序显示订购咖啡的订单。
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //显示提示框 不能超过100杯
            Toast.makeText(this, "不能超过100杯", Toast.LENGTH_SHORT).show();
            //提前退出此方法，因为没有什么要做
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //显示提示框 不能少于1杯
            Toast.makeText(this, "不能少于1杯", Toast.LENGTH_SHORT).show();
            //提前退出此方法，因为没有什么要做
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //增加用户名字
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);//日志
        //确定用户是否想要whipped_cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has whipped cream:" + hasWhippedCream);//日志
        //确定用户是否想要Chocolate
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
       // displayMessage(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream 用户是否要奶油
     * @param addChocolate    用户是否要巧克力
     * @return tatal price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //一杯咖啡的价格
        int basePrice = 5;
        //需要奶油加一元
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //需要巧克力加两元
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        //杯数 * 价格
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     *
     * @param name            客户
     * @param price           的订单
     * @param addWhippedCream 要不要奶油
     * @param addChocolate    要不要巧克力
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd Whipped Cream: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage = priceMessage + "\nQuantity " + quantity;
        priceMessage = priceMessage + "\nTltal: $" + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
