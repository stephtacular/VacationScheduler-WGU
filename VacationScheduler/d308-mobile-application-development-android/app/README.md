The title of this app is "Vacation Scheduler." The purpose of this application is to help users organize vacations and associated excursions. The app will allow users to add, view, edit, and delete vacations, track excursions within each vacation, set date-based reminders, and share vacation details. It uses the Room Framework as an abstraction layer over SQLite to store all data locally on the device.


////////// 2. How to Operate the Application //////////
This section explains how the user interacts with the app and how each feature meets the rubric expectations.


B1. Create/Update/Delete Vacations


Add a Vacation(B1a):
• From the Vacation List screen, tap the "+" button.
• Enter the vacation title, housing location, start date, and end date.
• Tap Save to add it to the database.
• There is no limit to how many vacations the user can add.


Update a Vacation:
• Select any vacation from the Vacation List.
• The Vacation Detail screen loads with that vacation's information.
• Edit any field and tap Save to update the entry.


Delete a Vacation(B1b):
• On the Vacation Detail screen, select Delete from the menu.
• If the vacation has associated excursions, deletion is prevented.
• A notification appears informing the user that excursions must be deleted first.


B2. Vacation Details


Each vacation includes the following required fields:
• Title
• Hotel/Stay location
• Start date
• End date
These fields appear on the Vacation Details screen when adding or editing a vacation.


B3. Vacation Functional Features
B3a. Display a Detailed View
When the user selects a vacation from the list, the Vacation Detail screen displays:
• Title
• Housing
• Start date
• End date
• List of associated excursions
This screen is used for both viewing and editing.


B3b. Enter, Edit, and Delete Vacation Information
• Tap Add Vacation to create a new vacation.
• Tap an existing vacation to edit it.
• Tap Delete in the menu to remove a vacation.


B3d. End Date Must Be After Start Date
• If the user attempts to save a vacation where the end date is before the start date:
  • A notification appears indicating the error.
  • The vacation cannot be saved until it is corrected.
  
B3e. Vacation Alerts
• From the Vacation Detail menu, users can set:
  • Start date alert
  • End date alert
• Alerts trigger on the selected date, showing:
  • The vacation title
  • Whether the vacation is starting or ending
  
B3f. Sharing Vacation Details
• From the Vacation Detail menu, the user may choose Share Vacation.
• The device's share options appear.
• All vacation details automatically populate in the outgoing message.


B3g. Display Excursions for Each Vacation
• The Vacation Detail screen displays a list of all excursions linked to that vacation.


B3h. Add/Update/Delete Excursions from a Vacation
• Tap Add Excursions to create a new excursion
• Tap an existing excursion to edit or delete it.


B4. Excursion Details
Each excursion includes the following required fields:
• Excursion title
• Excursion date


B5. Excursion Functional Features
• When the user selects an excursion, the Excursion Detail screen displays its title and date.


B5b. Enter, Edit, and Delete Excursion Information
• Users can add excursions via the Add Excursion button.
• Users can edit or delete excursions on the Excursion Detail screen.


B5c. Date Format Validation
• The date picker ensures excursions are formatted using MM/dd/yyyy.


B5d. Excursion Alerts
• From the excursion menu, users may set an alert.
• The alert triggers on the excursion date and displays the excursion title.


B5e. Validate Excursion Date Within Vacation
• The app checks that the excursion date is between:
  • The vacation start date
  • The vacation end date
• If invalid, the app displays a message and prevents saving.


3. Screens Included (Rubric Part C)
The app includes all the required screens:
• Home screen
• Vacation List screen
• Vacation Detail screen
• Excursion List (inside the vacation detail)
• Excursion Detail screen


////////// 3. To which Android version is the signed APK deployed //////////


Minimum Supported Version: Android 8.0 (API Level 26)
Target Version: Android 14 (API Level 36)
Compile Version: Android 14 (API Level 36)


///////// 4. Git Repository Link //////////


https://gitlab.com/wgu-gitlab-environment/student-repos/shahn31/d308-mobile-application-development-android
