# LEDs

<img src="http://gzozulin.com/wp-content/uploads/2020/08/leds.png">

## Navigation
* [MainActivity](https://github.com/gzozulin/leds/blob/master/app/src/main/java/com/gzozulin/leds/MainActivity.kt)
* [LedsViewModel](https://github.com/gzozulin/leds/blob/master/app/src/main/java/com/gzozulin/leds/LedsViewModel.kt)
* [Unit tests](https://github.com/gzozulin/leds/blob/master/app/src/test/java/com/gzozulin/leds/LedsViewModelTest.kt)
* [Instrumented tests](https://github.com/gzozulin/leds/blob/master/app/src/androidTest/java/com/gzozulin/leds/MainActivityInstrumentedTest.kt)

## Notes
* In Enterprise setting, I will split all business logic, repository, etc. from the view model, but for this case, it will only make code less readable
* I am not using DI for the same reason
* View model observed with live data since it is the most straightforward way for this app
