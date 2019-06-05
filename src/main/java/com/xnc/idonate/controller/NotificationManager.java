package com.xnc.idonate.controller;

import com.xnc.idonate.model.Person;
import com.xnc.idonate.model.Resource;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Heitor
 */
public class NotificationManager {
    private final String accountSid;
    private final String authToken;
    private final PhoneNumber sender;
    
    public NotificationManager() throws IOException {
        final String path = this.getClass().getClassLoader().getResource("services.auth").getPath();
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        this.accountSid = br.readLine();
        this.authToken = br.readLine();
        this.sender = this.properNumber(br.readLine());
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
                "O seu " + resource.getDescription() + " foi doado!");
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
