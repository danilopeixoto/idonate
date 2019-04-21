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

package idonate.util;

import java.sql.Date;

public abstract class Resource {
    private int id;
    private String donorCPF;
    private Date donationDate;
    private String description;
    private String acceptorCPF;
    private Date acceptationDate;
    
    public Resource() {}
    public Resource(
            int id, String donorCPF, Date donationDate, String description,
            String acceptorCPF, Date acceptationDate) {
        this.id = id;
        this.donorCPF = donorCPF;
        this.donationDate = donationDate;
        this.description = description;
        this.acceptorCPF = donorCPF;
        this.acceptationDate = donationDate;
    }

    public void setID(int id) {
        this.id = id;
    }
    public void setDonorCPF(String donorCPF) {
        this.donorCPF = donorCPF;
    }
    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAcceptorCPF(String acceptorCPF) {
        this.acceptorCPF = acceptorCPF;
    }
    public void setAcceptationDate(Date acceptationDate) {
        this.acceptationDate = acceptationDate;
    }

    public int getID() {
        return id;
    }
    public String getDonorCPF() {
        return donorCPF;
    }
    public Date getDonationDate() {
        return donationDate;
    }
    public String getDescription() {
        return description;
    }
    public String getAcceptorCPF() {
        return acceptorCPF;
    }
    public Date getAcceptationDate() {
        return acceptationDate;
    }
}