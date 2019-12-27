package state

import kotlin.properties.Delegates

sealed class WorkoutState {

    data class PrepareState(val exercise: Exercise) : WorkoutState()
    data class ExerciseState(val exercise: Exercise) : WorkoutState()
    object DoneState : WorkoutState()
}

fun List<Exercise>.toStates(): List<WorkoutState> =
    flatMap { exercise ->
        listOf(
            WorkoutState.PrepareState(exercise),
            WorkoutState.ExerciseState(exercise)
        )
    } + WorkoutState.DoneState

data class Exercise(val description: String)

fun main() {
    var state: WorkoutState by
    Delegates.observable(emptyList<Exercise>().toStates().first()) { _, _, new ->
        updateView(new)
    }

    state = WorkoutState.PrepareState(Exercise("Yoga"))
}

fun updateView(workoutState: WorkoutState) {
    println("$workoutState")
}