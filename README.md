# 🛒 Supermarket Management System

A Java-based application designed to streamline and automate supermarket operations, featuring inventory management, sales processing, and comprehensive reporting.

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=flat-square&logo=openjdk)

## 🚀 Features

### 📦 Inventory Management
- Add/update product details (name, price, stock levels)
- Real-time stock monitoring

### 🙍‍♂️ Customer and Bonus Card
- Add customers to bonus card system
- Reward loyal customers with points (discounts)
- Track transactions of customers

### 💵 Sales Processing
- Handle customer transactions
- Real-time inventory updates
- Receipt generation

### 📊 Reporting
- Sales analytics
- Inventory status reports
- Customer reports

### 🔒 User Authentication
- Role-based access control
- Secure login system

## 🛠️ Technologies Used
- **Programming Language:** ![Java](https://img.shields.io/badge/Java-17%2B-blue?style=flat-square&logo=openjdk)
- **Data Storage:** Text files (txt format)
- **IDE:** IntelliJ IDEA
- **Version Control:** Git/GitHub

## 📥 Installation

### Prerequisites
- Java 17 or higher
- Git
- IntelliJ IDEA (recommended)

1. **Clone Repository**  
   `git clone https://github.com/Giorgos-Chiras/Supermarket-Management-System.git`

2. **Import Project**
   - Open IntelliJ IDEA
   - Select `File > Open`
   - Choose the cloned directory

3. **Ensure proper folder structur**
```plaintext
src/
├── closure_reports/
├── Customer/
│   ├── customer_transactions/
│   ├── reports/
│   ├── BonusCard.java
│   ├── Customer.java
│   └── customers.txt
├── FileHandler/
│   └── FileHandler.java
├── Products/
│   ├── reports/
│   ├── Product.java
│   ├── ProductCategory.java
│   └── products.txt
├── Transaction/
│   ├── receipts/
│   └── Transaction.java
├── Users/
│   ├── cashier_transactions/
│   ├── Cashier.java
│   ├── HRManager.java
│   ├── Manager.java
│   ├── User.java
│   ├── UserPosition.java
│   └── users.txt
├── Main.java
└── SystemHandler.java
```


5. **Build & Run**
   - In IntelliJ:
     1. Click `Build Project`
     2. Run `Main.java`

## 🖥️ Usage

### Default Credentials

Username: HR_manager
Password: HR_manager

Username: manager_1
Password: manager_1_1

Username: cashier_1
Password: cashier_1


### Key Functions
- **User Management**
  - Add/remove staff accounts
- **Inventory Control**
  - Add new products
  - Update stock
  - View product details
- **Sales Operations**
  - Process purchases
  - Generate receipts
  - View transaction history
- **Reporting**
  - Generate daily reports
  - View inventory analytics
  - Export reports to text files

## 📄 License  
This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details

## 📧 Contact  
**Giorgos Chiras**  
[![GitHub](https://img.shields.io/badge/GitHub-Profile-blue?logo=github)](https://github.com/Giorgos-Chiras)  
📧 [gp.chiras@gmail.com](mailto:gp.chiras@gmail.com)
