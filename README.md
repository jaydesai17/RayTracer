# RayTracer
A library for ray tracing. Not very well docmented.
Requires one external dependency, org.json.*, but that's commented out for now.

You can compile it with the makefile. You can also run with make run.
This will run a runnable class called "Main" in the client package, you can edit that how you like.

Right now, running Main will render a 100 frame gif of a ball bouncing with some other stuff.

One little annoying thing is that if you make a runnable class to render a gif/image (which you should do in the client folder) then it will be rendered into the src folder if you don't specify the path.

