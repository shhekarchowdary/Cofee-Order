package com.arr.cofeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    Spinner mSpinner;
    TextView mPriceTextView,mCartValueTextView;
    EditText mQuantityEditText;
    Button mAddToCartButton,mPlaceOrder;

    ArrayList<Product>mProducts = new ArrayList<>();
    String[] productNames = new String[6];
    Product mProduct;
    double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        productNames[0] = "Coke";
        productNames[1] = "Book";
        productNames[2] = "Coffee";
        productNames[3] = "Lays";
        productNames[4] = "Pen";
        productNames[5] = "Mask";

        mProducts.add(new Product("Coke",25.0,100));
        mProducts.add(new Product("Book",20.0,50));
        mProducts.add(new Product("Coffee",10.0,75));
        mProducts.add(new Product("Lays",30.0,150));
        mProducts.add(new Product("Pen",10.0,200));
        mProducts.add(new Product("Mask",10.0,100));

        mSpinner = findViewById(R.id.spinnerProduct);
        mPriceTextView = findViewById(R.id.txtPrice);
        mCartValueTextView = findViewById(R.id.txtTotalPrice);
        mQuantityEditText = findViewById(R.id.etxtQuantity);
        mAddToCartButton = findViewById(R.id.btnAddtoCart);
        mPlaceOrder = findViewById(R.id.btnPlaceOrder);


        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,productNames);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(this);

        mAddToCartButton.setOnClickListener(this);
        mPlaceOrder.setOnClickListener(this);

    }

    final int addId = R.id.btnAddtoCart;
    final int placeId = R.id.btnPlaceOrder;

    @Override
    public void onClick(View view) {
        //Button b = (Button) view;
        switch (view.getId()){
            case addId:
                if(!mQuantityEditText.getText().toString().isEmpty()){
                    int quantityEntered = Integer.parseInt(mQuantityEditText.getText().toString());
                    if(quantityEntered <= mProduct.getStock()){
                        if(quantityEntered > 10){
                            double pPrice = mProduct.getPrice()-mProduct.getPrice()*0.05;
                            totalPrice += quantityEntered*pPrice;
                        }else
                            totalPrice += quantityEntered*mProduct.getPrice();
                        mProduct.setStock(mProduct.getStock()-quantityEntered);
                        mCartValueTextView.setText(String.format("%.2f",totalPrice));

                    }else{
                        if(mProduct.getStock() == 0){
                            Toast.makeText(this,"No Stock Please Choose other Product",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(this,"Less Stock Available Please Choose other Quantity",Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(this,"Please Enter Quantity",Toast.LENGTH_LONG).show();
                }
                break;
            case placeId:
                if(!mCartValueTextView.getText().toString().isEmpty()){
                    Toast.makeText(this,"Order Placed Successfully",Toast.LENGTH_LONG).show();
                    mCartValueTextView.setText("");
                    totalPrice = 0;
                    mQuantityEditText.setText("");
                }else{
                    Toast.makeText(this,"Your Cart is Empty - please Add some Products",Toast.LENGTH_LONG).show();
                }
                break;
            default:

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String prodSelected = productNames[i];

        for(Product prod:mProducts)
            if(prod.getName().equals(prodSelected)){
                mProduct = prod;
                break;
            }
        mPriceTextView.setText(String.format("%.2f",mProduct.getPrice()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mProduct = mProducts.get(0);
        mPriceTextView.setText(String.format("%.2f",mProduct.getPrice()));
    }
}