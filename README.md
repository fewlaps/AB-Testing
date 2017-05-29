# A/B Testing
The simpler A/B Testing library

Create an experiment, set some options for them, and test the user's behaviour asap!

This A/B Testing library just runs on the device. So:
- The setup is simple: just use the library. No need to setup a companion server to run experiments
- Marketing team can't change your lovely app on the fly. Zero bugs created by avoiding app testing

To run experiments, we suggest you to wrap the library with your business needs, and use that wrapper from your app. For example:

```kotlin
object ABTestingWrapper {

    private val EXPERIMENT_PRICE_AT_PURCHASE_FUNNEL = "Buy button label in the 'Be PRO' onboarding"
    private val OPTION_SHOW_BUY_WORD = "Show the 'buy' label"
    private val OPTION_SHOW_PRICE = "Show the SKU price"

    fun init(context: Context) {
        val buyOrPriceAtPurchaseFunnel = Experiment(EXPERIMENT_PRICE_AT_PURCHASE_FUNNEL,
                asList(OPTION_SHOW_BUY_WORD, OPTION_SHOW_PRICE))

        ABTestingAndroid.init(context, listOf(buyOrPriceAtPurchaseFunnel))
    }

    fun showPriceAtPurchaseFunnel(): Boolean {
        return ABTestingAndroid.getCurrentOptionFor(EXPERIMENT_PRICE_AT_PURCHASE_FUNNEL) == OPTION_SHOW_PRICE
    }
}
```

To track results, just call `ABTestingAndroid.getCurrentOptions()` to get all the experiments and the option set to that user. For example, if you track events using Fabric, you could write something like this:

```kotlin
private void populateExperimentCustomAttributes(AnswersEvent event) {
    for (CurrentOption currentOption : ABTestingAndroid.getCurrentOptions()) {
        event.putCustomAttribute("Experiment " + currentOption.getExperiment(), currentOption.getOption());
    }
}
```

# Download

* Grab via Gradle:
```groovy
repositories { jcenter() }

compile 'com.fewlaps.abtesting:abtesting-android:1.1.0'
```

## LICENSE ##

The MIT License (MIT)

Copyright (c) 2017 Fewlaps

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
