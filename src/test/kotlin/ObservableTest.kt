import lifeCycle.LifeCycleState
import lifeCycle.LifeCycleOwner
import observer.Listener
import observer.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ObservableTest {
    @ParameterizedTest
    @ValueSource(ints = [Int.MAX_VALUE, Int.MIN_VALUE])
    fun `subscribe후 해당 Listener에 값 전달 확인`(expectedValue: Int) {
        var actualValue = NOT_CHANGED_VALUE

        val testListener = Listener<Int> { actualValue = it }
        val testObservable = Observable<Int>()
            .apply { subscribe(testListener) }
            .also { it.setValue(expectedValue) }

        assertThat(actualValue).isEqualTo(expectedValue)
    }

    @Test
    fun `unsubscribe후 해당 Listener 값 미변경 확인`() {
        var actualValue = NOT_CHANGED_VALUE
        val firstChangedValue = Int.MIN_VALUE
        val secondChangedValue = Int.MAX_VALUE

        val testListener = Listener<Int> { actualValue = it }
        val testObservable = Observable<Int>()
            .apply { subscribe(testListener) }
            .also { it.setValue(firstChangedValue) }

        assertThat(actualValue).isEqualTo(firstChangedValue)

        testObservable.unsubscribe(testListener)

        testObservable.setValue(secondChangedValue)

        assertThat(actualValue)
            .isEqualTo(firstChangedValue)
            .isNotEqualTo(secondChangedValue)

    }

    @Test
    fun `LifeCycle Live to Stop 변경에 따른 value 전송 변경 확인`() {
        var actualValue = NOT_CHANGED_VALUE
        val changedValueWithStateLive = Int.MIN_VALUE
        val changedValueWithStateStop = Int.MAX_VALUE

        val testListener = Listener<Int> { actualValue = it }
        val lifecycleOwner = LifeCycleOwner(LifeCycleState.LIVE)
        val testObservable = Observable<Int>()
            .apply { subscribe(lifecycleOwner,testListener) }
            .also { it.setValue(changedValueWithStateLive) }

        assertThat(actualValue).isEqualTo(changedValueWithStateLive)
        lifecycleOwner.changeState(LifeCycleState.STOP)

        testObservable.setValue(changedValueWithStateStop)

        assertThat(actualValue)
            .isEqualTo(changedValueWithStateLive)
            .isNotEqualTo(changedValueWithStateStop)
    }

    @Test
    fun `LifeCycle DESTROYED로 변경에 따른 value 전송 변경 확인`() {
        var actualValue = NOT_CHANGED_VALUE
        val changedValueWithStateLive = Int.MIN_VALUE
        val changedValueWithStateStop = Int.MAX_VALUE

        val testListener = Listener<Int> { actualValue = it }
        val lifecycleOwner = LifeCycleOwner(LifeCycleState.LIVE)
        val testObservable = Observable<Int>()
            .apply { subscribe(lifecycleOwner,testListener) }
            .also { it.setValue(changedValueWithStateLive) }

        assertThat(actualValue).isEqualTo(changedValueWithStateLive)
        lifecycleOwner.changeState(LifeCycleState.DESTROYED)

        testObservable.setValue(changedValueWithStateStop)

        assertThat(actualValue)
            .isEqualTo(changedValueWithStateLive)
            .isNotEqualTo(changedValueWithStateStop)
    }

    companion object {
        const val NOT_CHANGED_VALUE = 0
    }
}
