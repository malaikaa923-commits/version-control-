# Delivery Robot Requirements

## Functional Requirements

1. The robot shall start in the **Idle** state.
2. The robot shall accept a delivery request and move to the **Assigned** state.
3. The robot shall navigate to the pickup location.
4. The robot shall move to the **Delivering** state after collecting the package.
5. The robot shall return to **Idle** after successful delivery.
6. The robot shall enter an **Obstacle** state when an obstacle blocks its path.
7. The robot shall resume its previous operation after the obstacle is cleared.
8. The robot shall enter a **Charging** state when its battery is low.
9. The robot shall return to **Idle** after charging is complete.
10. The robot shall enter an **Error** state when a critical fault occurs.

## Main States

- Idle
- Assigned
- Navigating
- Delivering
- Obstacle
- Charging
- Error
- Completed
