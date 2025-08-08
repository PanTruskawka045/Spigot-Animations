# 🎬 Spigot-Animations

An animation library for Spigot plugins, designed to make creating chained, and parallel animations easy and fun!
Despite the name, this library can be used in any Java project, not just Spigot plugins.

---

## ✨ Features

- Chainable animation API
- Parallel and joined animations
- Value easing functions (linear, quadratic, etc.)
- Conditional, and notification-based frames

---

## 🚀 Example Usage

### Animation Setup: 
```java
//Create instance of AnimationManager
final AnimationManager animationManager = new AnimationManager();

//Some kind of tick function.
//On a Bukkit plugin, use a scheduler to schedule a repeating task.
void tick(){
    animationManager.tick();
}
//Spigot Example:
Bukkit.getScheduler().runTaskTimer(plugin, animationManager::tick, 0L, 1L);
```

### Animation example usage:
```java
    animationManager
        .newAnimation().
        .then(() -> {
            //Do something
        })
        .repeat(5, () -> {
            //Do something 5 times, one tick after another
        })
        .easeFunction(0, 100, EaseFunctions.LINEAR, 11, number -> {
            //Do something with the number.
            //This will be called 11 times, with the number going from 0 to 100
            //The number will be calculated using the LINEAR easing function. (0, 10, 20, etc.)
        })
        .finish(); //Finish animation

```

### Example usage of 3D point and space classes

```java
// Create points
Point p1 = new Point(0, 0, 0);
Point p2 = new Point(5, 5, 5);

// Shift a point
p1.shift(1, 2, 3); // p1 is now (1, 2, 3)

// Rotate a point around axes
p2.rotateX((float)Math.PI / 2);
p2.rotateY((float)Math.PI / 4);
p2.rotateZ((float)Math.PI / 6);

// Calculate distance between points
float dist = p1.distance(p2);

// Create a 3D space and add points
Space3D space = new Space3D();
space.addPoint(p1).addPoint(p2);

// Shift all points in space
space.shiftAll(2, 0, -1);

// Scale all points in space
space.scale(2.0f);

// Get all points within a range
List<Point> inRange = space.allInRange(new Point(0, 0, 0), new Point(10, 10, 10));

// Draw a line and a circle in space
space.drawLine(new Point(0, 0, 0), new Point(10, 0, 0), 1.0f);
space.drawCircle(new Point(0, 0, 0), 5.0f, 0.5f);

space.getPoints().forEach(point -> {
    //Do something with each point  
});

```

---

## 🛠️ Getting Started

Add the library to your Spigot plugin (or whatever you're working on) and start animating!

---

## 📬 Contributing

Pull requests and suggestions are welcome!

---

## 📄 License

This project is licensed under the MIT License.  
See the [LICENSE](./LICENSE) file for details.
