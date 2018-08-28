#include <mpi.h>
#include <stdio.h>

int main(int argc, char **argv) {
	// initialize the MPI environment
	MPI_Init(NULL, NULL);

	// get the number of proceses
	int np;
	MPI_Comm_size(MPI_COMM_WORLD, &np);
	
	// get the rank of the process
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	// get the name of the processor
	char name[MPI_MAX_PROCESSOR_NAME];
	int length;
	MPI_Get_processor_name(name, &length);

	// display info
	printf("Hello from %s, rank %d out of %d processors\n", name, rank, np);

	// finalize MPI environment
	MPI_Finalize();
}
