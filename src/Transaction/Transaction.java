package Transaction;

import Customer.Customer;
import Products.Product;
import Users.Cashier;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Transaction {
    Customer customer;
    Cashier cashier;
    private int discount = 0;
    private final HashMap<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public Transaction(Cashier cashier) {
        this.cashier = cashier;
        customer = new Customer("No customer", "-", "/");

    }

    private float calculateFinalAmount() {
        float finalAmount = 0;
        for (Product product : products.keySet()) {
            finalAmount += product.getFinalPrice() * products.get(product);
        }
        return finalAmount - discount;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }


    /**
     * Function which creates a receipt and puts transaction into customer and into transaction file
     */
    public void createReceipt() {
        String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        String fileName = customer.getName() + "_" + date + "_" + time + "receipt.txt";
        String filePath = "src/Transaction/receipts/" + fileName;
        filePath = filePath.replace(" ", "_");
        filePath = filePath.replace(":", "_");


        try {
            //Creat receipt Writer
            FileWriter receiptWriter = new FileWriter(filePath, true);

            //Create customer writer
            FileWriter customerWriter = null;
            if (!customer.getName().equals("No customer")) {
                fileName = customer.getName() + "_" + customer.getPhone() + "_transactions.txt";
                filePath = "src/Customer/customer_transactions/" + fileName;
                filePath = filePath.replace(" ", "_");
                customerWriter = new FileWriter(filePath, true);
            }

            //Create cashierWriter
            fileName = cashier.getName() + "_transactions.txt";
            filePath = "src/Users/cashier_transactions/" + fileName;
            filePath = filePath.replace(" ", "_");
            FileWriter cashierWriter = new FileWriter(filePath, true);

            FileWriter[] writers = new FileWriter[]{receiptWriter, customerWriter, cashierWriter};

            for (FileWriter writer : writers) {
                if(writer == null){
                    continue;
                }
                writer.write("\n");

                writer.write("Transaction Date: " + date + "\nTransaction Time: " + time + "\n");
                writer.write("Customer: " + customer.getName() + "\nCashier: " + cashier.getName() + "\n");
            }

            int cnt = 0;
            float total = 0;
            for (FileWriter writer : writers) {
                if (writer == null){
                    continue;
                }
                String formattedTittle = String.format("%-35s %-20s %-10s %-8s %-15s %-10s %-15s",
                        "NAME", "ID", "AMOUNT", "PRICE/1", "TOTAL PRICE", "DISCOUNT", "FINAL PRICE");
                writer.write(formattedTittle);
                for (Product product : products.keySet()) {
                    writer.write("\n");
                    int productAmount = products.get(product);
                    String formattedContent = String.format("%-35s %-20s %-10s %-8s %-15s %-10s %-15s",
                            product.getProductName(), product.getProductID(), productAmount,
                            product.getProductPrice(), product.getProductPrice() * productAmount,
                            product.getDiscountAmount() * productAmount, product.getFinalPrice() * productAmount);
                    writer.write(formattedContent);
                    if(cnt == 0) {
                        total += product.getFinalPrice() * productAmount;
                        writer.write("\n");
                        writer.write("Current Total: " + total);
                    }
                }
                cnt++;
                writer.write("\n");
                writer.write("Reward points used: " + discount + "\n");
                writer.write("Final Amount: " + calculateFinalAmount() + "\n");
                writer.close();
            }
            receiptWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

