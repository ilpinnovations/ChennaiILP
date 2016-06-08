package info.androidhive.customlistviewvolley;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressWarnings("unused")

public class rating2 extends Activity {
    // Log tag
    private static final String TAG = rating2.class.getSimpleName();

    // Movies json url

    int rating = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating);

        View footerLayout = findViewById(R.id.include03);

        // Fetching the textview declared in footer.xml
        final ImageButton star1 = (ImageButton) footerLayout.findViewById(R.id.imageButton1);
        final ImageButton star2 = (ImageButton) footerLayout.findViewById(R.id.ImageButton01);
        final ImageButton star3 = (ImageButton) footerLayout.findViewById(R.id.ImageButton02);
        final ImageButton star4 = (ImageButton) footerLayout.findViewById(R.id.ImageButton03);
        final ImageButton star5 = (ImageButton) footerLayout.findViewById(R.id.ImageButton04);

        rating = 1;

        star1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                star1.setBackground(getResources().getDrawable(R.drawable.star));
                star2.setBackground(getResources().getDrawable(R.drawable.starempty));
                star3.setBackground(getResources().getDrawable(R.drawable.starempty));
                star4.setBackground(getResources().getDrawable(R.drawable.starempty));
                star5.setBackground(getResources().getDrawable(R.drawable.starempty));

                rating = 1;
            }
        });

        star2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                star1.setBackground(getResources().getDrawable(R.drawable.star));
                star2.setBackground(getResources().getDrawable(R.drawable.star));
                star3.setBackground(getResources().getDrawable(R.drawable.starempty));
                star4.setBackground(getResources().getDrawable(R.drawable.starempty));
                star5.setBackground(getResources().getDrawable(R.drawable.starempty));

                rating = 2;

            }
        });

        star3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                star1.setBackground(getResources().getDrawable(R.drawable.star));
                star2.setBackground(getResources().getDrawable(R.drawable.star));
                star3.setBackground(getResources().getDrawable(R.drawable.star));
                star4.setBackground(getResources().getDrawable(R.drawable.starempty));
                star5.setBackground(getResources().getDrawable(R.drawable.starempty));

                rating = 3;
            }
        });

        star4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                star1.setBackground(getResources().getDrawable(R.drawable.star));
                star2.setBackground(getResources().getDrawable(R.drawable.star));
                star3.setBackground(getResources().getDrawable(R.drawable.star));
                star4.setBackground(getResources().getDrawable(R.drawable.star));
                star5.setBackground(getResources().getDrawable(R.drawable.starempty));

                rating = 4;
            }
        });

        star5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                star1.setBackground(getResources().getDrawable(R.drawable.star));
                star2.setBackground(getResources().getDrawable(R.drawable.star));
                star3.setBackground(getResources().getDrawable(R.drawable.star));
                star4.setBackground(getResources().getDrawable(R.drawable.star));
                star5.setBackground(getResources().getDrawable(R.drawable.star));

                rating = 5;
            }
        });

        listner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void comment(View v)
    {
        EditText e1 = (EditText)findViewById(R.id.editText1);

        new updatecomment(this).execute(e1.getText().toString().trim(), String.valueOf(rating), list.sno);
        pd = ProgressDialog.show(rating2.this, "", "");
        pd.setContentView(R.layout.progress);
        pd.setCancelable(false);
        pd.show();
    }

    ProgressDialog pd;

    public static TextView result = null;

    void listner()
    {
        result = new TextView(this);
        result.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                pd.cancel();
                try
                {
                    //Toast.makeText(getApplicationContext(), String.valueOf(result.getText()), Toast.LENGTH_SHORT).show();
                    if(result.getText().toString().trim().equals("success"))
                    {
                        final Dialog dialog = new Dialog(rating2.this);
                        dialog.setContentView(R.layout.customerror);
                        dialog.setTitle("");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
                        // dialog.getWindow().setTitleColor(R.color.titlecolor);


                        // set the custom dialog components - text, image and button
                        TextView text = (TextView) dialog.findViewById(R.id.text);
                        text.setText("Posted Sucessfully");


                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    else if(result.getText().toString().trim().contains("Duplicate"))
                    {
                        final Dialog dialog = new Dialog(rating2.this);
                        dialog.setContentView(R.layout.customerror);
                        dialog.setTitle("");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
                        // dialog.getWindow().setTitleColor(R.color.titlecolor);


                        // set the custom dialog components - text, image and button
                        TextView text = (TextView) dialog.findViewById(R.id.text);
                        text.setText("You Already Posted Your Review");


                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    else if(result.getText().toString().trim().contains("Exception"))
                    {
                        final Dialog dialog = new Dialog(rating2.this);
                        dialog.setContentView(R.layout.customerror);
                        dialog.setTitle("");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
                        // dialog.getWindow().setTitleColor(R.color.titlecolor);


                        // set the custom dialog components - text, image and button
                        TextView text = (TextView) dialog.findViewById(R.id.text);
                        text.setText("No Internet Access");


                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    else
                    {

                    }
                }
                catch(Exception e)
                {
                    final Dialog dialog = new Dialog(rating2.this);
                    dialog.setContentView(R.layout.customerror);
                    dialog.setTitle("");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
                    // dialog.getWindow().setTitleColor(R.color.titlecolor);


                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText("Internet Not Available");


                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                //	startActivity(i);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }

}
