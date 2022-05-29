import observer.observer.DataObserver
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
        val testDataObserver = DataObserver<Int>{ actualValue = it }
        val testObservableWithLifeCycle = Observable<Int>()
            .apply { subscribe(testDataObserver) }
            .also { it.setValue(expectedValue) }
        assertThat(actualValue).isEqualTo(expectedValue)
    }

    @Test
    fun `unsubscribe후 해당 Listener 값 미변경 확인`() {
        var actualValue = NOT_CHANGED_VALUE
        val firstChangedValue = Int.MIN_VALUE
        val secondChangedValue = Int.MAX_VALUE
        val testDataObserver = DataObserver<Int> { actualValue = it }
        val testObservableWithLifeCycle = Observable<Int>()
            .apply { subscribe(testDataObserver) }
            .also { it.setValue(firstChangedValue) }

        assertThat(actualValue).isEqualTo(firstChangedValue)

        testObservableWithLifeCycle.unsubscribe(testDataObserver)

        testObservableWithLifeCycle.setValue(secondChangedValue)

        assertThat(actualValue)
            .isEqualTo(firstChangedValue)
            .isNotEqualTo(secondChangedValue)
    }
    companion object {
        const val NOT_CHANGED_VALUE = 0
    }
}
