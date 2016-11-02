default:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java

run:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java && java client.Main

gif:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java && java client.Main && mv *.gif ../images/

png:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java && java client.Main && mv *.png ../images/

jpg:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java && java client.Main && mv *.jpg ../images/
