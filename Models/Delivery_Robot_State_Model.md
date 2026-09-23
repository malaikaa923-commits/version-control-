# Delivery Robot State Model

## States

- **Idle:** Robot is waiting for a delivery request.
- **Assigned:** A delivery request has been assigned.
- **Navigating:** Robot is moving toward the pickup or delivery location.
- **Delivering:** Robot has the package and is completing the delivery.
- **Obstacle:** Robot is temporarily stopped because an obstacle is detected.
- **Charging:** Robot is charging because the battery is low.
- **Error:** Robot has detected a critical fault.
- **Completed:** Delivery has been successfully completed.

## State Flow

Idle → Assigned → Navigating → Delivering → Completed → Idle

Exception states:

Navigating → Obstacle → Navigating  
Any active state → Charging → previous active state  
Any active state → Error
