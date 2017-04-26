package org.taxireferral.api.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.taxireferral.api.Globals.Globals;

/**
 * Created by sumeet on 18/4/17.
 */
public class VehicleDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    // add vehicle
    // update vehicle
    // update location
    // get location
    // getVehicle(int driverID) : get vehicle details for driver
    // getVehicles(double latPickUp, double latPickUp, String searchString)
    // updateStatus(int status) : update status for drivers

    /*******************
     *
     * isApproved true -> accepted
     * isApproved NULL -> under review
     * isApproved false -> rejected
     *
     * parent NULL -> insert_submission (inserts a new entry)
     * parent NOT NULL -> update_submission(
     *
     * timestamp_applied ->
     * a) timestamp at which the version was applied to the entry
     * b) sort of timestamp applied in the descending order shows the version history
     *
     * backup exists -> tells if the backup exists for the given entry or not
     *
     * -------------------------------------------------------------------------------
     *
     * insert_an_entry
     * 1. a) Insert into entry table
     * b) mark backup exists as false
     * (reconsider)b) mark backup exists as true
     * (reconsider)2. insert into versions table
     *
     * update_an_entry
     * 1. take a backup if backup does not exist
     * 2. a) update the entry b) mark backup exists as false
     *
     * restore_a_version
     * 1. take a backup if backup does not exist
     * 2. a) update an entry from a version b) mark backup exists as true
     * 3. update the version fields - timestamp_applied to now()
     *
     *
     * submit_an_entry
     * 1. a) insert into versions table b) parentID is null, isApproved is null (indicates under review),
     * timestampApplied as null
     *
     * approve_an_entry
     * 1. a) create a backup of the current entry if not exist
     * 2. a)copy from versions table to an entry table b) mark backup exists as true
     * 3.Update version fields isApproved, timestampApplied, timestampApproved and also parentID
     *
     * submit_an_update_to_an_entry
     * 1. a)create a version with isApproved NULL, timestamp created now(), timestamp applied null,
     *      parent ID assigned
     *
     * approve_an_update
     * 1. a)create a backup of the current entry if not exist b) mark backup exists as true
     * 2. copy the version into the entry
     * 3. update version fields timestampApplied to now(), isApproved to true, timestampApproved to now(),
     *
     *
     * reject_an_entry
     * 1. set isApproved from NULL to false, mark timestamp approved as now()
     *
     * reject_an_update
     * 2. set isApproved from NULL to false, mark timestamp approved as now()
     *
     *
     * delete_an_entry
     * 1. deleting an entry deletes all of its
     *
     *
     *
     *
     *
     * ------------------------
     *
     * backup_exists
     *
     * timestamp_applied
     * timestamp_approved
     * is_approved
     *
     *
     *
     *
     *
     *
     * ------------------------------------
     * insert_an_item

     * update_an_item
     * submit_an_update
     * restore_a_version
     * approve_an_insert
     * approve_an_update
     *
     *
     */






}
