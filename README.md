# RayTracer
A library for ray tracing. Not very well docmented.
Requires one external dependency, org.json.*, but that's commented out for now.

You can compile it with the makefile, but the use the default setting you need to 
have a package called "client" in the src folder. You can also run with make run.
This will run a runnable class called "Main" in the client package, so you need that
and make run will work.

One little annoying thing is that if you make a runnable class to render a gif/image (which you should do in the client folder) then it will be rendered into the src folder if you don't specify the path.

