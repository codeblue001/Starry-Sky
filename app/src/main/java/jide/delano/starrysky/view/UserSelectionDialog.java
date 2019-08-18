package jide.delano.starrysky.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import jide.delano.starrysky.R;

public class UserSelectionDialog extends Dialog {
    EditText etZip;
    Spinner spinnerUnit;
    Button btnSubmit;

    public UserSelectionDialog(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.user_selection_dialog, null);
        this.setContentView(view);

        etZip = view.findViewById(R.id.et_zip);
        btnSubmit = view.findViewById(R.id.btn_submit);
        spinnerUnit = view.findViewById(R.id.spinner_unit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSelectionDialog.this.dismiss();

            }
        });
    }
}
