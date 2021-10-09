# Mandelbrot
Visualizes mandelbrot sets using java swing

## The Mandelbrot set
The calculator is located in the <tt>Mandelbrot</tt> class.<br>
The calculation can be controlled via two parameters
* the maximum number of iterations that are calulated (default is 50)
* the threshold that defines divergency of the function (default is 2.0)

## Application
Execute <tt>main</tt> in the <tt>Application</tt> class.

A swing window opens that shows the blue outline of a madelbrot set.<br>
On the eastern area of the window a plaintext area shows some data about the settings.

### Buttons
On the southern area there's a button pane with the following buttons

* reset - recover the initial state of the image
* x-hair toggles the visibility of the green reticle
* it +5 increases the maximum number of iterations by 5
* it -5 decreases the maximum number of iterations by 5
* light +5 is without function
* light -5 is without function

### Mouse control
* Clicking anywhere in the image area centers the image around this point.
* Scrolling up zooms into the image
* Scrolling down zooms out of the image

The zoom tries to keep the center of the image where it is.
This is not always accurate, especially when you zoom deep into the image.
Calculation might be improved in the future.  

## Colouring
The method <tt>Fractal.toColor(int)</tt> calculates the color from the number of iterations
after which the function passed the threshold.<br>
Currently the image is monochrome with different shades of blue.
Non divergent points are coloured black.
