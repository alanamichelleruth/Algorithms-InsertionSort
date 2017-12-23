package csi403;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.*;
import java.util.*;
import java.lang.System;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;



// Extend HttpServlet class
public class SortList extends HttpServlet {

    public PrintWriter out;

  // Standard servlet method 
  public void init() throws ServletException
  {
      // Do any required initialization here - likely none
  }

  // Standard servlet method - we will handle a POST operation
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      response.setContentType("application/json");
      out = response.getWriter();

      try {
          doService(request, response);
      } catch (Exception e){
          out.println("{ 'message' : 'Malformed JSON'}");
      }
  }

  // Standard servlet method - we will not respond to GET
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type and return an error message
      response.setContentType("application/json");
      out = response.getWriter();
      out.println("{ 'message' : 'Use POST!'}");
  }


  // Our main worker method
  // Parses messages e.g. {"inList" : [5, 32, 3, 12]}
  // Returns the list reversed.   
  private void doService(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Get received JSON data from HTTP request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String jsonStr = "";
      if(br != null){
          jsonStr = br.readLine();
      }

      if ((jsonStr.length() - jsonStr.replace("\"", "").length()) > 2){
          out.println("{ 'message' : 'Invalid number of keys'}");
          return;
      }

      // Create JsonReader object
      StringReader strReader = new StringReader(jsonStr);
      JsonReader reader = Json.createReader(strReader);

      // Get the singular JSON object (name:value pair) in this message.    
      JsonObject obj = reader.readObject();

      // From the object get the array named "inList"
      JsonArray inArray = obj.getJsonArray("inList");

      // Sort the data in the list using InsertionSort
      JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();
      int[] arr = new int[inArray.size()];
      for (int i = 0; i < inArray.size(); i++) {
          if (isInteger(inArray.get(i).toString()))
              arr[i] = inArray.getInt(i);
          else{
              out.println("{ 'message' : 'Incorrect data type'}");
              return;
          }
      }

      long timeMS = insertionSort(arr);

      //Build the new array into a JSON array
      for (int i = 0; i < arr.length; i++)
          outArrayBuilder.add(arr[i]);

      out.println("{ \"outList\" : " + outArrayBuilder.build().toString() +
              ",\n \"algorithm\" : \"insertionsort\",\n \"timeMS\" : " + timeMS + "}");
  }

  private long insertionSort(int[] arr){
        long time = System.currentTimeMillis();
        for (int i = 1; i < arr.length; ++i){
            int key = arr[i];
            //Insert arr[i] into the sorted sequence arr[1...i-1]
            int j = i-1;
            while (j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }

        return System.currentTimeMillis() - time;
  }

  private boolean isInteger(String s){
      boolean isInteger = false;
      try{
          Integer.parseInt(s);
          isInteger = true;
      } catch (NumberFormatException nfe){
      }
      return isInteger;
  }
    
  // Standard Servlet method
  public void destroy() {
      // Do any required tear-down here, likely nothing.
  }
}

