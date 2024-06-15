# In or Out Program Documentation 🎨

## Introduction
**In or Out** is a small program built using Java and a GUI framework. The primary function of this program is to allow users to determine whether a specific point is inside or outside a given shape.

### Point-in-Polygon Algorithm
The program uses the Point-in-Polygon (PIP) algorithm to determine if a point is inside or outside a shape. This algorithm is widely used in various fields such as computer graphics, geographic information systems (GIS), and game development to handle collision detection, map rendering, and more.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/RecursiveEvenPolygon.svg/1920px-RecursiveEvenPolygon.svg.png" alt="Creating shapes freely" width="50%">


## Key Features ✨
- **Shape Creation**: Users can freely create shapes.
- **Point Check**: Users can determine whether a created point is inside or outside the created shape.

## Usage 🛠️

### Creating Shapes
After running the program, users can draw shapes freely using the mouse. Each point can be removed by clicking on it and moved by dragging.

<img src="https://github.com/seunghyeonlee9661/InorOut/assets/101535408/cd28fad2-f729-4226-b057-4a65e734ee18" alt="Creating shapes freely" width="50%">

### Checking Points
Once the shape is completed, users can create points and determine whether these points are inside or outside the shape.

<img src="https://github.com/seunghyeonlee9661/InorOut/assets/101535408/89e34499-d001-4b4a-810e-577cc310b6af" alt="Creating a shape and generating points to check inside or outside" width="50%">

## Code Example 💻
The core functionality for determining and outputting whether a point is inside or outside a shape is implemented as follows:

```java
// Method to check if a point is inside a shape using the Point-in-Polygon algorithm
public static boolean isPointInsideShape(Point myPoint, List<Point> points, JLabel infoLabel) {
    // Counting the number of intersections
    int cross = 0;
    // Loop through the points to check intersections
    for (int i = 0; i < points.size(); i++) {
        int j;
        if (i == points.size() - 1)
            j = 0;
        else
            j = i + 1;
        // Check if the point crosses the line segment
        if (points.get(i).getY() > myPoint.getY() != points.get(j).getY() > myPoint.getY()) {
            int jx = points.get(j).getX();
            int jy = points.get(j).getY();
            int ix = points.get(i).getX();
            int iy = points.get(i).getY();
            // If the point crosses the line segment, increase the intersection count
            if (myPoint.getX() < ((jx - ix) * (myPoint.getY() - iy) / (jy - iy) + ix)) {
                cross++;
            }
        }
    }
    // If the number of intersections is odd, the point is inside
    if (cross % 2 > 0)
        infoLabel.setText("Inside!");
    // If the number of intersections is even, the point is outside
    else
        infoLabel.setText("Outside!");
    return cross % 2 > 0;
}
