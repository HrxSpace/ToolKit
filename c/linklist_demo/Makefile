# syspath & tools
BUILD_GCC	 = gcc

# build target
TARGET  := test

# compile option
CFLAGS		+= $(INCLUDES) -Wall

# include path
INCLUDES	+= -I h

# sources path       
SOURCES		+= ${wildcard c/*.c}

all:
	${BUILD_GCC} ${SOURCES} ${CFLAGS} -o ${TARGET}

clean:
	rm *.o *.exe
