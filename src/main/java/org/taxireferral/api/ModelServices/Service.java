package org.taxireferral.api.ModelServices;


public class Service {


    // Issues
    // - TripIssues
    // - ServiceIssues


    // ::Emergency
    // Emergency
    // DriverEmergency
    // TripEmergency
    // - EmergencyType, CreatedBy, CreatedByUserRole, EndUserLat, EndUserLon, TimestampCreated, Status

    // CreatedBy, CreatedByUserRole



    // ::Example Services
    // - DriverAccountActivation [TotalCharge : 100, StaffPay : 85]
    // - Renewal [TotalCharge : 100,  StaffPay : 85]
    // - ProfileUpdate [TotalCharge : 100, StaffPay : 85]
    // - PhotoShoot [TotalCharge : 120, StaffPay : 100]
    // - AddCredit [TotalCharge : 0, StaffPay : 10]
    // - Feedback and Suggestions [ TotalCharge : 10, StaffPay : 10]
    // - FileIssue [ TotalCharge 10, StaffPay : 10]

    // - EarningsLimit per payout cycle : For Staff - Limits and ensures staff dont overwork which might
    // lower the work quality

    // TotalCharge Should not be less than staff Pay . This can create opportunities for corruption


    // Staff - Code of Ethics


    // Table Name
    public static final String TABLE_NAME = "SERVICES_AVAILABLE";

    // Column names
    public static final String SERVICE_ID = "SERVICE_ID"; // primary key
    public static final String SERVICE_NAME = "NAME";
    public static final String SERVICE_DESCRIPTION = "DESCRIPTION";
    public static final String LIST_ORDER = "LIST_ORDER";
    public static final String AVAILABLE_FOR_DRIVER = "AVAILABLE_FOR_DRIVER";
    public static final String AVAILABLE_FOR_END_USER = "AVAILABLE_FOR_END_USER";
    public static final String TOTAL_CHARGE = "TOTAL_CHARGE";
    public static final String TAX_RATE = "TAX_RATE";
    public static final String STAFF_PAY = "STAFF_PAY";





    // Create Table Statement
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + Service.TABLE_NAME + "("

                    + " " + Service.SERVICE_ID + " SERIAL PRIMARY KEY,"
                    + " " + Service.SERVICE_NAME + " text,"
                    + " " + Service.SERVICE_DESCRIPTION + " text,"
                    + " " + Service.LIST_ORDER + " int NOT NULL default 0,"
                    + " " + Service.AVAILABLE_FOR_DRIVER + " boolean NOT NULL default 'f',"
                    + " " + Service.AVAILABLE_FOR_END_USER + " boolean NOT NULL default 'f',"
                    + " " + Service.TOTAL_CHARGE + " float NOT NULL default 0,"
                    + " " + Service.TAX_RATE + " float NOT NULL default 0,"
                    + " " + Service.STAFF_PAY + " float NOT NULL default 0"

                    + ")";










    // instance variables

    private int serviceID;
    private String serviceName;
    private String serviceDescription;
    private int listOrder;
    private boolean availableForDriver;
    private boolean availableForEndUser;
    private double totalCharge;
    private double taxRate;
    private double staffPay;



    // getter and setters


    public int getListOrder() {
        return listOrder;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }


    public boolean isAvailableForDriver() {
        return availableForDriver;
    }

    public void setAvailableForDriver(boolean availableForDriver) {
        this.availableForDriver = availableForDriver;
    }

    public boolean isAvailableForEndUser() {
        return availableForEndUser;
    }

    public void setAvailableForEndUser(boolean availableForEndUser) {
        this.availableForEndUser = availableForEndUser;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getStaffPay() {
        return staffPay;
    }

    public void setStaffPay(double staffPay) {
        this.staffPay = staffPay;
    }
}
