# RayTracer
A library for ray tracing. Not very well docmented.
Requires one external dependency, org.json.*, but that's commented out for now.

You can compile it with the makefile. You can also run with make run.
These will all run a runnable class called "Main" in the client package, you can edit that how you like. This will create some temporary folders. It will remove them. Running make clean will clear out and delete the temporary folders if something goes wrong.

Right now, running Main will render a 100 frame gif of a ball bouncing with some other stuff. It will take about 10 minutes to finish.