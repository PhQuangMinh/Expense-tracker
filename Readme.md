## 1. Group Information

**Name of Project:** ***Expense Tracker***

**Group Member: Class D22CN04**
- [Phạm Quang Minh](https://github.com/PhQuangMinh)
- [Nguyễn Hữu Lộc](https://github.com/Leonguyen2004)
- [Nguyễn Văn Học](https://github.com/Hocnv0204)
- [Nguyễn Hữu Hưng](https://github.com/Febhamchoi)
- [Nguyễn Minh Khánh](https://github.com/Khanhnm1401)

### Working model

The team operates according to the Scrum model, using Linear to manage work. Jobs are kept fully tracked on Linear.
- Link linear: [Linear of Expense Tracker](https://linear.app/oopjava/team/BTL/active)

Each week, the team will sit down to review the work they have done, solve problems together and propose solutions for the following week. There will then be a demo session for the mentor to receive feedback and guidance.

### Version Control Strategy


The team works according to Gitflow to manage code. Each member will create a branch from `develop` to work, branches are placed according to format `feature/ten-chuc-nang`. Once completed, a Pull Request will be created to review the code and merge it into develop
- Main branches:
  - `master/main`: Contains stable, thoroughly tested and tested code
  - `develop`: Contains the latest code, reviewed and tested
  - `feature/`: Branches contain code under development, short-live, and will be merged into develop `develop`. 

<figure>
  <img src="./Demo images/versioncontrol.png" alt="Git flow" style="width:500px">
  <figcaption>Git flow</figcaption>
</figure>

After each week, the team will merge `develop` into `master` to release the new version.



## 2. Project introduction

**Describe:** **An Expense Tracker App** is designed to help users manage their income and expenses efficiently. The app includes the following key features:

- **Record and Edit Income/Expenses:** Users can input and modify their income and expense entries, ensuring accurate tracking of financial activities.
- **Edit Expense Categories:** Customize and manage expense and income categories to suit personal or business needs.
- **Track Finances on a Calendar:** View income and expense records visually on a calendar to identify patterns and trends over time.
- **Monthly and Annual Reports:** Generate detailed reports summarizing income and expenses for a selected month or year, providing insights for financial planning.

This app is ideal for individuals or businesses seeking an organized and straightforward way to manage their finances.

## 3. Main fuction

- ***Function 1***: Record and Edit Income/Expenses.
- ***Function 2***: Edit Expense Categories.
- ***Function 3***: Track Finances on a Calendar.
- ***Function 4***: Monthly and Annual Reports.

## 4. Technology

### 4.1. Technology Used
- Java language
- Javafx
- Firebase

### 4.2. Project structure
```
───main
    ├───java
    │   └───org
    │       └───example
    │           └───projectassignment
    │               ├───common
    │               ├───controller
    │               │   ├───auth
    │               │   │   └───forgotpassword
    │               │   ├───feature
    │               │   │   ├───calendar
    │               │   │   └───input
    │               │   │       └───category
    │               │   └───firebase
    │               ├───model
    │               │   ├───category
    │               │   └───user
    │               │       └───informationuser
    │               └───view
    │                   ├───auth
    │                   │   ├───forgotpassword
    │                   │   ├───signin
    │                   │   └───signup
    │                   ├───feature
    │                   │   ├───calendar
    │                   │   ├───input
    │                   │   │   ├───expense
    │                   │   │   └───income
    │                   │   ├───other
    │                   │   │   ├───annualreport
    │                   │   │   └───informationuser
    │                   │   └───report
    │                   └───utils
    └───resources
        └───org
            └───example
                └───projectassignment
                    ├───css
                    │   └───category
                    └───view
                        ├───auth
                        │   ├───forgotpassword
                        │   ├───signin
                        │   └───signup
                        ├───feature
                        │   ├───calendar
                        │   ├───other
                        │   │   ├───annualreport
                        │   │   ├───editinformation
                        │   │   └───editpassword
                        │   └───report
                        └───Image
```

## 5. Demo Photos and Demo Videos

**Photos Demo:**

<figure style="text-align: center;">
  <img src="./Demo images/record.png" alt="Enter record" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Enter expense</figcaption>
</figure>


<figure style="text-align: center;">
  <img src="./Demo images/calendar.png" alt="calendar" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Calendar</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="./Demo images/edit_record.png" alt="edit_record" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Edit record</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="./Demo images/report_monthly.png" alt="report_monthly" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Report monthly</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="./Demo images/report_annualy.png" alt="Report annualy" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Report annualy</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="./Demo images/edit_category.png" alt="Edit category" style="width:400px; display: block; margin: 0 auto;">
  <figcaption style="font-weight: bold; font-size: 18px;">Edit category</figcaption>
</figure>



**Video Demo:**
[Expense Tracker](https://youtu.be/R-SVCe8wEUQ)


## 6. Conclude
**Results achieved:** The app project has been significantly improved in terms of performance, functionality, and user interface.

**Next development direction:** In the future, the team plans to research and develop additional features such as: expanding functionality (e.g., adding more detailed financial analysis), improving user experience (e.g., more intuitive navigation), and integrating advanced features (e.g., AI-based financial suggestions).

Through this project, our team gained valuable experience with Gitflow, enhanced teamwork skills, and learned how to manage a project in a real-life setting, significantly improving our programming abilities. We hope users will enjoy and find the app helpful. All contributions are welcome! For major changes, please open an issue first to discuss your proposed updates...
