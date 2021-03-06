package edu.wpi.cs3733.teamO.GraphSystem;

import static edu.wpi.cs3733.teamO.GraphSystem.Graph.GRAPH;

import edu.wpi.cs3733.teamO.Model.Node;
import edu.wpi.cs3733.teamO.UserTypes.Settings;
import java.util.*;

public abstract class AStarVariant {
  PriorityQueue<Node> frontier;
  Hashtable<Node, Node> cameFrom;
  Hashtable<Node, Double> costSoFar;

  /**
   * gets the algorithm ready to run
   *
   * @param startNode starting node
   */
  protected final void init(Node startNode) {
    for (Node n : GRAPH.getListOfNodes()) {
      n.setPriority(0.0);
      n.setVisited(false);
    }

    frontier = new PriorityQueue<>();
    cameFrom = new Hashtable<>();
    costSoFar = new Hashtable<>();

    frontier.add(startNode);
    cameFrom.put(startNode, new Node()); // didn't come from anywhere at start
    costSoFar.put(startNode, 0.0); // didn't cost anything at start
  }

  /**
   * public method to be called to find the path
   *
   * @param startNode start node
   * @param targetNode end node
   * @return not sure
   */
  public final List<Node> findRoute(Node startNode, Node targetNode) {
    init(startNode);
    boolean foundPath = false;

    foundPath = traverse(targetNode, foundPath);

    return backtrack(startNode, targetNode, foundPath);
  }

  /**
   * the part of the algorithm where the map is traversed
   *
   * @param targetNode destination
   * @param foundPath boolean if the path was found
   * @return boolean if path was found
   */
  protected final boolean traverse(Node targetNode, boolean foundPath) {
    while (!frontier.isEmpty()) {
      Node current = frontier.poll(); // continues searching from next frontier node

      if (current.equals(targetNode)) {
        foundPath = true;
        return foundPath;
      }

      // iterates through current nodes neighbours
      int llsize = current.getNeighbourList().size();

      Set<Node> nList = current.getNeighbourList();

      for (Node next : nList) {
        // if elevator only is selected, ignore all stairs
        if (Settings.getInstance().isElevatorOnly() && next.getNodeType().equals("STAI")) {
          continue;
        }
        // gets next node in neighbours
        // sets next's cost so far to current's cost so far + edge cost
        double newCost = costSoFar.get(current) + current.getDistToNeighbour(next);

        // if cost to next hasn't been calculated yet, or if the newCost is less
        //    than the previously calc'ed cost, replace with newCost
        if (costSoFar.get(next) == null || newCost < costSoFar.get(next)) {
          costSoFar.put(next, newCost);

          // calculates priority (cost from start + distance to target --> lower is better)
          double priority = newCost + heuristic(next, targetNode);
          next.setPriority(priority);
          frontier.add(next);
          cameFrom.put(next, current); // next came from current
        }
      }
    }
    return foundPath;
  }

  /**
   * calculates distance from Node a to Node b
   *
   * @param a first Node
   * @param b second Node
   * @return distance between nodes
   */
  public static double dist(Node a, Node b) {
    int x1 = a.getXCoord();
    int x2 = b.getXCoord();
    int y1 = a.getYCoord();
    int y2 = b.getYCoord();

    double distSq = Math.pow(Math.abs(x1 - x2), 2.0) + Math.pow(Math.abs(y1 - y2), 2.0);

    // if a and b aren't on the same floor, it adds the floor difference to the distance
    if (!a.getFloor().equals(b.getFloor())) {
      int aFloor = -1;
      int bFloor = -1;
      if (a.getFloor().equals("G")) {
        aFloor = 0;
      } else {
        aFloor = Integer.parseInt(a.getFloor());
      }

      if (b.getFloor().equals("G")) {
        bFloor = 0;
      } else {
        bFloor = Integer.parseInt(b.getFloor());
      }

      int floorDiff = Math.abs(aFloor - bFloor);

      distSq += floorDiff;
    }

    return Math.sqrt(distSq);
  }

  /**
   * searches backward to find the fastest path to the destination
   *
   * @param startNode start node
   * @param targetNode end node
   * @param foundPath boolean for if the path was found or not
   * @return the actual path
   */
  protected final LinkedList<Node> backtrack(Node startNode, Node targetNode, boolean foundPath) {
    LinkedList<Node> path = new LinkedList<>();

    if (foundPath) {
      // backtrack to add to path:
      // start by adding target node, then iterate through cameFrom,
      // appending next node to the front of path
      path.add(targetNode);
      Node cameFromNode = targetNode;

      while (!cameFromNode.equals(startNode)) { // goes until it appends startNode
        Node n = cameFrom.get(cameFromNode);
        // doesn't add consecutive elevator/stair nodes to path
        if ((cameFromNode.getNodeType().equals("STAI") || cameFromNode.getNodeType().equals("ELEV"))
            && !(n.getNodeType().equals("STAI") || n.getNodeType().equals("ELEV"))) {
          path.addFirst(cameFromNode);
          path.addFirst(n);
        } else if (!((cameFromNode.getNodeType().equals("STAI") && n.getNodeType().equals("STAI"))
            || (cameFromNode.getNodeType().equals("ELEV") && n.getNodeType().equals("ELEV")))) {
          path.addFirst(n);
        }

        cameFromNode = n;
      }

      return path;

    } else {
      // TODO: throw PathNotFoundException or something
      return null;
    }
  }

  /////////////////////////////////// only thing that's
  // different/////////////////////////////////////////////////////////

  protected abstract double heuristic(Node next, Node targetNode);
}
