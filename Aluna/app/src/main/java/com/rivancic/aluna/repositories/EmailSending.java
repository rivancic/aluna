package com.rivancic.aluna.repositories;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Write something about sending blue.
 * Created by rivancic on 29/12/2016.
 */
public class EmailSending {

    private static final String Ko = "bfEFw1jVQt";
    private static final String Po = "IkYqO5";

    public void send() {

        final Mailin http = new Mailin("https://api.sendinblue.com/v2.0", Ko + Po);
        Map<String, String> to = new HashMap<>();
        to.put("renatoivancic@gmail.com", "rivancic");
        final Map<String, Object> data = new HashMap<>();
        data.put("to", to);
        data.put("from", new String[]{"renatoivancic@gmail.com", "rivancic"});
        data.put("subject", "My subject");
        data.put("html", "This is the <h1>HTML</h1>");
        Thread downloadThread = new Thread() {
            public void run() {

                String str = http.send_email(data);
                Timber.i("Sent inside");
            }
        };
        downloadThread.start();
    }
}
