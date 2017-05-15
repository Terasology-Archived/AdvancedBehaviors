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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.advancedBehaviors.FindNearbyPlayers.FindNearbyPlayersComponent;
import org.terasology.advancedBehaviors.UpdateBehaviorEvent;
import org.terasology.assets.management.AssetManager;
import org.terasology.engine.Time;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.behavior.BehaviorComponent;
import org.terasology.logic.behavior.asset.BehaviorTree;
import org.terasology.logic.characters.CharacterMovementComponent;
import org.terasology.pathfinding.components.FollowComponent;
import org.terasology.registry.In;

@RegisterSystem(RegisterMode.AUTHORITY)
public class FollowInProximitySystem extends BaseComponentSystem {
    private static final Logger logger = LoggerFactory.getLogger(FollowInProximitySystem.class);

    @In
    private Time time;
    @In
    private AssetManager assetManager;

    @ReceiveEvent(priority = EventPriority.PRIORITY_HIGH, components = FollowInProximityComponent.class)
    public void onUpdateBehaviorAttack(UpdateBehaviorEvent event, EntityRef entity, FollowInProximityComponent followInProximityComponent, FindNearbyPlayersComponent findNearbyPlayersComponent) {
        if (findNearbyPlayersComponent.charactersWithinRange != null && findNearbyPlayersComponent.charactersWithinRange.size() > 0) {
            event.consume();
            EntityRef someCharacterWithinRange = findNearbyPlayersComponent.charactersWithinRange.get(0);
            FollowComponent followComponent = new FollowComponent();
            followComponent.entityToFollow = someCharacterWithinRange;
            entity.addOrSaveComponent(followComponent);
            // Start hostile behavior, when an entity enters nearby
            BehaviorComponent behaviorComponent = entity.getComponent(BehaviorComponent.class);
            behaviorComponent.tree = assetManager.getAsset("AdvancedBehaviors:friendlyFollow", BehaviorTree.class).get();
            logger.info("Changed behavior to Follow");
            // Increase speed by multiplier factor
            CharacterMovementComponent characterMovementComponent = entity.getComponent(CharacterMovementComponent.class);
            characterMovementComponent.speedMultiplier = followInProximityComponent.speedMultiplier;
            entity.saveComponent(characterMovementComponent);
            entity.saveComponent(behaviorComponent);
        } else {
            if (entity.hasComponent(FollowComponent.class)) {
                entity.removeComponent(FollowComponent.class);
            }
        }
    }
}
