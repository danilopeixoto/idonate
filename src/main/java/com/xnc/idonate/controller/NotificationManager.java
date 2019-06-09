// Copyright (c) 2019, Bruno Caputo, Danilo Peixoto and Heitor Toledo.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, this
//   list of conditions and the following disclaimer.
//
// * Redistributions in binary form must reproduce the above copyright notice,
//   this list of conditions and the following disclaimer in the documentation
//   and/or other materials provided with the distribution.
//
// * Neither the name of the copyright holder nor the names of its
//   contributors may be used to endorse or promote products derived from
//   this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

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
                "O seu " + resource.toString() + " foi doado!");
    }
    
    public void sendSms(
            final Person person,
            final String message) {
        Twilio.init(this.accountSid, this.authToken);

        final Message smsMessage = Message.creator(
                this.properNumber("+55" + person.getPhone()),
                this.sender,
                message).create();
    }
    
    private PhoneNumber properNumber(final String number) {
        return new PhoneNumber(number.replace("( -)", ""));
    }
}