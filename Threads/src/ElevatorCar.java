public class ElevatorCar extends Thread {
    int floor = 0;

    public int getFloor() {
        return floor;
    }

    enum Tour {
        UP,
        DOWN
    }

    Tour tour = Tour.UP;

    enum Movement {
        STOP,
        MOVING
    }

    Movement movementState = Movement.STOP;

    public void run() {
        for (; ; ) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (movementState == Movement.STOP && tour == Tour.DOWN) {
                if (!ElevatorStops.getInstance().hasStopBelow(floor)) tour = Tour.UP;
                else movementState = Movement.MOVING;
                continue;
            }

            if (movementState == Movement.STOP && tour == Tour.UP) {
                if (!ElevatorStops.getInstance().hasStopAbove(floor)) tour = Tour.DOWN;
                else movementState = Movement.MOVING;
                continue;
            }

            if (movementState == Movement.MOVING && tour == Tour.DOWN) {
                if (floor > ElevatorStops.getInstance().getMinSetFloor()) {
                    floor--;
                    System.out.println("Floor" + floor);
                } else {
                    movementState = Movement.STOP;
                    tour = Tour.UP;
                }

                if (ElevatorStops.getInstance().whileMovingDownShouldStopAt(floor)
                        || floor == ElevatorStops.getInstance().getMinSetFloor()) {
                    movementState = Movement.STOP;
                    ElevatorStops.getInstance().clearStopDown(floor);
                    System.out.println("STOP");
                }
                continue;
            }

            if (movementState == Movement.MOVING && tour == Tour.UP) {
                if (floor < ElevatorStops.getInstance().getMaxSetFloor()) {
                    floor++;
                    System.out.println("Floor" + floor);
                } else {
                    movementState = Movement.STOP;
                    tour = Tour.DOWN;
                }

                if (ElevatorStops.getInstance().whileMovingUpShouldStopAt(floor)
                        || floor == ElevatorStops.getInstance().getMaxSetFloor()) {
                    movementState = Movement.STOP;
                    ElevatorStops.getInstance().clearStopUp(floor);
                    System.out.println("STOP");
                }
            }
        }
    }
}