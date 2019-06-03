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
import com.xnc.idonate.model.BoneMarrow;
import com.xnc.idonate.model.Organ;
import com.xnc.idonate.model.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceAccessor extends Accessor {
    private enum ResourceAttributes {
        None,
        ID,
        DonorCPF,
        DonationDate,
        Description,
        AcceptorCPF,
        AcceptationDate,
        Type
    }

    private enum OrganAttributes {
        None,
        ResourceID,
        Type,
        Weight
    }

    private enum BloodAttributes {
        None,
        ResourceID,
        Type,
        Volume
    }

    private enum BoneMarrowAttributes {
        None,
        ResourceID,
        HLA,
        REDOME
    }
    
    public ResourceAccessor(Database database) {
        super(database);
    }
    
    public boolean add(Resource resource) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        StringBuilder values = new StringBuilder();
        
        values.append("(\"");
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
                "INSERT INTO resources(donor_cpf, donation_date, description,"
                        + "acceptor_cpf, acceptation_date, type) "
                        + "VALUES " + values.toString());
        
        if (!status) {
            database.disconnect();
            return false;
        }
        
        ResultSet currentIDResult = statement.executeQuery("SELECT LAST_INSERT_ID();");
        int id = 0;
        
        if (currentIDResult.next())
            id = currentIDResult.getInt(1);
        
        if (id == 0) {
            database.disconnect();
            return false;
        }
        
        resource.setID(id);
        
        values = new StringBuilder();
        status = false;
        
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
                
                status = statement.execute(
                    "INSERT INTO organs(resource_id, type, weight) "
                            + "VALUES " + values.toString());
                break;
                
            case Blood:
                Blood blood = (Blood)resource;
                
                values.append("(\"");
                values.append(blood.getID());
                values.append("\",\"");
                values.append(blood.getBloodType().ordinal());
                values.append("\",\"");
                values.append(blood.getVolume());
                values.append("\");");
                
                status = statement.execute(
                    "INSERT INTO bloods(resource_id, type, volume) "
                            + "VALUES " + values.toString());
                
                break;
                
            case BoneMarrow:
                BoneMarrow boneMarrow = (BoneMarrow)resource;
                
                values.append("(\"");
                values.append(boneMarrow.getID());
                values.append("\",\"");
                values.append(boneMarrow.getHLA());
                values.append("\",\"");
                values.append(boneMarrow.getREDOME());
                values.append("\");");
                
                status = statement.execute(
                    "INSERT INTO bone_marrows(resource_id, hla, redome) "
                            + "VALUES " + values.toString());
                
                break;
                
            default:
                break;
        }
        
        database.disconnect();
        
        return status;
    }
    public boolean remove(int id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return false;
        }
        
        Resource.ResourceType type = Resource.ResourceType.values()[result.getInt(ResourceAttributes.Type.ordinal())];
        boolean status = false;
        
        switch (type) {
            case Organ:
                status = statement.execute(
                         "DELETE FROM organs WHERE id = \"" + id + "\";");
                break;
                
            case Blood:
                status = statement.execute(
                         "DELETE FROM bloods WHERE id = \"" + id + "\";");
                break;
                
            case BoneMarrow:
                status = statement.execute(
                         "DELETE FROM bone_marrows WHERE id = \"" + id + "\";");
                break;
                
            default:
                break;
        }
        
        database.disconnect();
        
        return status;
    }
    public boolean has(int id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return false;
        }
        
        int resourceID = result.getInt(ResourceAttributes.ID.ordinal());
        
        database.disconnect();
        
        return resourceID != 0;
    }
    public Resource get(int id) throws SQLException {
        database.connect();
        statement = database.createStatement();
        
        ResultSet result = statement.executeQuery(
                "SELECT * FROM resources WHERE id = \"" + id + "\";");
        
        if (!result.next()) {
            database.disconnect();
            return null;
        }
        
        int resourceID = result.getInt(ResourceAttributes.ID.ordinal());
        
        if (resourceID == 0) {
            database.disconnect();
            return null;
        }
        
        Resource.ResourceType type = Resource.ResourceType.values()[result.getInt(ResourceAttributes.Type.ordinal())];
        Resource resource = null;
        
        switch (type) {
            case Organ:
                ResultSet organResult = statement.executeQuery(
                        "SELECT * FROM organs WHERE id = \"" + id + "\";");
            
                if (result.next()) {
                    resource = new Organ(
                            result.getInt(ResourceAttributes.ID.ordinal()),
                            result.getString(ResourceAttributes.DonorCPF.ordinal()),
                            result.getDate(ResourceAttributes.DonationDate.ordinal()),
                            result.getString(ResourceAttributes.Description.ordinal()),
                            Organ.OrganType.values()[organResult.getInt(OrganAttributes.Type.ordinal())],
                            organResult.getFloat(OrganAttributes.Weight.ordinal()),
                            result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                            result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
                }
                
                break;
                
            case Blood:
                ResultSet bloodResult = statement.executeQuery(
                         "SELECT * FROM bloods WHERE id = \"" + id + "\";");
                
                if (result.next()) {
                    resource = new Blood(
                            result.getInt(ResourceAttributes.ID.ordinal()),
                            result.getString(ResourceAttributes.DonorCPF.ordinal()),
                            result.getDate(ResourceAttributes.DonationDate.ordinal()),
                            result.getString(ResourceAttributes.Description.ordinal()),
                            Blood.BloodType.values()[bloodResult.getInt(BloodAttributes.Type.ordinal())],
                            bloodResult.getFloat(BloodAttributes.Volume.ordinal()),
                            result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                            result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
                }
                
                break;
                
            case BoneMarrow:
                ResultSet boneMarrowResult = statement.executeQuery(
                         "SELECT * FROM bone_marrows WHERE id = \"" + id + "\";");
                
                if (result.next()) {
                    resource = new BoneMarrow(
                            result.getInt(ResourceAttributes.ID.ordinal()),
                            result.getString(ResourceAttributes.DonorCPF.ordinal()),
                            result.getDate(ResourceAttributes.DonationDate.ordinal()),
                            result.getString(ResourceAttributes.Description.ordinal()),
                            boneMarrowResult.getString(BoneMarrowAttributes.HLA.ordinal()),
                            boneMarrowResult.getString(BoneMarrowAttributes.REDOME.ordinal()),
                            result.getString(ResourceAttributes.AcceptorCPF.ordinal()),
                            result.getDate(ResourceAttributes.AcceptationDate.ordinal()));
                }
                
                break;
                
            default:
                break;
        }
        
        database.disconnect();
        
        return resource;
    }
}