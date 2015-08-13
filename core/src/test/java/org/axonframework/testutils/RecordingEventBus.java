/*
 * Copyright (c) 2010-2014. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.testutils;

import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * EventBus implementation that does not perform any actions on subscriptions or published events, but records
 * them instead. This implementation is not a stand-in replacement for a mock, but might prove useful in many simple
 * cases.
 *
 * @author Rene de Waele
 * @since 3.0
 */
public class RecordingEventBus implements EventBus {

    private Collection<Cluster> subscriptions = new CopyOnWriteArraySet<>();
    private List<EventMessage<?>> publishedEvents = new ArrayList<>();

    @Override
    public void publish(List<EventMessage<?>> events) {
        publishedEvents.addAll(events);
    }

    @Override
    public void subscribe(Cluster cluster) {
        subscriptions.add(cluster);
    }

    @Override
    public void unsubscribe(Cluster cluster) {
        subscriptions.remove(cluster);
    }

    /**
     * Clears all the events recorded by this Event Bus as well as all subscribed clusters.
     */
    public void reset() {
        publishedEvents.clear();
        subscriptions.clear();
    }

    /**
     * Indicates whether the given <code>cluster</code> is subscribed to this Event Bus.
     *
     * @param cluster The cluster to verify the subscription for
     * @return <code>true</code> if the cluster is subscribed, otherwise <code>false</code>.
     */
    public boolean isSubscribed(Cluster cluster) {
        return subscriptions.contains(cluster);
    }

    /**
     * Returns a Collection of all subscribed Clusters on this Event Bus.
     *
     * @return a Collection of all subscribed Clusters
     */
    public Collection<Cluster> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Returns a list with all events that have been published by this Event Bus.
     *
     * @return a list with all events that have been published
     */
    public List<? extends EventMessage<?>> getPublishedEvents() {
        return publishedEvents;
    }

    /**
     * Returns the number of events published by this Event Bus.
     *
     * @return the number of published events
     */
    public int getPublishedEventCount() {
        return publishedEvents.size();
    }
}