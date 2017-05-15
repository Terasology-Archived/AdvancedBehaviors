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
package org.terasology.advancedBehaviors.FollowInProximity;

import org.terasology.entitySystem.Component;

/**
 * If this component is attached to a WildAnimal entity it will exhibit the attack-on-hit behavior
 * When hit, the animal will run with a speed of `speedMultiplier`*normalSpeed to attack the instigator
 * until it is at a greater distance than `maxDistance` from the damage inflicter- `instigator`.
 * When it reaches a greater distance, the instigator is set to null, and the animal stops.
 */
public class FollowInProximityComponent implements Component {
    // Speed factor by which attack speed increases
    public float speedMultiplier = 0.9f;
}