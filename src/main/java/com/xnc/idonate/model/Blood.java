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

package com.xnc.idonate.model;

import java.sql.Date;

public class Blood extends Resource {
    public enum BloodType {
        None,
        APlus,
        BPlus,
        OPlus,
        ABPlus,
        AMinus,
        BMinus,
        OMinus,
        ABMinus
    }
    
    private BloodType bloodType;
    private float volume;
    
    public Blood() {
        super(ResourceType.Blood);
    }
    public Blood(
            int id, String donorCPF, Date donationDate, String description,
            BloodType bloodType, float volume,
            String acceptorCPF, Date acceptationDate) {
        super(id, donorCPF, donationDate, description,
                acceptorCPF, acceptationDate, ResourceType.Blood);
        
        this.bloodType = bloodType;
        this.volume = volume;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }

    public BloodType getBloodType() {
        return bloodType;
    }
    public float getVolume() {
        return volume;
    }
    
    @Override
    public String toString() {
        switch (this.bloodType) {
            case APlus: return "Sangue A+";
            case BPlus: return "Sangue B+";
            case OPlus: return "Sangue O+";
            case ABPlus: return "Sangue AB+";
            case AMinus: return "Sangue A-";
            case BMinus: return "Sangue B-";
            case OMinus: return "Sangue O-";
            case ABMinus: return "Sangue AB-";
            default: throw new IndexOutOfBoundsException();
        }
    }
}