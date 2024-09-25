# Concurrent Matrix Solver

## Overview

The Matrix Solver system is designed to support the multiplication of pre-defined matrices of arbitrary sizes. This operation should be performed concurrently, with the ability to add new matrices as well as view the results of individual matrices and display all matrices.

### System Parameters
- The system must operate concurrently.
- Implemented in the Java programming language.
- The system is divided into several components, allowing for easy addition of new components in the future.
- The system should inform the user of any problems and should never completely crash.
- User interaction is through the command line interface (CLI), with configuration through a properties file.

## System Description

The system consists of three components based on a thread pool and several auxiliary components executing in their own threads. Each main component has its own thread pool:
- **Matrix Multiplier**
- **Matrix Extractor**
- **Matrix Brain**

Additionally, individual threads execute:
- **Main / CLI**
- **Task Coordinator**
- **System Explorer**

A shared queue (TaskQueue) is used for assigning and launching new tasks.

### 2.1 System Explorer Thread
The System Explorer component actively searches through specified directories and subdirectories for text files containing matrices. Its functionalities include:
- Directory searching
- Identification of text files (.rix)
- Task creation and allocation to Task Queue
- Detection of new and modified files
- Management of duplicates and modifications

### 2.2 Task Queue
The Task Queue is a structured blocking queue that serves as the central management hub for tasks in the system. Tasks can be defined through the Task interface, which specifies the type of task (CREATE or MULTIPLY) and initiates the operation.

### 2.3 Task Coordinator Thread
The Task Coordinator waits for a task to appear and delegates it to the appropriate thread pool, blocking if no items are in the queue.

### 2.4 Matrix Extractor / Matrix Multiplier Thread Pools
This part of the system handles the core functionalities of the Matrix Extractor and Matrix Multiplier. Each task is divided into smaller segments for processing, optimizing thread usage based on defined limits from the configuration file.

### 2.5 Matrix Brain Thread Pool
The Matrix Brain component is responsible for managing and accessing the results of matrix operations. Users can query for basic information about matrices, perform multiplications (blocking or asynchronous), and save results to files.

### 2.6 Main / CLI Thread
The Main component initializes and manages user interaction, loading settings from the configuration file and allowing command input. It interfaces with System Explorer and Matrix Brain for scanning directories and fetching results.

## Error Handling
The system is designed to be resilient, allowing operations to continue even when encountering problems. Key error scenarios are handled gracefully, informing the user without stopping the system.

## System Configuration and Commands

### 4.1 Configuration File
The system is configured using the `app.properties` file, which includes parameters such as:
- `sys_explorer_sleep_time=1000` (pause time for the system explorer in ms)
- `maximum_file_chunk_size=1024` (limit for matrix extractor in bytes)
- `maximum_rows_size=3` (limit for matrix multiplier)
- `start_dir=/` (starting directory for search)

### 4.2 Commands
The system supports the following commands:

- **`dir dir_name`**: Adds a directory for scanning by the System Explorer.
  
- **`info matrix_name`**: Fetches basic information about a specific matrix or set of matrices with options for sorting and limiting the display.

- **`multiply mat1,mat2`**: Requests multiplication of two matrices, with options for asynchronous execution and naming the resulting matrix.

- **`save -name mat_name -file file_name`**: Saves a matrix to disk.

- **`clear mat_name` / `clear file_name`**: Clears specified matrices or files from the system.

## Notes
- The Matrix Brain component supports both blocking and non-blocking result retrieval.
- Results are cached for efficiency, and the system allows for deletion of stored results.

