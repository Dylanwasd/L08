package sg.edu.rp.c346.lesson8;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText weight;
    EditText height;
    Button calculate;
    Button reset;
    TextView date;
    TextView BMI;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight=findViewById(R.id.editWeight);
        height=findViewById(R.id.editHeight);
        calculate=findViewById(R.id.buttonCal);
        reset=findViewById(R.id.buttonReset);
        date=findViewById(R.id.textCalcDate);
        BMI=findViewById(R.id.textCalcBMI);
        result=findViewById(R.id.textResult);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float Height = Float.parseFloat(height.getText().toString());
                float  Weight= Float.parseFloat(weight.getText().toString());

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor preEdit = prefs.edit();
                preEdit.putFloat("Height", Height);
                preEdit.putFloat("Weight", Weight);
                preEdit.commit();

                float bmi=Weight/(Height*Height);
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                String str1=String.format("%.3f",bmi);
                date.setText("Last Calculated Date: "+ datetime);
                System.out.println(str1);
                BMI.setText("Last Calculated BMI: "+str1);


                if (bmi<18.5){
                    result.setText("You are underweight");

                }else if(bmi<24.9){
                    result.setText("Your BMI is normal");

                }else if(bmi<29.9){
                    result.setText("You are overweight");
                }else{
                    result.setText("You are obese");
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        Float Height = Float.parseFloat(height.getText().toString());
        float  Weight= Float.parseFloat(weight.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preEdit = prefs.edit();

        preEdit.putFloat("Height", Height);
        preEdit.putFloat("Weight", Weight);
        preEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Float Height= prefs.getFloat("Height",0);
        Float Weight= prefs.getFloat("Weight",0);

        float bmi=Weight/(Height*Height);
        Calendar now = Calendar.getInstance();
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        String str1=String.format("%.3f",bmi);
        date.setText("Last Calculated Date: "+ datetime);
        System.out.println(str1);
        BMI.setText("Last Calculated BMI: "+str1);

    }
}
