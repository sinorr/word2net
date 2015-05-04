#compilation only works with g++ v4.4
CC = g++ -Wall
#CFLAGS = -g -Wall -O3 -ffast-math -DHAVE_INLINE -DGSL_RANGE_CHECK_OFF
# CFLAGS = -g -Wall
LDFLAGS = -lgsl -lm -lgslcblas

GSL_INCLUDE = /usr/local/lib/include
GSL_LIB = /usr/local/lib/lib

LSOURCE =  utils.cpp corpus.cpp state.cpp hdp.cpp main.cpp
LHEADER =  utils.h corpus.h hdp.h state.h

hdp: $(LSOURCE) $(HEADER)
	#$(CC) $(LSOURCE) -o $@ $(LDFLAGS)
	$(CC)  -I$(GSL_INCLUDE) -L$(GSL_LIB) $(LSOURCE) -o $@ $(LDFLAGS)

clean:
	rm -f *.o hdp
