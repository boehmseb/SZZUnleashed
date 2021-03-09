/*
 * MIT License
 *
 * Copyright (c) 2018 Axis Communications AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package graph;

import java.io.*;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * A class which represents Annotations between commits.
 *
 * @author Oscar Svensson
 */
public class AnnotationMap extends HashMap<String, List<FileAnnotationGraph>> {

  /** Constructor for the AnnotationMap. */
  public AnnotationMap() {
    super();
  }

  /**
   * A helper method to save a AnnotationMap to JSON.
   *
   * @param path the path to the directory where the JSON file will be written.
   */
  public void saveToJSON(String path) {
    Set<Map.Entry<String, List<FileAnnotationGraph>>> entries = entrySet();

    JSONObject tree = new JSONObject();
    for (Map.Entry<String, List<FileAnnotationGraph>> entry : entries) {
      String commit = entry.getKey();
      List<FileAnnotationGraph> graphs = entry.getValue();

      JSONArray jFileObject = new JSONArray();

      for (FileAnnotationGraph graph : graphs) {
        jFileObject.add(graph.getGraphJSON());
      }

      tree.put(commit, jFileObject);
    }

    if (path != null) {
      try (FileWriter file = new FileWriter(path + "/" + "annotations.json")) {
        file.write(tree.toJSONString());
        file.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("Can't save AnnotationMap to a null path...");
    }
  }
}
