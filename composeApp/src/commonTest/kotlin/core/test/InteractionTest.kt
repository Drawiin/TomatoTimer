package core.test

import core.arch.InteractionModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

/**
 * Opt-in annotation for the ExperimentalCoroutinesApi.
 * This function is used to run interaction tests.
 *
 * @param S The type of the state.
 * @param A The type of the action.
 * @param E The type of the effects.
 * @param model The interaction model to be tested.
 * @param block The test block to be executed.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <S, A, E> runInteractionTest(
    model: InteractionModel<S, A, E>,
    block: InteractionTestScope<S, E>.() -> Unit
) = runTest {
    // A mutable list of states.
    val states = mutableListOf<S>()
    // A mutable list of effects.
    val effects = mutableListOf<E>()
    // A list of jobs that collect states and effects.
    val jobs = listOf(
        launch(UnconfinedTestDispatcher()) { model.state.collect { states.add(it) } },
        launch { model.effects.collect { effects.add(it) } }
    )
    // Execute the test block.
    InteractionTestScopeImpl(this, states, effects).block()

    // Cancel all jobs after the test block is executed.
    jobs.forEach { it.cancel() }
}

/**
 * This is an interface for the interaction test scope.
 *
 * @param S The type of the state.
 * @param E The type of the effects.
 */
interface InteractionTestScope<S, E> {
    // The test scope.
    val testScope: TestScope
    // A list of states.
    val states: List<S>
    // A list of effects.
    val effects: List<E>
}

/**
 * This is an implementation of the InteractionTestScope interface.
 *
 * @param S The type of the state.
 * @param E The type of the effects.
 */
internal class InteractionTestScopeImpl<S, E>(
    // The test scope.
    override val testScope: TestScope,
    // A list of states.
    override val states: List<S>,
    // A list of effects.
    override val effects: List<E>
) : InteractionTestScope<S, E>
