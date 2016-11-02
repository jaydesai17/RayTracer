# RayTracer
A library for ray tracing. Not very well docmented.
Requires one external dependency, org.json.*, but that's commented out for now.

You can compile it with the makefile. You can also run with make run, make gif, make jpg, make png.
These will all run a runnable class called "Main" in the client package, you can edit that how you like. Running make gif/jpg/png will move any gif/jpg/png from the src folder to images folder. make run won't move them. make just compiles.

Right now, running Main will render a 100 frame gif of a ball bouncing with some other stuff. It will save it in the src folder though. Run it with make gif.