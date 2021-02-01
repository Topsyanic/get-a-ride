package com.example.get_a_ridemobileportal;

public class Booking {
    String pickup,destination,date,time,phoneNumber,customerEmail,driverEmail,charge,status,customerName;

    public Booking() {
    }

    public Booking(String pickup, String destination, String date, String time, String phoneNumber, String customerEmail, String driverEmail, String charge,String status,String customerName) {
        this.pickup = pickup;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.phoneNumber = phoneNumber;
        this.customerEmail = customerEmail;
        this.driverEmail = driverEmail;
        this.charge = charge;
        this.status=status;
        this.customerName=customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }
}
