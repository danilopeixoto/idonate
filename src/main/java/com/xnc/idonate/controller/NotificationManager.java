package com.xnc.idonate.controller;

import com.xnc.idonate.model.Person;
import com.xnc.idonate.model.Resource;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Heitor
 */
public class NotificationManager {
    private final String accountSid;
    private final String authToken;
    private final PhoneNumber sender;
    
    public NotificationManager(final String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        this.accountSid = lines.get(0);
        this.authToken = lines.get(1);
        this.sender = this.properNumber(lines.get(2));
    }
    
    public NotificationManager(
            final String accountSid,
            final String authToken,
            final PhoneNumber sender) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.sender = sender;
    }
    
    public void sendDefaultSmsToDonor(
            final Person person,
            final Resource resource) {
        this.sendSms(
                person,
                "O seu " + resource.getDescription() + " foi doado! Yay!");
    }
    
    public void sendSms(
            final Person person,
            final String message) {
        Twilio.init(this.accountSid, this.authToken);

        final Message smsMessage = Message.creator(
                this.properNumber(person.getPhone()),
                this.sender,
                message).create();
    }
    
    private PhoneNumber properNumber(final String number) {
        return new PhoneNumber("+55" + number.replace("( -)", ""));
    }
}
