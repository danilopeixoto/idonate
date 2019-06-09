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

import com.xnc.idonate.model.Blood;
import com.xnc.idonate.model.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        preparedStatement = database.createPreparedStatement(
                "INSERT INTO people(cpf, name, address, phone, email, age,"
                + "sex, weight, blood_type, medical_conditions, hospital_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        preparedStatement.setString(1, person.getCPF());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getAddress());
        preparedStatement.setString(4, person.getPhone());
        preparedStatement.setString(5, person.getEmail());
        preparedStatement.setInt(6, person.getAge());
        preparedStatement.setInt(7, person.getSex().ordinal());
        preparedStatement.setFloat(8, person.getWeight());
        preparedStatement.setInt(9, person.getBloodType().ordinal());
        preparedStatement.setString(10, person.getMedicalConditions());
        preparedStatement.setString(11, person.getHospitalID());

        boolean status = preparedStatement.execute();

        database.disconnect();

        return status;
    }
    
    public boolean update(String oldCpf, Person person) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "UPDATE people SET cpf=?, name=?, address=?, phone=?, email=?, age=?,"
                + "sex=?, weight=?, blood_type=?, medical_conditions=?, hospital_id=?"
                + "WHERE cpf=?");

        preparedStatement.setString(1, person.getCPF());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getAddress());
        preparedStatement.setString(4, person.getPhone());
        preparedStatement.setString(5, person.getEmail());
        preparedStatement.setInt(6, person.getAge());
        preparedStatement.setInt(7, person.getSex().ordinal());
        preparedStatement.setFloat(8, person.getWeight());
        preparedStatement.setInt(9, person.getBloodType().ordinal());
        preparedStatement.setString(10, person.getMedicalConditions());
        preparedStatement.setString(11, person.getHospitalID());
        preparedStatement.setString(12, oldCpf);

        boolean status = preparedStatement.execute();

        database.disconnect();

        return status;
    }

    public boolean remove(String cpf) throws SQLException {
        database.connect();
        preparedStatement = database.createPreparedStatement(
                "DELETE FROM people WHERE cpf = ?;");

        preparedStatement.setString(1, cpf);

        boolean status = preparedStatement.execute();

        database.disconnect();

        return status;
    }

    public boolean has(String cpf) throws SQLException {
        database.connect();
        
        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM people WHERE cpf = ?;");

        preparedStatement.setString(1, cpf);

        ResultSet result = preparedStatement.executeQuery();

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

        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM people WHERE cpf = ?;");

        preparedStatement.setString(1, cpf);

        ResultSet result = preparedStatement.executeQuery();

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

    public List<Person> getAll(String hospitalID) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM people WHERE hospital_id = ?;");

        preparedStatement.setString(1, hospitalID);

        ResultSet result = preparedStatement.executeQuery();

        List<Person> personList = new ArrayList<>();

        while (result.next()) {
            Person person = new Person(
                    result.getString(PersonAttributes.CPF.ordinal()),
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

            personList.add(person);
        }

        database.disconnect();

        return personList;
    }
}
