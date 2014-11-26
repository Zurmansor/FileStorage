FileStorage
===========
File Storage service is designed to store a very large number of files.

1. Service features:
  - Add files
  - Search file
  - Remove file
  - Automatic removal at the specified time
  - Delete old files on demand
  - The opportunity to get the rest of the available space to write files to the repository

2. In development:
  - Multi-threading support 

3. Main advantages:
  - Perform basic file operations in constant time.
  - High performance independently of the file storage occupancy.
  - Quick search of old files

4. Technical details:
Location of any file in the storage is defined by its name. Therefore, the search and removal is performed in one iteration. Search by creation time is performed with help of files register. Therefore, binary search is used and achieves high performance at any size of storage. The next release is planned to implement the time search through the attributes of folders. This will find files in just 32 iterations at any size of storage.
