/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.advancedBehaviors.Flee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.advancedBehaviors.FleeOnHit.FleeOnHitComponent;
import org.terasology.advancedBehaviors.UpdateBehaviorEvent;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.logic.behavior.tree.Node;
import org.terasology.logic.behavior.tree.Status;
import org.terasology.logic.behavior.tree.Task;
import org.terasology.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3f;

public class CheckFleeStopNode extends Node {
    private static final Logger logger = LoggerFactory.getLogger(CheckFleeStopNode.class);

    public CheckFleeStopNode() {
    }

    public CheckFleeStopTask createTask() {
        return new CheckFleeStopTask(this);
    }

    public static class CheckFleeStopTask extends Task {

        public CheckFleeStopTask(Node node) {
            super(node);
        }

        public Status update(float dt) {
            FleeComponent fleeComponent = this.actor().getComponent(FleeComponent.class);
            EntityRef instigator = fleeComponent.instigator;
            if (instigator == null || !instigator.isActive()) {
                return Status.FAILURE;
            }
            LocationComponent targetLocation = instigator.getComponent(LocationComponent.class);
            if (targetLocation == null) {
                return Status.FAILURE;
            }
            LocationComponent currentLocation = actor().getComponent(LocationComponent.class);
            if (currentLocation == null) {
                return Status.FAILURE;
            }
            Vector3f instigatorLocation = currentLocation.getWorldPosition();
            Vector3f selfLocation = targetLocation.getWorldPosition();
            float currentDistanceSquared = selfLocation.distanceSquared(instigatorLocation);

            float minDistance = fleeComponent.minDistance;
            float minDistanceSquared = minDistance * minDistance;

            // Triggers an updateBehaviorEvent for the entity when it reaches a safe distance from flee instigator
            if (currentDistanceSquared >= minDistanceSquared) {
                fleeComponent.instigator = null;
                this.actor().getEntity().saveComponent(fleeComponent);
                this.actor().getEntity().send(new UpdateBehaviorEvent());
                return Status.FAILURE;
            } else {
                return Status.RUNNING;
            }
        }

        public void handle(Status result) {
        }

        public CheckFleeStopNode getNode() {
            return (CheckFleeStopNode) super.getNode();
        }
    }
}