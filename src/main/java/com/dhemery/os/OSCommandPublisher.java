package com.dhemery.os;

/**
 * Publishes OSCommand execution events to {@link OSCommandSubscriber}s.
 */
public interface OSCommandPublisher {
    /**
     * Enroll a new subscriber.
     * As long as the subscriber remains subscribed,
     * the publisher informs the subscriber of OSCommand events.
     * @param subscriber a subscriber to inform of OSCommand events
     */
    void subscribe(OSCommandSubscriber subscriber);

    /**
     * Unsubscribe a subscriber.
     * As long as the subscriber remains unsubscribed,
     * the publisher does not inform the subscriber of OSCommand events.
     * @param subscriber a subscriber not to inform of OSCommand events
     */
    void unsubscribe(OSCommandSubscriber subscriber);
}
