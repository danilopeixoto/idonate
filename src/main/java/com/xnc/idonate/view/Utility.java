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
    
    static OrganType comboBoxIndexToOrganType(int index) {
        switch (index) {
            case 0: return OrganType.Bone;
            case 1: return OrganType.Cornea;
            case 2: return OrganType.Heart;
            case 3: return OrganType.Intestine;
            case 4: return OrganType.Kidneys;
            case 5: return OrganType.Liver;
            case 6: return OrganType.Lungs;
            case 7: return OrganType.Pancreas;
            case 8: return OrganType.Skin;
            case 9: return OrganType.Tendon;
            case 10: return OrganType.Vein;
            default: throw new IndexOutOfBoundsException();
        }
    }
}