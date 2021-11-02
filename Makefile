TITLE = Assignment1


${TITLE}.class: Utils.class Assignment1Interface.class JavaIsACruelJokeException.class

%.class: %.java
	javac -classpath . -d . $<

IV.txt:
	cat /dev/random | head -c 16 > IV.txt

clean:
	-rm -f *.class

test: ${TITLE}.class IV.txt
	java ${TITLE}

.PHONY: clean test
