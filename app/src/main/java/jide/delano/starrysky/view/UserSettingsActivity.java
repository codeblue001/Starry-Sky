package jide.delano.starrysky.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import jide.delano.starrysky.R;
import static jide.delano.starrysky.view.UserSelectionUtil.UNIT;
import static jide.delano.starrysky.view.UserSelectionUtil.ZIP;
import static jide.delano.starrysky.view.UserSelectionUtil.verifyZip;


public class UserSettingsActivity extends AppCompatActivity {
    private Button btnStart;
    private TextView tvZip;
    private TextView tvUnit;
    private UserSelectionDialog userSelectionDialog;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        btnStart = findViewById(R.id.btn_start);
        tvZip = findViewById(R.id.tv_zip);
        tvUnit = findViewById(R.id.tv_unit);
        UserSelectionUtil.INPUT_UNIT = null;
        UserSelectionUtil.INPUT_ZIPCODE = null;

        //video
        mVideoView = findViewById(R.id.vv_intro_video);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rain);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        //zip and unit check conditional
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectionDialog = new UserSelectionDialog(UserSettingsActivity.this);
                userSelectionDialog.show();
                userSelectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String selectedItem = userSelectionDialog.spinnerUnit.getSelectedItem().toString();
                        if (verifyZip(userSelectionDialog.etZip.getText().toString()) && selectedItem != null) {
                            UserSelectionUtil.INPUT_UNIT = selectedItem;
                            tvUnit.setText(selectedItem);
                            UserSelectionUtil.INPUT_ZIPCODE = userSelectionDialog.etZip.getText().toString();
                            tvZip.setText(UserSelectionUtil.INPUT_ZIPCODE);
                            if (UserSelectionUtil.INPUT_ZIPCODE != null && UserSelectionUtil.INPUT_UNIT != null) {
                                startWeatherDetailActivity();
                            }
                        } else {
                            Toast.makeText(UserSettingsActivity.this, "Please enter a zip code and select a unit", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //Start Weather Activity Intent
    private void startWeatherDetailActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ZIP, UserSelectionUtil.INPUT_ZIPCODE);
        if (UserSelectionUtil.INPUT_UNIT.equals(getResources().getString(R.string.fahrenheit)))
            intent.putExtra(UNIT, "imperial");
        else if (UserSelectionUtil.INPUT_UNIT.equals(getResources().getString(R.string.celsius)))
            intent.putExtra(UNIT, "metric");
        startActivity(intent);
    }
}
