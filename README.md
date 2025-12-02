# 🌴 Vacation Scheduler — WGU Mobile Development Project  
![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-orange?logo=coffee&logoColor=white)
![Room](https://img.shields.io/badge/Database-Room%20ORM-795548?logo=sqlite&logoColor=white)
![Status](https://img.shields.io/badge/Project%20Type-WGU%20Coursework-4B2E83)
![Platform](https://img.shields.io/badge/Platform-Android%20Studio-5C2D91?logo=androidstudio&logoColor=white)

Vacation Scheduler is a multi-screen Android application built for a **Western Governors University (WGU)** software development project.  
The goal: create a fully functional mobile app that demonstrates competency in **Java**, **Android fundamentals**, **local persistence with Room**, **multi-activity navigation**, **input validation**, and **system-level alerts**.

This repo represents the complete implementation of the required assessment.

---

## 🎓 Project Overview  
WGU’s objective for this project is to simulate a real-world mobile development scenario:  
> Build an app for a traveler to manage vacations and excursions using modern Android components and local data storage.

Students were required to implement:

- Persistent storage using the **Room Framework** (Entities, DAO, Database, Repository)  
- Multiple screens for managing data  
- Full CRUD operations for vacations and excursions  
- Date validation and relationship validation  
- Alerts using `AlarmManager` + `BroadcastReceiver`  
- A polished, intuitive UI with lists, details, and navigation  
- Signed APK deployment  
- GitLab commit history documenting progress  

This repository contains the Android Studio project fulfilling all these requirements.

---

## 🧾 What Was Expected (WGU Requirements)  
Below is a concise mapping of the assessment expectations and how this app meets them.

### **1. Vacation Management**
**WGU Requirement:**  
- Add, update, delete vacations  
- Prevent deleting a vacation that has excursions  
- Validate date format and date order  

**Delivered:**  
- Clean Vacation List screen with FAB  
- Full Vacation Details screen  
- Date validation + start-before-end logic  
- Safe delete preventing removal of vacations with excursions  

---

### **2. Vacation Details**
**WGU Requirement:**  
- Title  
- Lodging  
- Start date  
- End date  
- Alerts for start/end dates  
- Share vacation details  
- Show associated excursions  

**Delivered:**  
- Structured UI for viewing & editing vacation details  
- Alerts via `AlarmManager`  
- Full trip-sharing functionality  
- RecyclerView listing attached excursions  

---

### **3. Excursion Management**
**WGU Requirement:**  
- Add, update, delete excursions  
- Validate date formatting  
- Excursion date must fall within vacation date range  
- Alerts for excursion dates  

**Delivered:**  
- Excursion Details screen with validation  
- DatePickerDialog for consistent formatting  
- Alerts for excursion dates  
- Enforcement of date-in-range logic  

---

### **4. UI & Navigation**
**WGU Requirement:**  
- Vacation list  
- Vacation details  
- Excursion list  
- Excursion details  
- Clear navigation across screens  

**Delivered:**  
- Activity-based navigation structure  
- RecyclerViews for dynamic lists  
- Toolbar menus and FABs for intuitive control  
- Clean and simple layout design  

---

### **5. Architecture**
**WGU Requirement:**  
- Use Room as a persistence layer  
- Apply good architecture and separation of concerns  

**Delivered:**  
- Entities: `Vacation`, `Excursion`  
- DAO interfaces for structured data access  
- Repository layer to abstract business logic  
- Organized UI layer with focused Activities  
- Notification architecture using modern Android components  

---

### **6. Deployment**
**WGU Requirement:**  
- Generate a signed APK  
- Verify app runs on device/emulator  

**Delivered:**  
- Signed APK targeting **Android 8.0+**  
- Fully tested on emulator/device  
- Included screenshots of APK generation process  

---

## 🗂️ Tech Stack  
- **Language:** Java  
- **IDE:** Android Studio  
- **Database:** Room (SQLite abstraction)  
- **Notifications:** AlarmManager + BroadcastReceiver  
- **UI Components:** RecyclerView, FAB, Menus, DatePickerDialog  
- **Min SDK:** Android 8.0 (API 26)

---

## 📱 How to Use  
1. Launch the app → view the full vacation list  
2. Tap **+** → create a new vacation  
3. Open any vacation → edit details, set alerts, or view excursions  
4. Tap **Add Excursion** → attach activities to a vacation  
5. Use toolbar icons → save, delete, set alerts, or share  
6. Navigate smoothly with the built-in toolbar controls  

---

## 📦 APK  
A signed release APK is included in the submission and runs on:

- **Android 8.0 (API 26) and above**

---

## 🎉 Final Notes  
This app was created for WGU’s Mobile Application Development course and demonstrates real, practical skills including:

- End-to-end mobile app creation  
- Working with Room database architecture  
- Clean Activity-based navigation  
- Robust date validation & error handling  
- Full CRUD operations  
- Notifications & system services  
- App deployment & packaging  

It is both an academic submission **and** a strong portfolio piece showcasing Android development capability.

---
