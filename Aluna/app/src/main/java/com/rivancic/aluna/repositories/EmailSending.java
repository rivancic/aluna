package com.rivancic.aluna.repositories;

import android.text.TextUtils;

import com.rivancic.aluna.models.EmailContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

/**
 * Write something about sending blue.
 * Created by rivancic on 29/12/2016.
 */
public class EmailSending {

    private static final String Ko = "bfEFw1jVQt";
    private static final String Po = "IkYqO5";
    private EmailContent emailContent;

    public enum ValidationError {

        NAME_REQUIRED, EMAIL_REQUIRED, MESSAGE_REQUIRED, EMAIL_NOT_VALID
    }

    public class EmailContentValidationException extends Exception {

        public List<ValidationError> getValidationErrors() {

            return validationErrors;
        }

        public void addValidationError(ValidationError validationErrors) {

            this.validationErrors.add(validationErrors);
        }
        List<ValidationError> validationErrors = new ArrayList<>();

        public boolean hasErrors() {
            return !validationErrors.isEmpty();
        }
    }

    public  void validateEmailContent() throws EmailContentValidationException {

        EmailContentValidationException emailContentValidationException = new EmailContentValidationException();

        if(TextUtils.isEmpty(emailContent.name)) {
            emailContentValidationException.addValidationError(ValidationError.NAME_REQUIRED);
        }
        if(TextUtils.isEmpty(emailContent.email)) {
            emailContentValidationException.addValidationError(ValidationError.EMAIL_REQUIRED);
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailContent.email).matches()) {
            emailContentValidationException.addValidationError(ValidationError.EMAIL_NOT_VALID);
        }
        if(TextUtils.isEmpty(emailContent.message)) {
            emailContentValidationException.addValidationError(ValidationError.MESSAGE_REQUIRED);
        }
        if(emailContentValidationException.hasErrors()) {
            throw  emailContentValidationException;
        }
    }

    public void send(EmailContent emailContent) throws EmailContentValidationException {

        this.emailContent = emailContent;
        validateEmailContent();
        sendEmailContent(emailContent);
    }

    private void sendEmailContent(EmailContent emailContent) {

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
