import {afterEach, assert, beforeEach, describe, expect, it, test, vi} from 'vitest'

// Note: This file is only a short showcase of possible vitest features.
// Testing suite: https://vitest.dev/

test('vitest function check', () => {
    expect(true).toBeTruthy()
    assert.equal("foobar", "foobar")
})

describe('some workflow to test', () => {
    beforeEach(() => {
        // tell vitest we use mocked time
        vi.useFakeTimers()
    })

    afterEach(() => {
        // restoring date after each test run
        vi.useRealTimers()
    })

    it('allows something', () => {
        // set hour within business hours
        const date = new Date(2000, 1, 1, 13)
        vi.setSystemTime(date)

        // test something
    })

    it('disallows purchases outside of business hours', () => {
        // set hour outside business hours
        const date = new Date(2000, 1, 1, 19)
        vi.setSystemTime(date)

        // test something
    })
})