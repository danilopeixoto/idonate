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

import idonate.model.Hospital;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalAccessor extends Accessor {
    private enum HospitalAttributes {
        None,
        ID,
        Password,
        Name,
        Address,
        Phone,
        Email
    }
    
    public HospitalAccessor(Database database) {
        super(database);
    }
    
    public boolean add(Hospital hospital) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
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
        
        boolean status = statement.execute(
                "INSERT INTO hospitals(id, password, name, address, phone, email) "
                        + "VALUES " + values.toString());
        
        database.disconnect();
        
        return status;
    }
    public boolean remove(String id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        boolean status = statement.execute(
                "DELETE FROM hospitals WHERE id = \"" + id + "\";");
        
        database.disconnect();
        
        return status;
    }
    public boolean has(String id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM hospitals WHERE id = \"" + id + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return false;
        }
        
        String hospitalID = result.getString(HospitalAttributes.ID.ordinal());
        
        database.disconnect();
        
        return hospitalID != null;
    }
    public Hospital get(String id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM hospitals WHERE id = \"" + id + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return null;
        }
        
        String hospitalID = result.getString(HospitalAttributes.ID.ordinal());
        
        if (hospitalID == null) {
            database.disconnect();
            return null;
        }
        
        Hospital hospital = new Hospital(
                hospitalID,
                result.getString(HospitalAttributes.Password.ordinal()),
                result.getString(HospitalAttributes.Name.ordinal()),
                result.getString(HospitalAttributes.Address.ordinal()),
                result.getString(HospitalAttributes.Phone.ordinal()),
                result.getString(HospitalAttributes.Email.ordinal()));
        
        database.disconnect();
        
        return hospital;
    }
}