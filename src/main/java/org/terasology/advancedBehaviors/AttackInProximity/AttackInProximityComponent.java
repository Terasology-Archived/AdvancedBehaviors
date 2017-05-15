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
package org.terasology.advancedBehaviors.AttackInProximity;

import org.terasology.entitySystem.Component;

/**
 * If this component is attached to an NPC entity it will exhibit the attack-in=proximity behavior
 * When a player enters a nearby area defined by FindNearbyPlayers, the NPC will run with a speed of
 * `speedMultiplier`*normalSpeed to attack the player until the player escapes the NPC's range.
 * When the player reaches a greater distance, the NPC stops.
 */
public class AttackInProximityComponent implements Component {
    // Speed factor by which attack speed increases
    public float speedMultiplier = 1.2f;
}