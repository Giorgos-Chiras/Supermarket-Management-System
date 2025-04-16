package Users;

import Customer.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Cashier class that extends from user
 * Doesn't have any methods as it has no privileges
 */
public class Cashier extends User {
    public Cashier(String name, String id) {
        super(name, UserPosition.CASHIER, id);
    }

    /**
        Helper function that searches through customers in the system to find
        if a customer registered to a phone number exists
     */
    public Customer searchCustomer(String phoneNumber, HashSet<Customer> customers) {
        Customer searchCustomer = new Customer("name", "email", phoneNumber);

        for (Customer customer : customers) {
            if (customer.getPhone().equals(searchCustomer.getPhone())) {
                return customer;
            }
        }
        System.out.println("Customer not found");
        return null;
    }

    /**
        Reads customer transaction history from file and prints it to the console
     */
    public void printCustomerDetails(String phoneNumber, HashSet<Customer> customers){
        Customer customer = searchCustomer(phoneNumber, customers);
        if(customer == null){
            return;
        }

        String filePath ="Customer/customer_transactions/"+
                customer.getName().replace(" ", "_")+
                "_"+customer.getPhone() + "_transactions.txt";

        BufferedReader br = null;
        StringBuilder transactionHistory= new StringBuilder();
        try{
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) !=null){
                transactionHistory.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Customer has no recorded transactions");
            return;
        }
        System.out.println(transactionHistory);
        System.out.println(customer);
    }

}
