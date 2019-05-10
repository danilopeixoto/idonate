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

package idonate.controller;

import idonate.model.Blood;
import idonate.model.Person;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonAccessor extends Accessor {
    private enum PersonAttributes {
        None,
        CPF,
        Name,
        Address,
        Phone,
        Email,
        Age,
        Sex,
        Weight,
        BloodType,
        MedicalConditions,
        HospitalID
    }
    
    public PersonAccessor(Database database) {
        super(database);
    }
    
    public boolean add(Person person) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        StringBuilder values = new StringBuilder();
        
        values.append("(\"");
        values.append(person.getCPF());
        values.append("\",\"");
        values.append(person.getName());
        values.append("\",\"");
        values.append(person.getAddress());
        values.append("\",\"");
        values.append(person.getPhone());
        values.append("\",\"");
        values.append(person.getEmail());
        values.append("\",\"");
        values.append(person.getAge());
        values.append("\",\"");
        values.append(person.getSex().ordinal());
        values.append("\",\"");
        values.append(person.getWeight());
        values.append("\",\"");
        values.append(person.getBloodType().ordinal());
        values.append("\",\"");
        values.append(person.getMedicalConditions());
        values.append("\",\"");
        values.append(person.getHospitalID());
        values.append("\");");
        
        boolean status = statement.execute(
                "INSERT INTO people(cpf, name, address, phone, email, age,"
                        + "sex, weight, blood_type, medical_conditions, hospital_id) "
                        + "VALUES " + values.toString());
        
        database.disconnect();
        
        return status;
    }
    public boolean remove(String cpf) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        boolean status = statement.execute(
                "DELETE FROM people WHERE id = \"" + cpf + "\";");
        
        database.disconnect();
        
        return status;
    }
    public boolean has(String cpf) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM people WHERE id = \"" + cpf + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return false;
        }
        
        String personCPF = result.getString(PersonAttributes.CPF.ordinal());
        
        database.disconnect();
        
        return personCPF != null;
    }
    public Person get(String cpf) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM people WHERE id = \"" + cpf + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return null;
        }
        
        String personCPF = result.getString(PersonAttributes.CPF.ordinal());
        
        if (personCPF == null) {
            database.disconnect();
            return null;
        }
        
        Person person = new Person(
                personCPF,
                result.getString(PersonAttributes.Name.ordinal()),
                result.getString(PersonAttributes.Address.ordinal()),
                result.getString(PersonAttributes.Phone.ordinal()),
                result.getString(PersonAttributes.Email.ordinal()),
                result.getInt(PersonAttributes.Age.ordinal()),
                Person.Sex.values()[result.getInt(PersonAttributes.Sex.ordinal())],
                result.getFloat(PersonAttributes.Weight.ordinal()),
                Blood.BloodType.values()[result.getInt(PersonAttributes.BloodType.ordinal())],
                result.getString(PersonAttributes.MedicalConditions.ordinal()),
                result.getString(PersonAttributes.HospitalID.ordinal()));
        
        database.disconnect();
        
        return person;
    }
}