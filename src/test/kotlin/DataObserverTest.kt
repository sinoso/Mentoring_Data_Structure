import observer.observer.DataObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DataObserverTest {
    @ParameterizedTest
    @ValueSource(ints = [Int.MIN_VALUE, Int.MAX_VALUE])
    fun `onChange 호출에 따른 값 변경 확인`(expectedValue: Int) {
        var actualValue = NOT_CHANGED_VALUE
        val testDataObserver = DataObserver<Int> { actualValue = it }
        testDataObserver.onChange(expectedValue)
        assertThat(actualValue).isEqualTo(expectedValue)
    }
    companion object{
        const val NOT_CHANGED_VALUE = 0
    }
}
