JDKPATH = /usr

LIBPATH = lib/*

CLASSPATH = $(JDKPATH)/lib/classes.zip:.:..:$(LIBPATH)

BINPATH = $(JDKPATH)/bin

JAVAC = $(JDKPATH)/bin/javac -classpath $(CLASSPATH)
JAVA  = $(JDKPATH)/bin/java  -classpath $(CLASSPATH)

PROGS = xx

all: $(PROGS)

compile:src/*/*.java
	$(JAVAC) -cp $(CLASSPATH) -d bin src/*/*.java

xx : compile 
	$(JAVA) -cp $(CLASSPATH):bin tests.RelOperatorTest

clean:
	\rm -f *.class *~ \#* core TRACE