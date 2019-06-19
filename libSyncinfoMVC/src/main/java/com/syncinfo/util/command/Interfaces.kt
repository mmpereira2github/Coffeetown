package com.syncinfo.util.command

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by mmartins on 2018-03-21.
 */
interface Command

sealed class Timer(val time:Long, val timeUnit: TimeUnit) {
    object None: Timer(Long.MAX_VALUE, TimeUnit.HOURS)
    object WaitForever: Timer(Long.MAX_VALUE, TimeUnit.HOURS)
}

sealed class ExecutionStrategy {

    class Sync<Input,Output>: ExecutionStrategy() {
        fun execute(action: ()->Output,
                    timeout: Timer = Timer.None,
                    onTimeout: ()->Output = {
                        throw TimeoutException("SyncExecutionStrategy: Timeout " +
                                "${TimeUnit.MICROSECONDS.convert(timeout.time,
                                        timeout.timeUnit)}")}): Output {
            if (timeout === Timer.None)
                return action()
            else {
                val countDownLatch = CountDownLatch(1)
                var output: Output? = null
                Thread({
                    output = action()
                    countDownLatch.countDown()
                }).start()

                return if (countDownLatch.await(timeout.time, timeout.timeUnit)) {
                    output!!
                }
                else {
                    onTimeout()
                }
            }
        }
    }

    abstract class Async<Input>(val context: Context): ExecutionStrategy() {
        interface Context {
            val executorService: ExecutorService
        }

        class FireAndForget<Input>(context: Context, private val action: (Input)->Unit): Async<Input>(context) {
            private var submissionConfirmation: Boolean = true
            fun withoutSubmissionConfirmation(): FireAndForget<Input> {
                this.submissionConfirmation = false
                return this
            }

            fun execute(input: Input): Unit {
                try {
                    context.executorService.submit({ action(input) })
                }
                catch (e: Throwable) {
                    if (submissionConfirmation) throw e
                }
            }
        }

        class WithCallback<Input,Output>(context: Context,
                                         private val action: (Input)->Output,
                                         private val callback: (Output)->Unit): Async<Input>(context) {
            private var submissionConfirmation: Boolean = true
            fun withoutSubmissionConfirmation(): WithCallback<Input,Output> {
                this.submissionConfirmation = false
                return this
            }

            fun execute(input: Input, timeout: Timer = Timer.None): Unit {
                try {
                    context.executorService.submit({ callback(action(input)) })
                }
                catch (e: Throwable) {
                    if (submissionConfirmation) throw e
                }
            }
        }

        class WithHandler<Input,Output>(context: Async.Context,
                                        private val action: (Input)->Output): Async<Input>(context) {
            class Handler<Output>() {
                internal val countDownLatch = CountDownLatch(1)
                internal var output: Output? = null
                fun await(timeout: Timer = Timer.None): Boolean =
                        if (timeout === Timer.None) {
                            countDownLatch.await()
                            true
                        }
                        else {
                            countDownLatch.await(timeout.time, timeout.timeUnit)
                        }
            }

            fun execute(input: Input, timeout: Timer = Timer.None): Handler<Output> {
                val handler = Handler<Output>()
                Thread({
                    handler.output = action(input)
                    handler.countDownLatch.countDown()
                }).start()

                return handler
            }
        }
    }

    interface Factory<T> {
        fun create
        fun <Input, Output> sync(): Sync<Input, Output>
    }
}

