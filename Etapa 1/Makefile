
JAVAC=javac
JAVA=java

SOURCES=Estado.java Inventario.java Item.java Main.java Mascota.java
CLASSES=$(SOURCES:.java=.class)

all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class

run: all
	$(JAVA) Main config.csv
