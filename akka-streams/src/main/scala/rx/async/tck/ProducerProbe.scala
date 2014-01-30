package rx.async
package tck

import spi.{ Subscriber, Subscription }
import rx.async.api.Producer

sealed trait ProducerEvent
case class Subscribe(subscription: Subscription) extends ProducerEvent
case class CancelSubscription(subscription: Subscription) extends ProducerEvent
case class RequestMore(subscription: Subscription, elements: Int) extends ProducerEvent

abstract case class ActiveSubscription[I](subscriber: Subscriber[I]) extends Subscription {
  def sendNext(element: I): Unit
  def sendComplete(): Unit
  def sendError(cause: Exception): Unit
}

trait ProducerProbe[I] extends Producer[I] {
  def expectSubscription(): ActiveSubscription[I]
}
