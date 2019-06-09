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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResourceAccessor extends Accessor {

//    public ResourceAccessor() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
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
    
    public boolean update(Resource resource) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "UPDATE resources SET donor_cpf = ?, donation_date = ?, description = ?,"
                + "acceptor_cpf = ?, acceptation_date = ?, type = ? "
                + "WHERE id = ?;");

        preparedStatement.setString(1, resource.getDonorCPF());
        preparedStatement.setDate(2, resource.getDonationDate());
        preparedStatement.setString(3, resource.getDescription());
        preparedStatement.setString(4, resource.getAcceptorCPF());
        preparedStatement.setDate(5, resource.getAcceptationDate());
        preparedStatement.setInt(6, resource.getType().ordinal());
        preparedStatement.setInt(7, resource.getID());

        boolean status = preparedStatement.execute();

        /*if (!status) {
            database.disconnect();
            return false;
        }*/

        status = false;

        switch (resource.getType()) {
            case Organ:
                Organ organ = (Organ) resource;

                preparedStatement = database.createPreparedStatement(
                        "UPDATE organs SET type = ?, weight = ? "
                        + "WHERE resource_id = ?;");

                preparedStatement.setInt(1, organ.getOrganType().ordinal());
                preparedStatement.setFloat(2, organ.getWeight());
                preparedStatement.setInt(3, organ.getID());

                status = preparedStatement.execute();

                break;

            case Blood:
                Blood blood = (Blood) resource;

                preparedStatement = database.createPreparedStatement(
                        "UPDATE bloods SET type = ?, volume = ? "
                        + "WHERE resource_id = ?;");

                preparedStatement.setInt(1, blood.getBloodType().ordinal());
                preparedStatement.setFloat(2, blood.getVolume());
                preparedStatement.setInt(3, blood.getID());

                status = preparedStatement.execute();

                break;

            case BoneMarrow:
                BoneMarrow boneMarrow = (BoneMarrow) resource;

                preparedStatement = database.createPreparedStatement(
                        "UPDATE bone_marrows SET hla = ?, redome = ? "
                        + "WHERE resource_id = ?;");

                preparedStatement.setString(1, boneMarrow.getHLA());
                preparedStatement.setString(2, boneMarrow.getREDOME());
                preparedStatement.setInt(3, boneMarrow.getID());

                status = preparedStatement.execute();

                break;

            default:
                break;
        }

        database.disconnect();

        return status;
    }

    public boolean add(Resource resource) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "INSERT INTO resources(donor_cpf, donation_date, description,"
                + "acceptor_cpf, acceptation_date, type) "
                + "VALUES (?, ?, ?, ?, ?, ?);");

        preparedStatement.setString(1, resource.getDonorCPF());
        preparedStatement.setDate(2, resource.getDonationDate());
        preparedStatement.setString(3, resource.getDescription());
        preparedStatement.setString(4, resource.getAcceptorCPF());
        preparedStatement.setDate(5, resource.getAcceptationDate());
        preparedStatement.setInt(6, resource.getType().ordinal());

        boolean status = preparedStatement.execute();

        /*if (!status) {
            database.disconnect();
            return false;
        }*/

        statement = database.createStatement();
        ResultSet currentIDResult = statement.executeQuery("SELECT LAST_INSERT_ID();");
        int id = 0;

        if (currentIDResult.next()) {
            id = currentIDResult.getInt(1);
        }

        if (id == 0) {
            database.disconnect();
            return false;
        }

        resource.setID(id);

        status = false;

        switch (resource.getType()) {
            case Organ:
                Organ organ = (Organ) resource;

                preparedStatement = database.createPreparedStatement(
                        "INSERT INTO organs(resource_id, type, weight) "
                        + "VALUES (?, ?, ?);");

                preparedStatement.setInt(1, organ.getID());
                preparedStatement.setInt(2, organ.getOrganType().ordinal());
                preparedStatement.setFloat(3, organ.getWeight());

                status = preparedStatement.execute();

                break;

            case Blood:
                Blood blood = (Blood) resource;

                preparedStatement = database.createPreparedStatement(
                        "INSERT INTO bloods(resource_id, type, volume) "
                        + "VALUES (?, ?, ?);");

                preparedStatement.setInt(1, blood.getID());
                preparedStatement.setInt(2, blood.getBloodType().ordinal());
                preparedStatement.setFloat(3, blood.getVolume());

                status = preparedStatement.execute();

                break;

            case BoneMarrow:
                BoneMarrow boneMarrow = (BoneMarrow) resource;

                preparedStatement = database.createPreparedStatement(
                        "INSERT INTO bone_marrows(resource_id, hla, redome) "
                        + "VALUES (?, ?, ?);");

                preparedStatement.setInt(1, boneMarrow.getID());
                preparedStatement.setString(2, boneMarrow.getHLA());
                preparedStatement.setString(3, boneMarrow.getREDOME());

                status = preparedStatement.execute();

                break;

            default:
                break;
        }

        database.disconnect();

        return status;
    }

    public boolean remove(int id) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "DELETE FROM resources WHERE id = ?;");

        preparedStatement.setInt(1, id);
        boolean status = preparedStatement.execute();

        database.disconnect();

        return status;
    }

    public boolean has(int id) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM resources WHERE id = ?");

        preparedStatement.setInt(1, id);

        ResultSet result = preparedStatement.executeQuery();

        if (!result.next()) {
            database.disconnect();
            return false;
        }

        int resourceID = result.getInt(ResourceAttributes.ID.ordinal());

        database.disconnect();

        return resourceID != 0;
    }

    public List<Resource> getAll(String cpf) throws SQLException {
        database.connect();

        List<Resource> all = new ArrayList<>();

        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM resources WHERE donor_cpf = ?;");
        preparedStatement.setString(1, cpf);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            int resourceID = result.getInt(ResourceAttributes.ID.ordinal());
            all.add(this.get(resourceID));
        }

        database.disconnect();
        return all;
    }

    public Resource get(int id) throws SQLException {
        database.connect();

        preparedStatement = database.createPreparedStatement(
                "SELECT * FROM resources WHERE id = ?;");

        preparedStatement.setInt(1, id);

        ResultSet result = preparedStatement.executeQuery();

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
                preparedStatement = database.createPreparedStatement(
                        "SELECT * FROM organs WHERE resource_id = ?;");

                preparedStatement.setInt(1, id);

                ResultSet organResult = preparedStatement.executeQuery();

                if (organResult.next()) {
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
                preparedStatement = database.createPreparedStatement(
                        "SELECT * FROM bloods WHERE resource_id = ?;");

                preparedStatement.setInt(1, id);

                ResultSet bloodResult = preparedStatement.executeQuery();

                if (bloodResult.next()) {
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
                preparedStatement = database.createPreparedStatement(
                        "SELECT * FROM bone_marrows WHERE resource_id = ?;");

                preparedStatement.setInt(1, id);

                ResultSet boneMarrowResult = preparedStatement.executeQuery();

                if (boneMarrowResult.next()) {
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
