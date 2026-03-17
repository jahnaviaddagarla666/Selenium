# Selenium Login/Signup Demo

This project contains a simple **login** and **signup** HTML pages and **Selenium** automation tests written in **Java**.

## 🧩 What’s Included

- `src/main/resources/pages/login.html` - A minimal login page with client-side validation
- `src/main/resources/pages/signup.html` - A minimal signup page with client-side validation
- `src/test/java/com/example/selenium/LoginSignupTest.java` - Selenium tests that open the pages locally, fill fields, submit forms, and validate the displayed messages

## 🚀 Running the Tests

### Requirements
- Java 17 or newer
- Maven
- Chrome browser installed (ChromeDriver is auto-managed via WebDriverManager)

### Run tests (headless)

```bash
mvn test
```

### Run tests (with browser window visible)

```bash
mvn test -Dshow.browser=true
```

## 🛠️ How it works

The tests load the static HTML pages using a `file://` URL and interact with them using Selenium WebDriver.

- Successful login and signup flows are verified by asserting the success message text.
- Failure cases (e.g., short password) are also validated.
