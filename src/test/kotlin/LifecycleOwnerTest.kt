import lifeCycle.LifeCycleState
import lifeCycle.LifecycleOwner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LifecycleOwnerTest {
    @Test
    fun `LifeCycle changeState 시 state값 변경`() {
        val lifecycleOwner = LifecycleOwner(LifeCycleState.LIVE)
        fun changeAndCheck(changedLifeCycleState: LifeCycleState) {
            lifecycleOwner.changeState(changedLifeCycleState)
            assertThat(lifecycleOwner.state).isEqualTo(changedLifeCycleState)
        }
        changeAndCheck(LifeCycleState.STOP)
        changeAndCheck(LifeCycleState.DESTROYED)
    }

    @Test
    fun `Empty LifeCycleOwner를 가져올 수 있다`() {
        val emptyLifecycleOwner = LifecycleOwner.getEmptyOwner()
        assertThat(emptyLifecycleOwner).isEqualTo(LifecycleOwner.NONE)
    }

    @Test
    fun `bindLifeCycle 후 상태 변경값을 전송 한다`() {
        val lifecycleOwner = LifecycleOwner(LifeCycleState.LIVE)
        var changedByBinding: LifeCycleState = LifeCycleState.LIVE
        lifecycleOwner.bindLifeCycle { changedByBinding = it }

        fun changeAndCheck(changedLifeCycleState: LifeCycleState) {
            lifecycleOwner.changeState(changedLifeCycleState)
            assertThat(changedByBinding).isEqualTo(changedLifeCycleState)
        }

        changeAndCheck(LifeCycleState.STOP)
        changeAndCheck(LifeCycleState.DESTROYED)
    }

    @Test
    fun `LifeCycle이 DESTROYED로 변경 될경우 Listener를 제거한다`() {
        val lifecycleOwner = LifecycleOwner(LifeCycleState.LIVE)
        val testListenerSize = 2
        val emptySize = 0
        val testListener = List<(LifeCycleState) -> Unit>(testListenerSize) { {} }
        fun LifecycleOwner.numberOfListeners() = getLifeCycleListeners().size

        testListener.forEach(lifecycleOwner::bindLifeCycle)

        assertThat(lifecycleOwner.numberOfListeners()).isEqualTo(testListenerSize)

        lifecycleOwner.changeState(LifeCycleState.DESTROYED)
        assertThat(lifecycleOwner.numberOfListeners()).isEqualTo(emptySize)
    }
}
