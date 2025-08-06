# 📚 Library Management System

A Java-based Library Management System that streamlines the process of lending and purchasing books.  
The system integrates **real-world book data from the OpenLibrary API**, features a **normalized relational database design**, and includes **advanced SQL queries** for library administration insights.

---

## Features

- 📖 Search and view books with availability
- 👤 Register library members (borrowers or buyers)
- 📚 Track borrowing and purchasing activities
- 🏷️ Manage book copies with shelf and floor locations
- 📊 Generate statistical reports with advanced SQL queries
- 🔑 Secure member login and purchase tracking

---

## Tech Stack

- **Database**: MySQL (AWS RDS)  
- **Backend**: Java (Maven Project)  
- **Frontend**: Java Swing GUI  
- **Dependencies**:  
  - `mysql-connector-java`  
  - `org.json`  
  - `jcalendar`  
- **External Data**: [OpenLibrary API](https://openlibrary.org/developers/api)

---

## Database Design

The system uses a **normalized relational schema** to ensure consistency and scalability.

- Distinguishes between **Borrowers** and **Buyers**
- Maintains **referential integrity** with cascading deletes
- Tracks **multiple copies of books** with precise shelf locations
- Includes **billing information** for buyers and **fine tracking** for borrowers

### Entity-Relationship Diagram
![ER Diagram](./assets/er_diagram.png)


---

## 📈 Advanced SQL Queries

Below are examples of advanced queries used in the project:

