# Algorithms-InsertionSort
Project for Algorithms and Data Structures-- sorting a list.

## Problem
Provide a RESTful service which accepts a POST of a list of integers in JSON format and returns the list as a JSON object in sorted order.
Example input:
{ “inList” 	: [ 5, 35, 1, 272, 12, 0, -2, 12 ] } 
Example output:
{ “outList” 	: [ -2, 0, 1, 5, 12, 12, 35, 272 ], 
  “algorithm” : “quicksort”,
  “timeMS” 	: 52 }
The output JSON must also include the name of the algorithm and the amount of time taken to execute the sort, in milliseconds.
Erroneous input (e.g. malformed JSON) should be handled gracefully with an error message.  
  Example error:	{ “message”	: “Malformed JSON” } 

## Deliverable
An HTTP URL was available for the class project yet was destroyed upon completion.
Users invoked a RESTful service with a tool such as curl or Postman.
