# Application

Pellego android development

## Testing

### Unit Tests

Unit tests are located in the Pellego/app/src/test/java/com/example/pellego/ui/quiz folder by default. Unit tests are run in the CI/CD testing pipeline.

### Emulated Instrumentation Tests

Espresso instrumented tests are located in the Pellego/app/src/androidTest/java/com/example/pellego folder. Instrumented tests require an emulator and should be ran locally before pushing up for a merge request to make sure there were no breaking changes. If your MR requires changes to the UI, you may need to update/re-record a test to reflect the changes. 

To run instrumented tests, right click on the test folder and click run all. 

To record new espresso instrumented test in Android Studio go to Run->Record Espresso Test.
