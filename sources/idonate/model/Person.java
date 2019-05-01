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

package idonate.model;

import idonate.model.Blood.BloodType;

public class Person {
    public enum Sex {
        None,
        Female,
        Male
    }
    
    private String cpf;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int age;
    private Sex sex;
    private float weight;
    private BloodType bloodType;
    private String medicalConditions;
    private String hospitalID;
    
    public Person() {}
    public Person(
            String cpf, String name, String address, String phone, String email,
            int age, Sex sex, float weight, BloodType bloodType,
            String medicalConditions, String hospitalID) {
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.weight = weight;
        this.bloodType = bloodType;
        this.medicalConditions = medicalConditions;
        this.hospitalID = hospitalID;
    }
    
    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
    
    public String getCPF() {
        return cpf;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public Sex getSex() {
        return sex;
    }
    public float getWeight() {
        return weight;
    }
    public BloodType getBloodType() {
        return bloodType;
    }
    public String getMedicalConditions() {
        return medicalConditions;
    }
    public String getHospitalID() {
        return hospitalID;
    }
}