package fi.ariokkon.jalog;

import io.github.JalogTeam.jalog.Jalog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.io.IOException;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static Jalog myJalog = new Jalog();

    static String[] replaceprogram = {
            "replace(E1, [E1|L1], E2, [E2|L1]).",
            "",
            "replace(E1, [H|L1], E2, [H|L2]) :-",
            "  replace(E1, L1, E2, L2).",
            "test(X) :-",
            "  replace(2,[1,2,3,4],5,X).",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Jalog.Term answer = Jalog.open();
        String result;

        super.onCreate(savedInstanceState);

        myJalog.consult_stringlist(replaceprogram, "replace");

        try {
            if (myJalog.call("test", answer))
            { // Getting the solution
                result = "success: " + answer;
            } else { // fail: Report solution not found
                result = "fail";
            }
        } catch (Jalog.Exit e) { // Report exception
            result = "exit: " + e.status;
        }




        setContentView(R.layout.activity_main);
        final AlertDialog.Builder myBox = new AlertDialog.Builder(this)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton(android.R.string.ok, null);

        final TextView tekstikentta = (TextView) findViewById(R.id.tekstikentta);
        tekstikentta.append("\n" + result, 0, result.length()+1);


        EditText intext = (EditText) findViewById(R.id.intext);
        intext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do something
                    //myBox.show();

                    String teksti = v.getText().toString();
                    tekstikentta.append("\nIntegroidaan", 0, 13);
                    tekstikentta.append("\n", 0, 1);
                    tekstikentta.append(teksti, 0, teksti.length());
                    v.setText("");

                    String string = "";
                    InputStream inputStream = null;
                    try {
                        inputStream = getAssets().open("koe.txt");
                        int size = inputStream.available();
                        byte[] buffer = new byte[size];
                        inputStream.read(buffer);
                        string = new String(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        string = e.toString();
                    }
                    tekstikentta.append("\n", 0, 1);
                    tekstikentta.append(string, 0, string.length());








                }
                return false;
            }
        });
    }
}


