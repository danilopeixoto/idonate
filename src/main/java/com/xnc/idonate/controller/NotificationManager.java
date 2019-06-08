package com.xnc.idonate.controller;

import com.xnc.idonate.model.Person;
import com.xnc.idonate.model.Resource;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class NotificationManager {
    private final String accountSid;
    private final String authToken;
    private final PhoneNumber sender;
    
    public NotificationManager(
            final String accountSid,
            final String accountPhone,
            final String authToken) {
        this.accountSid = accountSid;
        this.sender = this.properNumber(accountPhone);
        this.authToken = authToken;
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