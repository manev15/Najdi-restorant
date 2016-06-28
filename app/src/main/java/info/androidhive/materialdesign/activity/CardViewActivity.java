package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.R;

public class CardViewActivity extends Activity {

    TextView bankomatName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardview_activity);
        bankomatName = (TextView)findViewById(R.id.bankomat_name);
      //  personAge = (TextView)findViewById(R.id.person_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

//        personName.setText("Emma Wilson");
//        personAge.setText("23 years old");

        //slikata
    personPhoto.setImageResource(R.drawable.ic_action_search);
    }
}
