# State Transition Table

| Current State | Event / Condition | Action | Next State |
|---|---|---|---|
| Idle | Delivery request received | Assign delivery | Assigned |
| Assigned | Assignment accepted | Start navigation | Navigating |
| Navigating | Pickup location reached | Collect package | Delivering |
| Navigating | Obstacle detected | Stop and wait | Obstacle |
| Obstacle | Obstacle cleared | Resume navigation | Navigating |
| Delivering | Delivery location reached | Drop off package | Completed |
| Completed | Delivery finished | Reset robot | Idle |
| Navigating | Battery low | Stop and start charging | Charging |
| Delivering | Battery low | Stop and start charging | Charging |
| Charging | Battery fully charged | Resume previous task | Navigating |
| Any active state | Critical error detected | Stop robot and report error | Error |
| Error | Error resolved | Restart operation | Idle |

## Transition Rules

1. Every transition is triggered by a specific event or condition.
2. The robot must stop when an obstacle or critical error is detected.
3. A low battery condition sends the robot to the Charging state.
4. After successful delivery, the robot returns to Idle.
