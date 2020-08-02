package com.gzozulin.leds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class LedsViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test(expected = IllegalStateException::class)
    fun `Should not accept wrong input`() {
        LedViewModel().guess('d')
    }

    @Test
    fun `Should not win with incorrect sequence`() {
        val viewModel = LedViewModel()
        viewModel.answer = listOf('a', 'a', 'a')
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('b'), `is`(false))
        assertThat(viewModel.guess('c'), `is`(false))
    }

    @Test
    fun `Should win with correct sequence`() {
        val viewModel = LedViewModel()
        viewModel.answer = listOf('a', 'b', 'c')
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('b'), `is`(false))
        assertThat(viewModel.guess('c'), `is`(true))
    }

    @Test
    fun `Should reset after 3 attempts`() {
        val viewModel = LedViewModel()
        viewModel.answer = listOf('a', 'b', 'c')
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('a'), `is`(false))
        assertThat(viewModel.guess('b'), `is`(false))
        assertThat(viewModel.guess('c'), `is`(true))
    }
}