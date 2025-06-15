#  API Test Automation Framework

This project is a **REST Assured-based API Test Automation Framework** using **Cucumber BDD + TestNG**, designed for scalable, readable, and maintainable API test execution. It integrates with reporting tools and supports dynamic test data, schema validation, and reusable components for real-world API workflows.

---

##  Framework Components

###  1. **Test Execution (TestNG XML)**

* Uses `testng.xml` to run the `TestRunner` class.
* Supports grouping, parallel execution, and suite-level control.

###  2. **Feature Files**

* Written in **Gherkin syntax**.
* Define test scenarios using Given/When/Then for better readability.


### 3. **Step Definitions**

* Maps Gherkin steps to Java code.
* Calls utility methods and manages test flow.

---

##  Core Utilities

###  `APIHelper.java`

* Central class to send API requests.
* Handles base URI setup, authentication, header injection.
* Provides methods for response validation, extracting userId, and conditional cleanup (`DELETE` if needed).

###  `JsonReader.java`

* Reads scenario-specific test data from external JSON files.
* Easily supports data-driven tests.

###  `ConfigReader.java`

* Reads config values from `config.properties` (like baseURI, username, password).
* Makes the framework environment-agnostic.

### `RandomGenerator.java`

* Generates dynamic data such as:

  * Unique emails
  * Random contact numbers
* Prevents duplication issues in test runs.

### `TestDataStorage.java`

* Stores `userId` `userFirstName` at runtime.
* Enables **chained test scenarios** across multiple requests.

---

##  Model Layer (POJO)

* Java classes used for serializing/deserializing JSON payloads.
* Ensures type safety and easy request/response body mapping.

---

##  Hooks

* Contains `@Before`  for:


  * Logging request/response for debugging

---

##  JSON Schema Validation

* Uses `json-schema-validator` library.
* Ensures API responses conform to expected schemas.
* Promotes contract testing and early failure detection.

---

##  Reports

This framework generates multiple reports to support debugging and traceability:

| Report Type          | Description                                         |
| -------------------- | --------------------------------------------------- |
| **Extent Report**    | Rich HTML UI with step-by-step test logs            |
| **Cucumber Report**  | Scenario-based reporting                            |
| **ChainTest Report** | Custom HTML report for chained scenario validations |
| **Allure Report**    | Interactive report with attachments, steps, trends  |

---

##  Tools & Technologies

* Java
* REST Assured
* TestNG
* Cucumber BDD
* Jackson (JSON handling)
* JSON Schema Validator
* Maven (Dependency Management)
* Extent Reports
* Allure 

---

##  Project Structure 

```
src/test/java/
│
├── features/                  → Feature files in Gherkin
├── stepdefinitions/           → Step definitions for feature steps
├── hooks/                     → Cucumber Hooks
├── pojo/                      → Request/response POJOs
├── utilities/
│   ├── APIHelper.java         → Request, validation, schema, etc.
│   ├── JsonReader.java
│   ├── ConfigReader.java
│   ├── RandomGenerator.java
│   └── TestDataStorage.java
├── testrunner/                → TestNG runner
└── resources/
    ├── testdata.json          → Input data for scenarios
    ├── config.properties
    └── schemas/               → JSON schema files
```

---

##  How to Run Tests

1. **Via TestNG XML:**

   ```bash
   mvn  clean test -DsuiteXmlFile=testng.xml
   ```

2. **Reports are generated at:**

   * `target/extent-reports/`
   * `target/cucumber-reports/`
   * `target/chaintest-report/`

---

## Why Cucumber for API Testing?

Although Cucumber is popular for UI testing, here it is used for:

* Clear behavior-driven documentation.
* Better collaboration with non-developers (QA, BA).
* Seamless step reuse across scenarios.

---

Feel free to contribute or customize the framework to suit your needs!
