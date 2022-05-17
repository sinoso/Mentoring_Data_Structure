import lifeCycle.LifeCycleState
import lifeCycle.LifeCycle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LifeCycleTest {
    @Test
    fun `LifeCycle changeState 시 state값 변경`() {
        val lifecycle = LifeCycle(LifeCycleState.LIVE)
        fun changeAndCheck(changedLifeCycleState: LifeCycleState) {
            lifecycle.changeState(changedLifeCycleState)
            assertThat(lifecycle.state).isEqualTo(changedLifeCycleState)
        }
        changeAndCheck(LifeCycleState.STOP)
        changeAndCheck(LifeCycleState.DESTROYED)
    }

    @Test
    fun `bindLifeCycle 후 상태 변경값을 전송 한다`() {
        val lifecycle = LifeCycle(LifeCycleState.LIVE)
        var changedByBinding: LifeCycleState = LifeCycleState.LIVE
        lifecycle.addListener { changedByBinding = it }

        fun changeAndCheck(changedLifeCycleState: LifeCycleState) {
            lifecycle.changeState(changedLifeCycleState)
            assertThat(changedByBinding).isEqualTo(changedLifeCycleState)
        }

        changeAndCheck(LifeCycleState.STOP)
        changeAndCheck(LifeCycleState.DESTROYED)
    }

    @Test
    fun `LifeCycle이 DESTROYED로 변경 될경우 Listener를 제거한다`() {
        val lifecycle = LifeCycle(LifeCycleState.LIVE)
        val testListenerSize = 2
        val emptySize = 0
        val testListener =
            List(testListenerSize) { LifeCycle.OnChangeListener {} }

        fun LifeCycle.numberOfListeners() = getListeners().size

        testListener.forEach(lifecycle::addListener)

        assertThat(lifecycle.numberOfListeners()).isEqualTo(testListenerSize)

        lifecycle.changeState(LifeCycleState.DESTROYED)
        assertThat(lifecycle.numberOfListeners()).isEqualTo(emptySize)
    }
}
