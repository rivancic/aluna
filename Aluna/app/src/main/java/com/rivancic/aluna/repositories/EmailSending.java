package com.rivancic.aluna.repositories;

import com.rivancic.aluna.models.EmailContent;

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

    public void send(EmailContent emailContent) {

        final Mailin http = new Mailin("https://api.sendinblue.com/v2.0", Ko + Po);
        Map<String, String> to = new HashMap<>();
        to.put("renatoivancic@gmail.com", "rivancic");
        final Map<String, Object> data = new HashMap<>();
        data.put("to", to);
        data.put("from", new String[]{emailContent.email});
        data.put("subject", "Contact from Android Alunaapp");
        data.put("html", "<p>name: "+ emailContent.name +"</p>" +

                "<p>phone: "+emailContent.phone+"</p>" +
                "<p>message: "+emailContent.message+"</p>");
        Thread downloadThread = new Thread() {
            public void run() {

                String str = http.send_email(data);
                Timber.i("Sent inside, status: %s", str);
            }
        };
        downloadThread.start();
    }
}
