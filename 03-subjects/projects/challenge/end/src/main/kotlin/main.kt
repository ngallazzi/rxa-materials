/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject

fun main(args: Array<String>) {

  exampleOf("PublishSubject") {

    val subscriptions = CompositeDisposable()

    val dealtHand = PublishSubject.create<List<Pair<String, Int>>>()

    fun deal(cardCount: Int) {
      val deck = cards
      var cardsRemaining = 52
      val hand = mutableListOf<Pair<String, Int>>()

      (0 until cardCount).forEach { _ ->
        val randomIndex = (0 until cardsRemaining).random()
        hand.add(deck[randomIndex])
        deck.removeAt(randomIndex)
        cardsRemaining -= 1
      }

      // Add code to update dealtHand here
      if (points(hand) > 21) {
        dealtHand.onError(HandError.Busted())
      } else {
        dealtHand.onNext(hand)
      }
    }

    // Add subscription to dealtHand here
    val subscription = dealtHand.subscribeBy(
            onNext = { hand -> println("${cardString(hand)} for ${points(hand)} points") },
            onError = { error -> println(error) }
    )

    subscriptions.add(subscription)

    deal(3)
  }
}