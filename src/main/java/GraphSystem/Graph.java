package GraphSystem;

import Controllers.DatabaseFunctionality;
import Controllers.model.Edge;
import Controllers.model.Node;
import java.util.Hashtable;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Graph {

  private int size;
  private Hashtable<String, GraphNode> listOfNodes; // <NodeID, Node>
  // what is the purpose of this?
  private LinkedList<String> listOfNodeIDs;

  // default constructor
  Graph() {
    // TODO: figure out how Graph should end up being initialized/setup
    // (haven't changed this constructor yet -- it may not need to?)

    // width/height???
    size = 0;

    listOfNodes = new Hashtable<>();
  }

  public void initialize() {
    GraphNode node = null;

    // initialize a graph
    // new graph graph??

    // get the nodes from the database function
    ObservableList<Node> nodeList = FXCollections.observableArrayList();
    ObservableList<Edge> edgeList = FXCollections.observableArrayList();

    // freakk inteilkijdh
    // initialize all the graph nodes
    for (Node n : nodeList) {

      nodeList = DatabaseFunctionality.showNodes(nodeList);

      String nodeID = n.getID();

      // add to nodeIDs list?? idk what it is

      int xcoord = Integer.parseInt(n.getXCoord());
      int ycoord = Integer.parseInt(n.getYCoord());

      // creates a single node with the given entered parameters (above)
      node = new GraphNode(nodeID, xcoord, ycoord);

      // ADDS THE NODE
      addNode(node);
    }

    // grab edges from database
    edgeList = DatabaseFunctionality.showEdges(edgeList);
    String[] nodeIDA;
    String node1ID = "";
    String node2ID = "";

    // for each edge, parse the first half of ID, add to the
    for (Edge e : edgeList) {
      // get the first half (delimiter = _)
      nodeIDA = (e.getID()).split("_", 10);

      node1ID = nodeIDA[0];
      node2ID = nodeIDA[1];

      link(node1ID, node2ID);
    }
  }

  LinkedList<String> getNodeIDList() {
    return listOfNodeIDs;
  }

  void addNode(GraphNode node) {
    // put node into listOfNodes with nodeID as the key
    String key = node.getNodeID();
    listOfNodes.put(key, node);
    listOfNodeIDs.add(node.getNodeID()); // also add nodeID to listOfNodeIDs for easy access

    // increment size
    size++;
  }

  // adds a bi-directional edge to the nodes corresponding to both ID's
  void link(String node1ID, String node2ID) {
    // checks to make sure listOfNodes has both nodes before linking
    if (listOfNodes.containsKey(node1ID) && listOfNodes.containsKey(node2ID)) {
      // adds each node to each others list of neighbours
      listOfNodes.get(node1ID).addNeighbour(listOfNodes.get(node2ID));
      listOfNodes.get(node2ID).addNeighbour(listOfNodes.get(node1ID));
    }
  }

  GraphNode getNode(String nodeID) {
    return listOfNodes.get(nodeID);
  }

  int getSize() {
    return size;
  }
}
