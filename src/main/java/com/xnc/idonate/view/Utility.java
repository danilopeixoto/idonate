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

package com.xnc.idonate.view;

import com.xnc.idonate.model.Blood.BloodType;
import com.xnc.idonate.model.Organ.OrganType;

class Utility {
    static String organTypeToString(OrganType organType) {
        switch (organType) {
            case Heart: return "Coração";
            case Lungs: return "Pulmões";
            case Liver: return "Fígado";
            case Kidneys: return "Rins";
            case Pancreas: return "Pâncreas";
            case Intestine: return "Intestino";
            case Skin: return "Pele";
            case Bone: return "Osso";
            case Cornea: return "Córnea";
            case Vein: return "Veia";
            case Tendon: return "Tendão";
            default: return "None";
        }
    }
    
    static String bloodTypeToString(BloodType bloodType) {
        switch (bloodType) {
            case APlus: return "A+";
            case BPlus: return "B+";
            case OPlus: return "O+";
            case ABPlus: return "AB+";
            case AMinus: return "A-";
            case BMinus: return "B-";
            case OMinus: return "O-";
            case ABMinus: return "AB-";
            default: return "None";
        }
    }
    
    static BloodType comboBoxIndexToBloodType(int index) {
        switch (index) {
            case 0: return BloodType.APlus;
            case 1: return BloodType.AMinus;
            case 2: return BloodType.ABPlus;
            case 3: return BloodType.ABMinus;
            case 4: return BloodType.BPlus;
            case 5: return BloodType.BMinus;
            case 6: return BloodType.OPlus;
            case 7: return BloodType.OMinus;
            default: throw new IndexOutOfBoundsException();
        }
    }
    
    static int bloodTypeToComboBoxIndex(BloodType type) {
        switch (type) {
            case APlus: return 0;
            case AMinus: return 1;
            case ABPlus: return 2;
            case ABMinus: return 3;
            case BPlus: return 4;
            case BMinus: return 5;
            case OPlus: return 6;
            case OMinus: return 7;
            default: throw new IndexOutOfBoundsException();
        }
    }
    
    static OrganType comboBoxIndexToOrganType(int index) {
        switch (index) {
            case 0: return OrganType.Heart;
            case 1: return OrganType.Cornea;
            case 2: return OrganType.Liver;
            case 3: return OrganType.Intestine;
            case 4: return OrganType.Bone;
            case 5: return OrganType.Pancreas;
            case 6: return OrganType.Skin;
            case 7: return OrganType.Lungs;
            case 8: return OrganType.Kidneys;
            case 9: return OrganType.Tendon;
            case 10: return OrganType.Vein;
            default: throw new IndexOutOfBoundsException();
        }
    }
    
    static int organTypeToComboBoxIndex(OrganType type) {
        switch (type) {
            case Heart: return 0;
            case Cornea: return 1;
            case Liver: return 2;
            case Intestine: return 3;
            case Bone: return 4;
            case Pancreas: return 5;
            case Skin: return 6;
            case Lungs: return 7;
            case Kidneys: return 8;
            case Tendon: return 9;
            case Vein: return 10;
            default: throw new IndexOutOfBoundsException();
        }
    }
}