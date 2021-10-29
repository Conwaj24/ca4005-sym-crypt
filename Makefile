TITLE = Assignment1


${TITLE}.class: Utils.class Assignment1Interface.class

%.class: %.java
	javac -classpath . -d . $<

clean:
	-rm -f *.class

test: ${TITLE}.class
	java ${TITLE}

.PHONY: clean test
