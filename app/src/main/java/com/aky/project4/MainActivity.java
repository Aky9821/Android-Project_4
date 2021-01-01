package com.aky.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aky.project4.Kamal.kamalBuild;
import com.aky.project4.Kamal.kamalInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner convertFrom, convertTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convertFrom = findViewById(R.id.convertFrom);
        convertTo = findViewById(R.id.convertTo);
        String[] dropDownList = {"USD", "EUR", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON",
                "SEK", "CHF", "ISK", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY",
                "HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertFrom.setAdapter(adapter);
        convertTo.setAdapter(adapter);
        convertTo.setSelection(1);
    }

    public void clickFunction(View v) {

        EditText enteredCurrency = findViewById(R.id.enteredCurrency);

        if (!enteredCurrency.getText().toString().equals("")) {

            double input = Double.parseDouble(enteredCurrency.getText().toString());

            Retrofit z = kamalBuild.getRetrofitInstance();
            kamalInterface retrofitInterface = z.create(kamalInterface.class);

            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFrom.getSelectedItem().toString());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    JsonObject rates = res.getAsJsonObject("conversion_rates");
                    double currency = Double.parseDouble(enteredCurrency.getText().toString());
                    if (rates.get(convertTo.getSelectedItem().toString()).toString() != null) {
                        double x = Double.valueOf(rates.get(convertTo.getSelectedItem().toString()).toString());
                        final double output = x * input;
                        final String outputString = output + "";
                        TextView convertedCurrency = findViewById(R.id.convertedCurrency);
                        convertedCurrency.setText(outputString);
                    } else
                        Toast.makeText(MainActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(this, "Please enter value!", Toast.LENGTH_SHORT).show();
        }
    }

    public void exchangeFunction(View v) {
        convertFrom = findViewById(R.id.convertFrom);
        convertTo = findViewById(R.id.convertTo);
        int from = (int) convertFrom.getSelectedItemId();
        int to = (int) convertTo.getSelectedItemId();
        convertTo.setSelection(from);
        convertFrom.setSelection(to);
        clickFunction(v);
    }
}