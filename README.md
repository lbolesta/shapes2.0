A simple program that creates thre shapes on the screen. No touch activities.

# Running the Application

In Android Studio: `Run > Run app`

# Running the Tests

## Unit tests

In Android Studio:

* `View > Tool Windows > Build Variants`
* `Test Artifact: Unit Tests`
* right-click on `app/java/edu...shapes (test)`, then choose `Run Tests in edu...`

You can also use Gradle:

    $ ./gradlew testDebug

You can view the resulting test reports in HTML by opening this file in your browser:

    app/build/reports/tests/debug/index.html

(So far, this example does not include any Android instrumentation tests.)
