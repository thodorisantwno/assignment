Selenium test scenarios for Xrysi Efkairia

Introduction

The solution developed for this challenge focuses on creating an automated test script for property searches on xe.gr. Below are the key features and parameters of the implementation:

Programming Language: Java.
Technologies: Selenium WebDriver, TestNG.
Objective: Create a test script to ensure the functionality of property searches with specific filters.

Solution Overview

1. Working Environment

WebDriver: ChromeDriver was used for browser automation.
Execution Environment: The script was executed using Eclipse IDE.

2. Functionality

The script performs the following:

Search for “Pagrati”:

Enter the search term in the search box.
Select all suggested areas from the autocomplete.

Filtering Results:

Apply filters for price (€200-€700) and area (75m2-150m2).

Validating Results:

Verify that:
The price of each listing is within the specified range.
The area of each listing is within the specified range.
No listing contains more than 30 images.

Sorting:

Sort the results by descending price.
Verify the correctness of the sorting.

Bonus – Contact Phone Validation:

Confirm that the phone number is initially hidden.
Verify it becomes visible only after clicking the reveal button.

Code Architecture

Environment Setup:

@BeforeClass prepares the testing environment and opens the browser.
@AfterClass ensures the browser closes even in case of a failure.

Main Test:

Embedded assertions for each step, with detailed validations.
Dynamic elements and popups handled using WebDriverWait.

Reports:

TestNG capabilities are used for generating execution reports.

Executing the Solution

Preparation

Installation:

Ensure you have JDK and ChromeDriver installed.

Use Eclipse IDE to run the script.

Execution:

Open the project in Eclipse.

Run the PropertySearchTest class as a TestNG test.

GitHub Accessibility

The solution is uploaded to a GitHub repository, making it easily accessible and executable by others. It includes:

The source code.

A README.md file with execution instructions and requirements.
