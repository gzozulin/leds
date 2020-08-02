# LEDs

<img src="http://gzozulin.com/wp-content/uploads/2020/08/leds.png">

## Navigation
* [MainActivity]()
* [LedsViewModel]()
* [Unit tests]()
* [Instrumented tests]()

## Notes
* In Enterprise setting, I will split all business logic, repository, etc. from the view model, but for this case, it will only make code less readable
* I am not using DI for the same reason
* View model observed with live data since it is the most straightforward way for this app
