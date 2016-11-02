default:
	cd src && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java

run:
	cd src && mkdir temp && javac geometry/*.java gradients/*.java interactive/*.java threads/*.java fileio/*.java gifs/*.java imgur/*.java scene/*.java utilities/*.java client/*.java && java client.Main && mv temp/* ../images && rm -rf temp

clean:
	cd src && mv temp/* ../images && rm -rf temp