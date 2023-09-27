# MyMail
![App_view][App_view]
## 1. About The Project
This is a Java FX-based email client with a user-friendly GUI. The application allows you to seamlessly manage your Gmail account, offering features for viewing emails (including attachments) and composing new ones. It leverages the latest JavaFX and Java technologies, demonstrating advanced programming techniques.

## 2. Built With

* [![Java][Java.io]][Java-url]
* [![JavaFX][JavaFX.io]][JavaFX-url]

## 3. Features

- **Intuitive Interface**: Our application boasts an intuitive graphical interface for effortless email management.
- **Gmail Integration**: Seamlessly connect the email client to your Gmail account for easy email access.
- **Email Viewing**: View emails from your Gmail account, including any attachments.
- **Email Composition**: Compose new emails directly within the application.Drag and drop markdown and HTML files into Dillinger
- **Advanced JavaFX**: Harness the power of JavaFX with advanced features to ensure robust functionality.

## 4. Installing
 1. Download the latest release at [Releases][Releases].
 2. Run the downloaded installation file.
 3. Run the MyMail.exe from the folder or via the shortcut on the desktop.

You can also run the project from your IDE:

1. Clone this repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Build a JAR file and run it from the folder with JAR file using the following command (please note that you would need to install and configure libraries: JavaFX, Java Mail, and Activation):
```
java --module-path 'path to javafx lib' --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar Email_App.jar

```

## 5. Getting started

> **_SECURITY NOTE:_**  As this project is one of my first experiences, I strongly recommend against using your real Gmail accounts for testing or development. It's advisable to create a separate Gmail account solely for the purpose of testing this application.

To successfully set up and use the application with your Gmail account, follow these steps:
#### 1. Enable 2-Factor Authentication:
First, ensure that your Gmail account is configured with 2-factor authentication. To set this up:
- Log in to your Gmail account in a web browser.
- Click on your profile picture or avatar in the top-right corner.
- Select "Manage your account."
- In the left-hand menu, click on "Security."
- Locate the "2-step verification" section and set up 2-factor authentication as prompted.

#### 2. Generate an App Password:
After enabling 2-factor authentication, you'll need to generate an app-specific password for the email client :
- Follow Google's official instructions provided [here][Google-Account-url] to create an app password.
- During this process, you'll choose "App" and "Other (Custom name)" as the app type and name.
- Google will generate a unique app password for you. Keep this password safe, as you'll need it to log in to the Email Client Application.

#### 3. Log in to the MyMail email client:
Now that you have your app password, follow these steps to log in to the  application:
- Launch the MyMail on your computer.
- When prompted for your Gmail login, use your Gmail email address as the username.
- Instead of your regular Gmail password, enter the app-specific password you generated in the previous step.

You are now ready to use the MyMail to manage your Gmail account.


## 6. Contributing

Any contributions you make are **greatly appreciated**. If you plan to make significant changes, kindly initiate a discussion by creating an issue beforehand.

Feel free to explore and address these issues, which are conveniently listed in the "Issues" tab for your convenience.


[Java.io]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/
[JavaFX.io]: https://img.shields.io/badge/JavaFX-%233f95ea?style=for-the-badge
[JavaFX-url]: https://openjfx.io/
[App_view]:https://i.imgur.com/Ui7LtoT.jpg
[Releases]: https://github.com/DEnsM0/MyMail/releases
[Google-Account-url]:https://support.google.com/accounts/answer/185833