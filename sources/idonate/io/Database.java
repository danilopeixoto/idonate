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

package idonate.io;

import idonate.util.Hospital;
import idonate.util.Person;
import idonate.util.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    
    public boolean setHospital(Hospital hospital) throws SQLException {
        return false;
    }
    public Hospital getHospital(String id) {
        return null;
    }
    public boolean removeHospital(String id) {
        return false;
    }
    public boolean hasHospital(String id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("");
        
        return false;
    }
    
    public boolean setPerson(Person person) {
        return false;
    }
    public Person getPerson(String cpf) {
        return null;
    }
    public boolean removePerson(String cpf) {
        return false;
    }
    public boolean hasPerson(String cpf) {
        return false;
    }
    
    public boolean setResource(Resource resource) {
        return false;
    }
    public Resource getResource(int id) {
        return null;
    }
    public boolean removeResource(int id) {
        return false;
    }
    public boolean hasResource(int id) {
        return false;
    }
    
    public ArrayList<Resource> getDonationResources(String cpf) {
        return null;
    }
    public ArrayList<Resource> getReceivedResources(String cpf) {
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