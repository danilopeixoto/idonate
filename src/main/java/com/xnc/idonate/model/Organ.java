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

public class Organ extends Resource {
    public enum OrganType {
        None,
        Heart,
        Lungs,
        Liver,
        Kidneys,
        Pancreas,
        Intestine,
        Skin,
        Bone,
        Cornea,
        Vein,
        Tendon
    }
    
    private OrganType organType;
    private float weight;
    
    public Organ() {
        super(ResourceType.Organ);
    }
    public Organ(
            int id, String donorCPF, Date donationDate, String description,
            OrganType organType, float weight,
            String acceptorCPF, Date acceptationDate) {
        super(id, donorCPF, donationDate, description,
                acceptorCPF, acceptationDate, ResourceType.Organ);
        
        this.organType = organType;
        this.weight = weight;
    }

    public void setType(OrganType organType) {
        this.organType = organType;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public OrganType getOrganType() {
        return organType;
    }
    public float getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        switch (this.organType) {
            case Heart: return "Coração";
            case Lungs: return "Pulmão";
            case Liver: return "Fígado";
            case Kidneys: return "Rins";
            case Pancreas: return "Pâncreas";
            case Intestine: return "Intestino";
            case Skin: return "Pele";
            case Bone: return "Osso";
            case Cornea: return "Córnea";
            case Vein: return "Veia";
            case Tendon: return "Tendão";
            default: throw new IndexOutOfBoundsException();
        }
    }
}