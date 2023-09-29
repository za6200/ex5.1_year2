package com.example.ex5_year2;

import static java.lang.Math.pow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * This is the main activity for the Android app.
     * Author: Ziv Ankri (ziv.ankri@example.com)
     * Version: 7.3.1
     * Since: 29/9/2023
     */
    AlertDialog.Builder adb;
    ListView serie_list;
    TextView X1Tv;
    TextView dTv;
    TextView nTv;
    TextView SnTv;
    Button button, newInputField, noRestartField, restart1Field;
    LinearLayout my_dialog;
    EditText serieTypeAnswerField, firstNumAnswerField, progresorAnswerField;
    String serieTypeAnswer;
    String firstNumAnswer;
    String progresorAnswer;
    Double numbers[];
    AlertDialog dialog; // Use AlertDialog as a member variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Called when the activity is first created.
         * @param savedInstanceState The saved instance state.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serie_list = findViewById(R.id.serie_list);
        X1Tv = findViewById(R.id.X1Tv);
        dTv = findViewById(R.id.dTv);
        nTv = findViewById(R.id.nTv);
        SnTv = findViewById(R.id.SnTv);
        button = findViewById(R.id.button);
        makeSerie();
    }

    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        /**
         * Click listener for the dialog buttons.
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                serieTypeAnswer = serieTypeAnswerField.getText().toString();
                firstNumAnswer = firstNumAnswerField.getText().toString();
                progresorAnswer = progresorAnswerField.getText().toString();
                Toast.makeText(MainActivity.this, "serie type: " + serieTypeAnswer + " first number: " + firstNumAnswer + " progresor: " + progresorAnswer, Toast.LENGTH_SHORT).show();

                makeSerie();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel();
            }
        }
    };

    public void clicked(View view) {
        /**
         * Handles the button click to open the input dialog.
         * @param view The view clicked.
         */
        my_dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.my_dialog, null);
        serieTypeAnswerField = my_dialog.findViewById(R.id.serieTypeAnswer);
        firstNumAnswerField = my_dialog.findViewById(R.id.firstNumAnswer);
        progresorAnswerField = my_dialog.findViewById(R.id.progresorAnswer);
        newInputField = my_dialog.findViewById(R.id.newInput);
        noRestartField = my_dialog.findViewById(R.id.noRestart);
        restart1Field = my_dialog.findViewById(R.id.restart1);

        adb = new AlertDialog.Builder(this);

        adb.setView(my_dialog);
        adb.setTitle("serie info");
        adb.setMessage("Please enter serie type(geometric/math), first num, progresor: ");
        adb.setPositiveButton("Enter", myclick);
        adb.setNegativeButton("Cancel", myclick);
        dialog = adb.create(); // Create the dialog here

        // Show the dialog
        dialog.show();
    }

    public void newInput(View view) {
        /**
         * Resets input fields.
         * @param view The view clicked.
         */
        serieTypeAnswerField.setText("");
        firstNumAnswerField.setText("");
        progresorAnswerField.setText("");
    }

    public void noRestart(View view) {
        /**
         * Dismisses the input dialog.
         * @param view The view clicked.
         */
        // Dismiss the dialog if it's showing
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void restart1(View view) {
        /**
         * Restarts and clears input fields and text views.
         * @param view The view clicked.
         */
        serieTypeAnswerField.setText("");
        firstNumAnswerField.setText("");
        progresorAnswerField.setText("");

        X1Tv.setText(""); // Set the text to "0"
        dTv.setText(""); // Set the text to "0"
        nTv.setText(""); // Set the text to "0"
        SnTv.setText(""); // Set the text to "0"

        // Clear the ListView by creating a new empty array adapter
        ArrayAdapter<Double> emptyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new Double[0]);
        serie_list.setAdapter(emptyAdapter);
    }

    public void makeSerie() {
        /**
         * Generates the series and updates the list view.
         */
        numbers = new Double[20];
        double firstNum = 0.0;
        double progresor = 0.0;

        if (firstNumAnswer != null && !firstNumAnswer.isEmpty()) {
            firstNum = Double.parseDouble(firstNumAnswer);
        }

        if (progresorAnswer != null && !progresorAnswer.isEmpty()) {
            progresor = Double.parseDouble(progresorAnswer);
        }

        numbers[0] = firstNum;

        for (int i = 1; i < 20; i++) {
            if (serieTypeAnswer != null && serieTypeAnswer.equals("geometric")) {
                numbers[i] = firstNum * Math.pow(progresor, i);
            } else {
                numbers[i] = firstNum + i * progresor;
            }
        }

        ArrayAdapter<Double> adp = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numbers);
        serie_list.setAdapter(adp);
        serie_list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Handles item click on the list view.
         * @param parent   The adapter view where the click happened.
         * @param view     The view within the adapter view that was clicked.
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        X1Tv.setText(firstNumAnswer);
        dTv.setText(progresorAnswer);

        int itemNumber = position + 1;
        nTv.setText(String.valueOf(itemNumber));

        double sum = 0;
        for (int i = 0; i < itemNumber; i++) {
            sum += numbers[i];
        }
        SnTv.setText(String.valueOf(sum));
    }
}
