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
import idonate.model.Blood.BloodType;
import idonate.model.BoneMarrow;
import idonate.model.Hospital;
import idonate.model.Organ;
import idonate.model.Organ.OrganType;
import idonate.model.Person;
import idonate.model.Person.Sex;
import idonate.model.Resource;
import idonate.model.Resource.ResourceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

enum HospitalAttributes {
    None,
    ID,
    Password,
    Name,
    Address,
    Phone,
    Email
}

enum PersonAttributes {
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

enum ResourceAttributes {
    None,
    ID,
    DonorCPF,
    DonationDate,
    Description,
    AcceptorCPF,
    AcceptationDate,
    Type
}

enum OrganAttributes {
    None,
    ResourceID,
    Type,
    Weight
}

enum BloodAttributes {
    None,
    ResourceID,
    Type,
    Volume
}

enum BoneMarrowAttributes {
    None,
    ResourceID,
    HLA,
    REDOME
}

public class Database {
    private String username;
    private String password;
    private String name;
    
    private String url;
    private Connection connection;
    
    private static int timeout = 1;
    
    public Database(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
        
        this.url = "jdbc:mysql://localhost:3306/" + name;
        this.connection = null;
    }
    
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }
    public void disconnect() throws SQLException {
        if (connection != null)
            connection.close();
        
        connection = null;
    }
    public boolean isConnected() throws SQLException {
        if (connection != null)
            return connection.isValid(timeout);
        
        return false;
    }
    
    public boolean addHospital(Hospital hospital) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder values = new StringBuilder();
        
        values.append("(\"");
        values.append(hospital.getID());
        values.append("\",\"");
        values.append(hospital.getPassword());
        values.append("\",\"");
        values.append(hospital.getName());
        values.append("\",\"");
        values.append(hospital.getAddress());
        values.append("\",\"");
        values.append(hospital.getPhone());
        values.append("\",\"");
        values.append(hospital.getEmail());
        values.append("\");");
        
        return statement.execute(
                "INSERT INTO hospitals(id, password, name, address, phone, email) "
                        + "VALUES " + values.toString());
    }
    public boolean removeHospital(String id) throws SQLException {
        Statement statement = connection.createStatement();
        
        return statement.execute(
                "DELETE FROM hospitals WHERE id = \"" + id + "\";");
    }
    public boolean hasHospital(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM hospitals WHERE id = \"" + id + "\";");
        
        if (!result.next())
            return false;
        
        String hospitalID = result.getString(HospitalAttributes.ID.ordinal());
        
        return hospitalID != null;
    }
    public Hospital getHospital(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM hospitals WHERE id = \"" + id + "\";");
        
        if (!result.next())
            return null;
        
        String hospitalID = result.getString(HospitalAttributes.ID.ordinal());
        
        if (hospitalID == null)
            return null;
        
        return new Hospital(
                hospitalID,
                result.getString(HospitalAttributes.Password.ordinal()),
                result.getString(HospitalAttributes.Name.ordinal()),
                result.getString(HospitalAttributes.Address.ordinal()),
                result.getString(HospitalAttributes.Phone.ordinal()),
                result.getString(HospitalAttributes.Email.ordinal()));
    }
    
    public boolean addPerson(Person person) throws SQLException {
        Statement statement = connection.createStatement();
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
        
        return statement.execute(
                "INSERT INTO people(cpf, name, address, phone, email, age,"
                        + "sex, weight, blood_type, medical_conditions, hospital_id) "
                        + "VALUES " + values.toString());
    }
    public boolean removePerson(String cpf) throws SQLException {
        Statement statement = connection.createStatement();
        
        return statement.execute(
                "DELETE FROM people WHERE id = \"" + cpf + "\";");
    }
    public boolean hasPerson(String cpf) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM people WHERE id = \"" + cpf + "\";");
        
        if (!result.next())
            return false;
        
        String personCPF = result.getString(PersonAttributes.CPF.ordinal());
        
        return personCPF != null;
    }
    public Person getPerson(String cpf) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM people WHERE id = \"" + cpf + "\";");
        
        if (!result.next())
            return null;
        
        String personCPF = result.getString(PersonAttributes.CPF.ordinal());
        
        if (personCPF == null)
            return null;
        
        return new Person(
                personCPF,
                result.getString(PersonAttributes.Name.ordinal()),
                result.getString(PersonAttributes.Address.ordinal()),
                result.getString(PersonAttributes.Phone.ordinal()),
                result.getString(PersonAttributes.Email.ordinal()),
                result.getInt(PersonAttributes.Age.ordinal()),
                Sex.values()[result.getInt(PersonAttributes.Sex.ordinal())],
                result.getFloat(PersonAttributes.Weight.ordinal()),
                BloodType.values()[result.getInt(PersonAttributes.BloodType.ordinal())],
                result.getString(PersonAttributes.MedicalConditions.ordinal()),
                result.getString(PersonAttributes.HospitalID.ordinal()));
    }
    
    public boolean addResource(Resource resource) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder values = new StringBuilder();
        
        values.append("(\"");
        values.append(resource.getID());
        values.append("\",\"");
        values.append(resource.getDonorCPF());
        values.append("\",\"");
        values.append(resource.getDonationDate());
        values.append("\",\"");
        values.append(resource.getDescription());
        values.append("\",\"");
        values.append(resource.getAcceptorCPF());
        values.append("\",\"");
        values.append(resource.getAcceptationDate());
        values.append("\",\"");
        values.append(resource.getType().ordinal());
        values.append("\");");
        
        boolean status = statement.execute(
                "INSERT INTO resources(id, donor_cpf, donation_date, description,"
                        + "acceptor_cpf, acceptation_date, type) "
                        + "VALUES " + values.toString());
        
        if (!status)
            return false;
        
        values = new StringBuilder();
        
        switch (resource.getType()) {
            case Organ:
                Organ organ = (Organ)resource;
                
                values.append("(\"");
                values.append(organ.getID());
                values.append("\",\"");
                values.append(organ.getOrganType().ordinal());
                values.append("\",\"");
                values.append(organ.getWeight());
                values.append("\");");
                
                return statement.execute(
                    "INSERT INTO organs(resource_id, type, weight) "
                            + "VALUES " + values.toString());
            case Blood:
                Blood blood = (Blood)resource;
                
                values.append("(\"");
                values.append(blood.getID());
                values.append("\",\"");
                values.append(blood.getBloodType().ordinal());
                values.append("\",\"");
                values.append(blood.getVolume());
                values.append("\");");
                
                return statement.execute(
                    "INSERT INTO bloods(resource_id, type, volume) "
                            + "VALUES " + values.toString());
            case BoneMarrow:
                BoneMarrow boneMarrow = (BoneMarrow)resource;
                
                values.append("(\"");
                values.append(boneMarrow.getID());
                values.append("\",\"");
                values.append(boneMarrow.getHLA());
                values.append("\",\"");
                values.append(boneMarrow.getREDOME());
                values.append("\");");
                
                return statement.execute(
                    "INSERT INTO bone_marrows(resource_id, hla, redome) "
                            + "VALUES " + values.toString());
            default:
        }
        
        return false;
    }
    public boolean removeResource(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next())
            return false;
        
        ResourceType type = ResourceType.values()[result.getInt(ResourceAttributes.Type.ordinal())];
        
        switch (type) {
            case Organ:
                return statement.execute(
                         "DELETE FROM organs WHERE id = \"" + id + "\";");
            case Blood:
                return statement.execute(
                         "DELETE FROM bloods WHERE id = \"" + id + "\";");
            case BoneMarrow:
                return statement.execute(
                         "DELETE FROM bone_marrows WHERE id = \"" + id + "\";");
            default:
        }
        
        return false;
    }
    public boolean hasResource(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next())
            return false;
        
        int resourceID = result.getInt(ResourceAttributes.ID.ordinal());
        
        return resourceID != 0;
    }
    public Resource getResource(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next())
            return null;
        
        int resourceID = result.getInt(ResourceAttributes.ID.ordinal());
        
        if (resourceID == 0)
            return null;
        
        ResourceType type = ResourceType.values()[result.getInt(ResourceAttributes.Type.ordinal())];
        
        switch (type) {
            case Organ:
                ResultSet organResult = statement.executeQuery(
                        "SELECT * FROM organs WHERE id = \"" + id + "\";");
            
                if (!result.next())
                    return null;

                return new Organ(
                        result.getInt(ResourceAttributes.ID.ordinal()),
                        result.getString(ResourceAttributes.DonorCPF.ordinal()),
                        result.getDate(ResourceAttributes.DonationDate.ordinal()),
                        result.getString(ResourceAttributes.Description.ordinal()),
                        OrganType.values()[organResult.getInt(OrganAttributes.Type.ordinal())],
                        organResult.getFloat(OrganAttributes.Weight.ordinal()),
                        result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                        result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
            case Blood:
                ResultSet bloodResult = statement.executeQuery(
                         "SELECT * FROM bloods WHERE id = \"" + id + "\";");
                
                if (!result.next())
                    return null;
                
                return new Blood(
                        result.getInt(ResourceAttributes.ID.ordinal()),
                        result.getString(ResourceAttributes.DonorCPF.ordinal()),
                        result.getDate(ResourceAttributes.DonationDate.ordinal()),
                        result.getString(ResourceAttributes.Description.ordinal()),
                        BloodType.values()[bloodResult.getInt(BloodAttributes.Type.ordinal())],
                        bloodResult.getFloat(BloodAttributes.Volume.ordinal()),
                        result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                        result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
            case BoneMarrow:
                ResultSet bmResult = statement.executeQuery(
                         "SELECT * FROM bone_marrows WHERE id = \"" + id + "\";");
                
                if (!result.next())
                    return null;
                
                return new BoneMarrow(
                        result.getInt(ResourceAttributes.ID.ordinal()),
                        result.getString(ResourceAttributes.DonorCPF.ordinal()),
                        result.getDate(ResourceAttributes.DonationDate.ordinal()),
                        result.getString(ResourceAttributes.Description.ordinal()),
                        bmResult.getString(BoneMarrowAttributes.HLA.ordinal()),
                        bmResult.getString(BoneMarrowAttributes.REDOME.ordinal()),
                        result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                        result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
            default:
        }
        
        return null;
    }
    
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
}