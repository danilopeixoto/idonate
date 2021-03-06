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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class Database {
    private final String username;
    private final String password;
    private final String name;
    
    private String url;
    private Connection connection;
    
    private static int timeout = 1;
    
    public Database(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        
        this.url = "jdbc:mysql://localhost:3306/" + name;
        this.connection = null;
    }
    
    public void createDefaultDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        Connection connection = DriverManager.getConnection(url, username, password);
        
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?;");
        
        preparedStatement.setString(1, name);
        
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next()) {
             boolean hasDatabase = result.getInt(1) > 0;
             
             if (!hasDatabase) {
                Statement statement = connection.createStatement();
            
                try {
                    String path = getClass().getResource("/database.sql").getPath();
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(new FileInputStream(path), "UTF-8"));
                    String sql = buffer.lines().collect(Collectors.joining("\n"));
                    String[] statements = sql.split(";");
                    
                    for (String stm : statements)
                        statement.executeUpdate(stm + ";");
                }
                catch (IOException exception) {
                    connection.close();
                    throw new SQLException();
                }
            }
        }
        else {
            connection.close();
            throw new SQLException();
        }
        
        connection.close();
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
    
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }
    public PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}